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
import com.bazl.hslims.web.datamodel.PcrSystemParams;
import com.bazl.hslims.web.datamodel.QuickExamineRecordModel;
import com.bazl.hslims.web.helper.ExamineHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2017/1/8.
 */

@Controller
@RequestMapping("/center/examine")
public class ExamineController extends BaseController {

    @Autowired
    LimsCaseInfoService limsCaseInfoService;
    @Autowired
    LimsSampleInfoService limsSampleInfoService;
    @Autowired
    LimsExtractRecordService limsExtractRecordService;
    @Autowired
    LimsPcrRecordService limsPcrRecordService;
    @Autowired
    LimsSyRecordService limsSyRecordService;
    @Autowired
    DictService dictService;
    @Autowired
    LabUserService labUserService;
    @Autowired
    DelegateOrgService delegateOrgService;
    @Autowired
    CenterLoginService centerLoginService;
    @Autowired
    QuickExamineRecordService quickExamineRecordService;
    @Autowired
    LimsQuickExamineRecordService limsQuickExamineRecordService;
    @Autowired
    EquipmentNameInfoService equipmentNameInfoService;
    @Autowired
    PanelInfoService panelInfoService;

    @RequestMapping(value="/querySample.html")
    @ResponseBody
    public Map<String, Object> querySample(HttpServletRequest request, String sampleNo, String caseNo) {

        List<LimsSampleInfo> sampleInfos = new ArrayList<>();
        if(StringUtils.isNotBlank(sampleNo))
            sampleInfos = limsSampleInfoService.selectListBySampleNo(sampleNo.trim().toUpperCase());
        else
            sampleInfos = limsSampleInfoService.selectListByCaseNo(caseNo.trim().toUpperCase());

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sampleCnt", ListUtils.isNullOrEmptyList(sampleInfos) ? 0 : sampleInfos.size());
        map.put("sampleInfoList", sampleInfos);
        return map;
    }

    @RequestMapping("/quickExamine.html")
    public ModelAndView list(HttpServletRequest request, LimsCaseInfoVO query, PageInfo pageInfo) throws ParseException {
        List<DelegateOrg> delegateOrgList = delegateOrgService.selectAll();
        /*  实验室人员 */
        List<LabUser> labUserList = labUserService.selectAll();

        query = resetCaseInfoQuery(query);
        query.getEntity().setCaseStatus(Constants.CASE_INFO_STATUS_ACCEPTED);
        query.setAdditionalFlag(Constants.FLAG_FALSE);
        query.setOrderByClause(" caseNo1 DESC, caseNo2 DESC");
        /*List<LimsCaseInfoVO> caseInfoVOList = limsCaseInfoService.selectVOPaginationList(query, pageInfo);*/
        List<LimsCaseInfoVO> caseInfoVOList = limsCaseInfoService.selectVOAllList(query, pageInfo);
        int examineRecordCount = 0;
        for (LimsCaseInfoVO caseInfo : caseInfoVOList) {
            examineRecordCount = limsQuickExamineRecordService.selectCountByCaseId(caseInfo.getEntity().getId());

            caseInfo.setQuickExamineCount(examineRecordCount);
        }
        int totalCnt = limsCaseInfoService.selectVOCnt(query);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("caseInfoQuery", query);
        modelAndView.addObject("caseInfoVOList", caseInfoVOList);
        modelAndView.addObject("pageInfo", pageInfo.updatePageInfo(totalCnt));
        modelAndView.addObject("delegateOrgList", delegateOrgList);
        modelAndView.addObject("labUserList", labUserList);
        modelAndView.setViewName("center/quickExamine/quickExamineList");
        return modelAndView;
    }

    @RequestMapping("/addQuickExamine.html")
    public ModelAndView addExtractQuickExamine(HttpServletRequest request, Integer caseId) {

        ModelAndView modelAndView = initDict();

        PanelInfo panelInfo = new PanelInfo();
        List<PanelInfo> panelInfoList = panelInfoService.selectPanelInfoList( panelInfo);

        LimsExtractRecord limsExtractRecord = new LimsExtractRecord();
        limsExtractRecord.setCaseId(caseId);
        LabUser labUser1 = centerLoginService.selectByLoginName(Constants.EXPERIMENTER_2);
        if(labUser1!=null){
            limsExtractRecord.setExtractPersonId1(labUser1.getId());
            limsExtractRecord.setExtractPersonName1(labUser1.getUserName());
        }

        LabUser labUser2 = centerLoginService.selectByLoginName(Constants.EXPERIMENTER_1);
        if(labUser2!=null){
            limsExtractRecord.setExtractPersonId2(labUser2.getId());
            limsExtractRecord.setExtractPersonName2(labUser2.getUserName());
        }

        limsExtractRecord.setExtractDatetime(DateUtils.getCurrentDate());

        List<DictItem> pcrSystemList = dictService.selectDictItemListByType(Constants.DICT_TYPE_PCR_SYSTEM);
        DictItem pcrSystemDefault = pcrSystemList.get(0);

        LimsPcrRecord limsPcrRecord = new LimsPcrRecord();
        limsPcrRecord.setCaseId(caseId);
        if (labUser1!=null) {
            limsPcrRecord.setPcrPersonId1(labUser1.getId());
            limsPcrRecord.setPcrPersonName1(labUser1.getUserName());
        }
        if(labUser2!=null){
            limsPcrRecord.setPcrPersonId2(labUser2.getId());
            limsPcrRecord.setPcrPersonName2(labUser2.getUserName());
        }

        long currentTimeMillis = System.currentTimeMillis();
        limsPcrRecord.setPcrStartDatetime(new Date(currentTimeMillis - (2*60*60*1000 + 30*60*1000)));   //间隔半个小时
        limsPcrRecord.setPcrEndDatetime(new Date(currentTimeMillis));
        limsPcrRecord.setPcrSystem(pcrSystemDefault.getDictCode());

        ObjectMapper jsonObjectMapper = new ObjectMapper();
        List<PcrSystemParams> pcrSystemParamsList = null;
        try {
            pcrSystemParamsList = jsonObjectMapper.readValue(pcrSystemDefault.getDictDesc(), jsonObjectMapper.getTypeFactory().constructCollectionType(List.class, PcrSystemParams.class));
        }catch(Exception ex){
            logger.error("获取扩增体系默认的扩增比例参数错误！", ex);
            pcrSystemParamsList = new ArrayList<PcrSystemParams>();
        }

        LimsSyRecord limsSyRecord = new LimsSyRecord();
        limsSyRecord.setCaseId(caseId);
        limsSyRecord.setNeibiaoFlag("02");
        if(labUser1!=null){
            limsSyRecord.setOperatePersonId1(labUser1.getId());
            limsSyRecord.setOperatePersonName1(labUser1.getUserName());
        }

        if(labUser2!=null){
            limsSyRecord.setOperatePersonId2(labUser2.getId());
            limsSyRecord.setOperatePersonName2(labUser2.getUserName());
        }

        currentTimeMillis = System.currentTimeMillis();
        limsSyRecord.setStartDatetime(new Date(currentTimeMillis));
        limsSyRecord.setEndDatetime(new Date(currentTimeMillis + (2*60*60*1000 + 30*60*1000)));

        List<LimsSampleInfo> sampleInfolist = limsSampleInfoService.selectAcceptedListByCaseId(caseId);
        List<LimsQuickExamineRecordSample> limsQuickExamineSampleList = new ArrayList<>();
        LimsQuickExamineRecordSample limsQuickExamine = null;
        int i = 0;
        int count = 0;
        String[] positionArr = Constants.SYTABLE_POSTION_ARR_VER;
        for(LimsSampleInfo sample : sampleInfolist){
            limsQuickExamine = new LimsQuickExamineRecordSample();
            limsQuickExamine.setSampleId(sample.getId());
            limsQuickExamine.setSampleNo(sample.getSampleNo());
            limsQuickExamine.setSampleName(sample.getSampleName());
            limsQuickExamine.setSampleTypeName(sample.getSampleTypeName());
            limsQuickExamine.setExtractPsa("");
            limsQuickExamine.setExtractHb("");

            limsQuickExamine.setExtractMethod(ExamineHelper.getExtractMethodBySampleType(sample.getSampleType()));

            limsQuickExamine.setExtractYuFlag(Constants.FLAG_TRUE);
            limsQuickExamine.setExtractZhenFlag(Constants.FLAG_TRUE);
            limsQuickExamine.setExtractJiaoFlag(Constants.FLAG_TRUE);
            limsQuickExamine.setExtractLiFlag(Constants.FLAG_TRUE);

            for(PcrSystemParams param : pcrSystemParamsList){
                if(sample.getSampleType().equals(param.getSampleType())
                        || param.getSampleType().contains(sample.getSampleType())){
                    limsQuickExamine.setPrimerUl(StringUtils.isBlank(param.getPrimerUl()) ? "" : param.getPrimerUl());
                    limsQuickExamine.setBufferUl(StringUtils.isBlank(param.getBufferUl()) ? "" : param.getBufferUl());
                    limsQuickExamine.setDdwUl(StringUtils.isBlank(param.getDdwUl()) ? "" : param.getDdwUl());
                    limsQuickExamine.setTemplateUl(StringUtils.isBlank(param.getTemplateUl()) ? "" : param.getTemplateUl());
                    break;
                }
            }

            limsQuickExamine.setStandardPcrFlag(Constants.FLAG_FALSE);

            if (i > 95) {
                count = (i % 95) - 1;
                limsQuickExamine.setSamplePosition(positionArr[count]);
            }else {
                limsQuickExamine.setSamplePosition(positionArr[i]);
            }

            i++;

            limsQuickExamineSampleList.add(limsQuickExamine);
        }

        //根据检材类型区分、微量检材（脱落细胞、骨骼/牙齿）模板为4，水为0
        for (LimsQuickExamineRecordSample limsQuickExamineSample:limsQuickExamineSampleList) {
            if(limsQuickExamineSample.getSampleTypeName().equals("骨骼/牙齿") || limsQuickExamineSample.getSampleTypeName().equals("脱落细胞")){
                limsQuickExamineSample.setTemplateUl("4");
                limsQuickExamineSample.setDdwUl("0");
            }else{
                limsQuickExamineSample.setTemplateUl("1");
                limsQuickExamineSample.setDdwUl("3");
            }
        }

        List<EquipmentNameInfo> pcrInstrumentLxjList = equipmentNameInfoService.selectEquipmentNameInfoListByTypeNo(Constants.EQUIPMENT_TYPE_INFO_LXJ);
        List<EquipmentNameInfo> pcrInstrumentZtqList = equipmentNameInfoService.selectEquipmentNameInfoListByTypeNo(Constants.EQUIPMENT_TYPE_INFO_ZTQ);
        List<EquipmentNameInfo> pcrInstrumentGzzList = equipmentNameInfoService.selectEquipmentNameInfoListByTypeNo(Constants.EQUIPMENT_TYPE_INFO_GZZ);
        List<EquipmentNameInfo> pcrInstrumentJsyList = equipmentNameInfoService.selectEquipmentNameInfoListByTypeNo(Constants.EQUIPMENT_TYPE_INFO_JSY);

        modelAndView.addObject("panelInfoList",panelInfoList);
        modelAndView.addObject("operateType", Constants.OPERATE_TYPE_ADD);
        modelAndView.addObject("caseId", caseId);
        modelAndView.addObject("limsExtractRecord", limsExtractRecord);
        modelAndView.addObject("limsPcrRecord", limsPcrRecord);
        modelAndView.addObject("limsSyRecord", limsSyRecord);
        modelAndView.addObject("samplePositionList", Constants.SYTABLE_POSTION_ARR_VER);
        modelAndView.addObject("limsQuickExamineSampleList", limsQuickExamineSampleList);

        modelAndView.addObject("pcrInstrumentLxjList",pcrInstrumentLxjList);
        modelAndView.addObject("pcrInstrumentZtqList",pcrInstrumentZtqList);
        modelAndView.addObject("pcrInstrumentGzzList",pcrInstrumentGzzList);
        modelAndView.addObject("pcrInstrumentJsyList",pcrInstrumentJsyList);

        modelAndView.setViewName("center/quickExamine/quickExtamineInfo");
        return modelAndView;
    }

    @RequestMapping("/deleteExamineRecord.html")
    @ResponseBody
    public Map<String,Object> deleteExamineRecord(HttpServletRequest request,Integer caseId) {
        Map<String,Object> result = new HashMap<String,Object>();

        try {
            List<LimsQuickExamineRecord> quickExamineRecordList = limsQuickExamineRecordService.selectListByCaseId(caseId);
            LimsQuickExamineRecord quickExamineRecord = quickExamineRecordList.get(0);

            limsExtractRecordService.deleteById(quickExamineRecord.getLimsExtractRecordId());

            limsPcrRecordService.deleteById(quickExamineRecord.getLimsPcrRecordId());

            String syRecordIds = quickExamineRecord.getLimsSyRecordId();
            String[] syRecordStr = syRecordIds.split(",");
            for (int i = 0;i < syRecordStr.length; i ++) {
                limsSyRecordService.deleteById(Integer.parseInt(syRecordStr[i]));
            }

            limsQuickExamineRecordService.deleteById(quickExamineRecord.getId());
            result.put("success", true);
        }catch (Exception e) {
            result.put("success", false);
        }

        return result;
    }

    @RequestMapping("/delByRecordId.html")
    @ResponseBody
    public Map<String,Object> delByRecordId(HttpServletRequest request,Integer quickExamineRecordId) {
        Map<String,Object> result = new HashMap<String,Object>();

        try {
            LimsQuickExamineRecord quickExamineRecord = limsQuickExamineRecordService.selectById(quickExamineRecordId);

            limsExtractRecordService.deleteById(quickExamineRecord.getLimsExtractRecordId());

            limsPcrRecordService.deleteById(quickExamineRecord.getLimsPcrRecordId());

            String syRecordIds = quickExamineRecord.getLimsSyRecordId();
            String[] syRecordStr = syRecordIds.split(",");
            for (int i = 0;i < syRecordStr.length; i ++) {
                limsSyRecordService.deleteById(Integer.parseInt(syRecordStr[i]));
            }

            limsQuickExamineRecordService.deleteById(quickExamineRecord.getId());

            result.put("success", true);
        }catch (Exception e) {
            result.put("success", false);
        }

        return result;
    }

    @RequestMapping("/editQuickExamine.html")
    public ModelAndView editQuickExamine(HttpServletRequest request, Integer quickExamineRecordId, Integer caseId) {
        LimsQuickExamineRecord limsQuickExamineRecord = limsQuickExamineRecordService.selectById(quickExamineRecordId);

        ModelAndView modelAndView = viewQuickExamine(limsQuickExamineRecord, caseId);

        return modelAndView;
    }

    @RequestMapping(value="/saveQuickExamineRecord.html", method = RequestMethod.POST, produces="application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> saveQuickExamineRecord(HttpServletRequest request, @RequestBody QuickExamineRecordModel quickExamineRecordModel, String operateType) {
        Map<String, Object> result = new HashMap<String, Object>();

        if(Constants.OPERATE_TYPE_ADD.equals(operateType)) {
            result = quickExamineRecordService.addRecord(quickExamineRecordModel);
        } else {
            result = quickExamineRecordService.updateRecord(quickExamineRecordModel);
        }

        return result;
    }

    @RequestMapping("/viewExamineRecord.html")
    public ModelAndView viewExamineRecord(HttpServletRequest request,Integer caseId, int examineCount) {
        List<LimsQuickExamineRecord> quickExamineRecordList = limsQuickExamineRecordService.selectListByCaseId(caseId);
        if(examineCount == 1){
            return viewOne(quickExamineRecordList.get(0), caseId);
        }else{
            return viewList(quickExamineRecordList, caseId);
        }
    }

    private ModelAndView viewOne(LimsQuickExamineRecord limsQuickExamineRecord, Integer caseId) {

        ModelAndView modelAndView = viewQuickExamine(limsQuickExamineRecord, caseId);

        return modelAndView;
    }

    private ModelAndView viewList(List<LimsQuickExamineRecord> limsQuickExamineRecordList,Integer caseId) {
        ModelAndView modelAndView = new ModelAndView();
        PanelInfo panelInfo = new PanelInfo();
        List<PanelInfo> panelInfoList = panelInfoService.selectPanelInfoList( panelInfo);

        modelAndView.addObject("panelInfoList", panelInfoList);
        modelAndView.addObject("caseId", caseId);
        modelAndView.addObject("limsQuickExamineRecordList", limsQuickExamineRecordList);
        modelAndView.setViewName("center/quickExamine/viewQuickExamineList");
        return modelAndView;
    }

    private ModelAndView viewQuickExamine(LimsQuickExamineRecord limsQuickExamineRecord, Integer caseId) {
        ModelAndView modelAndView = initDict();

        LimsExtractRecord limsExtractRecord = limsExtractRecordService.selectById(limsQuickExamineRecord.getLimsExtractRecordId());

        LimsPcrRecord limsPcrRecord = limsPcrRecordService.selectById(limsQuickExamineRecord.getLimsPcrRecordId());

        String syRecordId = limsQuickExamineRecord.getLimsSyRecordId();
        String [] syRecordIdArr = syRecordId.split(",");;
        LimsSyRecord limsSyRecord = limsSyRecordService.selectById(Integer.valueOf(syRecordIdArr[0]));

        List<DictItem> elecInstrumentList = dictService.selectDictItemListByType(Constants.DICT_TYPE_ELEC_INSTRUMENT);
        for(DictItem di : elecInstrumentList){
            if (StringUtils.isNotBlank(limsSyRecord.getElecInstrument())) {
                if(limsSyRecord.getElecInstrument().equals(di.getDictCode())){
                    limsSyRecord.setElecInstrumentName(di.getDictName());
                    break;
                }
            }
        }

        List<LimsExtractRecordSample> limsExtractRecordSampleList = limsExtractRecordService.selectSampleListByExtractRecordId(limsExtractRecord.getId());
        List<LimsPcrRecordSample> limsPcrRecordSampleList = limsPcrRecordService.selectSampleListByPcrRecordId(limsPcrRecord.getId());
        List<LimsSyRecordSample> limsSyRecordSampleListAll = new ArrayList<>();
        List<LimsSyRecordSample> limsSyRecordSampleList = new ArrayList<>();
        for (int i = 0;i < syRecordIdArr.length; i++) {
            limsSyRecordSampleList = limsSyRecordService.selectSampleListBySyRecordId(Integer.valueOf(syRecordIdArr[i]));

            limsSyRecordSampleListAll.addAll(limsSyRecordSampleList);
        }

        List<LimsQuickExamineRecordSample> limsQuickExamineSampleList = new ArrayList<>();
        LimsQuickExamineRecordSample quickExamineRecordSample;

        int examineCount = 0;
        examineCount = limsExtractRecordSampleList.size();

        LimsExtractRecordSample lers;
        LimsPcrRecordSample lprs;
        LimsSyRecordSample lsrs;
        for (int i = 0;i < examineCount;i++) {
            quickExamineRecordSample = new LimsQuickExamineRecordSample();

            if (ListUtils.isNotNullAndEmptyList(limsExtractRecordSampleList)) {
                if (i < limsExtractRecordSampleList.size()) {
                    lers = limsExtractRecordSampleList.get(i);

                    quickExamineRecordSample.setExtractRecordId(lers.getLimsExtractRecordId());
                    quickExamineRecordSample.setExtractRecordSampleId(lers.getId());
                    quickExamineRecordSample.setSampleId(lers.getSampleId());
                    quickExamineRecordSample.setSampleNo(lers.getSampleNo());
                    quickExamineRecordSample.setSampleName(lers.getSampleName());
                    quickExamineRecordSample.setSampleTypeName(lers.getSampleTypeName());
                    quickExamineRecordSample.setExtractHb(lers.getExtractHb());
                    quickExamineRecordSample.setExtractPsa(lers.getExtractPsa());
                    quickExamineRecordSample.setChangeMethod(lers.getChangeMethod());
                    quickExamineRecordSample.setOtherChangeMethod(lers.getOtherChangeMethod());
                    quickExamineRecordSample.setExtractPosition(lers.getExtractPosition());
                    quickExamineRecordSample.setExtractMethod(lers.getExtractMethod());
                    quickExamineRecordSample.setExtractLiFlag(lers.getExtractLiFlag());
                    quickExamineRecordSample.setExtractZhenFlag(lers.getExtractZhenFlag());
                    quickExamineRecordSample.setExtractJiaoFlag(lers.getExtractJiaoFlag());
                    quickExamineRecordSample.setExtractYuFlag(lers.getExtractYuFlag());
                }
            }

            if (ListUtils.isNotNullAndEmptyList(limsPcrRecordSampleList)) {
                if (i < limsPcrRecordSampleList.size()) {
                    lprs = limsPcrRecordSampleList.get(i);

                    quickExamineRecordSample.setPcrRecordId(lprs.getLimsPcrRecordId());
                    quickExamineRecordSample.setPcrRecordSampleId(lprs.getId());
                    if (quickExamineRecordSample.getSampleId() == null)
                        quickExamineRecordSample.setSampleId(lprs.getSampleId());
                    if (StringUtils.isBlank(quickExamineRecordSample.getSampleNo()))
                        quickExamineRecordSample.setSampleNo(lprs.getSampleNo());
                    if (StringUtils.isBlank(quickExamineRecordSample.getSampleName()))
                        quickExamineRecordSample.setSampleName(lprs.getSampleName());
                    if (StringUtils.isBlank(quickExamineRecordSample.getSampleTypeName()))
                        quickExamineRecordSample.setSampleTypeName(lprs.getSampleTypeName());
                    quickExamineRecordSample.setStandardPcrFlag(lprs.getStandardFlag());
                    quickExamineRecordSample.setPrimerUl(lprs.getPrimerUl());
                    quickExamineRecordSample.setBufferUl(lprs.getBufferUl());
                    quickExamineRecordSample.setTemplateUl(lprs.getTemplateUl());
                    quickExamineRecordSample.setDdwUl(lprs.getDdwUl());
                }
            }

            if (ListUtils.isNotNullAndEmptyList(limsSyRecordSampleListAll)) {
                if (i < limsSyRecordSampleListAll.size()) {
                    lsrs = limsSyRecordSampleListAll.get(i);

                    quickExamineRecordSample.setSyRecordId(lsrs.getLimsSyRecordId());
                    quickExamineRecordSample.setSyRecordSampleId(lsrs.getId());
                    if (quickExamineRecordSample.getSampleId() == null)
                        quickExamineRecordSample.setSampleId(lsrs.getSampleId());
                    if (StringUtils.isBlank(quickExamineRecordSample.getSampleNo()))
                        quickExamineRecordSample.setSampleNo(lsrs.getSampleNo());
                    if (StringUtils.isBlank(quickExamineRecordSample.getSampleName()))
                        quickExamineRecordSample.setSampleName(lsrs.getSampleName());
                    if (StringUtils.isBlank(quickExamineRecordSample.getSampleTypeName()))
                        quickExamineRecordSample.setSampleTypeName(lsrs.getSampleTypeName());
                    quickExamineRecordSample.setStandardSyFlag(lsrs.getStandardFlag());
                    quickExamineRecordSample.setSyBoardNo(lsrs.getBoardNo());
                    quickExamineRecordSample.setSamplePosition(lsrs.getSamplePosition());
                }
            }

            limsQuickExamineSampleList.add(quickExamineRecordSample);
        }

        PanelInfo panelInfo = new PanelInfo();
        List<PanelInfo> panelInfoList = panelInfoService.selectPanelInfoList( panelInfo);

        List<EquipmentNameInfo> pcrInstrumentLxjList = equipmentNameInfoService.selectEquipmentNameInfoListByTypeNo(Constants.EQUIPMENT_TYPE_INFO_LXJ);
        List<EquipmentNameInfo> pcrInstrumentZtqList = equipmentNameInfoService.selectEquipmentNameInfoListByTypeNo(Constants.EQUIPMENT_TYPE_INFO_ZTQ);
        List<EquipmentNameInfo> pcrInstrumentGzzList = equipmentNameInfoService.selectEquipmentNameInfoListByTypeNo(Constants.EQUIPMENT_TYPE_INFO_GZZ);
        List<EquipmentNameInfo> pcrInstrumentJsyList = equipmentNameInfoService.selectEquipmentNameInfoListByTypeNo(Constants.EQUIPMENT_TYPE_INFO_JSY);

        modelAndView.addObject("panelInfoList", panelInfoList);
        modelAndView.addObject("operateType", Constants.OPERATE_TYPE_EDIT);
        modelAndView.addObject("limsQuickExamineRecord", limsQuickExamineRecord);
        modelAndView.addObject("caseId", caseId);
        modelAndView.addObject("limsExtractRecord", limsExtractRecord);
        modelAndView.addObject("limsPcrRecord", limsPcrRecord);
        modelAndView.addObject("limsSyRecord", limsSyRecord);
        modelAndView.addObject("samplePositionList", Constants.SYTABLE_POSTION_ARR_VER);
        modelAndView.addObject("limsQuickExamineSampleList", limsQuickExamineSampleList);

        modelAndView.addObject("pcrInstrumentLxjList",pcrInstrumentLxjList);
        modelAndView.addObject("pcrInstrumentZtqList",pcrInstrumentZtqList);
        modelAndView.addObject("pcrInstrumentGzzList",pcrInstrumentGzzList);
        modelAndView.addObject("pcrInstrumentJsyList",pcrInstrumentJsyList);

        modelAndView.setViewName("center/quickExamine/quickExtamineInfo");

        return modelAndView;
    }

    private ModelAndView initDict() {
        ModelAndView modelAndView = new ModelAndView();

        //提取方法字典
        List<DictItem> extractMethodList = dictService.selectDictItemListByType(Constants.DICT_TYPE_EXTRACT_METHOD);
        /*  实验室人员 */
        List<LabUser> labUserList = labUserService.selectAll();

        /*  扩增字典 */
        List<DictItem> pcrProgramList = dictService.selectDictItemListByType(Constants.DICT_TYPE_PCR_PROGRAM_NO);
        List<DictItem> pcrReagentKitList = dictService.selectDictItemListByType(Constants.DICT_TYPE_PCR_REAGENT_KIT);
        List<DictItem> pcrSystemList = dictService.selectDictItemListByType(Constants.DICT_TYPE_PCR_SYSTEM);
        /*List<DictItem> pcrInstrumentList = dictService.selectDictItemListByType(Constants.DICT_TYPE_PCR_INSTRUMENT);*/
        List<EquipmentNameInfo> pcrInstrumentList = equipmentNameInfoService.selectEquipmentNameInfoListByTypeNo(Constants.EQUIPMENT_TYPE_INFO_KZY);

        List<DictItem> chanwuList = dictService.selectDictItemListByType(Constants.DICT_TYPE_CHANWU_UL);
        List<DictItem> jiaxiananList = dictService.selectDictItemListByType(Constants.DICT_TYPE_JIAXIANAN_UL);
        List<DictItem> neibiaoulList = dictService.selectDictItemListByType(Constants.DICT_TYPE_NEIBIAO_UL);
        List<DictItem> neibiaoList = dictService.selectDictItemListByType(Constants.DICT_TYPE_NEIBIAO);
        /*List<DictItem> elecInstrumentList = dictService.selectDictItemListByType(Constants.DICT_TYPE_ELEC_INSTRUMENT);*/
        List<EquipmentNameInfo> elecInstrumentList = equipmentNameInfoService.selectEquipmentNameInfoListByTypeNo(Constants.EQUIPMENT_TYPE_INFO_DYY);
        //超净台
        List<EquipmentNameInfo> pcrInstrumentProgramList = equipmentNameInfoService.selectEquipmentNameInfoListByTypeNo(Constants.EQUIPMENT_TYPE_PCR_PROGRAM_NO);

        modelAndView.addObject("extractMethodList", extractMethodList);
        modelAndView.addObject("labUserList", labUserList);
        modelAndView.addObject("pcrProgramList", pcrProgramList);
        modelAndView.addObject("pcrInstrumentProgramList",pcrInstrumentProgramList);
        modelAndView.addObject("pcrReagentKitList", pcrReagentKitList);
        modelAndView.addObject("pcrSystemList", pcrSystemList);
        modelAndView.addObject("pcrInstrumentList", pcrInstrumentList);
        modelAndView.addObject("chanwuList", chanwuList);
        modelAndView.addObject("jiaxiananList", jiaxiananList);
        modelAndView.addObject("neibiaoulList", neibiaoulList);
        modelAndView.addObject("neibiaoList", neibiaoList);
        modelAndView.addObject("elecInstrumentList", elecInstrumentList);

        return modelAndView;
    }

    public LimsCaseInfoVO resetCaseInfoQuery(LimsCaseInfoVO caseInfoVO) throws ParseException {
        if (StringUtils.isBlank(caseInfoVO.getEntity().getCaseNo())){
            caseInfoVO.getEntity().setCaseNo(null);
        }else {
            caseInfoVO.getEntity().setCaseNo(caseInfoVO.getEntity().getCaseNo().trim());
        }
        if (StringUtils.isBlank(caseInfoVO.getEntity().getCaseXkNo())){
            caseInfoVO.getEntity().setCaseXkNo(null);
        }else {
            caseInfoVO.getEntity().setCaseXkNo(caseInfoVO.getEntity().getCaseXkNo().trim());
        }

        if (StringUtils.isBlank(caseInfoVO.getEntity().getEntrustmentType())){
            caseInfoVO.getEntity().setEntrustmentType(null);
        }else {
            caseInfoVO.getEntity().setEntrustmentType(caseInfoVO.getEntity().getEntrustmentType());
        }
        if (StringUtils.isBlank(caseInfoVO.getEntity().getCaseName())){
            caseInfoVO.getEntity().setCaseName(null);
        }else {
            caseInfoVO.getEntity().setCaseName(caseInfoVO.getEntity().getCaseName());
        }

        if (StringUtils.isBlank(caseInfoVO.getEntity().getCaseProperty())){
            caseInfoVO.getEntity().setCaseProperty(null);
        }else {
            caseInfoVO.getEntity().setCaseProperty(caseInfoVO.getEntity().getCaseProperty());
        }

        if (StringUtils.isBlank(caseInfoVO.getDelegateOrg())){
            caseInfoVO.setDelegateOrg(null);
        }else {
            caseInfoVO.setDelegateOrg(caseInfoVO.getDelegateOrg());
        }

        if (StringUtils.isBlank(caseInfoVO.getDelegateAcceptor())){
            caseInfoVO.setDelegateAcceptor(null);
        }else {
            caseInfoVO.setDelegateAcceptor(caseInfoVO.getDelegateAcceptor());
        }

        if (caseInfoVO.getAcceptDateStart() == null){
            caseInfoVO.setAcceptDateStart(null);
        }else {
            caseInfoVO.setAcceptDateStart(caseInfoVO.getAcceptDateStart());
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String oldTime = null;
        Date newTime = null;

        if (caseInfoVO.getAcceptDateEnd() == null){
            caseInfoVO.setAcceptDateEnd(null);
        }else {
            oldTime = sdf.format(caseInfoVO.getAcceptDateEnd());
            newTime =sd.parse(oldTime + " 23:59:59");
            caseInfoVO.setAcceptDateEnd(newTime);
        }

        return caseInfoVO;
    }

}
