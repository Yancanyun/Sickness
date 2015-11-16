package com.emenu.test.other;

import com.emenu.common.exception.EmenuException;

/**
 * ExceptionCodeUtil
 *
 * @author: zhangteng
 * @time: 2015/11/16 11:12
 **/
public class ExceptionCodeUtil {

    public static void main(String[] args) {
        int codeStart = 1000;
        int maxCode = 0;
        for (EmenuException exception : EmenuException.values()) {
            if ((exception.getCode() - 1000) < 1000
                    && exception.getCode() > maxCode) {
                maxCode = exception.getCode();
            }
        }
        System.out.println("max code: " + maxCode);
    }
}
