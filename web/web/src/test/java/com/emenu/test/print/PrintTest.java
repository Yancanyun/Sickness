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

                os.write("测试!".getBytes("GBK"));

                //选择打印字体
                os.write(0x1D);
                os.write(0x21);
                //字体大小选择，16进制数第一位为宽度，第二位为高度
                os.write(0x22);

                os.write("放大！".getBytes("GBK"));

                //走纸5行
                String enter = "";
                for (int i = 0; i < 5; i++) {
                    enter += "\n";
                }
                os.write(enter.getBytes("GBK"));

                //切纸
                byte[] cutPaper = new byte[3];
                cutPaper[0] = 0x1D;
                cutPaper[1] = 0x56;
                cutPaper[2] = 0x00;
                os.write(cutPaper);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}