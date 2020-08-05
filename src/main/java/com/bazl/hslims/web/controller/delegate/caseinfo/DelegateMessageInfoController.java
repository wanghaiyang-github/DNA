package com.bazl.hslims.web.controller.delegate.caseinfo;

import com.bazl.hslims.common.Constants;
import com.bazl.hslims.manager.PageInfo;
import com.bazl.hslims.manager.model.po.DictItem;
import com.bazl.hslims.manager.model.po.LimsCaseInfo;
import com.bazl.hslims.manager.model.vo.LimsCaseInfoVO;
import com.bazl.hslims.manager.services.common.DictService;
import com.bazl.hslims.manager.services.common.LimsCaseInfoService;
import com.bazl.hslims.utils.DateUtils;
import com.bazl.hslims.utils.SystemUtil;
import com.bazl.hslims.web.controller.BaseController;
import com.bazl.hslims.web.security.LimsSecurityUtils;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by Administrator on 2017/1/2.
 */
@Controller
@RequestMapping("/wt/message")
public class DelegateMessageInfoController extends BaseController {

    @Autowired
    LimsCaseInfoService limsCaseInfoService;
    @Autowired
    DictService dictService;

    @RequestMapping("/listIdentify.html")
    public ModelAndView list(HttpServletRequest request, LimsCaseInfoVO query, PageInfo pageInfo) {

        List<DictItem> caseStatusList = dictService.selectDictItemListByType(Constants.DICT_TYPE_CASE_STATUS);
        List<DictItem> caseTypeList = dictService.selectDictItemListByType(Constants.DICT_TPYE_CASE_TYPE);
        List<DictItem> casePropertyList = dictService.selectDictItemListByType(Constants.DICT_TPYE_CASE_PROPERTY);

        query = resetCaseInfoQuery(query);
        query.setCaseStatusList(Arrays.asList(Constants.CASE_INFO_STATUS_ACCEPTED,Constants.CASE_INFO_STATUS_FINISHED));
        query.setAdditionalFlag(Constants.FLAG_FALSE);
        query.setIdentifyBookStatus(Constants.IDENTIFY_BOOK_SIGNED);
        query.setOrderByClause(" caseNo1 DESC, caseNo2 DESC");
        query.setDelegateOrg(LimsSecurityUtils.getLoginDelegateOrg().getOrgCode());
        List<LimsCaseInfoVO> caseInfoList = limsCaseInfoService.selectVOPaginationList(query, pageInfo);
        int totalCnt = limsCaseInfoService.selectVOCnt(query);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("query", query);
        modelAndView.addObject("casePropertyList", casePropertyList);
        modelAndView.addObject("caseInfoList", caseInfoList);
        modelAndView.addObject("pageInfo", pageInfo.updatePageInfo(totalCnt));
        modelAndView.addObject("loginTitleName", SystemUtil.getSystemConfig().getProperty("loginTitleName"));
        modelAndView.setViewName("delegate/caseinfo/listMessage");
        return modelAndView;
    }

    @RequestMapping("/printReceiveFrom.html")
    public void generateAndDownloadExtractDoc(HttpServletRequest request, HttpServletResponse response, Integer caseId) {

        LimsCaseInfo caseInfo = limsCaseInfoService.selectById(caseId);

        Map<String, Object> params = new HashMap<String, Object>();

        try {
            Configuration cfg = new Configuration();
            cfg.setDefaultEncoding("UTF-8");
            ServletContext servletContext = request.getSession().getServletContext();
            cfg.setServletContextForTemplateLoading(servletContext, "/templates");
            //获取模板
            Template tmp = cfg.getTemplate("printReceive.ftl", "UTF-8");

            String filename = "-领取单"+ DateUtils.dateToString(new Date(),"yyyyMMddHHmmss") +".doc";

            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-disposition", "attachment;filename="+ (StringUtils.isBlank(caseInfo.getCaseNo())?"":caseInfo.getCaseNo()) + new String(filename.getBytes("GBK"),"ISO-8859-1"));//文件头，导出的文件名
            response.setContentType("application/x-msdownload");//类型

            tmp.process(params, response.getWriter());
        } catch (Exception ex) {
            logger.error("", ex);
        }

    }

    public LimsCaseInfoVO resetCaseInfoQuery(LimsCaseInfoVO caseInfoVO){
        if (StringUtils.isBlank(caseInfoVO.getEntity().getCaseNo())){
            caseInfoVO.getEntity().setCaseNo(null);
        }else {
            caseInfoVO.getEntity().setCaseNo(caseInfoVO.getEntity().getCaseNo());
        }

        if (StringUtils.isBlank(caseInfoVO.getEntity().getCaseXkNo())){
            caseInfoVO.getEntity().setCaseXkNo(null);
        }else {
            caseInfoVO.getEntity().setCaseXkNo(caseInfoVO.getEntity().getCaseXkNo());
        }

        if (StringUtils.isBlank(caseInfoVO.getEntity().getCaseName())){
            caseInfoVO.getEntity().setCaseName(null);
        }else {
            caseInfoVO.getEntity().setCaseName(caseInfoVO.getEntity().getCaseName());
        }

        if (StringUtils.isBlank(caseInfoVO.getDelegatorName())){
            caseInfoVO.setDelegatorName(null);
        }else {
            caseInfoVO.setDelegatorName(caseInfoVO.getDelegatorName());
        }

        if (StringUtils.isBlank(caseInfoVO.getDelegateAcceptor())){
            caseInfoVO.setDelegateAcceptor(null);
        }else {
            caseInfoVO.setDelegateAcceptor(caseInfoVO.getDelegateAcceptor());
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

        return caseInfoVO;
    }

}
