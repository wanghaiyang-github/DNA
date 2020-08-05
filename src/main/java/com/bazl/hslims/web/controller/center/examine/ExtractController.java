package com.bazl.hslims.web.controller.center.examine;

import com.bazl.hslims.common.Constants;
import com.bazl.hslims.manager.PageInfo;
import com.bazl.hslims.manager.model.po.*;
import com.bazl.hslims.manager.model.vo.LimsCaseInfoVO;
import com.bazl.hslims.manager.services.center.CenterLoginService;
import com.bazl.hslims.manager.services.common.*;
import com.bazl.hslims.utils.DateUtils;
import com.bazl.hslims.web.controller.BaseController;
import com.bazl.hslims.web.datamodel.ExtractRecordDataModel;
import com.bazl.hslims.web.helper.ExamineHelper;
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
@RequestMapping("/center/extract")
public class ExtractController extends BaseController {

    @Autowired
    LimsCaseInfoService limsCaseInfoService;
    @Autowired
    LimsSampleInfoService limsSampleInfoService;
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

    @RequestMapping("/list.html")
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
        for (LimsCaseInfoVO caseInfo : caseInfoVOList) {

            caseInfo.setExtractRecordCount(limsExtractRecordService.selectCountByCaseId(caseInfo.getEntity().getId()));
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
                /*int count = limsExtractRecordService.selectCountBySampleNo(cno);*/
                List<LimsExtractRecord> extractRecordSamples = limsExtractRecordService.selectSampleNoByTaskId(cno);
                List<LimsExtractRecordSample> limsExtractRecordSamples = limsExtractRecordService.selectListBySampleNo(cno);
                if (extractRecordSamples.size() != 0) {
                    caseInfo.setAppExtractRecordCount(extractRecordSamples.size());
                    caseInfo.setCno(cno);

                    for (LimsExtractRecordSample extractRecordSample : limsExtractRecordSamples) {
                        LimsExtractRecord limsExtractRecord = limsExtractRecordService.selectById(extractRecordSample.getLimsExtractRecordId());
                        caseInfo.setTaskId(limsExtractRecord.getTaskId());
                        if (extractRecordSample.getLimsExtractRecordId() != 0) {
                            caseInfo.setExtractRecordId(extractRecordSample.getLimsExtractRecordId().toString());
                        }
                    }
                }
            }
        }

        /*int totalCnt = limsCaseInfoService.selectVOCnt(query);*/
        int totalCnt = limsCaseInfoService.selectVOCnt(query);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("caseInfoQuery", query);
        modelAndView.addObject("caseInfoVOList", caseInfoVOList);
        modelAndView.addObject("pageInfo", pageInfo.updatePageInfo(totalCnt));
        modelAndView.addObject("delegateOrgList", delegateOrgList);
        modelAndView.addObject("labUserList", labUserList);
        modelAndView.setViewName("center/examine/extractRecordList");
        return modelAndView;
    }

    @RequestMapping("/add.html")
    public ModelAndView add(HttpServletRequest request, Integer caseId) {
        //提取方法字典
        List<DictItem> extractMethodList = dictService.selectDictItemListByType(Constants.DICT_TYPE_EXTRACT_METHOD);
        /*  实验室人员 */
        List<LabUser> labUserList = labUserService.selectAll();

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

        List<LimsSampleInfo> sampleInfolist = limsSampleInfoService.selectAcceptedListByCaseId(caseId);
        List<LimsExtractRecordSample> limsExtractRecordSampleList = new ArrayList<>();
        LimsExtractRecordSample extractRecordSample = null;
        for (LimsSampleInfo sample : sampleInfolist) {
            extractRecordSample = new LimsExtractRecordSample();
            extractRecordSample.setSampleId(sample.getId());
            extractRecordSample.setSampleNo(sample.getSampleNo());
            extractRecordSample.setSampleName(sample.getSampleName());
            extractRecordSample.setSampleFlag(sample.getSampleFlag());
            extractRecordSample.setSampleTypeName(sample.getSampleTypeName());
            extractRecordSample.setExtractHb("");
            extractRecordSample.setExtractPsa("");

            extractRecordSample.setExtractMethod(ExamineHelper.getExtractMethodBySampleType(sample.getSampleType()));

            extractRecordSample.setExtractYuFlag(Constants.FLAG_TRUE);
            extractRecordSample.setExtractZhenFlag(Constants.FLAG_TRUE);
            extractRecordSample.setExtractJiaoFlag(Constants.FLAG_TRUE);
            extractRecordSample.setExtractLiFlag(Constants.FLAG_TRUE);
            limsExtractRecordSampleList.add(extractRecordSample);
        }
        List<EquipmentNameInfo> pcrInstrumentLxjList = equipmentNameInfoService.selectEquipmentNameInfoListByTypeNo(Constants.EQUIPMENT_TYPE_INFO_LXJ);
        List<EquipmentNameInfo> pcrInstrumentZtqList = equipmentNameInfoService.selectEquipmentNameInfoListByTypeNo(Constants.EQUIPMENT_TYPE_INFO_ZTQ);
        List<EquipmentNameInfo> pcrInstrumentGzzList = equipmentNameInfoService.selectEquipmentNameInfoListByTypeNo(Constants.EQUIPMENT_TYPE_INFO_GZZ);
        List<EquipmentNameInfo> pcrInstrumentJsyList = equipmentNameInfoService.selectEquipmentNameInfoListByTypeNo(Constants.EQUIPMENT_TYPE_INFO_JSY);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("operateType", Constants.OPERATE_TYPE_ADD);
        modelAndView.addObject("extractMethodList", extractMethodList);
        modelAndView.addObject("labUserList", labUserList);
        modelAndView.addObject("limsExtractRecord", limsExtractRecord);
        modelAndView.addObject("limsExtractRecordSampleList", limsExtractRecordSampleList);

        modelAndView.addObject("pcrInstrumentLxjList", pcrInstrumentLxjList);
        modelAndView.addObject("pcrInstrumentZtqList", pcrInstrumentZtqList);
        modelAndView.addObject("pcrInstrumentGzzList", pcrInstrumentGzzList);
        modelAndView.addObject("pcrInstrumentJsyList", pcrInstrumentJsyList);

        modelAndView.setViewName("center/examine/extractInfo");
        return modelAndView;
    }


    @RequestMapping("/viewExtractRecord.html")
    public ModelAndView viewExtractRecord(HttpServletRequest request, Integer caseId) {
        List<LimsExtractRecord> limsExtractRecordList = limsExtractRecordService.selectListByCaseId(caseId);

        int extractRecordCount = limsExtractRecordService.selectCountByCaseId(caseId);
        if (limsExtractRecordList.size() == 1) {
            return viewOne(limsExtractRecordList.get(0));
        } else {
            return viewList(limsExtractRecordList, caseId);
        }
    }

    @RequestMapping("/viewAppExtractDoc.html")
    public ModelAndView viewAppExtractRecord(HttpServletRequest request, Integer caseId,String cno) {

        List<LimsExtractRecord> limsExtractRecordSampleList = limsExtractRecordService.selectSampleNoByTaskId(cno);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("operateType", Constants.OPERATE_TYPE_EDIT);
        modelAndView.addObject("caseId", caseId);
        modelAndView.addObject("limsExtractRecordList", limsExtractRecordSampleList);
        modelAndView.setViewName("center/examine/viewExtractList");

        return modelAndView;
    }

    private ModelAndView viewOne(LimsExtractRecord limsExtractRecord) {
        //提取方法字典
        List<DictItem> extractMethodList = dictService.selectDictItemListByType(Constants.DICT_TYPE_EXTRACT_METHOD);
        /*  实验室人员 */
        List<LabUser> labUserList = labUserService.selectAll();

        List<LimsExtractRecordSample> limsExtractRecordSampleList = limsExtractRecordService.selectSampleListByExtractRecordId(limsExtractRecord.getId());

        List<EquipmentNameInfo> pcrInstrumentLxjList = equipmentNameInfoService.selectEquipmentNameInfoListByTypeNo(Constants.EQUIPMENT_TYPE_INFO_LXJ);
        List<EquipmentNameInfo> pcrInstrumentZtqList = equipmentNameInfoService.selectEquipmentNameInfoListByTypeNo(Constants.EQUIPMENT_TYPE_INFO_ZTQ);
        List<EquipmentNameInfo> pcrInstrumentGzzList = equipmentNameInfoService.selectEquipmentNameInfoListByTypeNo(Constants.EQUIPMENT_TYPE_INFO_GZZ);
        List<EquipmentNameInfo> pcrInstrumentJsyList = equipmentNameInfoService.selectEquipmentNameInfoListByTypeNo(Constants.EQUIPMENT_TYPE_INFO_JSY);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("operateType", Constants.OPERATE_TYPE_EDIT);
        modelAndView.addObject("extractMethodList", extractMethodList);
        modelAndView.addObject("labUserList", labUserList);
        modelAndView.addObject("caseId", limsExtractRecord.getCaseId());
        modelAndView.addObject("limsExtractRecord", limsExtractRecord);
        modelAndView.addObject("limsExtractRecordSampleList", limsExtractRecordSampleList);

        modelAndView.addObject("pcrInstrumentLxjList", pcrInstrumentLxjList);
        modelAndView.addObject("pcrInstrumentZtqList", pcrInstrumentZtqList);
        modelAndView.addObject("pcrInstrumentGzzList", pcrInstrumentGzzList);
        modelAndView.addObject("pcrInstrumentJsyList", pcrInstrumentJsyList);

        modelAndView.setViewName("center/examine/extractInfo");
        return modelAndView;
    }

    private ModelAndView viewList(List<LimsExtractRecord> limsExtractRecordList, Integer caseId) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("operateType", Constants.OPERATE_TYPE_EDIT);
        modelAndView.addObject("caseId", caseId);
        modelAndView.addObject("limsExtractRecordList", limsExtractRecordList);
        modelAndView.setViewName("center/examine/viewExtractList");
        return modelAndView;
    }

    @RequestMapping("/edit.html")
    public ModelAndView edit(HttpServletRequest request, Integer extractRecordId) {
        //提取方法字典
        List<DictItem> extractMethodList = dictService.selectDictItemListByType(Constants.DICT_TYPE_EXTRACT_METHOD);
        /*  实验室人员 */
        List<LabUser> labUserList = labUserService.selectAll();

        LimsExtractRecord limsExtractRecord = limsExtractRecordService.selectById(extractRecordId);
        List<LimsExtractRecordSample> limsExtractRecordSampleList = limsExtractRecordService.selectSampleListByExtractRecordId(extractRecordId);

        List<EquipmentNameInfo> pcrInstrumentLxjList = equipmentNameInfoService.selectEquipmentNameInfoListByTypeNo(Constants.EQUIPMENT_TYPE_INFO_LXJ);
        List<EquipmentNameInfo> pcrInstrumentZtqList = equipmentNameInfoService.selectEquipmentNameInfoListByTypeNo(Constants.EQUIPMENT_TYPE_INFO_ZTQ);
        List<EquipmentNameInfo> pcrInstrumentGzzList = equipmentNameInfoService.selectEquipmentNameInfoListByTypeNo(Constants.EQUIPMENT_TYPE_INFO_GZZ);
        List<EquipmentNameInfo> pcrInstrumentJsyList = equipmentNameInfoService.selectEquipmentNameInfoListByTypeNo(Constants.EQUIPMENT_TYPE_INFO_JSY);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("operateType", Constants.OPERATE_TYPE_EDIT);
        modelAndView.addObject("extractMethodList", extractMethodList);
        modelAndView.addObject("labUserList", labUserList);
        modelAndView.addObject("limsExtractRecord", limsExtractRecord);
        modelAndView.addObject("limsExtractRecordSampleList", limsExtractRecordSampleList);

        modelAndView.addObject("pcrInstrumentLxjList", pcrInstrumentLxjList);
        modelAndView.addObject("pcrInstrumentZtqList", pcrInstrumentZtqList);
        modelAndView.addObject("pcrInstrumentGzzList", pcrInstrumentGzzList);
        modelAndView.addObject("pcrInstrumentJsyList", pcrInstrumentJsyList);

        modelAndView.setViewName("center/examine/extractInfo");
        return modelAndView;
    }


    @RequestMapping("/delByRecordId.html")
    @ResponseBody
    public Map<String, Object> delByRecordId(HttpServletRequest request, Integer extractRecordId) {

        limsExtractRecordService.deleteById(extractRecordId);

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("success", true);
        return result;
    }


    @RequestMapping("/delByCaseId.html")
    @ResponseBody
    public Map<String, Object> delByCaseId(HttpServletRequest request, Integer caseId) {
        List<LimsExtractRecord> recordList = limsExtractRecordService.selectListByCaseId(caseId);
        for (LimsExtractRecord record : recordList) {
            limsExtractRecordService.deleteById(record.getId());
        }

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("success", true);
        return result;
    }


    @RequestMapping(value = "/saveRecord.html", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> saveRecord(HttpServletRequest request, @RequestBody ExtractRecordDataModel extractRecordDataModel, String operateType) {

        Integer extractRecordId = null;

        if (Constants.OPERATE_TYPE_ADD.equals(operateType)) {
            extractRecordId = limsExtractRecordService.addRecord(extractRecordDataModel);
        } else {
            extractRecordId = limsExtractRecordService.updateRecord(extractRecordDataModel);
        }

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("success", true);
        result.put("extractRecordId", extractRecordId);
        return result;
    }

    @RequestMapping("/extractDoc.html")
    public void generateAndDownloadExtractDoc(HttpServletRequest request, HttpServletResponse response,
                                              Integer extractRecordId, Integer caseId) {
        LimsExtractRecord limsExtractRecord = null;

        if (extractRecordId != null && extractRecordId != 0) {
            limsExtractRecord = limsExtractRecordService.selectById(extractRecordId);

        }

        if (caseId != null && caseId != 0) {
            List<LimsExtractRecord> recordList = limsExtractRecordService.selectListByCaseId(caseId);
            if (recordList.size() > 0) {
                limsExtractRecord = recordList.get(0);
            }
        }

        Map<String, Object> params = new HashMap<String, Object>();
        List<LimsExtractRecordSample> sampleInfoList = limsExtractRecordService.selectSampleListByExtractRecordId(limsExtractRecord.getId());

        List<EquipmentNameInfo> equipmentNameInfoList = equipmentNameInfoService.selectAllList();

        for (LimsExtractRecordSample sampleInfo : sampleInfoList) {
            for (EquipmentNameInfo equipmentInfo : equipmentNameInfoList) {
                if (sampleInfo.getExtractLiFlag().equals(equipmentInfo.getId().toString())) {
                    sampleInfo.setExtractLiFlag(equipmentInfo.getEquipmentNo());
                }
                if (sampleInfo.getExtractZhenFlag().equals(equipmentInfo.getId().toString())) {
                    sampleInfo.setExtractZhenFlag(equipmentInfo.getEquipmentNo());
                }
                if (sampleInfo.getExtractJiaoFlag().equals(equipmentInfo.getId().toString())) {
                    sampleInfo.setExtractJiaoFlag(equipmentInfo.getEquipmentNo());
                }
                if (sampleInfo.getExtractYuFlag().equals(equipmentInfo.getId().toString())) {
                    sampleInfo.setExtractYuFlag(equipmentInfo.getEquipmentNo());
                }
            }
        }

        for (LimsExtractRecordSample sampleInfo : sampleInfoList) {
            if ("0".equals(sampleInfo.getExtractLiFlag())) {
                sampleInfo.setExtractLiFlag("");
            }
            if ("0".equals(sampleInfo.getExtractZhenFlag())) {
                sampleInfo.setExtractZhenFlag("");
            }
            if ("0".equals(sampleInfo.getExtractJiaoFlag())) {
                sampleInfo.setExtractJiaoFlag("");
            }
            if ("0".equals(sampleInfo.getExtractYuFlag())) {
                sampleInfo.setExtractYuFlag("");
            }
        }

        if (sampleInfoList.size() <= 20) {
            int num = 20 - sampleInfoList.size();
            for (int i = 0; i < num; i++) {
                LimsExtractRecordSample tmp = new LimsExtractRecordSample();
                tmp.setSampleNo("");
                tmp.setExtractHb("");
                tmp.setExtractPsa("");
                tmp.setExtractMethod("");
                tmp.setExtractLiFlag("");
                tmp.setExtractYuFlag("");
                tmp.setExtractZhenFlag("");
                tmp.setExtractJiaoFlag("");
                sampleInfoList.add(tmp);
            }

            params.put("extractRecordSampleList1", sampleInfoList);
        } else {
            int sampleLength = sampleInfoList.size();
            int loopCount = (sampleLength % 20 == 0) ? (sampleLength / 20) : (sampleLength / 20 + 1);
            int startIdx = 0;
            int rows = 20;
            List<LimsExtractRecordSample> samplePageList = null;
            for (int i = 1; i <= loopCount; i++) {
                if (rows >= sampleLength) {
                    rows = sampleLength;
                }

                samplePageList = new ArrayList<>();
                samplePageList.addAll(sampleInfoList.subList(startIdx, rows));
                if (i == loopCount && rows < loopCount * 20) {
                    int num = 20 - samplePageList.size();
                    for (int j = 0; j < num; j++) {
                        LimsExtractRecordSample tmp = new LimsExtractRecordSample();
                        tmp.setSampleNo("");
                        tmp.setExtractHb("");
                        tmp.setExtractPsa("");
                        tmp.setExtractMethod("");
                        tmp.setExtractLiFlag("");
                        tmp.setExtractYuFlag("");
                        tmp.setExtractZhenFlag("");
                        tmp.setExtractJiaoFlag("");
                        samplePageList.add(tmp);
                    }
                }

                params.put("extractRecordSampleList" + i, samplePageList);

                startIdx += 20;
                rows += 20;
            }
        }

        LimsCaseInfo caseInfo = limsCaseInfoService.selectById(limsExtractRecord.getCaseId());

        try {
            String caseNo = caseInfo.getCaseNo();
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
            logger.error("获取提取任务号错误", ex);
            params.put("year", "");
            params.put("no", "");
        }

        params.put("extractDatetime", limsExtractRecord.getExtractDatetime() == null ? "" : DateUtils.dateToString(limsExtractRecord.getExtractDatetime(), "yyyy年MM月dd日 HH:mm"));
        params.put("extractPerson",
                (StringUtils.isBlank(limsExtractRecord.getExtractPersonName1()) ? "" : limsExtractRecord.getExtractPersonName1())
                        + "  " + (StringUtils.isBlank(limsExtractRecord.getExtractPersonName2()) ? "" : limsExtractRecord.getExtractPersonName2())
        );

        try {
            Configuration cfg = new Configuration();
            cfg.setDefaultEncoding("UTF-8");
            ServletContext servletContext = request.getSession().getServletContext();
            cfg.setServletContextForTemplateLoading(servletContext, "/templates");
            //获取模板
            Template tmp = cfg.getTemplate("extractRecord.ftl", "UTF-8");

            String filename = "-提取记录表" + DateUtils.dateToString(new Date(), "yyyyMMddHHmmss") + ".doc";

            response.setCharacterEncoding("UTF-8");
            //文件头，导出的文件名
            response.setHeader("Content-disposition", "attachment;filename=" + (StringUtils.isBlank(caseInfo.getCaseNo()) ? "" : caseInfo.getCaseNo()) + new String(filename.getBytes("GBK"), "ISO-8859-1"));
            response.setContentType("application/x-msdownload");//类型

            tmp.process(params, response.getWriter());
        } catch (Exception ex) {
            logger.error("", ex);
        }
    }

    @RequestMapping("/appExtractDoc.html")
    public void generateAndDownloadAppExtractDoc(HttpServletRequest request, HttpServletResponse response,
                                                 Integer taskId, Integer caseId) {
        LimsExtractRecord limsExtractRecord =null;
        if(taskId!=0){
             limsExtractRecord = limsExtractRecordService.selectByTaskId(taskId);
        }

        if (caseId != null && caseId != 0) {
            List<LimsExtractRecord> recordList = limsExtractRecordService.selectListByCaseId(caseId);
            if (recordList.size() > 0) {
                limsExtractRecord = recordList.get(0);
            }
        }

        Map<String, Object> params = new HashMap<String, Object>();
        List<LimsExtractRecordSample> sampleInfoList = limsExtractRecordService.selectSampleListByExtractRecordId(limsExtractRecord.getId());

        List<EquipmentNameInfo> equipmentNameInfoList = equipmentNameInfoService.selectAllList();

        for (LimsExtractRecordSample sampleInfo : sampleInfoList) {
            for (EquipmentNameInfo equipmentInfo : equipmentNameInfoList) {
                if (sampleInfo.getExtractLiFlag() != null) {
                    if (sampleInfo.getExtractLiFlag().equals(equipmentInfo.getId().toString())) {
                        sampleInfo.setExtractLiFlag(equipmentInfo.getEquipmentNo());
                    }
                }
                if (sampleInfo.getExtractZhenFlag() != null) {
                    if (sampleInfo.getExtractZhenFlag().equals(equipmentInfo.getId().toString())) {
                        sampleInfo.setExtractZhenFlag(equipmentInfo.getEquipmentNo());
                    }
                }
                if (sampleInfo.getExtractJiaoFlag() != null) {
                    if (sampleInfo.getExtractJiaoFlag().equals(equipmentInfo.getId().toString())) {
                        sampleInfo.setExtractJiaoFlag(equipmentInfo.getEquipmentNo());
                    }
                }
                if (sampleInfo.getExtractYuFlag() != null) {
                    if (sampleInfo.getExtractYuFlag().equals(equipmentInfo.getId().toString())) {
                        sampleInfo.setExtractYuFlag(equipmentInfo.getEquipmentNo());
                    }
                }

            }
        }

        for (LimsExtractRecordSample sampleInfo : sampleInfoList) {
            if ("0".equals(sampleInfo.getExtractLiFlag())) {
                sampleInfo.setExtractLiFlag("");
            }
            if ("0".equals(sampleInfo.getExtractZhenFlag())) {
                sampleInfo.setExtractZhenFlag("");
            }
            if ("0".equals(sampleInfo.getExtractJiaoFlag())) {
                sampleInfo.setExtractJiaoFlag("");
            }
            if ("0".equals(sampleInfo.getExtractYuFlag())) {
                sampleInfo.setExtractYuFlag("");
            }
        }

        List extractRecordSampleList = new ArrayList<>();
        for (LimsExtractRecordSample sampleInfo : sampleInfoList) {
            /*List<LimsSampleInfo> limsSampleInfoList = limsSampleInfoService.selectListByCaseId(caseId);
            for (LimsSampleInfo limsSampleInfo : limsSampleInfoList) {
                if (sampleInfo.getSampleNo().equals(limsSampleInfo.getSampleNo())) {*/
                    extractRecordSampleList.add(sampleInfo);
                /*}
            }*/
        }

        if (extractRecordSampleList.size() <= 20) {
            int num = 20 - sampleInfoList.size();
            for (int i = 0; i < num; i++) {
                LimsExtractRecordSample tmp = new LimsExtractRecordSample();
                tmp.setSampleNo("");
                tmp.setExtractHb("");
                tmp.setExtractPsa("");
                tmp.setExtractMethod("");
                tmp.setExtractLiFlag("");
                tmp.setExtractYuFlag("");
                tmp.setExtractZhenFlag("");
                tmp.setExtractJiaoFlag("");
                extractRecordSampleList.add(tmp);
            }

            params.put("extractRecordSampleList1", extractRecordSampleList);
        } else {
            int sampleLength = extractRecordSampleList.size();
            int loopCount = (sampleLength % 20 == 0) ? (sampleLength / 20) : (sampleLength / 20 + 1);
            int startIdx = 0;
            int rows = 20;
            List<LimsExtractRecordSample> samplePageList = null;
            for (int i = 1; i <= loopCount; i++) {
                if (rows >= sampleLength) {
                    rows = sampleLength;
                }

                samplePageList = new ArrayList<>();
                samplePageList.addAll(extractRecordSampleList.subList(startIdx, rows));
                if (i == loopCount && rows < loopCount * 20) {
                    int num = 20 - samplePageList.size();
                    for (int j = 0; j < num; j++) {
                        LimsExtractRecordSample tmp = new LimsExtractRecordSample();
                        tmp.setSampleNo("");
                        tmp.setExtractHb("");
                        tmp.setExtractPsa("");
                        tmp.setExtractMethod("");
                        tmp.setExtractLiFlag("");
                        tmp.setExtractYuFlag("");
                        tmp.setExtractZhenFlag("");
                        tmp.setExtractJiaoFlag("");
                        samplePageList.add(tmp);
                    }
                }

                params.put("extractRecordSampleList" + i, samplePageList);

                startIdx += 20;
                rows += 20;
            }
        }
        LimsCaseInfo caseInfo = new LimsCaseInfo();
        if (limsExtractRecord.getCaseId() != null) {
            caseInfo = limsCaseInfoService.selectById(limsExtractRecord.getCaseId());
        } else {
            for (LimsExtractRecordSample extractSampleInfo : sampleInfoList) {
                LimsSampleInfo sampleInfo = limsSampleInfoService.selectById(extractSampleInfo.getSampleId());
                caseInfo = limsCaseInfoService.selectById(sampleInfo.getCaseId());
                break;
            }
        }

        try {
            String caseNo = caseInfo.getCaseNo();
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
            logger.error("获取提取任务号错误", ex);
            params.put("year", "");
            params.put("no", "");
        }

        params.put("extractDatetime", limsExtractRecord.getExtractDatetime() == null ? "" : DateUtils.dateToString(limsExtractRecord.getExtractDatetime(), "yyyy年MM月dd日 HH:mm"));
        params.put("extractPerson",
                (StringUtils.isBlank(limsExtractRecord.getExtractPersonName1()) ? "" : limsExtractRecord.getExtractPersonName1())
                        + "  " + (StringUtils.isBlank(limsExtractRecord.getExtractPersonName2()) ? "" : limsExtractRecord.getExtractPersonName2())
        );

        try {
            Configuration cfg = new Configuration();
            cfg.setDefaultEncoding("UTF-8");
            ServletContext servletContext = request.getSession().getServletContext();
            cfg.setServletContextForTemplateLoading(servletContext, "/templates");
            //获取模板
            Template tmp = cfg.getTemplate("extractRecord.ftl", "UTF-8");

            String filename = "-提取记录表" + DateUtils.dateToString(new Date(), "yyyyMMddHHmmss") + ".doc";

            response.setCharacterEncoding("UTF-8");
            //文件头，导出的文件名
            response.setHeader("Content-disposition", "attachment;filename=" + (StringUtils.isBlank(caseInfo.getCaseNo()) ? "" : caseInfo.getCaseNo()) + new String(filename.getBytes("GBK"), "ISO-8859-1"));
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
