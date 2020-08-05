package com.bazl.hslims.common;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2016/12/28.
 */
public class Constants {

    /**
     * 上报国家库二期(案件样本)
     */
    public static final String TO_NATION_15 = "15";
    /**
     * 上报国家库二期(建库人员)
     */
    public static final String TO_NATION_16 = "16";

    public static boolean SYNC_DNA36_TASK_STARTED = false;

    public static final String LOGIN_TYPE_DELEGATE = "delegate";        //送检用户
    public static final String LOGIN_TYPE_CENTER = "center";            //中心用户

    public static final String LOGIN_QXJ_ORG_CODE = "522401"; //七星关
    public static final String LOGIN_JHH_ORG_CODE = "522402"; //金海湖
    public static final String LOGIN_DF_ORG_CODE = "522422"; //大方
    public static final String LOGIN_QX_ORG_CODE = "522423"; //黔西
    public static final String LOGIN_JS_ORG_CODE = "522424"; //金沙
    public static final String LOGIN_ZJ_ORG_CODE = "522425"; //织金
    public static final String LOGIN_HZ_ORG_CODE = "522428"; //赫章
    public static final String LOGIN_WN_ORG_CODE = "522427"; //威宁
    public static final String LOGIN_BLDJ_ORG_CODE = "522429"; //百里杜鹃

    public static final String SAMPLE_FLAG_EVIDENCE = "0";
    public static final String SAMPLE_FLAG_PERSON = "1";

    public static final String PERSON_TYPE_BEIHAIREN = "01";
    public static final String PERSON_TYPE_XIANYIREN = "03";
    public static final String PERSON_TYPE_QITARENYUAN = "06";
    public static final String PERSON_TYPE_BEIHAIREN_RELATIVE = "02";       //被害人亲属

    public static final String CASE_INFO_STATUS_PENDING = "01";      //未受理
    public static final String CASE_INFO_STATUS_ACCEPTED = "02";     //在检验
    public static final String CASE_INFO_STATUS_FINISHED = "03";     //已完成
    public static final String CASE_INFO_STATUS_REFUSED = "04";     //退案

    //案件已委托
    public static final String CASE_INFO_COMMISSIONED = "20";
    //案件已受理
    public static final String CASE_INFO_ACCEPTED = "21";
    //物证已受理
    public static final String CASE_INFO_IDENTIFIED = "22";
    //物证已检出
    public static final String CASE_INFO_DETECTION = "24";
    //身份不明人员
    public static final String CASE_INFORMATION_UNIDENTFIED = "01";
    //失踪人口
    public static final String CASE_INFORMATION_BE_LOST = "02";

    public static final String IDENTIFY_BOOK_PENDING = "01";        //未出
    public static final String IDENTIFY_BOOK_PENDING_NAME = "未出";        //未出
    public static final String IDENTIFY_BOOK_NEED_SIGN = "02";      //已出未签发
    public static final String IDENTIFY_BOOK_NEED_SIGN_NAME = "已出，未签发";      //已出未签发
    public static final String IDENTIFY_BOOK_SIGNED = "03";         //签发
    public static final String IDENTIFY_BOOK_SIGNED_NAME = "已签发，未领取";         //签发
    public static final String IDENTIFY_BOOK_FETCHED = "04";        //已领取
    public static final String IDENTIFY_BOOK_FETCHED_NAME = "已领取";        //已领取


    public static final String SAMPLE_ACCEPT_STATUS_PENDING = "0";      //未受理
    public static final String SAMPLE_ACCEPT_STATUS_ACCEPTED = "1";     //已受理
    public static final String SAMPLE_ACCEPT_STATUS_REFUSED = "2";     //拒绝受理

    public static final String FLAG_TRUE = "1";
    public static final String FLAG_FALSE= "0";
    public static final String FLAG_TWO= "2";

    public static final String CRIMINAL_PERSON_STATUS_PENDING = "0";      //未送检
    public static final String CRIMINAL_PERSON_STATUS_ACCEPTED = "1";     //不受理
    public static final String CRIMINAL_PERSON_STATUS_REFUSED = "2";     //已受理


    public static final String CASE_NO_RULE = "DNA";
    public static final String CONSIGNMENT_NO_RULE = "WT";
    public static final int CASE_NO_LENGTH = 4;
    public static final int CONSIGNMENT_NO_LENGTH = 4;
    public static final int PERSON_NO_LENGTH = 3;
    public static final int SAMPLE_NO_LENGTH = 3;
    public static final String PERSON_NO_PREFIX = "Y";
    public static final String SAMPLE_NO_PREFIX = "J";

    public static final String TQ_TASK_PREFIX = "TQ";
    public static final String PCR_TASK_PREFIX = "KZ";
    public static final String SY_TASK_PREFIX = "SY";


    public static final String OPERATE_TYPE_ADD = "1";
    public static final String OPERATE_TYPE_EDIT = "2";
    public static final String OPERATE_TYPE_DEL = "3";

    public static final String GENE_TYPE_STR = "1";         //STR
    public static final String GENE_TYPE_MIX = "2";         //MIX
    public static final String GENE_TYPE_YSTR = "3";         //YSTR
    public static final String GENE_YSTR = "DYS";         //YSTR

    public static final int SAME_MATCH_LIMIT_DEFAULT = 15;      //同型匹配下限
    public static final int SAME_RELATION_MATCH_LIMIT_DEFAULT = 15;   //亲缘匹配下限
    public static final int YSTR_MATCH_LIMIT_DEFAULT = 25;      //YSTR匹配下限
    public static final int SAME_RELATION_HALF_COUNT = 0; //亲缘半容差

    public static final String QUEUE_STATUS_UPLOAD_FAILED = "-2";
    public static final String QUEUE_STATUS_GENERATED_FAILED = "-1";
    public static final String QUEUE_STATUS_PENDING = "0";
    public static final String QUEUE_STATUS_GENERATE_SUCCESS = "1";
    public static final String QUEUE_STATUS_UPLOAD_SUCCESS = "2";

    //入库类型
    public static final String SAMPLE_ENTRY_TYPE = "SAMPLE_ENTRY_TYPE";

    /*  DICT */
    public static final String DICT_TPYE_CASE_TYPE = "CASE_TYPE";
    public static final String DICT_TPYE_PERSON_RACE = "PERSON_RACE"; //名族
    public static final String DICT_TPYE_CASE_PROPERTY = "CASE_PROPERTY";
    public static final String DICT_TPYE_CASE_LEVEL = "CASE_LEVEL";
    public static final String DICT_TPYE_IDENTIFY_REQUIREMENT = "IDENTIFY_REQUIREMENT";
    public static final String DICT_TPYE_PERSON_TYPE = "PERSON_TYPE";
    public static final String DICT_TPYE_PERSON_RELATION = "PERSON_RELATION";
    public static final String DICT_TPYE_SAMPLE_TYPE = "SAMPLE_TYPE";
    public static final String DICT_TYPE_PERSON_GENDER = "PERSON_GENDER";
    public static final String DICT_TYPE_CASE_STATUS = "CASE_STATUS";
    public static final String DICT_TYPE_DUTY = "DUTY";   //职务
    public static final String DICT_TYPE_CERTIFICATE_TYPE = "CERTIFICATE_TYPE";     //证件类型
    public static final String DICT_TYPE_EXTRACT_METHOD = "EXTRACT_METHOD";         //提取方法
    public static final String DICT_TYPE_PCR_PROGRAM_NO = "PCR_PROGRAM_NO";         //扩增程序号
    public static final String DICT_TYPE_PCR_REAGENT_KIT = "PCR_REAGENT_KIT";         //扩增试剂盒
    public static final String DICT_TYPE_PCR_SYSTEM = "PCR_SYSTEM";         //扩增体系
    public static final String DICT_TYPE_PCR_INSTRUMENT = "PCR_INSTRUMENT";         //扩增仪
    public static final String DICT_TYPE_SAMPLE_PROPERTIES = "SAMPLE_PROPERTIES";   //样本性状
    public static final String DICT_TYPE_CODIES_FILE_PATH = "CODIES_FILE_PATH";
    public static final String DICT_TYPE_CODIES_READ_FILE_PATH = "CODIES_READ_FILE_PATH";
    public static final String DICT_TYPE_SERVER_PATH = "SERVER_PATH";
    public static final String DICT_TYPE_IDENTIFY_BOOK_PATH = "IDENTIFY_BOOK_FILE_PATH";
    public static final String DICT_TYPE_QUALITY_SAME_LIMIT = "QUALITY_SAME_LIMIT"; //污染比对匹配数
    public static final String DICT_TYPE_QUALITY_DIFF_LIMIT = "QUALITY_DIFF_LIMIT"; //污染比对容差数

    public static final String DICT_TYPE_CHANWU_UL = "CHANWU_UL";       //产物ul
    public static final String DICT_TYPE_JIAXIANAN_UL = "JIAXIANAN_UL";       //甲酰胺ul
    public static final String DICT_TYPE_NEIBIAO_UL = "NEIBIAO_UL";       //内标ul
    public static final String DICT_TYPE_NEIBIAO = "NEIBIAO";       //内标
    public static final String DICT_TYPE_ELEC_INSTRUMENT = "ELEC_INSTRUMENT";       //电泳仪
    public static final String DICT_TYPE_EQUIPMENT_STATUS = "EQUIPMENT_STATUS";     //设备状态
    public static final String DICT_TYPE_EXPERIMENTAL_STAGE = "EXPERIMENTAL_STAGE";   //实验阶段

    public static final String[] SY_STANDARD_SAMPLE_ARR = new String[]{"9947A","YIN","LAD"};
    public static final List<String> SY_STANDARD_SAMPLE_LIST = Arrays.asList("9947A","YIN","LAD");

    public static final String[] PCR_STANDARD_SAMPLE_ARR = new String[]{"9947","Water"};
    public static final List<String> PCR_STANDARD_SAMPLE_LIST = Arrays.asList("9947","Water");


    public static final String PANEL_NAME_IDENTIFILER = "Identifiler";


    public static final String DICT_ITEM_OTHER_CODE = "9999";

    public static final String DICT_ITEM_SAMPLE_TYPE_BLOOD = "01";              //血痕/血液斑
    public static final String DICT_ITEM_SAMPLE_TYPE_SEMINAL = "02";            //精斑
    public static final String DICT_ITEM_SAMPLE_TYPE_HAIR = "03";               //毛发
    public static final String DICT_ITEM_SAMPLE_TYPE_NAIL = "04";               //指甲
    public static final String DICT_ITEM_SAMPLE_TYPE_BUTT = "05";               //烟蒂
    public static final String DICT_ITEM_SAMPLE_TYPE_SALIVA = "06";              //唾液（斑）
    public static final String DICT_ITEM_SAMPLE_TYPE_BONES_TEETH = "07";         //骨骼/牙齿
    public static final String DICT_ITEM_SAMPLE_TYPE_MUSCLE = "08";              //肌肉（组织）
    public static final String DICT_ITEM_SAMPLE_TYPE_TISSUE = "09";              //组织
    public static final String DICT_ITEM_SAMPLE_TYPE_FINGERPRINT = "10";              //指纹
    public static final String DICT_ITEM_SAMPLE_TYPE_CASTOFF_CELLS = "11";              //脱落细胞
    public static final String DICT_ITEM_SAMPLE_TYPE_OTHERS = "99";              //其他
    public static final String DICT_ITEM_SAMPLE_L = "+";//阳性
    public static final String DICT_ITEM_SAMPLE_N = "-";//阴性
    //提取方法
    public static final String DICT_ITEM_EXTRACT_METHOD_A_BLOOD = "A";              //血斑类
    public static final String DICT_ITEM_EXTRACT_METHOD_B_BONE_TEETH = "B";        //骨骼、牙齿类 硅胶膜吸附法
    public static final String DICT_ITEM_EXTRACT_METHOD_C_AUTOMATIC = "C";         //骨骼、牙齿类 全自动工作站法
    public static final String DICT_ITEM_EXTRACT_METHOD_D_CELLS = "D";              //口腔脱落上皮细胞类/  脱落细胞类
    public static final String DICT_ITEM_EXTRACT_METHOD_E_SEMINAL = "E";            //精斑类

    public static final String EQUIPMENT_TYPE_INFO_KZY = "KZY";//扩增仪
    public static final String EQUIPMENT_TYPE_INFO_TQ = "TQ";//提取

    public static final String EQUIPMENT_TYPE_INFO_LXJ = "LXJ";//离心机
    public static final String EQUIPMENT_TYPE_INFO_ZTQ = "ZTQ";//振荡器
    public static final String EQUIPMENT_TYPE_INFO_GZZ = "GZZ";//工作站
    public static final String EQUIPMENT_TYPE_INFO_JSY = "JSY";//金属浴

    public static final String EQUIPMENT_TYPE_PCR_PROGRAM_NO = "PCR_PROGRAM_NO";     //超净台
    public static final String EQUIPMENT_TYPE_INFO_DYY = "DYY";            //电泳仪

    //实验人员
    public static final String  EXPERIMENTER_1 = "zsj";            //祝世敬
    public static final String  EXPERIMENTER_2 = "myc";            //毛艳春


    public static final String COMPARE_PARAM_KEY_MIN_SAME_COUNT = "minSameCount";
    public static final String COMPARE_PARAM_KEY_MAX_DIFF_COUNT = "maxDiffCount";
    //同型匹配
    public static final String MATCH_TYPE_SAME = "01";
    //亲缘匹配 （子女）
    public static final String MATCH_TYPE_RELATIVE_ZN = "02";
    //亲缘匹配 （配偶）
    public static final String MATCH_TYPE_RELATIVE_PO = "03";
    //亲缘匹配 （配偶-子女）
    public static final String MATCH_TYPE_RELATIVE_POZN = "04";
    //亲缘匹配 （父母）
    public static final String MATCH_TYPE_RELATIVE_FM = "05";

    public static final String[] SYTABLE_POSTION_ARR = new String[]{
            "A01","A02","A03","A04","A05","A06","A07","A08","A09","A10","A11","A12",
            "B01","B02","B03","B04","B05","B06","B07","B08","B09","B10","B11","B12",
            "C01","C02","C03","C04","C05","C06","C07","C08","C09","C10","C11","C12",
            "D01","D02","D03","D04","D05","D06","D07","D08","D09","D10","D11","D12",
            "E01","E02","E03","E04","E05","E06","E07","E08","E09","E10","E11","E12",
            "F01","F02","F03","F04","F05","F06","F07","F08","F09","F10","F11","F12",
            "G01","G02","G03","G04","G05","G06","G07","G08","G09","G10","G11","G12",
            "H01","H02","H03","H04","H05","H06","H07","H08","H09","H10","H11","H12"
    };


    public static final String[] SYTABLE_POSTION_ARR_VER = new String[]{
            "A01","B01","C01","D01","E01","F01","G01","H01",
            "A02","B02","C02","D02","E02","F02","G02","H02",
            "A03","B03","C03","D03","E03","F03","G03","H03",
            "A04","B04","C04","D04","E04","F04","G04","H04",
            "A05","B05","C05","D05","E05","F05","G05","H05",
            "A06","B06","C06","D06","E06","F06","G06","H06",
            "A07","B07","C07","D07","E07","F07","G07","H07",
            "A08","B08","C08","D08","E08","F08","G08","H08",
            "A09","B09","C09","D09","E09","F09","G09","H09",
            "A10","B10","C10","D10","E10","F10","G10","H10",
            "A11","B11","C11","D11","E11","F11","G11","H11",
            "A12","B12","C12","D12","E12","F12","G12","H12"
    };

    public static final String GENDER_MALE_CODE = "01";
    public static final String GENDER_FEMALE_CODE = "02";
    public static final String GENDER_OTHERS_CODE = "03";


    public static final String GENDER_MALE_NAME = "男";
    public static final String GENDER_FEMALE_NAME = "女";
    public static final String GENDER_OTHERS_NAME = "其他";

    public static final List<String> GENE_LIST1 = Arrays.asList("D8S1179","D21S11","D7S820","CSF1PO","D3S1358","TH01","D13S317","D16S539");
    public static final List<String> GENE_LIST2 = Arrays.asList("D2S1338","D19S433","vWA","TPOX","D18S51","Amel","D5S818","FGA");

    public static final List<String> YGENE_LIST1 = Arrays.asList("DYS576","DYS389I","DYS635","DYS389II","DYS627","DYS460","DYS458","DYS19","GATA_H4");
    public static final List<String> YGENE_LIST2 = Arrays.asList("DYS448","DYS391","DYS456","DYS390","DYS438","DYS392","DYS518","DYS570","DYS437");
    public static final List<String> YGENE_LIST3 = Arrays.asList("DYS385","DYS449","DYS393","DYS439","DYS481","DYF387S1","DYS533");

    public static final List<String> HENE_LIST1 = Arrays.asList("D3S1358","vWA","D16S539","CSF1PO","TPOX","AMEL","D8S1179","D21S11");
    public static final List<String> HENE_LIST2 = Arrays.asList("D18S51","Penta E","D2S441","D19S433","TH01","FGA","D22S1045","D5S818");
    public static final List<String> HENE_LIST3 = Arrays.asList("D13S317","D7S820","D6S1043","D10S1248","D1S1656","D12S391","D2S1338","Penta D");

    public static final List<String> IENE_LIST1 = Arrays.asList("AMEL","TH01","D3S1358","vWA","D21S11","TPOX","DYS391","D1S1656");
    public static final List<String> IENE_LIST2 = Arrays.asList("D12S391","SE33","D10S1248","D22S1045","D19S433","D8S1179","D2S1338","D2S441");
    public static final List<String> IENE_LIST3 = Arrays.asList("D18S51","FGA","D16S539","CSF1PO","D13S317","D5S818","D7S820");

    public static final String REGENT_NAME_1 = "Identifiler Plus";
    public static final String REGENT_NAME_2 = "GlobalFiler";
    public static final String REGENT_NAME_3 = "YFILER PLUS";
    public static final String REGENT_NAME_4 = "TYPER 19";
    public static final String REGENT_NAME_5 = "华夏白金";
    public static final String REGENT_NAME_6 = "Investigator 24plex";

    public static final List<String> IdentifilerPlusList = Arrays.asList("D8S1179","D21S11","D7S820","CSF1PO","D3S1358","TH01","D13S317","D16S539","D2S1338","D19S433","vWA","TPOX","D18S51","Amel","D5S818","FGA");
    public static final List<String> YfilerPlusList = Arrays.asList("DYS576","DYS389I","DYS635","DYS389II","DYS627","DYS460","DYS458","DYS19","GATA_H4",
            "DYS448","DYS391","DYS456","DYS390","DYS438","DYS392","DYS518","DYS570","DYS437",
            "DYS385","DYS449","DYS393","DYS439","DYS481","DYF387S1","DYS533");

    public static final String PANELS = "D8S1179、D21S11、D7S820、CSF1PO、D3S1358、TH01、D13S317、D16S539、D2S1338、D19S433、vWA、TPOX、D18S51、D5S818、FGA";
    public static final String IPPANELS = "TH01、D3S1358、vWA、D21S11、TPOX、D1S1656、D12S391、SE33、D10S1248、D22S1045、D19S433、D8S1179、D2S1338、D2S441、D18S51、FGA、D16S539、CSF1PO、D13S317、D5S818、D7S820";

    public static final List<String> typer19List = Arrays.asList("Amel","D5S818","D21S11","D7S820","CSF1PO","D2S1338","D3S1358","vWA","D8S1179",
            "D16S539","PentaE","TPOX","TH01","D19S433","D18S51","FGA","D6S1043","D13S317","D12S391");

    public static final List<String> globalFilerList = Arrays.asList("D3S1358","vWA","D16S539","CSF1PO","TPOX","Yindel","AMEL","D8S1179","D21S11",
            "D18S51","DYS391","D2S441","D19S433","THO1","FGA","D22S1045","D5S818","D13S317",
            "D7S820","SE33","D10S1248","D1S1656","D12S391","D2S1338");

    public static final List<String> hxPlatinumList = Arrays.asList("D3S1358","vWA","D16S539","CSF1PO","TPOX","AMEL","D8S1179","D21S11",
            "D18S51","Penta E","D2S441","D19S433","TH01","FGA","D22S1045","D5S818",
            "D13S317","D7S820","D6S1043","D10S1248","D1S1656","D12S391","D2S1338","Penta D");

    public static final List<String> IPlatinumList = Arrays.asList("AMEL","TH01","D3S1358","vWA","D21S11","TPOX","DYS391","D1S1656",
            "D12S391","SE33","D10S1248","D22S1045","D19S433","D8S1179","D2S1338","D2S441",
            "D18S51","FGA","D16S539","CSF1PO","D13S317","D5S818","D7S820");

}
