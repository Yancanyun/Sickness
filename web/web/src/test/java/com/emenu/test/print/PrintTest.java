package com.emenu.test.print;

import com.emenu.common.utils.PrintUtils;

import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * PrintTest
 *
 * @author: yangch
 * @time: 2015/11/17 18:55
 */
public class PrintTest {
    public static void main(String[] args) {

        try {
            Socket socket = new Socket();
            OutputStream os = null;
            String ip = "192.168.1.200";
            //设置打印机IP、端口
            socket.connect(new InetSocketAddress(ip, 9100), 1000);

            if (socket.isConnected()) {
                os = socket.getOutputStream();

                //初始化打印机参数，防止上次打印的参数影响本次打印
                os.write(PrintUtils.initPrinter());
//
//                //设置为居中
//                os.write(PrintUtils.setLocation(1));
//                os.write(PrintUtils.printText("居中显示测试！\n"));
//
//                //设置为右对齐
//                os.write(PrintUtils.setLocation(2));
//                os.write(PrintUtils.printText("右对齐测试！\n"));
//
//                //设置为左对齐
//                os.write(PrintUtils.setLocation(0));
//
//                //选择打印字体，第一位为宽度，第二位为高度，0为正常字体，最大值为7
//                os.write(PrintUtils.setSize(2, 2));
//                os.write(PrintUtils.printText("放大！\n"));
//
//                //恢复正常字体
//                os.write(PrintUtils.setSize(0, 0));
//
//                //58毫米一行最多16个中文字，80毫米一行最多24个中文字
//                for(int i = 1; i <= 16; i++){
//                    os.write(PrintUtils.printText("哈"));
//                }
//                os.write(PrintUtils.printText("\n"));
//
//                //58毫米一行最多32个英文字or数字，80毫米一行最多48个英文字or数字
//                for(int i = 1; i <= 16; i++){
//                    os.write(PrintUtils.printText("1"));
//                }
//                for(int i = 1; i <= 16; i++){
//                    os.write(PrintUtils.printText("H"));
//                }
//                os.write(PrintUtils.printText("\n"));
//
//                //设置条码高度，不设置则为162
//                os.write(PrintUtils.setBarCodeHeight(100));
//
//                //设置条码宽度，不设置则为3
//                os.write(PrintUtils.setBarCodeWeight(3));
//
//                //打印条码
//                os.write(PrintUtils.printBarCode("1234567"));

                os.write(PrintUtils.printText("测试"));
                os.write(0x09);
                os.write(PrintUtils.printText("测试"));
                //走纸
                os.write(PrintUtils.println(5));

                //切纸
                os.write(PrintUtils.cutPaper());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}