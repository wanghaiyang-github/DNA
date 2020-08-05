package com.bazl.hslims.utils;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by Administrator on 2017/9/10.
 */
public class PropertiesUtils {

    static Properties property = new Properties();
    public static boolean loadFile(String fileName){
        try {
            property.load(PropertiesUtils.class.getClassLoader().getResourceAsStream(fileName));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public static String getPropertyValue(String key){
        return property.getProperty(key);
    }

}
