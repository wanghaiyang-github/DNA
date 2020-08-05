package com.bazl.hslims.web.controller.center.examine;

import com.bazl.hslims.common.Constants;
import com.bazl.hslims.manager.PageInfo;
import com.bazl.hslims.manager.model.po.*;
import com.bazl.hslims.manager.model.vo.LimsCaseInfoVO;
import com.bazl.hslims.manager.services.center.CenterLoginService;
import com.bazl.hslims.manager.services.common.*;
import com.bazl.hslims.utils.DateUtils;
import com.bazl.hslims.utils.ListUtils;
import com.bazl.hslims.web.controller.BaseController;
import com.bazl.hslims.web.datamodel.PcrRecordDataModel;
import com.bazl.hslims.web.datamodel.PcrSystemParams;
import com.fasterxml.jackson.databind.ObjectMapper;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2017/1/4.
 */
@Controller
@RequestMapping("/center/pcr")
public class PcrController extends BaseController {

    @Autowired
    LimsCaseInfoService limsCaseInfoService;
    @Autowired
    LimsSampleInfoService limsSampleInfoService;
    @Autowired
    LimsPcrRecordService limsPcrRecordService;
    @Autowired
    LimsExtractRecordService limsExtractRecordService;
    @Autowired
    DictService dictService;
    @Autowired
    LabUserService labUserService;
    @Autowired
    DelegateOrgService delegateOrgService;
    @Autowired
    CenterLoginService centerLoginService;
    @Autowired
    EquipmentNameInfoService equipmentNameInfoService;
    @Autowired
    EquipmentInfoService equipmentInfoService;
    @Autowired
    PanelInfoService panelInfoService;

    @RequestMapping("/list.html")
    public ModelAndView list(HttpServletRequest request, LimsCaseInfoVO query, PageInfo pageInfo) throws ParseException {
        List<DelegateOrg> delegateOrgList = delegateOrgService.selectAll();
        /*  实验室人员 */
        List<LabUser> labUserList = labUserService.selectAll();

        query = resetCaseInfoQuery(query);
        query.getEntity().setCaseStatus(Constants.CASE_INFO_STATUS_ACCEPTED);
        query.setAdditionalFlag(Constants.FLAG_FALSE);
        query.setOrderByClause(" caseNo1 DESC, caseNo2 DESC");
        //List<LimsCaseInfoVO> caseInfoVOList = limsCaseInfoService.selectVOPaginationList(query, pageInfo);
        List<LimsCaseInfoVO> caseInfoVOList = limsCaseInfoService.selectVOAllList(query, pageInfo);

        for (LimsCaseInfoVO caseInfo : caseInfoVOList) {
            caseInfo.setPcrRecordCount(limsPcrRecordService.selectCountByCaseId(caseInfo.getEntity().getId()));
        }

        for (LimsCaseInfoVO caseInfo : caseInfoVOList) {
            String caseNo = caseInfo.getEntity().getCaseNo();
            String cno = null;
            if (caseNo.length() == 9) {
                cno = caseNo.substring(9);
            } else if (caseNo.length() == 11) {
                cno = caseNo.substring(9, 11);
            } else if (caseNo.length() == 12) {
                cno = caseNo.substring(9, 12);
            } else if (caseNo.length() == 13) {
                cno = caseNo.substring(9, 13);
            }
            if (cno != null) {
                /*int count = limsPcrRecordService.selectCountBySampleNo(cno);*/
                List<LimsPcrRecord> limsPcrRecordList = limsPcrRecordService.selectSampleNoByTaskId(cno);
                List<LimsPcrRecordSample> limsPcrRecordSampleList = limsPcrRecordService.selectListBySampleNo(cno);
                if (limsPcrRecordList.size() != 0) {
                    caseInfo.setAppPcrRecordCount(limsPcrRecordList.size());
                    caseInfo.setCno(cno);
                    for (LimsPcrRecordSample pcrRecordSample : limsPcrRecordSampleList) {
                        LimsPcrRecord limsPcrRecord = limsPcrRecordService.selectById(pcrRecordSample.getLimsPcrRecordId());
                        caseInfo.setTaskId(limsPcrRecord.getTaskId());
                        if (pcrRecordSample.getLimsPcrRecordId() != 0) {
                            caseInfo.setPcrRecordId(pcrRecordSample.getLimsPcrRecordId().toString());
                        }
                    }
                }
            }
        }


        //int totalCnt = limsCaseInfoService.selectVOCnt(query);
        int totalCnt = limsCaseInfoService.selectVOCnt(query);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("caseInfoQuery", query);
        modelAndView.addObject("caseInfoVOList", caseInfoVOList);
        modelAndView.addObject("pageInfo", pageInfo.updatePageInfo(totalCnt));
        modelAndView.addObject("delegateOrgList", delegateOrgList);
        modelAndView.addObject("labUserList", labUserList);
        modelAndView.setViewName("center/examine/pcrRecordList");
        return modelAndView;
    }

    @RequestMapping("/add.html")
    public ModelAndView add(HttpServletRequest request, Integer caseId) {
        //扩增相关字典
        ModelAndView modelAndView = initDict();

        PanelInfo panelInfo = new PanelInfo();
        List<PanelInfo> panelInfoList = panelInfoService.selectPanelInfoList(panelInfo);

        List<DictItem> pcrSystemList = dictService.selectDictItemListByType(Constants.DICT_TYPE_PCR_SYSTEM);
        DictItem pcrSystemDefault = pcrSystemList.get(0);

        LimsPcrRecord limsPcrRecord = new LimsPcrRecord();
        limsPcrRecord.setCaseId(caseId);
        LabUser labUser1 = centerLoginService.selectByLoginName(Constants.EXPERIMENTER_2);
        if (labUser1 != null) {
            limsPcrRecord.setPcrPersonId1(labUser1.getId());
            limsPcrRecord.setPcrPersonName1(labUser1.getUserName());
        }

        LabUser labUser2 = centerLoginService.selectByLoginName(Constants.EXPERIMENTER_1);
        if (labUser2 != null) {
            limsPcrRecord.setPcrPersonId2(labUser2.getId());
            limsPcrRecord.setPcrPersonName2(labUser2.getUserName());
        }

        long currentTimeMillis = System.currentTimeMillis();
        limsPcrRecord.setPcrStartDatetime(new Date(currentTimeMillis - (2 * 60 * 60 * 1000 + 30 * 60 * 1000)));   //间隔半个小时
        limsPcrRecord.setPcrEndDatetime(new Date(currentTimeMillis));
        limsPcrRecord.setPcrSystem(pcrSystemDefault.getDictCode());

        ObjectMapper jsonObjectMapper = new ObjectMapper();
        List<PcrSystemParams> pcrSystemParamsList = null;
        try {
            pcrSystemParamsList = jsonObjectMapper.readValue(pcrSystemDefault.getDictDesc(), jsonObjectMapper.getTypeFactory().constructCollectionType(List.class, PcrSystemParams.class));
        } catch (Exception ex) {
            logger.error("获取扩增体系默认的扩增比例参数错误！", ex);
            pcrSystemParamsList = new ArrayList<PcrSystemParams>();
        }
        List<LimsSampleInfo> sampleInfolist = limsSampleInfoService.selectAcceptedListByCaseId(caseId);
        List<LimsPcrRecordSample> limsPcrRecordSampleList = new ArrayList<>();
        LimsPcrRecordSample pcrRecordSample = null;
        for (int i = 0; i < sampleInfolist.size(); i++) {
            pcrRecordSample = new LimsPcrRecordSample();
            pcrRecordSample.setSampleId(sampleInfolist.get(i).getId());
            pcrRecordSample.setSampleNo(sampleInfolist.get(i).getSampleNo());
            pcrRecordSample.setSampleName(sampleInfolist.get(i).getSampleName());
            pcrRecordSample.setSampleTypeName(sampleInfolist.get(i).getSampleTypeName());
            pcrRecordSample.setExtractDatetime(sampleInfolist.get(i).getExtractDatetime());

            for (PcrSystemParams param : pcrSystemParamsList) {
                if (sampleInfolist.get(i).getSampleType().equals(param.getSampleType())
                        || param.getSampleType().contains(sampleInfolist.get(i).getSampleType())) {
                    pcrRecordSample.setPrimerUl(StringUtils.isBlank(param.getPrimerUl()) ? "" : param.getPrimerUl());
                    pcrRecordSample.setBufferUl(StringUtils.isBlank(param.getBufferUl()) ? "" : param.getBufferUl());
                    pcrRecordSample.setTemplateUl(StringUtils.isBlank(param.getTemplateUl()) ? "" : param.getTemplateUl());
                    pcrRecordSample.setDdwUl(StringUtils.isBlank(param.getDdwUl()) ? "" : param.getDdwUl());
                    break;
                }
            }

            pcrRecordSample.setStandardFlag(Constants.FLAG_FALSE);
            limsPcrRecordSampleList.add(pcrRecordSample);
        }

        //根据检材类型区分、微量检材（脱落细胞、骨骼/牙齿）模板为4，水为0
        for (LimsPcrRecordSample limsPcrRecordSample : limsPcrRecordSampleList) {
            if (limsPcrRecordSample.getSampleTypeName().equals("骨骼/牙齿") || limsPcrRecordSample.getSampleTypeName().equals("脱落细胞")) {
                limsPcrRecordSample.setTemplateUl("4");
                limsPcrRecordSample.setDdwUl("0");
            } else {
                limsPcrRecordSample.setTemplateUl("1");
                limsPcrRecordSample.setDdwUl("3");
            }
        }

        pcrRecordSample = null;
        for (String pcrStandardSample : Constants.PCR_STANDARD_SAMPLE_ARR) {
            pcrRecordSample = new LimsPcrRecordSample();
            pcrRecordSample.setSampleNo(pcrStandardSample);
            pcrRecordSample.setStandardFlag(Constants.FLAG_TRUE);
            //TODO 扩增比例参数

            pcrRecordSample.setPrimerUl("2");
            pcrRecordSample.setBufferUl("4");
            pcrRecordSample.setTemplateUl("1");
            pcrRecordSample.setDdwUl("3");
            if(pcrStandardSample.equals("Water")){
                pcrRecordSample.setSamplePosition("C03");
            }
            pcrRecordSample.setDdwUl("3");

            limsPcrRecordSampleList.add(pcrRecordSample);
        }

        LimsExtractRecord limsExtractRecord = new LimsExtractRecord();
        List<LimsExtractRecord> limsExtractRecordList = limsExtractRecordService.selectListByCaseId(caseId);
        if (ListUtils.isNotNullAndEmptyList(limsExtractRecordList)) {
            Date extractTime = limsExtractRecordList.get(0).getExtractDatetime();
            limsExtractRecord.setExtractDatetime(extractTime);
        }

        //孔位
        String[] positionArr = Constants.SYTABLE_POSTION_ARR_VER;
        LimsPcrRecordSample pcrRecord = null;
        int count = 0;
        for (int i = 0; i < limsPcrRecordSampleList.size(); i++) {
            if (positionArr[count].equals("C03") || positionArr[i].equals("C03")){
                continue;
            }
            if (i > 95) {
                count = (i % 95) - 1;
                limsPcrRecordSampleList.get(i).setSamplePosition(positionArr[count]);
            } else {
                limsPcrRecordSampleList.get(i).setSamplePosition(positionArr[i]);
            }

        }


        modelAndView.addObject("panelInfoList", panelInfoList);
        modelAndView.addObject("operateType", Constants.OPERATE_TYPE_ADD);
        modelAndView.addObject("limsPcrRecord", limsPcrRecord);
        modelAndView.addObject("limsExtractRecord", limsExtractRecord);
        modelAndView.addObject("limsPcrRecordSampleList", limsPcrRecordSampleList);
        modelAndView.addObject("samplePositionList", Constants.SYTABLE_POSTION_ARR_VER);
        modelAndView.setViewName("center/examine/pcrInfo");
        return modelAndView;
    }


    @RequestMapping("/viewPcrRecord.html")
    public ModelAndView viewPcrRecord(HttpServletRequest request, Integer caseId) {
        List<LimsPcrRecord> limsPcrRecordList = limsPcrRecordService.selectListByCaseId(caseId);

        //int extractRecordCount = limsPcrRecordService.selectCountByCaseId(caseId);
        if (limsPcrRecordList.size() == 1) {
            return viewOne(limsPcrRecordList.get(0));
        } else {
            return viewList(limsPcrRecordList, caseId);
        }
    }

    @RequestMapping("/viewAppPcrRecordDoc.html")
    public ModelAndView viewAppPcrRecord(HttpServletRequest request, Integer caseId, String cno) {
        List<LimsPcrRecord> limsPcrRecordList = limsPcrRecordService.selectSampleNoByTaskId(cno);

        LimsExtractRecord limsExtractRecord = new LimsExtractRecord();
        List<LimsExtractRecord> limsExtractRecordList = limsExtractRecordService.selectListByCaseId(caseId);
        if (ListUtils.isNotNullAndEmptyList(limsExtractRecordList)) {
            Date extractTime = limsExtractRecordList.get(0).getExtractDatetime();
            limsExtractRecord.setExtractDatetime(extractTime);
        }

        List<EquipmentInfo> equipmentInfoList = equipmentInfoService.selectAllList();
        List<DictItem> pcrReagentKitList = dictService.selectDictItemListByType(Constants.DICT_TYPE_PCR_REAGENT_KIT);
        List<DictItem> pcrSystemList = dictService.selectDictItemListByType(Constants.DICT_TYPE_PCR_SYSTEM);

        for (LimsPcrRecord limsPcrRecord : limsPcrRecordList) {
            for (EquipmentInfo equipmentInfo : equipmentInfoList) {
                if (limsPcrRecord.getPcrProgram().equals(equipmentInfo.getEquipmentNameId().toString())) {
                    limsPcrRecord.setPcrProgram(equipmentInfo.getEquipmentName());
                }
                if (limsPcrRecord.getPcrInstrument().equals(equipmentInfo.getEquipmentNameId().toString())) {
                    limsPcrRecord.setPcrInstrument(equipmentInfo.getEquipmentName());
                }
            }
            for (DictItem pcrReagentKit : pcrReagentKitList) {
                if (limsPcrRecord.getPcrReagent().equals(pcrReagentKit.getDictCode())) {
                    limsPcrRecord.setPcrReagent(pcrReagentKit.getDictName());
                }
            }
            for (DictItem pcrSystem : pcrSystemList) {
                if (limsPcrRecord.getPcrSystem().equals(pcrSystem.getDictCode())) {
                    limsPcrRecord.setPcrSystem(pcrSystem.getDictName());
                }
            }
        }


        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("operateType", Constants.OPERATE_TYPE_EDIT);
        modelAndView.addObject("caseId", caseId);
        modelAndView.addObject("limsExtractRecord", limsExtractRecord);
        modelAndView.addObject("limsPcrRecordList", limsPcrRecordList);
        modelAndView.setViewName("center/examine/viewPcrList");
        return modelAndView;
    }

    private ModelAndView viewOne(LimsPcrRecord limsPcrRecord) {
        //扩增相关字典
        ModelAndView modelAndView = initDict();
        List<LimsPcrRecordSample> limsPcrRecordSampleList = limsPcrRecordService.selectSampleListByPcrRecordId(limsPcrRecord.getId());

        LimsExtractRecord limsExtractRecord = new LimsExtractRecord();
        List<LimsExtractRecord> limsExtractRecordList = limsExtractRecordService.selectListByCaseId(limsPcrRecord.getCaseId());
        if (ListUtils.isNotNullAndEmptyList(limsExtractRecordList)) {
            Date extractTime = limsExtractRecordList.get(0).getExtractDatetime();
            limsExtractRecord.setExtractDatetime(extractTime);
        }

        PanelInfo panelInfo = new PanelInfo();
        List<PanelInfo> panelInfoList = panelInfoService.selectPanelInfoList(panelInfo);

        modelAndView.addObject("panelInfoList", panelInfoList);
        modelAndView.addObject("operateType", Constants.OPERATE_TYPE_EDIT);
        modelAndView.addObject("caseId", limsPcrRecord.getCaseId());
        modelAndView.addObject("limsPcrRecord", limsPcrRecord);
        modelAndView.addObject("limsExtractRecord", limsExtractRecord);
        modelAndView.addObject("limsPcrRecordSampleList", limsPcrRecordSampleList);
        modelAndView.addObject("samplePositionList", Constants.SYTABLE_POSTION_ARR_VER);
        modelAndView.setViewName("center/examine/pcrInfo");
        return modelAndView;
    }

    private ModelAndView viewList(List<LimsPcrRecord> limsPcrRecordList, Integer caseId) {

        LimsExtractRecord limsExtractRecord = new LimsExtractRecord();
        List<LimsExtractRecord> limsExtractRecordList = limsExtractRecordService.selectListByCaseId(caseId);
        if (ListUtils.isNotNullAndEmptyList(limsExtractRecordList)) {
            Date extractTime = limsExtractRecordList.get(0).getExtractDatetime();
            limsExtractRecord.setExtractDatetime(extractTime);
        }

        List<EquipmentInfo> equipmentInfoList = equipmentInfoService.selectAllList();
        List<DictItem> pcrReagentKitList = dictService.selectDictItemListByType(Constants.DICT_TYPE_PCR_REAGENT_KIT);
        List<DictItem> pcrSystemList = dictService.selectDictItemListByType(Constants.DICT_TYPE_PCR_SYSTEM);

        for (LimsPcrRecord limsPcrRecord : limsPcrRecordList) {
            for (EquipmentInfo equipmentInfo : equipmentInfoList) {
                if (limsPcrRecord.getPcrProgram().equals(equipmentInfo.getEquipmentNameId().toString())) {
                    limsPcrRecord.setPcrProgram(equipmentInfo.getEquipmentName());
                }
                if (limsPcrRecord.getPcrInstrument().equals(equipmentInfo.getEquipmentNameId().toString())) {
                    limsPcrRecord.setPcrInstrument(equipmentInfo.getEquipmentName());
                }
            }
            for (DictItem pcrReagentKit : pcrReagentKitList) {
                if (limsPcrRecord.getPcrReagent().equals(pcrReagentKit.getDictCode())) {
                    limsPcrRecord.setPcrReagent(pcrReagentKit.getDictName());
                }
            }
            for (DictItem pcrSystem : pcrSystemList) {
                if (limsPcrRecord.getPcrSystem().equals(pcrSystem.getDictCode())) {
                    limsPcrRecord.setPcrSystem(pcrSystem.getDictName());
                }
            }
        }
        PanelInfo panelInfo = new PanelInfo();
        List<PanelInfo> panelInfoList = panelInfoService.selectPanelInfoList(panelInfo);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("panelInfoList", panelInfoList);
        modelAndView.addObject("operateType", Constants.OPERATE_TYPE_EDIT);
        modelAndView.addObject("caseId", caseId);
        modelAndView.addObject("limsExtractRecord", limsExtractRecord);
        modelAndView.addObject("limsPcrRecordList", limsPcrRecordList);
        modelAndView.addObject("samplePositionList", Constants.SYTABLE_POSTION_ARR_VER);
        modelAndView.setViewName("center/examine/viewPcrList");
        return modelAndView;
    }


    @RequestMapping("/edit.html")
    public ModelAndView edit(HttpServletRequest request, Integer pcrRecordId) {
        //扩增相关字典
        ModelAndView modelAndView = initDict();

        LimsPcrRecord limsPcrRecord = limsPcrRecordService.selectById(pcrRecordId);
        List<LimsPcrRecordSample> limsPcrRecordSampleList = limsPcrRecordService.selectSampleListByPcrRecordId(pcrRecordId);
        PanelInfo panelInfo = new PanelInfo();
        List<PanelInfo> panelInfoList = panelInfoService.selectPanelInfoList(panelInfo);

        modelAndView.addObject("operateType", Constants.OPERATE_TYPE_EDIT);
        modelAndView.addObject("panelInfoList", panelInfoList);
        //modelAndView.addObject("extractMethodList", extractMethodList);
        modelAndView.addObject("limsPcrRecord", limsPcrRecord);
        modelAndView.addObject("limsPcrRecordSampleList", limsPcrRecordSampleList);
        modelAndView.addObject("samplePositionList", Constants.SYTABLE_POSTION_ARR_VER);
        modelAndView.setViewName("center/examine/pcrInfo");
        return modelAndView;
    }


    private ModelAndView initDict() {
        ModelAndView modelAndView = new ModelAndView();

        /*  字典 */
        List<DictItem> pcrProgramList = dictService.selectDictItemListByType(Constants.DICT_TYPE_PCR_PROGRAM_NO);
        List<DictItem> pcrReagentKitList = dictService.selectDictItemListByType(Constants.DICT_TYPE_PCR_REAGENT_KIT);
        List<DictItem> pcrSystemList = dictService.selectDictItemListByType(Constants.DICT_TYPE_PCR_SYSTEM);
//        List<DictItem> pcrInstrumentList = dictService.selectDictItemListByType(Constants.DICT_TYPE_PCR_INSTRUMENT);
        //扩增仪
        List<EquipmentNameInfo> pcrInstrumentList = equipmentNameInfoService.selectEquipmentNameInfoListByTypeNo(Constants.EQUIPMENT_TYPE_INFO_KZY);
        //超净台
        List<EquipmentNameInfo> pcrInstrumentProgramList = equipmentNameInfoService.selectEquipmentNameInfoListByTypeNo(Constants.EQUIPMENT_TYPE_PCR_PROGRAM_NO);

        modelAndView.addObject("pcrProgramList", pcrProgramList);
        modelAndView.addObject("pcrReagentKitList", pcrReagentKitList);
        modelAndView.addObject("pcrSystemList", pcrSystemList);
        modelAndView.addObject("pcrInstrumentProgramList", pcrInstrumentProgramList);
        modelAndView.addObject("pcrInstrumentList", pcrInstrumentList);

        /*  实验室人员 */
        List<LabUser> labUserList = labUserService.selectAll();
        modelAndView.addObject("labUserList", labUserList);

        return modelAndView;
    }


    @RequestMapping("/delByRecordId.html")
    @ResponseBody
    public Map<String, Object> delByRecordId(HttpServletRequest request, Integer pcrRecordId) {

        limsPcrRecordService.deleteById(pcrRecordId);

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("success", true);
        return result;
    }


    @RequestMapping("/delByCaseId.html")
    @ResponseBody
    public Map<String, Object> delByCaseId(HttpServletRequest request, Integer caseId) {
        List<LimsPcrRecord> recordList = limsPcrRecordService.selectListByCaseId(caseId);
        for (LimsPcrRecord record : recordList) {
            limsPcrRecordService.deleteById(record.getId());
        }

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("success", true);
        return result;
    }


    @RequestMapping(value = "/saveRecord.html", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> saveRecord(HttpServletRequest request, @RequestBody PcrRecordDataModel pcrRecordDataModel, String operateType) {

        Integer pcrRecordId = null;

        if (Constants.OPERATE_TYPE_ADD.equals(operateType))
            pcrRecordId = limsPcrRecordService.addRecord(pcrRecordDataModel);
        else
            pcrRecordId = limsPcrRecordService.updateRecord(pcrRecordDataModel);

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("success", true);
        result.put("pcrRecordId", pcrRecordId);
        return result;
    }

    @RequestMapping("/pcrDoc.html")
    public void generateAndDownloadExtractDoc(HttpServletRequest request, HttpServletResponse response,
                                              Integer pcrRecordId, Integer caseId) {
        LimsPcrRecord limsPcrRecord = null;

        if (pcrRecordId != null && pcrRecordId != 0) {
            limsPcrRecord = limsPcrRecordService.selectById(pcrRecordId);
        }

        if (caseId != null && caseId != 0) {
            List<LimsPcrRecord> recordList = limsPcrRecordService.selectListByCaseId(caseId);
            if (recordList.size() > 0) {
                limsPcrRecord = recordList.get(0);
            }
        }

        List<LimsPcrRecordSample> sampleInfoList = limsPcrRecordService.selectSampleListByPcrRecordId(limsPcrRecord.getId());

        LimsCaseInfo caseInfo = new LimsCaseInfo();

        if (limsPcrRecord.getCaseId() != null) {
            caseInfo = limsCaseInfoService.selectById(limsPcrRecord.getCaseId());
        } else {
            if (caseId != null) {
                caseInfo = limsCaseInfoService.selectById(caseId);

            }
        }

        String caseNo = caseInfo.getCaseNo();

        Map<String, Object> params = new HashMap<String, Object>();
        try {
            if (caseNo == null || caseNo == "") {
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
        } catch (Exception ex) {
            logger.error("获取扩增任务号错误", ex);
            params.put("year", "");
            params.put("no", "");
        }

        params.put("pcrProgram", "其他");
        if (StringUtils.isNotBlank(limsPcrRecord.getPcrProgram())) {
            //超净台
            List<EquipmentInfo> equipmentInfoList = equipmentInfoService.selectAllList();
            for (EquipmentInfo equipmentInfo : equipmentInfoList) {
                if (equipmentInfo.getEquipmentNameId().toString().equals(limsPcrRecord.getPcrProgram())) {
                    params.put("pcrProgram", equipmentInfo.getEquipmentNo() + "号超净台");
                    break;
                }
            }
        }

        params.put("pcrInstrument", "其他");
        if (StringUtils.isNotBlank(limsPcrRecord.getPcrInstrument())) {
            List<EquipmentNameInfo> pcrInstrumentList = equipmentNameInfoService.selectEquipmentNameInfoListByTypeNo(Constants.EQUIPMENT_TYPE_INFO_KZY);
            List<EquipmentInfo> equipmentInfoList = equipmentInfoService.selectAllList();
            for (EquipmentNameInfo ei : pcrInstrumentList) {
                for (EquipmentInfo equipmentInfo : equipmentInfoList) {
                    if (equipmentInfo.getEquipmentNameId().toString().equals(limsPcrRecord.getPcrInstrument()) && ei.getId().toString().equals(limsPcrRecord.getPcrInstrument())) {
                        params.put("pcrInstrument", equipmentInfo.getEquipmentNo() + "号" + ei.getEquipmentName());
                        break;
                    }

                }
            }
        }

        params.put("pcrReagent", "其他");
        if (StringUtils.isNotBlank(limsPcrRecord.getPcrReagent())) {
            PanelInfo panelInfo = new PanelInfo();
            List<PanelInfo> panelInfoList = panelInfoService.selectPanelInfoList(panelInfo);
            for (PanelInfo di : panelInfoList) {
                if (di.getId().toString().equals(limsPcrRecord.getPcrReagent())) {
                    params.put("pcrReagent", di.getPanelName());
                    break;
                }
            }
        }

        params.put("pcrSystem", "其他");
        if (StringUtils.isNotBlank(limsPcrRecord.getPcrSystem())) {
            List<DictItem> pcrSystemList = dictService.selectDictItemListByType(Constants.DICT_TYPE_PCR_SYSTEM);
            for (DictItem di : pcrSystemList) {
                if (di.getDictCode().equals(limsPcrRecord.getPcrSystem())) {
                    params.put("pcrSystem", di.getDictName());
                    break;
                }
            }
        }

        params.put("pcrStartDatetime", limsPcrRecord.getPcrStartDatetime() == null ? "" : DateUtils.dateToString(limsPcrRecord.getPcrStartDatetime(), "yyyy年MM月dd日 HH:mm"));
        params.put("pcrEndDatetime", limsPcrRecord.getPcrEndDatetime() == null ? "" : DateUtils.dateToString(limsPcrRecord.getPcrEndDatetime(), "yyyy年MM月dd日 HH:mm"));
        params.put("pcrPerson", ((StringUtils.isBlank(limsPcrRecord.getPcrPersonName1()) ? "" : limsPcrRecord.getPcrPersonName1()))
                + "  " + (StringUtils.isBlank(limsPcrRecord.getPcrPersonName2()) ? "" : limsPcrRecord.getPcrPersonName2())
        );

        String oldSample = null;
        for (LimsPcrRecordSample lprs : sampleInfoList) {
            if (lprs.getSamplePosition() == null) {
                oldSample = "1";
                break;
            } else {
                oldSample = "2";
                break;
            }
        }

        if (oldSample.equals("2")) {
            for (String pos : Constants.SYTABLE_POSTION_ARR) {
                params.put(pos, "");
                for (LimsPcrRecordSample lprs : sampleInfoList) {
                    if (StringUtils.isNotBlank(lprs.getSamplePosition())
                            && pos.equals(lprs.getSamplePosition().trim().toUpperCase()) && lprs.getTemplateUl().equals("1")) {
                        params.put(pos, lprs.getSampleNo() + " 模板1ul");
                        break;
                    } else if (lprs.getTemplateUl().equals("4") && pos.equals(lprs.getSamplePosition().trim().toUpperCase())) {
                        params.put(pos, lprs.getSampleNo() + " 模板4ul");
                        break;
                    }
                    if (pos.equals("H12")) {
                        params.put(pos, "9947" + " 模板1ul");
                    }
                }
            }
        }else {
            if (sampleInfoList.size() <= 18) {
                int num = 18 - sampleInfoList.size();
                for (int i = 0; i < num; i++) {
                    LimsPcrRecordSample tmp = new LimsPcrRecordSample();
                    tmp.setSampleNo("");
                    tmp.setPrimerUl("");
                    tmp.setBufferUl("");
                    tmp.setTemplateUl("");
                    tmp.setDdwUl("");
                    sampleInfoList.add(tmp);
                }

                params.put("pcrRecordSampleList1", sampleInfoList);
            } else {
                int sampleLength = sampleInfoList.size();
                int loopCount = (sampleLength % 18 == 0) ? (sampleLength / 18) : (sampleLength / 18 + 1);
                int startIdx = 0;
                int rows = 18;
                List<LimsPcrRecordSample> samplePageList = null;
                for (int i = 1; i <= loopCount; i++) {
                    if (rows >= sampleLength) {
                        rows = sampleLength;
                    }

                    samplePageList = new ArrayList<>();
                    samplePageList.addAll(sampleInfoList.subList(startIdx, rows));
                    if (i == loopCount && rows < loopCount * 12) {
                        int num = 18 - samplePageList.size();
                        for (int j = 0; j < num; j++) {
                            LimsPcrRecordSample tmp = new LimsPcrRecordSample();
                            tmp.setSampleNo("");
                            tmp.setPrimerUl("");
                            tmp.setBufferUl("");
                            tmp.setTemplateUl("");
                            tmp.setDdwUl("");
                            samplePageList.add(tmp);
                        }
                    }

                    params.put("pcrRecordSampleList" + i, samplePageList);

                    startIdx += 18;
                    rows += 18;
                }
            }
        }

        try {
            Configuration cfg = new Configuration();
            cfg.setDefaultEncoding("UTF-8");
            ServletContext servletContext = request.getSession().getServletContext();
            cfg.setServletContextForTemplateLoading(servletContext, "/templates");
            //获取模板
            Template tmp  = null;
            if (oldSample.equals("2")) {
               tmp = cfg.getTemplate("appPcrRecord.ftl", "UTF-8");
            }else {
                tmp = cfg.getTemplate("pcrRecord.ftl", "UTF-8");
            }

            String filename = "-扩增记录表" + DateUtils.dateToString(new Date(), "yyyyMMddHHmmss") + ".doc";

            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + caseNo + new String(filename.getBytes("GBK"), "ISO-8859-1"));//文件头，导出的文件名
            response.setContentType("application/x-msdownload");//类型

            tmp.process(params, response.getWriter());
        } catch (Exception ex) {
            logger.error("", ex);
        }
    }

    @RequestMapping("/appPcrDoc.html")
    public void generateAndDownloadAppExtractDoc(HttpServletRequest request, HttpServletResponse response,
                                                 Integer taskId, Integer caseId) {
        LimsPcrRecord limsPcrRecord = null;

        if (taskId != null && taskId != 0) {
            limsPcrRecord = limsPcrRecordService.selectByTaskId(taskId);
        }

        if (caseId != null && caseId != 0) {
            List<LimsPcrRecord> recordList = limsPcrRecordService.selectListByCaseId(caseId);
            if (recordList.size() > 0) {
                limsPcrRecord = recordList.get(0);
            }
        }

        List<LimsPcrRecordSample> sampleInfoList = limsPcrRecordService.selectSampleListByPcrRecordId(limsPcrRecord.getId());
        /*LimsCaseInfo caseInfo = limsCaseInfoService.selectById(limsPcrRecord.getCaseId());*/

        LimsCaseInfo caseInfo = new LimsCaseInfo();

        if (limsPcrRecord.getCaseId() != null) {
            caseInfo = limsCaseInfoService.selectById(limsPcrRecord.getCaseId());
        } else {
            if (caseId != null) {
                caseInfo = limsCaseInfoService.selectById(caseId);

            }
        }
        List<LimsPcrRecordSample> pcrRecordSampleList = new ArrayList<>();
        for (LimsPcrRecordSample sampleInfo : sampleInfoList) {
            List<LimsSampleInfo> limsSampleInfoList = limsSampleInfoService.selectListByCaseId(caseId);
            for (LimsSampleInfo limsSampleInfo : limsSampleInfoList) {
                /*if (sampleInfo.getSampleNo().equals(limsSampleInfo.getSampleNo())) {*/
                pcrRecordSampleList.add(sampleInfo);
                /*}*/
            }
        }

        String caseNo = caseInfo.getCaseNo();

        Map<String, Object> params = new HashMap<String, Object>();
        //String pcrTaskNo = limsPcrRecord.getPcrTaskNo();
        try {
            if (caseNo == null || caseNo == "") {
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
        } catch (Exception ex) {
            logger.error("获取扩增任务号错误", ex);
            params.put("year", "");
            params.put("no", "");
        }

        params.put("pcrProgram", "其他");
        if (StringUtils.isNotBlank(limsPcrRecord.getPcrProgram())) {
            //List<DictItem> pcrProgramList = dictService.selectDictItemListByType(Constants.DICT_TYPE_PCR_PROGRAM_NO);
            //超净台
            List<EquipmentInfo> equipmentInfoList = equipmentInfoService.selectAllList();
            for (EquipmentInfo equipmentInfo : equipmentInfoList) {
                if (equipmentInfo.getEquipmentNameId().toString().equals(limsPcrRecord.getPcrProgram())) {
                    params.put("pcrProgram", equipmentInfo.getEquipmentNo() + "号超净台");
                    break;
                }
            }
        }

        params.put("pcrInstrument", "其他");
        if (StringUtils.isNotBlank(limsPcrRecord.getPcrInstrument())) {
//            List<DictItem> pcrInstrumentList = dictService.selectDictItemListByType(Constants.DICT_TYPE_PCR_INSTRUMENT);
            List<EquipmentNameInfo> pcrInstrumentList = equipmentNameInfoService.selectEquipmentNameInfoListByTypeNo(Constants.EQUIPMENT_TYPE_INFO_KZY);
            List<EquipmentInfo> equipmentInfoList = equipmentInfoService.selectAllList();
            for (EquipmentNameInfo ei : pcrInstrumentList) {
                for (EquipmentInfo equipmentInfo : equipmentInfoList) {
                    if (equipmentInfo.getEquipmentNameId().toString().equals(limsPcrRecord.getPcrInstrument()) && ei.getId().toString().equals(limsPcrRecord.getPcrInstrument())) {
                        params.put("pcrInstrument", equipmentInfo.getEquipmentNo() + "号" + ei.getEquipmentName());
                        break;
                    }

                }
            }
        }

        params.put("pcrReagent", "其他");
        if (StringUtils.isNotBlank(limsPcrRecord.getPcrReagent())) {
            //List<DictItem> pcrReagentKitList = dictService.selectDictItemListByType(Constants.DICT_TYPE_PCR_REAGENT_KIT);
            PanelInfo panelInfo = new PanelInfo();
            List<PanelInfo> panelInfoList = panelInfoService.selectPanelInfoList(panelInfo);
            for (PanelInfo di : panelInfoList) {
                if (di.getId().toString().equals(limsPcrRecord.getPcrReagent())) {
                    params.put("pcrReagent", di.getPanelName());
                    break;
                }
            }
        }

        params.put("pcrSystem", "其他");
        if (StringUtils.isNotBlank(limsPcrRecord.getPcrSystem())) {
            List<DictItem> pcrSystemList = dictService.selectDictItemListByType(Constants.DICT_TYPE_PCR_SYSTEM);
            for (DictItem di : pcrSystemList) {
                if (di.getDictCode().equals(limsPcrRecord.getPcrSystem())) {
                    params.put("pcrSystem", di.getDictName());
                    break;
                }
            }
        }

        params.put("pcrStartDatetime", limsPcrRecord.getPcrStartDatetime() == null ? "" : DateUtils.dateToString(limsPcrRecord.getPcrStartDatetime(), "yyyy年MM月dd日 HH:mm"));
        params.put("pcrEndDatetime", limsPcrRecord.getPcrEndDatetime() == null ? "" : DateUtils.dateToString(limsPcrRecord.getPcrEndDatetime(), "yyyy年MM月dd日 HH:mm"));
        params.put("pcrPerson", ((StringUtils.isBlank(limsPcrRecord.getPcrPersonName1()) ? "" : limsPcrRecord.getPcrPersonName1()))
                + "  " + (StringUtils.isBlank(limsPcrRecord.getPcrPersonName2()) ? "" : limsPcrRecord.getPcrPersonName2())
        );

        for (String pos : Constants.SYTABLE_POSTION_ARR) {
            params.put(pos, "");
            for (LimsPcrRecordSample lprs : pcrRecordSampleList) {
                if (StringUtils.isNotBlank(lprs.getSamplePosition())
                        && pos.equals(lprs.getSamplePosition().trim().toUpperCase()) && lprs.getTemplateUl().equals("3")) {
                    params.put(pos, lprs.getSampleNo() + " 模板1ul");
                    break;
                } else if (lprs.getTemplateUl().equals("0") && pos.equals(lprs.getSamplePosition().trim().toUpperCase())) {
                    params.put(pos, lprs.getSampleNo() + " 模板4ul");
                    break;
                }
                if (pos.equals("H12")) {
                    params.put(pos, "9947" + " 模板1ul");
                }
            }
        }

        /*if (pcrRecordSampleList.size() <= 18) {
            int num = 18 - pcrRecordSampleList.size();
            for (int i = 0; i < num; i++) {
                LimsPcrRecordSample tmp = new LimsPcrRecordSample();
                tmp.setSampleNo("");
                tmp.setPrimerUl("");
                tmp.setBufferUl("");
                tmp.setTemplateUl("");
                tmp.setDdwUl("");
                pcrRecordSampleList.add(tmp);
            }

            params.put("pcrRecordSampleList1", pcrRecordSampleList);
        } else {
            int sampleLength = pcrRecordSampleList.size();
            int loopCount = (sampleLength % 18 == 0) ? (sampleLength / 18) : (sampleLength / 18 + 1);
            int startIdx = 0;
            int rows = 18;
            List<LimsPcrRecordSample> samplePageList = null;
            for (int i = 1; i <= loopCount; i++) {
                if (rows >= sampleLength) {
                    rows = sampleLength;
                }

                samplePageList = new ArrayList<>();
                samplePageList.addAll(pcrRecordSampleList.subList(startIdx, rows));
                if (i == loopCount && rows < loopCount * 12) {
                    int num = 18 - samplePageList.size();
                    for (int j = 0; j < num; j++) {
                        LimsPcrRecordSample tmp = new LimsPcrRecordSample();
                        tmp.setSampleNo("");
                        tmp.setPrimerUl("");
                        tmp.setBufferUl("");
                        tmp.setTemplateUl("");
                        tmp.setDdwUl("");
                        samplePageList.add(tmp);
                    }
                }

                params.put("pcrRecordSampleList" + i, samplePageList);

                startIdx += 18;
                rows += 18;
            }
        }*/

        try {
            Configuration cfg = new Configuration();
            cfg.setDefaultEncoding("UTF-8");
            ServletContext servletContext = request.getSession().getServletContext();
            cfg.setServletContextForTemplateLoading(servletContext, "/templates");
            //获取模板
            Template tmp = cfg.getTemplate("appPcrRecord.ftl", "UTF-8");

            String filename = "-扩增记录表" + DateUtils.dateToString(new Date(), "yyyyMMddHHmmss") + ".doc";

            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + caseNo + new String(filename.getBytes("GBK"), "ISO-8859-1"));//文件头，导出的文件名
            response.setContentType("application/x-msdownload");//类型

            tmp.process(params, response.getWriter());
        } catch (Exception ex) {
            logger.error("", ex);
        }
    }

    public LimsCaseInfoVO resetCaseInfoQuery(LimsCaseInfoVO caseInfoVO) throws ParseException {
        if (StringUtils.isBlank(caseInfoVO.getEntity().getCaseNo())) {
            caseInfoVO.getEntity().setCaseNo(null);
        } else {
            caseInfoVO.getEntity().setCaseNo(caseInfoVO.getEntity().getCaseNo().trim());
        }
        if (StringUtils.isBlank(caseInfoVO.getEntity().getCaseXkNo())) {
            caseInfoVO.getEntity().setCaseXkNo(null);
        } else {
            caseInfoVO.getEntity().setCaseXkNo(caseInfoVO.getEntity().getCaseXkNo().trim());
        }
        if (StringUtils.isBlank(caseInfoVO.getEntity().getEntrustmentType())) {
            caseInfoVO.getEntity().setEntrustmentType(null);
        } else {
            caseInfoVO.getEntity().setEntrustmentType(caseInfoVO.getEntity().getEntrustmentType());
        }

        if (StringUtils.isBlank(caseInfoVO.getEntity().getCaseName())) {
            caseInfoVO.getEntity().setCaseName(null);
        } else {
            caseInfoVO.getEntity().setCaseName(caseInfoVO.getEntity().getCaseName());
        }

        if (StringUtils.isBlank(caseInfoVO.getEntity().getCaseProperty())) {
            caseInfoVO.getEntity().setCaseProperty(null);
        } else {
            caseInfoVO.getEntity().setCaseProperty(caseInfoVO.getEntity().getCaseProperty());
        }

        if (StringUtils.isBlank(caseInfoVO.getDelegateOrg())) {
            caseInfoVO.setDelegateOrg(null);
        } else {
            caseInfoVO.setDelegateOrg(caseInfoVO.getDelegateOrg());
        }

        if (StringUtils.isBlank(caseInfoVO.getDelegateAcceptor())) {
            caseInfoVO.setDelegateAcceptor(null);
        } else {
            caseInfoVO.setDelegateAcceptor(caseInfoVO.getDelegateAcceptor());
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
            oldTime = sdf.format(caseInfoVO.getAcceptDateEnd());
            newTime =sd.parse(oldTime + " 23:59:59");
            caseInfoVO.setAcceptDateEnd(newTime);
        }

        return caseInfoVO;
    }

}
