package com.bazl.hslims.utils;

import java.text.NumberFormat;
import java.util.UUID;

/**
 * Created by Administrator on 2017/1/18.
 */
public class MathUtils {

    public static String GetPercentStr(int num1, int num2, int lenAfterDot){
        // 创建一个数值格式化对象
        NumberFormat numberFormat = NumberFormat.getInstance();

        // 设置精确到小数点后2位
        numberFormat.setMaximumFractionDigits(lenAfterDot);

        String result;

        if (num1 == 0 || num2 == 0) {
            result = "0";
        }else {
            result = numberFormat.format((float)num1/(float)num2*100);
        }

        return result + "%";
    }

    public static void main(String[] args) {
        System.out.println(UUID.randomUUID().toString());
    }
}
