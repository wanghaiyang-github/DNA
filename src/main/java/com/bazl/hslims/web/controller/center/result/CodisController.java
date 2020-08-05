package com.bazl.hslims.web.controller.center.result;

import com.bazl.hslims.common.Constants;
import com.bazl.hslims.manager.model.po.LimsSampleGene;
import com.bazl.hslims.manager.model.po.LimsSampleInfo;
import com.bazl.hslims.manager.model.po.PanelInfo;
import com.bazl.hslims.manager.services.common.LimsSampleGeneService;
import com.bazl.hslims.manager.services.common.LimsSampleInfoService;
import com.bazl.hslims.manager.services.common.PanelInfoService;
import com.bazl.hslims.utils.ListUtils;
import com.bazl.hslims.web.controller.BaseController;
import com.bazl.hslims.web.helper.codis.CodisContentModel;
import com.bazl.hslims.web.helper.codis.CodisFileParser;
import com.bazl.hslims.web.helper.codis.CodisGeneInfo;
import com.bazl.hslims.web.security.LimsSecurityUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/1/4.
 */
@Controller
@RequestMapping("/center/3")
public class CodisController extends BaseController {

    @Autowired
    LimsSampleGeneService limsSampleGeneService;
    @Autowired
    LimsSampleInfoService limsSampleInfoService;
    @Autowired
    PanelInfoService panelInfoService;

    @RequestMapping("/codis.html")
    public ModelAndView codis(HttpServletRequest request) {

        PanelInfo panelInfo = new PanelInfo();

        List<PanelInfo> panelInfoList = panelInfoService.selectPanelInfoList(panelInfo);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("panelInfoList", panelInfoList);
        modelAndView.setViewName("center/result/importCodis");
        return modelAndView;
    }

    @RequestMapping("/uploadCodis.html")
    public
    @ResponseBody
    Map<String, Object> uploadCodis(HttpServletRequest request, HttpServletResponse response,
                                    @RequestParam("codisFile") MultipartFile codisFile, String reagentName) {
        Map<String, Object> resultMap = new HashMap<>();

        List<CodisContentModel> codisContentModelList = null;
        try {
            codisContentModelList = CodisFileParser.getDataCodisFile(codisFile.getInputStream(), codisFile.getOriginalFilename(), reagentName);
        } catch (Exception ex) {
            resultMap.put("success", false);
            resultMap.put("errMsg", ex.getMessage());
            return resultMap;
        }

        if (ListUtils.isNullOrEmptyList(codisContentModelList)) {
            resultMap.put("success", false);
            resultMap.put("errMsg", "CODIS文件中未检索到数据！");
            return resultMap;
        }

        List<CodisContentModel> failedList = new ArrayList<CodisContentModel>();
        List<CodisContentModel> succeedList = new ArrayList<CodisContentModel>();

        List<LimsSampleInfo> sampleListBySampleNo = null;
        List<LimsSampleInfo> sampleListBySampleNo1 = null;
        LimsSampleInfo tmpSampleInfo = null;
        List<LimsSampleGene> tmpSampleGeneList = null;

        for (CodisContentModel ccm : codisContentModelList) {
            String SampleNo = null;
            if (ccm.getSampleNo().trim().length() > 20 && ccm.getSampleNo().trim().substring(19, 20).equals("-")) {
                SampleNo = ccm.getSampleNo().trim().substring(0, 18);
            } else if (ccm.getSampleNo().trim().length() > 19 && ccm.getSampleNo().trim().substring(17, 18).equals("-")) {
                SampleNo = ccm.getSampleNo().trim().substring(0, 17);
            } else if (ccm.getSampleNo().trim().length() > 18 && ccm.getSampleNo().trim().substring(16, 17).equals("-")) {
                SampleNo = ccm.getSampleNo().trim().substring(0, 16);
            } else if (ccm.getSampleNo().trim().length() > 17 && ccm.getSampleNo().trim().substring(15, 16).equals("-")) {
                SampleNo = ccm.getSampleNo().trim().substring(0, 15);
            }

            if (null != SampleNo) {
                sampleListBySampleNo1 = limsSampleInfoService.selectListBySampleNo(SampleNo);

                List<LimsSampleInfo> limsSampleInfoList = limsSampleInfoService.selectListBySampleNo(ccm.getSampleNo().trim());
                //判断是否存在该样本
                if (limsSampleInfoList.size() == 0) {
                    //精斑检材拆分为-CD和-SQ
                    for (LimsSampleInfo sampleBySampleNo : sampleListBySampleNo1) {
                        if (sampleBySampleNo.getSampleType().equals("02")) {
                            LimsSampleInfo sampleInfo = sampleBySampleNo;
                            try {
                                sampleInfo.setSampleNo(SampleNo + "-SQ");
                                limsSampleInfoService.insertSample(sampleInfo);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            try {
                                sampleInfo.setSampleNo(SampleNo + "-CD");
                                limsSampleInfoService.insertSample(sampleInfo);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }

        for (CodisContentModel ccm : codisContentModelList) {
            sampleListBySampleNo = limsSampleInfoService.selectListBySampleNo(ccm.getSampleNo().trim().toUpperCase());
            if (ListUtils.isNullOrEmptyList(sampleListBySampleNo)) {
                ccm.setImportFlag(Constants.FLAG_FALSE);
                ccm.setFailedReason("系统中不存在该检材编号！");
                failedList.add(ccm);
                continue;
            }

            boolean importFlag = CodisFileParser.checkValidGene(ccm.getCodisGeneInfoList());
            if (!importFlag) {
                ccm.setImportFlag(Constants.FLAG_FALSE);
                ccm.setFailedReason("该检材的基因型信息全为空！");
                failedList.add(ccm);
                continue;
            }

            try {
                tmpSampleInfo = sampleListBySampleNo.get(0);
                tmpSampleGeneList = limsSampleGeneService.selectListBySampleId(tmpSampleInfo.getId());
                if (ListUtils.isNotNullAndEmptyList(tmpSampleGeneList)) {

                    boolean flag = false;
                    List<CodisGeneInfo> codisGeneInfos = ccm.getCodisGeneInfoList();
                    LimsSampleGene sampleGene = null;

                    for (int j = 0; j < tmpSampleGeneList.size(); j++) {
                        sampleGene = tmpSampleGeneList.get(j);
                        if (compare(codisGeneInfos, sampleGene, codisGeneInfos.size())) {
                            ccm.setImportFlag(Constants.FLAG_FALSE);
                            ccm.setFailedReason("系统中已存在该检材编号相同的检验结果！");
                            failedList.add(ccm);
                            flag = false;
                            break;
                        } else {
                            flag = true;
                        }
                    }

                    if (flag) {
                        limsSampleGeneService.insert(ConvertCodisContentToSampleGene(tmpSampleInfo, ccm, reagentName));
                        ccm.setImportFlag(Constants.FLAG_TRUE);
                        succeedList.add(ccm);
                    }

                } else {
                    limsSampleGeneService.insert(ConvertCodisContentToSampleGene(tmpSampleInfo, ccm, reagentName));
                    ccm.setImportFlag(Constants.FLAG_TRUE);
                    succeedList.add(ccm);
                }
            } catch (Exception ex) {
                ccm.setImportFlag(Constants.FLAG_FALSE);
                ccm.setFailedReason("数据格式错误！");
                failedList.add(ccm);
            }
        }

        resultMap.put("success", true);
        resultMap.put("sampleCountInFile", codisContentModelList.size());
        resultMap.put("succeedCountInFile", succeedList.size());
        resultMap.put("failedCountInFile", failedList.size());
        resultMap.put("succeedList", succeedList);
        resultMap.put("failedList", failedList);
        return resultMap;
    }

    private LimsSampleGene ConvertCodisContentToSampleGene(LimsSampleInfo sampleInfo, CodisContentModel codisContentModel, String reagentName) {
        LimsSampleGene sampleGene = new LimsSampleGene();

        sampleGene.setSampleId(sampleInfo.getId());
        sampleGene.setSampleNo(sampleInfo.getSampleNo());

        String geneInfo = "[]";

        try {
            ObjectMapper jsonObjectMapper = new ObjectMapper();
            //jsonObjectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
            List<CodisGeneInfo> codisGeneInfoList = codisContentModel.getCodisGeneInfoList();
            geneInfo = jsonObjectMapper.writeValueAsString(codisGeneInfoList);

            if (CodisFileParser.isMix(geneInfo)) {
                sampleGene.setGeneType(Constants.GENE_TYPE_MIX);
            } else if (CodisFileParser.isYstr(geneInfo)) {
                sampleGene.setGeneType(Constants.GENE_TYPE_YSTR);
            }else {
                sampleGene.setGeneType(Constants.GENE_TYPE_STR);
            }
            if (reagentName.equals("Investigator 24plex")){
                sampleGene.setGeneType(Constants.GENE_TYPE_STR);
            }
        } catch (Exception ex) {
            logger.error("CODIS基因型转存json格式错误！", ex);
        }

        sampleGene.setGeneInfo(geneInfo);
        sampleGene.setAuditStatus(Constants.FLAG_FALSE);
        sampleGene.setInstoredFlag(Constants.FLAG_FALSE);
        sampleGene.setReagentName(reagentName);
        PanelInfo panelInfo = new PanelInfo();
        panelInfo.setPanelName(reagentName);
        List<PanelInfo> list = panelInfoService.selectQueryList(panelInfo);
        if (ListUtils.isNotNullAndEmptyList(list)) {
            sampleGene.setPanelId(list.get(0).getId());
        }
        sampleGene.setCreatePerson(LimsSecurityUtils.getLoginName());

        return sampleGene;
    }

    private boolean compare(List<CodisGeneInfo> codisGeneInfos, LimsSampleGene sampleGene, Integer count) {

        ObjectMapper jsonObjectMapper = new ObjectMapper();

        String geneStr = sampleGene.getGeneInfo();
        List<CodisGeneInfo> geneDetailList = null;
        try {
            geneDetailList = jsonObjectMapper.readValue(geneStr, jsonObjectMapper.getTypeFactory().constructCollectionType(List.class, CodisGeneInfo.class));
        } catch (Exception ex) {
            logger.error("解析基因分型信息错误！", ex);
        }

        if (ListUtils.isNullOrEmptyList(geneDetailList))
            return false;

        Integer sameCount = 0;
        String geneName = "";
        String gene1Val1 = "";
        String gene1Val2 = "";

        String gene2Val1 = "";
        String gene2Val2 = "";
        for (CodisGeneInfo gene1 : codisGeneInfos) {
            geneName = gene1.getGeneName();
            gene1Val1 = gene1.getGeneVal1();
            gene1Val2 = gene1.getGeneVal2();

            for (CodisGeneInfo gene2 : geneDetailList) {
                if (gene2.getGeneName().toUpperCase().equals(geneName.toUpperCase())) {
                    gene2Val1 = gene2.getGeneVal1();
                    gene2Val2 = gene2.getGeneVal2();

                    if ((gene1Val1.toUpperCase().equals(gene2Val1.toUpperCase()) && gene1Val2.toUpperCase().equals(gene2Val2.toUpperCase())
                            || (gene1Val1.toUpperCase().equals(gene2Val2.toUpperCase())) && gene1Val2.toUpperCase().equals(gene2Val1.toUpperCase()))) {
                        sameCount++;
                        break;
                    }
                }
            }
        }
        return sameCount.equals(count);
    }

}
