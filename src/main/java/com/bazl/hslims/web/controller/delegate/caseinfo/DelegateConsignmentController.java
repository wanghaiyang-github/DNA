package com.bazl.hslims.web.controller.delegate.caseinfo;

import com.bazl.hslims.common.Constants;
import com.bazl.hslims.manager.model.po.DelegateOrg;
import com.bazl.hslims.manager.model.po.DictItem;
import com.bazl.hslims.manager.model.po.LimsConsignmentInfo;
import com.bazl.hslims.manager.services.common.DelegateOrgService;
import com.bazl.hslims.manager.services.common.DictService;
import com.bazl.hslims.manager.services.common.LimsConsignmentInfoService;
import com.bazl.hslims.utils.SystemUtil;
import com.bazl.hslims.web.controller.BaseController;
import com.bazl.hslims.web.security.LimsSecurityUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/10/18.
 */
@Controller
@RequestMapping("/wt/consignment")
public class DelegateConsignmentController extends BaseController {

    @Autowired
    DelegateOrgService delegateOrgService;
    @Autowired
    LimsConsignmentInfoService limsConsignmentInfoService;
    @Autowired
    DictService dictService;

    @RequestMapping("listDelegator.html")
    public ModelAndView listDelegator(HttpServletRequest request) {


        DelegateOrg delegateOrg = LimsSecurityUtils.getLoginDelegateOrg();

        LimsConsignmentInfo consignmentInfo = new LimsConsignmentInfo();
        consignmentInfo.setDelegateOrgId(delegateOrg.getId());
        consignmentInfo.setDeleteFlag(Constants.FLAG_FALSE);

        List<LimsConsignmentInfo> consignmentInfoList = limsConsignmentInfoService.selectLimsConsignmentInfoList(consignmentInfo);

        List<DictItem> dictItemDutyList = dictService.selectByParentDictTypeCode(Constants.DICT_TYPE_DUTY);

        List<DictItem> dictItemCertificateList = dictService.selectByParentDictTypeCode(Constants.DICT_TYPE_CERTIFICATE_TYPE);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("delegateOrg", delegateOrg);
        modelAndView.addObject("consignmentInfoList", consignmentInfoList);
        modelAndView.addObject("dictItemDutyList", dictItemDutyList);
        modelAndView.addObject("dictItemCertificateList", dictItemCertificateList);
        modelAndView.addObject("loginTitleName", SystemUtil.getSystemConfig().getProperty("loginTitleName"));
        modelAndView.setViewName("delegate/delegator/listDelegator");
        return modelAndView;
    }

    @RequestMapping(value="/addOrEditLimsConsignmentInfo.html",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    @ResponseBody
    public Map<String,Object> addOrEditLimsConsignmentInfo(HttpServletRequest request, @RequestBody LimsConsignmentInfo limsConsignmentInfo, String operate){
        Map<String, Object> result = new HashMap<>();

        try {

            limsConsignmentInfo = getConsignmentInfo(limsConsignmentInfo);

            if (operate.equals(Constants.OPERATE_TYPE_EDIT)) {
                limsConsignmentInfo.setUpdatePerson(LimsSecurityUtils.getLoginName());
                limsConsignmentInfoService.update(limsConsignmentInfo);
            }else {
                limsConsignmentInfo.setCreatePerson(LimsSecurityUtils.getLoginName());
                limsConsignmentInfoService.insert(limsConsignmentInfo);
            }

            result.put("success", true);
        }catch (Exception e) {
            result.put("success", false);
        }

        return result;
    }

    @RequestMapping("delLimsConsignmentInfo.html")
    @ResponseBody
    public Map<String, Object> delLimsConsignmentInfo(HttpServletRequest request, Integer id) {

        Map<String, Object> result = new HashMap<>();

        try {

            LimsConsignmentInfo consignmentInfo = new LimsConsignmentInfo();
            consignmentInfo.setId(id);
            consignmentInfo.setDeleteFlag(Constants.FLAG_TRUE);
            consignmentInfo.setDeletePerson(LimsSecurityUtils.getLoginDelegateOrg().getOrgName());
            limsConsignmentInfoService.deleteById(consignmentInfo);

            result.put("success", true);
        }catch (Exception e) {
            result.put("success", false);
        }

        return result;
    }

    @RequestMapping(value="/selectDelagatorQuery.html")
    @ResponseBody
    public List<LimsConsignmentInfo> selectDelagatorQuery(HttpServletRequest request, String delegatorName) {
        LimsConsignmentInfo consignmentInfo = new LimsConsignmentInfo();
        consignmentInfo.setDelegateOrgId(LimsSecurityUtils.getLoginDelegateOrg().getId());
        consignmentInfo.setDelegator(delegatorName);

        List<LimsConsignmentInfo> consignmentInfoList = limsConsignmentInfoService.selectLimsConsignmentInfoList(consignmentInfo);

        return consignmentInfoList;
    }

    private LimsConsignmentInfo getConsignmentInfo(LimsConsignmentInfo limsConsignmentInfo) {

        if (StringUtils.isNotBlank(limsConsignmentInfo.getDelegateOrgName())) {
            limsConsignmentInfo.setDelegateOrgName(limsConsignmentInfo.getDelegateOrgName().trim());
        }else {
            limsConsignmentInfo.setDelegateOrgName(null);
        }

        if (StringUtils.isNotBlank(limsConsignmentInfo.getDelegator())) {
            limsConsignmentInfo.setDelegator(limsConsignmentInfo.getDelegator().trim());
        }else {
            limsConsignmentInfo.setDelegator(null);
        }

        if (StringUtils.isNotBlank(limsConsignmentInfo.getDelegatorDuty())) {
            limsConsignmentInfo.setDelegatorDuty(limsConsignmentInfo.getDelegatorDuty().trim());
        }else {
            limsConsignmentInfo.setDelegatorDuty(null);
        }

        if (StringUtils.isNotBlank(limsConsignmentInfo.getDelegatorCertificate())) {
            limsConsignmentInfo.setDelegatorCertificate(limsConsignmentInfo.getDelegatorCertificate().trim());
        }else {
            limsConsignmentInfo.setDelegatorCertificate(null);
        }

        if (StringUtils.isNotBlank(limsConsignmentInfo.getDelegatorCertificateNo())) {
            limsConsignmentInfo.setDelegatorCertificateNo(limsConsignmentInfo.getDelegatorCertificateNo().trim());
        }else {
            limsConsignmentInfo.setDelegatorCertificateNo(null);
        }

        if (StringUtils.isNotBlank(limsConsignmentInfo.getDelegatorPhone())) {
            limsConsignmentInfo.setDelegatorPhone(limsConsignmentInfo.getDelegatorPhone().trim());
        }else {
            limsConsignmentInfo.setDelegatorPhone(null);
        }

        return limsConsignmentInfo;
    }

}
