package com.enzo.commonlib.utils.common;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 文 件 名: MathUtils
 * 创 建 人: xiaofangyin
 * 创建日期: 2019-06-14
 * 邮   箱: xiaofangyin@360.cn
 */
public class MathUtils {

    /**
     * 字符串转整型
     */
    public static int parseInt(String data) {
        if (isNumeric(data)) {
            return Integer.parseInt(data);
        }
        return 0;
    }

    /**
     * 字符串转浮点型
     */
    public static float parseFloat(String data) {
        if (isDoubleOrFloat(data)) {
            return Float.parseFloat(data);
        }
        return 0;
    }

    /**
     * 保留一位小数
     */
    public static float keepADecimal(float data) {
        DecimalFormat dl = new DecimalFormat("0.0");
        return parseFloat(dl.format(data));
    }

    /**
     * 是否是整型
     */
    private static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        return isNum.matches();
    }

    /**
     * 是否为浮点数？double或float类型。
     *
     * @param str 传入的字符串。
     * @return 是浮点数返回true, 否则返回false。
     */
    private static boolean isDoubleOrFloat(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[.\\d]*$");
        return pattern.matcher(str).matches();
    }
}
