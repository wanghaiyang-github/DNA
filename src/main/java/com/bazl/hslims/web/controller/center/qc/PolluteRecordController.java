package com.bazl.hslims.web.controller.center.qc;

import com.bazl.hslims.manager.PageInfo;
import com.bazl.hslims.manager.model.po.QcPersonGene;
import com.bazl.hslims.manager.model.po.QcPolluteRecord;
import com.bazl.hslims.manager.model.vo.QcPolluteRecordVO;
import com.bazl.hslims.manager.services.common.QcPersonGeneService;
import com.bazl.hslims.manager.services.common.QcPolluteRecordService;
import com.bazl.hslims.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Administrator on 2017/2/16.
 */
@Controller
@RequestMapping("/center/qc")
public class PolluteRecordController extends BaseController {

    @Autowired
    QcPersonGeneService qcPersonGeneService;
    @Autowired
    QcPolluteRecordService qcPolluteRecordService;

    @RequestMapping("/polluteRecord.html")
    public ModelAndView listPolluteRecord(HttpServletRequest request, QcPolluteRecordVO query, PageInfo pageInfo){

        List<QcPersonGene> qcPersonGeneList = qcPersonGeneService.selectList(null);

        List<QcPolluteRecord> qcPolluteRecordList = qcPolluteRecordService.selectPaginationList(query, pageInfo);
        int totalCnt = qcPolluteRecordService.selectCount(query);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("qcPersonGeneList", qcPersonGeneList);
        modelAndView.addObject("qcPolluteRecordList", qcPolluteRecordList);
        modelAndView.addObject("pageInfo", pageInfo.updatePageInfo(totalCnt));
        modelAndView.setViewName("center/qc/listPolluteRecord");
        return modelAndView;
    }


}
