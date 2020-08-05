package com.bazl.hslims.web.controller.center.examine;

import com.bazl.hslims.common.Constants;
import com.bazl.hslims.manager.PageInfo;
import com.bazl.hslims.manager.model.po.*;
import com.bazl.hslims.manager.model.vo.LimsCaseInfoVO;
import com.bazl.hslims.manager.services.center.CenterLoginService;
import com.bazl.hslims.manager.services.common.*;
import com.bazl.hslims.utils.DateUtils;
import com.bazl.hslims.utils.ListUtils;
import com.bazl.hslims.utils.SystemUtil;
import com.bazl.hslims.web.controller.BaseController;
import com.bazl.hslims.web.datamodel.SyRecordDataModel;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by Administrator on 2017/1/4.
 */
@Controller
@RequestMapping("/center/sy")
public class SyRecordController extends BaseController {

    @Autowired
    LimsCaseInfoService limsCaseInfoService;
    @Autowired
    LimsSampleInfoService limsSampleInfoService;
    @Autowired
    LimsSyRecordService limsSyRecordService;
    @Autowired
    LimsPcrRecordService limsPcrRecordService;
    @Autowired
    DictService dictService;
    @Autowired
    LabUserService labUserService;
    @Autowired
    DelegateOrgService delegateOrgService;
    @Autowired
    CenterLoginService centerLoginService;
    @Autowired
    LimsQuickExamineRecordService limsQuickExamineRecordService;
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
        //List<LimsCaseInfoVO> caseInfoVOList = limsCaseInfoService.selectVOPaginationList(query, pageInfo);
        List<LimsCaseInfoVO> caseInfoVOList = limsCaseInfoService.selectVOAllList(query, pageInfo);
        for (LimsCaseInfoVO caseInfo : caseInfoVOList) {
            caseInfo.setSyRecordCount(limsSyRecordService.selectCountByCaseId(caseInfo.getEntity().getId()));
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
                /*int count = limsSyRecordService.selectCountBySampleNo(cno);*/
                List<LimsSyRecord> limsSyRecordList = limsSyRecordService.selectSampleNoByTaskId(cno);
                List<LimsSyRecordSample> limsSyRecordSampleList = limsSyRecordService.selectListBySampleNo(cno);
                if (limsSyRecordList.size() != 0) {
                    caseInfo.setAppSyRecordCount(limsSyRecordList.size());
                    caseInfo.setCno(cno);
                    for (LimsSyRecordSample extractRecordSample : limsSyRecordSampleList) {
                        LimsSyRecord limsSyRecord = limsSyRecordService.selectById(extractRecordSample.getLimsSyRecordId());
                        caseInfo.setTaskId(limsSyRecord.getTaskId());
                        if (extractRecordSample.getLimsSyRecordId() != 0) {
                            caseInfo.setSyRecordId(extractRecordSample.getLimsSyRecordId().toString());
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
        modelAndView.setViewName("center/examine/syRecordList");
        return modelAndView;
    }


    @RequestMapping("/add.html")
    public ModelAndView add(HttpServletRequest request, Integer caseId) {
        //扩增相关字典
        ModelAndView modelAndView = initDict();

        LimsSyRecord limsSyRecord = new LimsSyRecord();
        limsSyRecord.setCaseId(caseId);
        limsSyRecord.setNeibiaoFlag("02");
        LabUser labUser1 = centerLoginService.selectByLoginName(Constants.EXPERIMENTER_2);
        if (labUser1 != null) {
            limsSyRecord.setOperatePersonId1(labUser1.getId());
            limsSyRecord.setOperatePersonName1(labUser1.getUserName());
        }

        LabUser labUser2 = centerLoginService.selectByLoginName(Constants.EXPERIMENTER_1);
        if (labUser2 != null) {
            limsSyRecord.setOperatePersonId2(labUser2.getId());
            limsSyRecord.setOperatePersonName2(labUser2.getUserName());
        }

        long currentTimeMillis = System.currentTimeMillis();
        limsSyRecord.setStartDatetime(new Date(currentTimeMillis));
        limsSyRecord.setEndDatetime(new Date(currentTimeMillis + (2 * 60 * 60 * 1000 + 30 * 60 * 1000)));

        List<LimsSampleInfo> sampleInfolist = limsSampleInfoService.selectAcceptedListByCaseId(caseId);
        List<LimsSyRecordSample> syRecordSampleList = new ArrayList<>();
        List<LimsPcrRecord> limsPcrRecordList = limsPcrRecordService.selectListByCaseId(caseId);
        String[] positionArr = Constants.SYTABLE_POSTION_ARR_VER;
        LimsSyRecordSample syRecordSample = null;
        int count = 0;
        if (limsPcrRecordList.size() > 0) {
            for (int i = 0; i < sampleInfolist.size(); i++) {
                for (LimsPcrRecord limsPcrRecord : limsPcrRecordList) {
                    List<LimsPcrRecordSample> limsPcrRecordSamples = limsPcrRecordService.selectSampleListByPcrRecordId(limsPcrRecord.getId());
                    for (LimsPcrRecordSample limsPcrRecordSample : limsPcrRecordSamples) {
                        syRecordSample = new LimsSyRecordSample();
                        syRecordSample.setSampleId(sampleInfolist.get(i).getId());
                        syRecordSample.setSampleNo(sampleInfolist.get(i).getSampleNo());
                        syRecordSample.setSampleName(sampleInfolist.get(i).getSampleName());
                        syRecordSample.setSampleTypeName(sampleInfolist.get(i).getSampleTypeName());
                        if (limsPcrRecordSample.getSampleNo().equals(sampleInfolist.get(i).getSampleNo())) {
                            syRecordSample.setSamplePosition(limsPcrRecordSample.getSamplePosition());
                            break;
                        }
                    }
                    syRecordSampleList.add(syRecordSample);
                }
            }

        } else {
            for (int i = 0; i < sampleInfolist.size(); i++) {
                syRecordSample = new LimsSyRecordSample();
                syRecordSample.setSampleId(sampleInfolist.get(i).getId());
                syRecordSample.setSampleNo(sampleInfolist.get(i).getSampleNo());
                syRecordSample.setSampleName(sampleInfolist.get(i).getSampleName());
                syRecordSample.setSampleTypeName(sampleInfolist.get(i).getSampleTypeName());
                if (i > 95) {
                    count = (i % 95) - 1;
                    syRecordSample.setSamplePosition(positionArr[count]);
                } else {
                    syRecordSample.setSamplePosition(positionArr[i]);
                }

                syRecordSampleList.add(syRecordSample);
            }
        }
        LimsPcrRecord limsPcrRecord = new LimsPcrRecord();

        if (ListUtils.isNotNullAndEmptyList(limsPcrRecordList)) {
            Date pcrTime = limsPcrRecordList.get(0).getPcrEndDatetime();
            limsPcrRecord.setPcrEndDatetime(pcrTime);
        }

        modelAndView.addObject("operateType", Constants.OPERATE_TYPE_ADD);
        modelAndView.addObject("limsSyRecordSampleList", syRecordSampleList);
        modelAndView.addObject("limsSyRecord", limsSyRecord);
        modelAndView.addObject("limsPcrRecord", limsPcrRecord);
        modelAndView.addObject("samplePositionList", Constants.SYTABLE_POSTION_ARR_VER);
        modelAndView.setViewName("center/examine/syInfo");
        return modelAndView;
    }


    @RequestMapping("/viewSyRecord.html")
    public ModelAndView viewSyRecord(HttpServletRequest request, Integer caseId) {
        List<LimsSyRecord> limsSyRecordList = limsSyRecordService.selectListByCaseId(caseId);

        //int extractRecordCount = limsPcrRecordService.selectCountByCaseId(caseId);
        if (limsSyRecordList.size() == 1) {
            return viewOne(limsSyRecordList.get(0));
        } else {
            return viewList(limsSyRecordList, caseId);
        }
    }

    @RequestMapping("/viewAppSyRecord.html")
    public ModelAndView viewAppSyRecord(HttpServletRequest request, Integer caseId, String cno) {
        List<LimsSyRecord> limsSyRecordList = limsSyRecordService.selectSampleNoByTaskId(cno);

        List<DictItem> elecInstrumentList = dictService.selectDictItemListByType(Constants.DICT_TYPE_ELEC_INSTRUMENT);
        for (LimsSyRecord limsSyRecord : limsSyRecordList) {
            for (DictItem di : elecInstrumentList) {
                if (di.getDictCode().equals(limsSyRecord.getElecInstrument())) {
                    limsSyRecord.setElecInstrumentName(di.getDictName());
                    break;
                }
            }
        }

        LimsPcrRecord limsPcrRecord = new LimsPcrRecord();
        List<LimsPcrRecord> limsPcrRecordList = limsPcrRecordService.selectListByCaseId(caseId);
        if (ListUtils.isNotNullAndEmptyList(limsPcrRecordList)) {
            Date pcrTime = limsPcrRecordList.get(0).getPcrEndDatetime();
            limsPcrRecord.setPcrEndDatetime(pcrTime);
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("operateType", Constants.OPERATE_TYPE_EDIT);
        modelAndView.addObject("caseId", caseId);
        modelAndView.addObject("limsPcrRecord", limsPcrRecord);
        modelAndView.addObject("limsSyRecordList", limsSyRecordList);
        modelAndView.setViewName("center/examine/viewSyList");
        return modelAndView;
    }

    private ModelAndView viewOne(LimsSyRecord limsSyRecord) {
        List<DictItem> elecInstrumentList = dictService.selectDictItemListByType(Constants.DICT_TYPE_ELEC_INSTRUMENT);
        for (DictItem di : elecInstrumentList) {
            if (di.getDictCode().equals(limsSyRecord.getElecInstrument())) {
                limsSyRecord.setElecInstrumentName(di.getDictName());
                break;
            }
        }

        //扩增相关字典
        ModelAndView modelAndView = initDict();
        List<LimsSyRecordSample> limsSyRecordSampleList = limsSyRecordService.selectSampleListBySyRecordId(limsSyRecord.getId());

        LimsPcrRecord limsPcrRecord = new LimsPcrRecord();
        List<LimsPcrRecord> limsPcrRecordList = limsPcrRecordService.selectListByCaseId(limsSyRecord.getCaseId());
        if (ListUtils.isNotNullAndEmptyList(limsPcrRecordList)) {
            Date pcrTime = limsPcrRecordList.get(0).getPcrEndDatetime();
            limsPcrRecord.setPcrEndDatetime(pcrTime);
        }

        modelAndView.addObject("operateType", Constants.OPERATE_TYPE_EDIT);
        modelAndView.addObject("caseId", limsSyRecord.getCaseId());
        modelAndView.addObject("limsSyRecord", limsSyRecord);
        modelAndView.addObject("limsPcrRecord", limsPcrRecord);
        modelAndView.addObject("limsSyRecordSampleList", limsSyRecordSampleList);
        modelAndView.addObject("samplePositionList", Constants.SYTABLE_POSTION_ARR_VER);
        modelAndView.setViewName("center/examine/syInfo");
        return modelAndView;
    }

    private ModelAndView viewList(List<LimsSyRecord> limsSyRecordList, Integer caseId) {
        List<DictItem> elecInstrumentList = dictService.selectDictItemListByType(Constants.DICT_TYPE_ELEC_INSTRUMENT);
        for (LimsSyRecord limsSyRecord : limsSyRecordList) {
            for (DictItem di : elecInstrumentList) {
                if (di.getDictCode().equals(limsSyRecord.getElecInstrument())) {
                    limsSyRecord.setElecInstrumentName(di.getDictName());
                    break;
                }
            }
        }

        LimsPcrRecord limsPcrRecord = new LimsPcrRecord();
        List<LimsPcrRecord> limsPcrRecordList = limsPcrRecordService.selectListByCaseId(caseId);
        if (ListUtils.isNotNullAndEmptyList(limsPcrRecordList)) {
            Date pcrTime = limsPcrRecordList.get(0).getPcrEndDatetime();
            limsPcrRecord.setPcrEndDatetime(pcrTime);
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("operateType", Constants.OPERATE_TYPE_EDIT);
        modelAndView.addObject("caseId", caseId);
        modelAndView.addObject("limsPcrRecord", limsPcrRecord);
        modelAndView.addObject("limsSyRecordList", limsSyRecordList);
        modelAndView.setViewName("center/examine/viewSyList");
        return modelAndView;
    }


    @RequestMapping("/edit.html")
    public ModelAndView edit(HttpServletRequest request, Integer syRecordId) {
        //扩增相关字典
        ModelAndView modelAndView = initDict();

        LimsSyRecord limsSyRecord = limsSyRecordService.selectById(syRecordId);
        List<LimsSyRecordSample> limsSyRecordSampleList = limsSyRecordService.selectSampleListBySyRecordId(syRecordId);

        modelAndView.addObject("operateType", Constants.OPERATE_TYPE_EDIT);
//        modelAndView.addObject("positionArr", Constants.SYTABLE_POSTION_ARR);
        //modelAndView.addObject("extractMethodList", extractMethodList);
        modelAndView.addObject("limsSyRecord", limsSyRecord);
        modelAndView.addObject("caseId", limsSyRecord.getCaseId());
//        for(LimsSyRecordSample lss : limsSyRecordSampleList){
//            modelAndView.addObject(lss.getSamplePosition(), lss);
//        }
        modelAndView.addObject("limsSyRecordSampleList", limsSyRecordSampleList);
        modelAndView.addObject("samplePositionList", Constants.SYTABLE_POSTION_ARR_VER);
        modelAndView.setViewName("center/examine/syInfo");
        return modelAndView;
    }

    @RequestMapping("/upLoadSyTable.html")
    public
    @ResponseBody
    Map<String, Object> upLoadSyTable(HttpServletRequest request, HttpServletResponse response,
                                      @RequestParam("syFile") MultipartFile syFile, Integer caseId) {
        Map<String, Object> resultMap = new HashMap<>();

        List<LimsSyRecordSample> syRecordSampleList = new ArrayList<>();

        String boardNo = null;
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(syFile.getInputStream()));

            String str = null;
            br.readLine();
            boardNo = br.readLine();
            //跳过3行
            for (int i = 0; i < 3; i++) {
                br.readLine();
            }

            String sampleNo = null;
            String samplePostion = null;
            LimsSyRecordSample sample = null;

            while ((str = br.readLine()) != null) {
                sample = new LimsSyRecordSample();
                try {
                    String[] sampleLine = str.split("\t");
                    samplePostion = sampleLine[0];
                    sampleNo = sampleLine[1] + "";

                    sample = new LimsSyRecordSample();
                    sample.setSamplePosition(samplePostion);
                    sample.setSampleNo(sampleNo);
                    //校验条码是否存在
                    List<LimsSampleInfo> sampleInDbList = limsSampleInfoService.selectListBySampleNo(sampleNo.trim().toUpperCase());

                    if (ListUtils.isNotNullAndEmptyList(sampleInDbList)) {
                        LimsSampleInfo sampleInDb = sampleInDbList.get(0);

                        if (caseId.equals(sampleInDb.getCaseId())) {

                            if (sample.getSampleNo().equals(sampleInDb.getSampleNo())) {

                                LimsSyRecordSample syRecordSample = null;

                                syRecordSample = new LimsSyRecordSample();

                                syRecordSample.setSampleId(sampleInDb.getId());
                                syRecordSample.setSampleNo(sample.getSampleNo());
                                syRecordSample.setSampleName(sampleInDb.getSampleName());
                                syRecordSample.setSampleTypeName(sampleInDb.getSampleTypeName());
                                syRecordSample.setSamplePosition(sample.getSamplePosition());

                                syRecordSampleList.add(syRecordSample);
                            }

                        }

                    }

                } catch (Exception ee) {
                    logger.error("解析上样表数据行错误！ lineStr=[" + str + "].", ee);
                    continue;
                }
            }
        } catch (Exception ex) {
            logger.error("解析上样表错误！", ex);
            resultMap.put("success", false);
            resultMap.put("errMsg", "上样表格式无法解析！");
            return resultMap;
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (Exception ex) {
                }
            }
        }

        resultMap.put("success", true);
        resultMap.put("syRecordSampleList", syRecordSampleList);
        resultMap.put("samplePositionList", Constants.SYTABLE_POSTION_ARR_VER);
        return resultMap;
    }

    @RequestMapping("/uploadSyTable.html")
    public
    @ResponseBody
    Map<String, Object> uploadSyTable(HttpServletRequest request, HttpServletResponse response,
                                      @RequestParam("sytableFile") MultipartFile sytableFile) {
        Map<String, Object> resultMap = new HashMap<>();

        Map<Integer, List<LimsSyRecordSample>> caseSampleMap = new HashMap<Integer, List<LimsSyRecordSample>>();
        List<LimsSyRecordSample> notExistsSampleList = new ArrayList<>();
        int sampleCount = 0;
        String boardNo = null;
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(sytableFile.getInputStream()));

            String str = null;
            br.readLine();
            boardNo = br.readLine();
            //跳过3行
            for (int i = 0; i < 3; i++) {
                br.readLine();
            }

            String sampleNo = null;
            String samplePostion = null;
            LimsSyRecordSample sample = null;
            LimsCaseInfo caseInfo = null;
            List<LimsSyRecordSample> samplesOfCase = null;

            while ((str = br.readLine()) != null) {
                sample = new LimsSyRecordSample();
                try {
                    String[] sampleLine = str.split("\t");
                    samplePostion = sampleLine[0];
                    sampleNo = sampleLine[1] + "";

                    sample = new LimsSyRecordSample();
                    sample.setSamplePosition(samplePostion);
                    sample.setSampleNo(sampleNo);
                    //标准样本不用去数据库校验条码号是否存在
                    if (Constants.SY_STANDARD_SAMPLE_LIST.contains(sampleNo.trim().toUpperCase())) {
//                        sample.setStandardFlag(Constants.FLAG_TRUE);
//                        syRecordSampleList.add(sample);
                        continue;
                    }

                    sampleCount++;

                    //校验条码是否存在
                    List<LimsSampleInfo> sampleInDbList = limsSampleInfoService.selectListBySampleNo(sampleNo.trim().toUpperCase());
                    if (ListUtils.isNotNullAndEmptyList(sampleInDbList)) {
                        LimsSampleInfo sampleInDb = sampleInDbList.get(0);

                        sample.setSampleId(sampleInDb.getId());
                        sample.setStandardFlag(Constants.FLAG_FALSE);

                        if (!caseSampleMap.containsKey(sampleInDb.getCaseId())) {
                            samplesOfCase = new ArrayList<>();
                        } else {
                            samplesOfCase = caseSampleMap.get(sampleInDb.getCaseId());
                        }

                        samplesOfCase.add(sample);
                        caseSampleMap.put(sampleInDb.getCaseId(), samplesOfCase);
                    } else {
                        //反馈条码号不存在的数据
                        notExistsSampleList.add(sample);
                    }

                } catch (Exception ee) {
                    logger.error("解析上样表数据行错误！ lineStr=[" + str + "].", ee);
                    continue;
                }
            }
        } catch (Exception ex) {
            logger.error("解析上样表错误！", ex);
            resultMap.put("success", false);
            resultMap.put("errMsg", "上样表格式无法解析！");
            return resultMap;
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (Exception ex) {
                }
            }
        }

        List<Map<String, String>> caseInfoList = new ArrayList<Map<String, String>>();
        SyRecordDataModel dataModel = null;
        for (Map.Entry<Integer, List<LimsSyRecordSample>> entry : caseSampleMap.entrySet()) {
            dataModel = new SyRecordDataModel();
            LimsSyRecord syRecord = new LimsSyRecord();
            syRecord.setCaseId(entry.getKey());
            dataModel.setLimsSyRecord(syRecord);
            dataModel.setLimsSyRecordSampleList(entry.getValue());

            limsSyRecordService.addRecord(dataModel, boardNo);

            LimsCaseInfo caseInfo = limsCaseInfoService.selectById(entry.getKey());
            Map<String, String> caseInfoMap = new HashMap<>();
            caseInfoMap.put("caseId", caseInfo.getId() + "");
            caseInfoMap.put("caseNo", caseInfo.getCaseNo());
            caseInfoMap.put("caseName", caseInfo.getCaseName());
            caseInfoMap.put("sampleCount", entry.getValue().size() + "");

            caseInfoList.add(caseInfoMap);
        }

        resultMap.put("success", true);
        resultMap.put("caseInfoList", caseInfoList);
        resultMap.put("notExistsSampleList", notExistsSampleList);
        resultMap.put("caseCount", caseInfoList.size());
        resultMap.put("sampleCount", sampleCount);
        resultMap.put("failedCount", notExistsSampleList.size());
        return resultMap;
    }


    private ModelAndView initDict() {
        ModelAndView modelAndView = new ModelAndView();

        /*  字典 */
        List<DictItem> chanwuList = dictService.selectDictItemListByType(Constants.DICT_TYPE_CHANWU_UL);
        List<DictItem> jiaxiananList = dictService.selectDictItemListByType(Constants.DICT_TYPE_JIAXIANAN_UL);
        List<DictItem> neibiaoulList = dictService.selectDictItemListByType(Constants.DICT_TYPE_NEIBIAO_UL);
        List<DictItem> neibiaoList = dictService.selectDictItemListByType(Constants.DICT_TYPE_NEIBIAO);
//        List<DictItem> elecInstrumentList = dictService.selectDictItemListByType(Constants.DICT_TYPE_ELEC_INSTRUMENT);
        List<EquipmentNameInfo> elecInstrumentList = equipmentNameInfoService.selectEquipmentNameInfoListByTypeNo(Constants.EQUIPMENT_TYPE_INFO_DYY);

        modelAndView.addObject("chanwuList", chanwuList);
        modelAndView.addObject("jiaxiananList", jiaxiananList);
        modelAndView.addObject("neibiaoulList", neibiaoulList);
        modelAndView.addObject("neibiaoList", neibiaoList);
        modelAndView.addObject("elecInstrumentList", elecInstrumentList);

        /*  实验室人员 */
        List<LabUser> labUserList = labUserService.selectAll();
        modelAndView.addObject("labUserList", labUserList);

        return modelAndView;
    }

    @RequestMapping("/delByRecordId.html")
    @ResponseBody
    public Map<String, Object> delByRecordId(HttpServletRequest request, Integer syRecordId) {

        limsSyRecordService.deleteById(syRecordId);

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("success", true);
        return result;
    }


    @RequestMapping("/delByCaseId.html")
    @ResponseBody
    public Map<String, Object> delByCaseId(HttpServletRequest request, Integer caseId) {
        List<LimsSyRecord> recordList = limsSyRecordService.selectListByCaseId(caseId);
        for (LimsSyRecord record : recordList) {
            limsSyRecordService.deleteById(record.getId());
        }

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("success", true);
        return result;
    }


    @RequestMapping(value = "/saveRecord.html", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> saveRecord(HttpServletRequest request, @RequestBody SyRecordDataModel syRecordDataModel, String operateType) {

        Integer syRecordId = null;

        String syRecordIds = "";

        LimsSyRecord record = syRecordDataModel.getLimsSyRecord();

        String boardNo = record.getBoardNo();
        String[] boardNoStr = boardNo.split(",");

        for (int i = 0; i < boardNoStr.length; i++) {

            if (Constants.OPERATE_TYPE_ADD.equals(operateType)) {
                syRecordId = limsSyRecordService.addRecord(syRecordDataModel, boardNoStr[i]);
            } else {
                syRecordId = limsSyRecordService.updateRecord(syRecordDataModel, boardNoStr[i]);
            }

            syRecordIds += syRecordId + ",";
        }

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("success", true);
        result.put("syRecordIds", syRecordIds);
        return result;
    }


    @RequestMapping("/downloadSytable.html")
    public void downloadSytable(HttpServletRequest request, HttpServletResponse response,
                                Integer syRecordId, Integer caseId) {
        LimsSyRecord limsSyRecord = null;

        if (syRecordId != null && syRecordId != 0) {
            limsSyRecord = limsSyRecordService.selectById(syRecordId);

            downloadSYtable(response, limsSyRecord);
        }

        if (caseId != null && caseId != 0) {
            List<LimsSyRecord> recordList = limsSyRecordService.selectListByCaseId(caseId);
            List<File> fileList = new ArrayList<File>();

            String filePath = null;
            if (recordList.size() > 1) {
                for (int i = 0; i < recordList.size(); i++) {
                    limsSyRecord = recordList.get(i);

                    filePath = downloadtableSy(response, limsSyRecord.getId());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (StringUtils.isNotBlank(filePath))
                        fileList.add(new File(filePath));
                }

                LimsCaseInfo caseInfo = limsCaseInfoService.selectById(limsSyRecord.getCaseId());
                String writeFilePath = null;
                String zipName = caseInfo.getCaseNo() + DateUtils.dateToString(new Date(), "yyyyMMddHHmmss") + ".zip";
                FileOutputStream fos = null;
                ZipOutputStream zos = null;
                writeFilePath = SystemUtil.getSystemConfig().getProperty("DownloadPath");
                try {
                    writeFilePath = makePathFile(writeFilePath) + zipName;
                    File zipFile = new File(writeFilePath);
                    if (!zipFile.exists()) {
                        zipFile.createNewFile();
                    }

                    //创建文件输出流
                    fos = new FileOutputStream(zipFile);
                    /**打包的方法我们会用到ZipOutputStream这样一个输出流,
                     * 所以这里我们把输出流转换一下*/
                    zos = new ZipOutputStream(fos);
                    /**这个方法接受的就是一个所要打包文件的集合，
                     * 还有一个ZipOutputStream*/
                    zipFiles(fileList, zos);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    //关闭流
                    try {
                        if (null != zos) zos.close();
                        if (null != fos) fos.close();

                        for (int i = 0; i < fileList.size(); i++) {
                            File file = (File) fileList.get(i);
                            if (file.exists())
                                file.delete();
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                        throw new RuntimeException(e);
                    }
                }

                downLoadZip(response, writeFilePath);
            } else {
                limsSyRecord = recordList.get(0);

                downloadSYtable(response, limsSyRecord);
            }
        }
    }

    private String downloadtableSy(HttpServletResponse response, Integer syRecordId) {
        String filePathName = null;

        LimsSyRecord limsSyRecord = null;

        if (syRecordId != null && syRecordId != 0) {
            limsSyRecord = limsSyRecordService.selectById(syRecordId);
        }

        List<LimsSyRecordSample> sampleInfoList = limsSyRecordService.selectSampleListBySyRecordId(limsSyRecord.getId());
        LimsCaseInfo caseInfo = limsCaseInfoService.selectById(limsSyRecord.getCaseId());

        StringBuffer buffer = new StringBuffer();
        BufferedWriter bufferedWriter = null;
        FileWriter fileWriter = null;
        try {
            String filename = "-上样表" + DateUtils.dateToString(new Date(), "yyyyMMddHHmmss") + ".txt";

            buffer.append("Container Name" + "\t" + "Plate ID" + "\t" + "Description" + "\t" + "ContainerType" + "\t" + "AppType" + "\t" + "Owner" + "\t" + "Operator" + "\t" + "PlateSealing" + "\t" + "SchedulingPref" + "\n");
            buffer.append((StringUtils.isBlank(limsSyRecord.getBoardNo()) ? limsSyRecord.getSyTaskNo() : limsSyRecord.getBoardNo()) + "\t" + (StringUtils.isBlank(limsSyRecord.getBoardNo()) ? limsSyRecord.getSyTaskNo() : limsSyRecord.getBoardNo()) + "\t" + "" + "\t" + "96-Well" + "\t" + "Regular" + "\t" + "1" + "\t" + "1" + "\t" + "Septa" + "\t" + "1234" + "\n");
            buffer.append("AppServer" + "\t" + "AppInstance" + "\n");
            buffer.append("GeneMapper" + "\t" + "GeneMapper_Generic_Instance" + "\n");

            buffer.append("Well" + "\t" + "Sample Name" + "\t" + "Comment" + "\t" + "Size Standard" + "\t" + "Snp Set" + "\t" + "User-Defined 3" + "\t"
                    + "User-Defined 2" + "\t" + "User-Defined 1" + "\t" + "Panel" + "\t" + "Study" + "\t" + "Sample Type" + "\t" + "Analysis Method"
                    + "\t" + "Results Group 1" + "\t" + "Instrument Protocol 1" + "\n");

            for (String positionStr : Constants.SYTABLE_POSTION_ARR_VER) {
                for (LimsSyRecordSample boardSampleMap : sampleInfoList) {
                    if (positionStr.equals(boardSampleMap.getSamplePosition())) {
                        buffer.append(boardSampleMap.getSamplePosition() + "\t"
                                + boardSampleMap.getSampleNo() + "\t"
                                + "" + "\t" + "" + "\t" + "" + "\t"
                                + "" + "\t" + "" + "\t" + "" + "\t" + "" + "\t"
                                + "" + "\t" + "" + "\t" + "" + "\t"
                                + "DNA_Results_Group" + "\t"
                                + "DNATyper-G5-Run" + "\n");
                        break;
                    }
                }
            }
            fileWriter = new FileWriter(getGeneratePath(caseInfo, filename));
            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(buffer.toString());

            filePathName = getGeneratePath(caseInfo, filename);
        } catch (Exception ex) {
            logger.error("生成上样表错误！", ex);
        } finally {
            if (bufferedWriter != null) {
                try {
                    bufferedWriter.flush();
                    bufferedWriter.close();
                } catch (Exception ex) {
                }
            }
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (Exception ex) {
                }
            }
        }

        return filePathName;
    }

   /* public void downloadSYtable(HttpServletResponse response, LimsSyRecord limsSyRecord) {
        List<LimsSyRecordSample> sampleInfoList = limsSyRecordService.selectSampleListBySyRecordId(limsSyRecord.getId());


        StringBuffer buffer = new StringBuffer();
        BufferedOutputStream  bos = null;
        try {
            String filename = "-上样表"+ DateUtils.dateToString(new Date(),"yyyyMMddHHmmss") +".txt";

            response.setContentType("text/plain");
            response.setHeader("Content-Disposition","attachment; filename=" + (StringUtils.isBlank(limsSyRecord.getBoardNo()) ? limsSyRecord.getSyTaskNo() : limsSyRecord.getBoardNo()) + new String(filename.getBytes("GBK"),"ISO-8859-1"));

            bos = new BufferedOutputStream(response.getOutputStream());

            buffer.append("Container Name" + "\t" + "Plate ID" + "\t" + "Description" + "\t" + "ContainerType" + "\t" + "AppType" + "\t" + "Owner" + "\t" + "Operator" + "\t" + "PlateSealing" + "\t" + "SchedulingPref" + "\n");
            buffer.append((StringUtils.isBlank(limsSyRecord.getBoardNo()) ? limsSyRecord.getSyTaskNo() : limsSyRecord.getBoardNo()) + "\t" + (StringUtils.isBlank(limsSyRecord.getBoardNo()) ? limsSyRecord.getSyTaskNo() : limsSyRecord.getBoardNo()) + "\t" + "" + "\t" + "96-Well" + "\t" + "Regular" + "\t" + "1" + "\t" + "1" + "\t" + "Septa" + "\t" + "1234" + "\n");
            buffer.append("AppServer" + "\t" + "AppInstance" + "\n");
            buffer.append("GeneMapper" + "\t" + "GeneMapper_Generic_Instance" + "\n");

            buffer.append("Well" + "\t" + "Sample Name" + "\t" + "Comment" + "\t" + "Size Standard" + "\t" + "Snp Set" + "\t" + "User-Defined 3" + "\t"
                    + "User-Defined 2" + "\t" + "User-Defined 1" + "\t" + "Panel" + "\t" + "Study" + "\t" + "Sample Type" + "\t" + "Analysis Method"
                    + "\t" + "Results Group 1" + "\t" + "Instrument Protocol 1" + "\n");

            for (String positionStr : Constants.SYTABLE_POSTION_ARR_VER) {
                for (LimsSyRecordSample boardSampleMap : sampleInfoList) {
                    if (positionStr.equals(boardSampleMap.getSamplePosition())) {
                        buffer.append(boardSampleMap.getSamplePosition() + "\t"
                                + boardSampleMap.getSampleNo() + "\t"
                                + "" + "\t" + "" + "\t" + "" + "\t"
                                + "" + "\t" + "" + "\t" + "" + "\t" + "" + "\t"
                                + "" + "\t" + "" + "\t" + "" + "\t"
                                + "DNA_Results_Group" + "\t"
                                + "DNATyper-G5-Run" + "\n");
                        break;
                    }
                }
            }

            bos.write(buffer.toString().getBytes("UTF-8"));
            bos.flush();
        }catch(Exception ex){
            logger.error("生成上样表错误！", ex);
        }finally{
            if(bos != null) {
                try {
                    bos.close();
                }catch(Exception ex){}
            }
        }
    }*/

    public void downloadSYtable(HttpServletResponse response, LimsSyRecord limsSyRecord) {
        List<LimsSyRecordSample> sampleInfoList = limsSyRecordService.selectSampleListBySyRecordId(limsSyRecord.getId());

        StringBuffer buffer = new StringBuffer();
        BufferedOutputStream bos = null;
        try {
            String filename = limsSyRecord.getBoardNo() + ".txt";

            response.setContentType("text/plain");
            response.setHeader("Content-Disposition", "attachment; filename=" + new String(filename.getBytes("GBK"), "ISO-8859-1"));

            bos = new BufferedOutputStream(response.getOutputStream());

            buffer.append("3500 Plate Layout File Version 1.0" + "\n" + "\n");
            buffer.append("Plate Name\tApplication Type\tCapillary Length (cm)\tPolymer\tNumber of Wells\tOwner Name\tBarcode Number\tComments" + "\n");
            buffer.append((StringUtils.isBlank(limsSyRecord.getBoardNo()) ? limsSyRecord.getSyTaskNo() : limsSyRecord.getBoardNo()) + "\t" + "\t" + "HID" + "\t" + "36" + "\t" + "POP4" + "\t" + "96" + "\n" + "\n");
            buffer.append("Well\tSample Name\tAssay\tResults Group\tFile Name Convention\tSample Type\tUser Defined Field 1\tUser Defined Field 2\tUser" + "\n");
            buffer.append("Defined Field 3\tUser Defined Field 4\tUser Defined Field 5\tComments" + "\n");

            for (String positionStr : Constants.SYTABLE_POSTION_ARR_VER) {
                for (LimsSyRecordSample boardSampleMap : sampleInfoList) {
                    if (positionStr.equals(boardSampleMap.getSamplePosition())) {
                        buffer.append(boardSampleMap.getSamplePosition()
                                + "\t" + boardSampleMap.getSampleNo()
                                + "\t" + "" + "\t" + "" + "\t"
                                + "" + "\t" + "" + "\t" + "" + "\t" + "" + "\t"
                                + "" + "\n");
                        break;
                    }
                }
            }

            bos.write(buffer.toString().getBytes("UTF-8"));
            bos.flush();
        } catch (Exception ex) {
            logger.error("生成上样表错误！", ex);
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (Exception ex) {
                }
            }
        }
    }

    @RequestMapping("/syDoc.html")
    public void generateAndDownloadExtractDoc(HttpServletRequest request, HttpServletResponse response,
                                              String syRecordId, Integer caseId) {
        List<LimsQuickExamineRecord> quickExamineRecordList = limsQuickExamineRecordService.selectListByCaseId(caseId);
        if (ListUtils.isNotNullAndEmptyList(quickExamineRecordList) && StringUtils.isBlank(syRecordId)) {
            syRecordId = quickExamineRecordList.get(0).getLimsSyRecordId();
        }

        LimsSyRecord limsSyRecord = null;
        String filePath = null;
        List<File> fileList = new ArrayList<File>();
        if (StringUtils.isNotBlank(syRecordId)) {
            String[] syRecordIdStr = syRecordId.split(",");
            if (syRecordIdStr.length > 1) {
                for (int i = 0; i < syRecordIdStr.length; i++) {
                    limsSyRecord = limsSyRecordService.selectById(Integer.parseInt(syRecordIdStr[i]));

                    filePath = generateAndDownloadSyDoc(request, limsSyRecord);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (StringUtils.isNotBlank(filePath))
                        fileList.add(new File(filePath));
                }

                LimsCaseInfo caseInfo = limsCaseInfoService.selectById(limsSyRecord.getCaseId());
                String writeFilePath = null;
                String zipName = caseInfo.getCaseNo() + DateUtils.dateToString(new Date(), "yyyyMMddHHmmss") + ".zip";
                FileOutputStream fos = null;
                ZipOutputStream zos = null;
                writeFilePath = SystemUtil.getSystemConfig().getProperty("DownloadPath");
                try {
                    writeFilePath = makePathFile(writeFilePath) + zipName;
                    File zipFile = new File(writeFilePath);
                    if (!zipFile.exists()) {
                        zipFile.createNewFile();
                    }

                    //创建文件输出流
                    fos = new FileOutputStream(zipFile);
                    /**打包的方法我们会用到ZipOutputStream这样一个输出流,
                     * 所以这里我们把输出流转换一下*/
                    zos = new ZipOutputStream(fos);
                    /**这个方法接受的就是一个所要打包文件的集合，
                     * 还有一个ZipOutputStream*/
                    zipFiles(fileList, zos);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    //关闭流
                    try {
                        if (null != zos) zos.close();
                        if (null != fos) fos.close();

                        for (int i = 0; i < fileList.size(); i++) {
                            File file = (File) fileList.get(i);
                            if (file.exists())
                                file.delete();
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                        throw new RuntimeException(e);
                    }
                }

                downLoadZip(response, writeFilePath);
            } else {
                limsSyRecord = limsSyRecordService.selectById(Integer.parseInt(syRecordIdStr[0]));
                downloadSyDoc(request, response, limsSyRecord);
            }
        } else {
            if (caseId != null && caseId != 0) {
                List<LimsSyRecord> recordList = limsSyRecordService.selectListByCaseId(caseId);
                if (recordList.size() > 1) {
                    for (int i = 0; i < recordList.size(); i++) {
                        limsSyRecord = recordList.get(i);

                        filePath = generateAndDownloadSyDoc(request, limsSyRecord);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (StringUtils.isNotBlank(filePath))
                            fileList.add(new File(filePath));
                    }

                    LimsCaseInfo caseInfo = limsCaseInfoService.selectById(limsSyRecord.getCaseId());
                    String writeFilePath = null;
                    String zipName = caseInfo.getCaseNo() + DateUtils.dateToString(new Date(), "yyyyMMddHHmmss") + ".zip";
                    FileOutputStream fos = null;
                    ZipOutputStream zos = null;
                    writeFilePath = SystemUtil.getSystemConfig().getProperty("DownloadPath");
                    try {
                        writeFilePath = makePathFile(writeFilePath) + zipName;
                        File zipFile = new File(writeFilePath);
                        if (!zipFile.exists()) {
                            zipFile.createNewFile();
                        }

                        //创建文件输出流
                        fos = new FileOutputStream(zipFile);
                        /**打包的方法我们会用到ZipOutputStream这样一个输出流,
                         * 所以这里我们把输出流转换一下*/
                        zos = new ZipOutputStream(fos);
                        /**这个方法接受的就是一个所要打包文件的集合，
                         * 还有一个ZipOutputStream*/
                        zipFiles(fileList, zos);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        //关闭流
                        try {
                            if (null != zos) zos.close();
                            if (null != fos) fos.close();

                            for (int i = 0; i < fileList.size(); i++) {
                                File file = (File) fileList.get(i);
                                if (file.exists())
                                    file.delete();
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                            throw new RuntimeException(e);
                        }
                    }

                    downLoadZip(response, writeFilePath);
                } else {
                    limsSyRecord = recordList.get(0);

                    downloadSyDoc(request, response, limsSyRecord);
                }
            }
        }
    }

    @RequestMapping("/appSyDoc.html")
    public void generateAndDownloadAppExtractDoc(HttpServletRequest request, HttpServletResponse response,
                                                 String syRecordId, Integer caseId, Integer taskId) {
        List<LimsQuickExamineRecord> quickExamineRecordList = limsQuickExamineRecordService.selectListByCaseId(caseId);
        if (ListUtils.isNotNullAndEmptyList(quickExamineRecordList) && StringUtils.isBlank(syRecordId)) {
            syRecordId = quickExamineRecordList.get(0).getLimsSyRecordId();
        }
        if (taskId != null) {
            LimsSyRecord syRecord = limsSyRecordService.selectByTaskId(taskId);
            syRecordId = syRecord.getId().toString();
        }

        LimsSyRecord limsSyRecord = null;
        String filePath = null;
        List<File> fileList = new ArrayList<File>();
        if (StringUtils.isNotBlank(syRecordId)) {
            String[] syRecordIdStr = syRecordId.split(",");
            if (syRecordIdStr.length > 1) {
                for (int i = 0; i < syRecordIdStr.length; i++) {
                    limsSyRecord = limsSyRecordService.selectById(Integer.parseInt(syRecordIdStr[i]));

                    filePath = generateAndDownloadSyDoc(request, limsSyRecord);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (StringUtils.isNotBlank(filePath))
                        fileList.add(new File(filePath));
                }

                LimsCaseInfo caseInfo = limsCaseInfoService.selectById(limsSyRecord.getCaseId());
                String writeFilePath = null;
                String zipName = caseInfo.getCaseNo() + DateUtils.dateToString(new Date(), "yyyyMMddHHmmss") + ".zip";
                FileOutputStream fos = null;
                ZipOutputStream zos = null;
                writeFilePath = SystemUtil.getSystemConfig().getProperty("DownloadPath");
                try {
                    writeFilePath = makePathFile(writeFilePath) + zipName;
                    File zipFile = new File(writeFilePath);
                    if (!zipFile.exists()) {
                        zipFile.createNewFile();
                    }

                    //创建文件输出流
                    fos = new FileOutputStream(zipFile);
                    /**打包的方法我们会用到ZipOutputStream这样一个输出流,
                     * 所以这里我们把输出流转换一下*/
                    zos = new ZipOutputStream(fos);
                    /**这个方法接受的就是一个所要打包文件的集合，
                     * 还有一个ZipOutputStream*/
                    zipFiles(fileList, zos);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    //关闭流
                    try {
                        if (null != zos) zos.close();
                        if (null != fos) fos.close();

                        for (int i = 0; i < fileList.size(); i++) {
                            File file = (File) fileList.get(i);
                            if (file.exists())
                                file.delete();
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                        throw new RuntimeException(e);
                    }
                }

                downLoadZip(response, writeFilePath);
            } else {
                limsSyRecord = limsSyRecordService.selectById(Integer.parseInt(syRecordIdStr[0]));
                 /*limsSyRecord = limsSyRecordService.selectByTaskId(taskId);*/
                downloadAppSyDoc(request, response, limsSyRecord, caseId, taskId);
            }
        } else {
            if (caseId != null && caseId != 0) {
                List<LimsSyRecord> recordList = limsSyRecordService.selectListByCaseId(caseId);
                if (recordList.size() > 1) {
                    for (int i = 0; i < recordList.size(); i++) {
                        limsSyRecord = recordList.get(i);

                        filePath = generateAndDownloadSyDoc(request, limsSyRecord);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (StringUtils.isNotBlank(filePath))
                            fileList.add(new File(filePath));
                    }

                    LimsCaseInfo caseInfo = limsCaseInfoService.selectById(limsSyRecord.getCaseId());
                    String writeFilePath = null;
                    String zipName = caseInfo.getCaseNo() + DateUtils.dateToString(new Date(), "yyyyMMddHHmmss") + ".zip";
                    FileOutputStream fos = null;
                    ZipOutputStream zos = null;
                    writeFilePath = SystemUtil.getSystemConfig().getProperty("DownloadPath");
                    try {
                        writeFilePath = makePathFile(writeFilePath) + zipName;
                        File zipFile = new File(writeFilePath);
                        if (!zipFile.exists()) {
                            zipFile.createNewFile();
                        }

                        //创建文件输出流
                        fos = new FileOutputStream(zipFile);
                        /**打包的方法我们会用到ZipOutputStream这样一个输出流,
                         * 所以这里我们把输出流转换一下*/
                        zos = new ZipOutputStream(fos);
                        /**这个方法接受的就是一个所要打包文件的集合，
                         * 还有一个ZipOutputStream*/
                        zipFiles(fileList, zos);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        //关闭流
                        try {
                            if (null != zos) zos.close();
                            if (null != fos) fos.close();

                            for (int i = 0; i < fileList.size(); i++) {
                                File file = (File) fileList.get(i);
                                if (file.exists())
                                    file.delete();
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                            throw new RuntimeException(e);
                        }
                    }

                    downLoadZip(response, writeFilePath);
                } else {
                    limsSyRecord = recordList.get(0);

                    downloadSyDoc(request, response, limsSyRecord);
                }
            }
        }
    }

    private void downLoadZip(HttpServletResponse response, String zipFilePath) {
        try {
            File file = new File(zipFilePath);
            if (file.exists()) {
                InputStream ins = new FileInputStream(zipFilePath);
                BufferedInputStream bins = new BufferedInputStream(ins);// 放到缓冲流里面
                OutputStream outs = response.getOutputStream();// 获取文件输出IO流
                BufferedOutputStream bouts = new BufferedOutputStream(outs);
                response.setContentType("application/x-download");// 设置response内容的类型
                response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(file.getName(), "UTF-8"));// 设置头部信息
                int bytesRead = 0;
                byte[] buffer = new byte[8192];
                // 开始向网络传输文件流
                while ((bytesRead = bins.read(buffer, 0, 8192)) != -1) {
                    bouts.write(buffer, 0, bytesRead);
                }
                bouts.flush();// 这里一定要调用flush()方法
                ins.close();
                bins.close();
                outs.close();
                bouts.close();
            }
        } catch (IOException e) {
            logger.error("文件下载出错", e);
        }
    }

    /**
     * 把接受的全部文件打成压缩包
     */
    public static void zipFiles(List files, ZipOutputStream outputStream) {
        int size = files.size();
        for (int i = 0; i < size; i++) {
            File file = (File) files.get(i);
            zipFile(file, outputStream);
        }
    }

    /**
     * 根据输入的文件与输出流对文件进行打包
     */
    public static void zipFile(File inputFile, ZipOutputStream ouputStream) {
        try {
            if (inputFile.exists()) {
                /**如果是目录的话这里是不采取操作的，
                 * 至于目录的打包正在研究中*/
                if (inputFile.isFile()) {
                    FileInputStream IN = new FileInputStream(inputFile);
                    BufferedInputStream bins = new BufferedInputStream(IN, 512);
                    //org.apache.tools.zip.ZipEntry
                    ZipEntry entry = new ZipEntry(inputFile.getName());
                    ouputStream.putNextEntry(entry);
                    // 向压缩文件中输出数据
                    int nNumber;
                    byte[] buffer = new byte[512];
                    while ((nNumber = bins.read(buffer)) != -1) {
                        ouputStream.write(buffer, 0, nNumber);
                    }
                    // 关闭创建的流对象
                    bins.close();
                    IN.close();
                } else {
                    try {
                        File[] files = inputFile.listFiles();
                        for (int i = 0; i < files.length; i++) {
                            zipFile(files[i], ouputStream);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String generateAndDownloadSyDoc(HttpServletRequest request, LimsSyRecord limsSyRecord) {
        String filePathName = null;

        List<LimsSyRecordSample> sampleInfoList = limsSyRecordService.selectSampleListBySyRecordId(limsSyRecord.getId());
        LimsCaseInfo caseInfo = limsCaseInfoService.selectById(limsSyRecord.getCaseId());
        Map<String, Object> params = new HashMap<String, Object>();
        String caseNo = caseInfo.getCaseNo();
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
            logger.error("获取上样任务号错误", ex);
            params.put("year", "");
            params.put("no", "");
        }


        params.put("chanwuFlag", "");
        if (StringUtils.isNotBlank(limsSyRecord.getChanwuFlag())) {
            List<DictItem> chanwuList = dictService.selectDictItemListByType(Constants.DICT_TYPE_CHANWU_UL);
            for (DictItem di : chanwuList) {
                if (di.getDictCode().equals(limsSyRecord.getChanwuFlag())) {
                    params.put("chanwuFlag", di.getDictName());
                    break;
                }
            }
        }
        params.put("jiaxiananFlag", "");
        if (StringUtils.isNotBlank(limsSyRecord.getJiaxiananFlag())) {
            List<DictItem> jiaxiananList = dictService.selectDictItemListByType(Constants.DICT_TYPE_JIAXIANAN_UL);
            for (DictItem di : jiaxiananList) {
                if (di.getDictCode().equals(limsSyRecord.getChanwuFlag())) {
                    params.put("jiaxiananFlag", di.getDictName());
                    break;
                }
            }
        }
        params.put("neibiaoUlFlag", "");
        if (StringUtils.isNotBlank(limsSyRecord.getNeibiaoUlFlag())) {
            List<DictItem> neibiaoulList = dictService.selectDictItemListByType(Constants.DICT_TYPE_NEIBIAO_UL);
            for (DictItem di : neibiaoulList) {
                if (di.getDictCode().equals(limsSyRecord.getNeibiaoUlFlag())) {
                    params.put("neibiaoUlFlag", di.getDictName());
                    break;
                }
            }
        }
        params.put("neibiaoFlag", "");
        if (StringUtils.isNotBlank(limsSyRecord.getNeibiaoFlag())) {
            List<DictItem> neibiaoList = dictService.selectDictItemListByType(Constants.DICT_TYPE_NEIBIAO);
            for (DictItem di : neibiaoList) {
                if (di.getDictCode().equals(limsSyRecord.getNeibiaoFlag())) {
                    params.put("neibiaoFlag", di.getDictName());
                    break;
                }
            }
        }
        params.put("elecInstrument", "其他");
        if (StringUtils.isNotBlank(limsSyRecord.getElecInstrument())) {
            List<DictItem> elecInstrumentList = dictService.selectDictItemListByType(Constants.DICT_TYPE_ELEC_INSTRUMENT);
            for (DictItem di : elecInstrumentList) {
                if (di.getDictCode().equals(limsSyRecord.getElecInstrument())) {
                    params.put("elecInstrument", di.getDictName());
                    break;
                }
            }
        }

        params.put("startDateteime", limsSyRecord.getStartDatetime() == null ? "" : DateUtils.dateToString(limsSyRecord.getStartDatetime(), "yyyy.MM.dd HH:mm"));
        params.put("endDatetime", limsSyRecord.getEndDatetime() == null ? "" : DateUtils.dateToString(limsSyRecord.getEndDatetime(), "yyyy.MM.dd HH:mm"));
        params.put("operatePersonName",
                (StringUtils.isBlank(limsSyRecord.getOperatePersonName1()) ? "" : limsSyRecord.getOperatePersonName1())
                        + "、" + (StringUtils.isBlank(limsSyRecord.getOperatePersonName2()) ? "" : limsSyRecord.getOperatePersonName2())
        );

        for (String pos : Constants.SYTABLE_POSTION_ARR) {
            params.put(pos, "");
            for (LimsSyRecordSample lsrs : sampleInfoList) {
                if (StringUtils.isNotBlank(lsrs.getSamplePosition())
                        && pos.equals(lsrs.getSamplePosition().trim().toUpperCase())) {
                    params.put(pos, lsrs.getSampleNo());
                    break;
                }
            }
        }

        Writer out = null;
        try {
            Configuration cfg = new Configuration();
            cfg.setDefaultEncoding("UTF-8");
            ServletContext servletContext = request.getSession().getServletContext();
            cfg.setServletContextForTemplateLoading(servletContext, "/templates");
            //获取模板
            Template tmp = cfg.getTemplate("syRecord.ftl", "UTF-8");

            String filename = "-上样记录表" + DateUtils.dateToString(new Date(), "yyyyMMddHHmmss") + ".doc";

            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(getGeneratePath(caseInfo, filename)), "UTF-8"));
            tmp.process(params, out);

            filePathName = getGeneratePath(caseInfo, filename);
        } catch (Exception ex) {
            logger.error("", ex);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return filePathName;
    }

    private void downloadSyDoc(HttpServletRequest request, HttpServletResponse response, LimsSyRecord limsSyRecord) {

        List<LimsSyRecordSample> sampleInfoList = limsSyRecordService.selectSampleListBySyRecordId(limsSyRecord.getId());
        LimsCaseInfo caseInfo = limsCaseInfoService.selectById(limsSyRecord.getCaseId());
        Map<String, Object> params = new HashMap<String, Object>();
        String caseNo = caseInfo.getCaseNo();
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
            logger.error("获取上样任务号错误", ex);
            params.put("year", "");
            params.put("no", "");
        }

        //板号
        params.put("boardNo",limsSyRecord.getBoardNo());

        params.put("chanwuFlag", "");
        if (StringUtils.isNotBlank(limsSyRecord.getChanwuFlag())) {
            List<DictItem> chanwuList = dictService.selectDictItemListByType(Constants.DICT_TYPE_CHANWU_UL);
            for (DictItem di : chanwuList) {
                if (di.getDictCode().equals(limsSyRecord.getChanwuFlag())) {
                    params.put("chanwuFlag", di.getDictName());
                    break;
                }
            }
        }
        params.put("jiaxiananFlag", "");
        if (StringUtils.isNotBlank(limsSyRecord.getJiaxiananFlag())) {
            List<DictItem> jiaxiananList = dictService.selectDictItemListByType(Constants.DICT_TYPE_JIAXIANAN_UL);
            for (DictItem di : jiaxiananList) {
                if (di.getDictCode().equals(limsSyRecord.getJiaxiananFlag())) {
                    params.put("jiaxiananFlag", di.getDictName());
                    break;
                }
            }
        }
        params.put("neibiaoUlFlag", "");
        if (StringUtils.isNotBlank(limsSyRecord.getNeibiaoUlFlag())) {
            List<DictItem> neibiaoulList = dictService.selectDictItemListByType(Constants.DICT_TYPE_NEIBIAO_UL);
            for (DictItem di : neibiaoulList) {
                if (di.getDictCode().equals(limsSyRecord.getNeibiaoUlFlag())) {
                    params.put("neibiaoUlFlag", di.getDictName());
                    break;
                }
            }
        }
        params.put("neibiaoFlag", "");
        if (StringUtils.isNotBlank(limsSyRecord.getNeibiaoFlag())) {
            List<DictItem> neibiaoList = dictService.selectDictItemListByType(Constants.DICT_TYPE_NEIBIAO);
            for (DictItem di : neibiaoList) {
                if (di.getDictCode().equals(limsSyRecord.getNeibiaoFlag())) {
                    params.put("neibiaoFlag", di.getDictName());
                    break;
                }
            }
        }
        params.put("elecInstrument", "其他");
        if (StringUtils.isNotBlank(limsSyRecord.getElecInstrument())) {
            List<EquipmentNameInfo> elecInstrumentList = equipmentNameInfoService.selectEquipmentNameInfoListByTypeNo(Constants.EQUIPMENT_TYPE_INFO_DYY);
            for (EquipmentNameInfo ei : elecInstrumentList) {
                if (limsSyRecord.getElecInstrument().equals(ei.getId().toString())) {
                    params.put("elecInstrument", ei.getEquipmentNo());
                    params.put("elecInstrumentNo", ei.getEquipmentName() + "→" + ei.getEquipmentNo() + "号");
                    break;
                }
            }
        }

        params.put("startDateteime", limsSyRecord.getStartDatetime() == null ? "" : DateUtils.dateToString(limsSyRecord.getStartDatetime(), "yyyy.MM.dd HH:mm"));
        params.put("endDatetime", limsSyRecord.getEndDatetime() == null ? "" : DateUtils.dateToString(limsSyRecord.getEndDatetime(), "yyyy.MM.dd HH:mm"));
        params.put("operatePersonName",
                (StringUtils.isBlank(limsSyRecord.getOperatePersonName1()) ? "" : limsSyRecord.getOperatePersonName1())
                        + "、" + (StringUtils.isBlank(limsSyRecord.getOperatePersonName2()) ? "" : limsSyRecord.getOperatePersonName2())
        );

        for (String pos : Constants.SYTABLE_POSTION_ARR) {
            params.put(pos, "");
            for (LimsSyRecordSample lsrs : sampleInfoList) {
                if (StringUtils.isNotBlank(lsrs.getSamplePosition())
                        && pos.equals(lsrs.getSamplePosition().trim().toUpperCase())) {
                    params.put(pos, lsrs.getSampleNo());
                    break;
                }
            }
        }

        try {
            Configuration cfg = new Configuration();
            cfg.setDefaultEncoding("UTF-8");
            ServletContext servletContext = request.getSession().getServletContext();
            cfg.setServletContextForTemplateLoading(servletContext, "/templates");
            //获取模板
            Template tmp = cfg.getTemplate("syRecord.ftl", "UTF-8");

            String filename = "-上样记录表" + DateUtils.dateToString(new Date(), "yyyyMMddHHmmss") + ".doc";

            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + caseNo + new String(filename.getBytes("GBK"), "ISO-8859-1"));//文件头，导出的文件名
            response.setContentType("application/x-msdownload");//类型

            tmp.process(params, response.getWriter());
        } catch (Exception ex) {
            logger.error("", ex);
        }
    }

    private void downloadAppSyDoc(HttpServletRequest request, HttpServletResponse response, LimsSyRecord limsSyRecord, Integer caseId, Integer taskId) {
        LimsSyRecord syRecord = limsSyRecordService.selectByTaskId(taskId);
        List<LimsSyRecordSample> sampleInfoList = limsSyRecordService.selectSampleListBySyRecordId(syRecord.getId());

        List<LimsSyRecordSample> syRecordSampleList = new ArrayList<>();
        for (LimsSyRecordSample sampleInfo : sampleInfoList) {
            /*List<LimsSampleInfo> limsSampleInfoList = limsSampleInfoService.selectListByCaseId(caseId);
            for (LimsSampleInfo limsSampleInfo : limsSampleInfoList) {
                if (sampleInfo.getSampleNo().equals(limsSampleInfo.getSampleNo())) {*/
            syRecordSampleList.add(sampleInfo);
               /* }
            }*/
        }

        LimsCaseInfo caseInfo = limsCaseInfoService.selectById(caseId);
        Map<String, Object> params = new HashMap<String, Object>();
        String caseNo = caseInfo.getCaseNo();
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
            logger.error("获取上样任务号错误", ex);
            params.put("year", "");
            params.put("no", "");
        }


        params.put("chanwuFlag", "");
        if (StringUtils.isNotBlank(limsSyRecord.getChanwuFlag())) {
            List<DictItem> chanwuList = dictService.selectDictItemListByType(Constants.DICT_TYPE_CHANWU_UL);
            for (DictItem di : chanwuList) {
                if (di.getDictCode().equals(limsSyRecord.getChanwuFlag())) {
                    params.put("chanwuFlag", di.getDictName());
                    break;
                }
            }
        }
        params.put("jiaxiananFlag", "");
        if (StringUtils.isNotBlank(limsSyRecord.getJiaxiananFlag())) {
            List<DictItem> jiaxiananList = dictService.selectDictItemListByType(Constants.DICT_TYPE_JIAXIANAN_UL);
            for (DictItem di : jiaxiananList) {
                if (di.getDictCode().equals(limsSyRecord.getJiaxiananFlag())) {
                    params.put("jiaxiananFlag", di.getDictName());
                    break;
                }
            }
        }
        params.put("neibiaoUlFlag", "");
        if (StringUtils.isNotBlank(limsSyRecord.getNeibiaoUlFlag())) {
            List<DictItem> neibiaoulList = dictService.selectDictItemListByType(Constants.DICT_TYPE_NEIBIAO_UL);
            for (DictItem di : neibiaoulList) {
                if (di.getDictCode().equals(limsSyRecord.getNeibiaoUlFlag())) {
                    params.put("neibiaoUlFlag", di.getDictName());
                    break;
                }
            }
        }
        params.put("neibiaoFlag", "");
        if (StringUtils.isNotBlank(limsSyRecord.getNeibiaoFlag())) {
            List<DictItem> neibiaoList = dictService.selectDictItemListByType(Constants.DICT_TYPE_NEIBIAO);
            for (DictItem di : neibiaoList) {
                if (di.getDictCode().equals(limsSyRecord.getNeibiaoFlag())) {
                    params.put("neibiaoFlag", di.getDictName());
                    break;
                }
            }
        }
        params.put("elecInstrument", "其他");
        if (StringUtils.isNotBlank(limsSyRecord.getElecInstrument())) {
            List<EquipmentNameInfo> elecInstrumentList = equipmentNameInfoService.selectEquipmentNameInfoListByTypeNo(Constants.EQUIPMENT_TYPE_INFO_DYY);
            for (EquipmentNameInfo ei : elecInstrumentList) {

                if (limsSyRecord.getElecInstrument().equals(ei.getId().toString())) {
                    params.put("elecInstrument", ei.getEquipmentNo());
                    params.put("elecInstrumentNo", ei.getEquipmentName() + "→" + ei.getEquipmentNo() + "号");
                    break;
                }

            }
        }

        params.put("startDateteime", limsSyRecord.getStartDatetime() == null ? "" : DateUtils.dateToString(limsSyRecord.getStartDatetime(), "yyyy.MM.dd HH:mm"));
        params.put("endDatetime", limsSyRecord.getEndDatetime() == null ? "" : DateUtils.dateToString(limsSyRecord.getEndDatetime(), "yyyy.MM.dd HH:mm"));
        params.put("operatePersonName",
                (StringUtils.isBlank(limsSyRecord.getOperatePersonName1()) ? "" : limsSyRecord.getOperatePersonName1())
                        + "、" + (StringUtils.isBlank(limsSyRecord.getOperatePersonName2()) ? "" : limsSyRecord.getOperatePersonName2())
        );

        for (String pos : Constants.SYTABLE_POSTION_ARR) {
            params.put(pos, "");
            for (LimsSyRecordSample lsrs : syRecordSampleList) {
                if (StringUtils.isNotBlank(lsrs.getSamplePosition())
                        && pos.equals(lsrs.getSamplePosition().trim().toUpperCase())) {
                    params.put(pos, lsrs.getSampleNo());
                    break;
                }
                if (pos.equals("H12")) {
                    params.put(pos, "9947" + " 模板1ul");
                }
            }
        }

        try {
            Configuration cfg = new Configuration();
            cfg.setDefaultEncoding("UTF-8");
            ServletContext servletContext = request.getSession().getServletContext();
            cfg.setServletContextForTemplateLoading(servletContext, "/templates");
            //获取模板
            Template tmp = cfg.getTemplate("syRecord.ftl", "UTF-8");

            String filename = "-上样记录表" + DateUtils.dateToString(new Date(), "yyyyMMddHHmmss") + ".doc";

            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + caseNo + new String(filename.getBytes("GBK"), "ISO-8859-1"));//文件头，导出的文件名
            response.setContentType("application/x-msdownload");//类型

            tmp.process(params, response.getWriter());
        } catch (Exception ex) {
            logger.error("", ex);
        }
    }

    private String getGeneratePath(LimsCaseInfo caseInfo, String filename) {
        String writeFilePath = SystemUtil.getSystemConfig().getProperty("DownloadPath");
        writeFilePath = makePathFile(writeFilePath);

        String writeFileName = (StringUtils.isBlank(caseInfo.getCaseNo()) ? "" : caseInfo.getCaseNo()) + filename;

        String writeFile = "";
        if (writeFilePath.endsWith("\\") || writeFilePath.endsWith("/")) {
            writeFile += writeFilePath + writeFileName;
        } else {
            writeFile = writeFilePath + System.getProperty("file.separator") + writeFileName;
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

        return pathFile + "\\\\";
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
            caseInfoVO.getEntity().setCaseName(caseInfoVO.getEntity().getCaseName().trim());
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
            newTime = sd.parse(oldTime + " 23:59:59");
            caseInfoVO.setAcceptDateEnd(newTime);
        }

        return caseInfoVO;
    }

}
