package com.emenu.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 *
 * @author: zhangteng
 * @time: 2015/10/22 14:53
 **/
public class StringUtils {

    /**
     * 对传入的字符串计算汉字的个数
     *
     * @param str
     * @return
     */
    public static int countChinese(String str) {
        int count = 0;
        String regEx = "[\\u4e00-\\u9fa5]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        while (m.find()) {
            count += (m.groupCount() + 1);
        }
        return count;
    }
}
