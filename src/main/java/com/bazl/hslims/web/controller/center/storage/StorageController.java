package com.bazl.hslims.web.controller.center.storage;

import com.bazl.hslims.common.Constants;
import com.bazl.hslims.manager.PageInfo;
import com.bazl.hslims.manager.model.po.DictItem;
import com.bazl.hslims.manager.model.po.ReagentSuppliesInfo;
import com.bazl.hslims.manager.model.po.StorageInfo;
import com.bazl.hslims.manager.model.po.StorageRecordInfo;
import com.bazl.hslims.manager.services.common.DictService;
import com.bazl.hslims.manager.services.common.ReagentSuppliesInfoService;
import com.bazl.hslims.manager.services.common.StorageInfoService;
import com.bazl.hslims.manager.services.common.StorageRecordInfoService;
import com.bazl.hslims.utils.DateUtils;
import com.bazl.hslims.utils.ListUtils;
import com.bazl.hslims.web.controller.BaseController;
import com.bazl.hslims.web.security.LimsSecurityUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by Administrator on 2017/9/6.
 */
@Controller
@RequestMapping("/center/storage")
public class StorageController extends BaseController {

    @Autowired
    ReagentSuppliesInfoService reagentSuppliesInfoService;
    @Autowired
    StorageRecordInfoService storageRecordInfoService;
    @Autowired
    StorageInfoService storageInfoService;
    @Autowired
    DictService dictService;

    @RequestMapping("/listStorageInfo.html")
    public ModelAndView listStorageInfo(HttpServletRequest request, ReagentSuppliesInfo reagentSuppliesInfo, PageInfo pageInfo) {

        reagentSuppliesInfo = resetReagentSuppliesInfo(reagentSuppliesInfo);

        ModelAndView modelAndView = init();
        int totalCnt = reagentSuppliesInfoService.selectCount(reagentSuppliesInfo);
        List<ReagentSuppliesInfo> reagentSuppliesInfoList = reagentSuppliesInfoService.selectPaginationList(reagentSuppliesInfo, pageInfo);

        modelAndView.addObject("reagentSuppliesInfoList", reagentSuppliesInfoList);
        modelAndView.addObject("pageInfo", pageInfo.updatePageInfo(totalCnt));
        modelAndView.setViewName("center/supplies/storage");
        return modelAndView;
    }

    @RequestMapping("/listReagentSuppliesInfo.html")
    public ModelAndView listReagentSuppliesInfo(HttpServletRequest request,ReagentSuppliesInfo reagentSuppliesInfo,PageInfo pageInfo) {
        reagentSuppliesInfo = resetReagentSuppliesInfo(reagentSuppliesInfo);

        ModelAndView modelAndView = init();
        int totalCnt = reagentSuppliesInfoService.selectCount(reagentSuppliesInfo);
        List<ReagentSuppliesInfo> reagentSuppliesInfoList = reagentSuppliesInfoService.selectPaginationList(reagentSuppliesInfo, pageInfo);

        modelAndView.addObject("reagentSuppliesInfo", reagentSuppliesInfo);
        modelAndView.addObject("reagentSuppliesInfoList", reagentSuppliesInfoList);
        modelAndView.addObject("pageInfo", pageInfo.updatePageInfo(totalCnt));
        modelAndView.setViewName("center/supplies/reagentSupplies");
        return modelAndView;
    }

    @RequestMapping(value = "/saveReagentSuppliesInfo.html", method = RequestMethod.POST, produces="application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> saveReagentSuppliesInfo(HttpServletRequest request, @RequestBody ReagentSuppliesInfo reagentSuppliesInfo, String operateType){

        Map<String, Object> result = new HashMap<>();

        reagentSuppliesInfo = resetReagentSuppliesInfo(reagentSuppliesInfo);

        try{
            if(Constants.OPERATE_TYPE_ADD.equals(operateType)){    //根据标志判断添加修改
                reagentSuppliesInfo.setCreatePerson(LimsSecurityUtils.getLoginName());
                reagentSuppliesInfoService.add(reagentSuppliesInfo);
            }else if(Constants.OPERATE_TYPE_EDIT.equals(operateType)){
                reagentSuppliesInfo.setUpdatePerson(LimsSecurityUtils.getLoginName());
                reagentSuppliesInfoService.update(reagentSuppliesInfo);
            }
            result.put("success", true);
        }catch (Exception e){
            logger.error("addReagentSupplies error",e);
            result.put("success", false);
        }


        return result;
    }

    @RequestMapping("/editReagentSupplies.html")
    public ModelAndView editReagentSupplies(HttpServletRequest request,ReagentSuppliesInfo reagentSuppliesInfo, String operateType){

        if(Constants.OPERATE_TYPE_EDIT.equals(operateType)){    //根据标志判断添加修改
            reagentSuppliesInfo = reagentSuppliesInfoService.selectById(reagentSuppliesInfo.getId());
        }

        ModelAndView modelAndView = init();

        modelAndView.addObject("reagentSuppliesInfo",reagentSuppliesInfo);
        modelAndView.addObject("operateType", operateType);
        modelAndView.setViewName("center/supplies/editReagentSupplies");

        return modelAndView;
    }

    @RequestMapping("/delReagentSupplies.html")
    @ResponseBody
    public Map<String, Object> delReagentSupplies(HttpServletRequest request, Integer reagentSuppliesInfoId){
        Map<String, Object> result = new HashMap<String, Object>();

        try {
            reagentSuppliesInfoService.deleteById(reagentSuppliesInfoId);
            result.put("success", true);
        }catch (Exception e) {
            result.put("error", false);
        }

        return result;
    }

    @RequestMapping("/getStorageRecordInfo.html")
    @ResponseBody
    public StorageRecordInfo getStorageRecordInfo(HttpServletRequest request, Integer id){

        StorageRecordInfo storageRecordInfo = storageRecordInfoService.selectById(id);

        if (storageRecordInfo.getStorageDatetime() != null)
            storageRecordInfo.setStorageTime(DateUtils.dateToString(storageRecordInfo.getStorageDatetime(), "yyyy-MM-dd hh:mm"));


        if (storageRecordInfo.getEffectiveDatetime() != null)
            storageRecordInfo.setEffectiveTime(DateUtils.dateToString(storageRecordInfo.getEffectiveDatetime(), "yyyy-MM-dd hh:mm"));

        return storageRecordInfo;
    }

    @RequestMapping("selectStorageInfo.html")
    @ResponseBody
    public Map<String, Object> selectStorageInfo(HttpServletRequest request, Integer id){
        Map<String, Object> result = new HashMap<>();


        StorageInfo storageInfo = storageInfoService.selectStorageInfoByReagentSuppliesInfoId(id);

        if (storageInfo == null) {
            result.put("storageNum", 0);
        }else {
            result.put("storageNum", storageInfo.getStorageNum());
        }

        result.put("success", true);

        return result;
    }

    @RequestMapping("selectStorageInfoByBarcode.html")
    @ResponseBody
    public Map<String, Object> selectStorageInfoByBarcode (HttpServletRequest request, String barcode) {
        Map<String, Object> result = new HashMap<>();

        StorageRecordInfo storageRecordInfo = new StorageRecordInfo();
        storageRecordInfo.setBarcode(barcode);

        List<StorageRecordInfo> storageRecordInfoList = storageRecordInfoService.selectList(storageRecordInfo);

        if (ListUtils.isNotNullAndEmptyList(storageRecordInfoList)) {
            storageRecordInfo = storageRecordInfoList.get(0);
            result.put("reagentSuppliesInfoId", storageRecordInfo.getReagentSuppliesInfoId());
            result.put("effectiveTime", DateUtils.dateToString(storageRecordInfo.getEffectiveDatetime(), "yyyy-MM-dd hh:mm"));
            result.put("id", storageRecordInfo.getId());
        }

        result.put("success", true);
        return result;
    }

    @RequestMapping("/inStorageList.html")
    public ModelAndView inStorageList(HttpServletRequest request, StorageRecordInfo storageRecordInfo, PageInfo pageInfo) {

        StorageRecordInfo s = new StorageRecordInfo();
        s.setRecordType(Constants.FLAG_FALSE);
        List<StorageRecordInfo> recordInfoList = storageRecordInfoService.selectList(s);

        storageRecordInfo = resetStorageRecordInfo(storageRecordInfo);

        storageRecordInfo.setRecordType(Constants.FLAG_FALSE);
        int totalCnt = storageRecordInfoService.selectCount(storageRecordInfo);
        List<StorageRecordInfo> storageRecordInfoList = storageRecordInfoService.selectPaginationList(storageRecordInfo, pageInfo);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("pageInfo", pageInfo.updatePageInfo(totalCnt));
        modelAndView.addObject("recordInfoList", recordInfoList);
        modelAndView.addObject("storageRecordInfoList", storageRecordInfoList);

        modelAndView.setViewName("center/supplies/instoreList");

        return modelAndView;
    }

    @RequestMapping("/inStorage.html")
    public ModelAndView inStorage(HttpServletRequest request, StorageRecordInfo storageRecordInfo) {

        List<ReagentSuppliesInfo> reagentSuppliesInfoList = reagentSuppliesInfoService.selectList(null);

        if (storageRecordInfo.getId() != null) {
            storageRecordInfo = storageRecordInfoService.selectById(storageRecordInfo.getId());
        }

        if (storageRecordInfo.getStorageDatetime() != null)
            storageRecordInfo.setStorageTime(DateUtils.dateToString(storageRecordInfo.getStorageDatetime(), "yyyy-MM-dd hh:mm"));


        if (storageRecordInfo.getEffectiveDatetime() != null)
            storageRecordInfo.setEffectiveTime(DateUtils.dateToString(storageRecordInfo.getEffectiveDatetime(), "yyyy-MM-dd hh:mm"));

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("storageRecordInfo", storageRecordInfo);
        modelAndView.addObject("reagentSuppliesInfoList", reagentSuppliesInfoList);

        modelAndView.setViewName("center/supplies/instore");

        return modelAndView;
    }

    @RequestMapping("/outStorageList.html")
    public ModelAndView outStorageList(HttpServletRequest request, StorageRecordInfo storageRecordInfo, PageInfo pageInfo) {

        StorageRecordInfo s = new StorageRecordInfo();
        s.setRecordType(Constants.FLAG_TRUE);
        List<StorageRecordInfo> recordInfoList = storageRecordInfoService.selectList(s);

        storageRecordInfo = resetStorageRecordInfo(storageRecordInfo);

        storageRecordInfo.setRecordType(Constants.FLAG_TRUE);
        int totalCnt = storageRecordInfoService.selectCount(storageRecordInfo);
        List<StorageRecordInfo> storageRecordInfoList = storageRecordInfoService.selectPaginationList(storageRecordInfo, pageInfo);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("pageInfo", pageInfo.updatePageInfo(totalCnt));
        modelAndView.addObject("recordInfoList", recordInfoList);
        modelAndView.addObject("storageRecordInfoList", storageRecordInfoList);

        modelAndView.setViewName("center/supplies/outstoreList");

        return modelAndView;
    }

    @RequestMapping("/outStorage.html")
    public ModelAndView outStorage(HttpServletRequest request, StorageRecordInfo storageRecordInfo) {

        List<ReagentSuppliesInfo> reagentSuppliesInfoList = reagentSuppliesInfoService.selectList(null);

        StorageRecordInfo s = new StorageRecordInfo();
        s.setRecordType(Constants.FLAG_FALSE);
        List<StorageRecordInfo> recordInfoList = storageRecordInfoService.selectList(s);

        if (storageRecordInfo.getId() != null) {
            storageRecordInfo = storageRecordInfoService.selectById(storageRecordInfo.getId());
        }

        if (storageRecordInfo.getStorageDatetime() != null)
            storageRecordInfo.setStorageTime(DateUtils.dateToString(storageRecordInfo.getStorageDatetime(), "yyyy-MM-dd hh:mm"));


        if (storageRecordInfo.getEffectiveDatetime() != null)
            storageRecordInfo.setEffectiveTime(DateUtils.dateToString(storageRecordInfo.getEffectiveDatetime(), "yyyy-MM-dd hh:mm"));


        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("reagentSuppliesInfoList", reagentSuppliesInfoList);
        modelAndView.addObject("recordInfoList", recordInfoList);
        modelAndView.addObject("storageRecordInfo", storageRecordInfo);

        modelAndView.setViewName("center/supplies/outstore");

        return modelAndView;
    }

    @RequestMapping(value = "/saveInStore.html", method = RequestMethod.POST, produces="application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> saveInStore(HttpServletRequest request,@RequestBody StorageRecordInfo storageRecordInfo) {
        Map<String, Object> result = new HashMap<>();

        storageRecordInfo = resetStorageRecordInfo(storageRecordInfo);

        try {
            StorageInfo storageInfo = null;
            storageInfo = storageInfoService.selectStorageInfoByReagentSuppliesInfoId(storageRecordInfo.getReagentSuppliesInfoId());
            if (Constants.OPERATE_TYPE_ADD.equals(storageRecordInfo.getOperateType())) {
                storageRecordInfoService.insert(storageRecordInfo);

                if (storageInfo == null) {
                    storageInfo = new StorageInfo();
                    storageInfo.setReagentSuppliesInfoId(storageRecordInfo.getReagentSuppliesInfoId());
                    storageInfo.setStorageNum(storageRecordInfo.getStorageNum());
                    storageInfoService.add(storageInfo);
                }else {
                    storageInfo.setReagentSuppliesInfoId(storageRecordInfo.getReagentSuppliesInfoId());
                    storageInfo.setStorageNum(storageInfo.getStorageNum() + storageRecordInfo.getStorageNum());
                    storageInfoService.update(storageInfo);
                }
            }else {
                storageRecordInfoService.update(storageRecordInfo);

                Integer num = 0;
                StorageRecordInfo recordInfo = new StorageRecordInfo();
                recordInfo.setReagentSuppliesInfoId(storageRecordInfo.getReagentSuppliesInfoId());
                List<StorageRecordInfo> storageRecordInfoList = storageRecordInfoService.selectList(recordInfo);

                for (StorageRecordInfo sri : storageRecordInfoList) {
                    if (sri.getRecordType().equals(Constants.FLAG_FALSE)) {
                        num += sri.getStorageNum();
                    }else {
                        num -= sri.getStorageNum();
                    }
                }

                if (storageInfo == null) {
                    storageInfo = new StorageInfo();
                    storageInfo.setReagentSuppliesInfoId(storageRecordInfo.getReagentSuppliesInfoId());
                    storageInfo.setStorageNum(num);
                    storageInfoService.add(storageInfo);
                }else {
                    storageInfo.setReagentSuppliesInfoId(storageRecordInfo.getReagentSuppliesInfoId());
                    storageInfo.setStorageNum(num);
                    storageInfoService.update(storageInfo);
                }
            }

            result.put("success", true);
        }catch (Exception e) {
            result.put("success", false);
        }

        return result;
    }

    @RequestMapping(value = "/saveOutStore.html", method = RequestMethod.POST, produces="application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> saveOutStore(HttpServletRequest request,@RequestBody StorageRecordInfo storageRecordInfo) {
        Map<String, Object> result = new HashMap<>();

        try {
            storageRecordInfo = resetStorageRecordInfo(storageRecordInfo);

            if (Constants.OPERATE_TYPE_ADD.equals(storageRecordInfo.getOperateType())) {
                storageRecordInfoService.insert(storageRecordInfo);
            }else {
                storageRecordInfoService.update(storageRecordInfo);
            }

            Integer num = 0;
            StorageRecordInfo recordInfo = new StorageRecordInfo();
            recordInfo.setReagentSuppliesInfoId(storageRecordInfo.getReagentSuppliesInfoId());
            List<StorageRecordInfo> storageList = storageRecordInfoService.selectList(recordInfo);

            for (StorageRecordInfo sri : storageList) {
                if (sri.getRecordType().equals(Constants.FLAG_FALSE)) {
                    num += sri.getStorageNum();
                }else {
                    num -= sri.getStorageNum();
                }
            }

            StorageInfo storageInfo = null;
            storageInfo = storageInfoService.selectStorageInfoByReagentSuppliesInfoId(storageRecordInfo.getReagentSuppliesInfoId());

            if (storageInfo != null) {
                storageInfo.setReagentSuppliesInfoId(storageRecordInfo.getReagentSuppliesInfoId());
                storageInfo.setStorageNum(num);
                storageInfoService.update(storageInfo);
            }else {
                storageInfo = new StorageInfo();
                storageInfo.setReagentSuppliesInfoId(storageRecordInfo.getReagentSuppliesInfoId());
                storageInfo.setStorageNum(num);
                storageInfoService.add(storageInfo);
            }

            result.put("success", true);
        }catch (Exception e) {
            result.put("success", false);
        }

        return result;
    }

    @RequestMapping("/listStorageRecordInfo.html")
    public ModelAndView listStorageRecordInfo(HttpServletRequest request,StorageRecordInfo storageRecordInfo,PageInfo pageInfo) {
        ReagentSuppliesInfo reagentSuppliesInfo = new ReagentSuppliesInfo();
        List<ReagentSuppliesInfo> reagentSuppliesInfoList = reagentSuppliesInfoService.selectList(reagentSuppliesInfo);

        StorageRecordInfo s = new StorageRecordInfo();
        s.setRecordType(Constants.FLAG_FALSE);
        List<StorageRecordInfo> recordInfoList = storageRecordInfoService.selectList(s);

        storageRecordInfo = resetStorageRecordInfo(storageRecordInfo);

        int totalCnt = storageRecordInfoService.selectCount(storageRecordInfo);
        List<StorageRecordInfo> storageRecordInfoList = storageRecordInfoService.selectPaginationList(storageRecordInfo, pageInfo);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("storageRecordInfoList", storageRecordInfoList);
        modelAndView.addObject("recordInfoList", recordInfoList);
        modelAndView.addObject("storageRecordInfo", storageRecordInfo);
        modelAndView.addObject("reagentSuppliesInfoList", reagentSuppliesInfoList);
        modelAndView.addObject("pageInfo", pageInfo.updatePageInfo(totalCnt));

        modelAndView.setViewName("center/supplies/query");
        return modelAndView;
    }

    public ModelAndView init() {
        ModelAndView modelAndView = new ModelAndView();

        List<DictItem> experimentalStageList = dictService.selectDictItemListByType(Constants.DICT_TYPE_EXPERIMENTAL_STAGE);

        modelAndView.addObject("experimentalStageList", experimentalStageList);

        return modelAndView;
    }

    public ReagentSuppliesInfo resetReagentSuppliesInfo(ReagentSuppliesInfo reagentSuppliesInfo){

        if (StringUtils.isBlank(reagentSuppliesInfo.getReagentType())) {
            reagentSuppliesInfo.setReagentType(null);
        } else {
            reagentSuppliesInfo.setReagentType(reagentSuppliesInfo.getReagentType().trim());
        }

        if (StringUtils.isBlank(reagentSuppliesInfo.getReagentName())) {
            reagentSuppliesInfo.setReagentName(null);
        } else {
            reagentSuppliesInfo.setReagentName(reagentSuppliesInfo.getReagentName().trim());
        }

        if (StringUtils.isBlank(reagentSuppliesInfo.getReagentNo())) {
            reagentSuppliesInfo.setReagentNo(null);
        }else {
            reagentSuppliesInfo.setReagentNo(reagentSuppliesInfo.getReagentNo().trim());
        }

        if (StringUtils.isBlank(reagentSuppliesInfo.getReagentBrand())) {
            reagentSuppliesInfo.setReagentBrand(null);
        } else {
            reagentSuppliesInfo.setReagentBrand(reagentSuppliesInfo.getReagentBrand().trim());
        }

        if (StringUtils.isBlank(reagentSuppliesInfo.getReagentModel())) {
            reagentSuppliesInfo.setReagentModel(null);
        } else {
            reagentSuppliesInfo.setReagentModel(reagentSuppliesInfo.getReagentModel().trim());
        }

        if (StringUtils.isBlank(reagentSuppliesInfo.getReagentPrice())) {
            reagentSuppliesInfo.setReagentPrice(null);
        }else {
            reagentSuppliesInfo.setReagentPrice(reagentSuppliesInfo.getReagentPrice().trim());
        }

        if (StringUtils.isBlank(reagentSuppliesInfo.getReagentFirm())) {
            reagentSuppliesInfo.setReagentFirm(null);
        }else {
            reagentSuppliesInfo.setReagentFirm(reagentSuppliesInfo.getReagentFirm().trim());
        }

        if (StringUtils.isBlank(reagentSuppliesInfo.getExperimentalStage())) {
            reagentSuppliesInfo.setExperimentalStage(null);
        }else {
            reagentSuppliesInfo.setExperimentalStage(reagentSuppliesInfo.getExperimentalStage());
        }

        return reagentSuppliesInfo;
    }

    public StorageRecordInfo resetStorageRecordInfo(StorageRecordInfo storageRecordInfo){

        if (StringUtils.isBlank(storageRecordInfo.getBarcode())) {
            storageRecordInfo.setBarcode(null);
        }else {
            storageRecordInfo.setBarcode(storageRecordInfo.getBarcode().trim());
        }

        if(StringUtils.isBlank(storageRecordInfo.getReagentName())){
            storageRecordInfo.setReagentName(null);
        }else {
            storageRecordInfo.setReagentName(storageRecordInfo.getReagentName().trim());
        }

        if (StringUtils.isBlank(storageRecordInfo.getStoragePerson())) {
            storageRecordInfo.setStoragePerson(null);
        }else {
            storageRecordInfo.setStoragePerson(storageRecordInfo.getStoragePerson().trim());
        }

        if (StringUtils.isBlank(storageRecordInfo.getEffectiveTime())) {
            storageRecordInfo.setEffectiveDatetime(null);
        }else {
            storageRecordInfo.setEffectiveDatetime(DateUtils.stringToDate(storageRecordInfo.getEffectiveTime(), "yyyy-MM-dd hh:mm"));
        }

        if (StringUtils.isBlank(storageRecordInfo.getStorageTime())) {
            storageRecordInfo.setStorageDatetime(null);
        }else {
            storageRecordInfo.setStorageDatetime(DateUtils.stringToDate(storageRecordInfo.getStorageTime(), "yyyy-MM-dd hh:mm"));
        }

        if (storageRecordInfo.getStorageDatetimeStart() == null) {
            storageRecordInfo.setStorageDatetimeStart(null);
        }else {
            storageRecordInfo.setStorageDatetimeStart(storageRecordInfo.getStorageDatetimeStart());
        }

        if (storageRecordInfo.getStorageDatetimeEnd() == null) {
            storageRecordInfo.setStorageDatetimeEnd(null);
        }else {
            storageRecordInfo.setStorageDatetimeEnd(storageRecordInfo.getStorageDatetimeEnd());
        }

        if (storageRecordInfo.getEffectiveDatetimeStart() == null) {
            storageRecordInfo.setEffectiveDatetimeStart(null);
        }else {
            storageRecordInfo.setEffectiveDatetimeStart(storageRecordInfo.getEffectiveDatetimeStart());
        }

        if (storageRecordInfo.getEffectiveDatetimeEnd() == null) {
            storageRecordInfo.setEffectiveDatetimeEnd(storageRecordInfo.getEffectiveDatetimeEnd());
        }

        if (StringUtils.isBlank(storageRecordInfo.getRecordType())) {
            storageRecordInfo.setRecordType(null);
        }else {
            storageRecordInfo.setRecordType(storageRecordInfo.getRecordType());
        }

        if (StringUtils.isBlank(storageRecordInfo.getStorageRemark())) {
            storageRecordInfo.setStorageRemark(null);
        }else {
            storageRecordInfo.setStorageRemark(storageRecordInfo.getStorageRemark().trim());
        }

        return storageRecordInfo;
    }
}
