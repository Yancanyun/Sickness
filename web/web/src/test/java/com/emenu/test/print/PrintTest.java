package com.emenu.test.print;

import java.io.IOException;
import java.io.InputStream;
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
        Socket socket = new Socket();
        OutputStream os = null;
        String ip = "192.168.1.200";

        try {
            //设置打印机IP、端口
            socket.connect(new InetSocketAddress(ip, 9100), 1000);

            if (socket.isConnected()) {
                os = socket.getOutputStream();

                //初始化打印机参数，防止上次打印的参数影响本次打印
                os.write(0x1B);
                os.write(0x40);

                //设置显示位置
                os.write(0x1B);
                os.write(0x61);
                os.write(1);    //0-左对齐;1-居中;2-右对齐

                os.write("居中显示测试！\n".getBytes("GBK"));

                //设置显示位置
                os.write(0x1B);
                os.write(0x61);
                os.write(2);    //0-左对齐;1-居中;2-右对齐

                os.write("右对齐测试！\n".getBytes("GBK"));

                //设置显示位置
                os.write(0x1B);
                os.write(0x61);
                os.write(0);    //0-左对齐;1-居中;2-右对齐

                //选择打印字体
                os.write(0x1D);
                os.write(0x21);
                os.write(0x22);    //字体大小选择，16进制数第一位为宽度，第二位为高度

                os.write("放大！\n".getBytes("GBK"));

                //恢复正常字体
                os.write(0x1D);
                os.write(0x21);
                os.write(0x00);

                //一行最多16个中文字
                for(int i = 1; i <= 16; i++){
                    os.write("哈".getBytes("GBK"));
                }
                os.write("\n".getBytes("GBK"));

                //一行最多32个英文字or数字
                for(int i = 1; i <= 16; i++){
                    os.write("1".getBytes("GBK"));
                }
                for(int i = 1; i <= 16; i++){
                    os.write("H".getBytes("GBK"));
                }
                os.write("\n".getBytes("GBK"));

                //设置条码高度
                os.write(0x1D);
                os.write(0x68);
                os.write(162);    //条码高度数值，默认是162

                //设置条码宽度
                os.write(0x1D);
                os.write(0x77);
                os.write(3);    //条码宽度数值，默认是3

                //打印条形码测试
                os.write(0x1D);
                os.write(0x6B);
                os.write(4);    //条码类型
                os.write("1993123".getBytes("GBK"));    //宽度为3时最多7位，宽度为2时最多12位

                os.write(0x00);    //条码打印结束符


                //走纸
                os.write(0x1B);
                os.write(0x64);
                os.write(5);    //走纸行数

                //切纸
                os.write(0x1D);
                os.write(0x56);
                os.write(0);    //设置切纸格式，0-全切;1-半切
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}