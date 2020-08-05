package com.bazl.hslims.web.controller;

import com.bazl.hslims.utils.IdcardValidator;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/15.
 */
@Controller
public class IdcardNoValidateController extends BaseController {

    @RequestMapping("/wt/checkIdcard.html")
    @ResponseBody
    public Map<String, Object> checkIdcardValidateForDelegate(HttpServletRequest request, HttpServletResponse response,
                                                              String idcardNo){
        return checkIdcardNo(idcardNo);
    }


    @RequestMapping("/center/checkIdcard.html")
    @ResponseBody
    public Map<String, Object> checkIdcardValidateForAccept(HttpServletRequest request, HttpServletResponse response,
                                                            String idcardNo){
        return checkIdcardNo(idcardNo);
    }

    private Map<String, Object> checkIdcardNo(String idcardNo){
        Map<String, Object> resultMap = new HashMap<String, Object>();

        boolean success = true;
        String errorMsg = "";

        if(StringUtils.isBlank(idcardNo)
                || (idcardNo.trim().length() != 15 && idcardNo.trim().length() != 18)) {
            success = false;
            errorMsg = "长度只能为15位或18位！";
        }else{
            success = IdcardValidator.isValidatedAllIdcard(idcardNo.trim());
            if(!success){
                errorMsg = "无效身份证号！";
            }
        }

        resultMap.put("success", success);
        resultMap.put("errorMsg", errorMsg);
        return resultMap;
    }

}
