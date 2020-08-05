package com.bazl.hslims.web.controller.center.system;

import com.bazl.hslims.common.Constants;
import com.bazl.hslims.manager.model.po.DelegateOrg;
import com.bazl.hslims.manager.model.po.DictItem;
import com.bazl.hslims.manager.model.po.LimsConsignmentInfo;
import com.bazl.hslims.manager.services.common.DelegateOrgService;
import com.bazl.hslims.manager.services.common.DictService;
import com.bazl.hslims.manager.services.common.LimsConsignmentInfoService;
import com.bazl.hslims.utils.EncryptUtils;
import com.bazl.hslims.utils.ListUtils;
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
 * Created by Administrator on 2017/1/11.
 */
@Controller
@RequestMapping("/center/7")
public class DelegateOrgController  extends BaseController {

    @Autowired
    DelegateOrgService delegateOrgService;
    @Autowired
    LimsConsignmentInfoService limsConsignmentInfoService;
    @Autowired
    DictService dictService;

    @RequestMapping("/02.html")
    public ModelAndView into(HttpServletRequest request) {
        List<DelegateOrg> delegateOrgList = delegateOrgService.selectAll();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("delegateOrgList", delegateOrgList);
        modelAndView.setViewName("center/system/listDelegateOrg");

        return modelAndView;
    }

    @RequestMapping(value="/editDelegateOrg.html", method = RequestMethod.POST, produces="application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> accept(HttpServletRequest request, @RequestBody DelegateOrg delegateOrg) {

        Map<String, Object> result = new HashMap<String, Object>();
        String orgName = delegateOrg.getOrgName();
        String orgCode = delegateOrg.getOrgCode();
        String ipaddrRange = delegateOrg.getIpaddrRange();
        String contactPhonenum = delegateOrg.getContactPhonenum();
        String loginPassword = delegateOrg.getLoginPassword();

        try {
            if (delegateOrg.getId() == null || delegateOrg.getId() == 0) {
                delegateOrg.setOrgName(orgName);
                delegateOrg.setOrgCode(orgCode);
                delegateOrg.setRootFlag("1");
                delegateOrg.setParentId(0);
                delegateOrg.setLoginPassword(EncryptUtils.encryptMD5(loginPassword));
                delegateOrg.setIpaddrRange(ipaddrRange);
                delegateOrg.setContactPhonenum(contactPhonenum);
                delegateOrg.setDeleteFlag("0");
                delegateOrgService.insert(delegateOrg);
            }else {
                delegateOrg = delegateOrgService.selectById(delegateOrg.getId());
                delegateOrg.setOrgName(orgName);
                delegateOrg.setOrgCode(orgCode);
                delegateOrg.setRootFlag("1");
                delegateOrg.setParentId(0);
                if (!delegateOrg.getLoginPassword().equals(loginPassword))
                    delegateOrg.setLoginPassword(EncryptUtils.encryptMD5(loginPassword));
                delegateOrg.setIpaddrRange(ipaddrRange);
                delegateOrg.setContactPhonenum(contactPhonenum);
                delegateOrg.setDeleteFlag("0");
                delegateOrgService.update(delegateOrg);
            }
            result.put("success", true);
        }catch(Exception ex){
            result.put("success", false);
        }

        return result;
    }

    @RequestMapping("/delDelegateOrg.html")
    @ResponseBody
    public Map<String,Object> delDelegateOrg(HttpServletRequest request, Integer id){

        delegateOrgService.delete(id);

        List<LimsConsignmentInfo> consignmentInfoList = limsConsignmentInfoService.selectListByDelegateOrgId(id);
        if (ListUtils.isNotNullAndEmptyList(consignmentInfoList)) {
            for (LimsConsignmentInfo lci : consignmentInfoList) {
                lci.setId(lci.getId());
                lci.setDeleteFlag(Constants.FLAG_TRUE);
                lci.setDeletePerson(LimsSecurityUtils.getLoginName());
                limsConsignmentInfoService.deleteById(lci);
            }
        }

        Map<String,Object> result = new HashMap<String,Object>();
        result.put("success", true);
        return result;
    }

    @RequestMapping("addConsignmentInfo.html")
    public ModelAndView addConsignmentInfo(HttpServletRequest request, Integer delegateOrgId) {

        DelegateOrg delegateOrg = delegateOrgService.selectById(delegateOrgId);

        LimsConsignmentInfo consignmentInfo = new LimsConsignmentInfo();
        consignmentInfo.setDelegateOrgId(delegateOrgId);
        consignmentInfo.setDeleteFlag(Constants.FLAG_FALSE);

        List<LimsConsignmentInfo> consignmentInfoList = limsConsignmentInfoService.selectLimsConsignmentInfoList(consignmentInfo);

        List<DictItem> dictItemDutyList = dictService.selectByParentDictTypeCode(Constants.DICT_TYPE_DUTY);

        List<DictItem> dictItemCertificateList = dictService.selectByParentDictTypeCode(Constants.DICT_TYPE_CERTIFICATE_TYPE);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("delegateOrgName", delegateOrg.getOrgName());
        modelAndView.addObject("delegateOrgId", delegateOrgId);
        modelAndView.addObject("consignmentInfoList", consignmentInfoList);
        modelAndView.addObject("dictItemDutyList", dictItemDutyList);
        modelAndView.addObject("dictItemCertificateList", dictItemCertificateList);
        modelAndView.setViewName("center/system/addConsignmentInfo");
        return modelAndView;
    }

    @RequestMapping(value="/addOrEditLimsConsignmentInfo.html",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    @ResponseBody
    public Map<String,Object> addOrEditDictItem(HttpServletRequest request,@RequestBody LimsConsignmentInfo limsConsignmentInfo, String operate){
        Map<String, Object> result = new HashMap<>();

        try {

            limsConsignmentInfo = getConsignmentInfo(limsConsignmentInfo);

            if (operate.equals(Constants.OPERATE_TYPE_ADD)) {
                limsConsignmentInfo.setCreatePerson(LimsSecurityUtils.getLoginName());
                limsConsignmentInfoService.insert(limsConsignmentInfo);
            }else {
                limsConsignmentInfo.setUpdatePerson(LimsSecurityUtils.getLoginName());
                limsConsignmentInfoService.update(limsConsignmentInfo);
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
            consignmentInfo.setDeletePerson(LimsSecurityUtils.getLoginName());
            limsConsignmentInfoService.deleteById(consignmentInfo);

            result.put("success", true);
        }catch (Exception e) {
            result.put("success", false);
        }

        return result;
    }

    @RequestMapping(value="/selectDelagatorQuery.html")
    @ResponseBody
    public List<LimsConsignmentInfo> selectDelagatorQuery(HttpServletRequest request,Integer delegateOrgId, String delegatorName) {
        LimsConsignmentInfo consignmentInfo = new LimsConsignmentInfo();
        consignmentInfo.setDelegateOrgId(delegateOrgId);
        consignmentInfo.setDelegator(delegatorName);

        List<LimsConsignmentInfo> consignmentInfoList = limsConsignmentInfoService.selectLimsConsignmentInfoList(consignmentInfo);

        return consignmentInfoList;
    }

    private LimsConsignmentInfo getConsignmentInfo(LimsConsignmentInfo limsConsignmentInfo) {

        if (StringUtils.isBlank(limsConsignmentInfo.getDelegateOrgName())) {
            limsConsignmentInfo.setDelegateOrgName(null);
        }else {
            limsConsignmentInfo.setDelegateOrgName(limsConsignmentInfo.getDelegateOrgName().trim());
        }

        if (StringUtils.isBlank(limsConsignmentInfo.getDelegator())) {
            limsConsignmentInfo.setDelegator(null);
        }else {
            limsConsignmentInfo.setDelegator(limsConsignmentInfo.getDelegator().trim());
        }

        if (StringUtils.isBlank(limsConsignmentInfo.getDelegatorDuty())) {
            limsConsignmentInfo.setDelegatorDuty(null);
        }else {
            limsConsignmentInfo.setDelegatorDuty(limsConsignmentInfo.getDelegatorDuty().trim());
        }

        if (StringUtils.isBlank(limsConsignmentInfo.getDelegatorCertificate())) {
            limsConsignmentInfo.setDelegatorCertificate(null);
        }else {
            limsConsignmentInfo.setDelegatorCertificate(limsConsignmentInfo.getDelegatorCertificate().trim());
        }

        if (StringUtils.isBlank(limsConsignmentInfo.getDelegatorCertificateNo())) {
            limsConsignmentInfo.setDelegatorCertificateNo(null);
        }else {
            limsConsignmentInfo.setDelegatorCertificateNo(limsConsignmentInfo.getDelegatorCertificateNo().trim());
        }

        if (StringUtils.isBlank(limsConsignmentInfo.getDelegatorPhone())) {
            limsConsignmentInfo.setDelegatorPhone(null);
        }else {
            limsConsignmentInfo.setDelegatorPhone(limsConsignmentInfo.getDelegatorPhone().trim());
        }

        return limsConsignmentInfo;
    }

}
