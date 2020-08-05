package com.bazl.hslims.web.helper;

import com.bazl.hslims.manager.model.po.LimsConsignmentInfo;
import com.bazl.hslims.utils.ListUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/10/17.
 */
public class CosignmentInfoHelper {

    public static List<String> getDelegatorList(List<LimsConsignmentInfo> consignmentInfoList) {
        List<String> delegatorList = new ArrayList<>();

        if (ListUtils.isNotNullAndEmptyList(consignmentInfoList)) {
            for (LimsConsignmentInfo lci : consignmentInfoList) {
                delegatorList.add(lci.getDelegator());
            }
        }

        return delegatorList;
    }

    public static List<String> getDelegatorCertificateNoList(List<LimsConsignmentInfo> consignmentInfoList) {
        List<String> delegatorCertificateNoList = new ArrayList<>();

        if (ListUtils.isNotNullAndEmptyList(consignmentInfoList)) {
            for (LimsConsignmentInfo lci : consignmentInfoList) {
                delegatorCertificateNoList.add(lci.getDelegatorCertificateNo());
            }
        }

        return delegatorCertificateNoList;
    }

    public static List<String> getDelegatorPhoneList(List<LimsConsignmentInfo> consignmentInfoList) {
        List<String> delegatorPhoneList = new ArrayList<>();

        if (ListUtils.isNotNullAndEmptyList(consignmentInfoList)) {
            for (LimsConsignmentInfo lci : consignmentInfoList) {
                delegatorPhoneList.add(lci.getDelegatorPhone());
            }
        }

        return delegatorPhoneList;
    }

}
