package com.bazl.hslims.web.helper;

import com.bazl.hslims.common.Constants;

/**
 * Created by Administrator on 2017/3/14.
 */
public class ExamineHelper {


    public static String getExtractMethodBySampleType(String sampleType){
        if(Constants.DICT_ITEM_SAMPLE_TYPE_BLOOD.equals(sampleType)
                || Constants.DICT_ITEM_SAMPLE_TYPE_BUTT.equals(sampleType)
                || Constants.DICT_ITEM_SAMPLE_TYPE_HAIR.equals(sampleType)
                || Constants.DICT_ITEM_SAMPLE_TYPE_TISSUE.equals(sampleType)
                || Constants.DICT_ITEM_SAMPLE_TYPE_SALIVA.equals(sampleType)
                || Constants.DICT_ITEM_SAMPLE_TYPE_MUSCLE.equals(sampleType)){   //血、烟蒂、毛发、 组织、唾液、肌肉（组织）
            return Constants.DICT_ITEM_EXTRACT_METHOD_A_BLOOD;
        }else if(Constants.DICT_ITEM_SAMPLE_TYPE_SEMINAL.equals(sampleType)){           //精斑
            return Constants.DICT_ITEM_EXTRACT_METHOD_E_SEMINAL;
        }else if (Constants.DICT_ITEM_SAMPLE_TYPE_BONES_TEETH.equals(sampleType)){    //骨骼、牙齿 硅胶膜吸附法
            return Constants.DICT_ITEM_EXTRACT_METHOD_B_BONE_TEETH;
        }else{
            return Constants.DICT_ITEM_EXTRACT_METHOD_D_CELLS;  //指甲、指纹、脱落细胞
        }
    }

}
