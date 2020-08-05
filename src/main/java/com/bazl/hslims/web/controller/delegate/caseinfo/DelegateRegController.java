package com.bazl.hslims.web.controller.delegate.caseinfo;

import com.bazl.hslims.common.Constants;
import com.bazl.hslims.manager.PageInfo;
import com.bazl.hslims.manager.model.po.*;
import com.bazl.hslims.manager.model.vo.SceneInfoVO;
import com.bazl.hslims.manager.services.center.SceneInvestigationService;
import com.bazl.hslims.manager.services.common.*;
import com.bazl.hslims.utils.DateUtils;
import com.bazl.hslims.utils.ListUtils;
import com.bazl.hslims.utils.SystemUtil;
import com.bazl.hslims.web.controller.BaseController;
import com.bazl.hslims.web.datamodel.ConsignmentDataModel;
import com.bazl.hslims.web.helper.CosignmentInfoHelper;
import com.bazl.hslims.web.security.LimsSecurityUtils;
import com.bazl.hslims.webservice.client.SceneManagerPortType;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2017/1/2.
 */
@Controller
@RequestMapping("/wt/reg")
public class DelegateRegController extends BaseController {

    @Autowired
    DictService dictService;
    @Autowired
    DelegateOrgService delegateOrgService;
    @Autowired
    LimsCaseInfoService limsCaseInfoService;
    @Autowired
    LimsConsignmentService limsConsignmentService;
    @Autowired
    LimsPersonInfoService limsPersonInfoService;
    @Autowired
    LimsSampleInfoService limsSampleInfoService;
    @Autowired
    IdentifyKernelService identifyKernelService;
    @Autowired
    LimsConsignmentInfoService limsConsignmentInfoService;
    @Autowired
    LimsCaseInformationService limsCaseInformationService;
    @Autowired
    LimsPersonSampleService limsPersonSampleService;

    @Autowired
    SceneInvestigationService sceneInvestigationService;
    @Autowired
    SceneManagerPortType sceneManagerPortType;

    private final Logger log = LoggerFactory.getLogger(getClass());

    /**
     * @param request
     * @param caseId         案件ID
     * @param additionalFlag 是否为补送
     * @return
     */
    @RequestMapping("/1.html")
    public ModelAndView reg(HttpServletRequest request, Integer caseId, String additionalFlag, String caseXkNo) {
        ModelAndView modelAndView = initDict();

        List<LimsConsignmentInfo> consignmentInfoList = limsConsignmentInfoService.selectListByDelegateOrgId(LimsSecurityUtils.getLoginDelegateOrg().getId());
        LimsConsignment consignment = new LimsConsignment();
        if (ListUtils.isNotNullAndEmptyList(consignmentInfoList)) {
            LimsConsignmentInfo consignmentInfo = consignmentInfoList.get(0);

            consignment.setDelegateOrgDesc(consignmentInfo.getDelegateOrgName());
        }
        //各委托单位默认委托人
        consignment = delegator(consignment);
        LimsCaseInfo caseInfo = new LimsCaseInfo();
        if (StringUtils.isNotBlank(caseXkNo)) {
            caseInfo.setCaseXkNo(caseXkNo);
        }

        modelAndView.addObject("operateType", Constants.OPERATE_TYPE_ADD);
        modelAndView.addObject("loginTitleName", SystemUtil.getSystemConfig().getProperty("loginTitleName"));
        modelAndView.addObject("consignment", consignment);
        modelAndView.addObject("caseInfo", caseInfo);
        modelAndView.setViewName("delegate/caseinfo/identifyCaseReg");
        return modelAndView;
    }

    //解析返回的现勘数据
    @RequestMapping(value = "/analyticalXk.html", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    //@RequestMapping(value="/analyticalXk",method=RequestMethod.POST)
    public Map<String, Object> analyticalXk(HttpServletRequest request, HttpServletResponse response, @RequestParam String paramsXk) {
        Map<String, Object> map = new HashMap();
        ModelAndView modelAndView = initDict();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        this.logger.error("现勘返回数据" + paramsXk);

        LimsCaseInfo caseInfo = new LimsCaseInfo();
        LimsConsignment consignment = new LimsConsignment();
        List<LimsSampleInfo> limsSampleInfoList = new ArrayList<>();
        try {
            //解析现堪返回数据
            JSONObject jsonObject = JSONObject.fromObject(paramsXk);

            JSONObject data = jsonObject.getJSONObject("data");
            //案件基本详情
            JSONObject anjianjibenxinxi = data.getJSONObject("anjianjibenxinxi");
            //勘察基本信息
            JSONObject kanchajibenxinxi = data.getJSONObject("kanchajibenxinxi");
            //痕迹物证
            JSONObject hengjiwuzheng = data.getJSONObject("hengjiwuzheng");
            //生物物证
            JSONArray shengwu = hengjiwuzheng.getJSONArray("shengwu");

            for (int i = 0; i < anjianjibenxinxi.size(); i++) {
                //案件受理号
                String anjianshoulihao = anjianjibenxinxi.get("anjianshoulihao").toString();
                //案件类别
                String anjianleibie = anjianjibenxinxi.get("anjianleibie").toString();
                //发案区划
                String faanquhua = anjianjibenxinxi.get("faanquhua").toString();
                //犯案时间
                String faanshijian = anjianjibenxinxi.get("faanshijian").toString();
                String shijian = faanshijian.substring(0, 16);
                caseInfo.setCaseDatetime(sdf.parse(shijian));
                //是否命案
                String shifoumingan = anjianjibenxinxi.get("shifoumingan").toString();
                //是否刑案
                String shifouxingan = anjianjibenxinxi.get("shifouxingan").toString();

                if (anjianleibie.contains("死") || anjianleibie.contains("杀人")) {
                    caseInfo.setCaseProperty("01");
                } else if (anjianleibie.contains("强奸")) {
                    caseInfo.setCaseProperty("02");
                } else if (anjianleibie.contains("碎尸")) {
                    caseInfo.setCaseProperty("03");
                } else if (anjianleibie.contains("爆炸")) {
                    caseInfo.setCaseProperty("04");
                } else if (anjianleibie.contains("抢劫")) {
                    caseInfo.setCaseProperty("05");
                } else if (anjianleibie.contains("民事")) {
                    caseInfo.setCaseProperty("06");
                } else if (anjianleibie.contains("交通")) {
                    caseInfo.setCaseProperty("07");
                } else if (anjianleibie.contains("入室盗窃")) {
                    caseInfo.setCaseProperty("08");
                } else if (anjianleibie.contains("盗窃机动车")) {
                    caseInfo.setCaseProperty("09");
                } else if (anjianleibie.contains("盗窃车内财物")) {
                    caseInfo.setCaseProperty("10");
                } else if (anjianleibie.contains("盗窃工地")) {
                    caseInfo.setCaseProperty("11");
                } else if (anjianleibie.contains("盗窃")) {
                    caseInfo.setCaseProperty("12");
                } else if (anjianleibie.contains("伤害")) {
                    caseInfo.setCaseProperty("13");
                } else if (anjianleibie.contains("纵火")) {
                    caseInfo.setCaseProperty("14");
                } else if (anjianleibie.contains("投毒")) {
                    caseInfo.setCaseProperty("15");
                } else if (anjianleibie.contains("毒品")) {
                    caseInfo.setCaseProperty("16");
                } else if (anjianleibie.contains("意外")) {
                    caseInfo.setCaseProperty("17");
                } else if (anjianleibie.contains("劫持")) {
                    caseInfo.setCaseProperty("18");
                } else if (anjianleibie.contains("绑架")) {
                    caseInfo.setCaseProperty("19");
                } else if (anjianleibie.contains("诈骗")) {
                    caseInfo.setCaseProperty("20");
                } else if (anjianleibie.contains("敲诈勒索")) {
                    caseInfo.setCaseProperty("21");
                } else if (anjianleibie.contains("焚尸")) {
                    caseInfo.setCaseProperty("22");
                } else if (anjianleibie.contains("轮奸")) {
                    caseInfo.setCaseProperty("23");
                } else {
                    caseInfo.setCaseProperty("24");
                }
                if (shifouxingan.equals("是")) {
                    caseInfo.setCaseType("1");
                } else {
                    caseInfo.setCaseType("2");
                }
                break;
            }

            for (int i = 0; i < kanchajibenxinxi.size(); i++) {
                //勘验检查情况
                String kanyanjianchaqingkuang = kanchajibenxinxi.get("kanyanjianchaqingkuang").toString();
                //勘验地点
                String kanyandidian = kanchajibenxinxi.get("kanyandidian").toString();
                caseInfo.setCaseLocation(kanyandidian);
                caseInfo.setCaseBrief(kanyanjianchaqingkuang);
                break;
            }

            for (int i = 0; i < shengwu.size(); i++) {
                JSONObject jsonObj = shengwu.getJSONObject(i);
                for (int j = 0; j < jsonObj.size(); j++) {
                    LimsSampleInfo sampleInfo = new LimsSampleInfo();
                    String leixing = jsonObj.get("leixing").toString();
                    String jibentezheng = jsonObj.get("jibentezheng").toString();
                    String yiliubuwei = jsonObj.get("yiliubuwei").toString();
                    String tiqufangfa = jsonObj.get("tiqufangfa").toString();
                    String tiquren = jsonObj.get("tiquren").toString();
                    String tiquriqi = jsonObj.get("tiquriqi").toString();

                    sampleInfo.setSampleType("01");
                    sampleInfo.setSampleTypeName(leixing);
                    sampleInfo.setSampleName("测试现堪物证检材名称");
                    //物证编号
                    sampleInfo.setEvidenceNo("W11022905030020181000122001003");
                    sampleInfo.setExtractDatetime(sdf.parse(tiquriqi));
                    sampleInfo.setSampleProperties("9999");
                    sampleInfo.setOtherPropertiesDesc(jibentezheng);

                    sampleInfo.setExtractPerson(tiquren);
                    sampleInfo.setSampleDesc(yiliubuwei);
                    sampleInfo.setSamplePacking("物证袋");
                    sampleInfo.setSampleFlag("0");
                    sampleInfo.setDeleteFlag("0");
                    limsSampleInfoList.add(sampleInfo);
                    break;
                }
            }
        } catch (Exception ex) {
            this.logger.error("转换现勘数据失败" + ex.getMessage());
        }

        List<LimsConsignmentInfo> consignmentInfoList = limsConsignmentInfoService.selectListByDelegateOrgId(LimsSecurityUtils.getLoginDelegateOrg().getId());

        if (ListUtils.isNotNullAndEmptyList(consignmentInfoList)) {
            LimsConsignmentInfo consignmentInfo = consignmentInfoList.get(0);
            consignment.setDelegateOrgDesc(consignmentInfo.getDelegateOrgName());
        }

        map.put("caseInfo", caseInfo);
        map.put("limsSampleInfoList", limsSampleInfoList);
        map.put("consignment", consignment);
        map.put("PARAMS_OPERATE_TYPE", Constants.OPERATE_TYPE_ADD);
        map.put("loginTitleName", SystemUtil.getSystemConfig().getProperty("loginTitleName"));
        return map;
    }

    //刷新现堪物证
    @RequestMapping(value = "/refreshXk.html", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> refreshXk(HttpServletRequest request, HttpServletResponse response, @RequestParam String paramsXk, String consignmentId) {
        Map<String, Object> map = new HashMap();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        this.logger.error("现勘返回数据" + paramsXk);

        List<LimsSampleInfo> limsSampleInfoList = new ArrayList<>();
        List<LimsSampleInfo> newSampleList = new ArrayList<>();
        try {
            //解析现堪返回数据
            JSONObject jsonObject = JSONObject.fromObject(paramsXk);
            JSONObject data = jsonObject.getJSONObject("data");
            //痕迹物证
            JSONObject hengjiwuzheng = data.getJSONObject("hengjiwuzheng");
            //生物物证
            JSONArray shengwu = hengjiwuzheng.getJSONArray("shengwu");

            for (int i = 0; i < shengwu.size(); i++) {
                JSONObject jsonObj = shengwu.getJSONObject(i);
                for (int j = 0; j < jsonObj.size(); j++) {
                    LimsSampleInfo sampleInfo = new LimsSampleInfo();
                    String leixing = jsonObj.get("leixing").toString();
                    String jibentezheng = jsonObj.get("jibentezheng").toString();
                    String yiliubuwei = jsonObj.get("yiliubuwei").toString();
                    String tiqufangfa = jsonObj.get("tiqufangfa").toString();
                    String tiquren = jsonObj.get("tiquren").toString();
                    String tiquriqi = jsonObj.get("tiquriqi").toString();

                    sampleInfo.setSampleType(leixing);
                    sampleInfo.setSampleName("测试现堪物证检材名称");
                    //物证编号
                    sampleInfo.setEvidenceNo("W11022905030020181000122001003");
                    sampleInfo.setExtractDatetime(sdf.parse(tiquriqi));
                    sampleInfo.setSampleProperties("9999");
                    sampleInfo.setOtherPropertiesDesc(jibentezheng);

                    sampleInfo.setExtractPerson(tiquren);
                    sampleInfo.setSampleDesc(yiliubuwei);
                    sampleInfo.setSamplePacking("物证袋");
                    sampleInfo.setSampleFlag("0");
                    sampleInfo.setDeleteFlag("0");
                    limsSampleInfoList.add(sampleInfo);
                    break;
                }
            }

            List<LimsSampleInfo> sampleInfoList = limsSampleInfoService.selectListByConsignmentId(Integer.parseInt(consignmentId));

            for (LimsSampleInfo sampleInfo : sampleInfoList) {
                for (LimsSampleInfo limsSampleInfo : limsSampleInfoList) {
                    if (!sampleInfo.getEvidenceNo().equals(limsSampleInfo.getEvidenceNo())) {
                        newSampleList.add(limsSampleInfo);
                    }
                }
            }

        } catch (Exception ex) {
            this.logger.error("转换现勘数据失败" + ex.getMessage());
        }
        map.put("limsSampleInfoListCnt", newSampleList.size());
        map.put("limsSampleInfoList", newSampleList);
        return map;
    }

    /**
     * @param request
     * @param caseId         案件ID
     * @param additionalFlag 是否为补送
     * @return
     */
    @RequestMapping("/supply.html")
    public ModelAndView supply(HttpServletRequest request, Integer caseId, String additionalFlag) {
        ModelAndView modelAndView = initDict();

        LimsCaseInfo caseInfo = limsCaseInfoService.selectById(caseId);
        LimsConsignment consignment = null;
        List<LimsConsignment> consignmentList = limsConsignmentService.selectListByCaseId(caseId);
        for (LimsConsignment consign : consignmentList) {
            if (consign.getAdditionalFlag().equals(Constants.FLAG_FALSE)) {
                consignment = consign;
                break;
            }
        }

        List<LimsPersonInfo> personInfoList = limsPersonInfoService.selectListByCaseId(caseId);
        List<LimsSampleInfo> sampleInfoList = limsSampleInfoService.selectListByCaseId(caseId);

       /* LimsSampleInfo tmpSample = null;
        //去掉未受理的检材
        for(Iterator<LimsSampleInfo> it = sampleInfoList.iterator(); it.hasNext();){
            tmpSample = it.next();
            if(!tmpSample.getAcceptStatus().equals(Constants.SAMPLE_ACCEPT_STATUS_ACCEPTED)){
                it.remove();
            }
        }*/

        String xkLoginName = null;
        String xkLoginPassword = null;

        String code = LimsSecurityUtils.getLoginDelegateOrg().getOrgCode();
        if (code.equals(Constants.LOGIN_QXJ_ORG_CODE)) {
            xkLoginName = SystemUtil.getSystemConfig().getProperty("QXJloginName");
            xkLoginPassword = SystemUtil.getSystemConfig().getProperty("QXJpassword");
        } else if (code.equals(Constants.LOGIN_DF_ORG_CODE)) {
            xkLoginName = SystemUtil.getSystemConfig().getProperty("DFloginName");
            xkLoginPassword = SystemUtil.getSystemConfig().getProperty("DFpassword");
        } else if (code.equals(Constants.LOGIN_QX_ORG_CODE)) {
            xkLoginName = SystemUtil.getSystemConfig().getProperty("QXloginName");
            xkLoginPassword = SystemUtil.getSystemConfig().getProperty("QXpassword");
        } else if (code.equals(Constants.LOGIN_JS_ORG_CODE)) {
            xkLoginName = SystemUtil.getSystemConfig().getProperty("JSloginName");
            xkLoginPassword = SystemUtil.getSystemConfig().getProperty("JSpassword");
        } else if (code.equals(Constants.LOGIN_ZJ_ORG_CODE)) {
            xkLoginName = SystemUtil.getSystemConfig().getProperty("ZJloginName");
            xkLoginPassword = SystemUtil.getSystemConfig().getProperty("ZJpassword");
        } else if (code.equals(Constants.LOGIN_HZ_ORG_CODE)) {
            xkLoginName = SystemUtil.getSystemConfig().getProperty("HZloginName");
            xkLoginPassword = SystemUtil.getSystemConfig().getProperty("HZpassword");
        } else if (code.equals(Constants.LOGIN_WN_ORG_CODE)) {
            xkLoginName = SystemUtil.getSystemConfig().getProperty("WNloginName");
            xkLoginPassword = SystemUtil.getSystemConfig().getProperty("WNpassword");
        } else if (code.equals(Constants.LOGIN_BLDJ_ORG_CODE)) {
            xkLoginName = SystemUtil.getSystemConfig().getProperty("BLloginName");
            xkLoginPassword = SystemUtil.getSystemConfig().getProperty("BLpassword");
        } else if (code.equals(Constants.LOGIN_JHH_ORG_CODE)) {
            xkLoginName = SystemUtil.getSystemConfig().getProperty("JHHloginName");
            xkLoginPassword = SystemUtil.getSystemConfig().getProperty("JHHpassword");
        } else {
            xkLoginName = SystemUtil.getSystemConfig().getProperty("SJloginName");
            xkLoginPassword = SystemUtil.getSystemConfig().getProperty("SJpassword");
        }

        modelAndView.addObject("xkLoginName", xkLoginName);
        modelAndView.addObject("xkLoginPassword", xkLoginPassword);

        modelAndView.addObject("caseInfo", caseInfo);
        modelAndView.addObject("consignment", consignment);
        modelAndView.addObject("personInfoList", personInfoList);
        modelAndView.addObject("sampleInfoList", sampleInfoList);
        modelAndView.addObject("additionalFlag", Constants.FLAG_TRUE);
        modelAndView.addObject(PARAMS_OPERATE_TYPE, Constants.OPERATE_TYPE_ADD);
        modelAndView.addObject("loginTitleName", SystemUtil.getSystemConfig().getProperty("loginTitleName"));
        modelAndView.setViewName("delegate/caseinfo/identifyAddRegSupply");
        return modelAndView;
    }


    @RequestMapping(value = "/submit.html", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String submit(HttpServletRequest request, HttpServletResponse response, @RequestParam String operateType,
                         @RequestBody ConsignmentDataModel consignmentDataModel) {
        boolean result = false;
        Integer resultl = 0;
        try {
            resultl = limsCaseInfoService.identifyReg(operateType, consignmentDataModel);
        } catch (Exception ex) {
            result = false;
        }
        return "{\"consignmentId\":" + resultl + "}";
    }

    @RequestMapping(value = "/submitCase.html", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> submitCase(HttpServletRequest request, HttpServletResponse response, @RequestParam String operateType,
                                          @RequestBody ConsignmentDataModel consignmentDataModel) {
        boolean result = false;
        Map<String, Object> map = null;
        try {
            map = limsCaseInfoService.identifyCaseReg(operateType, consignmentDataModel);
            result = true;
        } catch (Exception ex) {
            result = false;
        }
        map.put("result", result);
        return map;
    }

    @RequestMapping(value = "/submitPerson.html", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> submitPerson(HttpServletRequest request, HttpServletResponse response, @RequestParam String operateType,
                                            @RequestBody ConsignmentDataModel consignmentDataModel) {
        boolean result = false;
        Map<String, Object> map = null;
        try {
            map = limsCaseInfoService.identifyPersonReg(operateType, consignmentDataModel);
            result = true;
        } catch (Exception ex) {
            result = false;
        }
        map.put("result", result);
        return map;
    }

    @RequestMapping(value = "/submitSample.html", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> submitSample(HttpServletRequest request, HttpServletResponse response, @RequestParam String operateType,
                                            @RequestBody ConsignmentDataModel consignmentDataModel) {
        boolean result = false;
        Map<String, Object> map = null;
        try {
            map = limsCaseInfoService.identifySampleReg(operateType, consignmentDataModel);
            result = true;
        } catch (Exception ex) {
            result = false;
        }
        map.put("result", result);
        return map;
    }

    @RequestMapping("/identifySampleReg.html")
    public ModelAndView identifySampleReg(HttpServletRequest request, LimsConsignment consignment, String operateType) {
        ModelAndView modelAndView = initDict();

        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("consignmentId", consignment.getId());
        dataMap.put("caseId", consignment.getCaseId());
        dataMap.put("status", consignment.getStatus());
        dataMap.put("operateType", operateType);

        List<LimsPersonInfo> personInfoList = limsPersonInfoService.selectListByConsignmentId(consignment.getId());
        List<LimsSampleInfo> sampleInfoList = limsSampleInfoService.selectListByConsignmentId(consignment.getId());

        modelAndView.addObject("dataMap", dataMap);
        modelAndView.addObject("personInfoList", personInfoList);
        modelAndView.addObject("sampleInfoList", sampleInfoList);
        modelAndView.addObject("loginTitleName", SystemUtil.getSystemConfig().getProperty("loginTitleName"));
        modelAndView.setViewName("delegate/caseinfo/identifySampleReg");
        return modelAndView;
    }

    @RequestMapping(value = "/submitPersonSupply.html", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> submitPersonSupply(HttpServletRequest request, HttpServletResponse response, @RequestParam Integer newConsignmentId,
                                                  @RequestBody ConsignmentDataModel consignmentDataModel) {
        boolean result = false;
        Map<String, Object> map = null;
        try {
            map = limsCaseInfoService.identifyPersonRegSupply(newConsignmentId, consignmentDataModel);
            result = true;
        } catch (Exception ex) {
            result = false;
        }

        map.put("result", result);

        return map;
    }

    @RequestMapping(value = "/submitEditPersonSupply.html", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> submitEditPersonSupply(HttpServletRequest request, HttpServletResponse response, @RequestParam String operateType,
                                                      @RequestBody ConsignmentDataModel consignmentDataModel) {
        boolean result = false;
        Map<String, Object> map = null;
        try {
            map = limsCaseInfoService.identifyEditPersonRegSupply(operateType, consignmentDataModel);
            result = true;
        } catch (Exception ex) {
            result = false;
        }

        map.put("result", result);

        return map;
    }

    @RequestMapping(value = "/submitEditSampleSupply.html", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> submitEditSampleSupply(HttpServletRequest request, HttpServletResponse response, @RequestParam String operateType,
                                                      @RequestBody ConsignmentDataModel consignmentDataModel) {
        boolean result = false;
        Map<String, Object> map = null;
        try {
            map = limsCaseInfoService.identifyEditSampleRegSupply(operateType, consignmentDataModel);
            result = true;
        } catch (Exception ex) {
            result = false;
        }
        map.put("result", result);
        return map;
    }

    @RequestMapping(value = "/submitSampleSupply.html", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> submitSampleSupply(HttpServletRequest request, HttpServletResponse response, @RequestParam Integer newConsignmentId,
                                                  @RequestBody ConsignmentDataModel consignmentDataModel) {
        boolean result = false;
        Map<String, Object> map = null;
        try {
            map = limsCaseInfoService.identifySampleRegSupply(newConsignmentId, consignmentDataModel);
            result = true;
        } catch (Exception ex) {
            result = false;
        }

        map.put("result", result);

        return map;
    }

    @RequestMapping(value = "/submitSupply.html", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public String submitSupply(HttpServletRequest request, HttpServletResponse response, @RequestParam String operateType,
                               @RequestBody ConsignmentDataModel consignmentDataModel) {
        boolean result = false;
        Integer resultl = 0;
        try {
            resultl = limsCaseInfoService.identifyRegSupply(operateType, consignmentDataModel);
        } catch (Exception ex) {
            result = false;
        }
        return "{\"consignmentId\":" + resultl + "}";
    }


    public ModelAndView initDict() {
        ModelAndView modelAndView = new ModelAndView();

        /*  字典 */
        List<DictItem> caseTypeList = dictService.selectDictItemListByType(Constants.DICT_TPYE_CASE_TYPE);
        List<DictItem> personRaceList = dictService.selectDictItemListByType(Constants.DICT_TPYE_PERSON_RACE);
        List<DictItem> casePropertyList = dictService.selectDictItemListByType(Constants.DICT_TPYE_CASE_PROPERTY);
        List<DictItem> caseLevelList = dictService.selectDictItemListByType(Constants.DICT_TPYE_CASE_LEVEL);
        List<DictItem> personTypeList = dictService.selectDictItemListByType(Constants.DICT_TPYE_PERSON_TYPE);
        List<DictItem> personRelationList = dictService.selectDictItemListByType(Constants.DICT_TPYE_PERSON_RELATION);
        List<DictItem> sampleTypeList = dictService.selectDictItemListByType(Constants.DICT_TPYE_SAMPLE_TYPE);
        List<DictItem> personGenderList = dictService.selectDictItemListByType(Constants.DICT_TYPE_PERSON_GENDER);
        List<DictItem> samplePropertiesList = dictService.selectDictItemListByType(Constants.DICT_TYPE_SAMPLE_PROPERTIES);
        List<DictItem> identifyRequirementList = dictService.selectDictItemListByType(Constants.DICT_TPYE_IDENTIFY_REQUIREMENT);
        List<DictItem> dictItemDutyList = dictService.selectByParentDictTypeCode(Constants.DICT_TYPE_DUTY);
        List<DictItem> dictItemCertificateList = dictService.selectByParentDictTypeCode(Constants.DICT_TYPE_CERTIFICATE_TYPE);

        modelAndView.addObject("caseTypeList", caseTypeList);
        modelAndView.addObject("casePropertyList", casePropertyList);
        modelAndView.addObject("caseLevelList", caseLevelList);
        modelAndView.addObject("personTypeList", personTypeList);
        modelAndView.addObject("personRelationList", personRelationList);
        modelAndView.addObject("sampleTypeList", sampleTypeList);
        modelAndView.addObject("personGenderList", personGenderList);
        modelAndView.addObject("samplePropertiesList", samplePropertiesList);
        modelAndView.addObject("identifyRequirementList", identifyRequirementList);
        modelAndView.addObject("dictItemDutyList", dictItemDutyList);
        modelAndView.addObject("dictItemCertificateList", dictItemCertificateList);
        modelAndView.addObject("personRaceList", personRaceList);

        List<LimsConsignmentInfo> consignmentInfoList = limsConsignmentInfoService.selectListByDelegateOrgId(LimsSecurityUtils.getLoginDelegateOrg().getId());

        modelAndView.addObject("delegatorList", CosignmentInfoHelper.getDelegatorList(consignmentInfoList));
        modelAndView.addObject("delegatorCertificateNoList", CosignmentInfoHelper.getDelegatorCertificateNoList(consignmentInfoList));
        modelAndView.addObject("delegatorPhoneList", CosignmentInfoHelper.getDelegatorPhoneList(consignmentInfoList));

        /*  委托单位 */
        List<DelegateOrg> delegateOrgList = delegateOrgService.selectAll();
        modelAndView.addObject("delegateOrgList", delegateOrgList);

         /*  鉴定机构名称 */
        List<IdentifyKernel> identifyKernelList = identifyKernelService.selectAll();
        modelAndView.addObject("identifyKernelList", identifyKernelList);

        return modelAndView;
    }

    /**
     * 身份不明人员委托登记
     *
     * @param request
     * @return
     */
    @RequestMapping("/2.html")
    public ModelAndView unidentified(HttpServletRequest request, LimsEntrustmentInformation limsEntrustmentInformation) {
        ModelAndView modelAndView = initDict();
        limsEntrustmentInformation.setAppraisalPoints("DNA鉴定");
        modelAndView.addObject("operateType", Constants.OPERATE_TYPE_ADD);
        modelAndView.addObject("limsEntrustmentInformation", limsEntrustmentInformation);
        modelAndView.addObject("loginTitleName", SystemUtil.getSystemConfig().getProperty("loginTitleName"));
        modelAndView.setViewName("delegate/caseinfo/unidentifiedCaseReg");
        return modelAndView;
    }

    @RequestMapping(value = "/addCase.html", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> addCase(HttpServletRequest request, HttpServletResponse response, @RequestParam String operateType,
                                       @RequestBody ConsignmentDataModel consignmentDataModel) {
        boolean result = false;
        Map<String, Object> map = null;
        try {
            map = limsCaseInformationService.identifyCaseReg(operateType, consignmentDataModel);
            result = true;
            map.put("result", result);
        } catch (Exception ex) {
            result = false;
            map.put("result", result);
        }

        return map;
    }

    @RequestMapping("/identifyPersonSampleReg.html")
    public ModelAndView identifyPersonSampleReg(HttpServletRequest request, Integer consignmentId, Integer caseId, String operateType, String delegateOrgDesc) {
        ModelAndView modelAndView = initDict();

        LimsCaseInfo caseInfo = limsCaseInfoService.selectById(caseId);

        Map<String, Object> dataMap = new HashMap<>();
        modelAndView.addObject("dataMap", dataMap);
        modelAndView.addObject("consignmentId", consignmentId);
        modelAndView.addObject("delegateOrgDesc", delegateOrgDesc);
        modelAndView.addObject("caseId", caseId);
        modelAndView.addObject("caseInfo", caseInfo);
        modelAndView.addObject("loginTitleName", SystemUtil.getSystemConfig().getProperty("loginTitleName"));
        modelAndView.setViewName("delegate/caseinfo/identifyPersonSampleReg");
        return modelAndView;
    }

    @RequestMapping(value = "/addPersonSample.html", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> addPersonSample(HttpServletRequest request, HttpServletResponse response, String operateType,
                                               @RequestBody ConsignmentDataModel consignmentDataModel) {
        boolean result = false;
        Map<String, Object> map = null;
        try {
            map = limsPersonSampleService.identifyPersonReg(operateType, consignmentDataModel);
            result = true;
            map.put("result", result);
        } catch (Exception ex) {
            result = false;
            map.put("result", result);
        }

        return map;
    }

    @RequestMapping(value = "/checkSampleLaboratoryNo.html", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> checkSampleLaboratoryNo(HttpServletRequest request, HttpServletResponse response, @RequestParam String sampleLaboratoryNo) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        boolean success = true;
        List<LimsSampleInfo> limsSampleInfoList = limsSampleInfoService.selectListBySampleNo(sampleLaboratoryNo);
        if (limsSampleInfoList.size() == 0) {
            success = true;
            resultMap.put("success", success);
        } else {
            success = false;
            resultMap.put("success", success);
        }
        return resultMap;
    }

    @RequestMapping("/3.html")
    public ModelAndView missing(HttpServletRequest request, LimsEntrustmentInformation limsEntrustmentInformation) {
        ModelAndView modelAndView = initDict();
        limsEntrustmentInformation.setAppraisalPoints("DNA鉴定");
        modelAndView.addObject("operateType", Constants.OPERATE_TYPE_ADD);
        modelAndView.addObject("limsEntrustmentInformation", limsEntrustmentInformation);
        modelAndView.addObject("loginTitleName", SystemUtil.getSystemConfig().getProperty("loginTitleName"));
        modelAndView.setViewName("delegate/caseinfo/missingCaseReg");
        return modelAndView;
    }

    @RequestMapping(value = "/addMissingCase.html", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> addMissingCase(HttpServletRequest request, HttpServletResponse response, @RequestParam String operateType,
                                              @RequestBody ConsignmentDataModel consignmentDataModel) {
        boolean result = false;
        Map<String, Object> map = null;
        try {
            map = limsCaseInformationService.identifyMissingCaseReg(operateType, consignmentDataModel);
            result = true;
        } catch (Exception ex) {
            result = false;
        }
        map.put("result", result);
        return map;
    }

    @RequestMapping("/identifyMissingPersonSampleReg.html")
    public ModelAndView identifyMissingPersonSampleReg(HttpServletRequest request, Integer entrustmentId, String operateType) {
        ModelAndView modelAndView = initDict();
        modelAndView.addObject("entrustmentId", entrustmentId);
        modelAndView.addObject("operateType", operateType);
        modelAndView.addObject("loginTitleName", SystemUtil.getSystemConfig().getProperty("loginTitleName"));
        modelAndView.setViewName("delegate/caseinfo/identifyMissingPersonSampleReg");
        return modelAndView;
    }

    //old
    /*@RequestMapping(value = "/getCaseInfoXk.html", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> getCaseInfoXk(HttpServletRequest request, @RequestParam String caseXkNo) {
        Map<String, Object> result = new HashMap<String, Object>();
        List<LimsSampleInfo> limsSampleInfoList = new ArrayList<>();
        List<LimsPersonInfo> limsPersonInfoList = new ArrayList<>();
        LimsCaseInfo limsCaseInfo = new LimsCaseInfo();
        try {
            Map<String, Object> xkMap = this.sceneInvestigationService.findSceneInvestigationByNo(caseXkNo);

            this.logger.error("解析完数据：" + xkMap);
            boolean flag = false;
            limsSampleInfoList = (List) xkMap.get("limsSampleInfoList");
            limsCaseInfo = (LimsCaseInfo) xkMap.get("caseInfo");
        } catch (Exception e) {
            e.printStackTrace();
        }

        result.put("limsSampleInfoList", limsSampleInfoList);
        result.put("limsCaseInfo", limsCaseInfo);

        return result;
    }*/

    //根据现堪编号获取现堪数据-new
    @RequestMapping(value = "/getCaseInfoXk.html", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> getCaseInfoXk(HttpServletRequest request, @RequestParam String caseXkNo) {
        //返回map
        Map<String, Object> returnMap = new HashMap<>();
        //案件信息
        LimsCaseInfo limsCaseInfo = new LimsCaseInfo();
        //委托信息
        LimsConsignment limsConsignment = new LimsConsignment();
        //物证检材信息
        List<LimsSampleInfo> limsSampleInfoList = new ArrayList();

        String xkAdress = SystemUtil.getSystemConfig().getProperty("xkAdress");
        String result = "";
        try {
            HttpClient client = HttpClients.createDefault();
            // 要调用的接口方法
            String url = xkAdress+caseXkNo.trim();
            HttpPost post = new HttpPost(url);
            try {
                StringEntity s = new StringEntity("");
                s.setContentEncoding("UTF-8");
                s.setContentType("application/json");
                post.setEntity(s);
                post.addHeader("content-type", "text/xml");
                HttpResponse res = client.execute(post);
                result = EntityUtils.toString(res.getEntity());
                System.out.println(result);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            Document document = DocumentHelper.parseText(result);
            Element root = document.getRootElement();
            Element caseElement = root.element("CASE");

            //委托编号
            Element caseNoAttr = caseElement.element("WTBH");
            if (caseNoAttr != null) {
                limsConsignment.setConsignmentNo(caseNoAttr.getTextTrim());
            }

            //现堪A号
            Element xkAnoAttr = caseElement.element("CASE_NO");
            if (xkAnoAttr != null) {
                limsCaseInfo.setCaseXkAno(xkAnoAttr.getTextTrim());
            }

            //现堪编号
            Element kNoAttr = caseElement.element("K_NO");
            if (kNoAttr != null) {
                limsCaseInfo.setCaseXkNo(kNoAttr.getTextTrim());
            }
            //案件性质
            Element caseTypeAttr = caseElement.element("CASE_TYPE");
            if (caseTypeAttr != null) {
                limsCaseInfo.setCaseProperty(xkCaseTypeToLimsType(caseTypeAttr.getTextTrim()));
            }
            //案件名称
            Element caseNameAttr = caseElement.element("CASE_NAME");
            if (caseNameAttr != null) {
                limsCaseInfo.setCaseName(caseNameAttr.getTextTrim());
            }
            //案发地点行政编号
            Element sceneRegionalismAttr = caseElement.element("SCENE_REGIONALISM");
            if (sceneRegionalismAttr != null) {
                limsCaseInfo.setCaseLocation(sceneRegionalismAttr.getTextTrim());
            }
            //案发地点
            Element scenePlaceAttr = caseElement.element("SCENE_PLACE");
            if (scenePlaceAttr != null) {
                limsCaseInfo.setCaseLocation(scenePlaceAttr.getTextTrim());
            }

            limsCaseInfo.setCaseType("1");

            //案发时间
            Element occurrenceDateAttr = caseElement.element("OCCURRENCE_DATE");
            if (occurrenceDateAttr != null) {
                String occurrenceDateStr = occurrenceDateAttr.getTextTrim();
                Date occurrenceDate = DateUtils.stringToDate(occurrenceDateStr, "yyyy-MM-dd HH:mm");
                limsCaseInfo.setCaseDatetime(occurrenceDate);
            }
            //简要案情
            Element caseSummaryAttr = caseElement.element("CASE_SUMMARY");
            if (caseSummaryAttr != null) {
                limsCaseInfo.setCaseBrief(caseSummaryAttr.getTextTrim());
            }

            Element reserve1Attr = caseElement.element("RESERVE1");
            Element externalCaseNoAttr = caseElement.element("EXTERNAL_CASE_NO");

            //检材
            Element bioEvidenceListElement = root.element("BIO_EVIDENCE_LIST");
            List bioEvidenceElementList = bioEvidenceListElement.elements("BIO_EVIDENCE");
            for (int i = 0; i < bioEvidenceElementList.size(); i++) {
                LimsSampleInfo sampleInfoDna = new LimsSampleInfo();
                Element bioEvidenceElement = (Element) bioEvidenceElementList.get(i);

                //物证编号
                Element wnoAttr = bioEvidenceElement.element("W_NO");
                if (wnoAttr != null) {
                    sampleInfoDna.setEvidenceNo(wnoAttr.getTextTrim());
                }
                //检材描述
                Element descriptionAttr = bioEvidenceElement.element("DESCRIPTION");
                if (descriptionAttr != null) {
                    sampleInfoDna.setSampleDesc(descriptionAttr.getTextTrim());
                }
                //提取人
                Element collectByAttr = bioEvidenceElement.element("COLLECT_BY");
                if (collectByAttr != null) {
                    sampleInfoDna.setExtractPerson(collectByAttr.getTextTrim());
                }
                //提取时间
                Element collectDateAttr = bioEvidenceElement.element("COLLECT_DATE");
                if (collectDateAttr != null) {
                    String collectDateStr = collectDateAttr.getTextTrim();
                    Date collectDate = DateUtils.stringToDate(collectDateStr, "yyyy-MM-dd");
                    sampleInfoDna.setExtractDatetime(collectDate);
                }
                //检材名称
                Element evidenceNameAttr = bioEvidenceElement.element("EVIDENCE_NAME");
                if (evidenceNameAttr != null) {
                    sampleInfoDna.setSampleName(evidenceNameAttr.getTextTrim());
                }
                //检材类型
                Element sampleTypeAttr = bioEvidenceElement.element("SAMPLE_TYPE");
                if ((sampleTypeAttr != null) && (!sampleTypeAttr.equals(""))) {
                    sampleInfoDna.setSampleType(xkTypeToLimsType(sampleTypeAttr.getTextTrim()));
                    List<DictItem> sampleTypeList = dictService.selectByParentDictTypeCode("SAMPLE_TYPE");
                    for (DictItem dictItem : sampleTypeList) {
                        if (sampleInfoDna.getSampleType().equals(dictItem.getDictCode())) {
                            sampleInfoDna.setSampleTypeName(dictItem.getDictName());
                        }
                    }
                }
                Element collectPosAttr = bioEvidenceElement.element("COLLECT_POS");
                Element testDescAttr = bioEvidenceElement.element("TEST_DESC");
                Element warnAttr = bioEvidenceElement.element("WARN_MSG");

                sampleInfoDna.setSampleFlag("0");
                Element personIdAttr = bioEvidenceElement.element("PERSON_ID");
                Element relationAttr;
                if (personIdAttr != null) {
                    relationAttr = bioEvidenceElement.element("SAMPLE_RELATION");
                }
                Element acceptStatus = bioEvidenceElement.element("IF_SJ");
                Element sceneAddressAttr = root.element("AUTHORADDRESS");

                sampleInfoDna.setSamplePacking("物证袋");
                //以下为默认
                sampleInfoDna.setSampleProperties("01");
                sampleInfoDna.setOtherPropertiesDesc("棉签");
                limsSampleInfoList.add(sampleInfoDna);
            }

            Collections.sort(limsSampleInfoList, new SortByEvidenceNo());

            returnMap.put("limsConsignment", limsConsignment);
            returnMap.put("limsCaseInfo", limsCaseInfo);
            returnMap.put("limsSampleInfoList", limsSampleInfoList);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("目前系统没有对应的勘验信息" + e);
//            throw new IllegalArgumentException("目标系统没有对应的勘验信息！");
            return null;
        }

        return returnMap;
    }

    //排序
    class SortByEvidenceNo implements Comparator {
        @Override
        public int compare(Object o1, Object o2) {
            LimsSampleInfo s1 = (LimsSampleInfo) o1;
            LimsSampleInfo s2 = (LimsSampleInfo) o2;
            return s1.getEvidenceNo().compareTo(s2.getEvidenceNo());
        }
    }

    @RequestMapping(value = "/refreshXkSample.html", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> refreshXkSample(HttpServletRequest request, @RequestParam String caseXkNo, String consignmentId) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        List<LimsSampleInfo> limsSampleInfoListXK = new ArrayList<>();
        List<LimsSampleInfo> newSampleList = new ArrayList<>();

        String xkAdress = SystemUtil.getSystemConfig().getProperty("xkAdress");

        String result = null;
        try {
            HttpClient client = HttpClients.createDefault();
            // 要调用的接口方法
            String url = xkAdress+caseXkNo.trim();
            HttpPost post = new HttpPost(url);
            try {
                StringEntity s = new StringEntity("");
                s.setContentEncoding("UTF-8");
                s.setContentType("application/json");
                post.setEntity(s);
                post.addHeader("content-type", "text/xml");
                HttpResponse res = client.execute(post);
                result = EntityUtils.toString(res.getEntity());
                System.out.println(result);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            Document document = DocumentHelper.parseText(result);
            Element root = document.getRootElement();
            Element caseElement = root.element("CASE");


            Element reserve1Attr = caseElement.element("RESERVE1");
            Element externalCaseNoAttr = caseElement.element("EXTERNAL_CASE_NO");

            //检材
            Element bioEvidenceListElement = root.element("BIO_EVIDENCE_LIST");
            List bioEvidenceElementList = bioEvidenceListElement.elements("BIO_EVIDENCE");
            for (int i = 0; i < bioEvidenceElementList.size(); i++) {
                LimsSampleInfo sampleInfoDna = new LimsSampleInfo();
                Element bioEvidenceElement = (Element) bioEvidenceElementList.get(i);

                //物证编号
                Element wnoAttr = bioEvidenceElement.element("W_NO");
                if (wnoAttr != null) {
                    sampleInfoDna.setEvidenceNo(wnoAttr.getTextTrim());
                }
                //检材描述
                Element descriptionAttr = bioEvidenceElement.element("DESCRIPTION");
                if (descriptionAttr != null) {
                    sampleInfoDna.setSampleDesc(descriptionAttr.getTextTrim());
                }
                //提取人
                Element collectByAttr = bioEvidenceElement.element("COLLECT_BY");
                if (collectByAttr != null) {
                    sampleInfoDna.setExtractPerson(collectByAttr.getTextTrim());
                }
                //提取时间
                Element collectDateAttr = bioEvidenceElement.element("COLLECT_DATE");
                if (collectDateAttr != null) {
                    String collectDateStr = collectDateAttr.getTextTrim();
                    Date collectDate = DateUtils.stringToDate(collectDateStr, "yyyy-MM-dd");
                    sampleInfoDna.setExtractDatetime(collectDate);
                }
                //检材名称
                Element evidenceNameAttr = bioEvidenceElement.element("EVIDENCE_NAME");
                if (evidenceNameAttr != null) {
                    sampleInfoDna.setSampleName(evidenceNameAttr.getTextTrim());
                }
                //检材类型
                Element sampleTypeAttr = bioEvidenceElement.element("SAMPLE_TYPE");
                if ((sampleTypeAttr != null) && (!sampleTypeAttr.equals(""))) {
                    sampleInfoDna.setSampleType(xkTypeToLimsType(sampleTypeAttr.getTextTrim()));
                    List<DictItem> sampleTypeList = dictService.selectByParentDictTypeCode("SAMPLE_TYPE");
                    for (DictItem dictItem : sampleTypeList) {
                        if (sampleInfoDna.getSampleType().equals(dictItem.getDictCode())) {
                            sampleInfoDna.setSampleTypeName(dictItem.getDictName());
                        }
                    }
                }
                Element collectPosAttr = bioEvidenceElement.element("COLLECT_POS");
                Element testDescAttr = bioEvidenceElement.element("TEST_DESC");
                Element warnAttr = bioEvidenceElement.element("WARN_MSG");

                sampleInfoDna.setSampleFlag("0");
                Element personIdAttr = bioEvidenceElement.element("PERSON_ID");
                Element relationAttr;
                if (personIdAttr != null) {
                    relationAttr = bioEvidenceElement.element("SAMPLE_RELATION");
                }
                Element acceptStatus = bioEvidenceElement.element("IF_SJ");
                Element sceneAddressAttr = root.element("AUTHORADDRESS");

                sampleInfoDna.setSamplePacking("物证袋");
                //以下为默认
                sampleInfoDna.setSampleProperties("01");
                sampleInfoDna.setOtherPropertiesDesc("棉签");
                limsSampleInfoListXK.add(sampleInfoDna);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("目前系统没有对应的勘验信息" + e);
            //throw new IllegalArgumentException("目标系统没有对应的勘验信息！");
            return null;
        }
        Collections.sort(limsSampleInfoListXK, new SortByEvidenceNo());

        List<LimsSampleInfo> sampleInfoList = limsSampleInfoService.selectListByConsignmentId(Integer.parseInt(consignmentId));

        List<String> l1 = new ArrayList<>();
        for (int i = 0; i < limsSampleInfoListXK.size(); i++) {
            l1.add(limsSampleInfoListXK.get(i).getEvidenceNo());
        }
        List<String> l2 = new ArrayList<>();
        for (int j = 0; j < sampleInfoList.size(); j++) {
            l2.add(sampleInfoList.get(j).getEvidenceNo());
        }

        l1.removeAll(l2);

        for (LimsSampleInfo sampleInfoXk : limsSampleInfoListXK) {
            if (l1.size() > 0) {
                for (String l : l1) {
                    if (sampleInfoXk.getEvidenceNo().equals(l)) {
                        newSampleList.add(sampleInfoXk);
                    }
                }
            }
        }

        if (newSampleList.size() > 0) {
            resultMap.put("limsSampleInfoList", newSampleList);
            resultMap.put("limsSampleInfoListCnt", newSampleList.size());
        } else if (newSampleList.size() == 0) {
            resultMap.put("limsSampleInfoList", "0");
            resultMap.put("limsSampleInfoListCnt", "0");
        } else {
            resultMap.put("limsSampleInfoList", limsSampleInfoListXK);
            resultMap.put("limsSampleInfoListCnt", limsSampleInfoListXK.size());
        }


        return resultMap;
    }

    @RequestMapping(value = "/delSample.html", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> delSample(HttpServletRequest request, @RequestParam String sampleId) {
        Map<String, Object> result = new HashMap<String, Object>();

        try {
            limsSampleInfoService.deleteById(Integer.parseInt(sampleId));
            result.put("success", true);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("error", false);
        }

        return result;
    }

    @RequestMapping(value = "/delPerson.html", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> delPerson(HttpServletRequest request, @RequestParam String personId) {
        Map<String, Object> result = new HashMap<String, Object>();

        try {
            limsSampleInfoService.deleteById(Integer.parseInt(personId));
            result.put("success", true);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("error", false);
        }

        return result;
    }

    public LimsConsignment delegator(LimsConsignment consignment) {

        String code = LimsSecurityUtils.getLoginDelegateOrg().getOrgCode();
        LimsConsignmentInfo consignmentInfo = new LimsConsignmentInfo();
        consignmentInfo.setDelegateOrgId(LimsSecurityUtils.getLoginDelegateOrg().getId());

        if (code.equals("522401") && consignment.getDelegator1() == null) {//七星关
            consignmentInfo.setDelegator("吴国才");
            List<LimsConsignmentInfo> consignment1List = limsConsignmentInfoService.selectLimsConsignmentInfoList(consignmentInfo);
            for (LimsConsignmentInfo consignment1 : consignment1List) {
                consignment.setDelegator1("吴国才");
                consignment.setDelegator1Position(consignment1.getDelegatorDutyName());
                consignment.setDelegator1Cname(consignment1.getDelegatorCertificateName());
                consignment.setDelegator1Cno(consignment1.getDelegatorCertificateNo());
                consignment.setDelegator1Phone(consignment1.getDelegatorPhone());
            }

            consignmentInfo.setDelegator("王建军");
            List<LimsConsignmentInfo> consignment2List = limsConsignmentInfoService.selectLimsConsignmentInfoList(consignmentInfo);
            for (LimsConsignmentInfo consignment2 : consignment2List) {
                consignment.setDelegator2("王建军");
                consignment.setDelegator2Position(consignment2.getDelegatorDutyName());
                consignment.setDelegator2Cname(consignment2.getDelegatorCertificateName());
                consignment.setDelegator2Cno(consignment2.getDelegatorCertificateNo());
                consignment.setDelegator2Phone(consignment2.getDelegatorPhone());
            }

        } else if (code.equals("522422") && consignment.getDelegator1() == null) {//大方
            consignmentInfo.setDelegator("余仁祥");
            List<LimsConsignmentInfo> consignment1List = limsConsignmentInfoService.selectLimsConsignmentInfoList(consignmentInfo);
            for (LimsConsignmentInfo consignment1 : consignment1List) {
                consignment.setDelegator1("余仁祥");
                consignment.setDelegator1Position(consignment1.getDelegatorDutyName());
                consignment.setDelegator1Cname(consignment1.getDelegatorCertificateName());
                consignment.setDelegator1Cno(consignment1.getDelegatorCertificateNo());
                consignment.setDelegator1Phone(consignment1.getDelegatorPhone());
            }

            consignmentInfo.setDelegator("王伟");
            List<LimsConsignmentInfo> consignment2List = limsConsignmentInfoService.selectLimsConsignmentInfoList(consignmentInfo);
            for (LimsConsignmentInfo consignment2 : consignment2List) {
                consignment.setDelegator2("王伟");
                consignment.setDelegator2Position(consignment2.getDelegatorDutyName());
                consignment.setDelegator2Cname(consignment2.getDelegatorCertificateName());
                consignment.setDelegator2Cno(consignment2.getDelegatorCertificateNo());
                consignment.setDelegator2Phone(consignment2.getDelegatorPhone());
            }

        } else if (code.equals("522402") && consignment.getDelegator1() == null) {//金海湖
            consignmentInfo.setDelegator("吴蔺");
            List<LimsConsignmentInfo> consignment1List = limsConsignmentInfoService.selectLimsConsignmentInfoList(consignmentInfo);
            for (LimsConsignmentInfo consignment1 : consignment1List) {
                consignment.setDelegator1("吴蔺");
                consignment.setDelegator1Position(consignment1.getDelegatorDutyName());
                consignment.setDelegator1Cname(consignment1.getDelegatorCertificateName());
                consignment.setDelegator1Cno(consignment1.getDelegatorCertificateNo());
                consignment.setDelegator1Phone(consignment1.getDelegatorPhone());
            }

            consignmentInfo.setDelegator("邓付林");
            List<LimsConsignmentInfo> consignment2List = limsConsignmentInfoService.selectLimsConsignmentInfoList(consignmentInfo);
            for (LimsConsignmentInfo consignment2 : consignment2List) {
                consignment.setDelegator2("邓付林");
                consignment.setDelegator2Position(consignment2.getDelegatorDutyName());
                consignment.setDelegator2Cname(consignment2.getDelegatorCertificateName());
                consignment.setDelegator2Cno(consignment2.getDelegatorCertificateNo());
                consignment.setDelegator2Phone(consignment2.getDelegatorPhone());
            }

        } else if (code.equals("522426") && consignment.getDelegator1() == null) {//纳雍
            consignmentInfo.setDelegator("李祥");
            List<LimsConsignmentInfo> consignment1List = limsConsignmentInfoService.selectLimsConsignmentInfoList(consignmentInfo);
            for (LimsConsignmentInfo consignment1 : consignment1List) {
                consignment.setDelegator1("李祥");
                consignment.setDelegator1Position(consignment1.getDelegatorDutyName());
                consignment.setDelegator1Cname(consignment1.getDelegatorCertificateName());
                consignment.setDelegator1Cno(consignment1.getDelegatorCertificateNo());
                consignment.setDelegator1Phone(consignment1.getDelegatorPhone());
            }

            consignmentInfo.setDelegator("宋强榕");
            List<LimsConsignmentInfo> consignment2List = limsConsignmentInfoService.selectLimsConsignmentInfoList(consignmentInfo);
            for (LimsConsignmentInfo consignment2 : consignment2List) {
                consignment.setDelegator2("宋强榕");
                consignment.setDelegator2Position(consignment2.getDelegatorDutyName());
                consignment.setDelegator2Cname(consignment2.getDelegatorCertificateName());
                consignment.setDelegator2Cno(consignment2.getDelegatorCertificateNo());
                consignment.setDelegator2Phone(consignment2.getDelegatorPhone());
            }

        } else if (code.equals("522424") && consignment.getDelegator1() == null) {//金沙
            consignmentInfo.setDelegator("王方");
            List<LimsConsignmentInfo> consignment1List = limsConsignmentInfoService.selectLimsConsignmentInfoList(consignmentInfo);
            for (LimsConsignmentInfo consignment1 : consignment1List) {
                consignment.setDelegator1("王方");
                consignment.setDelegator1Position(consignment1.getDelegatorDutyName());
                consignment.setDelegator1Cname(consignment1.getDelegatorCertificateName());
                consignment.setDelegator1Cno(consignment1.getDelegatorCertificateNo());
                consignment.setDelegator1Phone(consignment1.getDelegatorPhone());
            }

            consignmentInfo.setDelegator("宋松");
            List<LimsConsignmentInfo> consignment2List = limsConsignmentInfoService.selectLimsConsignmentInfoList(consignmentInfo);
            for (LimsConsignmentInfo consignment2 : consignment2List) {
                consignment.setDelegator2("宋松");
                consignment.setDelegator2Position(consignment2.getDelegatorDutyName());
                consignment.setDelegator2Cname(consignment2.getDelegatorCertificateName());
                consignment.setDelegator2Cno(consignment2.getDelegatorCertificateNo());
                consignment.setDelegator2Phone(consignment2.getDelegatorPhone());
            }

        } else if (code.equals("522423") && consignment.getDelegator1() == null) {//黔西
            consignmentInfo.setDelegator("汪忠保");
            List<LimsConsignmentInfo> consignment1List = limsConsignmentInfoService.selectLimsConsignmentInfoList(consignmentInfo);
            for (LimsConsignmentInfo consignment1 : consignment1List) {
                consignment.setDelegator1("汪忠保");
                consignment.setDelegator1Position(consignment1.getDelegatorDutyName());
                consignment.setDelegator1Cname(consignment1.getDelegatorCertificateName());
                consignment.setDelegator1Cno(consignment1.getDelegatorCertificateNo());
                consignment.setDelegator1Phone(consignment1.getDelegatorPhone());
            }

            consignmentInfo.setDelegator("李道光");
            List<LimsConsignmentInfo> consignment2List = limsConsignmentInfoService.selectLimsConsignmentInfoList(consignmentInfo);
            for (LimsConsignmentInfo consignment2 : consignment2List) {
                consignment.setDelegator2("李道光");
                consignment.setDelegator2Position(consignment2.getDelegatorDutyName());
                consignment.setDelegator2Cname(consignment2.getDelegatorCertificateName());
                consignment.setDelegator2Cno(consignment2.getDelegatorCertificateNo());
                consignment.setDelegator2Phone(consignment2.getDelegatorPhone());
            }

        } else if (code.equals("522425") && consignment.getDelegator1() == null) {//织金
            consignmentInfo.setDelegator("曾元松");
            List<LimsConsignmentInfo> consignment1List = limsConsignmentInfoService.selectLimsConsignmentInfoList(consignmentInfo);
            for (LimsConsignmentInfo consignment1 : consignment1List) {
                consignment.setDelegator1("曾元松");
                consignment.setDelegator1Position(consignment1.getDelegatorDutyName());
                consignment.setDelegator1Cname(consignment1.getDelegatorCertificateName());
                consignment.setDelegator1Cno(consignment1.getDelegatorCertificateNo());
                consignment.setDelegator1Phone(consignment1.getDelegatorPhone());
            }

            consignmentInfo.setDelegator("陈运生");
            List<LimsConsignmentInfo> consignment2List = limsConsignmentInfoService.selectLimsConsignmentInfoList(consignmentInfo);
            for (LimsConsignmentInfo consignment2 : consignment2List) {
                consignment.setDelegator2("陈运生");
                consignment.setDelegator2Position(consignment2.getDelegatorDutyName());
                consignment.setDelegator2Cname(consignment2.getDelegatorCertificateName());
                consignment.setDelegator2Cno(consignment2.getDelegatorCertificateNo());
                consignment.setDelegator2Phone(consignment2.getDelegatorPhone());
            }

        } else if (code.equals("522427") && consignment.getDelegator1() == null) {//威宁
            consignmentInfo.setDelegator("聂国彦");
            List<LimsConsignmentInfo> consignment1List = limsConsignmentInfoService.selectLimsConsignmentInfoList(consignmentInfo);
            for (LimsConsignmentInfo consignment1 : consignment1List) {
                consignment.setDelegator1("聂国彦");
                consignment.setDelegator1Position(consignment1.getDelegatorDutyName());
                consignment.setDelegator1Cname(consignment1.getDelegatorCertificateName());
                consignment.setDelegator1Cno(consignment1.getDelegatorCertificateNo());
                consignment.setDelegator1Phone(consignment1.getDelegatorPhone());
            }

            consignmentInfo.setDelegator("张华清");
            List<LimsConsignmentInfo> consignment2List = limsConsignmentInfoService.selectLimsConsignmentInfoList(consignmentInfo);
            for (LimsConsignmentInfo consignment2 : consignment2List) {
                consignment.setDelegator2("张华清");
                consignment.setDelegator2Position(consignment2.getDelegatorDutyName());
                consignment.setDelegator2Cname(consignment2.getDelegatorCertificateName());
                consignment.setDelegator2Cno(consignment2.getDelegatorCertificateNo());
                consignment.setDelegator2Phone(consignment2.getDelegatorPhone());
            }

        } else if (code.equals("522428") && consignment.getDelegator1() == null) {//赫章
            consignmentInfo.setDelegator("罗中伦");
            List<LimsConsignmentInfo> consignment1List = limsConsignmentInfoService.selectLimsConsignmentInfoList(consignmentInfo);
            for (LimsConsignmentInfo consignment1 : consignment1List) {
                consignment.setDelegator1("罗中伦");
                consignment.setDelegator1Position(consignment1.getDelegatorDutyName());
                consignment.setDelegator1Cname(consignment1.getDelegatorCertificateName());
                consignment.setDelegator1Cno(consignment1.getDelegatorCertificateNo());
                consignment.setDelegator1Phone(consignment1.getDelegatorPhone());
            }

            consignmentInfo.setDelegator("徐廷茂");
            List<LimsConsignmentInfo> consignment2List = limsConsignmentInfoService.selectLimsConsignmentInfoList(consignmentInfo);
            for (LimsConsignmentInfo consignment2 : consignment2List) {
                consignment.setDelegator2("徐廷茂");
                consignment.setDelegator2Position(consignment2.getDelegatorDutyName());
                consignment.setDelegator2Cname(consignment2.getDelegatorCertificateName());
                consignment.setDelegator2Cno(consignment2.getDelegatorCertificateNo());
                consignment.setDelegator2Phone(consignment2.getDelegatorPhone());
            }

        } else if (code.equals("522429") && consignment.getDelegator1() == null) {//百里杜鹃
            consignmentInfo.setDelegator("张林");
            List<LimsConsignmentInfo> consignment1List = limsConsignmentInfoService.selectLimsConsignmentInfoList(consignmentInfo);
            for (LimsConsignmentInfo consignment1 : consignment1List) {
                consignment.setDelegator1("张林");
                consignment.setDelegator1Position(consignment1.getDelegatorDutyName());
                consignment.setDelegator1Cname(consignment1.getDelegatorCertificateName());
                consignment.setDelegator1Cno(consignment1.getDelegatorCertificateNo());
                consignment.setDelegator1Phone(consignment1.getDelegatorPhone());
            }
            consignmentInfo.setDelegator("周礼梦");
            List<LimsConsignmentInfo> consignment2List = limsConsignmentInfoService.selectLimsConsignmentInfoList(consignmentInfo);
            for (LimsConsignmentInfo consignment2 : consignment2List) {
                consignment.setDelegator2("周礼梦");
                consignment.setDelegator2Position(consignment2.getDelegatorDutyName());
                consignment.setDelegator2Cname(consignment2.getDelegatorCertificateName());
                consignment.setDelegator2Cno(consignment2.getDelegatorCertificateNo());
                consignment.setDelegator2Phone(consignment2.getDelegatorPhone());
            }
        }

        return consignment;
    }

    /**
     * 未委托现堪案件查询
     *
     * @param request
     * @return
     */
    @RequestMapping("/unentrustedList.html")
    public ModelAndView unentrustedList(HttpServletRequest request, SceneInfoVO sceneInfoVO, PageInfo pageInfo) throws Exception {
        ModelAndView modelAndView = initDict();
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> map1 = new HashMap<String, Object>();
        String orgCode = LimsSecurityUtils.getLoginDelegateOrg().getOrgCode();

        JSONArray jsonArray = new JSONArray();
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String oldTime = null;
        Date frontTime = null;
        Date afterTime = null;

        Calendar cale = Calendar.getInstance();
        cale.add(Calendar.MONTH, 0);
        cale.set(Calendar.DAY_OF_MONTH, 1);
        oldTime = sd.format(cale.getTime());
        frontTime = sd.parse(oldTime);
        afterTime = sd.parse(sd.format(new Date()));

        String pageNo = String.valueOf(pageInfo.getPage());
        String pageSize = String.valueOf(pageInfo.getEvePageRecordCnt());
        if (pageNo.equals("1")) {
            map.put("offSet", pageNo);//记录开始位置
        } else {
            int offSet = (Integer.parseInt(pageNo) - 1) * Integer.parseInt(pageSize);
            map.put("offSet", String.valueOf(offSet));//记录开始位置
        }
        map.put("fetchSize", pageSize);//获取记录数（1-100），默认15

        /*if (null == sceneInfoVO.getInvestDateFrom()) {
            map.put("investDateFrom", sd.format(frontTime));//勘验开始时间 （格式：yyyy-mm-dd hh24:mi）
        } else {
            map.put("investDateFrom", sceneInfoVO.getInvestDateFrom());
        }
        if (null == sceneInfoVO.getInvestDateTo()) {
            map.put("investDateTo", sd.format(afterTime));//勘验结束时间（格式：yyyy-mm-dd hh24:mi）
        } else {
            map.put("investDateTo", sceneInfoVO.getInvestDateTo());
        }*/

        String xkCode = xkOrgCode(orgCode);

        if (StringUtils.isNotBlank(sceneInfoVO.getInvestigationNo())) {//勘验号
            map.put("investigationNo", sceneInfoVO.getInvestigationNo());
        } else {
            map.put("investigationNo", xkCode);
        }
        if (StringUtils.isNotBlank(sceneInfoVO.getInvesPlace())) {//勘验地点
            map.put("invesPlace", sceneInfoVO.getInvesPlace());
        }

        //查询现堪现场信息列表
        jsonArray.add(map);
        String para = jsonArray.toString();
        String returnSceneInfo = sceneManagerPortType.querySceneInfo(para);

        Map<String, Object> xkMap = new HashMap<String, Object>();
        //解析返回的json数据
        xkMap = analysisXk(returnSceneInfo);

        List<SceneInfoVO> xkList = (List) xkMap.get("xkList");
        Integer total = 0;
        String returnTotal = (String) xkMap.get("total");

        for (int i = 0; i < xkList.size(); i++) {
            List<LimsCaseInfo> limsCaseInfoList = limsCaseInfoService.selectListByCaseXkNo(xkList.get(i).getInvestigationNo());
            if (limsCaseInfoList.size() > 0) {
                xkList.remove(i);
                total = Integer.parseInt(returnTotal) - 1;
            }
        }
        if(null == returnTotal){
            modelAndView.addObject("pageInfo", pageInfo.updatePageInfo(Integer.parseInt(returnTotal)));
        }else{
            modelAndView.addObject("pageInfo", pageInfo.updatePageInfo(total));
        }
        modelAndView.addObject("returnSceneInfo", returnSceneInfo);
        modelAndView.addObject("xkList", xkList);
        modelAndView.addObject("loginTitleName", SystemUtil.getSystemConfig().getProperty("loginTitleName"));
        modelAndView.setViewName("delegate/caseinfo/unentrustedList");
        return modelAndView;
    }

    private Map<String, Object> analysisXk(String result) throws Exception {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Map<String, Object> warpInfo = new HashMap();
        List<Object> returnXkList = new ArrayList<>();
        String total = null;
        String errorMessage = null;

        JSONObject jsonObj = null;
        JSONArray jsonArray = JSONArray.fromObject(result);
        for (int i = 0; i < jsonArray.size(); i++) {
            jsonObj = jsonArray.getJSONObject(i);
            for (int j = 0; j < jsonObj.size(); j++) {
                errorMessage = jsonObj.get("errorMessage").toString();//错误信息
                total = jsonObj.get("total").toString();//条数
                break;
            }
        }

        JSONArray resultList = jsonObj.getJSONArray("result");//列表List
        for (int x = 0; x < resultList.size(); x++) {
            JSONObject xkList = resultList.getJSONObject(x);
            for (int k = 0; k < xkList.size(); k++) {
                SceneInfoVO sceneInfoVO = new SceneInfoVO();
                if ("null" != xkList.get("INVESTIGATIONNO").toString()) {
                    sceneInfoVO.setInvestigationNo(xkList.get("INVESTIGATIONNO").toString());
                }
                if ("null" != xkList.get("CASENO").toString()) {
                    sceneInfoVO.setCaseNo(xkList.get("CASENO").toString());
                }
                if ("null" != xkList.get("RECEPTIONNO").toString()) {
                    sceneInfoVO.setReceptionNo(xkList.get("RECEPTIONNO").toString());
                }
                if ("null" != xkList.get("INVESTDATEFROM").toString()) {
                    sceneInfoVO.setInvestDateFrom(sdf.parse(xkList.get("INVESTDATEFROM").toString()));
                }
                if ("null" != xkList.get("INVESTDATETO").toString()) {
                    sceneInfoVO.setInvestDateTo(sdf.parse(xkList.get("INVESTDATETO").toString()));
                }
                if ("null" != xkList.get("INVESPLACE").toString()) {
                    sceneInfoVO.setInvesPlace(xkList.get("INVESPLACE").toString());
                }
                if ("null" != xkList.get("OCCUDATEFROM").toString()) {
                    sceneInfoVO.setOccuDateFrom(sdf.parse(xkList.get("OCCUDATEFROM").toString()));
                }
                if ("null" != xkList.get("OCCUDATETO").toString()) {
                    sceneInfoVO.setOccuDateTo(sdf.parse(xkList.get("OCCUDATETO").toString()));
                }
                if ("null" != xkList.get("CASETYPE").toString()) {
                    sceneInfoVO.setCaseType(xkList.get("CASETYPE").toString());
                }
                if ("null" != xkList.get("CASETYPECN").toString()) {
                    sceneInfoVO.setCaseTypeCn(xkList.get("CASETYPECN").toString());
                }
                if ("null" != xkList.get("INVESTIGATORS").toString()) {
                    sceneInfoVO.setInvestigators(xkList.get("INVESTIGATORS").toString());
                }
                returnXkList.add(sceneInfoVO);
                break;
            }
        }

        warpInfo.put("total", total);
        warpInfo.put("errorMessage", errorMessage);
        warpInfo.put("xkList", returnXkList);
        return warpInfo;
    }

    public String xkCaseTypeToLimsType(String caseType) {
        if (caseType == "") {
            return "09";
        }

        switch (caseType) {
            case "050225"://盗窃车内财物
                return "10";
            case "040101"://杀人
                return "01";
            case "040103"://伤害案
                return "13";
            case "040105"://强奸
                return "02";
            case "050248"://其他盗窃
                return "12";
            case "050228"://盗窃机动车
                return "09";
            case "050201"://入室盗窃
                return "08";
            case "050330"://诈骗
                return "20";
            case "060702"://毒品
                return "16";
            case "900000":
                return "24";
        }
        return "24";
    }

    public String xkTypeToLimsType(String bioEvidenceType) {
        if (bioEvidenceType == "") {
            return "99";
        }
        int bioEvidenctTypeInt = 0;
        try {
            bioEvidenctTypeInt = Integer.parseInt(bioEvidenceType);
        } catch (Exception e) {
            return "99";
        }
        switch (bioEvidenctTypeInt) {
            case 2002://血迹
                return "01";
            case 2003:
                return "09";
            case 2004://精斑
                return "02";
            case 2005:
                return "04";
            case 2011:
                return "07";
            case 2013:
                return "03";
            case 2014:
                return "03";
            case 2001://脱落细胞
                return "11";
            case 2007:
            case 2008:
            case 2009:
            case 2010:
            case 2012:
        }
        return "99";
    }

    public String xkOrgCode(String orgCode) {

        switch (orgCode) {
            case "522401":
                return "K522401";
            case "522402":
                return "K522402";
            case "522422":
                return "K522422";
            case "522423":
                return "K522423";
            case "522424":
                return "K522424";
            case "522425":
                return "K522425";
            case "522426":
                return "K522426";
            case "522427":
                return "K522427";
            case "522428":
                return "K522428";
            case "522429":
                return "K522429";
        }
        return null;
    }

}
