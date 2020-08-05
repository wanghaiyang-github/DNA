package com.bazl.hslims.web.controller.center;

import com.bazl.hslims.common.Constants;
import com.bazl.hslims.manager.model.po.*;
import com.bazl.hslims.manager.model.vo.LimsCaseInfoVO;
import com.bazl.hslims.manager.model.vo.LimsSampleGeneVO;
import com.bazl.hslims.manager.model.vo.LimsSampleInfoVO;
import com.bazl.hslims.manager.services.common.*;
import com.bazl.hslims.utils.ListUtils;
import com.bazl.hslims.utils.MathUtils;
import com.bazl.hslims.utils.SystemUtil;
import com.bazl.hslims.web.controller.BaseController;
import com.bazl.hslims.web.security.LimsSecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by Administrator on 2017/1/3.
 */
@Controller
@RequestMapping("/center")
public class IndexController extends BaseController {

    @Autowired
    LimsCaseInfoService limsCaseInfoService;
    @Autowired
    LimsSampleInfoService limsSampleInfoService;
    @Autowired
    LabPermissionService labPermissionService;
    @Autowired
    LabRoleService labRoleService;
    @Autowired
    LabUserService labUserService;
    @Autowired
    DictService dictService;
    @Autowired
    LimsSampleGeneService limsSampleGeneService;
    @Autowired
    LimsIdentifyBookService limsIdentifyBookService;


    @RequestMapping("/top.html")
    public ModelAndView top(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("loginTitleName", SystemUtil.getSystemConfig().getProperty("loginTitleName"));
        modelAndView.setViewName("center/top");
        return modelAndView;
    }


    @RequestMapping("/left.html")
    public ModelAndView left(HttpServletRequest request) {
        LabUser labUser = LimsSecurityUtils.getLoginLabUser();

        List<LabRole> userRoleList = labRoleService.selectByLabUserId(labUser.getId());
        List<LabPermission> labPermissionList = new ArrayList<>();
        Set<Integer> permissionIds = new HashSet<>();
        List<LabPermission> rolePermissions = null;
        for (LabRole role : userRoleList) {
            rolePermissions = labPermissionService.selectListByRoleId(role.getId());
            for (LabPermission lp : rolePermissions) {
                if (!permissionIds.contains(lp.getId())) {
                    permissionIds.add(lp.getId());
                    labPermissionList.add(lp);
                }
            }
        }
        Collections.sort(labPermissionList);

        List<LabPermission> rootPermissionList = new ArrayList<LabPermission>();
        List<LabPermission> childPermissionList = new ArrayList<LabPermission>();
        for (LabPermission lp : labPermissionList) {
            if (lp.getRootFlag().equals(Constants.FLAG_TRUE)) {
                rootPermissionList.add(lp);
            } else {
                childPermissionList.add(lp);
            }
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("loginTitleName", SystemUtil.getSystemConfig().getProperty("loginTitleName"));
        modelAndView.addObject("rootPermissionList", rootPermissionList);
        modelAndView.addObject("childPermissionList", childPermissionList);

        modelAndView.setViewName("center/left");
        return modelAndView;
    }


    @RequestMapping("/main.html")
    public ModelAndView main(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("center/main");

        List<String> caseStatusList = new ArrayList<>();
        caseStatusList.add(Constants.CASE_INFO_STATUS_ACCEPTED);
        caseStatusList.add(Constants.CASE_INFO_STATUS_FINISHED);

        LimsCaseInfoVO caseQuery = new LimsCaseInfoVO();
        caseQuery.setCaseStatusList(caseStatusList);
        caseQuery.setAdditionalFlag(Constants.FLAG_FALSE);
        int caseCount = limsCaseInfoService.selectVOCnt(caseQuery);
        modelAndView.addObject("caseCount", caseCount);

        LimsSampleInfo sample = new LimsSampleInfo();
        sample.setAcceptStatus(Constants.SAMPLE_ACCEPT_STATUS_ACCEPTED);
        int sampleCount = limsSampleInfoService.selectVOCount(new LimsSampleInfoVO(sample));
        modelAndView.addObject("sampleCount", sampleCount);

        //TODO 检出率
        LimsSampleGeneVO sampleGeneVO = new LimsSampleGeneVO();
        int sampleGeneCount = limsSampleGeneService.selectVOCount(sampleGeneVO);
        modelAndView.addObject("detectionRate", MathUtils.GetPercentStr(sampleGeneCount, sampleCount, 0));

        //TODO 作用率
        int identifyBookCount = limsIdentifyBookService.selectCount();
        modelAndView.addObject("actionRate", MathUtils.GetPercentStr(identifyBookCount, caseCount, 0));

        //TODO 未受理
        LimsCaseInfoVO caseStatusPending = new LimsCaseInfoVO();
        caseStatusPending.setConsignmentStatus(Constants.CASE_INFO_STATUS_PENDING);
        int caseStatusPendingCount = limsCaseInfoService.selectVOCnt(caseStatusPending);
        modelAndView.addObject("caseStatusPendingCount", caseStatusPendingCount);

        //TODO 在检验
        LimsCaseInfoVO caseStatusAccepted = new LimsCaseInfoVO();
        caseStatusAccepted.setConsignmentStatus(Constants.CASE_INFO_STATUS_ACCEPTED);
        int caseStatusAcceptedCount = limsCaseInfoService.selectVOCnt(caseStatusAccepted);
        modelAndView.addObject("caseStatusAcceptedCount", caseStatusAcceptedCount);

        //TODO 已导入结果
        Integer caseInfoCount = 0;

        LimsCaseInfoVO query = new LimsCaseInfoVO();
        query.setConsignmentStatus(Constants.CASE_INFO_STATUS_ACCEPTED);
        List<LimsCaseInfoVO> caseInfoList = limsCaseInfoService.selectVOList(query);
        if (ListUtils.isNotNullAndEmptyList(caseInfoList)) {
            List<LimsSampleGene> sampleGeneList;
            for (LimsCaseInfoVO lciVo : caseInfoList) {
                 sampleGeneList = limsSampleGeneService.selectListByCaseId(lciVo.getEntity().getId());
                if(sampleGeneList.size() > 0){
                    caseInfoCount ++;
                }
            }
        }
        modelAndView.addObject("caseInfoCount",caseInfoCount );


        //TODO 未出鉴定书
        LimsCaseInfoVO identifyBookPending = new LimsCaseInfoVO();
        identifyBookPending.setIdentifyBookStatus(Constants.IDENTIFY_BOOK_PENDING);
        identifyBookPending.setAdditionalFlag(Constants.FLAG_FALSE);
        int identifyBookPendingCount = limsCaseInfoService.selectVOCnt(identifyBookPending);
        modelAndView.addObject("identifyBookPendingCount", identifyBookPendingCount);

        //TODO 待签发鉴定书
        LimsCaseInfoVO identifyBookNeedSing = new LimsCaseInfoVO();
        identifyBookNeedSing.setIdentifyBookStatus(Constants.IDENTIFY_BOOK_NEED_SIGN);
        identifyBookNeedSing.setAdditionalFlag(Constants.FLAG_FALSE);
        int identifyBookNeedSingCount = limsCaseInfoService.selectVOCnt(identifyBookNeedSing);
        modelAndView.addObject("identifyBookNeedSingCount", identifyBookNeedSingCount);

        //TODO 待领取鉴定书
        LimsCaseInfoVO identifyBookSinged = new LimsCaseInfoVO();
        identifyBookSinged.setIdentifyBookStatus(Constants.IDENTIFY_BOOK_SIGNED);
        identifyBookSinged.setAdditionalFlag(Constants.FLAG_FALSE);
        int identifyBookSingedCount = limsCaseInfoService.selectVOCnt(identifyBookSinged);
        modelAndView.addObject("identifyBookSingedCount", identifyBookSingedCount);

        return modelAndView;
    }

    @RequestMapping("/caseDataByProperty.html")
    @ResponseBody
    public List<Map<String, Object>> caseDataByProperty(HttpServletRequest request) {
        List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();

        List<String> caseStatusList = new ArrayList<>();
        caseStatusList.add(Constants.CASE_INFO_STATUS_ACCEPTED);
        caseStatusList.add(Constants.CASE_INFO_STATUS_FINISHED);
        LimsCaseInfoVO caseQuery = new LimsCaseInfoVO();
        caseQuery.setConsignmentStatusList(caseStatusList);
        caseQuery.setAdditionalFlag(Constants.FLAG_FALSE);
        int caseCount = limsCaseInfoService.selectVOCnt(caseQuery);

        if (caseCount == 0) {
            Map<String, Object> map = new HashMap<>();
            map.put("value", 0);
            map.put("label", "无数据");
            map.put("formatted", "0%");

            dataList.add(map);
            return dataList;
        }

        List<DictItem> casePropertyList = dictService.selectDictItemListByType(Constants.DICT_TPYE_CASE_PROPERTY);
        for (DictItem di : casePropertyList) {
            Map<String, Object> map = new HashMap<>();
            int tmpCnt = limsCaseInfoService.selectCountByProperty(di.getDictCode());
            if (tmpCnt == 0) {
                continue;
            }
            map.put("value", tmpCnt);
            map.put("label", di.getDictName());
            map.put("formatted", MathUtils.GetPercentStr(tmpCnt, caseCount, 0));

            dataList.add(map);
        }

        return dataList;
    }


    @RequestMapping("/sampleDataByType.html")
    @ResponseBody
    public List<Map<String, Object>> sampleDataByType(HttpServletRequest request) {
        List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();

        LimsSampleInfo sample = new LimsSampleInfo();
        sample.setAcceptStatus(Constants.SAMPLE_ACCEPT_STATUS_ACCEPTED);
        int sampleCount = limsSampleInfoService.selectVOCount(new LimsSampleInfoVO(sample));

        if (sampleCount == 0) {
            Map<String, Object> map = new HashMap<>();
            map.put("value", 0);
            map.put("label", "无数据");
            map.put("formatted", "0%");

            dataList.add(map);
            return dataList;
        }

        List<DictItem> sampleTypeList = dictService.selectDictItemListByType(Constants.DICT_TPYE_SAMPLE_TYPE);
        for (DictItem di : sampleTypeList) {
            Map<String, Object> map = new HashMap<>();
            int tmpCnt = limsSampleInfoService.selectCountByType(di.getDictCode());
            if (tmpCnt == 0) {
                continue;
            }
            map.put("value", tmpCnt);
            map.put("label", di.getDictName());
            map.put("formatted", MathUtils.GetPercentStr(tmpCnt, sampleCount, 0));

            dataList.add(map);
        }

        return dataList;
    }

}
