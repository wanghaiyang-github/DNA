package com.bazl.hslims.helpers;

import com.bazl.hslims.web.helper.codis.CodisGeneInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017-08-25.
 */
public class CodisGeneInfoJsonConverter {

    public static String convertCodisGeneInfoListToJsonString(List<CodisGeneInfo> codisGeneInfoList) throws Exception {
        ObjectMapper jsonObjectMapper = new ObjectMapper();

        String geneInfo = jsonObjectMapper.writeValueAsString(codisGeneInfoList);
        return geneInfo;
    }

    public static List<CodisGeneInfo> convertJsonStringToCodisGeneInfoList(String geneInfo) throws Exception {
        ObjectMapper jsonObjectMapper = new ObjectMapper();

        List<CodisGeneInfo> srcGeneDetailList = jsonObjectMapper.readValue(geneInfo, jsonObjectMapper.getTypeFactory().constructCollectionType(List.class, CodisGeneInfo.class));
        return srcGeneDetailList;
    }

    public static List<CodisGeneInfo> convertJsonStringToCodisGeneInfoListAndDelNull(String geneInfo) throws Exception {
        ObjectMapper jsonObjectMapper = new ObjectMapper();

        List<CodisGeneInfo> srcGeneDetailList = srcGeneDetailList = jsonObjectMapper.readValue(geneInfo, jsonObjectMapper.getTypeFactory().constructCollectionType(List.class, CodisGeneInfo.class));
        List<CodisGeneInfo> resultList = new ArrayList<>();
        for(CodisGeneInfo codisGeneInfo : srcGeneDetailList){
            if(StringUtils.isNotBlank(codisGeneInfo.getGeneVal1())
                    || StringUtils.isNotBlank(codisGeneInfo.getGeneVal2())
                    || StringUtils.isNotBlank(codisGeneInfo.getGeneVal3())
                    || StringUtils.isNotBlank(codisGeneInfo.getGeneVal4())){
                resultList.add(codisGeneInfo);
            }
        }
        return resultList;
    }

}
