package com.bazl.hslims.manager.model.relativeMatch;

import com.bazl.hslims.common.Constants;
import com.bazl.hslims.manager.model.GeneInfoModel;
import com.bazl.hslims.manager.model.domain.CaseParentageMatchResult;
import com.bazl.hslims.manager.model.domain.TotalRelativeMatchResult;
import com.bazl.hslims.manager.model.factory.ParentageCalculatorFactory;
import com.bazl.hslims.manager.model.po.LimsSampleGene;
import com.bazl.hslims.manager.model.po.RelationCompare;
import com.bazl.hslims.manager.model.relative.ParentageCalculator;
import com.bazl.hslims.manager.model.relative.impl.EssenMollerParentageCalculator;
import com.bazl.hslims.manager.model.relative.types.Marker;
import com.bazl.hslims.manager.model.relative.types.ParentageMatchOptions;
import com.bazl.hslims.manager.model.relative.types.ParentageMatchResult;
import com.bazl.hslims.manager.model.relative.types.ParentageMatchResultRecord;
import com.bazl.hslims.manager.model.submitMatchDefaultSetting.WS_CaseParentageMatchResult;
import com.bazl.hslims.manager.model.submitMatchDefaultSetting.WS_SingleMarkerRate;
import com.bazl.hslims.manager.model.submitMatchDefaultSetting.WS_TotalRelativeMatchResult;
import com.bazl.hslims.utils.DataFormat;
import com.bazl.hslims.utils.ListUtils;
import com.bazl.hslims.utils.SystemUtil;
import com.bazl.hslims.web.helper.codis.CodisGeneInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/6.
 */
public class RelationCaseMatch {

    public TotalRelativeMatchResult relationCaseMatchSampleList(LimsSampleGene FsampleGene, LimsSampleGene MsampleGene,
                                                                LimsSampleGene ZsampleGene, RelationCompare relationCompare, int piLength) {


        WS_TotalRelativeMatchResult wsResult = parentageMatch(FsampleGene, MsampleGene, ZsampleGene, relationCompare);

        TotalRelativeMatchResult result = libMatchResult_forward(wsResult,piLength);

        return result;
    }

    public TotalRelativeMatchResult libMatchResult_forward(WS_TotalRelativeMatchResult wsResult,int piLength) {

        TotalRelativeMatchResult totalRelativeMatchResult = new TotalRelativeMatchResult();

        if (wsResult != null) {
            CaseParentageMatchResult result = caseParentageMatchResult_forward(wsResult.getResult(),piLength);
            CaseParentageMatchResult fResult = caseParentageMatchResult_forward(wsResult.getFResult(),piLength);
            CaseParentageMatchResult mResult = caseParentageMatchResult_forward(wsResult.getMResult(),piLength);
            totalRelativeMatchResult.setMatchMode(wsResult.getMatchMode());
            totalRelativeMatchResult.setResult(result);
            totalRelativeMatchResult.setFResult(fResult);
            totalRelativeMatchResult.setMResult(mResult);
            totalRelativeMatchResult.setFBarcode(wsResult.getFBarcode());
            totalRelativeMatchResult.setMBarcode(wsResult.getMBarcode());
            totalRelativeMatchResult.setCBarcode(wsResult.getCBarcode());
        }
        return totalRelativeMatchResult;
    }

    public CaseParentageMatchResult caseParentageMatchResult_forward(WS_CaseParentageMatchResult result,int piLength) {
        CaseParentageMatchResult matchResult = new CaseParentageMatchResult();
        if(result != null) {
            matchResult.setIsSuccessful(result.getIsSuccessful());
            matchResult.setMatchCount(result.getMatchCount());

            if(result.getTotalPossibility() != 0){
                String totalPossibility = DataFormat.formatDecimal(result.getTotalPossibility(),3,1,true);
                matchResult.setTotalPossibility(totalPossibility);
            }
            WS_SingleMarkerRate[] ws_singleMarkerRates = result.singleMarkers;
            Map<String,Double> markerRates = new HashMap<String,Double>();
            for(WS_SingleMarkerRate wsSingleMarkerRate : ws_singleMarkerRates) {
                markerRates.put(wsSingleMarkerRate.getMarkerName(),wsSingleMarkerRate.getMarkerRate());
            }
            matchResult.setMarkerRates(markerRates);
        }
        return matchResult;
    }

    public WS_TotalRelativeMatchResult parentageMatch(LimsSampleGene FsampleGene, LimsSampleGene MsampleGene,
                                                      LimsSampleGene ZsampleGene, RelationCompare relationCompare) {

        WS_TotalRelativeMatchResult totalResult = new WS_TotalRelativeMatchResult();

        ObjectMapper jsonObjectMapper = new ObjectMapper();

        List<CodisGeneInfo> ZgeneInfoList = null;
        List<CodisGeneInfo> FgeneInfoList = null;
        List<CodisGeneInfo> MgeneInfoList = null;

        try {
            if (ZsampleGene !=null && StringUtils.isNotBlank(ZsampleGene.getGeneInfo())) {
                ZgeneInfoList = jsonObjectMapper.readValue(ZsampleGene.getGeneInfo(), jsonObjectMapper.getTypeFactory().constructCollectionType(List.class, CodisGeneInfo.class));

                if (ZsampleGene != null && ListUtils.isNullOrEmptyList(ZgeneInfoList)) {
                    totalResult.matchMode = -1;
                    return totalResult;
                }
            }

            if (FsampleGene !=null && StringUtils.isNotBlank(FsampleGene.getGeneInfo())) {
                FgeneInfoList = jsonObjectMapper.readValue(FsampleGene.getGeneInfo(), jsonObjectMapper.getTypeFactory().constructCollectionType(List.class, CodisGeneInfo.class));

                if (FsampleGene != null && ListUtils.isNullOrEmptyList(FgeneInfoList)) {
                    totalResult.matchMode = -2;
                    return totalResult;
                }
            }

            if (MsampleGene !=null && StringUtils.isNotBlank(MsampleGene.getGeneInfo())) {
                MgeneInfoList = jsonObjectMapper.readValue(MsampleGene.getGeneInfo(), jsonObjectMapper.getTypeFactory().constructCollectionType(List.class, CodisGeneInfo.class));

                if (MsampleGene !=null && ListUtils.isNullOrEmptyList(MgeneInfoList)) {
                    totalResult.matchMode = -3;
                    return totalResult;
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

        totalResult = caculateFamily(FgeneInfoList, MgeneInfoList, ZgeneInfoList, relationCompare);

        return totalResult;
    }


    public WS_TotalRelativeMatchResult caculateFamily(List<CodisGeneInfo> FgeneInfoList, List<CodisGeneInfo> MgeneInfoList,
                                                             List<CodisGeneInfo> ZgeneInfoLis, RelationCompare relationCompare) {

        if (null == relationCompare) {
            relationCompare = new RelationCompare();
            relationCompare.setHalfDiffCount(Constants.SAME_RELATION_MATCH_LIMIT_DEFAULT);
            relationCompare.setMatchRelationLimit(Constants.SAME_RELATION_HALF_COUNT);
            String populationName = SystemUtil.getSystemConfig().getProperty("populationName");
            relationCompare.setPopulationName(populationName);
            String populationId = SystemUtil.getSystemConfig().getProperty("populationId");
            relationCompare.setPopulationId(populationId);
        }

        Map<String, Marker> af = convertGenotypeMap(FgeneInfoList);
        Map<String, Marker> m = convertGenotypeMap(MgeneInfoList);
        Map<String, Marker> c = convertGenotypeMap(ZgeneInfoLis);

        WS_TotalRelativeMatchResult totalResult = new WS_TotalRelativeMatchResult();

        ParentageMatchResult parentageMatchResult = calculateRelative(af,m,c,relationCompare);

        WS_CaseParentageMatchResult result = convertResultWs(parentageMatchResult.getResult());
        WS_CaseParentageMatchResult afResult = convertResultWs(parentageMatchResult.getResultOfAf());
        WS_CaseParentageMatchResult mResult = convertResultWs(parentageMatchResult.getResultOfM());

        if (af.size() > 0 && m.size() > 0 && c.size() > 0) {
            if (result.isSuccessful == 1 && afResult.isSuccessful == 1 && mResult.isSuccessful == 1)
                totalResult.matchMode = 1;
            else if (result.isSuccessful == 0 && afResult.isSuccessful == 0 && mResult.isSuccessful == 0)
                totalResult.matchMode = 0;
            else if (result.isSuccessful == 0 && afResult.isSuccessful == 1 && mResult.isSuccessful == 0)
                totalResult.matchMode = 2;
            else if (result.isSuccessful == 0 && afResult.isSuccessful == 0 && mResult.isSuccessful == 1)
                totalResult.matchMode = 3;
            else if (result.isSuccessful == 0 && afResult.isSuccessful == 1 && mResult.isSuccessful == 1)
                totalResult.matchMode = 4;

            totalResult.fResult = afResult;
            totalResult.mResult = mResult;
            totalResult.result = result;
        } else if (af.size() > 0 && m.size() == 0 && c.size() > 0) {
            if (afResult.isSuccessful == 1)
                totalResult.matchMode = 2;
            else
                totalResult.matchMode = 0;

            totalResult.fResult = afResult;
            totalResult.result = afResult;
        } else if (af.size() == 0 && m.size() > 0 && c.size() > 0) {
            if (mResult.isSuccessful == 1)
                totalResult.matchMode = 3;
            else
                totalResult.matchMode = 0;

            totalResult.mResult = mResult;
            totalResult.result = mResult;
        }

        return totalResult;
    }

    private WS_CaseParentageMatchResult convertResultWs(ParentageMatchResultRecord result) {
        WS_CaseParentageMatchResult wsResult = new WS_CaseParentageMatchResult();

        wsResult.setMatchCount(result.getMatchCount());
        wsResult.setIsSuccessful(result.isSuccessful() == true ? 1 : 0);
        wsResult.setTotalPossibility(result.getPi());

        WS_SingleMarkerRate[] singleMarkers = new WS_SingleMarkerRate[result.getPiOfMarker().size()];
        int index = 0;
        for (String markerName : result.getPiOfMarker().keySet()) {
            singleMarkers[index] = new WS_SingleMarkerRate();
            singleMarkers[index].markerRate = result.getPiOfMarker().get(
                    markerName);
            singleMarkers[index].markerName = markerName;
            if (result.getPiOfMarker().get(markerName).isNaN())
                singleMarkers[index].matchMode = 0;
            else
                singleMarkers[index].matchMode = 1;

            index++;
        }
        wsResult.setSingleMarkers(singleMarkers);

        return wsResult;
    }

    private ParentageMatchResult calculateRelative(Map<String,Marker> af,Map<String,Marker>  m,Map<String,Marker>  c,RelationCompare relationCompare) {

        ParentageMatchOptions option = new ParentageMatchOptions();
        option.setHalfDiffCount(relationCompare.getHalfDiffCount());
        option.setMatchThreshold(relationCompare.getMatchRelationLimit());
        option.setPopulationId(relationCompare.getPopulationId());

        ParentageCalculator calc = ParentageCalculatorFactory.getInstance().getCalculator("EssenMollerParentageCalculator");

        ParentageMatchResult parentageMatchResult = calc.calculate(af, m, c, option);

        return parentageMatchResult;
    }

    private static Map<String, Marker> convertGenotypeMap(List<CodisGeneInfo> geneInfoList) {
        Map<String, Marker> result = new Hashtable<String, Marker>();
        if (geneInfoList == null)
            return result;

        for (CodisGeneInfo codisGeneInfo : geneInfoList) {
            if(codisGeneInfo == null || codisGeneInfo.getGeneName() == null)
                continue;
            if (codisGeneInfo.getGeneName().equalsIgnoreCase("AMEL"))
                continue;
            if(StringUtils.isNotBlank(codisGeneInfo.getGeneVal1()) && StringUtils.isNotBlank(codisGeneInfo.getGeneVal2())) {
                Marker marker = new Marker();
                marker.add(codisGeneInfo.getGeneVal1());
                marker.add(codisGeneInfo.getGeneVal2());
                if (codisGeneInfo.getGeneName().equalsIgnoreCase("TH01") || codisGeneInfo.getGeneName().equalsIgnoreCase("THO1"))
                    codisGeneInfo.setGeneName("TH01");

                result.put(codisGeneInfo.getGeneName(), marker);
            }
        }

        return result;
    }

}
