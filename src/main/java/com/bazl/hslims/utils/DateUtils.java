package com.bazl.hslims.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2017/1/7.
 */
public class DateUtils {

    public static final Date getCurrentDate() {
        return new Date();
    }


    public static final String dateToString(Date date, String fmt) {
        SimpleDateFormat sdf = new SimpleDateFormat(fmt);
        return sdf.format(date);
    }

    public static final String getCurrentYearStr() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String currentDateStr = sdf.format(getCurrentDate());
        return currentDateStr.substring(0,4);
    }

    public static final String getCurrentYear() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        String currentDateStr = sdf.format(getCurrentDate());
        return currentDateStr.substring(0,4);
    }

    public static Date stringToDate(String dateStr, String fmt) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(fmt);
            return sdf.parse(dateStr);
        }catch(Exception ex){
            return null;
        }
    }
}
