package com.bazl.hslims.utils;

import org.apache.shiro.crypto.hash.Md5Hash;

/**
 * Created by Administrator on 2016/12/30.
 */
public class EncryptUtils {
    public static final String encryptMD5(String source) {
        if (source == null) {
            source = "";
        }
        Md5Hash md5 = new Md5Hash(source);
        return md5.toString().toUpperCase();
    }

    public static void main(String[] args) {
        System.out.println(encryptMD5("1"));
        //"111" -> "698D51A19D8A121CE581499D7B701668"
    }

}
