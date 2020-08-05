package com.bazl.hslims.web.controller.center.system;

import com.bazl.hslims.manager.model.po.DictInfo;
import com.bazl.hslims.manager.model.po.DictItem;
import com.bazl.hslims.manager.services.common.DictService;
import com.bazl.hslims.utils.ListUtils;
import com.bazl.hslims.web.controller.BaseController;
import com.bazl.hslims.web.security.LimsSecurityUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/1/11.
 */
@RequestMapping("/center/7")
@Controller
public class SysDictController extends BaseController {
    @Autowired
    DictService dictService;

    @RequestMapping("/03.html")
    public ModelAndView into(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        List<DictInfo> dictInfoList= dictService.selecList(null);
        modelAndView.addObject("dictInfoList", dictInfoList);
        modelAndView.setViewName("center/system/listDictInfo");

        return modelAndView;
    }

    @RequestMapping("/getChildrenDictItem.html")
    public ModelAndView getChildrenDictItem(HttpServletRequest request,String dictTypeCode) {
        ModelAndView modelAndView = new ModelAndView();

        List<DictItem> dictItemList = dictService.selectByParentDictTypeCode(dictTypeCode);
        modelAndView.addObject("dictTypeCode", dictTypeCode);
        modelAndView.addObject("dictItemList", dictItemList);

        modelAndView.setViewName("center/system/listDictItem");
        return modelAndView;
    }

    @RequestMapping(value="/checkRepeatDictItemQuery.html")
    @ResponseBody
    public Map<String, Object> checkRepeatDictItemQuery(HttpServletRequest request,@RequestBody DictItem dictItem){
        dictItem = resetDictItem(dictItem);
        Map<String, Object> result = new HashMap<>();

        try {
            int num = dictService.selectRepeatDictItemQuery(dictItem);

            if (num > 0) {
                result.put("success",true );
            }else {
                result.put("success",false );
            }
        }catch (Exception e){
            logger.error("selectRepeatDictItemQuery error!",e);
        }

        return result;
    }

    @RequestMapping(value="/addOrEditDictInfo.html",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    @ResponseBody
    public Map<String,Object> addOrEditDictInfo(HttpServletRequest request,@RequestBody DictInfo dictInfo){
        dictInfo = resetDictInfo(dictInfo);
        Map<String,Object> result = new HashMap<>();
        String dictTypeCode=dictInfo.getDictTypeCode().toUpperCase();
        dictInfo.setDictTypeCode(dictTypeCode);

        try{
            String loginName= LimsSecurityUtils.getLoginName();
            dictInfo.setCreatePerson(loginName);
            dictInfo.setCreateDatetime(new Date());

            if(dictInfo.getId() == null || dictInfo.getId() == 0){
                dictService.addDictInfo(dictInfo);
            }else{
                dictService.updateDictInfo(dictInfo);

                String oldDictTypeCode=dictInfo.getOldDictTypeCode().toUpperCase();
                List<DictItem> dictItemList = dictService.selectByParentDictTypeCode(oldDictTypeCode);
                if (ListUtils.isNotNullAndEmptyList(dictItemList)) {
                    for(DictItem dictItem:dictItemList){
                        dictItem.setDictTypeCode(dictInfo.getDictTypeCode());
                        dictService.updateDictItem(dictItem);
                    }
                }

            }

            result.put("success",true);
        }catch (Exception e){
            result.put("success",false);
            logger.error("addOrEditDictInfo error",e);
        }

        return result;
    }

    @RequestMapping(value="/addOrEditDictItem.html",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    @ResponseBody
    public Map<String,Object> addOrEditDictItem(HttpServletRequest request,@RequestBody DictItem dictItem){
        dictItem = resetDictItem(dictItem);
        Map<String,Object> result = new HashMap<>();

        try{
            String loginName= LimsSecurityUtils.getLoginName();
            dictItem.setCreatePerson(loginName);
            dictItem.setCreateDatetime(new Date());

            if(dictItem.getId() == null || dictItem.getId() == 0){
                dictService.addDictItem(dictItem);
            }else{
                dictService.updateDictItem(dictItem);
            }

            result.put("success",true);
        }catch (Exception e){
            result.put("success",false);
            logger.error("addOrEditDictItem error",e);
        }

        return result;
    }

    @RequestMapping("/delDictInfo.html")
    public ModelAndView delDictInfo(HttpServletRequest request, String dictInfoId){
        DictInfo dictInfo=dictService.selectDictInfoById(dictInfoId);
        String dictTypeCode=dictInfo.getDictTypeCode();
        dictService.deleteByDictTypeCode(dictTypeCode);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/center/7/03.html");
        return modelAndView;
    }

    @RequestMapping("/delDictItem.html")
    public ModelAndView delDictItem(HttpServletRequest request,String dictItemId,String dictTypeCode){
        dictService.deleteByDictItemId(dictItemId);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/center/7/getChildrenDictItem.html?dictTypeCode="+dictTypeCode);
        return modelAndView;
    }

    public DictInfo resetDictInfo(DictInfo dictInfo){
        if(StringUtils.isBlank(dictInfo.getDictTypeCode())){
            dictInfo.setDictTypeCode(null);
        }else {
            dictInfo.setDictTypeCode(dictInfo.getDictTypeCode().trim());
        }

        if(StringUtils.isBlank(dictInfo.getDictTypeName())){
            dictInfo.setDictTypeName(null);
        }else {
            dictInfo.setDictTypeName(dictInfo.getDictTypeName().trim());
        }

        if(StringUtils.isBlank(dictInfo.getCreatePerson())){
            dictInfo.setCreatePerson(null);
        }else {
            dictInfo.setCreatePerson(dictInfo.getCreatePerson().trim());
        }

        return dictInfo;
    }

    public DictItem resetDictItem(DictItem dictItem){
        if(StringUtils.isBlank(dictItem.getDictTypeCode())){
            dictItem.setDictTypeCode(null);
        }else {
            dictItem.setDictTypeCode(dictItem.getDictTypeCode().trim());
        }

        if(StringUtils.isBlank(dictItem.getDictCode())){
            dictItem.setDictCode(null);
        }else {
            dictItem.setDictCode(dictItem.getDictCode().trim());
        }

        if(StringUtils.isBlank(dictItem.getDictName())){
            dictItem.setDictName(null);
        }else {
            dictItem.setDictName(dictItem.getDictName().trim());
        }

        if(StringUtils.isBlank(dictItem.getDictDesc())){
            dictItem.setDictDesc(null);
        }else {
            dictItem.setDictDesc(dictItem.getDictDesc().trim());
        }

        return dictItem;
    }
}
