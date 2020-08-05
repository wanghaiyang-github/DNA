package com.bazl.hslims.web.helper;

import com.bazl.hslims.manager.model.GeneInfoModel;
import com.bazl.hslims.manager.model.po.MarkerInfo;
import com.bazl.hslims.utils.ListUtils;
import com.bazl.hslims.web.helper.codis.CodisGeneInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/2/16.
 */
public class GeneInfoConverter {


    /**
     * 日志对象
     */
    protected final static Logger logger = LoggerFactory.getLogger(GeneInfoConverter.class);

    public static List<GeneInfoModel> getGeneInfoModelListByGeneInfoStr(String geneInfoStr){
        ObjectMapper jsonObjectMapper = new ObjectMapper();
        List<GeneInfoModel> geneInfoList = null;
        try {
            geneInfoList = jsonObjectMapper.readValue(geneInfoStr, jsonObjectMapper.getTypeFactory().constructCollectionType(List.class, GeneInfoModel.class));
        }catch(Exception ex){
            logger.error("通过获取geneInfoModelList错误！", ex);
            geneInfoList = new ArrayList<GeneInfoModel>();
        }

        return geneInfoList;
    }

    public static List<CodisGeneInfo> getCodisInfoList(List<String> stringList, List<CodisGeneInfo> geneInfoList) {
        List<CodisGeneInfo> codisGeneInfoList = new ArrayList<>();
        String locusName;

        for (int i = 0;i < stringList.size();i++) {
            locusName = stringList.get(i);
            if (locusName.toUpperCase().equals("THO1") || locusName.toUpperCase().equals("TH01"))
                locusName = "TH01";

            if (ListUtils.isNotNullAndEmptyList(geneInfoList)) {
                for (CodisGeneInfo cgi : geneInfoList) {
                    if (locusName.equalsIgnoreCase(cgi.getGeneName()))
                        codisGeneInfoList.add(cgi);
                }
            }
        }
        return codisGeneInfoList;
    }

    public static List<CodisGeneInfo> getCodisInfoListByGeneInfoStr(String geneInfoStr){
        ObjectMapper jsonObjectMapper = new ObjectMapper();
        List<CodisGeneInfo> codisGeneInfoList = null;
        try {
            codisGeneInfoList = jsonObjectMapper.readValue(geneInfoStr, jsonObjectMapper.getTypeFactory().constructCollectionType(List.class, CodisGeneInfo.class));
        }catch(Exception ex){
            logger.error("通过获取codisGeneInfoList错误！", ex);
            codisGeneInfoList = new ArrayList<>();
        }

        return codisGeneInfoList;
    }

    public static List<GeneInfoModel> getGeneInfoModelListByMarkerInfoList(List<MarkerInfo> markerInfoList){
        List<GeneInfoModel> geneInfoList = new ArrayList<>();

        GeneInfoModel model = null;
        for(MarkerInfo mi : markerInfoList){
            model = new GeneInfoModel();
            model.setLocusName(mi.getMarkerName());

            geneInfoList.add(model);
        }

        return geneInfoList;
    }


    public static String getJsonStrByGeneInfoModel(List<GeneInfoModel> geneInfoModelList){
        String str = "";
        try {
            ObjectMapper jsonObjectMapper = new ObjectMapper();
            str = jsonObjectMapper.writeValueAsString(geneInfoModelList);
        } catch (Exception ex) {
            logger.error("geneInfoModelList转存json格式错误！", ex);
        }

        return str;
    }

    public static String getJsonStrByCodisGeneInfoModel(List<CodisGeneInfo> codisGeneInfoList){
        String str = "";
        try {
            ObjectMapper jsonObjectMapper = new ObjectMapper();
            str = jsonObjectMapper.writeValueAsString(codisGeneInfoList);
        } catch (Exception ex) {
            logger.error("codisGeneInfoList转存json格式错误！", ex);
        }

        return str;
    }

}
