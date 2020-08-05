package com.bazl.hslims.web.controller.center.result;

import com.bazl.hslims.common.Constants;
import com.bazl.hslims.manager.PageInfo;
import com.bazl.hslims.manager.model.po.*;
import com.bazl.hslims.manager.model.vo.LimsCaseInfoVO;
import com.bazl.hslims.manager.model.vo.LimsSampleInfoVO;
import com.bazl.hslims.manager.services.common.*;
import com.bazl.hslims.utils.ListUtils;
import com.bazl.hslims.web.controller.BaseController;
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
 * Created by Administrator on 2017/3/24.
 */
@Controller
@RequestMapping("/center/3")
public class InputGeneController  extends BaseController {

    @Autowired
    DictService dictService;
    @Autowired
    DelegateOrgService delegateOrgService;
    @Autowired
    LabUserService labUserService;
    @Autowired
    LimsCaseInfoService limsCaseInfoService;
    @Autowired
    LimsConsignmentService limsConsignmentService;
    @Autowired
    LimsPersonInfoService limsPersonInfoService;
    @Autowired
    LimsSampleInfoService limsSampleInfoService;
    @Autowired
    PanelInfoService panelInfoService;
    @Autowired
    LimsSampleGeneService limsSampleGeneService;
    @Autowired
    MarkerInfoService markerInfoService;

    @RequestMapping("/inputViewGene.html")
    public ModelAndView inputViewGene(HttpServletRequest request, LimsCaseInfoVO query, PageInfo pageInfo) throws ParseException {

        List<DictItem> caseTypeList = dictService.selectDictItemListByType(Constants.DICT_TPYE_CASE_TYPE);
        List<DictItem> casePropertyList = dictService.selectDictItemListByType(Constants.DICT_TPYE_CASE_PROPERTY);
        List<DelegateOrg> delegateOrgList = delegateOrgService.selectAll();
        /*  实验室人员 */
        List<LabUser> labUserList = labUserService.selectAll();

        query = resetCaseInfoQuery(query);
        query.setConsignmentStatus(Constants.CASE_INFO_STATUS_ACCEPTED);
        query.setOrderByClause(" caseNo1 DESC, caseNo2 DESC");
        //List<LimsCaseInfoVO> caseInfoList = limsCaseInfoService.selectVOPaginationList(query, pageInfo);
        List<LimsCaseInfoVO> caseInfoList = limsCaseInfoService.selectVOAllList(query, pageInfo);
        if (ListUtils.isNotNullAndEmptyList(caseInfoList)) {
            List<LimsSampleGene> sampleGeneList;
            for (LimsCaseInfoVO lciVo : caseInfoList) {
                sampleGeneList = limsSampleGeneService.selectListByCaseId(lciVo.getEntity().getId());

                lciVo.setCheckCount(sampleGeneList.size());
            }
        }
        int totalCnt = limsCaseInfoService.selectVOCnt(query);
        /*int totalCnt = limsCaseInfoService.selectVOCnt(query);*/

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("caseTypeList", caseTypeList);
        modelAndView.addObject("casePropertyList", casePropertyList);
        modelAndView.addObject("delegateOrgList", delegateOrgList);
        modelAndView.addObject("labUserList", labUserList);
        modelAndView.addObject("caseInfoQuery", query);
        modelAndView.addObject("caseInfoList", caseInfoList);
        modelAndView.addObject("pageInfo", pageInfo.updatePageInfo(totalCnt));
        modelAndView.setViewName("center/result/inputViewGene");
        return modelAndView;
    }

    @RequestMapping("/inputGene.html")
    public ModelAndView inputGene(HttpServletRequest request, LimsSampleGene sampleGene) {

        List<LimsSampleGene> sampleGeneList = null;

        if (sampleGene.getId() == null || sampleGene.getId() == 0) {
            sampleGeneList = limsSampleGeneService.selectGeneInfoList(sampleGene);
        }else {
            sampleGeneList = limsSampleGeneService.selectListByGeneId(sampleGene.getId());
        }

        LimsSampleInfo sampleInfo = limsSampleInfoService.selectById(sampleGene.getSampleId());

        sampleGene.setSampleId(sampleInfo.getId());
        sampleGene.setSampleNo(sampleInfo.getSampleNo());

        LimsSampleGene limsSampleGene = new LimsSampleGene();
        if(ListUtils.isNotNullAndEmptyList(sampleGeneList)){
            LimsSampleGene lsg = sampleGeneList.get(0);

            limsSampleGene.setId(lsg.getId());
            limsSampleGene.setReagentName(lsg.getReagentName());

            if (StringUtils.isNotBlank(lsg.getGeneInfo()) && StringUtils.isNotBlank(lsg.getReagentName())) {
                List<CodisGeneInfo> codisGeneInfoList = GeneInfoConverter.getCodisInfoListByGeneInfoStr(lsg.getGeneInfo());

                List<CodisGeneInfo> geneInfoList = new ArrayList<>();
                if (limsSampleGene.getReagentName().equalsIgnoreCase(Constants.REGENT_NAME_1)) {
                    List<String> identifilerPlusList = Constants.IdentifilerPlusList;

                    geneInfoList = GeneInfoConverter.getCodisInfoList(identifilerPlusList, codisGeneInfoList);
                }else if (limsSampleGene.getReagentName().equalsIgnoreCase(Constants.REGENT_NAME_3)) {
                    List<String> yfilerPlusList = Constants.YfilerPlusList;

                    geneInfoList = GeneInfoConverter.getCodisInfoList(yfilerPlusList, codisGeneInfoList);
                }

                String geneInfo = null;
                if (ListUtils.isNotNullAndEmptyList(geneInfoList))
                    geneInfo = GeneInfoConverter.getJsonStrByCodisGeneInfoModel(geneInfoList);

                if (StringUtils.isNotBlank(geneInfo))
                    lsg.setGeneInfo(geneInfo);

            }
        }


        PanelInfo panelInfo = new PanelInfo();
        panelInfo = getPanelInfo(panelInfo);

        List<PanelInfo> panelInfoList = panelInfoService.selectPanelInfoList(panelInfo);

        if (sampleGeneList.size() == 0){
            MarkerInfo markerInfo = new MarkerInfo();
            List<MarkerInfo> markerInfoList = markerInfoService.selectMarkerInfoList(markerInfo);
            if (ListUtils.isNotNullAndEmptyList(markerInfoList)){

                StringBuffer sb= new StringBuffer("[");
                for (int i = 0;i < markerInfoList.size();i++){

                    String markerName = markerInfoList.get(i).getMarkerName();

                    if(i == markerInfoList.size()-1){
                        sb.append("{\"geneName\":\"");
                        sb.append(markerName);
                        sb.append("\",\"geneVal1\":\"");
                        sb.append("");
                        sb.append("\",\"geneVal2\":\"");
                        sb.append("");
                        sb.append("\",\"geneVal3\":\"");
                        sb.append("");
                        sb.append("\",\"geneVal4\":\"");
                        sb.append("\"");
                        sb.append("}");
                    }else {
                        sb.append("{\"geneName\":\"");
                        sb.append(markerName);
                        sb.append("\",\"geneVal1\":\"");
                        sb.append("");
                        sb.append("\",\"geneVal2\":\"");
                        sb.append("");
                        sb.append("\",\"geneVal3\":\"");
                        sb.append("");
                        sb.append("\",\"geneVal4\":\"");
                        sb.append("\"");
                        sb.append("},");
                    }

                }
                sb.append("]");
                if (StringUtils.isNotBlank(sb.toString())){
                    sampleGene.setGeneInfo(sb.toString());
                    sampleGeneList.add(sampleGene);
                    limsSampleGene.setPanelId(0);
                }
            }
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("panelInfoList",panelInfoList);
        modelAndView.addObject("sampleGeneList",sampleGeneList);
        modelAndView.addObject("limsSampleGene",limsSampleGene);
        modelAndView.addObject("sampleInfo",sampleInfo);
        modelAndView.setViewName("center/result/inputGene");
        return modelAndView;
    }

    @RequestMapping("/viewGeneList.html")
    public ModelAndView viewGeneList(HttpServletRequest request, Integer sampleGeneId, int page) {

        List<LimsSampleGene> sampleGeneList = limsSampleGeneService.selectListByGeneId(sampleGeneId);
        LimsSampleInfo limsSampleInfo = limsSampleInfoService.selectById(sampleGeneList.get(0).getSampleId());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("page", page);
        modelAndView.addObject("sampleGeneList",sampleGeneList);
        modelAndView.addObject("sampleInfo",limsSampleInfo);
        modelAndView.setViewName("center/result/viewGeneList");
        return modelAndView;
    }

    @RequestMapping("/editGene.html")
    public ModelAndView editGene(HttpServletRequest request,Integer id){

        List<LimsSampleGene> sampleGeneList = limsSampleGeneService.selectListByGeneId(id);

        LimsSampleInfo sampleInfo = new LimsSampleInfo();
        LimsSampleGene limsSampleGene = new LimsSampleGene();

        if (ListUtils.isNotNullAndEmptyList(sampleGeneList)) {
            limsSampleGene = sampleGeneList.get(0);

            sampleInfo.setId(limsSampleGene.getSampleId());
            sampleInfo.setSampleNo(limsSampleGene.getSampleNo());

            if (StringUtils.isNotBlank(limsSampleGene.getGeneInfo()) && StringUtils.isNotBlank(limsSampleGene.getReagentName())) {
                List<CodisGeneInfo> codisGeneInfoList = GeneInfoConverter.getCodisInfoListByGeneInfoStr(limsSampleGene.getGeneInfo());

                List<CodisGeneInfo> geneInfoList = new ArrayList<>();
                List<String> identifilerPlusList;
                List<String> yfilerPlusList;
                if (limsSampleGene.getReagentName().equalsIgnoreCase(Constants.REGENT_NAME_1)) {
                    identifilerPlusList = Constants.IdentifilerPlusList;

                    geneInfoList = GeneInfoConverter.getCodisInfoList(identifilerPlusList, codisGeneInfoList);
                }else if (limsSampleGene.getReagentName().equalsIgnoreCase(Constants.REGENT_NAME_3)) {
                    yfilerPlusList = Constants.YfilerPlusList;

                    geneInfoList = GeneInfoConverter.getCodisInfoList(yfilerPlusList, codisGeneInfoList);
                }

                String geneInfo = null;
                if (ListUtils.isNotNullAndEmptyList(geneInfoList))
                    geneInfo = GeneInfoConverter.getJsonStrByCodisGeneInfoModel(geneInfoList);

                if (StringUtils.isNotBlank(geneInfo)) {
                    limsSampleGene.setGeneInfo(geneInfo);
                }

            }
        }

        PanelInfo panelInfo = new PanelInfo();
        panelInfo = getPanelInfo(panelInfo);

        List<PanelInfo> panelInfoList = panelInfoService.selectPanelInfoList(panelInfo);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("sampleGeneList",sampleGeneList);
        modelAndView.addObject("sampleInfo",sampleInfo);
        modelAndView.addObject("limsSampleGene",limsSampleGene);
        modelAndView.addObject("panelInfoList",panelInfoList);
        modelAndView.setViewName("center/result/editGene");
        return modelAndView;
    }

    @RequestMapping("/checkList.html")
    public ModelAndView editCase(HttpServletRequest request, Integer consignmentId, int page) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView = initDictList(modelAndView);

        LimsCaseInfo caseInfo = limsCaseInfoService.selectByConsignmentId(consignmentId);
        LimsConsignment consignment = limsConsignmentService.selectById(consignmentId);
        List<LimsPersonInfo> personInfoList = limsPersonInfoService.selectListByConsignmentId(consignmentId);
        List<LimsSampleInfo> sampleInfoList = limsSampleInfoService.selectListByConsignmentId(consignmentId);
        //只显示已受理的检材
        for(Iterator<LimsSampleInfo> it = sampleInfoList.iterator(); it.hasNext();){
            LimsSampleInfo si = it.next();
            if(!Constants.SAMPLE_ACCEPT_STATUS_ACCEPTED.equals(si.getAcceptStatus())){
                it.remove();
            }
        }

        //如果补送时，加载该案件其他委托中已受理的人员和检材列表
        List<LimsPersonInfo> acceptedPersonInfoList = null;
        List<LimsSampleInfo> acceptedSampleInfoList = null;
        if(Constants.FLAG_TRUE.equals(consignment.getAdditionalFlag())){
            acceptedPersonInfoList = limsPersonInfoService.selectListByCaseId(caseInfo.getId());
            acceptedSampleInfoList = limsSampleInfoService.selectAcceptedListByCaseId(caseInfo.getId());

            //去掉本次委托的人员、检材
            LimsPersonInfo tmpPerson = null;
            for(Iterator<LimsPersonInfo> personInfoIterator = acceptedPersonInfoList.iterator(); personInfoIterator.hasNext();){
                tmpPerson = personInfoIterator.next();
                if(tmpPerson.getConsignmentId().equals(consignmentId)){
                    personInfoIterator.remove();
                }
            }

            LimsSampleInfo tmpSample = null;
            for(Iterator<LimsSampleInfo> sampleInfoIterator = acceptedSampleInfoList.iterator(); sampleInfoIterator.hasNext();){
                tmpSample = sampleInfoIterator.next();
                if(tmpSample.getConsignmentId().equals(consignmentId)){
                    sampleInfoIterator.remove();
                }
            }

            if (ListUtils.isNotNullAndEmptyList(acceptedSampleInfoList)){
                for (LimsSampleInfo lsi:acceptedSampleInfoList){

                    List<LimsSampleGene> sampleGeneList = limsSampleGeneService.selectListBySampleId(lsi.getId());

                    lsi.setEnterCount(sampleGeneList.size());

                }
            }

            modelAndView.addObject("otherPersonInfoList", acceptedPersonInfoList);
            modelAndView.addObject("otherSampleInfoList", acceptedSampleInfoList);
        }

        if (ListUtils.isNotNullAndEmptyList(sampleInfoList)){
            for (LimsSampleInfo lsi:sampleInfoList){
                Integer sampleId = lsi.getId();

                List<LimsSampleGene> sampleGeneList = limsSampleGeneService.selectListBySampleId(sampleId);

                lsi.setEnterCount(sampleGeneList.size());

            }
        }

        modelAndView.addObject("consignment", consignment);
        modelAndView.addObject("page", page);
        modelAndView.addObject("personInfoList", personInfoList);
        modelAndView.addObject("sampleInfoList", sampleInfoList);
        modelAndView.addObject("caseInfo",caseInfo);
        modelAndView.addObject("consignmentId",consignmentId);
        modelAndView.setViewName("center/result/resultList");
        return modelAndView;
    }

    @RequestMapping(value="/selectPanelQuery.html", method = RequestMethod.POST, produces="application/json; charset=utf-8")
    @ResponseBody
    public List<Panel> selectPanelQuery(HttpServletRequest request, @RequestBody LimsSampleGene limsSampleGene){

        List<LimsSampleGene> geneList = limsSampleGeneService.selectListByGeneId(limsSampleGene.getId());
        List<LimsSampleGene> sampleGeneList = limsSampleGeneService.selectGeneInfoList(limsSampleGene);
        Panel panel = new Panel();
        panel.setPanelName(limsSampleGene.getReagentName());

        List<Panel> panelList = new ArrayList<>();
        try {

            if(ListUtils.isNullOrEmptyList(sampleGeneList)){
                panelList = panelInfoService.selectPanelList(panel);
                if(ListUtils.isNotNullAndEmptyList(geneList)){

                    LimsSampleGene lsg = geneList.get(0);

                    if (StringUtils.isNotBlank(lsg.getGeneInfo()) && StringUtils.isNotBlank(limsSampleGene.getReagentName())) {
                        List<CodisGeneInfo> codisGeneInfoList = GeneInfoConverter.getCodisInfoListByGeneInfoStr(lsg.getGeneInfo());

                        List<CodisGeneInfo> geneInfoList = new ArrayList<>();
                        if (limsSampleGene.getReagentName().equalsIgnoreCase(Constants.REGENT_NAME_1)) {
                            List<String> identifilerPlusList = Constants.IdentifilerPlusList;

                            geneInfoList = GeneInfoConverter.getCodisInfoList(identifilerPlusList, codisGeneInfoList);
                        }else if (limsSampleGene.getReagentName().equalsIgnoreCase(Constants.REGENT_NAME_3)) {
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

                    if(StringUtils.isNotBlank(lsg.getGeneInfo()) && ListUtils.isNotNullAndEmptyList(panelList)){
                        JSONArray json = JSONArray.fromObject(lsg.getGeneInfo());

                        String markerName = null;
                        String markerAlias = null;

                        for (int i=0;i<panelList.size();i++){

                            markerName = panelList.get(i).getMarkerName();

                            List<MarkerInfo> markerInfoList = null;
                            markerInfoList = markerInfoService.selectMarkerInfoByName(markerName);

                            markerAlias = markerInfoList.get(0).getMarkerAlias();

                            if(json.size() > 0){
                                for (int j=0;j<json.size();j++){
                                    JSONObject job = json.getJSONObject(j);
                                    if(markerName.equals(job.get("geneName")) || markerAlias.equals(job.get("geneName"))){
                                        String geneVal1 = job.get("geneVal1").toString();
                                        String geneVal2 = job.get("geneVal2").toString();
                                        String geneVal3 = job.get("geneVal3").toString();
                                        String geneVal4 = job.get("geneVal4").toString();
                                        panelList.get(i).setGeneVal1(geneVal1);
                                        panelList.get(i).setGeneVal2(geneVal2);
                                        panelList.get(i).setGeneVal3(geneVal3);
                                        panelList.get(i).setGeneVal4(geneVal4);
                                        break;
                                    }else {
                                        panelList.get(i).setGeneVal1("");
                                        panelList.get(i).setGeneVal2("");
                                        panelList.get(i).setGeneVal3("");
                                        panelList.get(i).setGeneVal4("");
                                    }
                                }
                            }
                        }
                    }
                }else {
                    if (ListUtils.isNotNullAndEmptyList(panelList)){
                        for (int i=0;i<panelList.size();i++){
                            panelList.get(i).setGeneVal1("");
                            panelList.get(i).setGeneVal2("");
                            panelList.get(i).setGeneVal3("");
                            panelList.get(i).setGeneVal4("");
                        }
                    }else {
                        MarkerInfo markerInfo = new MarkerInfo();
                        Panel panel1 = new Panel();
                        List<MarkerInfo> markerInfoList = markerInfoService.selectMarkerInfoList(markerInfo);
                        if (ListUtils.isNotNullAndEmptyList(markerInfoList)){

                            StringBuffer sb= new StringBuffer("[");
                            for (int i = 0;i < markerInfoList.size();i++){
                                String markerName = null;
                                markerName = markerInfoList.get(i).getMarkerName();

                                if(i == markerInfoList.size()-1){
                                    sb.append("{\"geneName\":\"");
                                    sb.append(markerName);
                                    sb.append("\",\"geneVal1\":\"");
                                    sb.append("");
                                    sb.append("\",\"geneVal2\":\"");
                                    sb.append("\"");
                                    sb.append("\",\"geneVal3\":\"");
                                    sb.append("");
                                    sb.append("\",\"geneVal4\":\"");
                                    sb.append("");
                                    sb.append("}");
                                }else {
                                    sb.append("{\"geneName\":\"");
                                    sb.append(markerName);
                                    sb.append("\",\"geneVal1\":\"");
                                    sb.append("");
                                    sb.append("\",\"geneVal2\":\"");
                                    sb.append("\"");
                                    sb.append("\",\"geneVal3\":\"");
                                    sb.append("");
                                    sb.append("\",\"geneVal4\":\"");
                                    sb.append("");
                                    sb.append("},");
                                }

                            }
                            sb.append("]");
                            if (StringUtils.isNotBlank(sb.toString())){
                                panel1.setGeneInfo(sb.toString());
                                panelList.add(panel1);
                            }
                        }
                    }
                }
            }else {
                LimsSampleGene sampleGene = sampleGeneList.get(0);

                if (StringUtils.isNotBlank(sampleGene.getGeneInfo()) && StringUtils.isNotBlank(limsSampleGene.getReagentName())) {
                    List<CodisGeneInfo> codisGeneInfoList = GeneInfoConverter.getCodisInfoListByGeneInfoStr(sampleGene.getGeneInfo());

                    List<CodisGeneInfo> geneInfoList = new ArrayList<>();
                    if (limsSampleGene.getReagentName().equalsIgnoreCase(Constants.REGENT_NAME_1)) {
                        List<String> identifilerPlusList = Constants.IdentifilerPlusList;

                        geneInfoList = GeneInfoConverter.getCodisInfoList(identifilerPlusList, codisGeneInfoList);
                    }else if (limsSampleGene.getReagentName().equalsIgnoreCase(Constants.REGENT_NAME_3)) {
                        List<String> yfilerPlusList = Constants.YfilerPlusList;

                        geneInfoList = GeneInfoConverter.getCodisInfoList(yfilerPlusList, codisGeneInfoList);
                    }

                    String geneInfo = null;
                    if (ListUtils.isNotNullAndEmptyList(geneInfoList)) {
                        geneInfo = GeneInfoConverter.getJsonStrByCodisGeneInfoModel(geneInfoList);

                        if (StringUtils.isNotBlank(geneInfo))
                            sampleGene.setGeneInfo(geneInfo);
                    }
                }

                panel.setGeneInfoId(sampleGene.getId());
                panelList = panelInfoService.selectGenePanelList(panel);
                String geneInfo = sampleGene.getGeneInfo();
                if(StringUtils.isNotBlank(geneInfo)){
                    if (ListUtils.isNotNullAndEmptyList(panelList)) {
                        for (Panel p : panelList) {
                            p.setGeneInfo(geneInfo);
                        }
                    }
                }
            }

        }catch (Exception e){
            logger.error("selectPanelQuery error!",e);
        }

        return panelList;
    }

    @RequestMapping(value="/saveGeneInfo.html", method = RequestMethod.POST, produces="application/json; charset=utf-8")
    @ResponseBody
    public Map<String,Object> saveGeneInfo(HttpServletRequest request, @RequestBody LimsSampleInfoVO sampleInfoVo, Integer geneId){

        PanelInfo panelInfo = new PanelInfo();
        panelInfo.setPanelName(sampleInfoVo.getReagentName());
        List<PanelInfo> panelInfoList = panelInfoService.selectQueryList(panelInfo);

        LimsSampleGene sampleGene = new LimsSampleGene();

        if(ListUtils.isNotNullAndEmptyList(panelInfoList)){
            sampleGene.setPanelId(panelInfoList.get(0).getId());
        }else {
            sampleGene.setPanelId(sampleInfoVo.getPanelInfoId());
        }
        sampleGene.setReagentName(sampleInfoVo.getReagentName());
        sampleGene.setSampleId(sampleInfoVo.getEntity().getId());
        sampleGene.setSampleNo(sampleInfoVo.getEntity().getSampleNo());
        sampleGene.setGeneInfo(sampleInfoVo.getGeneInfo());
        if (CodisFileParser.isMix(sampleGene.getGeneInfo())) {
            sampleGene.setGeneType(Constants.GENE_TYPE_MIX);
        }else if (CodisFileParser.isYstr(sampleGene.getGeneInfo())){
            sampleGene.setGeneType(Constants.GENE_TYPE_YSTR);
        }else {
            sampleGene.setGeneType(Constants.GENE_TYPE_STR);
        }
        sampleGene.setInstoredFlag(Constants.FLAG_FALSE);
        sampleGene.setDeleteFlag(Constants.FLAG_FALSE);
        sampleGene.setCreatePerson(LimsSecurityUtils.getLoginName());

        Map<String,Object> result = new HashMap<>();

        try {
            LimsSampleGene limsSampleGene = new LimsSampleGene();
            limsSampleGene.setSampleId(sampleGene.getSampleId());
            List<LimsSampleGene> sampleGeneList = limsSampleGeneService.selectListByGeneId(geneId);
            if(ListUtils.isNotNullAndEmptyList(sampleGeneList)){
                sampleGene.setId(geneId);
                sampleGene.setAuditStatus(sampleGeneList.get(0).getAuditStatus());
                sampleGene.setUpdatePerson(LimsSecurityUtils.getLoginName());
                limsSampleGeneService.update(sampleGene);
            }else {
                sampleGene.setAuditStatus(Constants.FLAG_FALSE);
                limsSampleGeneService.insert(sampleGene);
            }
            result.put("geneId",sampleGene.getId());
            result.put("success",true);
        }catch (Exception e){
            logger.error("saveGeneInfo error!",e);
            result.put("error",false);
        }

        return result;
    }

    private ModelAndView initDictList(ModelAndView modelAndView){
        /*  字典 */
        List<DictItem> caseStatusList = new ArrayList<>();
        caseStatusList = dictService.selectDictItemListByType(Constants.DICT_TYPE_CASE_STATUS);
        List<DictItem> caseTypeList = dictService.selectDictItemListByType(Constants.DICT_TPYE_CASE_TYPE);
        List<DictItem> casePropertyList = dictService.selectDictItemListByType(Constants.DICT_TPYE_CASE_PROPERTY);
        List<DictItem> caseLevelList = dictService.selectDictItemListByType(Constants.DICT_TPYE_CASE_LEVEL);
        List<DictItem> personTypeList = dictService.selectDictItemListByType(Constants.DICT_TPYE_PERSON_TYPE);
        List<DictItem> personRelationList = dictService.selectDictItemListByType(Constants.DICT_TPYE_PERSON_RELATION);
        List<DictItem> sampleTypeList = dictService.selectDictItemListByType(Constants.DICT_TPYE_SAMPLE_TYPE);
        List<DictItem> personGenderList = dictService.selectDictItemListByType(Constants.DICT_TYPE_PERSON_GENDER);
        /*  委托单位 */
        List<DelegateOrg> delegateOrgList = delegateOrgService.selectAll();
        List<DictItem> samplePropertiesList = dictService.selectDictItemListByType(Constants.DICT_TYPE_SAMPLE_PROPERTIES);

        modelAndView.addObject("samplePropertiesList", samplePropertiesList);
        modelAndView.addObject("delegateOrgList", delegateOrgList);
        modelAndView.addObject("caseStatusList", caseStatusList);
        modelAndView.addObject("caseTypeList", caseTypeList);
        modelAndView.addObject("casePropertyList", casePropertyList);
        modelAndView.addObject("caseLevelList", caseLevelList);
        modelAndView.addObject("personTypeList", personTypeList);
        modelAndView.addObject("personRelationList", personRelationList);
        modelAndView.addObject("sampleTypeList", sampleTypeList);
        modelAndView.addObject("personGenderList", personGenderList);

        return modelAndView;
    }

    public PanelInfo getPanelInfo(PanelInfo panelInfo){

        if (panelInfo.getId() == null){
            panelInfo.setId(null);
        }else {
            panelInfo.setId(panelInfo.getId());
        }

        if(StringUtils.isBlank(panelInfo.getPanelName())){
            panelInfo.setPanelName(null);
        }else {
            panelInfo.setPanelName(panelInfo.getPanelName());
        }

        if(StringUtils.isBlank(panelInfo.getCreatePerson())){
            panelInfo.setCreatePerson(null);
        }else {
            panelInfo.setCreatePerson(panelInfo.getCreatePerson());
        }

        if(StringUtils.isBlank(panelInfo.getPanelProducer())){
            panelInfo.setPanelProducer(null);
        }else {
            panelInfo.setPanelProducer(panelInfo.getPanelProducer());
        }

        if(StringUtils.isBlank(panelInfo.getPanelVersion())){
            panelInfo.setPanelVersion(null);
        }else {
            panelInfo.setPanelVersion(panelInfo.getPanelVersion());
        }

        return panelInfo;
    }

    public LimsCaseInfoVO resetCaseInfoQuery(LimsCaseInfoVO caseInfoVO) throws ParseException {
        if (StringUtils.isBlank(caseInfoVO.getEntity().getCaseNo())){
            caseInfoVO.getEntity().setCaseNo(null);
        }else {
            caseInfoVO.getEntity().setCaseNo(caseInfoVO.getEntity().getCaseNo().trim());
        }

        if (StringUtils.isBlank(caseInfoVO.getEntity().getCaseXkNo())){
            caseInfoVO.getEntity().setCaseXkNo(null);
        }else {
            caseInfoVO.getEntity().setCaseXkNo(caseInfoVO.getEntity().getCaseXkNo().trim());
        }

        if (StringUtils.isBlank(caseInfoVO.getEntity().getEntrustmentType())){
            caseInfoVO.getEntity().setEntrustmentType(null);
        }else {
            caseInfoVO.getEntity().setEntrustmentType(caseInfoVO.getEntity().getEntrustmentType());
        }

        if (StringUtils.isBlank(caseInfoVO.getEntity().getCaseName())){
            caseInfoVO.getEntity().setCaseName(null);
        }else {
            caseInfoVO.getEntity().setCaseName(caseInfoVO.getEntity().getCaseName().trim());
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

        if (StringUtils.isBlank(caseInfoVO.getDelegateAcceptor())){
            caseInfoVO.setDelegateAcceptor(null);
        }else {
            caseInfoVO.setDelegateAcceptor(caseInfoVO.getDelegateAcceptor());
        }

        if (caseInfoVO.getAcceptDateStart() == null){
            caseInfoVO.setAcceptDateStart(null);
        }else {
            caseInfoVO.setAcceptDateStart(caseInfoVO.getAcceptDateStart());
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String oldTime = null;
        Date newTime = null;

        if (caseInfoVO.getAcceptDateEnd() == null){
            caseInfoVO.setAcceptDateEnd(null);
        }else {
            oldTime = sdf.format(caseInfoVO.getAcceptDateEnd());
            newTime =sd.parse(oldTime + " 23:59:59");
            caseInfoVO.setAcceptDateEnd(newTime);
        }

        return caseInfoVO;
    }

    @RequestMapping("/entryStatus.html")
    public Map<String,Object> entryStatus(HttpServletRequest request, Integer caseId) {
        List<LimsSampleInfo> limsSampleInfos = limsSampleInfoService.selectListByCaseId(caseId);
        Map<String,Object> result = new HashMap<>();
        for (LimsSampleInfo limsSampleInfo:limsSampleInfos) {
            if(limsSampleInfo.getEnterCount()=='0'){
                result.put("error",false);
            }else {
                result.put("success", true);
            }
        }

        return result;
    }

}
