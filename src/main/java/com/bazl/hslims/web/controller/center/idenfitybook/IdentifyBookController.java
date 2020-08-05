package com.bazl.hslims.web.controller.center.idenfitybook;

import com.bazl.hslims.common.Constants;
import com.bazl.hslims.manager.PageInfo;
import com.bazl.hslims.manager.model.GeneInfoModel;
import com.bazl.hslims.manager.model.po.*;
import com.bazl.hslims.manager.model.vo.LimsCaseInfoVO;
import com.bazl.hslims.manager.services.common.*;
import com.bazl.hslims.utils.DateUtils;
import com.bazl.hslims.utils.ListUtils;
import com.bazl.hslims.utils.SystemUtil;
import com.bazl.hslims.web.controller.BaseController;
import com.bazl.hslims.web.datamodel.CaseCompareResultGroup;
import com.bazl.hslims.web.datamodel.CaseInnerMatchedModel;
import com.bazl.hslims.web.datamodel.SampleGeneDataModel;
import com.bazl.hslims.web.helper.codis.CodisGeneInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import freemarker.template.Template;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2017/1/6.
 */
@Controller
@RequestMapping("/center/4")
public class IdentifyBookController extends BaseController {

    @Autowired
    LimsCaseInfoService limsCaseInfoService;
    @Autowired
    LimsConsignmentService limsConsignmentService;
    @Autowired
    LimsSampleInfoService limsSampleInfoService;
    @Autowired
    LimsPersonInfoService limsPersonInfoService;
    @Autowired
    LimsIdentifyBookService limsIdentifyBookService;
    @Autowired
    LimsIdentifyBookNoticeService limsIdentifyBookNoticeService;
    @Autowired
    LimsExtractRecordService limsExtractRecordService;
    @Autowired
    LimsPcrRecordService limsPcrRecordService;
    @Autowired
    LimsSyRecordService limsSyRecordService;
    @Autowired
    CaseInnerMatchedService caseInnerMatchedService;
    @Autowired
    LimsSampleGeneService limsSampleGeneService;
    @Autowired
    DictService dictService;
    @Autowired
    LabUserService labUserService;
    @Autowired
    CaseCompareResultInfoService caseCompareResultInfoService;
    @Autowired
    PanelInfoService panelInfoService;
    @Autowired
    QueueSampleService queueSampleService;

    @RequestMapping("/01.html")
    public ModelAndView listPending(HttpServletRequest request, LimsCaseInfoVO query, PageInfo pageInfo, Integer caseId) throws ParseException {
        List<DictItem> caseStatusList = dictService.selectDictItemListByType(Constants.DICT_TYPE_CASE_STATUS);
        List<DictItem> caseTypeList = dictService.selectDictItemListByType(Constants.DICT_TPYE_CASE_TYPE);
        List<DictItem> casePropertyList = dictService.selectDictItemListByType(Constants.DICT_TPYE_CASE_PROPERTY);

        if (query.getIdentifyBookStatus() == null) {
            query.setIdentifyBookStatus("01");
        }

        query = resetCaseInfoQuery(query);
        query.setCaseStatusList(Arrays.asList(Constants.CASE_INFO_STATUS_ACCEPTED, Constants.CASE_INFO_STATUS_FINISHED));
        query.setAdditionalFlag(Constants.FLAG_FALSE);
        /*query.setIdentifyBookStatus(Constants.IDENTIFY_BOOK_PENDING);*/

        query.setOrderByClause(" caseNo1 DESC, caseNo2 DESC");

        if (caseId != null) {
            query.getEntity().setId(caseId);
        }

        List<LimsCaseInfoVO> caseInfoList = limsCaseInfoService.selectVOIdentifyBookList(query, pageInfo);
        int totalCnt = limsCaseInfoService.selectVOIdentifyBookCount(query);

        if (caseId != null) {
            LimsCaseInfo limsCaseInfo = limsCaseInfoService.selectById(caseId);
            query.getEntity().setCaseNo(limsCaseInfo.getCaseNo());
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("caseStatusList", caseStatusList);
        modelAndView.addObject("caseTypeList", caseTypeList);
        modelAndView.addObject("caseInfoQuery", query);
        modelAndView.addObject("casePropertyList", casePropertyList);
        modelAndView.addObject("caseInfoList", caseInfoList);
        modelAndView.addObject("pageInfo", pageInfo.updatePageInfo(totalCnt));
        modelAndView.addObject("loginTitleName", SystemUtil.getSystemConfig().getProperty("loginTitleName"));
        modelAndView.setViewName("center/identifybook/listAppraisal");
        return modelAndView;
    }


    @RequestMapping("/generateReport.html")
    @ResponseBody
    public boolean generateReport(HttpServletRequest request, HttpServletResponse response, Integer caseId) {
        boolean flag = true;

        LimsCaseInfo caseInfo = limsCaseInfoService.selectById(caseId);
        LimsConsignment consignment = null;

        List<LimsConsignment> listCon = limsConsignmentService.selectListByCaseId(caseId);
        for (LimsConsignment co : listCon) {
            if (co.getAdditionalFlag().equals(Constants.FLAG_FALSE)) {
                consignment = co;
                break;
            }
        }

        List<CaseInnerMatched> caseInnerMatchedList = caseInnerMatchedService.selectListByCaseId(caseId);
        List<LimsSampleInfo> sampleInfoList = new ArrayList<>();

        List<LimsSampleInfo> sampleInfoPersonList = null;
        List<LimsSampleInfo> sampleInfoEvidencrList = null;
        LimsSampleInfo personLimsSampleInfo = new LimsSampleInfo();
        LimsSampleInfo personAdditionLimsSampleInfo = new LimsSampleInfo();
        LimsSampleInfo evidenceLimsSampleInfo = new LimsSampleInfo();
        LimsSampleInfo evidenceAdditionLimsSampleInfo = new LimsSampleInfo();
        for (LimsConsignment co : listCon) {
            if (co.getAdditionalFlag().equals(Constants.FLAG_FALSE)) {

                personLimsSampleInfo.setConsignmentId(co.getId());
                personLimsSampleInfo.setSampleFlag(Constants.SAMPLE_FLAG_PERSON);
                sampleInfoPersonList = limsSampleInfoService.selectList(personLimsSampleInfo);

                evidenceLimsSampleInfo.setConsignmentId(co.getId());
                evidenceLimsSampleInfo.setSampleFlag(Constants.SAMPLE_FLAG_EVIDENCE);
                sampleInfoEvidencrList = limsSampleInfoService.selectList(evidenceLimsSampleInfo);

                if (ListUtils.isNotNullAndEmptyList(sampleInfoPersonList)) {
                    sampleInfoList.addAll(sampleInfoPersonList);
                }

                if (ListUtils.isNotNullAndEmptyList(sampleInfoEvidencrList)) {
                    sampleInfoList.addAll(sampleInfoEvidencrList);
                }
            }

            if (co.getAdditionalFlag().equals(Constants.FLAG_TRUE)) {
                personLimsSampleInfo.setConsignmentId(co.getId());
                personLimsSampleInfo.setSampleFlag(Constants.SAMPLE_FLAG_PERSON);
                sampleInfoPersonList = limsSampleInfoService.selectList(personLimsSampleInfo);

                evidenceLimsSampleInfo.setConsignmentId(co.getId());
                evidenceLimsSampleInfo.setSampleFlag(Constants.SAMPLE_FLAG_EVIDENCE);
                sampleInfoEvidencrList = limsSampleInfoService.selectList(evidenceLimsSampleInfo);

                if (ListUtils.isNotNullAndEmptyList(sampleInfoPersonList)) {
                    sampleInfoList.addAll(sampleInfoPersonList);
                }

                if (ListUtils.isNotNullAndEmptyList(sampleInfoEvidencrList)) {
                    sampleInfoList.addAll(sampleInfoEvidencrList);
                }
            }

        }


        List<LimsSampleGene> sampleGeneList = limsSampleGeneService.selectAuditedListByCaseId(caseId);
        String panels = "";
        if (ListUtils.isNotNullAndEmptyList(sampleGeneList)) {
            LimsSampleGene sampleGene = sampleGeneList.get(0);
            Panel p = new Panel();
            p.setPanelInfoId(sampleGene.getPanelId());
            List<Panel> panelList = panelInfoService.selectPanelList(p);

            if (ListUtils.isNotNullAndEmptyList(panelList)) {
                for (Panel panel : panelList) {
                    if (!panel.getMarkerName().equalsIgnoreCase("AMEL")) {
                        panels += panel.getMarkerName() + "、";
                    }
                    if (panel.getPanelName().equals("Investigator 24plex")){
                        panels = Constants.IPPANELS;
                        break;
                    }
                }
                for (Panel panel : panelList) {
                    if (!panel.getPanelName().equals("Investigator 24plex")){
                        panels = panels.substring(0, panels.length() - 1);
                        break;
                    }
                }

            } else {
                panels = Constants.PANELS;
            }
        }


        List<LimsSampleGene> mixSampleGeneList = limsSampleGeneService.selectMixListByCaseId(caseId);


        List<LimsSampleGene> newSampleGeneList = new ArrayList<>();
        if (ListUtils.isNotNullAndEmptyList(sampleGeneList)) {
            newSampleGeneList.addAll(sampleGeneList);
        }

        List<LimsSampleGene> newMixSampleGeneList = new ArrayList<>();
        if (ListUtils.isNotNullAndEmptyList(mixSampleGeneList)) {
            newMixSampleGeneList.addAll(mixSampleGeneList);
        }

        Map<String, Object> params = new HashMap<String, Object>();

        List<Map<String, Object>> sampleNoList = new ArrayList<>();
        String acceptTime = "";
        String year = null;
        for (LimsConsignment con : listCon) {
            Map<String, Object> paramMap = new HashMap<>();
            List<LimsSampleInfo> limsSampleInfoList = limsSampleInfoService.selectListByConsignmentId(con.getId());
            if (ListUtils.isNotNullAndEmptyList(limsSampleInfoList)) {
                LimsSampleInfo limsSampleInfo = limsSampleInfoList.get(0);
                paramMap.put("sampleFirstNo", StringUtils.isBlank(limsSampleInfo.getSampleNo()) ? "" : limsSampleInfo.getSampleNo());
                if (sampleInfoList.size() > 1) {
                    limsSampleInfo = limsSampleInfoList.get(limsSampleInfoList.size() - 1);
                    paramMap.put("sampleLastNo", StringUtils.isBlank(limsSampleInfo.getSampleNo()) ? "" : limsSampleInfo.getSampleNo());
                } else {
                    paramMap.put("sampleLastNo", null);
                }
            } else {
                paramMap.put("sampleFirstNo", "");
                paramMap.put("sampleLastNo", null);
            }
            paramMap.put("acceptDatetime", DateUtils.dateToString(con.getAcceptDatetime(), "yyyy年M月d日"));

            acceptTime = DateUtils.dateToString(con.getAcceptDatetime(), "yyyy年M月d日");
            year = String.format("%tY", con.getAcceptDatetime());

            if (con.getAdditionalFlag().equals(Constants.FLAG_TRUE)) {
                if (StringUtils.isNotBlank(year)) {
                    if (year.equals(String.format("%tY", con.getAcceptDatetime()))) {
                        acceptTime += "、" + DateUtils.dateToString(con.getAcceptDatetime(), "M月d日");
                    } else {
                        acceptTime += "、" + DateUtils.dateToString(con.getAcceptDatetime(), "yyyy年M月d日");
                    }
                }
            }

            sampleNoList.add(paramMap);
        }

        params.put("sampleNoList", sampleNoList);

        String caseNo = caseInfo.getCaseNo();

        if (StringUtils.isBlank(caseNo)) {
            params.put("year", "");
            params.put("no", "");
        } else {
            if (caseNo.contains("-")) {
                if (caseNo.split("-").length > 2) {
                    params.put("year", (caseNo.split("-")[1]));
                    params.put("no", (caseNo.split("-")[2]));
                } else {
                    params.put("year", (caseNo.split("-")[0]));
                    params.put("no", (caseNo.split("-")[1]));
                }
            } else {
                params.put("year", caseNo.substring(0, 4));
                params.put("no", caseNo.substring(4, caseNo.length()));
            }
        }
        String matchCaseNo = consignment.getMatchCaseNo();
        LimsSampleGene newSampleGene = new LimsSampleGene();

        if (StringUtils.isNotBlank(matchCaseNo)) {
            if (matchCaseNo.contains("-")) {
                if (matchCaseNo.split("-").length > 2) {
                    params.put("matchYear", (matchCaseNo.split("-")[1]));
                    params.put("matchNo", (matchCaseNo.split("-")[2]));
                } else {
                    params.put("matchYear", (matchCaseNo.split("-")[0]));
                    params.put("matchNo", (matchCaseNo.split("-")[1]));
                }
            } else {
                params.put("matchYear", matchCaseNo.substring(0, 4));
                params.put("matchNo", matchCaseNo.substring(4, matchCaseNo.length()));
            }

            newSampleGene.setSampleNo(matchCaseNo);
            newSampleGene.setAuditStatus(Constants.FLAG_TRUE);

            List<LimsSampleGene> matchSampleGeneList = limsSampleGeneService.selectGeneInfoList(newSampleGene);

            if (ListUtils.isNotNullAndEmptyList(matchSampleGeneList)) {
                for (LimsSampleGene matchSampleGene : matchSampleGeneList) {
                    if (matchSampleGene.getGeneType().equals(Constants.GENE_TYPE_MIX)) {
                        newMixSampleGeneList.add(matchSampleGene);
                    }
                    if (matchSampleGene.getGeneType().equals(Constants.GENE_TYPE_STR)) {
                        newSampleGeneList.add(matchSampleGene);
                    }
                }
            }
        } else {
            params.put("matchYear", "");
            params.put("matchNo", "");
        }
        params.put("caseName", StringUtils.isBlank(caseInfo.getCaseName()) ? "" : caseInfo.getCaseName());
        params.put("caseBrief", (StringUtils.isBlank(caseInfo.getCaseBrief()) ? "" : caseInfo.getCaseBrief()));
        params.put("acceptor", StringUtils.isBlank(consignment.getAcceptor()) ? "" : consignment.getAcceptor());
        params.put("acceptDatetime", acceptTime);
        params.put("delegateOrgName", StringUtils.isBlank(consignment.getDelegateOrgDesc()) ? "" : consignment.getDelegateOrgDesc());
        params.put("delegator1", StringUtils.isBlank(consignment.getDelegator1()) ? "" : consignment.getDelegator1());
        params.put("delegator1Cno", StringUtils.isBlank(consignment.getDelegator1Cno()) ? "" : consignment.getDelegator1Cno());
        params.put("delegator1Phone", StringUtils.isBlank(consignment.getDelegator1Phone()) ? "" : consignment.getDelegator1Phone());
        params.put("delegator2", StringUtils.isBlank(consignment.getDelegator2()) ? "" : consignment.getDelegator2());
        params.put("delegator2Cno", StringUtils.isBlank(consignment.getDelegator2Cno()) ? "" : consignment.getDelegator2Cno());
        params.put("delegator2Phone", StringUtils.isBlank(consignment.getDelegator2Phone()) ? "" : consignment.getDelegator2Phone());
        params.put("identifyRequirement", StringUtils.isBlank(consignment.getIdentifyRequirement()) ? "" : consignment.getIdentifyRequirement());
        params.put("identifyRequirementOther", StringUtils.isBlank(consignment.getIdentifyRequirementOther()) ? null : consignment.getIdentifyRequirementOther());
        params.put("matchCaseNo", StringUtils.isBlank(consignment.getMatchCaseNo()) ? null : consignment.getMatchCaseNo());
        params.put("preIdentifyDesc", StringUtils.isBlank(consignment.getPreIdentifyDesc()) ? "" : consignment.getPreIdentifyDesc());
        params.put("reidentifyReason", StringUtils.isBlank(consignment.getReidentifyReason()) ? "" : consignment.getReidentifyReason());
        params.put("currentDate", DateUtils.dateToString(new Date(), "yyyy年M月d日"));
        Calendar now = Calendar.getInstance();
        params.put("currentYear", String.format("%tY", new Date()));
        params.put("currentMonth", now.get(Calendar.MONTH) + 1);
        params.put("currentDay", now.get(Calendar.DAY_OF_MONTH));

        List<LimsExtractRecord> extractRecordList = limsExtractRecordService.selectListByCaseId(caseId);
        if (ListUtils.isNotNullAndEmptyList(extractRecordList)) {
            Date extractDatetime = extractRecordList.get(0).getExtractDatetime();
            params.put("extractDatetime", DateUtils.dateToString(extractDatetime, "yyyy年M月d日"));
        } else {
            params.put("extractDatetime", "");
        }
        String sampleBarcode = "";
        String sampleType = "";
        String sampleBarcodeHB = "";//人血
        String sampleBarcodePSA = "";//精斑
        String sampleBarcodeHB_L = "";//人血阳性
        String sampleBarcodeHB_N = "";//人血阴性
        String sampleBarcodePSA_L = "";//精斑阳性
        String sampleBarcodePSA_N = "";//精斑阴性
        String extractMethodCellCZ = "";//脱落细胞磁珠法
        String extractMethodPSACZ = "";//精斑磁珠法
        String extractMethodPSACZ_SQ = "";//精斑磁珠法，上清溶液
        String extractMethodPSACZ_CD = "";//精斑磁珠法，沉淀
        String extractMethodJBYX = "";//血、唾液班、软骨、组织
        String extractMethodGJMXF = "";//骨骼、牙齿 硅胶膜吸附法
        String extractMethodAUTOXF = "";//骨骼、牙齿 全自动工作站法

        String checkHBNoStr = "";//检出人血未检出基因型
        String checkHBNo = "";//未检出人血
        String checkPSANoStr = "";//检出精斑未检出基因型
        String checkPSANo = "";//未检出精斑
        String otherNo = "";//未检出人DNA
        List<SampleGeneDataModel> sampleGeneDataModelList = new ArrayList<>();
        String geneBarcode = "";
        String noGeneBarcode = "";
        String noYstrGeneBarcode = "";

        String tdpValue = "1-7.47×10";
        String power = "-18";
        String twoConjoined = "0.99977";
        String threeConjoined = "0.9999989";

        List<Map<String, Object>> checkList = new ArrayList<>();
        List<Map<String, Object>> checkHBAndPSANoHasStrList = new ArrayList<>();
        Map<String, Object> checkHBAndPSANoHasStrMap = new LinkedHashMap<String, Object>();
        List<LimsPcrRecord> pcrRecordList = limsPcrRecordService.selectListByCaseId(caseId);
        /**
         * 根据提取人确定鉴定人1
         */
        String appraisalPerson1 = null;
        /**
         * 根据提取人确定鉴定人2
         */
        String appraisalPerson2 = null;
        for(LimsExtractRecord extractRecord:extractRecordList){
            if(StringUtils.isNotBlank(extractRecord.getExtractPersonName1())){
                appraisalPerson1 = extractRecord.getExtractPersonName1();
            }
            if(StringUtils.isNotBlank(extractRecord.getExtractPersonName2())){
                appraisalPerson2 = extractRecord.getExtractPersonName2();
            }
        }

        if (StringUtils.isNotBlank(appraisalPerson1) && appraisalPerson1.length() == 2){
            appraisalPerson1.replace("","  ").trim();
        }
        if (StringUtils.isNotBlank(appraisalPerson2) && appraisalPerson2.length() == 2){
            appraisalPerson2 = appraisalPerson2.replace("","  ").trim();
        }

        if(null != appraisalPerson1 && appraisalPerson1.equals("祝世敬") || appraisalPerson1.equals("毛艳春") || appraisalPerson1.equals("尚煜洁")){
            params.put("appraisalPerson1", "主检法医师    "+appraisalPerson1);
        }else{
            params.put("appraisalPerson1", "法  医  师    "+appraisalPerson1);
        }
        if(null != appraisalPerson2 && appraisalPerson2.equals("祝世敬") || appraisalPerson2.equals("毛艳春") || appraisalPerson2.equals("尚煜洁")){
            params.put("appraisalPerson2", "主检法医师    "+appraisalPerson2);
        }else{
            params.put("appraisalPerson2", "法  医  师    "+appraisalPerson2);
        }

        String reagent = null;

        for (LimsSampleInfo sampleInfo : sampleInfoList) {
            sampleInfo.setSampleName(StringUtils.isBlank(sampleInfo.getSampleName()) ? "" : sampleInfo.getSampleName());
            String sampleNo = StringUtils.isBlank(sampleInfo.getSampleNo()) ? "" : sampleInfo.getSampleNo();
            sampleInfo.setSampleNo(sampleNo);
            sampleInfo.setSamplePacking(StringUtils.isBlank(sampleInfo.getSamplePacking()) ? "" : sampleInfo.getSamplePacking());
            sampleInfo.setSamplePropertiesName(StringUtils.isBlank(sampleInfo.getSamplePropertiesName()) ? "" : sampleInfo.getSamplePropertiesName());
            sampleInfo.setSampleDesc(StringUtils.isBlank(sampleInfo.getSampleDesc()) ? "" : sampleInfo.getSampleDesc());
            sampleInfo.setSampleFlag(StringUtils.isBlank(sampleInfo.getSampleFlag()) ? "" : sampleInfo.getSampleFlag());
            sampleInfo.setSampleTypeName(StringUtils.isBlank(sampleInfo.getSampleTypeName()) ? "" : sampleInfo.getSampleTypeName());

            sampleBarcode += sampleInfo.getSampleNo() + "、";
            sampleType += sampleInfo.getSampleType() + "、";

            if (StringUtils.isNotBlank(sampleInfo.getSampleType())) {
                Integer sampleId = sampleInfo.getId();
                List<LimsExtractRecordSample> extractRecordSampleList = limsExtractRecordService.selectListBySampleId(sampleId);
                if (ListUtils.isNotNullAndEmptyList(extractRecordSampleList)) {
                    LimsExtractRecordSample extractRecordSample = extractRecordSampleList.get(0);
                    LimsSampleGene sampleGene = limsSampleGeneService.selectAuditedBySampleId(sampleId);
                    String geneInfo = null;
                    Map<String, Object> checkMap = new HashMap<>();
                    if (sampleInfo.getSampleType().equals(Constants.DICT_ITEM_SAMPLE_TYPE_BLOOD)) {
                        checkMap = new HashMap<>();

                        if (extractRecordSample.getExtractHb().equals(Constants.DICT_ITEM_SAMPLE_L)) {
                            sampleBarcodeHB += sampleInfo.getSampleNo() + "、";
                            sampleBarcodeHB_L += sampleInfo.getSampleNo() + "、";
                            if (sampleInfo.getSampleFlag().equals(Constants.SAMPLE_FLAG_EVIDENCE)) {
                                checkMap.put("sampleNo", sampleInfo.getSampleNo());
                            }

                            if (sampleGene == null) {
                                checkHBNoStr += sampleInfo.getSampleNo() + "、";//检出人血未检出基因型
                                checkMap.put("checkType", "checkYangHBNoStr");
                            } else {
                                checkMap.put("checkType", "checkYangHBStr");
                            }
                        } else if (extractRecordSample.getExtractHb().equals(Constants.DICT_ITEM_SAMPLE_N)) {
                            sampleBarcodeHB += sampleInfo.getSampleNo() + "、";
                            sampleBarcodeHB_N += sampleInfo.getSampleNo() + "、";
                            if (sampleInfo.getSampleFlag().equals(Constants.SAMPLE_FLAG_EVIDENCE))
                                checkMap.put("sampleNo", sampleInfo.getSampleNo());//未检出人血

                            if (sampleGene == null) {
                                checkHBNo += sampleInfo.getSampleNo() + "、";//未检出人血
                                checkMap.put("checkType", "checkYinHBNoStr");
                            } else {
                                checkMap.put("checkType", "checkYinHBStr");
                            }
                        }

                    } else if (sampleInfo.getSampleType().equals(Constants.DICT_ITEM_SAMPLE_TYPE_SEMINAL)) {
                        checkMap = new HashMap<>();

                        if (extractRecordSample.getExtractPsa().equals(Constants.DICT_ITEM_SAMPLE_L)) {
                            sampleBarcodePSA += sampleInfo.getSampleNo() + "、";
                            sampleBarcodePSA_L += sampleInfo.getSampleNo() + "、";
                            if (sampleInfo.getSampleFlag().equals(Constants.SAMPLE_FLAG_EVIDENCE)) {
                                checkMap.put("sampleNo", sampleInfo.getSampleNo());
                            }

                            if (sampleGene == null) {
                                /** 检出精斑未检出分型*/
                                checkPSANoStr += sampleInfo.getSampleNo() + "、";
                                checkMap.put("checkType", "checkYangPSANoStr");
                            } else {
                                checkMap.put("checkType", "checkYangPSAStr");
                            }
                        } else if (extractRecordSample.getExtractPsa().equals(Constants.DICT_ITEM_SAMPLE_N)) {
                            sampleBarcodePSA += sampleInfo.getSampleNo() + "、";
                            sampleBarcodePSA_N += sampleInfo.getSampleNo() + "、";
                            if (sampleInfo.getSampleFlag().equals(Constants.SAMPLE_FLAG_EVIDENCE)) {
                                /**未检出精斑*/
                                checkMap.put("sampleNo", sampleInfo.getSampleNo());
                            }

                            if (sampleGene == null) {
                                /**未检出精斑*/
                                checkPSANo += sampleInfo.getSampleNo() + "、";
                                checkMap.put("checkType", "checkYinPSANoStr");
                            } else {
                                checkHBAndPSANoHasStrMap = new LinkedHashMap<String, Object>();
                                //未检出人血，检出基因型
                                checkHBAndPSANoHasStrMap.put("sampleNo", sampleInfo.getSampleNo());
                                checkHBAndPSANoHasStrMap.put("sampleType", sampleInfo.getSampleType());
                                if (sampleInfo.getSampleFlag().equals(Constants.SAMPLE_FLAG_PERSON)) {
                                    checkHBAndPSANoHasStrMap.put("personName", sampleInfo.getRefPersonName());
                                } else {
                                    List<CaseInnerMatched> caseInnerMatcheds = caseInnerMatchedService.selectListSampleId(sampleInfo.getId());
                                    String personName = "";
                                    if (ListUtils.isNotNullAndEmptyList(caseInnerMatcheds)) {
                                        LimsSampleInfo lsi = null;
                                        LimsPersonInfo lpi = null;
                                        for (int i = 0; i < caseInnerMatcheds.size(); i++) {
                                            CaseInnerMatched cim = caseInnerMatcheds.get(i);
                                            if (cim.getSample1Id().equals(sampleInfo.getId())) {
                                                lsi = limsSampleInfoService.selectById(cim.getSample2Id());
                                                if (lsi.getRefPersonId() != null && lsi.getSampleFlag().equals(Constants.SAMPLE_FLAG_PERSON)) {
                                                    lpi = limsPersonInfoService.selectById(lsi.getRefPersonId());
                                                    personName += lpi.getPersonName() + "、";
                                                }
                                            }
                                            if (cim.getSample2Id().equals(sampleInfo.getId())) {
                                                lsi = limsSampleInfoService.selectById(cim.getSample1Id());
                                                if (lsi.getRefPersonId() != null && lsi.getSampleFlag().equals(Constants.SAMPLE_FLAG_PERSON)) {
                                                    lpi = limsPersonInfoService.selectById(lsi.getRefPersonId());
                                                    personName += lpi.getPersonName() + "、";
                                                }
                                            }
                                        }
                                        if (StringUtils.isNotBlank(personName)) {
                                            personName = personName.substring(0, personName.length() - 1);
                                        }
                                    }

                                    checkHBAndPSANoHasStrMap.put("personName", personName);
                                }

                                checkHBAndPSANoHasStrList.add(checkHBAndPSANoHasStrMap);
                                checkMap.put("checkType", "checkYinPSAStr");
                            }
                        }

                    } else if (sampleInfo.getSampleType().equals(Constants.DICT_ITEM_SAMPLE_TYPE_SALIVA)
                            || sampleInfo.getSampleType().equals(Constants.DICT_ITEM_SAMPLE_TYPE_TISSUE)
                            || sampleInfo.getSampleType().equals(Constants.DICT_ITEM_SAMPLE_TYPE_HAIR)
                            || sampleInfo.getSampleType().equals(Constants.DICT_ITEM_SAMPLE_TYPE_BUTT)
                            || sampleInfo.getSampleType().equals(Constants.DICT_ITEM_SAMPLE_TYPE_MUSCLE)
                            || sampleInfo.getSampleType().equals(Constants.DICT_ITEM_SAMPLE_TYPE_OTHERS)) {
                        checkMap = new HashMap<>();

                        if (sampleGene == null) {
                            otherNo += sampleInfo.getSampleNo() + "、";
                        } else {
                            checkMap.put("checkType", "checkOtherStr");
                            if (sampleInfo.getSampleFlag().equals(Constants.SAMPLE_FLAG_EVIDENCE)) {
                                checkMap.put("sampleNo", sampleInfo.getSampleNo());
                            }
                        }

                    } else if (sampleInfo.getSampleType().equals(Constants.DICT_ITEM_SAMPLE_TYPE_CASTOFF_CELLS)
                            || sampleInfo.getSampleType().equals(Constants.DICT_ITEM_SAMPLE_TYPE_NAIL)
                            || sampleInfo.getSampleType().equals(Constants.DICT_ITEM_SAMPLE_TYPE_FINGERPRINT)) {
                        checkMap = new HashMap<>();

                        if (sampleGene == null) {
                            otherNo += sampleInfo.getSampleNo() + "、";
                        } else {
                            checkMap.put("checkType", "checkCELLStr");
                            if (sampleInfo.getSampleFlag().equals(Constants.SAMPLE_FLAG_EVIDENCE)) {
                                checkMap.put("sampleNo", sampleInfo.getSampleNo());
                            }
                        }
                    } else if (sampleInfo.getSampleType().equals(Constants.DICT_ITEM_SAMPLE_TYPE_BONES_TEETH)) {
                        checkMap = new HashMap<>();

                        if (sampleGene == null) {
                            otherNo += sampleInfo.getSampleNo() + "、";
                        } else {
                            checkMap.put("checkType", "checkBONEStr");
                            if (sampleInfo.getSampleFlag().equals(Constants.SAMPLE_FLAG_EVIDENCE))
                                checkMap.put("sampleNo", sampleInfo.getSampleNo());
                        }

                    }

                    if (sampleGene != null && checkMap.containsKey("checkType")) {
                        geneInfo = sampleGene.getGeneInfo();

                        if (StringUtils.isNotBlank(geneInfo)) {
                            JSONArray json = JSONArray.fromObject(geneInfo);

                            for (int j = 0; j < json.size(); j++) {
                                JSONObject job = json.getJSONObject(j);

                                String geneName = job.get("geneName") == null ? "" : job.get("geneName").toString();
                                String geneVal2 = job.get("geneVal2") == null ? "" : job.get("geneVal2").toString();
                                String geneVal1 = job.get("geneVal1") == null ? "" : job.get("geneVal1").toString();

                                String geneVal = geneVal1 + geneVal2;
                                if ("AMEL".equals(geneName.toUpperCase())) {
                                    if (geneVal.toUpperCase().equals("XY")) {
                                        checkMap.put("checkGender", "男性");
                                    } else if (geneVal.toUpperCase().equals("XX")) {
                                        checkMap.put("checkGender", "女性");
                                    } else {
                                        checkMap.put("checkGender", "其他");
                                    }
                                    break;
                                }
                            }

                        }
                    } else {
                        if (checkMap.containsKey("checkType")) {
                            checkMap.put("checkGender", "checkGender");
                        }
                    }

                    if (checkMap.size() > 0 && checkMap.containsKey("checkType")) {
                        checkList.add(checkMap);
                    }

                    if (extractRecordSample.getExtractMethod().equals(Constants.DICT_ITEM_EXTRACT_METHOD_A_BLOOD)) {
                        extractMethodJBYX += extractRecordSample.getSampleNo() + "、";
                    } else if (extractRecordSample.getExtractMethod().equals(Constants.DICT_ITEM_EXTRACT_METHOD_B_BONE_TEETH)) {
                        extractMethodGJMXF += extractRecordSample.getSampleNo() + "、";
                    } else if (extractRecordSample.getExtractMethod().equals(Constants.DICT_ITEM_EXTRACT_METHOD_C_AUTOMATIC)) {
                        extractMethodAUTOXF += extractRecordSample.getSampleNo() + "、";
                    } else if (extractRecordSample.getExtractMethod().equals(Constants.DICT_ITEM_EXTRACT_METHOD_D_CELLS)) {
                        extractMethodCellCZ += extractRecordSample.getSampleNo() + "、";
                    } else if (extractRecordSample.getExtractMethod().equals(Constants.DICT_ITEM_EXTRACT_METHOD_E_SEMINAL)) {
                        extractMethodPSACZ += extractRecordSample.getSampleNo() + "、";
                        extractMethodPSACZ_SQ += extractRecordSample.getSampleNo()+ "、";; /*+ "-SQ、";*/
                        extractMethodPSACZ_CD += extractRecordSample.getSampleNo()+ "、";; /*+ "-CD、";*/
                    }
                }
            }

            SampleGeneDataModel sampleGeneDataModel = new SampleGeneDataModel();


            LimsSampleGene limsSampleGene = limsSampleGeneService.selectAuditedBySampleId(sampleInfo.getId());

            if (ListUtils.isNotNullAndEmptyList(pcrRecordList)) {
                for (LimsPcrRecord pcrRecord : pcrRecordList) {
                    if (pcrRecord.getPcrReagent().equals("3")) {
                        if (limsSampleGene != null && limsSampleGene.getPanelId().toString().equals(pcrRecord.getPcrReagent()) ) {
                            sampleGeneDataModel.setBarcode(limsSampleGene.getSampleNo());
                            sampleGeneDataModel.setGeneType(limsSampleGene.getGeneType());

                            geneBarcode += limsSampleGene.getSampleNo() + "、";

                            String geneInfos = limsSampleGene.getGeneInfo();

                            List<GeneInfoModel> geneInfoModelList = new ArrayList<>();
                            List<String> geneList1 = Constants.GENE_LIST1;
                            List<String> geneList2 = Constants.GENE_LIST2;
                            if (StringUtils.isNotBlank(geneInfos)) {
                                JSONArray json = JSONArray.fromObject(geneInfos);

                                if (json.size() > 0) {
                                    for (int i = 0; i < geneList1.size(); i++) {
                                        String geneStr1 = geneList1.get(i).toUpperCase();
                                        GeneInfoModel geneInfoModel = new GeneInfoModel();
                                        for (int j = 0; j < json.size(); j++) {
                                            JSONObject job = json.getJSONObject(j);

                                            String geneName = job.get("geneName") == null ? "" : job.get("geneName").toString();
                                            String geneVal1 = job.get("geneVal1") == null ? "" : job.get("geneVal1").toString().trim();
                                            String geneVal2 = job.get("geneVal2") == null ? "" : job.get("geneVal2").toString().trim();
                                            String geneVal3 = job.get("geneVal3") == null ? "" : job.get("geneVal3").toString().trim();
                                            String geneVal4 = job.get("geneVal4") == null ? "" : job.get("geneVal4").toString().trim();
                                            if (geneName.toUpperCase().equals("THO1") || geneName.toUpperCase().equals("TH01")) {
                                                geneName = "TH01";
                                            }
                                            //纯合子基因只显示一个基因座即可
                                            if (!StringUtils.isBlank(geneVal1)) {
                                                if (!StringUtils.isBlank(geneVal2)) {
                                                    if (geneVal1.equals(geneVal2)) {
                                                        geneVal2 = null;
                                                    }
                                                }
                                            }

                                            if (geneStr1.equals(geneName.toUpperCase())) {
                                                geneInfoModel.setLocusName(geneName);
                                                if (StringUtils.isBlank(geneVal1)) {
                                                    geneInfoModel.setGeneVal1(null);
                                                } else {
                                                    geneInfoModel.setGeneVal1(geneVal1);
                                                }
                                                if (StringUtils.isBlank(geneVal2)) {
                                                    geneInfoModel.setGeneVal2(null);
                                                } else {
                                                    geneInfoModel.setGeneVal2(geneVal2);
                                                }
                                                if (StringUtils.isBlank(geneVal3)) {
                                                    geneInfoModel.setGeneVal3(null);
                                                } else {
                                                    geneInfoModel.setGeneVal3(geneVal3);
                                                }
                                                if (StringUtils.isBlank(geneVal4)) {
                                                    geneInfoModel.setGeneVal4(null);
                                                } else {
                                                    geneInfoModel.setGeneVal4(geneVal4);
                                                }
                                                break;
                                            }
                                        }
                                        geneInfoModelList.add(geneInfoModel);
                                    }

                                    sampleGeneDataModel.setGeneModelList1(geneInfoModelList);

                                    geneInfoModelList = new ArrayList<>();
                                    for (int m = 0; m < geneList2.size(); m++) {
                                        String geneStr2 = geneList2.get(m).toUpperCase();
                                        GeneInfoModel geneInfoModel = new GeneInfoModel();
                                        for (int n = 0; n < json.size(); n++) {
                                            JSONObject job = json.getJSONObject(n);

                                            String geneName = StringUtils.isBlank(job.get("geneName").toString()) ? "" : job.get("geneName").toString();
                                            if (geneName.toUpperCase().equals("THO1") || geneName.toUpperCase().equals("TH01")) {
                                                geneName = "TH01";
                                            }

                                            if (geneStr2.equals(geneName.toUpperCase())) {
                                                geneInfoModel.setLocusName(geneName);

                                                if (StringUtils.isBlank(job.get("geneVal1").toString().trim())) {
                                                    geneInfoModel.setGeneVal1(null);
                                                } else {
                                                    geneInfoModel.setGeneVal1(job.get("geneVal1").toString().trim());
                                                }
                                                if (StringUtils.isBlank(job.get("geneVal2").toString().trim())) {
                                                    geneInfoModel.setGeneVal2(null);
                                                } else {
                                                    geneInfoModel.setGeneVal2(job.get("geneVal2").toString().trim());
                                                }
                                                if (StringUtils.isBlank(job.get("geneVal3").toString().trim())) {
                                                    geneInfoModel.setGeneVal3(null);
                                                } else {
                                                    geneInfoModel.setGeneVal3(job.get("geneVal3").toString().trim());
                                                }
                                                if (StringUtils.isBlank(job.get("geneVal4").toString().trim())) {
                                                    geneInfoModel.setGeneVal4(null);
                                                } else {
                                                    geneInfoModel.setGeneVal4(job.get("geneVal4").toString().trim());
                                                }
                                                if (!StringUtils.isBlank(job.get("geneVal1").toString().trim()) && StringUtils.isBlank(job.get("geneVal3").toString().trim())) {
                                                    if (!StringUtils.isBlank(job.get("geneVal2").toString().trim())) {
                                                        if (job.get("geneVal1").toString().trim().equals(job.get("geneVal2").toString().trim())) {
                                                            geneInfoModel.setGeneVal2(null);
                                                        }
                                                    }
                                                }
                                                break;
                                            }
                                        }

                                        geneInfoModelList.add(geneInfoModel);
                                    }

                                    sampleGeneDataModel.setGeneModelList2(geneInfoModelList);

                                }
                            }
                            sampleGeneDataModelList.add(sampleGeneDataModel);
                        } else {
                            LimsPcrRecordSample pcrRecordSample = limsPcrRecordService.selectListBySampleId(sampleInfo.getId());
                            if (pcrRecordSample != null) {
                                if (pcrRecordSample.getPcrReagent().equals("5")) {
                                    noYstrGeneBarcode += sampleInfo.getSampleNo() + "、";
                                } else {
                                    noGeneBarcode += sampleInfo.getSampleNo() + "、";
                                }
                            }
                        }
                    }
                }
            }

            if (ListUtils.isNotNullAndEmptyList(pcrRecordList)) {
                for (LimsPcrRecord pcrRecord : pcrRecordList) {

                    if (pcrRecord.getPcrReagent().equals("22")) {
                        tdpValue = "1-2.72×10";
                        power = "-28";
                        if (limsSampleGene != null && limsSampleGene.getPanelId().toString().equals(pcrRecord.getPcrReagent())) {
                            sampleGeneDataModel.setBarcode(limsSampleGene.getSampleNo());
                            sampleGeneDataModel.setGeneType(limsSampleGene.getGeneType());
                            geneBarcode += limsSampleGene.getSampleNo() + "、";

                            String geneInfos = limsSampleGene.getGeneInfo();

                            List<GeneInfoModel> geneInfoModelList = new ArrayList<>();
                            List<String> hGgeneList1 = Constants.HENE_LIST1;
                            List<String> hGgeneList2 = Constants.HENE_LIST2;
                            List<String> hGgeneList3 = Constants.HENE_LIST3;

                            if (StringUtils.isNotBlank(geneInfos)) {
                                JSONArray json = JSONArray.fromObject(geneInfos);

                                if (json.size() > 0) {
                                    for (int i = 0; i < hGgeneList1.size(); i++) {
                                        String geneStr1 = hGgeneList1.get(i).toUpperCase();
                                        GeneInfoModel geneInfoModel = new GeneInfoModel();
                                        for (int j = 0; j < json.size(); j++) {
                                            JSONObject job = json.getJSONObject(j);

                                            String geneName = job.get("geneName") == null ? "" : job.get("geneName").toString();
                                            String geneVal1 = job.get("geneVal1") == null ? "" : job.get("geneVal1").toString().trim();
                                            String geneVal2 = job.get("geneVal2") == null ? "" : job.get("geneVal2").toString().trim();
                                            String geneVal3 = job.get("geneVal3") == null ? "" : job.get("geneVal3").toString().trim();
                                            String geneVal4 = job.get("geneVal4") == null ? "" : job.get("geneVal4").toString().trim();
                                            if (geneName.toUpperCase().equals("THO1") || geneName.toUpperCase().equals("TH01")) {
                                                geneName = "TH01";
                                            }
                                            //纯合子基因只显示一个基因座即可
                                            if (!StringUtils.isBlank(geneVal1)) {
                                                if (!StringUtils.isBlank(geneVal2)) {
                                                    if (geneVal1.equals(geneVal2)) {
                                                        geneVal2 = null;
                                                    }
                                                }
                                            }

                                            if (geneStr1.equals(geneName.toUpperCase())) {
                                                geneInfoModel.setLocusName(geneName);
                                                if (StringUtils.isBlank(geneVal1)) {
                                                    geneInfoModel.setGeneVal1(null);
                                                } else {
                                                    geneInfoModel.setGeneVal1(geneVal1);
                                                }
                                                if (StringUtils.isBlank(geneVal2)) {
                                                    geneInfoModel.setGeneVal2(null);
                                                } else {
                                                    geneInfoModel.setGeneVal2(geneVal2);
                                                }
                                                if (StringUtils.isBlank(geneVal3)) {
                                                    geneInfoModel.setGeneVal3(null);
                                                } else {
                                                    geneInfoModel.setGeneVal3(geneVal3);
                                                }
                                                if (StringUtils.isBlank(geneVal4)) {
                                                    geneInfoModel.setGeneVal4(null);
                                                } else {
                                                    geneInfoModel.setGeneVal4(geneVal4);
                                                }
                                                break;
                                            }
                                        }
                                        geneInfoModelList.add(geneInfoModel);
                                    }

                                    sampleGeneDataModel.setGeneModelList1(geneInfoModelList);

                                    geneInfoModelList = new ArrayList<>();
                                    for (int m = 0; m < hGgeneList2.size(); m++) {
                                        String geneStr2 = hGgeneList2.get(m).toUpperCase();
                                        GeneInfoModel geneInfoModel = new GeneInfoModel();
                                        for (int n = 0; n < json.size(); n++) {
                                            JSONObject job = json.getJSONObject(n);

                                            String geneName = StringUtils.isBlank(job.get("geneName").toString()) ? "" : job.get("geneName").toString();
                                            if (geneName.toUpperCase().equals("THO1") || geneName.toUpperCase().equals("TH01")) {
                                                geneName = "TH01";
                                            }

                                            if (geneStr2.equals(geneName.toUpperCase())) {
                                                geneInfoModel.setLocusName(geneName);

                                                if (StringUtils.isBlank(job.get("geneVal1").toString().trim())) {
                                                    geneInfoModel.setGeneVal1(null);
                                                } else {
                                                    geneInfoModel.setGeneVal1(job.get("geneVal1").toString().trim());
                                                }
                                                if (StringUtils.isBlank(job.get("geneVal2").toString().trim())) {
                                                    geneInfoModel.setGeneVal2(null);
                                                } else {
                                                    geneInfoModel.setGeneVal2(job.get("geneVal2").toString().trim());
                                                }
                                                if (StringUtils.isBlank(job.get("geneVal3").toString().trim())) {
                                                    geneInfoModel.setGeneVal3(null);
                                                } else {
                                                    geneInfoModel.setGeneVal3(job.get("geneVal3").toString().trim());
                                                }
                                                if (StringUtils.isBlank(job.get("geneVal4").toString().trim())) {
                                                    geneInfoModel.setGeneVal4(null);
                                                } else {
                                                    geneInfoModel.setGeneVal4(job.get("geneVal4").toString().trim());
                                                }
                                                if (!StringUtils.isBlank(job.get("geneVal1").toString().trim()) && StringUtils.isBlank(job.get("geneVal3").toString().trim())) {
                                                    if (!StringUtils.isBlank(job.get("geneVal2").toString().trim())) {
                                                        if (job.get("geneVal1").toString().trim().equals(job.get("geneVal2").toString().trim())) {
                                                            geneInfoModel.setGeneVal2(null);
                                                        }
                                                    }
                                                }
                                                break;
                                            }
                                        }

                                        geneInfoModelList.add(geneInfoModel);
                                    }

                                    sampleGeneDataModel.setGeneModelList2(geneInfoModelList);

                                    geneInfoModelList = new ArrayList<>();
                                    for (int m = 0; m < hGgeneList3.size(); m++) {
                                        String geneStr2 = hGgeneList3.get(m).toUpperCase();
                                        GeneInfoModel geneInfoModel = new GeneInfoModel();
                                        for (int n = 0; n < json.size(); n++) {
                                            JSONObject job = json.getJSONObject(n);

                                            String geneName = StringUtils.isBlank(job.get("geneName").toString()) ? "" : job.get("geneName").toString();
                                            if (geneName.toUpperCase().equals("THO1") || geneName.toUpperCase().equals("TH01")) {
                                                geneName = "TH01";
                                            }

                                            if (geneStr2.equals(geneName.toUpperCase())) {
                                                geneInfoModel.setLocusName(geneName);

                                                if (StringUtils.isBlank(job.get("geneVal1").toString().trim())) {
                                                    geneInfoModel.setGeneVal1(null);
                                                } else {
                                                    geneInfoModel.setGeneVal1(job.get("geneVal1").toString().trim());
                                                }
                                                if (StringUtils.isBlank(job.get("geneVal2").toString().trim())) {
                                                    geneInfoModel.setGeneVal2(null);
                                                } else {
                                                    geneInfoModel.setGeneVal2(job.get("geneVal2").toString().trim());
                                                }
                                                if (StringUtils.isBlank(job.get("geneVal3").toString().trim())) {
                                                    geneInfoModel.setGeneVal3(null);
                                                } else {
                                                    geneInfoModel.setGeneVal3(job.get("geneVal3").toString().trim());
                                                }
                                                if (StringUtils.isBlank(job.get("geneVal4").toString().trim())) {
                                                    geneInfoModel.setGeneVal4(null);
                                                } else {
                                                    geneInfoModel.setGeneVal4(job.get("geneVal4").toString().trim());
                                                }
                                                if (!StringUtils.isBlank(job.get("geneVal1").toString().trim()) && StringUtils.isBlank(job.get("geneVal3").toString().trim())) {
                                                    if (!StringUtils.isBlank(job.get("geneVal2").toString().trim())) {
                                                        if (job.get("geneVal1").toString().trim().equals(job.get("geneVal2").toString().trim())) {
                                                            geneInfoModel.setGeneVal2(null);
                                                        }
                                                    }
                                                }
                                                break;
                                            }
                                        }

                                        geneInfoModelList.add(geneInfoModel);
                                    }
                                    sampleGeneDataModel.setGeneModelList3(geneInfoModelList);
                                }
                            }
                            sampleGeneDataModelList.add(sampleGeneDataModel);
                        } else {
                            LimsPcrRecordSample pcrRecordSample = limsPcrRecordService.selectListBySampleId(sampleInfo.getId());
                            if (pcrRecordSample != null) {
                                if (pcrRecordSample.getPcrReagent().equals("5")) {
                                    noYstrGeneBarcode += sampleInfo.getSampleNo() + "、";
                                } else {
                                    noGeneBarcode += sampleInfo.getSampleNo() + "、";
                                }
                            }
                        }
                    }
                }
            }


            if (ListUtils.isNotNullAndEmptyList(pcrRecordList)) {
                for (LimsPcrRecord pcrRecord : pcrRecordList) {

                    if (pcrRecord.getPcrReagent().equals("23")) {
                        tdpValue = "1-7.6009×10";
                        power = "-26";
                        twoConjoined = "0.999998";
                        threeConjoined = "0.9999999998";

                        if (limsSampleGene != null && limsSampleGene.getPanelId().toString().equals(pcrRecord.getPcrReagent())) {
                            sampleGeneDataModel.setBarcode(limsSampleGene.getSampleNo());
                            sampleGeneDataModel.setGeneType(limsSampleGene.getGeneType());
                            geneBarcode += limsSampleGene.getSampleNo() + "、";

                            String geneInfos = limsSampleGene.getGeneInfo();

                            List<GeneInfoModel> geneInfoModelList = new ArrayList<>();
                            List<String> hGgeneList1 = Constants.IENE_LIST1;
                            List<String> hGgeneList2 = Constants.IENE_LIST2;
                            List<String> hGgeneList3 = Constants.IENE_LIST3;

                            if (StringUtils.isNotBlank(geneInfos)) {
                                JSONArray json = JSONArray.fromObject(geneInfos);

                                if (json.size() > 0) {
                                    for (int i = 0; i < hGgeneList1.size(); i++) {
                                        String geneStr1 = hGgeneList1.get(i).toUpperCase();
                                        GeneInfoModel geneInfoModel = new GeneInfoModel();
                                        for (int j = 0; j < json.size(); j++) {
                                            JSONObject job = json.getJSONObject(j);

                                            String geneName = job.get("geneName") == null ? "" : job.get("geneName").toString();
                                            String geneVal1 = job.get("geneVal1") == null ? "" : job.get("geneVal1").toString().trim();
                                            String geneVal2 = job.get("geneVal2") == null ? "" : job.get("geneVal2").toString().trim();
                                            String geneVal3 = job.get("geneVal3") == null ? "" : job.get("geneVal3").toString().trim();
                                            String geneVal4 = job.get("geneVal4") == null ? "" : job.get("geneVal4").toString().trim();
                                            if (geneName.toUpperCase().equals("THO1") || geneName.toUpperCase().equals("TH01")) {
                                                geneName = "TH01";
                                            }
                                            //纯合子基因只显示一个基因座即可
                                            if (!StringUtils.isBlank(geneVal1)) {
                                                if (!StringUtils.isBlank(geneVal2)) {
                                                    if (geneVal1.equals(geneVal2)) {
                                                        geneVal2 = null;
                                                    }
                                                }
                                            }

                                            if (geneStr1.equals(geneName.toUpperCase())) {
                                                geneInfoModel.setLocusName(geneName);
                                                if (StringUtils.isBlank(geneVal1)) {
                                                    geneInfoModel.setGeneVal1(null);
                                                } else {
                                                    geneInfoModel.setGeneVal1(geneVal1);
                                                }
                                                if (StringUtils.isBlank(geneVal2)) {
                                                    geneInfoModel.setGeneVal2(null);
                                                } else {
                                                    geneInfoModel.setGeneVal2(geneVal2);
                                                }
                                                if (StringUtils.isBlank(geneVal3)) {
                                                    geneInfoModel.setGeneVal3(null);
                                                } else {
                                                    geneInfoModel.setGeneVal3(geneVal3);
                                                }
                                                if (StringUtils.isBlank(geneVal4)) {
                                                    geneInfoModel.setGeneVal4(null);
                                                } else {
                                                    geneInfoModel.setGeneVal4(geneVal4);
                                                }
                                                break;
                                            }
                                        }
                                        geneInfoModelList.add(geneInfoModel);
                                    }

                                    sampleGeneDataModel.setGeneModelList1(geneInfoModelList);

                                    geneInfoModelList = new ArrayList<>();
                                    for (int m = 0; m < hGgeneList2.size(); m++) {
                                        String geneStr2 = hGgeneList2.get(m).toUpperCase();
                                        GeneInfoModel geneInfoModel = new GeneInfoModel();
                                        for (int n = 0; n < json.size(); n++) {
                                            JSONObject job = json.getJSONObject(n);

                                            String geneName = StringUtils.isBlank(job.get("geneName").toString()) ? "" : job.get("geneName").toString();
                                            if (geneName.toUpperCase().equals("THO1") || geneName.toUpperCase().equals("TH01")) {
                                                geneName = "TH01";
                                            }

                                            if (geneStr2.equals(geneName.toUpperCase())) {
                                                geneInfoModel.setLocusName(geneName);

                                                if (StringUtils.isBlank(job.get("geneVal1").toString().trim())) {
                                                    geneInfoModel.setGeneVal1(null);
                                                } else {
                                                    geneInfoModel.setGeneVal1(job.get("geneVal1").toString().trim());
                                                }
                                                if (StringUtils.isBlank(job.get("geneVal2").toString().trim())) {
                                                    geneInfoModel.setGeneVal2(null);
                                                } else {
                                                    geneInfoModel.setGeneVal2(job.get("geneVal2").toString().trim());
                                                }
                                                if (StringUtils.isBlank(job.get("geneVal3").toString().trim())) {
                                                    geneInfoModel.setGeneVal3(null);
                                                } else {
                                                    geneInfoModel.setGeneVal3(job.get("geneVal3").toString().trim());
                                                }
                                                if (StringUtils.isBlank(job.get("geneVal4").toString().trim())) {
                                                    geneInfoModel.setGeneVal4(null);
                                                } else {
                                                    geneInfoModel.setGeneVal4(job.get("geneVal4").toString().trim());
                                                }
                                                if (!StringUtils.isBlank(job.get("geneVal1").toString().trim()) && StringUtils.isBlank(job.get("geneVal3").toString().trim())) {
                                                    if (!StringUtils.isBlank(job.get("geneVal2").toString().trim())) {
                                                        if (job.get("geneVal1").toString().trim().equals(job.get("geneVal2").toString().trim())) {
                                                            geneInfoModel.setGeneVal2(null);
                                                        }
                                                    }
                                                }
                                                break;
                                            }
                                        }

                                        geneInfoModelList.add(geneInfoModel);
                                    }

                                    sampleGeneDataModel.setGeneModelList2(geneInfoModelList);

                                    geneInfoModelList = new ArrayList<>();
                                    for (int m = 0; m < hGgeneList3.size(); m++) {
                                        String geneStr2 = hGgeneList3.get(m).toUpperCase();
                                        GeneInfoModel geneInfoModel = new GeneInfoModel();
                                        for (int n = 0; n < json.size(); n++) {
                                            JSONObject job = json.getJSONObject(n);

                                            String geneName = StringUtils.isBlank(job.get("geneName").toString()) ? "" : job.get("geneName").toString();
                                            if (geneName.toUpperCase().equals("THO1") || geneName.toUpperCase().equals("TH01")) {
                                                geneName = "TH01";
                                            }

                                            if (geneStr2.equals(geneName.toUpperCase())) {
                                                geneInfoModel.setLocusName(geneName);

                                                if (StringUtils.isBlank(job.get("geneVal1").toString().trim())) {
                                                    geneInfoModel.setGeneVal1(null);
                                                } else {
                                                    geneInfoModel.setGeneVal1(job.get("geneVal1").toString().trim());
                                                }
                                                if (StringUtils.isBlank(job.get("geneVal2").toString().trim())) {
                                                    geneInfoModel.setGeneVal2(null);
                                                } else {
                                                    geneInfoModel.setGeneVal2(job.get("geneVal2").toString().trim());
                                                }
                                                if (StringUtils.isBlank(job.get("geneVal3").toString().trim())) {
                                                    geneInfoModel.setGeneVal3(null);
                                                } else {
                                                    geneInfoModel.setGeneVal3(job.get("geneVal3").toString().trim());
                                                }
                                                if (StringUtils.isBlank(job.get("geneVal4").toString().trim())) {
                                                    geneInfoModel.setGeneVal4(null);
                                                } else {
                                                    geneInfoModel.setGeneVal4(job.get("geneVal4").toString().trim());
                                                }
                                                if (!StringUtils.isBlank(job.get("geneVal1").toString().trim()) && StringUtils.isBlank(job.get("geneVal3").toString().trim())) {
                                                    if (!StringUtils.isBlank(job.get("geneVal2").toString().trim())) {
                                                        if (job.get("geneVal1").toString().trim().equals(job.get("geneVal2").toString().trim())) {
                                                            geneInfoModel.setGeneVal2(null);
                                                        }
                                                    }
                                                }
                                                break;
                                            }
                                        }

                                        geneInfoModelList.add(geneInfoModel);
                                    }
                                    sampleGeneDataModel.setGeneModelList3(geneInfoModelList);
                                }
                            }
                            sampleGeneDataModelList.add(sampleGeneDataModel);
                        } else {
                            LimsPcrRecordSample pcrRecordSample = limsPcrRecordService.selectListBySampleId(sampleInfo.getId());
                            if (pcrRecordSample != null) {
                                if (pcrRecordSample.getPcrReagent().equals("5")) {
                                    noYstrGeneBarcode += sampleInfo.getSampleNo() + "、";
                                } else {
                                    noGeneBarcode += sampleInfo.getSampleNo() + "、";
                                }
                            }
                        }
                    }
                }
            }

        }

        List<LimsSampleInfo> sampleInfos = limsSampleInfoService.selectAcceptStatusListByCaseId(caseId);

        List<CaseInnerMatchedModel> caseInnerMatchedModelHBList = new ArrayList<>();
        List<CaseInnerMatchedModel> caseInnerMatchedModelPSAList = new ArrayList<>();
        List<CaseInnerMatchedModel> caseInnerMatchedModelOtherList = new ArrayList<>();
        List<CaseInnerMatchedModel> caseInnerMatchedModelList = new ArrayList<>();
        if (ListUtils.isNotNullAndEmptyList(listCon)) {
            LimsConsignment limsConsignment = listCon.get(0);

            if (StringUtils.isNotBlank(limsConsignment.getMatchCaseNo())) {

                List<LimsSampleInfo> limsSampleInfoList = limsSampleInfoService.selectListByCaseNo(limsConsignment.getMatchCaseNo().trim());

                if (ListUtils.isNotNullAndEmptyList(limsSampleInfoList)) {
                    for (LimsSampleInfo s : limsSampleInfoList) {
                        sampleInfos.add(s);
                    }
                }
            }

            for (LimsSampleInfo sampleInfo : sampleInfos) {
                String sampleObjectBarcode = "";
                String sampleObjectName = "";
                String samplePersonBarcode = "";
                String sampleObjectBarcodePSA = "";
                String sampleObjectNamePSA = "";
                String sampleObjectBarcodeHB = "";
                String sampleObjectNameHB = "";
                String sampleObjectBarcodeOther = "";
                String sampleObjectNameOther = "";
                Integer sampleObjectId = 0;
                if (StringUtils.isNotBlank(geneBarcode) && ListUtils.isNotNullAndEmptyList(caseInnerMatchedList)) {
                    if (StringUtils.isNotBlank(sampleInfo.getSampleFlag())) {
                        if (sampleInfo.getSampleFlag().equals("1")) {
                            CaseInnerMatchedModel caseInnerMatchedModel = new CaseInnerMatchedModel();
                            for (int i = 0; i < caseInnerMatchedList.size(); i++) {
                                CaseInnerMatched caseInnerMatched = caseInnerMatchedList.get(i);
                                if (!sampleInfo.getId().equals(0) && !caseInnerMatched.getSample1Id().equals(0) && !caseInnerMatched.getSample2Id().equals(0)) {
                                    if (sampleInfo.getId().equals(caseInnerMatched.getSample1Id())) {
                                        sampleObjectId = caseInnerMatched.getSample2Id();
                                        List<LimsSampleInfo> limsSampleInfoList = limsSampleInfoService.selectListBySampleId(sampleObjectId);
                                        if (ListUtils.isNotNullAndEmptyList(limsSampleInfoList)) {
                                            sampleObjectBarcode += limsSampleInfoList.get(0).getSampleNo() + "、";
                                            sampleObjectName += limsSampleInfoList.get(0).getSampleName() + "、";
                                            String sampleInfoType = limsSampleInfoList.get(0).getSampleType();
                                            if (StringUtils.isNotBlank(sampleInfoType) && sampleInfoType.equals(Constants.DICT_ITEM_SAMPLE_TYPE_SEMINAL)) {
                                                sampleObjectBarcodePSA += limsSampleInfoList.get(0).getSampleNo() + "、";
                                                sampleObjectNamePSA += limsSampleInfoList.get(0).getSampleName() + "、";
                                            } else if (StringUtils.isNotBlank(sampleInfoType) && (sampleInfoType.equals(Constants.DICT_ITEM_SAMPLE_TYPE_BLOOD)
                                                    || sampleInfoType.equals(Constants.DICT_ITEM_SAMPLE_TYPE_BUTT)
                                                    || sampleInfoType.equals(Constants.DICT_ITEM_SAMPLE_TYPE_HAIR)
                                                    || sampleInfoType.equals(Constants.DICT_ITEM_SAMPLE_TYPE_TISSUE)
                                                    || sampleInfoType.equals(Constants.DICT_ITEM_SAMPLE_TYPE_SALIVA)
                                                    || sampleInfoType.equals(Constants.DICT_ITEM_SAMPLE_TYPE_MUSCLE))) {
                                                sampleObjectBarcodeHB += limsSampleInfoList.get(0).getSampleNo() + "、";
                                                sampleObjectNameHB += limsSampleInfoList.get(0).getSampleName() + "、";
                                            } else {
                                                sampleObjectBarcodeOther += limsSampleInfoList.get(0).getSampleNo() + "、";
                                                sampleObjectNameOther += limsSampleInfoList.get(0).getSampleName() + "、";
                                            }
                                        }
                                        String totalProbability = caseInnerMatched.getTotalProbability() == null ? "" : caseInnerMatched.getTotalProbability().toString();
                                        if (StringUtils.isNotBlank(totalProbability)) {
                                            caseInnerMatchedModel.setTotalProbability1(totalProbability.split("E")[0]);
                                            caseInnerMatchedModel.setTotalProbability2(totalProbability.split("E")[1]);
                                        } else {
                                            caseInnerMatchedModel.setTotalProbability1(null);
                                            caseInnerMatchedModel.setTotalProbability2(null);
                                        }

                                        /**基因座相同个数 (Investigator 24plex试剂盒固定21个)*/
                                        if(StringUtils.isNotBlank(caseInnerMatched.getSameCount().toString())){
                                            caseInnerMatchedModel.setSameCount(caseInnerMatched.getSameCount()-1);
                                        }
                                        if (ListUtils.isNotNullAndEmptyList(pcrRecordList)) {
                                            for (LimsPcrRecord pcrRecord : pcrRecordList) {
                                                if (pcrRecord.getPcrReagent().equals("23")) {
                                                    caseInnerMatchedModel.setSameCount(21);
                                                }
                                            }
                                        }


                                    }
                                    samplePersonBarcode = sampleInfo.getSampleNo();
                                }
                            }

                            caseInnerMatchedModel.setSamplePersonNo(samplePersonBarcode);
                            caseInnerMatchedModel.setSamplePersonName(sampleInfo.getSampleName());
                            if (StringUtils.isNotBlank(sampleObjectBarcode) && StringUtils.isNotBlank(sampleObjectName)) {
                                caseInnerMatchedModel.setSampleObjectNo(sampleObjectBarcode.substring(0, sampleObjectBarcode.length() - 1));
                                caseInnerMatchedModel.setSampleObjectName(sampleObjectName.substring(0, sampleObjectName.length() - 1));

                                caseInnerMatchedModelList.add(caseInnerMatchedModel);
                            }

                            if (StringUtils.isNotBlank(sampleObjectBarcodePSA) && StringUtils.isNotBlank(sampleObjectNamePSA)) {
                                caseInnerMatchedModel.setSampleObjectNoPSA(sampleObjectBarcodePSA.substring(0, sampleObjectBarcodePSA.length() - 1));
                                caseInnerMatchedModel.setSampleObjectNamePSA(sampleObjectNamePSA.substring(0, sampleObjectNamePSA.length() - 1));

                                caseInnerMatchedModelPSAList.add(caseInnerMatchedModel);
                            }

                            if (StringUtils.isNotBlank(sampleObjectBarcodeHB) && StringUtils.isNotBlank(sampleObjectNameHB)) {
                                caseInnerMatchedModel.setSampleObjectNoHB(sampleObjectBarcodeHB.substring(0, sampleObjectBarcodeHB.length() - 1));
                                caseInnerMatchedModel.setSampleObjectNameHB(sampleObjectNameHB.substring(0, sampleObjectNameHB.length() - 1));

                                caseInnerMatchedModelHBList.add(caseInnerMatchedModel);
                            }

                            if (StringUtils.isNotBlank(sampleObjectBarcodeOther) && StringUtils.isNotBlank(sampleObjectNameOther)) {
                                caseInnerMatchedModel.setSampleObjectNoOther(sampleObjectBarcodeOther.substring(0, sampleObjectBarcodeOther.length() - 1));
                                caseInnerMatchedModel.setSampleObjectNameOther(sampleObjectNameOther.substring(0, sampleObjectNameOther.length() - 1));

                                caseInnerMatchedModelOtherList.add(caseInnerMatchedModel);
                            }

                        }
                    }
                }
            }
        }

        params.put("tdpValue",tdpValue);
        params.put("power",power);

        params.put("twoConjoined",twoConjoined);
        params.put("threeConjoined",threeConjoined);
        if (StringUtils.isNotBlank(sampleBarcodeHB)) {
            params.put("sampleBarcodeHB", sampleBarcodeHB.substring(0, sampleBarcodeHB.length() - 1));
        } else {
            params.put("sampleBarcodeHB", null);
        }

        if (StringUtils.isNotBlank(sampleBarcodeHB_L)) {
            params.put("sampleBarcodeHB_L", sampleBarcodeHB_L.substring(0, sampleBarcodeHB_L.length() - 1));
        } else {
            params.put("sampleBarcodeHB_L", null);
        }

        if (StringUtils.isNotBlank(sampleBarcodeHB_N)) {
            params.put("sampleBarcodeHB_N", sampleBarcodeHB_N.substring(0, sampleBarcodeHB_N.length() - 1));
        } else {
            params.put("sampleBarcodeHB_N", null);
        }

        if (StringUtils.isNotBlank(sampleBarcodePSA)) {
            params.put("sampleBarcodePSA", sampleBarcodePSA.substring(0, sampleBarcodePSA.length() - 1));
        } else {
            params.put("sampleBarcodePSA", null);
        }

        if (StringUtils.isNotBlank(sampleBarcodePSA_L)) {
            params.put("sampleBarcodePSA_L", sampleBarcodePSA_L.substring(0, sampleBarcodePSA_L.length() - 1));
        } else {
            params.put("sampleBarcodePSA_L", null);
        }

        if (StringUtils.isNotBlank(sampleBarcodePSA_N)) {
            params.put("sampleBarcodePSA_N", sampleBarcodePSA_N.substring(0, sampleBarcodePSA_N.length() - 1));
        } else {
            params.put("sampleBarcodePSA_N", null);
        }

        if (StringUtils.isNotBlank(extractMethodCellCZ)) {
            params.put("extractMethodCellCZ", extractMethodCellCZ.substring(0, extractMethodCellCZ.length() - 1));
        } else {
            params.put("extractMethodCellCZ", null);
        }
        if (StringUtils.isNotBlank(extractMethodPSACZ)) {
            params.put("extractMethodPSACZ", extractMethodPSACZ.substring(0, extractMethodPSACZ.length() - 1));
        } else {
            params.put("extractMethodPSACZ", null);
        }

        if (StringUtils.isNotBlank(extractMethodPSACZ_SQ)) {
            params.put("extractMethodPSACZ_SQ", extractMethodPSACZ_SQ.substring(0, extractMethodPSACZ_SQ.length() - 1));
        } else {
            params.put("extractMethodPSACZ_SQ", null);
        }
        if (StringUtils.isNotBlank(extractMethodPSACZ_CD)) {
            params.put("extractMethodPSACZ_CD", extractMethodPSACZ_CD.substring(0, extractMethodPSACZ_CD.length() - 1));
        } else {
            params.put("extractMethodPSACZ_CD", null);
        }

        if (StringUtils.isNotBlank(extractMethodJBYX)) {
            params.put("extractMethodJBYX", extractMethodJBYX.substring(0, extractMethodJBYX.length() - 1));
        } else {
            params.put("extractMethodJBYX", null);
        }
        if (StringUtils.isNotBlank(extractMethodGJMXF)) {
            params.put("extractMethodGJMXF", extractMethodGJMXF.substring(0, extractMethodGJMXF.length() - 1));
        } else {
            params.put("extractMethodGJMXF", null);
        }
        if (StringUtils.isNotBlank(extractMethodAUTOXF)) {
            params.put("extractMethodAUTOXF", extractMethodAUTOXF.substring(0, extractMethodAUTOXF.length() - 1));
        } else {
            params.put("extractMethodAUTOXF", null);
        }

        if (sampleType.contains(Constants.DICT_ITEM_SAMPLE_TYPE_BLOOD)) {
            params.put("HB", "GA765-2008《人血红蛋白检测金标试剂条法》和");
        } else {
            params.put("HB", "");
        }
        if (sampleType.contains(Constants.DICT_ITEM_SAMPLE_TYPE_SEMINAL)) {
            params.put("PSA", "GA766-2008《人精液PSA检测金标试剂条法》和");
        } else {
            params.put("PSA", "");
        }

        sampleInfoList = getSampleInfoList(sampleInfoList);

        /*List<LimsSampleInfo> sampleList = new ArrayList<>();
        for(LimsSampleInfo sampleInfo:sampleInfoList){
            if (!sampleInfo.getSampleName().contains("CD") || !sampleInfo.getSampleName().contains("SQ")){
                sampleList.add(sampleInfo);
            }
        }*/

        params.put("sampleList", sampleInfoList);
        //params.put("sampleList",sampleList);
        /*for(LimsConsignment co : listCon){
            if(co.getAdditionalFlag().equals(Constants.FLAG_FALSE)){
                List<LimsSampleInfo> limsSampleInfoList = limsSampleInfoService.selectAcceptStatusListByConsignmentId(co.getId());
                limsSampleInfoList = getSampleInfoList(limsSampleInfoList);
                params.put("sampleList", limsSampleInfoList);
            }else {
                List<LimsSampleInfo> acceptSampleInfoList = limsSampleInfoService.selectAcceptStatusListByConsignmentId(co.getId());
                acceptSampleInfoList = getSampleInfoList(acceptSampleInfoList);
                params.put("acceptSampleList", acceptSampleInfoList);
            }
        }*/


        Map<String, Object> checkMap;
        String sampleManYangNoStrHBNo = "";
        String sampleManYangStrHBNo = "";
        String sampleManYinNoStrHBNo = "";
        String sampleManYinStrHBNo = "";
        String sampleManYangNoStrPSANo = "";
        String sampleManYangStrPSANo = "";
        String sampleManYinNoStrPSANo = "";
        String sampleManYinStrPSANo = "";
        String sampleManOtherNo = "";
        String sampleWomanYangNoStrHBNo = "";
        String sampleWomanYangStrHBNo = "";
        String sampleWomanYinNoStrHBNo = "";
        String sampleWomanYinStrHBNo = "";
        String sampleWomanYangNoStrPSANo = "";
        String sampleWomanYangStrPSANo = "";
        String sampleWomanYinNoStrPSANo = "";
        String sampleWomanYinStrPSANo = "";
        String sampleWomanOtherNo = "";
        String sampleOtherYangNoStrHBNo = "";
        String sampleOtherYangStrHBNo = "";
        String sampleOtherYinNoStrHBNo = "";
        String sampleOtherYinStrHBNo = "";
        String sampleOtherYangNoStrPSANo = "";
        String sampleOtherYangStrPSANo = "";
        String sampleOtherYinNoStrPSANo = "";
        String sampleOtherYinStrPSANo = "";
        String sampleOtherNo = "";
        String checkManGender = null;
        String checkWomanGender = null;
        String checkOtherGender = null;
        for (int i = 0; i < checkList.size(); i++) {
            checkMap = checkList.get(i);
            String checkType = null;
            String checkGender = null;
            String sampleNo = null;
            if (checkMap.size() > 0) {

                if (checkMap.containsKey("checkType")) {
                    checkType = checkMap.get("checkType").toString();
                }
                if (checkMap.containsKey("checkGender")) {
                    checkGender = checkMap.get("checkGender").toString();
                }
                if (checkMap.containsKey("sampleNo")) {
                    sampleNo = checkMap.get("sampleNo").toString();
                }

                if (StringUtils.isNotBlank(checkType) && StringUtils.isNotBlank(checkGender) && StringUtils.isNotBlank(sampleNo)) {
                    if (checkType.equals("checkYangHBNoStr")) {
                        if (checkGender.equals("男性")) {
                            sampleManYangNoStrHBNo += sampleNo + "、";
                            checkManGender = checkGender;
                        } else if (checkGender.equals("女性")) {
                            sampleWomanYangNoStrHBNo += sampleNo + "、";
                            checkWomanGender = checkGender;
                        } else {
                            sampleOtherYangNoStrHBNo += sampleNo + "、";
                            checkOtherGender = checkGender;
                        }
                    } else if (checkType.equals("checkYangHBStr")) {
                        if (checkGender.equals("男性")) {
                            sampleManYangStrHBNo += sampleNo + "、";
                            checkManGender = checkGender;
                        } else if (checkGender.equals("女性")) {
                            sampleWomanYangStrHBNo += sampleNo + "、";
                            checkWomanGender = checkGender;
                        } else {
                            sampleOtherYangStrHBNo += sampleNo + "、";
                            checkOtherGender = checkGender;
                        }
                    } else if (checkType.equals("checkYinHBNoStr")) {
                        if (checkGender.equals("男性")) {
                            sampleManYinNoStrHBNo += sampleNo + "、";
                            checkManGender = checkGender;
                        } else if (checkGender.equals("女性")) {
                            sampleWomanYinNoStrHBNo += sampleNo + "、";
                            checkWomanGender = checkGender;
                        } else {
                            sampleOtherYinNoStrHBNo += sampleNo + "、";
                            checkOtherGender = checkGender;
                        }
                    } else if (checkType.equals("checkYinHBStr")) {
                        if (checkGender.equals("男性")) {
                            sampleManYinStrHBNo += sampleNo + "、";
                            checkManGender = checkGender;
                        } else if (checkGender.equals("女性")) {
                            sampleWomanYinStrHBNo += sampleNo + "、";
                            checkWomanGender = checkGender;
                        } else {
                            sampleOtherYinStrHBNo += sampleNo + "、";
                            checkOtherGender = checkGender;
                        }
                    } else if (checkType.equals("checkYangPSANoStr")) {
                        if (checkGender.equals("男性")) {
                            sampleManYangNoStrPSANo += sampleNo + "、";
                            checkManGender = checkGender;
                        } else if (checkGender.equals("女性")) {
                            sampleWomanYangNoStrPSANo += sampleNo + "、";
                            checkWomanGender = checkGender;
                        } else {
                            sampleOtherYangNoStrPSANo += sampleNo + "、";
                            checkOtherGender = checkGender;
                        }
                    } else if (checkType.equals("checkYangPSAStr")) {
                        if (checkGender.equals("男性")) {
                            sampleManYangStrPSANo += sampleNo + "、";
                            checkManGender = checkGender;
                        } else if (checkGender.equals("女性")) {
                            sampleWomanYangStrPSANo += sampleNo + "、";
                            checkWomanGender = checkGender;
                        } else {
                            sampleOtherYangStrPSANo += sampleNo + "、";
                            checkOtherGender = checkGender;
                        }
                    } else if (checkType.equals("checkYinPSANoStr")) {
                        if (checkGender.equals("男性")) {
                            sampleManYinNoStrPSANo += sampleNo + "、";
                            checkManGender = checkGender;
                        } else if (checkGender.equals("女性")) {
                            sampleWomanYinNoStrPSANo += sampleNo + "、";
                            checkWomanGender = checkGender;
                        } else {
                            sampleOtherYinNoStrPSANo += sampleNo + "、";
                            checkOtherGender = checkGender;
                        }
                    } else if (checkType.equals("checkYinPSAStr")) {
                        if (checkGender.equals("男性")) {
                            sampleManYinStrPSANo += sampleNo + "、";
                            checkManGender = checkGender;
                        } else if (checkGender.equals("女性")) {
                            sampleWomanYinStrPSANo += sampleNo + "、";
                            checkWomanGender = checkGender;
                        } else {
                            sampleOtherYinStrPSANo += sampleNo + "、";
                            checkOtherGender = checkGender;
                        }
                    } else {
                        if (checkGender.equals("男性")) {
                            sampleManOtherNo += sampleNo + "、";
                            checkManGender = checkGender;
                        } else if (checkGender.equals("女性")) {
                            sampleWomanOtherNo += sampleNo + "、";
                            checkWomanGender = checkGender;
                        } else {
                            sampleOtherNo += sampleNo + "、";
                            checkOtherGender = checkGender;
                        }
                    }
                }
            }
        }


        List<Map<String, Object>> checkResultList = new ArrayList<>();
        Map<String, Object> resultMap;
        if (StringUtils.isNotBlank(sampleManYangNoStrHBNo)) {
            resultMap = new HashMap<>();

            resultMap.put("sampleManNo", sampleManYangNoStrHBNo.substring(0, sampleManYangNoStrHBNo.length() - 1));
            resultMap.put("checkType", "checkYangHBNoStr");
            resultMap.put("checkGender", checkManGender);

            checkResultList.add(resultMap);
        }
        if (StringUtils.isNotBlank(sampleManYangStrHBNo)) {
            resultMap = new HashMap<>();

            resultMap.put("sampleManNo", sampleManYangStrHBNo.substring(0, sampleManYangStrHBNo.length() - 1));
            resultMap.put("checkType", "checkYangHBStr");
            resultMap.put("checkGender", checkManGender);

            checkResultList.add(resultMap);
        }
        if (StringUtils.isNotBlank(sampleManYinNoStrHBNo)) {
            resultMap = new HashMap<>();

            resultMap.put("sampleManNo", sampleManYinNoStrHBNo.substring(0, sampleManYinNoStrHBNo.length() - 1));
            resultMap.put("checkType", "checkYinHBNoStr");
            resultMap.put("checkGender", checkManGender);

            checkResultList.add(resultMap);
        }
        if (StringUtils.isNotBlank(sampleManYinStrHBNo)) {
            resultMap = new HashMap<>();

            resultMap.put("sampleManNo", sampleManYinStrHBNo.substring(0, sampleManYinStrHBNo.length() - 1));
            resultMap.put("checkType", "checkYinHBStr");
            resultMap.put("checkGender", checkManGender);

            checkResultList.add(resultMap);
        }
        if (StringUtils.isNotBlank(sampleManYangNoStrPSANo)) {
            resultMap = new HashMap<>();

            resultMap.put("sampleManNo", sampleManYangNoStrPSANo.substring(0, sampleManYangNoStrPSANo.length() - 1));
            resultMap.put("checkType", "checkYangPSANoStr");
            resultMap.put("checkGender", checkManGender);

            checkResultList.add(resultMap);
        }
        if (StringUtils.isNotBlank(sampleManYangStrPSANo)) {
            resultMap = new HashMap<>();

            resultMap.put("sampleManNo", sampleManYangStrPSANo.substring(0, sampleManYangStrPSANo.length() - 1));
            resultMap.put("checkType", "checkYangPSAStr");
            resultMap.put("checkGender", checkManGender);

            checkResultList.add(resultMap);
        }
        if (StringUtils.isNotBlank(sampleManYinNoStrPSANo)) {
            resultMap = new HashMap<>();

            resultMap.put("sampleManNo", sampleManYinNoStrPSANo.substring(0, sampleManYinNoStrPSANo.length() - 1));
            resultMap.put("checkType", "checkYinPSANoStr");
            resultMap.put("checkGender", checkManGender);

            checkResultList.add(resultMap);
        }
        if (StringUtils.isNotBlank(sampleManYinStrPSANo)) {
            resultMap = new HashMap<>();

            resultMap.put("sampleManNo", sampleManYinStrPSANo.substring(0, sampleManYinStrPSANo.length() - 1));
            resultMap.put("checkType", "checkYinPSAStr");
            resultMap.put("checkGender", checkManGender);

            checkResultList.add(resultMap);
        }
        if (StringUtils.isNotBlank(sampleManOtherNo)) {
            resultMap = new HashMap<>();

            resultMap.put("sampleOtherNo", sampleManOtherNo.substring(0, sampleManOtherNo.length() - 1));
            resultMap.put("checkType", "checkOtherStr");
            resultMap.put("checkGender", checkManGender);

            checkResultList.add(resultMap);
        }
        if (StringUtils.isNotBlank(sampleWomanYangNoStrHBNo)) {
            resultMap = new HashMap<>();

            resultMap.put("sampleWomanNo", sampleWomanYangNoStrHBNo.substring(0, sampleWomanYangNoStrHBNo.length() - 1));
            resultMap.put("checkType", "checkYangHBNoStr");
            resultMap.put("checkGender", checkWomanGender);

            checkResultList.add(resultMap);
        }
        if (StringUtils.isNotBlank(sampleWomanYangStrHBNo)) {
            resultMap = new HashMap<>();

            resultMap.put("sampleWomanNo", sampleWomanYangStrHBNo.substring(0, sampleWomanYangStrHBNo.length() - 1));
            resultMap.put("checkType", "checkYangHBStr");
            resultMap.put("checkGender", checkWomanGender);

            checkResultList.add(resultMap);
        }
        if (StringUtils.isNotBlank(sampleWomanYinNoStrHBNo)) {
            resultMap = new HashMap<>();

            resultMap.put("sampleWomanNo", sampleWomanYinNoStrHBNo.substring(0, sampleWomanYinNoStrHBNo.length() - 1));
            resultMap.put("checkType", "checkYinHBNoStr");
            resultMap.put("checkGender", checkWomanGender);

            checkResultList.add(resultMap);
        }
        if (StringUtils.isNotBlank(sampleWomanYinStrHBNo)) {
            resultMap = new HashMap<>();

            resultMap.put("sampleWomanNo", sampleWomanYinStrHBNo.substring(0, sampleWomanYinStrHBNo.length() - 1));
            resultMap.put("checkType", "checkYinHBStr");
            resultMap.put("checkGender", checkWomanGender);

            checkResultList.add(resultMap);
        }
        if (StringUtils.isNotBlank(sampleWomanYangNoStrPSANo)) {
            resultMap = new HashMap<>();

            resultMap.put("sampleWomanNo", sampleWomanYangNoStrPSANo.substring(0, sampleWomanYangNoStrPSANo.length() - 1));
            resultMap.put("checkType", "checkYangPSANoStr");
            resultMap.put("checkGender", checkWomanGender);

            checkResultList.add(resultMap);
        }
        if (StringUtils.isNotBlank(sampleWomanYangStrPSANo)) {
            resultMap = new HashMap<>();

            resultMap.put("sampleWomanNo", sampleWomanYangStrPSANo.substring(0, sampleWomanYangStrPSANo.length() - 1));
            resultMap.put("checkType", "checkYangPSAStr");
            resultMap.put("checkGender", checkWomanGender);

            checkResultList.add(resultMap);
        }
        if (StringUtils.isNotBlank(sampleWomanYinNoStrPSANo)) {
            resultMap = new HashMap<>();

            resultMap.put("sampleWomanNo", sampleWomanYinNoStrPSANo.substring(0, sampleWomanYinNoStrPSANo.length() - 1));
            resultMap.put("checkType", "checkYinPSANoStr");
            resultMap.put("checkGender", checkWomanGender);

            checkResultList.add(resultMap);
        }
        if (StringUtils.isNotBlank(sampleWomanYinStrPSANo)) {
            resultMap = new HashMap<>();

            resultMap.put("sampleWomanNo", sampleWomanYinStrPSANo.substring(0, sampleWomanYinStrPSANo.length() - 1));
            resultMap.put("checkType", "checkYinPSAStr");
            resultMap.put("checkGender", checkWomanGender);

            checkResultList.add(resultMap);
        }
        if (StringUtils.isNotBlank(sampleWomanOtherNo)) {
            resultMap = new HashMap<>();

            resultMap.put("sampleOtherNo", sampleWomanOtherNo.substring(0, sampleWomanOtherNo.length() - 1));
            resultMap.put("checkType", "checkOtherStr");
            resultMap.put("checkGender", checkWomanGender);

            checkResultList.add(resultMap);
        }
        if (StringUtils.isNotBlank(sampleOtherYangNoStrHBNo)) {
            resultMap = new HashMap<>();

            resultMap.put("sampleOtherNo", sampleOtherYangNoStrHBNo.substring(0, sampleOtherYangNoStrHBNo.length() - 1));
            resultMap.put("checkType", "checkYangHBNoStr");
            resultMap.put("checkGender", "其他");

            checkResultList.add(resultMap);
        }
        if (StringUtils.isNotBlank(sampleOtherYangStrHBNo)) {
            resultMap = new HashMap<>();

            resultMap.put("sampleOtherNo", sampleOtherYangStrHBNo.substring(0, sampleOtherYangStrHBNo.length() - 1));
            resultMap.put("checkType", "checkYangHBStr");
            resultMap.put("checkGender", "其他");

            checkResultList.add(resultMap);
        }
        if (StringUtils.isNotBlank(sampleOtherYinNoStrHBNo)) {
            resultMap = new HashMap<>();

            resultMap.put("sampleOtherNo", sampleOtherYinNoStrHBNo.substring(0, sampleOtherYinNoStrHBNo.length() - 1));
            resultMap.put("checkType", "checkYinHBNoStr");
            resultMap.put("checkGender", "其他");

            checkResultList.add(resultMap);
        }
        if (StringUtils.isNotBlank(sampleOtherYinStrHBNo)) {
            resultMap = new HashMap<>();

            resultMap.put("sampleOtherNo", sampleOtherYinStrHBNo.substring(0, sampleOtherYinStrHBNo.length() - 1));
            resultMap.put("checkType", "checkYinHBStr");
            resultMap.put("checkGender", "其他");

            checkResultList.add(resultMap);
        }
        if (StringUtils.isNotBlank(sampleOtherYangNoStrPSANo)) {
            resultMap = new HashMap<>();

            resultMap.put("sampleOtherNo", sampleOtherYangNoStrPSANo.substring(0, sampleOtherYangNoStrPSANo.length() - 1));
            resultMap.put("checkType", "checkYangPSANoStr");
            resultMap.put("checkGender", "其他");

            checkResultList.add(resultMap);
        }
        if (StringUtils.isNotBlank(sampleOtherYangStrPSANo)) {
            resultMap = new HashMap<>();

            resultMap.put("sampleOtherNo", sampleOtherYangStrPSANo.substring(0, sampleOtherYangStrPSANo.length() - 1));
            resultMap.put("checkType", "checkYangPSAStr");
            resultMap.put("checkGender", "其他");

            checkResultList.add(resultMap);
        }
        if (StringUtils.isNotBlank(sampleOtherYinNoStrPSANo)) {
            resultMap = new HashMap<>();

            resultMap.put("sampleOtherNo", sampleOtherYinNoStrPSANo.substring(0, sampleOtherYinNoStrPSANo.length() - 1));
            resultMap.put("checkType", "checkYinPSANoStr");
            resultMap.put("checkGender", "其他");

            checkResultList.add(resultMap);
        }
        if (StringUtils.isNotBlank(sampleOtherYinStrPSANo)) {
            resultMap = new HashMap<>();

            resultMap.put("sampleOtherNo", sampleOtherYinStrPSANo.substring(0, sampleOtherYinStrPSANo.length() - 1));
            resultMap.put("checkType", "checkYinPSAStr");
            resultMap.put("checkGender", "其他");

            checkResultList.add(resultMap);
        }
        if (StringUtils.isNotBlank(sampleOtherNo)) {
            resultMap = new HashMap<>();

            resultMap.put("sampleOtherNo", sampleOtherNo.substring(0, sampleOtherNo.length() - 1));
            resultMap.put("checkType", "checkOtherStr");
            resultMap.put("checkGender", "其他");

            checkResultList.add(resultMap);
        }

        params.put("checkList", checkResultList);

        List<CaseCompareResultGroup> matchedGroupList = doCompare(newSampleGeneList, Constants.SAME_MATCH_LIMIT_DEFAULT);
        List<CaseCompareResultGroup> newMatchedGroupList = new ArrayList<>();
        if (ListUtils.isNotNullAndEmptyList(matchedGroupList)) {
            for (CaseCompareResultGroup ccrg : matchedGroupList) {
                for (LimsSampleGene sampleGene : ccrg.getMatchedSamples()) {
                    if (sampleGene.getSampleNo().contains(caseInfo.getCaseNo().trim())) {
                        newMatchedGroupList.add(ccrg);
                        break;
                    }
                }
            }
        }
        Set<LimsSampleGene> notMatchedGeneList = new LinkedHashSet<>();

        List<CaseCompareResultInfo> resultInfoList = caseCompareResultInfoService.selectListByCaseId(caseId);

        boolean hasMatched;
        for (LimsSampleGene sampleGene : sampleGeneList) {
            hasMatched = false;
            for (CaseCompareResultGroup group : newMatchedGroupList) {
                for (LimsSampleGene matched : group.getMatchedSamples()) {
                    if (matched.getId().equals(sampleGene.getId())) {
                        hasMatched = true;
                        break;
                    }
                }

                if (hasMatched) {
                    break;
                }
            }

            for (CaseCompareResultInfo ccri : resultInfoList) {
                if (StringUtils.isNotBlank(ccri.getFatherSampleNo())) {
                    if (ccri.getFatherSampleNo().equals(sampleGene.getSampleNo())) {
                        hasMatched = true;
                        break;
                    }
                }
                if (StringUtils.isNotBlank(ccri.getMotherSampleNo())) {
                    if (ccri.getMotherSampleNo().equals(sampleGene.getSampleNo())) {
                        hasMatched = true;
                        break;
                    }
                }
                if (StringUtils.isNotBlank(ccri.getChildSampleNo())) {
                    if (ccri.getChildSampleNo().equals(sampleGene.getSampleNo())) {
                        hasMatched = true;
                        break;
                    }
                }

            }

            if (!hasMatched) {
                notMatchedGeneList.add(sampleGene);
            }
        }

        List<CaseCompareResultGroup> mixMatchedGroupList = mixDoCompare(newMixSampleGeneList, newSampleGeneList, Constants.SAME_MATCH_LIMIT_DEFAULT);
        List<CaseCompareResultGroup> newMixMatchedGroupList = new ArrayList<>();
        if (ListUtils.isNotNullAndEmptyList(mixMatchedGroupList)) {
            for (CaseCompareResultGroup mixCcrg : mixMatchedGroupList) {
                for (LimsSampleGene mixSampleGene : mixCcrg.getMixMatchedSamples()) {
                    if (mixSampleGene.getSampleNo().contains(caseInfo.getCaseNo().trim())) {
                        newMixMatchedGroupList.add(mixCcrg);
                        break;
                    }
                }
            }
        }

        boolean mixHasMatched;
        for (LimsSampleGene sampleGene : mixSampleGeneList) {
            mixHasMatched = false;

            if (ListUtils.isNotNullAndEmptyList(newMixMatchedGroupList)) {
                for (CaseCompareResultGroup group : newMixMatchedGroupList) {
                    for (LimsSampleGene matched : group.getMixMatchedSamples()) {
                        if (matched.getId().equals(sampleGene.getId())) {
                            mixHasMatched = true;
                            break;
                        }
                    }

                    if (mixHasMatched) {
                        break;
                    }
                }
            }

            if (!mixHasMatched) {
                notMatchedGeneList.add(sampleGene);
            }
        }

        int HBPAS_SIZE = 0;
        int HBNoSIZE = 0;
        int otherNoSIZE = 0;
        if (StringUtils.isBlank(checkHBNo)) {
            params.put("checkHBNo", null);
        } else {
            params.put("checkHBNo", checkHBNo.substring(0, checkHBNo.length() - 1));
            HBPAS_SIZE = HBPAS_SIZE + 1;
            HBNoSIZE = HBNoSIZE + 1;
        }
        params.put("HBNoSIZE", HBNoSIZE);

        int NoHasStrSIZE = 0;

        if (ListUtils.isNullOrEmptyList(checkHBAndPSANoHasStrList)) {
            params.put("checkHBAndPSANoHasStrList", null);
            NoHasStrSIZE = HBNoSIZE;
        } else {
            params.put("checkHBAndPSANoHasStrList", checkHBAndPSANoHasStrList);
            HBPAS_SIZE = HBPAS_SIZE + 1;
            NoHasStrSIZE = NoHasStrSIZE + 1 + HBNoSIZE;
        }
        params.put("NoHasStrSIZE", NoHasStrSIZE);

        int PSANoSIZE = 0;
        if (StringUtils.isBlank(checkPSANo)) {
            params.put("checkPSANo", null);
            PSANoSIZE = NoHasStrSIZE;
        } else {
            params.put("checkPSANo", checkPSANo.substring(0, checkPSANo.length() - 1));
            HBPAS_SIZE = HBPAS_SIZE + 1;
            PSANoSIZE = PSANoSIZE + 1 + NoHasStrSIZE;
        }
        params.put("PSANoSIZE", PSANoSIZE);


        if (StringUtils.isBlank(otherNo)) {
            params.put("otherNo", null);
            otherNoSIZE = PSANoSIZE;
        } else {
            params.put("otherNo", otherNo.substring(0, otherNo.length() - 1));
            HBPAS_SIZE = HBPAS_SIZE + 1;
            otherNoSIZE = otherNoSIZE + 1 + PSANoSIZE;
        }

        params.put("otherNoSIZE", otherNoSIZE);

        int HBNoStrSIZE = 0;
        if (StringUtils.isBlank(checkHBNoStr)) {
            params.put("checkHBNoStr", null);
            HBNoStrSIZE = otherNoSIZE;
        } else {
            params.put("checkHBNoStr", checkHBNoStr.substring(0, checkHBNoStr.length() - 1));
            HBPAS_SIZE = HBPAS_SIZE + 1;
            HBNoStrSIZE = HBNoStrSIZE + 1 + otherNoSIZE;
        }
        params.put("HBNoStrSIZE", HBNoStrSIZE);

        int PSANoStrSIZE = 0;
        if (StringUtils.isBlank(checkPSANoStr)) {
            params.put("checkPSANoStr", null);
            PSANoStrSIZE = HBNoStrSIZE;
        } else {
            params.put("checkPSANoStr", checkPSANoStr.substring(0, checkPSANoStr.length() - 1));
            HBPAS_SIZE = HBPAS_SIZE + 1;
            PSANoStrSIZE = PSANoStrSIZE + 1 + HBNoStrSIZE;
        }
        params.put("PSANoStrSIZE", PSANoStrSIZE);

        String victim = "";
        String suspect = "";
        List<LimsPersonInfo> personInfoList = limsPersonInfoService.selectListByCaseId(caseId);
        for (LimsPersonInfo personInfo : personInfoList) {
            if (personInfo.getPersonType().equals(Constants.PERSON_TYPE_BEIHAIREN)) {
                victim += personInfo.getPersonName() + "、";
            }
            if (personInfo.getPersonType().equals(Constants.PERSON_TYPE_XIANYIREN)) {
                suspect += personInfo.getPersonName() + "、";
            }
        }

        List<Map<String, Object>> unKnownList = new ArrayList<>();
        for (LimsSampleGene lsg : notMatchedGeneList) {
            List<LimsExtractRecordSample> extractRecordSampleList = limsExtractRecordService.selectListBySampleId(lsg.getSampleId());
            Map<String, Object> unKnownMap = new HashMap<>();
            if (lsg.getSampleFlag().equals(Constants.FLAG_FALSE)) {
                if (ListUtils.isNotNullAndEmptyList(extractRecordSampleList)) {
                    LimsExtractRecordSample extractRecordSample = extractRecordSampleList.get(0);
                    if (lsg.getSampleType().equals(Constants.DICT_ITEM_SAMPLE_TYPE_BLOOD)) {
                        if (extractRecordSample.getExtractHb().equals(Constants.DICT_ITEM_SAMPLE_L)) {
                            unKnownMap.put("unKnown", "BLOOD");
                            unKnownMap.put("unKnwonSampleName", "人血");
                        }
                    } else if (lsg.getSampleType().equals(Constants.DICT_ITEM_SAMPLE_TYPE_SEMINAL)) {
                        if (extractRecordSample.getExtractPsa().equals(Constants.DICT_ITEM_SAMPLE_L)) {
                            unKnownMap.put("unKnown", "SEMINAL");
                            unKnownMap.put("unKnwonSampleName", "人精斑");
                        }
                    } else if (lsg.getSampleType().equals(Constants.DICT_ITEM_SAMPLE_TYPE_BONES_TEETH)) {
                        unKnownMap.put("unKnown", "BONES_TEETH");
                        unKnownMap.put("unKnwonSampleName", "骨骼/牙齿");
                    } else {
                        unKnownMap.put("unKnown", "unKnownOther");
                        unKnownMap.put("unKnwonSampleName", null);
                    }
                    unKnownMap.put("unKnwonSampleNo", lsg.getSampleNo());
                    String geneInfo = null;
                    String unKnwonGender = null;
                    if (lsg != null) {
                        geneInfo = lsg.getGeneInfo();

                        if (StringUtils.isNotBlank(geneInfo)) {
                            JSONArray json = JSONArray.fromObject(geneInfo);

                            for (int j = 0; j < json.size(); j++) {
                                JSONObject job = json.getJSONObject(j);

                                String geneName = job.get("geneName") == null ? "" : job.get("geneName").toString();
                                String geneVal1 = job.get("geneVal1") == null ? "" : job.get("geneVal1").toString();
                                String geneVal2 = job.get("geneVal2") == null ? "" : job.get("geneVal2").toString();

                                if ("AMEL".equals(geneName.toUpperCase())) {
                                    String geneVal = geneVal1 + geneVal2;
                                    if (geneVal.toUpperCase().equals("XY")) {
                                        unKnwonGender = "男性";
                                    } else if (geneVal.toUpperCase().equals("XX")) {
                                        unKnwonGender = "女性";
                                    } else {
                                        unKnwonGender = "其他";
                                    }
                                    break;
                                }
                            }
                        }
                    }
                    unKnownMap.put("unKnwonGender", unKnwonGender);
                    if (StringUtils.isBlank(victim)) {
                        unKnownMap.put("victim", null);
                    } else {
                        unKnownMap.put("victim", victim.substring(0, victim.length() - 1));
                    }
                    if (StringUtils.isBlank(suspect)) {
                        unKnownMap.put("suspect", null);
                    } else {
                        unKnownMap.put("suspect", suspect.substring(0, suspect.length() - 1));
                    }

                    unKnownList.add(unKnownMap);
                }
            }

        }

        params.put("unKnownList", unKnownList);

        HBPAS_SIZE = HBPAS_SIZE + unKnownList.size();
        params.put("HBPAS_SIZE", HBPAS_SIZE);

        if (StringUtils.isNotBlank(sampleBarcode)) {
            params.put("sampleBarcode", sampleBarcode);
        }

        //List<LimsPcrRecord> pcrRecordList = limsPcrRecordService.selectListByCaseId(caseId);

        if (ListUtils.isNotNullAndEmptyList(pcrRecordList)) {

            params.put("pcrRecordList", pcrRecordList);

            LimsPcrRecord pcrRecord = pcrRecordList.get(0);
            params.put("pcrReagentKit", pcrRecord.getPcrReagentName());
            params.put("pcrReagent", pcrRecord.getPcrReagent());
        } else {
            params.put("pcrReagentKit", "");
        }

        String pcrSampleNo = "";
        List<LimsPcrRecordSample> pcrRecordSampleList = null;
        for (LimsPcrRecord lcr : pcrRecordList) {
            pcrRecordSampleList = limsPcrRecordService.selectSampleListByPcrRecordId(lcr.getId());

            List<LimsPcrRecordSample> pcrlist = new ArrayList<>();
            for (LimsPcrRecordSample pcr : pcrRecordSampleList) {
                if (pcr.getStandardFlag().equals("0")) {
                    pcr.setSampleNo(pcr.getSampleNo());
                    pcrlist.add(pcr);
                }
            }

            for (LimsPcrRecordSample lprs : pcrlist) {
                pcrSampleNo += lprs.getSampleNo() + "、";
            }
        }
        if (StringUtils.isBlank(pcrSampleNo)) {
            params.put("pcrSampleNo", pcrSampleNo);
        } else {
            params.put("pcrSampleNo", pcrSampleNo.substring(0, pcrSampleNo.length() - 1));
        }

        List<LimsSyRecord> syRecordList = limsSyRecordService.selectListByCaseId(caseId);
        if (ListUtils.isNotNullAndEmptyList(syRecordList)) {
            LimsSyRecord syRecord = syRecordList.get(0);
            params.put("elecInstrumentName", syRecord.getElecInstrumentName());
        } else {
            params.put("elecInstrumentName", "");
        }

        try {
            limsIdentifyBookService.generatReport(caseId);
        } catch (Exception ex) {
            logger.error("修改鉴定书状态为 已出 时错误！", ex);
        }


        if (StringUtils.isBlank(geneBarcode)) {
            params.put("geneBarcode", null);
        } else {
            params.put("geneBarcode", geneBarcode.substring(0, geneBarcode.length() - 1));
        }

        if (StringUtils.isBlank(noGeneBarcode)) {
            params.put("noGeneBarcode", null);
        } else {
            params.put("noGeneBarcode", noGeneBarcode.substring(0, noGeneBarcode.length() - 1));
        }

        if (StringUtils.isBlank(noYstrGeneBarcode)) {
            params.put("noYstrGeneBarcode", null);
        } else {
            params.put("noYstrGeneBarcode", noYstrGeneBarcode.substring(0, noYstrGeneBarcode.length() - 1));
        }

        params.put("geneList1", Constants.GENE_LIST1);
        params.put("geneList2", Constants.GENE_LIST2);

        params.put("ygeneList1", Constants.YGENE_LIST1);
        params.put("ygeneList2", Constants.YGENE_LIST2);
        params.put("ygeneList1", Constants.YGENE_LIST1);

        params.put("hGeneList1", Constants.HENE_LIST1);
        params.put("hGeneList2", Constants.HENE_LIST2);
        params.put("hGeneList3", Constants.HENE_LIST3);

        params.put("iGeneList1", Constants.IENE_LIST1);
        params.put("iGeneList2", Constants.IENE_LIST2);
        params.put("iGeneList3", Constants.IENE_LIST3);

        params.put("panels", panels);

        params.put("sampleGeneDataModelList", sampleGeneDataModelList);

        params.put("caseInnerMatchedModelList", caseInnerMatchedModelList);

        params.put("caseInnerMatchedModelHBList", caseInnerMatchedModelHBList);

        int matchedModelHBSize = 0;
        matchedModelHBSize = HBPAS_SIZE + caseInnerMatchedModelHBList.size();

        params.put("matchedModelHBSize", matchedModelHBSize);

        int matchedModelPSASize = 0;
        matchedModelPSASize = matchedModelHBSize + caseInnerMatchedModelPSAList.size();

        params.put("caseInnerMatchedModelPSAList", caseInnerMatchedModelPSAList);

        params.put("matchedModelPSASize", matchedModelPSASize);

        int caseInnerMatchedSize = 0;
        caseInnerMatchedSize = matchedModelPSASize + caseInnerMatchedModelOtherList.size();

        params.put("caseInnerMatchedModelOtherList", caseInnerMatchedModelOtherList);

        params.put("caseInnerMatchedSize", caseInnerMatchedSize);

        String mixBarcode = "";
        for (LimsSampleGene lsg : mixSampleGeneList) {
            mixBarcode += lsg.getSampleNo() + "、";
        }

        if (StringUtils.isBlank(mixBarcode)) {
            params.put("mixBarcode", null);
        } else {
            mixBarcode = mixBarcode.substring(0, mixBarcode.length() - 1);
            params.put("mixBarcode", mixBarcode);
        }

        int checkResultNum = 0;
        if (checkResultList.size() > 0) {
            checkResultNum = 2;
            params.put("checkResultNum", checkResultNum);
        } else {
            checkResultNum = 1;
            params.put("checkResultNum", checkResultNum);
        }

        int geneBarcodeNum = 0;
        if (checkResultList.size() > 0 && (StringUtils.isNotBlank(noGeneBarcode) || StringUtils.isNotBlank(mixBarcode) || StringUtils.isNotBlank(noYstrGeneBarcode))) {
            geneBarcodeNum = 3;
            params.put("geneBarcodeNum", geneBarcodeNum);
        } else if (checkResultList.size() > 0 ||
                (StringUtils.isNotBlank(noGeneBarcode) || StringUtils.isNotBlank(mixBarcode) || StringUtils.isNotBlank(noYstrGeneBarcode))) {
            geneBarcodeNum = 2;
            params.put("geneBarcodeNum", geneBarcodeNum);
        } else {
            geneBarcodeNum = 1;
            params.put("geneBarcodeNum", geneBarcodeNum);
        }

        int reagentNum = 0;
        params.put("reagentNum", reagentNum + 1 + geneBarcodeNum);

        List<Map<String, Object>> mixGroupList = new ArrayList<>();
        Map<String, Object> mixMap = new LinkedHashMap<>();
        List<LimsSampleGene> mixMatchedSamples = null;
        for (CaseCompareResultGroup ccrg : newMixMatchedGroupList) {
            String mixSampleNo = "";
            String mixSampleType = "";
            String matchSampleNo = "";
            String matchSampleName = "";
            mixMatchedSamples = ccrg.getMixMatchedSamples();

            mixMap = new LinkedHashMap<>();
            LimsSampleInfo sampleInfo;
            for (LimsSampleGene lsg : mixMatchedSamples) {

                if (lsg.getSampleFlag().equals(Constants.SAMPLE_FLAG_EVIDENCE)) {
                    mixSampleNo = lsg.getSampleNo();
                    LimsSampleInfo lsi = limsSampleInfoService.selectById(lsg.getSampleId());
                    mixSampleType = lsi.getSampleType();
                } else {
                    matchSampleNo += lsg.getSampleNo() + "、";
                    sampleInfo = limsSampleInfoService.selectById(lsg.getSampleId());

                    matchSampleName += sampleInfo.getRefPersonName() + "、";
                }

            }

            mixMap.put("mixSampleNo", mixSampleNo);
            mixMap.put("mixSampleType", mixSampleType);
            if (StringUtils.isNotBlank(matchSampleNo)) {
                mixMap.put("matchSampleNo", matchSampleNo.substring(0, matchSampleNo.length() - 1));
            } else {
                mixMap.put("matchSampleNo", matchSampleNo);
            }

            if (StringUtils.isNotBlank(matchSampleName)) {
                mixMap.put("matchSampleName", matchSampleName.substring(0, matchSampleName.length() - 1));
            } else {
                mixMap.put("matchSampleName", matchSampleName);
            }

            mixGroupList.add(mixMap);
        }
        params.put("mixGroupList", mixGroupList);

        int mixGroupSize = 0;
        mixGroupSize = caseInnerMatchedSize + mixGroupList.size();
        params.put("mixGroupSize", mixGroupSize);

        List<CaseCompareResultInfo> compareResultInfoList = caseCompareResultInfoService.selectListByCaseId(caseId);

        if (ListUtils.isNotNullAndEmptyList(compareResultInfoList)) {
            LimsSampleInfo limsSampleInfo = null;
            for (CaseCompareResultInfo ccri : compareResultInfoList) {
                ccri.setRelationProbability1(ccri.getCompareTotalProbability().split("E")[0]);
                ccri.setRelationProbability2(ccri.getCompareTotalProbability().split("E")[1]);

                if (StringUtils.isNotBlank(ccri.getFatherSampleNo())) {
                    limsSampleInfo = limsSampleInfoService.selectListBySampleNo(ccri.getFatherSampleNo()).get(0);
                    ccri.setFatherSampleName(limsSampleInfo.getSampleName());
                }

                if (StringUtils.isNotBlank(ccri.getMotherSampleNo())) {
                    limsSampleInfo = limsSampleInfoService.selectListBySampleNo(ccri.getMotherSampleNo()).get(0);
                    ccri.setMotherSampleName(limsSampleInfo.getSampleName());
                }

                if (StringUtils.isNotBlank(ccri.getChildSampleNo())) {
                    limsSampleInfo = limsSampleInfoService.selectListBySampleNo(ccri.getChildSampleNo()).get(0);
                    ccri.setChildSampleName(limsSampleInfo.getSampleName());
                }
            }
            params.put("compareResultInfoList", compareResultInfoList);
        }

        int compareResultSize = 0;
        compareResultSize = compareResultInfoList.size() + mixGroupSize;
        params.put("compareResultSize", compareResultSize);

        LimsSampleGene YstrSampleGene = new LimsSampleGene();
        YstrSampleGene.setCaseId(caseId);
        YstrSampleGene.setAuditStatus(Constants.FLAG_TRUE);
        YstrSampleGene.setGeneType(Constants.GENE_TYPE_YSTR);
        List<LimsSampleGene> YstrSampleGeneList = limsSampleGeneService.selectGeneInfoList(YstrSampleGene);

        List<SampleGeneDataModel> ySampleGeneDataModelList = new ArrayList<>();
        List<Map<String, Object>> ystrList = new ArrayList<>();
        String yGeneBarcode = "";
        List<GeneInfoModel> yGeneInfoModelList = new ArrayList<>();
        if (ListUtils.isNotNullAndEmptyList(YstrSampleGeneList)) {
            int cnt = YstrSampleGeneList.size();
            LimsSampleGene s1 = null;
            LimsSampleGene s2 = null;
            HashSet<Integer> matchedIdxList = new LinkedHashSet<>();

            Map<String, Object> ystrMap;
            for (int i = 0; i < cnt; i++) {
                s1 = YstrSampleGeneList.get(i);

                String ystrSampleNo = "";
                String ystrMatchSampleNo = "";
                String ystrNoMatchSampleNo = "";
                ystrMap = new LinkedHashMap<>();

                if (matchedIdxList.contains(i) || s1.getSampleFlag().equals(Constants.SAMPLE_FLAG_EVIDENCE)) {
                    continue;
                }

                ystrSampleNo = s1.getSampleNo();

                for (int j = i + 1; j < cnt; j++) {
                    s2 = YstrSampleGeneList.get(j);
                    if (compare(s1, s2, Constants.YSTR_MATCH_LIMIT_DEFAULT)) {
                        ystrMatchSampleNo += s2.getSampleNo() + "、";
                    }

                    if (!compare(s1, s2, Constants.YSTR_MATCH_LIMIT_DEFAULT)) {
                        ystrNoMatchSampleNo += s2.getSampleNo() + "、";
                    }

                    matchedIdxList.add(j);
                }
                ystrMap.put("ystrSampleNo", ystrSampleNo);
                if (StringUtils.isBlank(ystrMatchSampleNo)) {
                    ystrMap.put("ystrMatchSampleNo", null);
                } else {
                    ystrMap.put("ystrMatchSampleNo", ystrMatchSampleNo.substring(0, ystrMatchSampleNo.length() - 1));
                }
                if (StringUtils.isBlank(ystrNoMatchSampleNo)) {
                    ystrMap.put("ystrNoMatchSampleNo", null);
                } else {
                    ystrMap.put("ystrNoMatchSampleNo", ystrNoMatchSampleNo.substring(0, ystrNoMatchSampleNo.length() - 1));
                }

                ystrList.add(ystrMap);

            }

            for (int i = 0; i < YstrSampleGeneList.size(); i++) {
                LimsSampleGene ylsg = YstrSampleGeneList.get(i);

                SampleGeneDataModel sampleGeneDataModel = new SampleGeneDataModel();
                sampleGeneDataModel.setBarcode(ylsg.getSampleNo());
                sampleGeneDataModel.setGeneType(ylsg.getGeneType());
                yGeneBarcode += ylsg.getSampleNo() + "、";

                String geneInfos = ylsg.getGeneInfo();

                List<String> yGeneList1 = Constants.YGENE_LIST1;
                List<String> yGeneList2 = Constants.YGENE_LIST2;
                List<String> yGeneList3 = Constants.YGENE_LIST3;

                if (StringUtils.isNotBlank(geneInfos)) {
                    JSONArray json = JSONArray.fromObject(geneInfos);

                    String geneName = null;
                    String geneVal1 = null;
                    if (json.size() > 0) {
                        yGeneInfoModelList = new ArrayList<>();
                        for (int m = 0; m < yGeneList1.size(); m++) {
                            String geneStr1 = yGeneList1.get(m).toUpperCase();
                            GeneInfoModel geneInfoModel = new GeneInfoModel();
                            for (int j = 0; j < json.size(); j++) {
                                JSONObject job = json.getJSONObject(j);

                                geneName = job.get("geneName") == null ? "" : job.get("geneName").toString();
                                geneVal1 = job.get("geneVal1") == null ? "" : job.get("geneVal1").toString();

                                if (geneStr1.equals(geneName.toUpperCase())) {
                                    geneInfoModel.setLocusName(geneName);
                                    geneInfoModel.setGeneVal1(geneVal1);
                                    break;
                                }
                            }
                            yGeneInfoModelList.add(geneInfoModel);
                        }

                        sampleGeneDataModel.setGeneModelList1(yGeneInfoModelList);

                        yGeneInfoModelList = new ArrayList<>();
                        for (int m = 0; m < yGeneList2.size(); m++) {
                            String geneStr2 = yGeneList2.get(m).toUpperCase();
                            GeneInfoModel geneInfoModel = new GeneInfoModel();
                            for (int n = 0; n < json.size(); n++) {
                                JSONObject job = json.getJSONObject(n);

                                geneName = StringUtils.isBlank(job.get("geneName").toString()) ? "" : job.get("geneName").toString();
                                geneVal1 = job.get("geneVal1") == null ? "" : job.get("geneVal1").toString();

                                if (geneStr2.equals(geneName.toUpperCase())) {
                                    geneInfoModel.setLocusName(geneName);
                                    geneInfoModel.setGeneVal1(geneVal1);
                                    break;
                                }
                            }

                            yGeneInfoModelList.add(geneInfoModel);
                        }

                        sampleGeneDataModel.setGeneModelList2(yGeneInfoModelList);

                        yGeneInfoModelList = new ArrayList<>();
                        for (int m = 0; m < yGeneList3.size(); m++) {
                            String geneStr3 = yGeneList3.get(m).toUpperCase();
                            GeneInfoModel geneInfoModel = new GeneInfoModel();
                            for (int n = 0; n < json.size(); n++) {
                                JSONObject job = json.getJSONObject(n);

                                geneName = StringUtils.isBlank(job.get("geneName").toString()) ? "" : job.get("geneName").toString();
                                geneVal1 = job.get("geneVal1") == null ? "" : job.get("geneVal1").toString();

                                if (geneStr3.equals(geneName.toUpperCase())) {
                                    geneInfoModel.setLocusName(geneName);
                                    geneInfoModel.setGeneVal1(geneVal1);
                                    break;
                                }
                            }

                            yGeneInfoModelList.add(geneInfoModel);
                        }

                        sampleGeneDataModel.setGeneModelList3(yGeneInfoModelList);
                    }
                }

                ySampleGeneDataModelList.add(sampleGeneDataModel);
            }

        }
        params.put("yGeneBarcode", yGeneBarcode);
        params.put("ystrList", ystrList);

        params.put("ySampleGeneDataModelList", ySampleGeneDataModelList);

        Writer out = null;
        try {
            freemarker.template.Configuration cfg = new freemarker.template.Configuration();
            cfg.setDefaultEncoding("UTF-8");
            ServletContext servletContext = request.getSession().getServletContext();
            cfg.setServletContextForTemplateLoading(servletContext, "/templates");
            //获取模板
            Template tmp = null;
            if (StringUtils.isNotBlank(geneBarcode) && (ListUtils.isNotNullAndEmptyList(caseInnerMatchedList) || ListUtils.isNotNullAndEmptyList(mixGroupList)
                    || ListUtils.isNotNullAndEmptyList(compareResultInfoList)) || ListUtils.isNotNullAndEmptyList(ystrList)) {

                for (int i = 0; i < sampleInfoList.size(); i++) {
                    //取出sampleNo 并循环对比
                    String sampleNo = sampleInfoList.get(i).getSampleNo();
                    //如果是拆分后的则进行对比
                    int count = sampleNo.length() - sampleNo.replaceAll("-", "").length();
                    if (count >= 4) {
                        if (!StringUtils.isBlank(sampleNo) && sampleNo.length() > 15) {
                            String sampleNoSub = "";
                            String[] sampleNoArr = sampleNo.split("-");
                            for (int g = 0; g < 4; g++) {
                                sampleNoSub = sampleNoSub + "-" + sampleNoArr[g];
                            }
                            sampleNoSub = sampleNoSub.substring(1);
                            for (int j = 0; j < sampleInfoList.size(); j++) {
                                String sampleNo1 = sampleInfoList.get(j).getSampleNo();
                                //比较sampleNo
                                if (sampleNoSub.equals(sampleNo1)) {
                                    if (StringUtils.isBlank(sampleInfoList.get(j).getSampleNoTemp())) {
                                        //给SampleNoTemp赋值
                                        //sampleInfoList.get(j).setSampleNoTemp(sampleNoSub);
                                    }
                                    List<LimsExtractRecordSample> limsExtractRecordSampleList = limsExtractRecordService.selectListSpitSampleNo(sampleInfoList.get(j).getSampleNoTemp() + "-1");
                                    List<LimsExtractRecordSample> limsExtractRecordSampleSpitList = limsExtractRecordService.selectListSpitSampleNo(sampleNo);

                                    for (LimsExtractRecordSample limsExtractRecordSample : limsExtractRecordSampleList) {
                                        sampleInfoList.get(j).setExtractPositionAndChangeMethod(limsExtractRecordSample.getChangeMethod() + limsExtractRecordSample.getExtractPosition() + "上可疑斑迹");
                                    }

                                    //如果一样 责赋值给sampleNoTemp字段（如果赋值给sampleNo则会覆盖）
                                    for (LimsExtractRecordSample limsExtractRecordSampleSpit : limsExtractRecordSampleSpitList) {

                                        String[] sCnt = limsExtractRecordSampleSpit.getSampleNo().split("-");
                                        String noCnt = null;
                                        for (int n = 0; n < sCnt.length; n++) {
                                            noCnt = sCnt[4];
                                        }

                                        if (noCnt.equals("2")) {
                                            sampleInfoList.get(j).setSampleNoTemp(sampleInfoList.get(j).getSampleNoTemp() + "-1" + "号检材;" + limsExtractRecordSampleSpit.getChangeMethod() + limsExtractRecordSampleSpit.getExtractPosition() + "上可疑斑迹编为" + limsExtractRecordSampleSpit.getSampleNo());
                                        } else {
                                            //sampleInfoList.get(j).setSampleNoTemp(sampleInfoList.get(j).getSampleNoTemp() + "号检材;" + limsExtractRecordSampleSpit.getChangeMethod() + limsExtractRecordSampleSpit.getExtractPosition() + "上可疑斑迹编为" + limsExtractRecordSampleSpit.getSampleNo());
                                        }
                                    }
                                    //移除赋值的下标
                                    sampleInfoList.remove(i);
                                    i--;//下标--
                                    break;
                                }
                            }
                        }
                    } else {
                        //赋值没有进行拆分的编号
                        sampleInfoList.get(i).setSampleNoTemp(sampleNo);
                    }
                }
                tmp = cfg.getTemplate("detectIdentifyBook.ftl", "UTF-8");

                String filename = "-鉴定书" + ".doc";

                out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(getGeneratePath(caseInfo, filename)), "UTF-8"));
            } else {
                tmp = cfg.getTemplate("detectIdentifyReport.ftl", "UTF-8");

                String filename = "-检验报告" + ".doc";

                out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(getGeneratePath(caseInfo, filename)), "UTF-8"));
            }

            tmp.process(params, out);
        } catch (Exception ex) {
            logger.error("生成鉴定书错误", ex);
            flag = false;
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }

    private List<CaseCompareResultGroup> doCompare(List<LimsSampleGene> sampleGeneList, int matchLimit) {
        List<CaseCompareResultGroup> groupList = new ArrayList<>();

        int cnt = sampleGeneList.size();
        LimsSampleGene s1 = null;
        LimsSampleGene s2 = null;
        HashSet<Integer> matchedIdxList = new LinkedHashSet<>();
        List<LimsSampleGene> matchedList = null;
        for (int i = 0; i < cnt; i++) {
            if (matchedIdxList.contains(i)) {
                continue;
            }

            matchedList = new ArrayList<>();
            s1 = sampleGeneList.get(i);
            for (int j = i + 1; j < cnt; j++) {
                s2 = sampleGeneList.get(j);
                if (compare(s1, s2, matchLimit)) {
                    matchedList.add(s2);
                    matchedIdxList.add(j);
                }
            }

            if (matchedList.size() > 0) {
                matchedList.add(0, s1);
                matchedIdxList.add(i);

                CaseCompareResultGroup group = new CaseCompareResultGroup();
                group.setGroupId(i + 1);
                group.setMatchedSamples(matchedList);
                groupList.add(group);
            }
        }

        return groupList;
    }

    private boolean compare(LimsSampleGene s1, LimsSampleGene s2, int matchLimit) {
        ObjectMapper jsonObjectMapper = new ObjectMapper();

        String geneStr1 = s1.getGeneInfo();
        String geneStr2 = s2.getGeneInfo();

        List<CodisGeneInfo> geneDetailList1 = null;
        List<CodisGeneInfo> geneDetailList2 = null;

        try {
            geneDetailList1 = jsonObjectMapper.readValue(geneStr1, jsonObjectMapper.getTypeFactory().constructCollectionType(List.class, CodisGeneInfo.class));
            geneDetailList2 = jsonObjectMapper.readValue(geneStr2, jsonObjectMapper.getTypeFactory().constructCollectionType(List.class, CodisGeneInfo.class));
        } catch (Exception ex) {
            logger.error("解析基因分型信息错误！", ex);
        }

        if (ListUtils.isNullOrEmptyList(geneDetailList1)
                || ListUtils.isNullOrEmptyList(geneDetailList2)) {
            return false;
        }

        int sameCount = 0;
        String geneName = "";
        String gene1Val1 = "";
        String gene1Val2 = "";

        String gene2Val1 = "";
        String gene2Val2 = "";
        for (CodisGeneInfo gene1 : geneDetailList1) {
            geneName = gene1.getGeneName().toUpperCase();
            gene1Val1 = gene1.getGeneVal1();
            gene1Val2 = gene1.getGeneVal2();

            if (StringUtils.isNotBlank(gene1Val1) || StringUtils.isNotBlank(gene1Val2)) {
                for (CodisGeneInfo gene2 : geneDetailList2) {
                    if (gene2.getGeneName().toUpperCase().equals(geneName)) {
                        gene2Val1 = gene2.getGeneVal1();
                        gene2Val2 = gene2.getGeneVal2();

                        if (StringUtils.isNotBlank(gene2Val1) || StringUtils.isNotBlank(gene2Val2)) {
                            if ((gene1Val1.toUpperCase().equals(gene2Val1.toUpperCase()) && gene1Val2.toUpperCase().equals(gene2Val2.toUpperCase())
                                    || (gene1Val1.toUpperCase().equals(gene2Val2.toUpperCase())) && gene1Val2.toUpperCase().equals(gene2Val1.toUpperCase()))) {
                                sameCount++;
                                break;
                            }
                        }
                    }
                }
            }
        }

        return sameCount >= matchLimit;
    }

    private List<CaseCompareResultGroup> mixDoCompare(List<LimsSampleGene> mixSampleGeneList, List<LimsSampleGene> sampleGeneList, int matchLimit) {
        List<CaseCompareResultGroup> groupList = new ArrayList<>();

        LimsSampleGene sg1 = null;
        LimsSampleGene sg2 = null;

        HashSet<Integer> matchedIdxList = new LinkedHashSet<>();
        List<LimsSampleGene> matchedList = null;
        CaseCompareResultGroup group;
        Map<String, Object> map;
        if (ListUtils.isNotNullAndEmptyList(mixSampleGeneList) && ListUtils.isNotNullAndEmptyList(sampleGeneList)) {
            for (int i = 0; i < mixSampleGeneList.size(); i++) {

                matchedList = new ArrayList<>();
                sg1 = mixSampleGeneList.get(i);

                for (int j = 0; j < sampleGeneList.size(); j++) {
                    sg2 = sampleGeneList.get(j);

                    if (sg2.getSampleFlag().equals(Constants.SAMPLE_FLAG_EVIDENCE)) {
                        continue;
                    }

                    map = mixCompare(sg1, sg2, matchLimit);
                    if (map.get("match").equals(true)) {
                        matchedList.add(sg2);
                        matchedIdxList.add(j);
                    }
                }

                if (matchedList.size() > 0) {
                    matchedList.add(0, sg1);
                    matchedIdxList.add(i);

                    group = new CaseCompareResultGroup();
                    group.setMixMatchedSamples(matchedList);
                    group.setGroupId(i + 1);
                    groupList.add(group);
                }
            }
        }

        return groupList;
    }

    private Map<String, Object> mixCompare(LimsSampleGene mixSg, LimsSampleGene sg, int matchLimit) {

        Map<String, Object> result = new HashMap<>();
        int diffCount = 0;
        int sameCount = 0;

        ObjectMapper jsonObjectMapper = new ObjectMapper();

        String geneStr = sg.getGeneInfo();
        String mixGeneStr = mixSg.getGeneInfo();

        List<CodisGeneInfo> mixGeneDetailList = null;
        List<CodisGeneInfo> geneDetailList = null;

        try {
            geneDetailList = jsonObjectMapper.readValue(geneStr, jsonObjectMapper.getTypeFactory().constructCollectionType(List.class, CodisGeneInfo.class));
            mixGeneDetailList = jsonObjectMapper.readValue(mixGeneStr, jsonObjectMapper.getTypeFactory().constructCollectionType(List.class, CodisGeneInfo.class));
        } catch (Exception ex) {
            logger.error("解析基因分型信息错误！", ex);
        }

        if (ListUtils.isNullOrEmptyList(geneDetailList)
                || ListUtils.isNullOrEmptyList(mixGeneDetailList)) {

            result.put("match", Boolean.FALSE);
            result.put("diffCount", diffCount);
            result.put("sameCount", sameCount);

            return result;
        }

        CodisGeneInfo geneInfo;
        CodisGeneInfo mixGeneInfo;

        String geneVal1 = "";
        String geneVal2 = "";


        for (int i = 0; i < geneDetailList.size(); i++) {
            boolean hasSame = false;
            geneInfo = geneDetailList.get(i);

            geneVal2 = geneInfo.getGeneVal2().toUpperCase();
            geneVal1 = geneInfo.getGeneVal1().toUpperCase();
            for (int j = 0; j < mixGeneDetailList.size(); j++) {
                mixGeneInfo = mixGeneDetailList.get(j);

                String mixGeneVal1 = mixGeneInfo.getGeneVal1().toUpperCase();
                String mixGeneVal2 = mixGeneInfo.getGeneVal2().toUpperCase();
                String mixGeneVal3 = mixGeneInfo.getGeneVal3().toUpperCase();
                String mixGeneVal4 = mixGeneInfo.getGeneVal4().toUpperCase();
                if (geneInfo.getGeneName().toUpperCase().equals(mixGeneInfo.getGeneName().toUpperCase())) {
                    if (StringUtils.isNotBlank(mixGeneVal1) || StringUtils.isNotBlank(mixGeneVal2) || StringUtils.isNotBlank(mixGeneVal3) || StringUtils.isNotBlank(mixGeneVal4)) {
                        if ((mixGeneVal1.equals(geneVal1) || mixGeneVal2.equals(geneVal1) || mixGeneVal3.equals(geneVal1) || mixGeneVal4.equals(geneVal1)) &&
                                (mixGeneVal1.equals(geneVal2) || mixGeneVal2.equals(geneVal2) || mixGeneVal3.equals(geneVal2) || mixGeneVal4.equals(geneVal2))) {
                            sameCount++;
                            hasSame = true;
                            break;
                        }
                    }
                }
            }

            if (!hasSame) {
                diffCount++;
            }
        }


        if (sameCount >= matchLimit) {
            result.put("match", Boolean.TRUE);
        } else {
            result.put("match", Boolean.FALSE);
        }
        result.put("sameCount", sameCount);
        result.put("diffCount", diffCount);
        return result;
    }

    @RequestMapping("/uploadIdentifyBook.html")
    public
    @ResponseBody
    Map<String, Object> uploadCodis(HttpServletRequest request, HttpServletResponse response,
                                    @RequestParam("identifyFile") MultipartFile identifyFile, LimsIdentifyBook limsIdentifyBook) {
        Map<String, Object> resultMap = new HashMap<>();

        try {
            String identifyBookPath = SystemUtil.getSystemConfig().getProperty("identifyBookPath");

            if (!new File(identifyBookPath).exists()) {
                new File(identifyBookPath).mkdir();
            }

            String uploadPath = SystemUtil.getSystemConfig().getProperty("uploadPath");

            uploadPath = identifyBookPath + uploadPath + "\\";

            File remoteFile = new File(uploadPath);

            if (!remoteFile.exists()) {
                remoteFile.mkdir();// 创建远程文件夹
            }
            LimsIdentifyBook identifyBook = limsIdentifyBookService.selectByCaseId(limsIdentifyBook.getCaseId());

            String filePath = uploadPath + identifyBook.getCaseId() + "\\";

            if (!new File(filePath).exists()) {
                new File(filePath).mkdir();// 创建远程文件夹
            }

            if (new File(filePath).exists()) {
                String filename = identifyFile.getOriginalFilename();
                InputStream is = identifyFile.getInputStream();

                // 开始保存文件到服务器
                if (!filename.equals("")) {
                    FileOutputStream fos = new FileOutputStream(filePath + filename);
                    byte[] buffer = new byte[1024];
                    int count = 0;
                    // 开始读取上传文件的字节，并将其输出到服务端的上传文件输出流中
                    while ((count = is.read(buffer)) > 0) {
                        fos.write(buffer, 0, count); // 向服务端文件写入字节流
                    }
                    fos.close(); // 关闭FileOutputStream对象
                    is.close(); // InputStream对象
                }

                List<LimsIdentifyBook> identifyBookList = limsIdentifyBookService.selectIdentifyBookList(limsIdentifyBook);
                if (ListUtils.isNotNullAndEmptyList(identifyBookList)) {
                    for (LimsIdentifyBook lib : identifyBookList) {
                        if (lib.getStatus().equals(Constants.IDENTIFY_BOOK_PENDING)) {
                            lib.setStatus(Constants.IDENTIFY_BOOK_NEED_SIGN);
                            lib.setStatusName(Constants.IDENTIFY_BOOK_NEED_SIGN_NAME);
                        }
                        lib.setUploadPath(filePath + filename);
                        updateIdentifyBookPath(lib);
                    }
                }

            }
        } catch (Exception ex) {
            resultMap.put("success", false);
            resultMap.put("errMsg", ex.getMessage());
            return resultMap;
        }

        resultMap.put("success", true);
        return resultMap;
    }

    public void updateIdentifyBookPath(LimsIdentifyBook identifyBook) {
        try {
            limsIdentifyBookService.updateIdentifyBookPath(identifyBook);
        } catch (Exception e) {
            logger.info("更新上传路径失败！");
        }
    }

    @RequestMapping(value = "/selectIsExist.html")
    @ResponseBody
    public boolean selectIsExist(HttpServletRequest request, HttpServletResponse response, Integer caseId, String generate) {
        boolean flag = true;

        try {
            LimsIdentifyBook identifyBook = limsIdentifyBookService.selectByCaseId(caseId);
            if (generate.equals("rebuild")) {
                if (StringUtils.isBlank(identifyBook.getFilePath())) {
                    flag = false;
                }
            } else {
                if (StringUtils.isBlank(identifyBook.getUploadPath())) {
                    flag = false;
                }
            }
        } catch (Exception e) {
            flag = false;
        }

        return flag;
    }

    @RequestMapping(value = "/downloadUpload.html")
    public void downloadUpload(HttpServletRequest request, HttpServletResponse response, Integer caseId, String generate) {

        LimsIdentifyBook identifyBook = limsIdentifyBookService.selectByCaseId(caseId);

        String fileSavePath = null;

        if (generate.equals("rebuild")) {
            fileSavePath = identifyBook.getFilePath();
        } else if (generate.equals("upload")) {
            fileSavePath = identifyBook.getUploadPath();
        } else if (generate.equals("download")) {
            if (StringUtils.isNotBlank(identifyBook.getFilePath()) && StringUtils.isNotBlank(identifyBook.getUploadPath())) {
                /*File downFile = new File(identifyBook.getFilePath());
                File uploadFile = new File(identifyBook.getUploadPath());
                long downDate = downFile.lastModified();
                long uploadDate = uploadFile.lastModified();

                if (downDate < uploadDate) {
                    fileSavePath = identifyBook.getUploadPath();
                }else if (uploadDate < downDate) {
                    fileSavePath = identifyBook.getFilePath();
                }else {
                    fileSavePath = identifyBook.getUploadPath();
                }*/
                fileSavePath = identifyBook.getUploadPath();
            } else if (StringUtils.isNotBlank(identifyBook.getFilePath()) && StringUtils.isBlank(identifyBook.getUploadPath())) {
                fileSavePath = identifyBook.getFilePath();
            } else if (StringUtils.isBlank(identifyBook.getFilePath()) && StringUtils.isNotBlank(identifyBook.getUploadPath())) {
                fileSavePath = identifyBook.getUploadPath();
            }
        }

        File filePath = new File(fileSavePath);

        String downloadName = filePath.getName();

        OutputStream out = null;
        FileInputStream in = null;
        try {
            response.setHeader("Content-disposition", "attachment;filename=" + new String(downloadName.getBytes("GBK"), "ISO8859-1"));

            out = response.getOutputStream();
            byte d[] = new byte[3052];
            int count = 0;
            in = new FileInputStream(fileSavePath);
            while ((count = in.read(d)) != -1) {
                out.write(d, 0, count);
            }
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @RequestMapping("/signIdentifyBook.html")
    public ModelAndView signIdentifyBook(HttpServletRequest request, LimsCaseInfoVO query, PageInfo pageInfo, Integer caseId) throws ParseException {

        query = resetCaseInfoQuery(query);
        LimsIdentifyBook book = limsIdentifyBookService.selectByCaseId(query.getEntity().getId());
         /*  实验室人员 */
        List<LabUser> labUserList = labUserService.selectAll();
        LimsIdentifyBookNotice identifyBookNotice = new LimsIdentifyBookNotice();
        identifyBookNotice.setCaseId(book.getCaseId());
        identifyBookNotice.setIdentifyBookId(book.getId());
        identifyBookNotice.setNoticeContent("");
        if (ListUtils.isNotNullAndEmptyList(labUserList)) {
            LabUser user = labUserList.get(0);
            identifyBookNotice.setCreatePersonId(user.getId().toString());
            identifyBookNotice.setCreatePersonName(user.getUserName());
        } else {
            identifyBookNotice.setCreatePersonId("");
            identifyBookNotice.setCreatePersonName("");
        }

        try {
            limsIdentifyBookService.updateStatus(book.getCaseId());
            limsIdentifyBookNoticeService.insert(identifyBookNotice);
        } catch (Exception e) {
            logger.error("签发失败!");
        }

        LimsCaseInfo limsCaseInfo = limsCaseInfoService.selectById(query.getEntity().getId());

        List<DictItem> caseStatusList = dictService.selectDictItemListByType(Constants.DICT_TYPE_CASE_STATUS);
        List<DictItem> caseTypeList = dictService.selectDictItemListByType(Constants.DICT_TPYE_CASE_TYPE);
        List<DictItem> casePropertyList = dictService.selectDictItemListByType(Constants.DICT_TPYE_CASE_PROPERTY);

        query.setCaseStatusList(Arrays.asList(Constants.CASE_INFO_STATUS_ACCEPTED, Constants.CASE_INFO_STATUS_FINISHED));
        query.setAdditionalFlag(Constants.FLAG_FALSE);
        query.setIdentifyBookStatus(Constants.IDENTIFY_BOOK_SIGNED);
        query.setOrderByClause(" caseNo1 DESC, caseNo2 DESC");
        /*query.getEntity().setCaseName(limsCaseInfo.getCaseName());*/
        query.getEntity().setCaseNo(limsCaseInfo.getCaseNo());

        List<LimsCaseInfoVO> caseInfoList = limsCaseInfoService.selectVOIdentifyBookList(query, pageInfo);
        int totalCnt = limsCaseInfoService.selectVOIdentifyBookCount(query);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("caseStatusList", caseStatusList);
        modelAndView.addObject("caseTypeList", caseTypeList);
        modelAndView.addObject("caseInfoQuery", query);
        modelAndView.addObject("casePropertyList", casePropertyList);
        modelAndView.addObject("caseInfoList", caseInfoList);
        modelAndView.addObject("limsCaseInfo", limsCaseInfo);
        modelAndView.addObject("pageInfo", pageInfo.updatePageInfo(totalCnt));
        modelAndView.setViewName("center/identifybook/listSigned");
        return modelAndView;
    }

    @RequestMapping("/02.html")
    public ModelAndView listGenerated(HttpServletRequest request, LimsCaseInfoVO query, PageInfo pageInfo) throws ParseException {
        List<DictItem> caseStatusList = dictService.selectDictItemListByType(Constants.DICT_TYPE_CASE_STATUS);
        List<DictItem> caseTypeList = dictService.selectDictItemListByType(Constants.DICT_TPYE_CASE_TYPE);
        List<DictItem> casePropertyList = dictService.selectDictItemListByType(Constants.DICT_TPYE_CASE_PROPERTY);
        ModelAndView modelAndView = new ModelAndView();
        query = resetCaseInfoQuery(query);
        query.setCaseStatusList(Arrays.asList(Constants.CASE_INFO_STATUS_ACCEPTED, Constants.CASE_INFO_STATUS_FINISHED));
        query.setAdditionalFlag(Constants.FLAG_FALSE);
        query.setIdentifyBookStatus(Constants.IDENTIFY_BOOK_NEED_SIGN);
        query.setOrderByClause(" caseNo1 DESC, caseNo2 DESC");

        if (query.getEntity().getId() != null) {
            LimsCaseInfo limsCaseInfo = limsCaseInfoService.selectById(query.getEntity().getId());
            query.getEntity().setCaseNo(limsCaseInfo.getCaseNo());
            modelAndView.addObject("limsCaseInfo", limsCaseInfo);
        }
        List<LimsCaseInfoVO> caseInfoList = limsCaseInfoService.selectVOIdentifyBookList(query, pageInfo);
        int totalCnt = limsCaseInfoService.selectVOIdentifyBookCount(query);

        modelAndView.addObject("caseStatusList", caseStatusList);
        modelAndView.addObject("caseTypeList", caseTypeList);
        modelAndView.addObject("caseInfoQuery", query);

        modelAndView.addObject("casePropertyList", casePropertyList);
        modelAndView.addObject("caseInfoList", caseInfoList);
        modelAndView.addObject("pageInfo", pageInfo.updatePageInfo(totalCnt));
        modelAndView.setViewName("center/identifybook/listGenerated");

        return modelAndView;
    }

    @RequestMapping("/revertIdentifyBook.html")
    public ModelAndView revertIdentifyBook(HttpServletRequest request, LimsCaseInfoVO query, PageInfo pageInfo) throws ParseException {

        query = resetCaseInfoQuery(query);
        try {
            limsIdentifyBookService.generatReport(query.getEntity().getId());
            limsIdentifyBookNoticeService.deleteByCaseId(query.getEntity().getId());
        } catch (Exception ex) {
            logger.error("修改鉴定书状态为 已出 时错误！", ex);
        }

        List<DictItem> caseStatusList = dictService.selectDictItemListByType(Constants.DICT_TYPE_CASE_STATUS);
        List<DictItem> caseTypeList = dictService.selectDictItemListByType(Constants.DICT_TPYE_CASE_TYPE);
        List<DictItem> casePropertyList = dictService.selectDictItemListByType(Constants.DICT_TPYE_CASE_PROPERTY);

        query.setCaseStatusList(Arrays.asList(Constants.CASE_INFO_STATUS_ACCEPTED, Constants.CASE_INFO_STATUS_FINISHED));
        query.setAdditionalFlag(Constants.FLAG_FALSE);
        query.setIdentifyBookStatus(Constants.IDENTIFY_BOOK_SIGNED);
        query.setOrderByClause(" caseNo1 DESC, caseNo2 DESC");
        List<LimsCaseInfoVO> caseInfoList = limsCaseInfoService.selectVOIdentifyBookList(query, pageInfo);
        int totalCnt = limsCaseInfoService.selectVOIdentifyBookCount(query);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("caseStatusList", caseStatusList);
        modelAndView.addObject("caseTypeList", caseTypeList);
        modelAndView.addObject("caseInfoQuery", query);
        modelAndView.addObject("casePropertyList", casePropertyList);
        modelAndView.addObject("caseInfoList", caseInfoList);
        modelAndView.addObject("pageInfo", pageInfo.updatePageInfo(totalCnt));
        modelAndView.setViewName("center/identifybook/listAppraisal");

        return modelAndView;
    }

    @RequestMapping("/receiveIdentifyBook.html")
    public ModelAndView receiveIdentifyBook(HttpServletRequest request, LimsCaseInfoVO query, PageInfo pageInfo) throws ParseException {
        query = resetCaseInfoQuery(query);

        try {
            limsIdentifyBookService.updateReceiveStatus(query.getEntity().getId());
            limsCaseInfoService.updateCaseStatus(query.getEntity().getId());
        } catch (Exception e) {
            logger.error("领取失败!");
        }

        LimsCaseInfo limsCaseInfo = limsCaseInfoService.selectById(query.getEntity().getId());

        List<DictItem> caseStatusList = dictService.selectDictItemListByType(Constants.DICT_TYPE_CASE_STATUS);
        List<DictItem> caseTypeList = dictService.selectDictItemListByType(Constants.DICT_TPYE_CASE_TYPE);
        List<DictItem> casePropertyList = dictService.selectDictItemListByType(Constants.DICT_TPYE_CASE_PROPERTY);

        query.setCaseStatusList(Arrays.asList(Constants.CASE_INFO_STATUS_ACCEPTED, Constants.CASE_INFO_STATUS_FINISHED));
        query.setAdditionalFlag(Constants.FLAG_FALSE);
        query.setIdentifyBookStatus(Constants.IDENTIFY_BOOK_FETCHED);
        query.setOrderByClause(" caseNo1 DESC, caseNo2 DESC");
        /*query.getEntity().setCaseName(limsCaseInfo.getCaseName());*/
        query.getEntity().setCaseNo(limsCaseInfo.getCaseNo());
        List<LimsCaseInfoVO> caseInfoList = limsCaseInfoService.selectVOIdentifyBookList(query, pageInfo);
        int totalCnt = limsCaseInfoService.selectVOIdentifyBookCount(query);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("caseStatusList", caseStatusList);
        modelAndView.addObject("caseTypeList", caseTypeList);
        modelAndView.addObject("caseInfoQuery", query);
        modelAndView.addObject("casePropertyList", casePropertyList);
        modelAndView.addObject("caseInfoList", caseInfoList);
        modelAndView.addObject("limsCaseInfo", limsCaseInfo);
        modelAndView.addObject("pageInfo", pageInfo.updatePageInfo(totalCnt));
        modelAndView.setViewName("center/identifybook/listFetched");

        return modelAndView;

    }

    @RequestMapping("/03.html")
    public ModelAndView listSigned(HttpServletRequest request, LimsCaseInfoVO query, PageInfo pageInfo) throws ParseException {
        List<DictItem> caseStatusList = dictService.selectDictItemListByType(Constants.DICT_TYPE_CASE_STATUS);
        List<DictItem> caseTypeList = dictService.selectDictItemListByType(Constants.DICT_TPYE_CASE_TYPE);
        List<DictItem> casePropertyList = dictService.selectDictItemListByType(Constants.DICT_TPYE_CASE_PROPERTY);

        query = resetCaseInfoQuery(query);
        query.setCaseStatusList(Arrays.asList(Constants.CASE_INFO_STATUS_ACCEPTED, Constants.CASE_INFO_STATUS_FINISHED));
        query.setAdditionalFlag(Constants.FLAG_FALSE);
        query.setIdentifyBookStatus(Constants.IDENTIFY_BOOK_SIGNED);
        query.setOrderByClause(" caseNo1 DESC, caseNo2 DESC");
        List<LimsCaseInfoVO> caseInfoList = limsCaseInfoService.selectVOIdentifyBookList(query, pageInfo);
        int totalCnt = limsCaseInfoService.selectVOIdentifyBookCount(query);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("caseStatusList", caseStatusList);
        modelAndView.addObject("caseTypeList", caseTypeList);
        modelAndView.addObject("caseInfoQuery", query);
        modelAndView.addObject("casePropertyList", casePropertyList);
        modelAndView.addObject("caseInfoList", caseInfoList);
        modelAndView.addObject("pageInfo", pageInfo.updatePageInfo(totalCnt));
        modelAndView.setViewName("center/identifybook/listSigned");

        return modelAndView;
    }


    @RequestMapping("/04.html")
    public ModelAndView listFetched(HttpServletRequest request, LimsCaseInfoVO query, PageInfo pageInfo) throws ParseException {
        List<DictItem> caseStatusList = dictService.selectDictItemListByType(Constants.DICT_TYPE_CASE_STATUS);
        List<DictItem> caseTypeList = dictService.selectDictItemListByType(Constants.DICT_TPYE_CASE_TYPE);
        List<DictItem> casePropertyList = dictService.selectDictItemListByType(Constants.DICT_TPYE_CASE_PROPERTY);

        query = resetCaseInfoQuery(query);
        query.setCaseStatusList(Arrays.asList(Constants.CASE_INFO_STATUS_ACCEPTED, Constants.CASE_INFO_STATUS_FINISHED));
        query.setAdditionalFlag(Constants.FLAG_FALSE);
        query.setIdentifyBookStatus(Constants.IDENTIFY_BOOK_FETCHED);
        query.setOrderByClause(" caseNo1 DESC, caseNo2 DESC");
        List<LimsCaseInfoVO> caseInfoList = limsCaseInfoService.selectVOIdentifyBookList(query, pageInfo);
        int totalCnt = limsCaseInfoService.selectVOIdentifyBookCount(query);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("caseStatusList", caseStatusList);
        modelAndView.addObject("caseTypeList", caseTypeList);
        modelAndView.addObject("caseInfoQuery", query);
        modelAndView.addObject("casePropertyList", casePropertyList);
        modelAndView.addObject("caseInfoList", caseInfoList);
        modelAndView.addObject("pageInfo", pageInfo.updatePageInfo(totalCnt));
        modelAndView.setViewName("center/identifybook/listFetched");

        return modelAndView;
    }

    private List<LimsSampleInfo> getSampleInfoList(List<LimsSampleInfo> sampleInfoList) {

        List<LimsExtractRecordSample> limsExtractRecordSampleList = null;
        LimsExtractRecordSample limsExtractRecordSample = new LimsExtractRecordSample();
        String extractPosition = "";
        String changeMethod = "";
        String otherChangeMethod = "";
 /*       for (LimsSampleInfo s : sampleInfoList) {
            limsExtractRecordSampleList = limsExtractRecordService.selectListBySampleId(s.getId());

            if (ListUtils.isNotNullAndEmptyList(limsExtractRecordSampleList)) {
                limsExtractRecordSample = limsExtractRecordSampleList.get(0);
            }

            if (StringUtils.isNotBlank(limsExtractRecordSample.getOtherChangeMethod())) {
                otherChangeMethod = limsExtractRecordSample.getOtherChangeMethod();
                if (StringUtils.isBlank(otherChangeMethod)) {
                    otherChangeMethod = "";
                }

                extractPosition = limsExtractRecordSample.getExtractPosition();
                if (StringUtils.isBlank(extractPosition)) {
                    extractPosition = "";
                }

                s.setExtractPositionAndChangeMethod(otherChangeMethod + extractPosition);
            }else {
                changeMethod = limsExtractRecordSample.getChangeMethod();
                if (StringUtils.isBlank(changeMethod)) {
                    changeMethod = "";
                }

                extractPosition = limsExtractRecordSample.getExtractPosition();
                if (StringUtils.isBlank(extractPosition)) {
                    extractPosition = "";
                }

                s.setExtractPositionAndChangeMethod(changeMethod + extractPosition);

                if (StringUtils.isBlank(s.getExtractPositionAndChangeMethod())) {
                    s.setExtractPositionAndChangeMethod("NO");
                }
            }
        }*/


        for (int i = 0; i < sampleInfoList.size(); i++) {
            limsExtractRecordSampleList = limsExtractRecordService.selectListBySampleId(sampleInfoList.get(i).getId());

            if (ListUtils.isNotNullAndEmptyList(limsExtractRecordSampleList)) {
                limsExtractRecordSample = limsExtractRecordSampleList.get(0);
            }

            if (StringUtils.isNotBlank(limsExtractRecordSample.getOtherChangeMethod())) {
                otherChangeMethod = limsExtractRecordSample.getOtherChangeMethod();
                if (StringUtils.isBlank(otherChangeMethod)) {
                    otherChangeMethod = "";
                }

                extractPosition = limsExtractRecordSample.getExtractPosition();
                if (StringUtils.isBlank(extractPosition)) {
                    extractPosition = "";
                }

                sampleInfoList.get(i).setExtractPositionAndChangeMethod(otherChangeMethod + extractPosition);
            } else {
                changeMethod = limsExtractRecordSample.getChangeMethod();
                if (StringUtils.isBlank(changeMethod)) {
                    changeMethod = "";
                }

                extractPosition = limsExtractRecordSample.getExtractPosition();
                if (StringUtils.isBlank(extractPosition)) {
                    extractPosition = "";
                }

                sampleInfoList.get(i).setExtractPositionAndChangeMethod(changeMethod + extractPosition);

                if (StringUtils.isBlank(sampleInfoList.get(i).getExtractPositionAndChangeMethod())) {
                    sampleInfoList.get(i).setExtractPositionAndChangeMethod("NO");
                }
            }
            /*String sampleNo = sampleInfoList.get(i).getSampleNo();//取出sampleNo 并循环对比
            //如果是拆分后的则进行对比
            if(!StringUtils.isBlank(sampleNo)&&sampleNo.length()>17){
                String sampleNoSub =sampleNo.substring(0,17);//进行截取
                for (int j=0;j<sampleInfoList.size();j++) {
                    String sampleNo1 =sampleInfoList.get(j).getSampleNo();
                    if(sampleNoSub.equals(sampleNo1)){
                        sampleInfoList.get(j).setSampleNo(sampleNo1+"/"+sampleNo);
                        sampleInfoList.remove(i);
                    }
                }
            }*/
        }
        return sampleInfoList;
    }

    private String getNumberOfSample(String sampleNo) {
        String newSampleNo = null;
        if (sampleNo.contains("-")) {
            newSampleNo = sampleNo.substring(sampleNo.lastIndexOf("-") + 1);
        } else {
            newSampleNo = sampleNo;
        }

        return newSampleNo;
    }

    private String getGeneratePath(LimsCaseInfo caseInfo, String filename) {
        String writeFilePath = SystemUtil.getSystemConfig().getProperty("identifyBookPath");

        File pathFile = new File(writeFilePath);
        if (!pathFile.exists()) {
            pathFile.mkdirs();
        }

        String rebuildPath = SystemUtil.getSystemConfig().getProperty("rebuildPath");
        if (!new File(rebuildPath).exists()) {
            new File(rebuildPath).mkdirs();
        }
        writeFilePath = makePathFile(writeFilePath + "\\" + rebuildPath + "\\" + caseInfo.getId());

        String writeFileName = (StringUtils.isBlank(caseInfo.getCaseNo()) ? "" : caseInfo.getCaseNo()) + filename;

        String writeFile = "";
        if (writeFilePath.endsWith("\\") || writeFilePath.endsWith("/")) {
            writeFile += writeFilePath + writeFileName;
        } else {
            writeFile = writeFilePath + System.getProperty("file.separator") + writeFileName;
        }

        LimsIdentifyBook identifyBook = new LimsIdentifyBook();
        identifyBook.setCaseId(caseInfo.getId());
        List<LimsIdentifyBook> identifyBookList = limsIdentifyBookService.selectIdentifyBookList(identifyBook);
        if (ListUtils.isNotNullAndEmptyList(identifyBookList)) {
            for (LimsIdentifyBook lib : identifyBookList) {
                lib.setFilePath(writeFile);
                updateIdentifyBookPath(lib);
            }
        }

        return writeFile;
    }

    /**
     * 验证目录是否存在，如果不存在，则创建对应目录。
     *
     * @param filePathName
     */
    private static String makePathFile(String filePathName) {

        File pathFile = new File(filePathName);
        if (!pathFile.exists()) {
            pathFile.mkdirs();
        }

        return pathFile + "\\";
    }

    public LimsCaseInfoVO resetCaseInfoQuery(LimsCaseInfoVO caseInfoVO) throws ParseException {
        if (StringUtils.isBlank(caseInfoVO.getEntity().getCaseName())) {
            caseInfoVO.getEntity().setCaseName(null);
        } else {
            caseInfoVO.getEntity().setCaseName(caseInfoVO.getEntity().getCaseName().trim());
        }

        if (StringUtils.isBlank(caseInfoVO.getEntity().getCaseXkNo())) {
            caseInfoVO.getEntity().setCaseXkNo(null);
        } else {
            caseInfoVO.getEntity().setCaseXkNo(caseInfoVO.getEntity().getCaseXkNo().trim());
        }

        if (StringUtils.isBlank(caseInfoVO.getEntity().getCaseStatus())) {
            caseInfoVO.getEntity().setCaseStatus(null);
        } else {
            caseInfoVO.getEntity().setCaseStatus(caseInfoVO.getEntity().getCaseStatus());
        }

        if (StringUtils.isBlank(caseInfoVO.getEntity().getCaseType())) {
            caseInfoVO.getEntity().setCaseType(null);
        } else {
            caseInfoVO.getEntity().setCaseType(caseInfoVO.getEntity().getCaseType());
        }

        if (StringUtils.isBlank(caseInfoVO.getEntity().getCaseProperty())) {
            caseInfoVO.getEntity().setCaseProperty(null);
        } else {
            caseInfoVO.getEntity().setCaseProperty(caseInfoVO.getEntity().getCaseProperty());
        }

        if (caseInfoVO.getAcceptDateStart() == null) {
            caseInfoVO.setAcceptDateStart(null);
        } else {
            caseInfoVO.setAcceptDateStart(caseInfoVO.getAcceptDateStart());
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String oldTime = null;
        Date newTime = null;

        if (caseInfoVO.getAcceptDateEnd() == null) {
            caseInfoVO.setAcceptDateEnd(null);
        } else {
            sdf.format(new Date());
            oldTime = sdf.format(caseInfoVO.getAcceptDateEnd());
            newTime = sd.parse(oldTime + " 23:59:59");
            caseInfoVO.setAcceptDateEnd(newTime);

        }

        if (StringUtils.isBlank(caseInfoVO.getEntity().getCaseNo())) {
            caseInfoVO.getEntity().setCaseNo(null);
        } else {
            caseInfoVO.getEntity().setCaseNo(caseInfoVO.getEntity().getCaseNo());
        }

        if (StringUtils.isBlank(caseInfoVO.getIdentifyBookStatus())) {
            caseInfoVO.setIdentifyBookStatus(null);
        } else {
            caseInfoVO.setIdentifyBookStatus(caseInfoVO.getIdentifyBookStatus());
        }

        return caseInfoVO;
    }

}
