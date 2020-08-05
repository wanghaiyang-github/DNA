package com.bazl.hslims.web.helper.codis;

import com.bazl.hslims.common.Constants;
import com.bazl.hslims.utils.ListUtils;
import com.bazl.hslims.web.helper.GeneInfoConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created by Administrator on 2017/1/11.
 */
public class CodisFileParser {

    public static List<CodisContentModel> getDataFromCodisFile(InputStream inputStream, String fileName) throws Exception {
        if (fileName.toUpperCase().endsWith("DAT")) {
            return getDataFromDat(inputStream);
        }

        /*if(fileName.toUpperCase().endsWith("TXT")){
            return getDataFromTxt(codisFile);
        }*/

        throw new Exception("不支持文件扩展名为：" + fileName.substring(fileName.lastIndexOf('.') + 1) + " 的CODIS文件！");
    }

    public static List<CodisContentModel> getDataCodisFile(InputStream inputStream, String fileName, String reagentName) throws Exception {
        if (fileName.toUpperCase().endsWith("DAT")) {
            return getDataDat(inputStream, reagentName);
        }

        if (fileName.toUpperCase().endsWith("TXT")) {
            return getDataFromTxt(inputStream, reagentName);
        }

        throw new Exception("不支持文件扩展名为：" + fileName.substring(fileName.lastIndexOf('.') + 1) + " 的CODIS文件！");
    }

    public static List<CodisContentModel> getDataFromDat(InputStream inputStream) throws Exception {
        List<CodisContentModel> fileStrInfoList = new ArrayList<CodisContentModel>();
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        String str = null;
        //基因座个数
        int locusCount = 0;

        while ((str = br.readLine()) != null) {

            if (StringUtils.isBlank(str)) {
                continue;
            }

            try {
                if ("DNA Analysis Result".equalsIgnoreCase(str)) {
                    int readCount = 0;
                    //峰值个数
                    int valCount = 0;

                    //2个无用信息
                    for (int i = 0; i < 2; i++) {
                        br.readLine();
                    }

                    CodisContentModel codisContentModel = new CodisContentModel();
                    List<CodisGeneInfo> locusValuesList = new ArrayList<CodisGeneInfo>();
                    CodisGeneInfo locusValues = null;
                    //样本实验室编号
                    codisContentModel.setSampleNo(br.readLine());

                    //5个无用信息
                    for (int i = 0; i < 5; i++) {
                        br.readLine();
                    }

                    locusCount = Integer.parseInt(br.readLine());
                    while (readCount < locusCount) {
                        locusValues = new CodisGeneInfo();

                        String locusName = br.readLine();
                        if (StringUtils.isBlank(locusName)) {
                            break;
                        }
                        //4个无用信息
                        for (int i = 0; i < 4; i++) {
                            br.readLine();
                        }

                        valCount = Integer.parseInt(br.readLine());

                        String alleleValue1 = "";
                        String alleleValue2 = "";
                        String alleleValue3 = "";
                        String alleleValue4 = "";

                        if (valCount == 1) {
                            alleleValue1 = br.readLine();
                            alleleValue2 = alleleValue1;
                        } else if (valCount == 2) {
                            alleleValue1 = br.readLine();
                            alleleValue2 = br.readLine();
                        } else if (valCount == 3) {
                            alleleValue1 = br.readLine();
                            alleleValue2 = br.readLine();
                            alleleValue3 = br.readLine();
                        } else if (valCount == 4) {
                            alleleValue1 = br.readLine();
                            alleleValue2 = br.readLine();
                            alleleValue3 = br.readLine();
                            alleleValue4 = br.readLine();
                        }

                        if (locusName.equalsIgnoreCase("TH01") || locusName.equalsIgnoreCase("THO1")) {
                            locusName = "TH01";
                        }

                        locusValues.setGeneName(locusName);
                        locusValues.setGeneVal1(alleleValue1);
                        locusValues.setGeneVal2(alleleValue2);
                        locusValues.setGeneVal3(alleleValue3);
                        locusValues.setGeneVal4(alleleValue4);
                        locusValuesList.add(locusValues);

                        readCount++;
                    }

                    codisContentModel.setCodisGeneInfoList(locusValuesList);
                    fileStrInfoList.add(codisContentModel);
                }
            } catch (Exception e) {
                br.close();
                throw new Exception("CODIS文件格式或基因数据有误！");
            }
        }

        br.close();

        return fileStrInfoList;
    }

    public static List<CodisContentModel> getDataDat(InputStream inputStream, String reagentName) throws Exception {
        List<CodisContentModel> fileStrInfoList = new ArrayList<CodisContentModel>();
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        String str = null;
        //基因座个数
        int locusCount = 0;

        while ((str = br.readLine()) != null) {

            if (StringUtils.isBlank(str)) {
                continue;
            }

            try {
                if ("DNA Analysis Result".equalsIgnoreCase(str)) {
                    int readCount = 0;
                    //峰值个数
                    int valCount = 0;

                    //2个无用信息
                    for (int i = 0; i < 2; i++) {
                        br.readLine();
                    }

                    CodisContentModel codisContentModel = new CodisContentModel();
                    List<CodisGeneInfo> locusValuesList = new ArrayList<CodisGeneInfo>();
                    CodisGeneInfo locusValues = null;
                    //样本实验室编号
                    codisContentModel.setSampleNo(br.readLine());

                    //5个无用信息
                    for (int i = 0; i < 5; i++) {
                        br.readLine();
                    }

                    locusCount = Integer.parseInt(br.readLine());
                    while (readCount < locusCount) {
                        locusValues = new CodisGeneInfo();

                        String locusName = br.readLine();
                        if (StringUtils.isBlank(locusName)) {
                            break;
                        }
                        //4个无用信息
                        for (int i = 0; i < 4; i++) {
                            br.readLine();
                        }

                        String alleleValue1 = "";
                        String alleleValue2 = "";
                        String alleleValue3 = "";
                        String alleleValue4 = "";

                        valCount = Integer.parseInt(br.readLine());
                        if (valCount == 1) {
                            alleleValue1 = br.readLine();
                            alleleValue2 = alleleValue1;
                        } else if (valCount == 2) {
                            alleleValue1 = br.readLine();
                            alleleValue2 = br.readLine();
                        } else if (valCount == 3) {
                            alleleValue1 = br.readLine();
                            alleleValue2 = br.readLine();
                            alleleValue3 = br.readLine();
                        } else if (valCount == 4) {
                            alleleValue1 = br.readLine();
                            alleleValue2 = br.readLine();
                            alleleValue3 = br.readLine();
                            alleleValue4 = br.readLine();
                        }

                        if (locusName.equalsIgnoreCase("TH01") || locusName.equalsIgnoreCase("THO1")) {
                            locusName = "TH01";
                        }

                        locusValues.setGeneName(locusName);
                        locusValues.setGeneVal1(alleleValue1);
                        locusValues.setGeneVal2(alleleValue2);
                        locusValues.setGeneVal3(alleleValue3);
                        locusValues.setGeneVal4(alleleValue4);
                        locusValuesList.add(locusValues);

                        readCount++;
                    }

                    List<CodisGeneInfo> codisGeneInfoList = new ArrayList<>();
                    if (StringUtils.isNotBlank(reagentName)) {
                        if (reagentName.equalsIgnoreCase(Constants.REGENT_NAME_1)) {
                            List<String> identifilerPlusList = Constants.IdentifilerPlusList;

                            codisGeneInfoList = GeneInfoConverter.getCodisInfoList(identifilerPlusList, locusValuesList);
                        }else if (reagentName.equalsIgnoreCase(Constants.REGENT_NAME_2)) {
                            List<String> globalFilerList = Constants.globalFilerList;

                            codisGeneInfoList = GeneInfoConverter.getCodisInfoList(globalFilerList, locusValuesList);
                        }else if (reagentName.equalsIgnoreCase(Constants.REGENT_NAME_3)) {
                            List<String> yfilerPlusList = Constants.YfilerPlusList;

                            codisGeneInfoList = GeneInfoConverter.getCodisInfoList(yfilerPlusList, locusValuesList);
                        }else if(reagentName.equalsIgnoreCase(Constants.REGENT_NAME_4)){
                            List<String> typer19List = Constants.typer19List;
                            codisGeneInfoList = GeneInfoConverter.getCodisInfoList(typer19List, locusValuesList);
                        }else if(reagentName.equalsIgnoreCase(Constants.REGENT_NAME_5)){
                            List<String> hxPlatinumList = Constants.hxPlatinumList;
                            codisGeneInfoList = GeneInfoConverter.getCodisInfoList(hxPlatinumList, locusValuesList);
                        }else if(reagentName.equalsIgnoreCase(Constants.REGENT_NAME_6)){
                            List<String> iPlatinumList = Constants.IPlatinumList;
                            codisGeneInfoList = GeneInfoConverter.getCodisInfoList(iPlatinumList, locusValuesList);
                        }
                    }

                    if(codisGeneInfoList.size()==0 && locusValuesList.size()>0){
                        codisContentModel.setCodisGeneInfoList(locusValuesList);
                    }else {
                        codisContentModel.setCodisGeneInfoList(codisGeneInfoList);
                    }
                    fileStrInfoList.add(codisContentModel);
                }
            } catch (Exception e) {
                br.close();
                throw new Exception("CODIS文件格式或基因数据有误！");
            }
        }

        br.close();

        return fileStrInfoList;
    }

    public static List<CodisContentModel> getDataFromTxt(InputStream inputStream, String reagentName) throws Exception {
        List<CodisContentModel> fileStrInfoList = new ArrayList();
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        String lineTxt = null;

        String sampleNo = "";
        CodisContentModel sampleInfo = new CodisContentModel();
        List<CodisGeneInfo> locusValuesList = new ArrayList<>();

        while ((lineTxt = br.readLine()) != null) {

            CodisGeneInfo locusValues = new CodisGeneInfo();

            if (StringUtils.isBlank(lineTxt)) {
                continue;
            }

            try {
                String[] arry = lineTxt.split("\t");

                if (arry[0].equalsIgnoreCase("Sample Info")) {
                    continue;
                }

                if (!arry[0].toLowerCase().equals(sampleNo) && !sampleNo.equals("")) {
                    sampleInfo.setSampleNo(sampleNo);

                    List<CodisGeneInfo> codisGeneInfoList = new ArrayList<>();
                    if (StringUtils.isNotBlank(reagentName)) {
                        if (reagentName.equalsIgnoreCase(Constants.REGENT_NAME_1)) {
                            List<String> identifilerPlusList = Constants.IdentifilerPlusList;

                            codisGeneInfoList = GeneInfoConverter.getCodisInfoList(identifilerPlusList, locusValuesList);
                        } else if (reagentName.equalsIgnoreCase(Constants.REGENT_NAME_3)) {
                            List<String> yfilerPlusList = Constants.YfilerPlusList;

                            codisGeneInfoList = GeneInfoConverter.getCodisInfoList(yfilerPlusList, locusValuesList);
                        }
                    }

                    sampleInfo.setCodisGeneInfoList(locusValuesList);

                    fileStrInfoList.add(sampleInfo);
                    locusValuesList = new ArrayList<>();
                    sampleInfo = new CodisContentModel();
                }

                sampleNo = arry[0].toLowerCase();
                locusValues.setGeneName(arry[1]);
                locusValues.setGeneVal1(arry[2]);
                if (arry.length > 4) {
                    locusValues.setGeneVal2(arry[3]);
                }
                if (arry.length > 5) {
                    locusValues.setGeneVal3(arry[4]);
                }
                if (arry.length > 6) {
                    locusValues.setGeneVal4(arry[5]);
                }
                locusValuesList.add(locusValues);

            } catch (Exception e) {
                br.close();
                throw new Exception("CODIS文件格式或基因数据有误！");
            }
        }
        br.close();

        return fileStrInfoList;
    }

    //是否是混合
    public static boolean isMix(String geneInfo) {
        boolean flag = false;

        ObjectMapper jsonObjectMapper = new ObjectMapper();

        List<CodisGeneInfo> geneDetailList = null;
        try {
            geneDetailList = jsonObjectMapper.readValue(geneInfo, jsonObjectMapper.getTypeFactory().constructCollectionType(List.class, CodisGeneInfo.class));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if (ListUtils.isNotNullAndEmptyList(geneDetailList)) {
            for (CodisGeneInfo cgi : geneDetailList) {
                if (StringUtils.isNotBlank(cgi.getGeneVal3()) || StringUtils.isNotBlank(cgi.getGeneVal4())) {
                    flag = true;
                    break;
                }
            }
        }

        return flag;
    }

    //是否是Ystr
    public static boolean isYstr(String geneInfo) {
        boolean flag = false;

        ObjectMapper jsonObjectMapper = new ObjectMapper();

        List<CodisGeneInfo> geneDetailList = null;
        try {
            geneDetailList = jsonObjectMapper.readValue(geneInfo, jsonObjectMapper.getTypeFactory().constructCollectionType(List.class, CodisGeneInfo.class));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if (ListUtils.isNotNullAndEmptyList(geneDetailList)) {
            for (CodisGeneInfo cgi : geneDetailList) {
                if (StringUtils.isNotBlank(cgi.getGeneName()) && cgi.getGeneName().toUpperCase().contains(Constants.GENE_YSTR)) {
                    flag = true;
                    break;
                }
            }
        }

        return flag;
    }

    /**
     * 校验基因座是否有效
     *
     * @param codisGeneInfoList
     * @return
     */
    public static boolean checkValidGene(List<CodisGeneInfo> codisGeneInfoList) {
        boolean flag = true;
        List<CodisGeneInfo> resultList = new ArrayList<>();
        for (CodisGeneInfo codisGeneInfo : codisGeneInfoList) {
            if (StringUtils.isNotBlank(codisGeneInfo.getGeneVal1())
                    || StringUtils.isNotBlank(codisGeneInfo.getGeneVal2())
                    || StringUtils.isNotBlank(codisGeneInfo.getGeneVal3())
                    || StringUtils.isNotBlank(codisGeneInfo.getGeneVal4())) {
                resultList.add(codisGeneInfo);
            }
        }

        if (ListUtils.isNullOrEmptyList(resultList)) {
            flag = false;
        }

        return flag;
    }

}
