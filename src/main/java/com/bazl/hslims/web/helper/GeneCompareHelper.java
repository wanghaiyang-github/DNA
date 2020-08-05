package com.bazl.hslims.web.helper;

import com.bazl.hslims.common.Constants;
import com.bazl.hslims.helpers.CodisGeneInfoJsonConverter;
import com.bazl.hslims.manager.services.common.AlleleFrequencyService;
import com.bazl.hslims.utils.ListUtils;
import com.bazl.hslims.web.helper.codis.CodisGeneInfo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017-08-25.
 */
public class GeneCompareHelper {
    @Autowired
    AlleleFrequencyService alleleFrequencyService;

    /**
     * 日志对象
     */
    private static final Logger logger = LoggerFactory.getLogger(GeneCompareHelper.class);

    /**
     * 同型比对
     * @param srcGeneInfo		待比对基因型
     * @param targetGeneInfo	被比样本基因型
     * @params compareParams    比对参数
     * @return
     *      key:matched     value: ture or false
     * 		key:sameCount	value:
     * 		key:diffCount	value
     */
    public static Map<String, Object> sameCompare(String srcGeneInfo, String targetGeneInfo, Map<String, Object> compareParamsMap) {

        List<CodisGeneInfo> srcGeneDetailList = null;
        try {
            srcGeneDetailList = CodisGeneInfoJsonConverter.convertJsonStringToCodisGeneInfoListAndDelNull(srcGeneInfo);
        } catch (Exception ex) {
            logger.error("解析待比对基因分型信息错误！", ex);
            return null;
        }

        List<CodisGeneInfo> targetGeneDetailList = null;
        try {
            targetGeneDetailList = CodisGeneInfoJsonConverter.convertJsonStringToCodisGeneInfoListAndDelNull(targetGeneInfo);
        } catch (Exception ex) {
            logger.error("解析比对对象基因分型信息错误！", ex);
            return null;
        }
        if(ListUtils.isNullOrEmptyList(srcGeneDetailList) || ListUtils.isNullOrEmptyList(targetGeneDetailList)){
            return null;
        }
        return compare(srcGeneDetailList, targetGeneDetailList, compareParamsMap);
    }


    /**
     * 同型比对
     * @param srcGeneDetailList		待比对基因型
     * @param targetGeneDetailList	被比样本基因型
     * @params compareParams    比对参数
     * @return
     *      key:matched     value: ture or false
     * 		key:sameCount	value:
     * 		key:diffCount	value
     */
    public static Map<String, Object> compare(List<CodisGeneInfo> srcGeneDetailList, List<CodisGeneInfo> targetGeneDetailList, Map<String, Object> compareParamsMap){
        Map<String, Object> resultMap = new HashMap<String, Object>();

        //比中数
        int sameCount = 0;
        int minSameCount = Integer.parseInt(String.valueOf(compareParamsMap.get(Constants.COMPARE_PARAM_KEY_MIN_SAME_COUNT)));
        //容差数
        int diffCount = 0;
        int maxDiffCount =  Integer.parseInt(String.valueOf(compareParamsMap.get(Constants.COMPARE_PARAM_KEY_MAX_DIFF_COUNT)));

        int srcGeneSize = srcGeneDetailList.size();
        int targetGeneSize = targetGeneDetailList.size();

        CodisGeneInfo srcCodisGeneInfo = null;
        CodisGeneInfo targetCodisGeneInfo = null;

        List<CodisGeneInfo> matchGeneInfo = new ArrayList<>();

        for(int i = 0; i < srcGeneSize; i++) {
            srcCodisGeneInfo = srcGeneDetailList.get(i);
            //待比对样本的基因座和分型信息
            String srcGeneName = srcCodisGeneInfo.getGeneName();
            String srcGeneVal1 = srcCodisGeneInfo.getGeneVal1();
            String srcGeneVal2 = srcCodisGeneInfo.getGeneVal2();

            for (int j = 0; j < targetGeneSize; j++) {
                targetCodisGeneInfo = targetGeneDetailList.get(j);
                //比对目标样本的基因座和分型信息
                String targetGeneName = targetCodisGeneInfo.getGeneName();
                String targetGeneVal1 = targetCodisGeneInfo.getGeneVal1();
                String targetGeneVal2 = targetCodisGeneInfo.getGeneVal2();

                if (srcGeneName.equalsIgnoreCase(targetGeneName)) {
                    if (StringUtils.isNotBlank(targetGeneVal1) || StringUtils.isNotBlank(targetGeneVal2)) {
                        if((targetGeneVal1.toUpperCase().equals(srcGeneVal1.toUpperCase()) && targetGeneVal2.toUpperCase().equals(srcGeneVal2.toUpperCase()))
                                || (targetGeneVal1.toUpperCase().equals(srcGeneVal2.toUpperCase()) && targetGeneVal2.toUpperCase().equals(srcGeneVal1.toUpperCase()))){
                            sameCount++;
                            matchGeneInfo.add(srcCodisGeneInfo);
                        }else{
                            diffCount++;
                        }
                    }
                break;
                }
            }
        }

        //如果容差个数超过最大容差个数，表示未比中，return
        if(diffCount > maxDiffCount || sameCount < minSameCount) {
            resultMap.put("matched", Boolean.FALSE);
            return resultMap;
        }

        resultMap.put("diffCount", diffCount);
        resultMap.put("sameCount", sameCount);
        resultMap.put("matchGeneInfo", matchGeneInfo);
        resultMap.put("matched", Boolean.TRUE);
        return resultMap;
    }

    /**
     * 亲缘比对
     * @param sourceAGeneInfo		基因型(源)
     * @param sourceBGeneInfo		基因型(源)
     * @param targetGeneInfo      基因型(目标)
     * @params compareParams       比对参数
     * @return
     *      key:matched     value: ture or false
     * 		key:sameCount	value:
     * 		key:diffCount	value
     */
    public static Map<String, Object> relativeCompare(String sourceAGeneInfo, String sourceBGeneInfo, String targetGeneInfo, Map<String, Object> compareParamsMap) {

        List<CodisGeneInfo> sourceAGeneDetailList = null;
        try {
            sourceAGeneDetailList = CodisGeneInfoJsonConverter.convertJsonStringToCodisGeneInfoListAndDelNull(sourceAGeneInfo);
        } catch (Exception ex) {
            logger.error("解析待比对基因分型信息错误！", ex);
            return null;
        }

        List<CodisGeneInfo> sourceBGeneDetailList = null;
        try {
            sourceBGeneDetailList = CodisGeneInfoJsonConverter.convertJsonStringToCodisGeneInfoListAndDelNull(sourceBGeneInfo);
        } catch (Exception ex) {
            logger.error("解析待比对基因分型信息错误！", ex);
            return null;
        }

        List<CodisGeneInfo> targetGeneDetailList = null;
        try {
            targetGeneDetailList = CodisGeneInfoJsonConverter.convertJsonStringToCodisGeneInfoListAndDelNull(targetGeneInfo);
        } catch (Exception ex) {
            logger.error("解析比对对象基因分型信息错误！", ex);
            return null;
        }
        if(ListUtils.isNullOrEmptyList(sourceAGeneDetailList) || ListUtils.isNullOrEmptyList(sourceBGeneDetailList) || ListUtils.isNullOrEmptyList(targetGeneDetailList)){
            return null;
        }
        return compare(sourceAGeneDetailList, sourceBGeneDetailList, targetGeneDetailList, compareParamsMap);
    }

    /**
     * 亲缘比对
     * @param sourceAGeneDetailList		基因型(源)
     * @param sourceBGeneDetailList		基因型(源)
     * @param targetGeneDetailList       	基因型(目标)
     * @params compareParams    比对参数
     * @return
     *      key:matched     value: ture or false
     * 		key:sameCount	value:
     * 		key:diffCount	value
     */
    public static Map<String, Object> compare(List<CodisGeneInfo> sourceAGeneDetailList, List<CodisGeneInfo> sourceBGeneDetailList, List<CodisGeneInfo> targetGeneDetailList, Map<String, Object> compareParamsMap){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        List<CodisGeneInfo> matchGeneInfo = new ArrayList<>();
        //比中数
        int sameCount = 0;
        int minSameCount = Integer.parseInt(String.valueOf(compareParamsMap.get(Constants.COMPARE_PARAM_KEY_MIN_SAME_COUNT)));
        //容差数
        int diffCount = 0;
        int maxDiffCount =  Integer.parseInt(String.valueOf(compareParamsMap.get(Constants.COMPARE_PARAM_KEY_MAX_DIFF_COUNT)));

        for(CodisGeneInfo childGeneinfo : targetGeneDetailList){
            String childGeneName = childGeneinfo.getGeneName();
            String childGeneVal1 = childGeneinfo.getGeneVal1();
            String childGeneVal2 = childGeneinfo.getGeneVal2() == null ? childGeneinfo.getGeneVal1() : childGeneinfo.getGeneVal2();

            String parentsAGeneVal = "";
            for(CodisGeneInfo parentsGeneInfo : sourceAGeneDetailList){
                if(parentsGeneInfo.getGeneName().equals(childGeneName)){
                    parentsAGeneVal = parentsGeneInfo.getGeneVal1() + "/" +parentsGeneInfo.getGeneVal2();
                    break;
                }
            }

            String parentsBGeneVal = "";
            for(CodisGeneInfo parentsGeneInfo : sourceBGeneDetailList){
                if(parentsGeneInfo.getGeneName().equals(childGeneName)){
                    parentsBGeneVal = parentsGeneInfo.getGeneVal1() + "/" + parentsGeneInfo.getGeneVal2();
                    break;
                }
            }

            if((parentsAGeneVal.contains(childGeneVal1) && parentsBGeneVal.contains(childGeneVal2))
                    || (parentsAGeneVal.contains(childGeneVal2) && parentsBGeneVal.contains(childGeneVal1))){
                sameCount++;
                matchGeneInfo.add(childGeneinfo);
            }else{
                diffCount++;
            }
        }

        //如果容差个数超过最大容差个数，表示未比中，return
        if(diffCount > maxDiffCount || sameCount < minSameCount) {
            resultMap.put("matched", Boolean.FALSE);
            return resultMap;
        }

        resultMap.put("diffCount", diffCount);
        resultMap.put("sameCount", sameCount);
        resultMap.put("matchGeneInfo", matchGeneInfo);
        resultMap.put("matched", Boolean.TRUE);
        return resultMap;
    }

    /**
     * 将codisGeneInfo的分型转换成字符串格式
     */
    private static String getStringGeneVal(CodisGeneInfo codisGeneInfo, String separator){
        String geneVal = codisGeneInfo.getGeneVal1();
        if(StringUtils.isNotBlank(codisGeneInfo.getGeneVal2())){
            geneVal += separator + codisGeneInfo.getGeneVal2();
        }
        if(StringUtils.isNotBlank(codisGeneInfo.getGeneVal3())){
            geneVal += separator + codisGeneInfo.getGeneVal3();
        }
        if(StringUtils.isNotBlank(codisGeneInfo.getGeneVal4())){
            geneVal += separator + codisGeneInfo.getGeneVal4();
        }
        return geneVal;
    }
}
