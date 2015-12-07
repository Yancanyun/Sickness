package com.emenu.test.other;

import java.io.BufferedInputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Stack;

/**
 * Main
 *
 * @author: zhangteng
 * @time: 2015/12/4 14:36
 **/
public class Main {

    public static void main(String[] args) {
        Scanner cin = new Scanner(new BufferedInputStream(System.in));
        while (cin.hasNext()) {
            Stack<Double> numStack = new Stack<Double>();
            Stack<String> operStack = new Stack<String>();

            String input = cin.nextLine();
            if ("0".equals(input)) {
                break;
            }
            String[] datas = input.split("\\s+");

            for (int i = 0;i < datas.length; ++i) {
                String cur = datas[i];
                if (isOper(cur)) {
                    if (!operStack.isEmpty()) {
                        String topOper = operStack.peek();
                        if (topOper.equals("*")
                                || topOper.equals("/")) {
                            Double b = numStack.pop();
                            Double a = numStack.pop();
                            numStack.push(calc(a, b, topOper));
                            operStack.pop();
                        }
                    }
                    operStack.push(cur);
                } else {
                    numStack.push(Double.valueOf(cur));
                }
            }

            String topOper = operStack.peek();
            if (topOper.equals("*")
                    || topOper.equals("/")) {
                Double b = numStack.pop();
                Double a = numStack.pop();
                numStack.push(calc(a, b, topOper));
                operStack.pop();
            }

            Iterator<Double> numIter = numStack.iterator();
            Iterator<String> operIter = operStack.iterator();
            Double result = numIter.next();
            while (operIter.hasNext()) {
                Double a = result;
                Double b = numIter.next();
                String op = operIter.next();

                result = calc(a, b, op);
            }

            System.out.printf("%.2f", result);
            System.out.println();
        }
    }

    private static Double calc(Double a, Double b, String oper) {
        if (oper.equals("+")) {
            return a + b;
        } else if (oper.equals("-")) {
            return a - b;
        } else if (oper.equals("*")) {
            return a * b;
        } else {
            return a / b;
        }

    }

    private static boolean isOper(String s) {
        return s.equals("+")
                || s.equals("-")
                || s.equals("*")
                || s.equals("/");
    }
}
