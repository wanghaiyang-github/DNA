package com.bazl.hslims.web.controller.center.jk;

import com.bazl.hslims.common.Constants;
import com.bazl.hslims.manager.PageInfo;
import com.bazl.hslims.manager.model.po.*;
import com.bazl.hslims.manager.model.vo.CriminalPersonInfoVo;
import com.bazl.hslims.manager.model.vo.LimsSampleInfoVO;
import com.bazl.hslims.manager.services.common.*;
import com.bazl.hslims.utils.ListUtils;
import com.bazl.hslims.web.controller.BaseController;
import com.bazl.hslims.web.datamodel.CriminalPersonDataModel;
import com.bazl.hslims.web.helper.GeneInfoConverter;
import com.bazl.hslims.web.helper.codis.CodisContentModel;
import com.bazl.hslims.web.helper.codis.CodisFileParser;
import com.bazl.hslims.web.helper.codis.CodisGeneInfo;
import com.bazl.hslims.web.security.LimsSecurityUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Created by wangliu on 2018/8/16.
 */
@Controller
@RequestMapping("/center/jk")
public class JkManage extends BaseController {

    @Autowired
    PanelInfoService panelInfoService;
    @Autowired
    LimsSampleInfoService limsSampleInfoService;
    @Autowired
    CriminalPersonInfoService criminalPersonInfoService;
    @Autowired
    LimsSampleGeneService limsSampleGeneService;
    @Autowired
    QueueSampleService queueSampleService;
    @Autowired
    QueueDetailService queueDetailService;
    @Autowired
    DictService dictService;

    /**
     * 建库登记
     *
     * @param request
     * @return
     */
    @RequestMapping("/jkRegister.html")
    public ModelAndView Register(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();

        List<DictItem> personTypeList = dictService.selectDictItemListByType(Constants.DICT_TPYE_PERSON_TYPE);
        List<DictItem> personGenderList = dictService.selectDictItemListByType(Constants.DICT_TYPE_PERSON_GENDER);
        List<DictItem> personRaceList = dictService.selectDictItemListByType(Constants.DICT_TPYE_PERSON_RACE);

        modelAndView.addObject("personGenderList", personGenderList);
        modelAndView.addObject("personRaceList", personRaceList);
        modelAndView.addObject("personTypeList", personTypeList);
        modelAndView.setViewName("center/jk/addPersonSample");
        return modelAndView;
    }

    @RequestMapping(value = "/insertPerson.html", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> insert(HttpServletRequest request, @RequestBody CriminalPersonDataModel criminalPersonDataModel) {
        Map<String, Object> map = new HashMap<String, Object>();

        List<CriminalPersonInfo> criminalPersonInfoList = criminalPersonDataModel.getCriminalPersonInfoList();

        for (CriminalPersonInfo criminalPersonInfo : criminalPersonInfoList) {
            criminalPersonInfo.setStatus(2);
            criminalPersonInfo.setCollectDatetime(new Date());
            criminalPersonInfo.setIsDeleted(0);

            try {
                criminalPersonInfoService.insert(criminalPersonInfo);
                map.put("success", true);
            } catch (Exception ex) {
                logger.error("添加人员信息错误！", ex);
                map.put("success", false);
            }
        }

        return map;
    }

    /**
     * 导入基因型
     * @param request
     * @return
     */
    @RequestMapping("/jkInputGene.html")
    public ModelAndView InputGene(HttpServletRequest request, PanelInfo panelInfo) {

        ModelAndView modelAndView = new ModelAndView();
        List<PanelInfo> panelInfoList = panelInfoService.selectPanelInfoList(panelInfo);
        modelAndView.addObject("panelInfoList", panelInfoList);
        modelAndView.setViewName("center/jk/inputGene");

        return modelAndView;
    }

    @RequestMapping("/uploadCodis.html")
    public
    @ResponseBody
    Map<String, Object> uploadCodis(MultipartHttpServletRequest request, HttpServletResponse response,
                                    @RequestParam("codisFile") MultipartFile codisFile, String reagentName) throws IOException {
        Map<String, Object> resultMap = new HashMap<>();

        Iterator<String> fileNames = request.getFileNames();
        while (fileNames.hasNext()) {
            //把fileNames的集合中的打出来
            String fileName = fileNames.next();
            List<MultipartFile> fileList = request.getFiles(fileName);
            if (fileList.size() > 0) {
                //遍历文件列表
                Iterator<MultipartFile> fileIte = fileList.iterator();

                List<CodisContentModel> failedList = new ArrayList<CodisContentModel>();
                List<CodisContentModel> succeedList = new ArrayList<CodisContentModel>();

                while (fileIte.hasNext()) {
                    //获取每一个文件
                    MultipartFile multipartFile = fileIte.next();
                    //获取源文件名
                    Map<String, Object> map = new HashMap<>();
                    //初始该文件为成功
                    map.put("success", true);
                    String originalFilename = multipartFile.getOriginalFilename();
                    InputStream inputStream = multipartFile.getInputStream();
                    map.put("filename", originalFilename);

                    List<CodisContentModel> codisContentModelList = null;
                    try {
                        codisContentModelList = CodisFileParser.getDataCodisFile(inputStream, originalFilename, reagentName);
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

                    List<CriminalPersonInfo> criminalPersonInfoList = null;
                    CriminalPersonInfo tmpSampleInfo = null;
                    List<LimsSampleGene> tmpSampleGeneList = null;
                    for (CodisContentModel ccm : codisContentModelList) {
                        criminalPersonInfoList = criminalPersonInfoService.selectByPersonNo(ccm.getSampleNo().trim().toUpperCase());
                        if (ListUtils.isNullOrEmptyList(criminalPersonInfoList)) {
                            ccm.setImportFlag(Constants.FLAG_FALSE);
                            ccm.setFailedReason("系统中不存在该DNA编号！");
                            failedList.add(ccm);
                            continue;
                        }

                        boolean importFlag = CodisFileParser.checkValidGene(ccm.getCodisGeneInfoList());
                        if (!importFlag) {
                            ccm.setImportFlag(Constants.FLAG_FALSE);
                            ccm.setFailedReason("该DNA样本的基因型信息全为空！");
                            failedList.add(ccm);
                            continue;
                        }

                        try {

                            int geneCount = 0;
                            List<CodisGeneInfo> codisGeneInfolist = ccm.getCodisGeneInfoList();
                            //基因型位点个数
                            geneCount = geneCountcompare(codisGeneInfolist);

                            tmpSampleInfo = criminalPersonInfoList.get(0);
                            tmpSampleGeneList = limsSampleGeneService.selectListByCriminalId(tmpSampleInfo.getId());

                            if (ListUtils.isNotNullAndEmptyList(tmpSampleGeneList)) {
                                boolean flag = false;
                                List<CodisGeneInfo> codisGeneInfos = ccm.getCodisGeneInfoList();
                                LimsSampleGene sampleGene = null;

                                for (int j = 0; j < tmpSampleGeneList.size(); j++) {
                                    sampleGene = tmpSampleGeneList.get(j);
                                    if (compare(codisGeneInfos, sampleGene, codisGeneInfos.size())) {
                                        ccm.setImportFlag(Constants.FLAG_FALSE);
                                        ccm.setFailedReason("系统中已存在与该DNA样本相同的检验结果！");
                                        failedList.add(ccm);
                                        flag = false;
                                        break;
                                    } else {
                                        flag = true;
                                    }
                                }

                                if (flag) {
                                    limsSampleGeneService.insert(ConvertCodisContentToSampleGene(tmpSampleInfo, ccm, reagentName, originalFilename, geneCount));
                                    ccm.setImportFlag(Constants.FLAG_TRUE);
                                    succeedList.add(ccm);
                                }

                            } else {
                                limsSampleGeneService.insert(ConvertCodisContentToSampleGene(tmpSampleInfo, ccm, reagentName, originalFilename, geneCount));
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
                    resultMap.put("sampleCountInFile", succeedList.size() + failedList.size());
                    resultMap.put("succeedCountInFile", succeedList.size());
                    resultMap.put("failedCountInFile", failedList.size());
                    resultMap.put("succeedList", succeedList);
                    resultMap.put("failedList", failedList);
                }
            }
        }

        return resultMap;
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

        if (ListUtils.isNullOrEmptyList(geneDetailList)) {
            return false;
        }

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


    private int geneCountcompare(List<CodisGeneInfo> codisGeneInfos) {
        int genecount = 0;
        for (CodisGeneInfo gene1 : codisGeneInfos) {
            if (!Objects.equals(gene1.getGeneVal1(), "")) {
                genecount = genecount + 1;
            }
        }
        return genecount;
    }

    private LimsSampleGene ConvertCodisContentToSampleGene(CriminalPersonInfo criminalPersonInfo, CodisContentModel codisContentModel,
                                                           String reagentName, String originalFilename, int geneCount) {
        LimsSampleGene sampleGene = new LimsSampleGene();

        sampleGene.setCriminalPersonId(criminalPersonInfo.getId());
        sampleGene.setCriminalPersonNo(criminalPersonInfo.getPersonNo());

        String geneInfo = "[]";
        try {
            ObjectMapper jsonObjectMapper = new ObjectMapper();
            List<CodisGeneInfo> codisGeneInfoList = codisContentModel.getCodisGeneInfoList();
            geneInfo = jsonObjectMapper.writeValueAsString(codisGeneInfoList);
            if (CodisFileParser.isMix(geneInfo)) {
                sampleGene.setGeneType(Constants.GENE_TYPE_MIX);
            } else if (CodisFileParser.isYstr(geneInfo)) {
                sampleGene.setGeneType(Constants.GENE_TYPE_YSTR);
            } else {
                sampleGene.setGeneType(Constants.GENE_TYPE_STR);
            }

        } catch (Exception ex) {
            logger.error("CODIS基因型转存json格式错误！", ex);
        }

        if (geneCount >= 16) {
            QueueSample queueSample = new QueueSample();
            queueSample.setQueueType(Constants.TO_NATION_16);
            queueSample.setStatus(Constants.FLAG_FALSE);
            queueSample.setOperatePerson(LimsSecurityUtils.getLoginName());
            queueSampleService.insert(queueSample);

            CriminalPersonInfo criminalPersonInfo1 = criminalPersonInfoService.selectById(criminalPersonInfo.getId());

            QueueDetail queueDetail = new QueueDetail();
            queueDetail.setQueueId(queueSample.getId());
            queueDetail.setSampleId(String.valueOf(criminalPersonInfo1.getId()));
            queueDetail.setStatus(Constants.FLAG_FALSE);
            queueDetail.setCreatePerson(LimsSecurityUtils.getLoginName());
            queueDetailService.insert(queueDetail);

        }

        sampleGene.setGeneInfo(geneInfo);
        sampleGene.setAuditStatus(Constants.FLAG_TRUE);
        sampleGene.setAuditPerson(LimsSecurityUtils.getLoginName());
        sampleGene.setAuditDatetime(new Date());
        sampleGene.setInstoredFlag(Constants.FLAG_FALSE);
        sampleGene.setCodisFileName(originalFilename);
        sampleGene.setGeneCount(String.valueOf(geneCount));
        sampleGene.setReagentName(reagentName);
        if(geneCount <= 16){
            sampleGene.setInstoredFlag(Constants.FLAG_TRUE);
        }else{
            sampleGene.setInstoredFlag(Constants.FLAG_TWO);
        }
        PanelInfo panelInfo = new PanelInfo();
        panelInfo.setPanelName(reagentName);
        List<PanelInfo> list = panelInfoService.selectQueryList(panelInfo);
        if (ListUtils.isNotNullAndEmptyList(list)) {
            sampleGene.setPanelId(list.get(0).getId());
        }
        sampleGene.setCreatePerson(LimsSecurityUtils.getLoginName());

        return sampleGene;
    }


    /**
     * 建库查询
     *
     * @param request
     * @return
     */
    @RequestMapping("/registerInfoList.html")
    public ModelAndView registerInfo(HttpServletRequest request, CriminalPersonInfoVo query, PageInfo pageInfo) {
        ModelAndView modelAndView = new ModelAndView();
        if (StringUtils.isBlank(query.getEntity().getPersonType())) {
            query.getEntity().setPersonType(null);
        } else {
            query.getEntity().setPersonType(query.getEntity().getPersonType());
        }
        if (StringUtils.isBlank(query.getPersonNo())) {
            query.setPersonNo(null);
        } else {
            query.setPersonNo(query.getPersonNo());
        }
        if (StringUtils.isBlank(query.getEntity().getPersonName())) {
            query.getEntity().setPersonName(null);
        } else {
            query.getEntity().setPersonName(query.getEntity().getPersonName());
        }
        if (StringUtils.isBlank(query.getEntity().getIdcardNo())) {
            query.getEntity().setIdcardNo(null);
        } else {
            query.getEntity().setIdcardNo(query.getEntity().getIdcardNo());
        }
        List<CriminalPersonInfoVo> criminalPersonInfoVoList = criminalPersonInfoService.selectVoPaginationList(query, pageInfo);
        int totalCnt = criminalPersonInfoService.selectVoCount(query);
        List<DictItem> personTypeList = dictService.selectDictItemListByType(Constants.DICT_TPYE_PERSON_TYPE);
        List<DictItem> personGenderList = dictService.selectDictItemListByType(Constants.DICT_TYPE_PERSON_GENDER);

        for (CriminalPersonInfoVo criminalPersonInfoVo : criminalPersonInfoVoList) {
            for (DictItem personType : personTypeList) {
                if (criminalPersonInfoVo.getEntity().getPersonType().equals(personType.getDictCode())) {
                    criminalPersonInfoVo.setPersonTypeName(personType.getDictName());
                }
            }
        }
        for (CriminalPersonInfoVo criminalPersonInfoVo : criminalPersonInfoVoList) {
            for (DictItem personGender : personGenderList) {
                if (criminalPersonInfoVo.getEntity().getGender().equals(personGender.getDictCode())) {
                    criminalPersonInfoVo.setGenderName(personGender.getDictName());
                }
            }
        }

        for (CriminalPersonInfoVo criminalPersonInfoVo : criminalPersonInfoVoList) {
            List<LimsSampleGene> limsSampleGeneList = limsSampleGeneService.selectListByCriminalPersonId(criminalPersonInfoVo.getId());
            if (limsSampleGeneList.size() > 0) {
                criminalPersonInfoVo.setPersonGeneFlag(1);
            }
            List<QueueDetail> queueDetails = queueDetailService.selectListBySampleId(criminalPersonInfoVo.getId());
            if (queueDetails.size() > 0) {
                criminalPersonInfoVo.setQueueDetaStatus(1);
            }

        }

        modelAndView.addObject("criminalPersonInfoVoList", criminalPersonInfoVoList);
        modelAndView.addObject("pageInfo", pageInfo.updatePageInfo(totalCnt));
        modelAndView.addObject("criminalPersonInfo", query);
        modelAndView.addObject("personTypeList", personTypeList);
        modelAndView.addObject("personGenderList", personGenderList);
        modelAndView.setViewName("center/jk/registerInfoList");
        return modelAndView;
    }

    @RequestMapping(value = "/acceptCriminalPerson.html")
    @ResponseBody
    public Map<String, Object> criminalPerson(HttpServletRequest request, Integer criminalPersonId) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            if (criminalPersonId != null) {
                CriminalPersonInfo criminalPersonInfo = criminalPersonInfoService.selectById(criminalPersonId);
                result.put("success", true);
                result.put("criminalPersonInfo", criminalPersonInfo);
            }
            return result;
        } catch (Exception e) {
            result.put("error", false);
            return result;
        }
    }

    @RequestMapping(value = "/updateCriminalPerson.html", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> update(HttpServletRequest request, @RequestBody CriminalPersonDataModel criminalPersonDataModel, Integer criminalPersonId) {
        Map<String, Object> map = new HashMap<String, Object>();
        CriminalPersonInfo criminalPersonInfo = null;
        if (criminalPersonId != null) {
            criminalPersonInfo = criminalPersonInfoService.selectById(criminalPersonId);
            CriminalPersonInfo criminalPerson = criminalPersonDataModel.getCriminalPerson();

            try {
                criminalPersonInfo.setPersonName(criminalPerson.getPersonName());
                criminalPersonInfo.setGender(criminalPerson.getGender());
                criminalPersonInfo.setIdcardNo(criminalPerson.getIdcardNo());
                criminalPersonInfo.setPersonType(criminalPerson.getPersonType());
                criminalPersonInfo.setPersonNo(criminalPerson.getPersonNo());
                criminalPersonInfo.setCreatePerson(criminalPerson.getCreatePerson());
                criminalPersonInfo.setRemark(criminalPerson.getRemark());

                criminalPersonInfoService.update(criminalPersonInfo);
                map.put("success", true);
                return map;
            } catch (Exception e) {
                map.put("error", false);
                return map;
            }
        }
        return null;
    }

    @RequestMapping(value = "/delCriminalPerson.html")
    @ResponseBody
    public Map<String, Object> delCriminalPerson(HttpServletRequest request, Integer criminalPersonId) {
        Map<String, Object> result = new HashMap<String, Object>();

        try {
            if (criminalPersonId != 0) {
                criminalPersonInfoService.delete(criminalPersonId);
            }
            result.put("success", true);
        } catch (Exception e) {
            result.put("error", false);
            return result;
        }
        return result;
    }

    /**
     * 建库上报
     *
     * @param request
     * @return
     */
    @RequestMapping("/jkUploadCountryDB.html")
    public ModelAndView UploadCountryDB(HttpServletRequest request, CriminalPersonInfoVo query, PageInfo pageInfo) {

        ModelAndView modelAndView = new ModelAndView();
        if (StringUtils.isBlank(query.getCodisFileName())) {
            query.setCodisFileName(null);
        } else {
            query.setCodisFileName(query.getCodisFileName());
        }
        if (StringUtils.isBlank(query.getPersonNo())) {
            query.setPersonNo(null);
        } else {
            query.setPersonNo(query.getPersonNo());
        }

        if (query.getScanedDatetimeStart() == null){
            query.setScanedDatetimeStart(null);
        }else {
            query.setScanedDatetimeStart(query.getScanedDatetimeStart());
        }

        if (query.getScanedDatetimeEnd() == null){
            query.setScanedDatetimeEnd(null);
        }else {
            query.setScanedDatetimeEnd(query.getScanedDatetimeEnd());
        }

        List<CriminalPersonInfoVo> criminalPersonInfoVoList = criminalPersonInfoService.selectGenePaginationList(query, pageInfo);
        int totalCnt = criminalPersonInfoService.selectVoCount(query);
        List<DictItem> personTypeList = dictService.selectDictItemListByType(Constants.DICT_TPYE_PERSON_TYPE);
        List<DictItem> personGenderList = dictService.selectDictItemListByType(Constants.DICT_TYPE_PERSON_GENDER);
        for (CriminalPersonInfoVo criminalPersonInfoVo : criminalPersonInfoVoList) {
            for (DictItem personType : personTypeList) {
                if (criminalPersonInfoVo.getEntity().getPersonType().equals(personType.getDictCode())) {
                    criminalPersonInfoVo.setPersonTypeName(personType.getDictName());
                }
            }
        }
        for (CriminalPersonInfoVo criminalPersonInfoVo : criminalPersonInfoVoList) {
            for (DictItem personGender : personGenderList) {
                if (criminalPersonInfoVo.getEntity().getGender().equals(personGender.getDictCode())) {
                    criminalPersonInfoVo.setGenderName(personGender.getDictName());
                }
            }
        }

        /*for (CriminalPersonInfoVo criminalPersonInfoVo : criminalPersonInfoVoList) {
            List<LimsSampleGene> limsSampleGeneList = limsSampleGeneService.selectListByCriminalPersonId(criminalPersonInfoVo.getId());
            if (limsSampleGeneList.size() > 0) {
                criminalPersonInfoVo.setPersonGeneFlag(1);
            }
            List<QueueDetail> queueDetails = queueDetailService.selectListBySampleId(criminalPersonInfoVo.getId());
            if (queueDetails.size() > 0) {
                criminalPersonInfoVo.setQueueDetaStatus(1);
            }
        }*/

        modelAndView.addObject("criminalPersonInfoVoList", criminalPersonInfoVoList);
        modelAndView.addObject("pageInfo", pageInfo.updatePageInfo(totalCnt));
        modelAndView.addObject("criminalPersonInfo", query);
        modelAndView.addObject("personTypeList", personTypeList);
        modelAndView.setViewName("center/jk/uploadCountryList");
        return modelAndView;
    }

    @RequestMapping(value = "/uploadQueue.html", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> uploadQueue(HttpServletRequest request, @RequestBody CriminalPersonDataModel criminalPersonDataModel,Integer sampleGeneId) {
        Map<String, Object> map = new HashMap<String, Object>();

        try {
            List<CriminalPersonInfo> criminalPersonInfoList = criminalPersonDataModel.getCriminalPersonInfoList();
            if (ListUtils.isNotNullAndEmptyList(criminalPersonInfoList)) {
                QueueSample queueSample = new QueueSample();
                queueSample.setQueueType(Constants.TO_NATION_16);
                queueSample.setStatus(Constants.FLAG_FALSE);
                queueSample.setOperatePerson(LimsSecurityUtils.getLoginName());
                queueSampleService.insert(queueSample);

                for (CriminalPersonInfo criminalPerson : criminalPersonInfoList) {
                    CriminalPersonInfo criminalPersonInfo = criminalPersonInfoService.selectById(criminalPerson.getId());

                    QueueDetail queueDetail = new QueueDetail();

                    queueDetail.setQueueId(queueSample.getId());
                    queueDetail.setSampleId(String.valueOf(criminalPersonInfo.getId()));
                    queueDetail.setStatus(Constants.FLAG_FALSE);
                    queueDetail.setCreatePerson(LimsSecurityUtils.getLoginName());
                    queueDetailService.insert(queueDetail);

                    List<LimsSampleGene> sampleGeneList = limsSampleGeneService.selectListGeneId(criminalPerson.getSampleGeneId());

                    for (LimsSampleGene sampleGene:sampleGeneList){
                        sampleGene.setInstoredFlag(Constants.FLAG_TWO);
                        limsSampleGeneService.update(sampleGene);
                    }

                    map.put("success", true);
                }
            } else {
                map.put("success", false);
            }
        } catch (Exception e) {
            map.put("success", false);
        }
        return map;
    }

    @RequestMapping("/editGene.html")
    public ModelAndView editGene(HttpServletRequest request,Integer id){

        List<LimsSampleGene> sampleGeneList = limsSampleGeneService.selectListGeneId(id);

        LimsSampleInfo sampleInfo = new LimsSampleInfo();
        LimsSampleGene limsSampleGene = new LimsSampleGene();

        if (ListUtils.isNotNullAndEmptyList(sampleGeneList)) {
            limsSampleGene = sampleGeneList.get(0);

            sampleInfo.setId(limsSampleGene.getCriminalPersonId());
            sampleInfo.setSampleNo(limsSampleGene.getCriminalPersonNo());

            if (StringUtils.isNotBlank(limsSampleGene.getGeneInfo()) && StringUtils.isNotBlank(limsSampleGene.getReagentName())) {
                List<CodisGeneInfo> codisGeneInfoList = GeneInfoConverter.getCodisInfoListByGeneInfoStr(limsSampleGene.getGeneInfo());

                List<CodisGeneInfo> geneInfoList = new ArrayList<>();
                List<String> identifilerPlusList;
                List<String> yfilerPlusList;
                if (limsSampleGene.getReagentName().equalsIgnoreCase(Constants.REGENT_NAME_1)) {
                    identifilerPlusList = Constants.IdentifilerPlusList;

                    geneInfoList = GeneInfoConverter.getCodisInfoList(identifilerPlusList, codisGeneInfoList);
                }else if (limsSampleGene.getReagentName().equalsIgnoreCase(Constants.REGENT_NAME_3)) {
                    yfilerPlusList = Constants.YfilerPlusList;

                    geneInfoList = GeneInfoConverter.getCodisInfoList(yfilerPlusList, codisGeneInfoList);
                }

                String geneInfo = null;
                if (ListUtils.isNotNullAndEmptyList(geneInfoList))
                    geneInfo = GeneInfoConverter.getJsonStrByCodisGeneInfoModel(geneInfoList);

                if (StringUtils.isNotBlank(geneInfo)) {
                    limsSampleGene.setGeneInfo(geneInfo);
                }
            }
        }

        PanelInfo panelInfo = new PanelInfo();
        panelInfo = getPanelInfo(panelInfo);

        List<PanelInfo> panelInfoList = panelInfoService.selectPanelInfoList(panelInfo);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("sampleGeneList",sampleGeneList);
        modelAndView.addObject("sampleInfo",sampleInfo);
        modelAndView.addObject("limsSampleGene",limsSampleGene);
        modelAndView.addObject("panelInfoList",panelInfoList);
        modelAndView.setViewName("center/jk/criminalEditGene");
        return modelAndView;
    }

    @RequestMapping(value="/saveGeneInfoQuery.html", method = RequestMethod.POST, produces="application/json; charset=utf-8")
    @ResponseBody
    public Map<String,Object> saveGeneInfoQuery(HttpServletRequest request, @RequestBody LimsSampleInfoVO sampleInfoVo, Integer geneId){

        PanelInfo panelInfo = new PanelInfo();
        panelInfo.setPanelName(sampleInfoVo.getReagentName());
        List<PanelInfo> panelInfoList = panelInfoService.selectQueryList(panelInfo);

        LimsSampleGene sampleGene = new LimsSampleGene();

        if(ListUtils.isNotNullAndEmptyList(panelInfoList)){
            sampleGene.setPanelId(panelInfoList.get(0).getId());
        }else {
            sampleGene.setPanelId(sampleInfoVo.getPanelInfoId());
        }
        sampleGene.setReagentName(sampleInfoVo.getReagentName());
        sampleGene.setCriminalPersonId(sampleInfoVo.getEntity().getId());
        sampleGene.setCriminalPersonNo(sampleInfoVo.getEntity().getSampleNo());
        sampleGene.setGeneInfo(sampleInfoVo.getGeneInfo());
        if (CodisFileParser.isMix(sampleGene.getGeneInfo())) {
            sampleGene.setGeneType(Constants.GENE_TYPE_MIX);
        }else if (CodisFileParser.isYstr(sampleGene.getGeneInfo())){
            sampleGene.setGeneType(Constants.GENE_TYPE_YSTR);
        }else {
            sampleGene.setGeneType(Constants.GENE_TYPE_STR);
        }
        sampleGene.setInstoredFlag(Constants.FLAG_TRUE);
        sampleGene.setDeleteFlag(Constants.FLAG_FALSE);
        sampleGene.setCreatePerson(LimsSecurityUtils.getLoginName());

        Map<String,Object> result = new HashMap<>();

        List<LimsSampleGene> sampleGeneList = limsSampleGeneService.selectListGeneId(geneId);
        try {
            if(ListUtils.isNotNullAndEmptyList(sampleGeneList)){
                sampleGene.setId(geneId);
                sampleGene.setUpdatePerson(LimsSecurityUtils.getLoginName());
                sampleGene.setAuditStatus(sampleGeneList.get(0).getAuditStatus());
                limsSampleGeneService.update(sampleGene);
            }else {
                sampleGene.setAuditStatus(Constants.FLAG_FALSE);
                limsSampleGeneService.insert(sampleGene);
            }
            result.put("geneId",sampleGene.getId());
            result.put("success",true);
        }catch (Exception e){
            logger.error("saveGeneInfo error!",e);
            result.put("error",false);
        }

        return result;
    }

    public PanelInfo getPanelInfo(PanelInfo panelInfo){

        if (panelInfo.getId() == null){
            panelInfo.setId(null);
        }else {
            panelInfo.setId(panelInfo.getId());
        }

        if(StringUtils.isBlank(panelInfo.getPanelName())){
            panelInfo.setPanelName(null);
        }else {
            panelInfo.setPanelName(panelInfo.getPanelName());
        }

        if(StringUtils.isBlank(panelInfo.getCreatePerson())){
            panelInfo.setCreatePerson(null);
        }else {
            panelInfo.setCreatePerson(panelInfo.getCreatePerson());
        }

        if(StringUtils.isBlank(panelInfo.getPanelProducer())){
            panelInfo.setPanelProducer(null);
        }else {
            panelInfo.setPanelProducer(panelInfo.getPanelProducer());
        }

        if(StringUtils.isBlank(panelInfo.getPanelVersion())){
            panelInfo.setPanelVersion(null);
        }else {
            panelInfo.setPanelVersion(panelInfo.getPanelVersion());
        }

        return panelInfo;
    }

}
