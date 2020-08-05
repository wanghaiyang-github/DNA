package com.bazl.hslims.web.controller.center.qc;

import com.bazl.hslims.common.Constants;
import com.bazl.hslims.manager.model.GeneInfoModel;
import com.bazl.hslims.manager.model.po.MarkerInfo;
import com.bazl.hslims.manager.model.po.Panel;
import com.bazl.hslims.manager.model.po.PanelInfo;
import com.bazl.hslims.manager.model.po.QcPersonGene;
import com.bazl.hslims.manager.services.common.MarkerInfoService;
import com.bazl.hslims.manager.services.common.PanelInfoService;
import com.bazl.hslims.manager.services.common.QcPersonGeneService;
import com.bazl.hslims.utils.ListUtils;
import com.bazl.hslims.web.controller.BaseController;
import com.bazl.hslims.web.helper.GeneInfoConverter;
import com.bazl.hslims.web.helper.codis.CodisGeneInfo;
import com.bazl.hslims.web.security.LimsSecurityUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/1/25.
 */
@Controller
@RequestMapping("/center/qc")
public class QualityControlController extends BaseController {

    @Autowired
    QcPersonGeneService qcPersonGeneService;
    @Autowired
    MarkerInfoService markerInfoService;
    @Autowired
    PanelInfoService panelInfoService;

    @RequestMapping("/1.html")
    public ModelAndView listQcPerson(HttpServletRequest request, QcPersonGene query){
        List<QcPersonGene> qcPersonGeneList = qcPersonGeneService.selectList(query);

        ModelAndView mv = new ModelAndView();
        mv.addObject("qcPersonGeneList", qcPersonGeneList);
        mv.setViewName("center/qc/listQcPerson");
        return mv;
    }

    @RequestMapping(value="/saveQcPersonGene.html", method = RequestMethod.POST, produces="application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Object> saveQcPersonGene(HttpServletRequest request, @RequestBody QcPersonGene qcPersonGene, String operateType) {

        qcPersonGene = resetQcPersonGene(qcPersonGene);

        Map<String, Object> result = new HashMap<String, Object>();
        try {
            if(Constants.OPERATE_TYPE_ADD.equals(operateType)){
                QcPersonGene query = new QcPersonGene();
                query.setQcPersonName(qcPersonGene.getQcPersonName());
                List<QcPersonGene> qcPersonGeneList = qcPersonGeneService.selectList(query);
                if (ListUtils.isNullOrEmptyList(qcPersonGeneList)) {
                    qcPersonGene.setCreatePerson(LimsSecurityUtils.getLoginName());
                    qcPersonGeneService.insert(qcPersonGene);
                    result.put("success", true);
                }else {
                    result.put("success", "repeat");
                }
            }else if(Constants.OPERATE_TYPE_EDIT.equals(operateType)){
                qcPersonGene.setUpdatePerson(LimsSecurityUtils.getLoginName());
                qcPersonGeneService.update(qcPersonGene);
                result.put("success", true);
            }
        }catch (Exception e) {
            result.put("success", false);
        }
        return result;
    }

    @RequestMapping("/editQcPerson.html")
    public ModelAndView editQcPerson(HttpServletRequest request, Integer qcPersonId, String operateType){
        QcPersonGene qcPerson = new QcPersonGene();

        PanelInfo panelInfo = new PanelInfo();
        panelInfo = getPanelInfo(panelInfo);

        List<PanelInfo> panelInfoList = panelInfoService.selectPanelInfoList(panelInfo);

        List<QcPersonGene> qcPersonGeneList = new ArrayList<>();
        if(Constants.OPERATE_TYPE_EDIT.equals(operateType)) {
            qcPerson = qcPersonGeneService.selectById(qcPersonId);

            qcPersonGeneList.add(qcPerson);
        }else {
           /* List<MarkerInfo> markerInfoList = markerInfoService.selectListByPanelName(Constants.PANEL_NAME_IDENTIFILER);
            geneInfoList = GeneInfoConverter.getGeneInfoModelListByMarkerInfoList(markerInfoList);*/

            MarkerInfo markerInfo = new MarkerInfo();
            List<MarkerInfo> markerInfoList = markerInfoService.selectMarkerInfoList(markerInfo);
            if (ListUtils.isNotNullAndEmptyList(markerInfoList)){

                StringBuffer sb= new StringBuffer("[");
                for (int i = 0;i < markerInfoList.size();i++){

                    String markerName = markerInfoList.get(i).getMarkerName();

                    if(i == markerInfoList.size() - 1){
                        sb.append("{\"locusName\":\"");
                        sb.append(markerName.trim());
                        sb.append("\",\"geneVal1\":\"");
                        sb.append("");
                        sb.append("\",\"geneVal2\":\"");
                        sb.append("");
                        sb.append("\",\"geneVal3\":\"");
                        sb.append("");
                        sb.append("\",\"geneVal4\":\"");
                        sb.append("\"");
                        sb.append("}");
                    } else {
                        sb.append("{\"locusName\":\"");
                        sb.append(markerName.trim());
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
                    qcPerson.setQcPersonStrGene(sb.toString());
                    qcPersonGeneList.add(qcPerson);
                    qcPerson.setPanelId(0);
                }
            }
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("operateType", operateType);
        modelAndView.addObject("qcPerson", qcPerson);
        modelAndView.addObject("panelInfoList",panelInfoList);
        modelAndView.addObject("qcPersonGeneList",qcPersonGeneList);
        modelAndView.setViewName("center/qc/editQcPerson");

        return modelAndView;
    }

    @RequestMapping(value="/selectQcPersonQuery.html", method = RequestMethod.POST, produces="application/json; charset=utf-8")
    @ResponseBody
    public List<Panel> selectQcPersonQuery(HttpServletRequest request, @RequestBody QcPersonGene qcPersonGene, String operateType){

        Panel panel = new Panel();
        panel.setPanelInfoId(qcPersonGene.getPanelId());
        List<Panel> panelList = panelInfoService.selectPanelList(panel);

        if (Constants.OPERATE_TYPE_ADD.equals(operateType)) {

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
                if (ListUtils.isNotNullAndEmptyList(markerInfoList)) {

                    StringBuffer sb = new StringBuffer("[");
                    String markerName = null;
                    for (int i = 0; i < markerInfoList.size(); i++) {
                        markerName = markerInfoList.get(i).getMarkerName();

                        if (i == markerInfoList.size() - 1) {
                            sb.append("{\"locusName\":\"");
                            sb.append(markerName.trim());
                            sb.append("\",\"geneVal1\":\"");
                            sb.append("");
                            sb.append("\",\"geneVal2\":\"");
                            sb.append("\"");
                            sb.append("\",\"geneVal3\":\"");
                            sb.append("");
                            sb.append("\",\"geneVal4\":\"");
                            sb.append("");
                            sb.append("}");
                        } else {
                            sb.append("{\"locusName\":\"");
                            sb.append(markerName.trim());
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
                    if (StringUtils.isNotBlank(sb.toString())) {
                        panel1.setGeneInfo(sb.toString());
                        panelList.add(panel1);
                    }
                }
            }
        }else {
            List<QcPersonGene> qcPersonGeneList = qcPersonGeneService.selectList(qcPersonGene);
            if (ListUtils.isNotNullAndEmptyList(qcPersonGeneList)) {

                String geneInfo = qcPersonGeneList.get(0).getQcPersonStrGene();
                if(StringUtils.isNotBlank(geneInfo)){
                    if (ListUtils.isNotNullAndEmptyList(panelList)) {
                        for (Panel p : panelList) {
                            p.setGeneInfo(geneInfo);
                        }
                    }
                }
            }else {
                for (int i=0;i<panelList.size();i++){
                    panelList.get(i).setGeneVal1("");
                    panelList.get(i).setGeneVal2("");
                    panelList.get(i).setGeneVal3("");
                    panelList.get(i).setGeneVal4("");
                }
            }
        }

        return panelList;
    }

    @RequestMapping("/delQcPerson.html")
    public ModelAndView del(HttpServletRequest request, Integer qcPersonId){
        qcPersonGeneService.deleteById(qcPersonId);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/center/qc/1.html");
        return modelAndView;
    }

    public QcPersonGene resetQcPersonGene(QcPersonGene qcPersonGene) {

        if (StringUtils.isBlank(qcPersonGene.getQcPersonType())) {
            qcPersonGene.setQcPersonType(null);
        }else {
            qcPersonGene.setQcPersonType(qcPersonGene.getQcPersonType().trim());
        }

        if (StringUtils.isBlank(qcPersonGene.getQcPersonNo())) {
            qcPersonGene.setQcPersonNo(null);
        }else {
            qcPersonGene.setQcPersonNo(qcPersonGene.getQcPersonNo().trim());
        }

        if (StringUtils.isBlank(qcPersonGene.getQcPersonName())) {
            qcPersonGene.setQcPersonName(null);
        }else {
            qcPersonGene.setQcPersonName(qcPersonGene.getQcPersonName().trim());
        }

        if (StringUtils.isBlank(qcPersonGene.getQcPersonGender())) {
            qcPersonGene.setQcPersonGender(null);
        }else {
            qcPersonGene.setQcPersonGender(qcPersonGene.getQcPersonGender().trim());
        }

        if (StringUtils.isBlank(qcPersonGene.getQcPersonCardId())) {
            qcPersonGene.setQcPersonCardId(null);
        }else {
            qcPersonGene.setQcPersonCardId(qcPersonGene.getQcPersonCardId().trim());
        }

        if (StringUtils.isBlank(qcPersonGene.getQcPersonPhonenum())) {
            qcPersonGene.setQcPersonPhonenum(null);
        }else {
            qcPersonGene.setQcPersonPhonenum(qcPersonGene.getQcPersonPhonenum().trim());
        }

        if (StringUtils.isBlank(qcPersonGene.getQcLabUserFlag())) {
            qcPersonGene.setQcLabUserFlag(null);
        }else {
            qcPersonGene.setQcLabUserFlag(qcPersonGene.getQcLabUserFlag().trim());
        }

        if (StringUtils.isBlank(qcPersonGene.getQcPersonOrgName())) {
            qcPersonGene.setQcPersonOrgName(null);
        }else {
            qcPersonGene.setQcPersonOrgName(qcPersonGene.getQcPersonOrgName().trim());
        }

        if (StringUtils.isBlank(qcPersonGene.getQcPersonStrGene())) {
            qcPersonGene.setQcPersonStrGene(null);
        }else {
            qcPersonGene.setQcPersonStrGene(qcPersonGene.getQcPersonStrGene().trim());
        }

        if (StringUtils.isBlank(qcPersonGene.getCreatePerson())) {
            qcPersonGene.setCreatePerson(null);
        }else {
            qcPersonGene.setCreatePerson(qcPersonGene.getCreatePerson().trim());
        }

        if (StringUtils.isBlank(qcPersonGene.getUpdatePerson())) {
            qcPersonGene.setUpdatePerson(null);
        }else {
            qcPersonGene.setUpdatePerson(qcPersonGene.getUpdatePerson().trim());
        }

        return qcPersonGene;
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
            panelInfo.setPanelName(panelInfo.getPanelName().trim());
        }

        if(StringUtils.isBlank(panelInfo.getCreatePerson())){
            panelInfo.setCreatePerson(null);
        }else {
            panelInfo.setCreatePerson(panelInfo.getCreatePerson().trim());
        }

        if(StringUtils.isBlank(panelInfo.getPanelProducer())){
            panelInfo.setPanelProducer(null);
        }else {
            panelInfo.setPanelProducer(panelInfo.getPanelProducer().trim());
        }

        if(StringUtils.isBlank(panelInfo.getPanelVersion())){
            panelInfo.setPanelVersion(null);
        }else {
            panelInfo.setPanelVersion(panelInfo.getPanelVersion().trim());
        }

        return panelInfo;
    }

}
