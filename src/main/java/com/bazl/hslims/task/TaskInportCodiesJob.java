package com.bazl.hslims.task;

import com.bazl.hslims.common.Constants;
import com.bazl.hslims.manager.model.po.LimsSampleGene;
import com.bazl.hslims.manager.model.po.LimsSampleInfo;
import com.bazl.hslims.manager.services.common.*;
import com.bazl.hslims.web.helper.codis.CodisContentModel;
import com.bazl.hslims.web.helper.codis.CodisGeneInfo;
import com.bazl.hslims.web.security.LimsSecurityUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import jcifs.smb.SmbFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Administrator on 2017/4/14.
 */
@Component
public class TaskInportCodiesJob {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private LimsSampleGeneService limsSampleGeneService;

    @Autowired
    private LimsSampleInfoService limsSampleInfoService;

    @Autowired
    private BatchImportCodiesService batchImportCodiesService;

    @Autowired
    private QcPersonGeneService qcPersonGeneService;

    @Autowired
    private QcPolluteRecordService qcPolluteRecordService;

    @Autowired
    private DictService dictService;

    /*@Scheduled(fixedRate = 30000)
    public void doTask() throws Exception{
        logger.info("定时任务开始......");
        List<DictItem> serverItemList = dictService.selectDictItemListByType(Constants.DICT_TYPE_SERVER_PATH);
        List<DictItem> dictItemList = dictService.selectDictItemListByType(Constants.DICT_TYPE_CODIES_FILE_PATH);
        List<DictItem> newDictItemList = dictService.selectDictItemListByType(Constants.DICT_TYPE_CODIES_READ_FILE_PATH);
        if (ListUtils.isNotNullAndEmptyList(dictItemList)){
            String serverPath = serverItemList.get(0).getDictName();
            String oldPath = serverPath + dictItemList.get(0).getDictName() + "/";
            String newPath = oldPath + newDictItemList.get(0).getDictName() + "/";

            SmbFile remoteFile;

            remoteFile = new SmbFile(newPath);
            if (!remoteFile.exists()) {
                remoteFile.mkdir();// 创建远程文件夹
            }

            if (new SmbFile(oldPath).exists() && new SmbFile(newPath).exists()){

                int matchLimit = 0;
                int diffLimit = 0;
                List<DictItem> dictItemSameList = dictService.selectDictItemListByType(Constants.DICT_TYPE_QUALITY_SAME_LIMIT);
                List<DictItem> dictItemDiffList = dictService.selectDictItemListByType(Constants.DICT_TYPE_QUALITY_DIFF_LIMIT);
                if (ListUtils.isNotNullAndEmptyList(dictItemSameList)){
                    matchLimit = Integer.parseInt(dictItemSameList.get(0).getDictName());
                }
                if (ListUtils.isNotNullAndEmptyList(dictItemDiffList)){
                    diffLimit = Integer.parseInt(dictItemDiffList.get(0).getDictName());
                }

                SmbFile file = new SmbFile(oldPath);
                SmbFile[] files = file.listFiles();

                if (files != null) {
                    for (int i = 0; i < files.length; i++) {
                        SmbFileInputStream sfi = null;
                        List<CodisContentModel> codisContentModelList = null;
                        if (files[i].isFile()) {
                            try {
                                sfi = new SmbFileInputStream(files[i].getPath());
                                codisContentModelList = CodisFileParser.getDataFromCodisFile(sfi, files[i].getPath());
                            } catch (Exception e) {
                                e.printStackTrace();
                                logger.error(e.getMessage());
                            }

                            if (ListUtils.isNullOrEmptyList(codisContentModelList)) {
                                System.out.println("CODIS文件中未检索到数据!");
                            }

                            List<LimsSampleInfo> sampleListBySampleNo = null;
                            LimsSampleInfo tmpSampleInfo = null;
                            List<LimsSampleGene> tmpSampleGeneList = null;

                            for (CodisContentModel ccm : codisContentModelList) {

                                BatchImportCodies batchImportCodies = new BatchImportCodies();
                                batchImportCodies.setCodiesName(files[i].getName());
                                batchImportCodies.setSampleBarcode(ccm.getSampleNo().trim());

                                sampleListBySampleNo = limsSampleInfoService.selectListBySampleNo(ccm.getSampleNo().trim().toUpperCase());
                                if (ListUtils.isNullOrEmptyList(sampleListBySampleNo)) {
                                    ccm.setImportFlag(Constants.FLAG_FALSE);
                                    ccm.setFailedReason("系统中不存在该检材编号！");
                                    logger.info(ccm.getFailedReason());

                                    batchImportCodies.setStatus(0);
                                    batchImportCodies.setImportReason(ccm.getFailedReason());
                                    batchImportCodiesService.insert(batchImportCodies);
                                    continue;
                                }

                                tmpSampleInfo = sampleListBySampleNo.get(0);
                                tmpSampleGeneList = limsSampleGeneService.selectListBySampleId(tmpSampleInfo.getId());
                                if (ListUtils.isNotNullAndEmptyList(tmpSampleGeneList)) {
                                    ccm.setImportFlag(Constants.FLAG_FALSE);
                                    ccm.setFailedReason("系统中已存在该检材编号的检验结果！");
                                    logger.info(ccm.getFailedReason());

                                    batchImportCodies.setStatus(0);
                                    batchImportCodies.setImportReason(ccm.getFailedReason());
                                    batchImportCodiesService.insert(batchImportCodies);
                                    continue;
                                }

                                try {
                                    LimsSampleGene sampleGene = new LimsSampleGene();
                                    sampleGene = ConvertCodisContentToSampleGene(tmpSampleInfo, ccm);
                                    limsSampleGeneService.insert(sampleGene);
                                    ccm.setImportFlag(Constants.FLAG_TRUE);
                                    logger.info("插入成功!");

                                    QcPersonGene qcPersonGene = new QcPersonGene();
                                    List<QcPersonGene> qcPersonGeneList = qcPersonGeneService.selectList(qcPersonGene);
                                    if (ListUtils.isNotNullAndEmptyList(qcPersonGeneList)){
                                        for (QcPersonGene qpg:qcPersonGeneList){
                                            Map map = null;
                                            String geneInfo = qpg.getQcPersonStrGene();
                                            map = PolluteRecordUtils.compare(sampleGene.getGeneInfo(), geneInfo);

                                            if (map !=null && !map.isEmpty()){
                                                if ((Integer)map.get("matchCount") >= matchLimit
                                                        && (Integer)map.get("diffCount") <= diffLimit){
                                                    QcPolluteRecord qcPolluteRecord = new QcPolluteRecord();
                                                    qcPolluteRecord.setQcPersonId(qpg.getId());
                                                    qcPolluteRecord.setPollutedSampleGeneId(sampleGene.getId());
                                                    qcPolluteRecord.setCreatePerson(qpg.getCreatePerson());

                                                    qcPolluteRecordService.insert(qcPolluteRecord);

                                                }
                                            }
                                        }
                                    }

                                    batchImportCodies.setStatus(1);
                                    batchImportCodies.setImportReason("导入成功！");
                                    batchImportCodiesService.insert(batchImportCodies);
                                } catch (Exception ex) {
                                    ccm.setImportFlag(Constants.FLAG_FALSE);
                                    ccm.setFailedReason("数据格式错误！");
                                    logger.error("数据格式错误！" + ex.getMessage());

                                    batchImportCodies.setStatus(0);
                                    batchImportCodies.setImportReason(ccm.getFailedReason());
                                    batchImportCodiesService.insert(batchImportCodies);
                                }
                            }
                            copyFolder(oldPath, newPath, files[i].getName());

                            delFile(files[i].getPath());

                        }

                    }
                }

            }
        }
        logger.info("定时任务结束......");
    }*/


    private LimsSampleGene ConvertCodisContentToSampleGene(LimsSampleInfo sampleInfo, CodisContentModel codisContentModel) {
        LimsSampleGene sampleGene = new LimsSampleGene();

        sampleGene.setSampleId(sampleInfo.getId());
        sampleGene.setSampleNo(sampleInfo.getSampleNo());
        sampleGene.setGeneType(Constants.GENE_TYPE_STR);

        String geneInfo = "[]";

        try {
            ObjectMapper jsonObjectMapper = new ObjectMapper();
            List<CodisGeneInfo> codisGeneInfoList = codisContentModel.getCodisGeneInfoList();
            geneInfo = jsonObjectMapper.writeValueAsString(codisGeneInfoList);
        } catch (Exception ex) {
            logger.error("CODIS基因型转存json格式错误！", ex.getMessage());
        }

        sampleGene.setGeneInfo(geneInfo);
        sampleGene.setAuditStatus(Constants.FLAG_FALSE);
        sampleGene.setInstoredFlag(Constants.FLAG_FALSE);
        sampleGene.setCreatePerson(LimsSecurityUtils.getLoginName());

        return sampleGene;
    }


    public static void copyFolder(String oldPath, String newPath,String fileName) {

        try {

            SmbFile f = new SmbFile(oldPath + fileName);
            SmbFile f1 = new SmbFile(newPath);
            if (!f1.exists()) { // 目录文件夹不存在，就创建
                f1.mkdirs();
            }
            if (f.exists() && f.isFile()) {
                SmbFile fa1 = new SmbFile(newPath + f.getName());
                f.copyTo(fa1);
            }

        } catch (Exception e) {
            System.out.println("复制文件内容操作出错");
            e.printStackTrace();

        }

    }

    public static void delFile(String path) throws Exception{
        SmbFile file = new SmbFile(path);
        if (!file.exists()) {
            return;
        }
        file.delete();
        System.out.println("删除文件成功！");
    }

}
