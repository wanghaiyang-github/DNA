package com.bazl.hslims.web.controller.center.jk;

import com.bazl.hslims.common.Constants;
import com.bazl.hslims.manager.PageInfo;
import com.bazl.hslims.manager.model.po.DelegateOrg;
import com.bazl.hslims.manager.model.po.DictItem;
import com.bazl.hslims.manager.model.po.LimsSampleGene;
import com.bazl.hslims.manager.model.vo.CriminalPersonInfoVo;
import com.bazl.hslims.manager.services.common.CriminalPersonInfoService;
import com.bazl.hslims.manager.services.common.DelegateOrgService;
import com.bazl.hslims.manager.services.common.DictService;
import com.bazl.hslims.manager.services.common.LimsSampleGeneService;
import com.bazl.hslims.utils.ListUtils;
import com.bazl.hslims.web.controller.BaseController;
import com.bazl.hslims.web.helper.GeneInfoConverter;
import com.bazl.hslims.web.helper.codis.CodisGeneInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangliu on 2019/10/24.
 */
@Controller
@RequestMapping("/center/jk")
public class JkPersonInfoController extends BaseController {

    @Autowired
    CriminalPersonInfoService criminalPersonInfoService;
    @Autowired
    LimsSampleGeneService limsSampleGeneService;
    @Autowired
    DelegateOrgService delegateOrgService;
    @Autowired
    DictService dictService;

    /**
     * 查询建库人员基因型
     * @param request
     * @return
     */
    @RequestMapping("/queryJkGeneList.html")
    public ModelAndView queryJkGeneList(HttpServletRequest request, CriminalPersonInfoVo query, PageInfo pageInfo) {
        ModelAndView modelAndView = new ModelAndView();

        List<DelegateOrg> delegateOrgList = delegateOrgService.selectAll();
        List<DictItem> personTypeDictItemList = dictService.selectByParentDictTypeCode(Constants.DICT_TPYE_PERSON_TYPE);

        query = getPersonInfo(query);
        List<CriminalPersonInfoVo> criminalPersonInfoVoList = criminalPersonInfoService.selectGenePersonList(query, pageInfo);
        int totalCnt = criminalPersonInfoService.selectGenePersonCount(query);

        modelAndView.addObject("delegateOrgList",delegateOrgList);
        modelAndView.addObject("personTypeDictItemList",personTypeDictItemList);
        modelAndView.addObject("query",query);
        modelAndView.addObject("criminalPersonInfoVoList",criminalPersonInfoVoList);
        modelAndView.addObject("pageInfo", pageInfo.updatePageInfo(totalCnt));
        modelAndView.setViewName("center/jk/jkGeneListList");
        return modelAndView;
    }

    /**
     * 查看基因型
     * @param request
     * @param sampleGeneId
     * @return
     */
    @RequestMapping("/viewPersonGene.html")
    public ModelAndView viewPersonGene(HttpServletRequest request, Integer sampleGeneId) {

        List<LimsSampleGene> sampleGeneList = limsSampleGeneService.selectListByGeneId(sampleGeneId);
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
        modelAndView.setViewName("center/jk/viewPersonGene");
        return modelAndView;
    }


    public CriminalPersonInfoVo getPersonInfo(CriminalPersonInfoVo criminalPersonInfoVo){

        if(StringUtils.isBlank(criminalPersonInfoVo.getEntity().getPersonNo())){
            criminalPersonInfoVo.getEntity().setPersonNo(null);
        }else {
            criminalPersonInfoVo.getEntity().setPersonNo(criminalPersonInfoVo.getEntity().getPersonNo());
        }

        if(StringUtils.isBlank(criminalPersonInfoVo.getEntity().getPersonName())){
            criminalPersonInfoVo.getEntity().setPersonName(null);
        }else {
            criminalPersonInfoVo.getEntity().setPersonName(criminalPersonInfoVo.getEntity().getPersonName());
        }

        if(StringUtils.isBlank(criminalPersonInfoVo.getEntity().getIdcardNo())){
            criminalPersonInfoVo.getEntity().setIdcardNo(null);
        }else {
            criminalPersonInfoVo.getEntity().setIdcardNo(criminalPersonInfoVo.getEntity().getIdcardNo());
        }

        if(StringUtils.isBlank(criminalPersonInfoVo.getEntity().getPersonType())){
            criminalPersonInfoVo.getEntity().setPersonType(null);
        }else {
            criminalPersonInfoVo.getEntity().setPersonType(criminalPersonInfoVo.getEntity().getPersonType());
        }

        if(StringUtils.isBlank(criminalPersonInfoVo.getCollectOrgId())){
            criminalPersonInfoVo.getEntity().setCollectOrgId(0);
        }else {
            criminalPersonInfoVo.getEntity().setCollectOrgId(Integer.parseInt(criminalPersonInfoVo.getCollectOrgId()));
        }

        return criminalPersonInfoVo;
    }

}
