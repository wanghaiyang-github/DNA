package com.bazl.hslims.web.controller.center.result;

import com.bazl.hslims.common.Constants;
import com.bazl.hslims.manager.PageInfo;
import com.bazl.hslims.manager.model.po.*;
import com.bazl.hslims.manager.model.vo.LimsCaseInfoVO;
import com.bazl.hslims.manager.model.vo.LimsSampleGeneVO;
import com.bazl.hslims.manager.model.vo.LimsSampleInfoVO;
import com.bazl.hslims.manager.services.common.*;
import com.bazl.hslims.utils.ListUtils;
import com.bazl.hslims.utils.RedisCacheUtil;
import com.bazl.hslims.web.controller.BaseController;
import com.bazl.hslims.web.datamodel.CheckGeneDataModel;
import com.bazl.hslims.web.helper.GeneInfoConverter;
import com.bazl.hslims.web.helper.codis.CodisFileParser;
import com.bazl.hslims.web.helper.codis.CodisGeneInfo;
import com.bazl.hslims.web.security.LimsSecurityUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2017/1/11.
 */
@Controller
@RequestMapping("/center/3")
public class SampleGeneController extends BaseController {

    @Autowired
    LimsSampleGeneService limsSampleGeneService;
    @Autowired
    LimsSampleInfoService limsSampleInfoService;
    @Autowired
    PanelInfoService panelInfoService;
    @Autowired
    MarkerInfoService markerInfoService;
    @Autowired
    DictService dictService;
    @Autowired
    LimsCaseInfoService limsCaseInfoService;
    @Autowired
    RedisCacheUtil redisCacheUtil;
    @Autowired
    QueueDetailService queueDetailService;
    @Autowired
    QueueSampleService queueSampleService;


    @RequestMapping("/listGene.html")
    public ModelAndView listGene(HttpServletRequest request, LimsCaseInfoVO query, PageInfo pageInfo, Integer caseId) throws ParseException {
        List<DictItem> caseStatusList = dictService.selectDictItemListByType(Constants.DICT_TYPE_CASE_STATUS);
        List<DictItem> caseTypeList = dictService.selectDictItemListByType(Constants.DICT_TPYE_CASE_TYPE);
        List<DictItem> casePropertyList = dictService.selectDictItemListByType(Constants.DICT_TPYE_CASE_PROPERTY);

        LimsCaseInfo limsCaseInfo = limsCaseInfoService.selectById(caseId);

        query = resetCaseInfoQuery(query);
        query.setCaseStatusList(Arrays.asList(Constants.CASE_INFO_STATUS_ACCEPTED, Constants.CASE_INFO_STATUS_FINISHED));
        query.setAdditionalFlag(Constants.FLAG_FALSE);
        query.setOrderByClause(" caseNo1 DESC, caseNo2 DESC");
        if (limsCaseInfo != null) {
            query.getEntity().setCaseNo(limsCaseInfo.getCaseNo());
        }
        //List<LimsCaseInfoVO> caseInfoList = limsCaseInfoService.selectVOPaginationList(query, pageInfo);
        List<LimsCaseInfoVO> caseInfoList = limsCaseInfoService.selectVOAllList(query, pageInfo);

        if (ListUtils.isNotNullAndEmptyList(caseInfoList)) {
            List<LimsSampleGene> sampleGeneList = null;
            for (LimsCaseInfoVO lciVo : caseInfoList) {
                sampleGeneList = limsSampleGeneService.selectListByCaseId(lciVo.getEntity().getId());

                lciVo.setCheckCount(sampleGeneList.size());
            }
        }
        int totalCnt = limsCaseInfoService.selectVOCnt(query);


        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("caseStatusList", caseStatusList);
        modelAndView.addObject("caseTypeList", caseTypeList);
        modelAndView.addObject("casePropertyList", casePropertyList);
        modelAndView.addObject("caseInfoQuery", query);
        modelAndView.addObject("caseInfoList", caseInfoList);
        modelAndView.addObject("pageInfo", pageInfo.updatePageInfo(totalCnt));
        modelAndView.addObject("limsCaseInfo", limsCaseInfo);
        /*query = resetSampleGeneVOQuery(query);

        List<LimsSampleGeneVO> limsSampleGeneVOList = limsSampleGeneService.selectVOPaginationList(query, pageInfo);
        int totalCnt = limsSampleGeneService.selectVOCount(query);

        if (ListUtils.isNotNullAndEmptyList(limsSampleGeneVOList)){
            for (int i = 0; i< limsSampleGeneVOList.size(); i++){
                LimsSampleGeneVO limsSampleGeneVO = limsSampleGeneVOList.get(i);
                LimsSampleGene limsSampleGene = limsSampleGeneVO.getEntity();

                List<LimsSampleGene> sampleGeneList = limsSampleGeneService.selectListBySampleId(limsSampleGene.getSampleId());
                Integer count = 0;
                count = sampleGeneList.size();
                limsSampleGene.setEnterCount(count);
            }
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("limsSampleGeneVOList", limsSampleGeneVOList);
        modelAndView.addObject("query",query);
        modelAndView.addObject("pageInfo", pageInfo.updatePageInfo(totalCnt));*/
//        modelAndView.setViewName("center/result/geneList");
        modelAndView.setViewName("center/result/caseInfoCheckList");
        return modelAndView;
    }

    @RequestMapping("/checkGene.html")
    public ModelAndView checkGene(HttpServletRequest request, Integer sampleId, int page) {

        List<LimsSampleGene> sampleGeneList = limsSampleGeneService.selectListBySampleId(sampleId);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("page", page);
        modelAndView.addObject("sampleGeneList", sampleGeneList);
        modelAndView.setViewName("center/result/checkGeneList");
        return modelAndView;
    }

    @RequestMapping("/checkSampleGene.html")
    public ModelAndView checkSampleGene(HttpServletRequest request, Integer caseId, LimsSampleGene limsSampleGene) {
        List<LimsSampleGene> sampleGeneList = limsSampleGeneService.selectListByCaseId(caseId);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("checkSampleGeneList", sampleGeneList);
        modelAndView.addObject("caseId", caseId);
        modelAndView.addObject("limsSampleGene", limsSampleGene);
        modelAndView.setViewName("center/result/checkSampleGeneList");
        return modelAndView;
    }

    /**
     * 确认审核基因型
     * @param request
     * @param checkGeneDataModel
     * @return
     */
    @RequestMapping(value = "/updateAuditStaus.html", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> updateAuditStaus(HttpServletRequest request, @RequestBody CheckGeneDataModel checkGeneDataModel) {
        Map<String, Object> map = new HashMap<>();
        LimsSampleInfo sampleInfo = new LimsSampleInfo();
        try {
            List<LimsSampleGene> sampleGeneList = checkGeneDataModel.getSampleGeneList();

            LimsSampleGene sampleGene = new LimsSampleGene();
            for (int i = 0; i < sampleGeneList.size(); i++) {
                sampleGene = sampleGeneList.get(i);
                limsSampleGeneService.updateBySampleId(sampleGene.getSampleId());

                sampleGene.setAuditPerson(LimsSecurityUtils.getLoginName());
                limsSampleGeneService.updateById(sampleGene);

                LimsSampleGene auditedSampleGene = limsSampleGeneService.selectAuditedBySampleId(sampleGene.getSampleId());
                //向redis插入数据
               redisCacheUtil.hset("sample_gene", String.valueOf(auditedSampleGene.getSampleId()), auditedSampleGene.getGeneInfo());
                //sampleInfo = limsSampleInfoService.selectById(sampleGene.getSampleId());
            }

            QueueSample queue = new QueueSample();
            for (int i = 0; i < sampleGeneList.size(); i++) {
                sampleGene = sampleGeneList.get(i);
                sampleInfo = limsSampleInfoService.selectById(sampleGene.getSampleId());
                if (sampleInfo.getSampleFlag().equals(Constants.SAMPLE_FLAG_EVIDENCE) && null != sampleInfo.getEvidenceNo()) {
                    //复核检材成功，向队列表插入物证已检出队列。
                    queue.setStatus("0");
                    //24,物证已检出
                    queue.setQueueType(Constants.CASE_INFO_DETECTION);
                    queue.setCaseId(sampleInfo.getCaseId());
                    queue.setConsignmentId(sampleInfo.getConsignmentId());
                    queue.setCreateDatetime(new Date());
                    queue.setOperatePerson(LimsSecurityUtils.getLoginName());
                    queueSampleService.insert(queue);
                    break;
                }
            }
            for (int i = 0; i < sampleGeneList.size(); i++) {
                sampleGene = sampleGeneList.get(i);
                try {
                     sampleInfo = limsSampleInfoService.selectById(sampleGene.getSampleId());

                    if (sampleInfo.getSampleFlag().equals(Constants.SAMPLE_FLAG_EVIDENCE) && null != sampleInfo.getEvidenceNo()) {
                        QueueDetail queueDetail = new QueueDetail();
                        queueDetail.setQueueId(queue.getId());
                        queueDetail.setCaseId(sampleGene.getCaseId());
                        queueDetail.setSampleId(sampleInfo.getEvidenceNo());
                        queueDetail.setStatus("0");
                        queueDetail.setCreatePerson(LimsSecurityUtils.getLoginName());
                        queueDetail.setCreateDatetime(new Date());
                        queueDetailService.insert(queueDetail);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error("插入委托队列错误！", e);
                }
            }
            map.put("success", true);
        } catch (Exception e) {
            logger.error("updateAuditStaus error!", e);
            map.put("success", false);
        }

        return map;
    }

    @RequestMapping("/viewCheckGene.html")
    public ModelAndView viewCheckGene(HttpServletRequest request, Integer id) {

        List<LimsSampleGene> sampleGeneList = limsSampleGeneService.selectListByGeneId(id);
        if (ListUtils.isNotNullAndEmptyList(sampleGeneList)) {
            LimsSampleGene limsSampleGene = sampleGeneList.get(0);

            if (StringUtils.isNotBlank(limsSampleGene.getGeneInfo()) && StringUtils.isNotBlank(limsSampleGene.getReagentName())) {
                List<CodisGeneInfo> codisGeneInfoList = GeneInfoConverter.getCodisInfoListByGeneInfoStr(limsSampleGene.getGeneInfo());

                List<CodisGeneInfo> geneInfoList = new ArrayList<>();
                if (limsSampleGene.getReagentName().equalsIgnoreCase(Constants.REGENT_NAME_1)) {
                    List<String> identifilerPlusList = Constants.IdentifilerPlusList;

                    geneInfoList = GeneInfoConverter.getCodisInfoList(identifilerPlusList, codisGeneInfoList);
                } else if (limsSampleGene.getReagentName().equalsIgnoreCase(Constants.REGENT_NAME_3)) {
                    List<String> yfilerPlusList = Constants.YfilerPlusList;

                    geneInfoList = GeneInfoConverter.getCodisInfoList(yfilerPlusList, codisGeneInfoList);
                }

                String geneInfo = null;
                if (ListUtils.isNotNullAndEmptyList(geneInfoList)) {
                    geneInfo = GeneInfoConverter.getJsonStrByCodisGeneInfoModel(geneInfoList);
                }

                if (StringUtils.isNotBlank(geneInfo)) {
                    limsSampleGene.setGeneInfo(geneInfo);
                }

            }
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("sampleGeneList", sampleGeneList);
        modelAndView.setViewName("center/result/viewCheckGene");
        return modelAndView;
    }

    @RequestMapping("/viewGene.html")
    public ModelAndView viewGene(HttpServletRequest request, LimsSampleGene sampleGene) {

        LimsSampleInfo sampleInfo = new LimsSampleInfo();
        sampleInfo.setId(sampleGene.getSampleId());
        sampleInfo.setSampleNo(sampleGene.getSampleNo());
        List<LimsSampleGene> sampleGeneList = limsSampleGeneService.selectGeneInfoList(sampleGene);
        LimsSampleGene limsSampleGene = new LimsSampleGene();
        String reagentName = null;
        int id = 0;
        int panelId = 0;
        if (ListUtils.isNotNullAndEmptyList(sampleGeneList)) {
            id = sampleGeneList.get(0).getId();
            reagentName = sampleGeneList.get(0).getReagentName();
            if (sampleGeneList.get(0).getPanelId() == null) {
                panelId = 0;
            } else {
                panelId = sampleGeneList.get(0).getPanelId();
            }
            limsSampleGene.setId(id);
            limsSampleGene.setPanelId(panelId);
            limsSampleGene.setReagentName(reagentName);
        }


        PanelInfo panelInfo = new PanelInfo();

        List<PanelInfo> panelInfoList = panelInfoService.selectPanelInfoList(panelInfo);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("panelInfoList", panelInfoList);
        modelAndView.addObject("sampleGeneList", sampleGeneList);
        modelAndView.addObject("limsSampleGene", limsSampleGene);
        modelAndView.addObject("sampleInfo", sampleInfo);
        modelAndView.setViewName("center/result/editGene");
        return modelAndView;
    }

    @RequestMapping(value = "/selectGeneInfoQuery.html", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public List<Panel> selectGeneInfoQuery(HttpServletRequest request, @RequestBody LimsSampleGene limsSampleGene) {

        List<LimsSampleGene> geneList = limsSampleGeneService.selectListByGeneId(limsSampleGene.getId());

        Panel panel = new Panel();
        panel.setPanelName(limsSampleGene.getReagentName());

        List<Panel> panelList = new ArrayList<>();
        try {
            List<LimsSampleGene> sampleGeneList = limsSampleGeneService.selectGeneInfoList(limsSampleGene);
            if (ListUtils.isNullOrEmptyList(sampleGeneList)) {
                panelList = panelInfoService.selectPanelList(panel);
                if (ListUtils.isNotNullAndEmptyList(geneList)) {
                    LimsSampleGene lsg = geneList.get(0);

                    if (StringUtils.isNotBlank(lsg.getGeneInfo()) && StringUtils.isNotBlank(limsSampleGene.getReagentName())) {
                        List<CodisGeneInfo> codisGeneInfoList = GeneInfoConverter.getCodisInfoListByGeneInfoStr(lsg.getGeneInfo());

                        List<CodisGeneInfo> geneInfoList = new ArrayList<>();
                        if (limsSampleGene.getReagentName().equalsIgnoreCase(Constants.REGENT_NAME_1)) {
                            List<String> identifilerPlusList = Constants.IdentifilerPlusList;

                            geneInfoList = GeneInfoConverter.getCodisInfoList(identifilerPlusList, codisGeneInfoList);
                        } else if (limsSampleGene.getReagentName().equalsIgnoreCase(Constants.REGENT_NAME_3)) {
                            List<String> yfilerPlusList = Constants.YfilerPlusList;

                            geneInfoList = GeneInfoConverter.getCodisInfoList(yfilerPlusList, codisGeneInfoList);
                        }

                        String geneInfo = null;
                        if (ListUtils.isNotNullAndEmptyList(geneInfoList)) {
                            geneInfo = GeneInfoConverter.getJsonStrByCodisGeneInfoModel(geneInfoList);

                            if (StringUtils.isNotBlank(geneInfo)) {
                                lsg.setGeneInfo(geneInfo);
                            }
                        }
                    }

                    String markerName = null;
                    String markerAlias = null;

                    if (StringUtils.isNotBlank(lsg.getGeneInfo()) && ListUtils.isNotNullAndEmptyList(panelList)) {
                        JSONArray json = JSONArray.fromObject(lsg.getGeneInfo());
                        for (int i = 0; i < panelList.size(); i++) {

                            markerName = panelList.get(i).getMarkerName();
                            List<MarkerInfo> markerInfoList = markerInfoService.selectMarkerInfoByName(markerName);
                            markerAlias = markerInfoList.get(0).getMarkerAlias();

                            if (json.size() > 0) {
                                for (int j = 0; j < json.size(); j++) {
                                    JSONObject job = json.getJSONObject(j);
                                    if (markerName.equals(job.get("geneName")) || markerAlias.equals(job.get("geneName"))) {
                                        panelList.get(i).setGeneVal1(job.get("geneVal1").toString());
                                        panelList.get(i).setGeneVal2(job.get("geneVal2").toString());
                                        panelList.get(i).setGeneVal3(job.get("geneVal3").toString());
                                        panelList.get(i).setGeneVal4(job.get("geneVal4").toString());
                                        break;
                                    } else {
                                        panelList.get(i).setGeneVal1("");
                                        panelList.get(i).setGeneVal2("");
                                        panelList.get(i).setGeneVal3("");
                                        panelList.get(i).setGeneVal4("");
                                    }
                                }
                            }
                        }
                    }
                } else {
                    for (int i = 0; i < panelList.size(); i++) {
                        panelList.get(i).setGeneVal1("");
                        panelList.get(i).setGeneVal2("");
                        panelList.get(i).setGeneVal3("");
                        panelList.get(i).setGeneVal4("");
                    }
                }
            } else {
                LimsSampleGene sampleGene = sampleGeneList.get(0);

                if (StringUtils.isNotBlank(sampleGene.getGeneInfo()) && StringUtils.isNotBlank(limsSampleGene.getReagentName())) {
                    List<CodisGeneInfo> codisGeneInfoList = GeneInfoConverter.getCodisInfoListByGeneInfoStr(sampleGene.getGeneInfo());

                    List<CodisGeneInfo> geneInfoList = new ArrayList<>();
                    if (limsSampleGene.getReagentName().equalsIgnoreCase(Constants.REGENT_NAME_1)) {
                        List<String> identifilerPlusList = Constants.IdentifilerPlusList;

                        geneInfoList = GeneInfoConverter.getCodisInfoList(identifilerPlusList, codisGeneInfoList);
                    } else if (limsSampleGene.getReagentName().equalsIgnoreCase(Constants.REGENT_NAME_3)) {
                        List<String> yfilerPlusList = Constants.YfilerPlusList;

                        geneInfoList = GeneInfoConverter.getCodisInfoList(yfilerPlusList, codisGeneInfoList);
                    }

                    String geneInfo = null;
                    if (ListUtils.isNotNullAndEmptyList(geneInfoList)) {
                        geneInfo = GeneInfoConverter.getJsonStrByCodisGeneInfoModel(geneInfoList);
                    }

                    if (StringUtils.isNotBlank(geneInfo)) {
                        sampleGene.setGeneInfo(geneInfo);
                    }

                }

                panel.setGeneInfoId(sampleGene.getId());
                panelList = panelInfoService.selectGenePanelList(panel);
                String geneInfo = sampleGene.getGeneInfo();
                if (StringUtils.isNotBlank(geneInfo)) {
                    if (ListUtils.isNotNullAndEmptyList(panelList)) {
                        for (Panel p : panelList) {
                            p.setGeneInfo(geneInfo);
                        }
                    }
                }
            }

        } catch (Exception e) {
            logger.error("selectPanelQuery error!", e);
        }

        return panelList;
    }

    @RequestMapping(value = "/saveGeneInfoQuery.html", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> saveGeneInfoQuery(HttpServletRequest request, @RequestBody LimsSampleInfoVO sampleInfoVo, Integer geneId) {

        PanelInfo panelInfo = new PanelInfo();
        panelInfo.setPanelName(sampleInfoVo.getReagentName());
        List<PanelInfo> panelInfoList = panelInfoService.selectQueryList(panelInfo);

        LimsSampleGene sampleGene = new LimsSampleGene();

        if (ListUtils.isNotNullAndEmptyList(panelInfoList)) {
            sampleGene.setPanelId(panelInfoList.get(0).getId());
        } else {
            sampleGene.setPanelId(sampleInfoVo.getPanelInfoId());
        }
        sampleGene.setReagentName(sampleInfoVo.getReagentName());
        sampleGene.setSampleId(sampleInfoVo.getEntity().getId());
        sampleGene.setSampleNo(sampleInfoVo.getEntity().getSampleNo());
        sampleGene.setGeneInfo(sampleInfoVo.getGeneInfo());
        if (CodisFileParser.isMix(sampleGene.getGeneInfo())) {
            sampleGene.setGeneType(Constants.GENE_TYPE_MIX);
        } else if (CodisFileParser.isYstr(sampleGene.getGeneInfo())) {
            sampleGene.setGeneType(Constants.GENE_TYPE_YSTR);
        } else {
            sampleGene.setGeneType(Constants.GENE_TYPE_STR);
        }
        sampleGene.setInstoredFlag(Constants.FLAG_FALSE);
        sampleGene.setDeleteFlag(Constants.FLAG_FALSE);
        sampleGene.setCreatePerson(LimsSecurityUtils.getLoginName());

        Map<String, Object> result = new HashMap<>();

        List<LimsSampleGene> sampleGeneList = limsSampleGeneService.selectListByGeneId(geneId);
        try {
            if (ListUtils.isNotNullAndEmptyList(sampleGeneList)) {
                sampleGene.setId(geneId);
                sampleGene.setUpdatePerson(LimsSecurityUtils.getLoginName());
                sampleGene.setAuditStatus(sampleGeneList.get(0).getAuditStatus());
                limsSampleGeneService.update(sampleGene);
            } else {
                sampleGene.setAuditStatus(Constants.FLAG_FALSE);
                limsSampleGeneService.insert(sampleGene);
            }
            result.put("geneId", sampleGene.getId());
            result.put("success", true);
        } catch (Exception e) {
            logger.error("saveGeneInfo error!", e);
            result.put("error", false);
        }

        return result;
    }

    public LimsSampleGeneVO resetSampleGeneVOQuery(LimsSampleGeneVO limsSampleGeneVO) {

        if (StringUtils.isBlank(limsSampleGeneVO.getEntity().getSampleNo())) {
            limsSampleGeneVO.getEntity().setSampleNo(null);
        } else {
            limsSampleGeneVO.getEntity().setSampleNo(limsSampleGeneVO.getEntity().getSampleNo().trim());
        }

        if (StringUtils.isBlank(limsSampleGeneVO.getCaseNo())) {
            limsSampleGeneVO.setCaseNo(null);
        } else {
            limsSampleGeneVO.setCaseNo(limsSampleGeneVO.getCaseNo().trim());
        }

        return limsSampleGeneVO;
    }

    public LimsCaseInfoVO resetCaseInfoQuery(LimsCaseInfoVO caseInfoVO) throws ParseException {
        if (StringUtils.isBlank(caseInfoVO.getEntity().getCaseName())) {
            caseInfoVO.getEntity().setCaseName(null);
        } else {
            caseInfoVO.getEntity().setCaseName(caseInfoVO.getEntity().getCaseName().trim());
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
        if (StringUtils.isBlank(caseInfoVO.getEntity().getCaseStatus())) {
            caseInfoVO.getEntity().setCaseStatus(null);
        } else {
            caseInfoVO.getEntity().setCaseStatus(caseInfoVO.getEntity().getCaseStatus());
        }

        if (StringUtils.isBlank(caseInfoVO.getEntity().getCaseType())) {
            caseInfoVO.getEntity().setCaseType(null);
        } else {
            caseInfoVO.getEntity().setCaseType(caseInfoVO.getEntity().getCaseType());
        }

        if (StringUtils.isBlank(caseInfoVO.getEntity().getCaseProperty())) {
            caseInfoVO.getEntity().setCaseProperty(null);
        } else {
            caseInfoVO.getEntity().setCaseProperty(caseInfoVO.getEntity().getCaseProperty());
        }

        if (caseInfoVO.getDelegateDatetimeStart() == null) {
            caseInfoVO.setDelegateDatetimeStart(null);
        } else {
            caseInfoVO.setDelegateDatetimeStart(caseInfoVO.getDelegateDatetimeStart());
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String oldTime = null;
        Date newTime = null;

        if (caseInfoVO.getDelegateDatetimeEnd() == null) {
            caseInfoVO.setDelegateDatetimeEnd(null);
        } else {
            oldTime = sdf.format(caseInfoVO.getDelegateDatetimeEnd());
            newTime = sd.parse(oldTime + " 23:59:59");
            caseInfoVO.setDelegateDatetimeEnd(newTime);
        }

        return caseInfoVO;
    }

    @RequestMapping(value = "/recheckSstate.html", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> recheckSstate(HttpServletRequest request, Integer caseId) {

        List<LimsSampleInfo> limsSampleInfos = limsSampleInfoService.selectListByCaseId(caseId);

        Map<String, Object> result = new HashMap<>();

        for (LimsSampleInfo limsSampleInfo : limsSampleInfos) {
            int sampleId = limsSampleInfo.getId();
            List<LimsSampleGene> limsSampleGenes = limsSampleGeneService.selectListBySampleId(sampleId);
            for (LimsSampleGene limsSampleGene : limsSampleGenes) {
                if (limsSampleGene.getAuditStatus() == "1") {
                    result.put("success", true);
                } else {
                    result.put("error", false);
                }
            }
        }
        return result;
    }


}
