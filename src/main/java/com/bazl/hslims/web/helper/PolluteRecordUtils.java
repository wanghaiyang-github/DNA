package com.bazl.hslims.web.helper;

import com.bazl.hslims.utils.ListUtils;
import com.bazl.hslims.web.helper.codis.CodisGeneInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/20.
 */
public class PolluteRecordUtils {

    protected final static Logger logger = LoggerFactory.getLogger(PolluteRecordUtils.class);

    public static Map compare(String geneStr1, String geneStr2){
        Map mapCount = new HashMap();

        ObjectMapper jsonObjectMapper = new ObjectMapper();

        List<CodisGeneInfo> geneDetailList1 = null;
        List<CodisGeneInfo> geneDetailList2 = null;

        try {
            geneDetailList1 = jsonObjectMapper.readValue(geneStr1, jsonObjectMapper.getTypeFactory().constructCollectionType(List.class, CodisGeneInfo.class));
            geneDetailList2 = jsonObjectMapper.readValue(geneStr2, jsonObjectMapper.getTypeFactory().constructCollectionType(List.class, CodisGeneInfo.class));
        } catch(Exception ex) {
            logger.error("解析基因分型信息错误！", ex);
        }

        int matchCount = 0;
        int diffCount = 0;
        if(ListUtils.isNullOrEmptyList(geneDetailList1) || ListUtils.isNullOrEmptyList(geneDetailList2)) {
            mapCount.put("matchCount",matchCount);
            mapCount.put("diffCount",diffCount);
            return mapCount;
        }

        String gene1Val1 = "";
        String gene1Val2 = "";

        String gene2Val1 = "";
        String gene2Val2 = "";
        for(CodisGeneInfo gene1 : geneDetailList1){
            gene1Val1 = gene1.getGeneVal1();
            gene1Val2 = gene1.getGeneVal2();

            for(CodisGeneInfo gene2 : geneDetailList2){
                if(gene2.getGeneName().toUpperCase().equals(gene1.getGeneName().toUpperCase())) {
                    gene2Val1 = gene2.getGeneVal1();
                    gene2Val2 = gene2.getGeneVal2();

                    if((gene1Val1.toUpperCase().equals(gene2Val1.toUpperCase()) && gene1Val2.toUpperCase().equals(gene2Val2.toUpperCase())
                            || (gene1Val1.toUpperCase().equals(gene2Val2.toUpperCase())) && gene1Val2.toUpperCase().equals(gene2Val1.toUpperCase()))){
                        matchCount++;
                    }else {
                        diffCount++;
                    }
                    break;
                }
            }
        }

        mapCount.put("matchCount",matchCount);
        mapCount.put("diffCount",diffCount);

        return mapCount;
    }

}
