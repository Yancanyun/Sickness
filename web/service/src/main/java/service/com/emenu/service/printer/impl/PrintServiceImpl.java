package com.emenu.service.printer.impl;

import com.emenu.common.entity.printer.Printer;
import com.emenu.common.enums.printer.PrinterModelEnums;
import com.emenu.common.exception.EmenuException;
import com.emenu.common.utils.PrintUtils;
import com.emenu.service.printer.PrintService;
import com.emenu.service.printer.PrinterService;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * PrintServiceImpl
 *
 * @author: yangch
 * @time: 2015/11/24 10:51
 */
@Service(value = "printService")
public class PrintServiceImpl implements PrintService {
    @Autowired
    PrinterService printerService;

    @Override
    public boolean printDishNameAndQuantityAndPrice(int printerId) throws SSException {
        Socket socket = new Socket();
        InputStream is = null;

        OutputStream os = null;
        try {
            //通过打印机ID获取IP、打印机型号
            Printer printer = printerService.queryById(printerId);
            String ipAddress = printer.getIpAddress();
            int printerModel = printer.getPrinterModel();

            // 服务器的IP和端口
            socket.connect(new InetSocketAddress(ipAddress, 9100), 1000);

            if (socket.isConnected()) {
                //由系统标准输入设备构造BufferedReader对象
                os = socket.getOutputStream();

                //初始化打印机参数，防止上次打印的参数影响本次打印
                os.write(PrintUtils.initPrinter());

                //设置为居中
                os.write(PrintUtils.setLocation(1));
                os.write(PrintUtils.printText("消费清单\n"));

                //设置为左对齐
                os.write(PrintUtils.setLocation(0));

                //若为58型号
                if (printerModel == PrinterModelEnums.Is58.getId()) {
                    os.write(PrintUtils.printText("12345678901234567890123456789012\n"));
                    for (int i = 1; i <= 20; i++){
                        os.write(PrintUtils.move(i));
                        os.write(PrintUtils.printText("1\n"));
                    }
                    //打印菜品、数量、单价
//                    os.write(PrintUtils.printText("菜品名称"));
//                    os.write(PrintUtils.move(21));
//                    os.write(PrintUtils.printText("数量"));
//                    os.write(PrintUtils.move(13));
//                    os.write(PrintUtils.printText("单价"));

                    //打两个菜试试
//                    os.write(PrintUtils.printText("\n德式香肠"));
//                    os.write(PrintUtils.move(10));
//                    os.write(PrintUtils.printText("1.0"));
//                    os.write(PrintUtils.move(3));
//                    os.write(PrintUtils.printText("88.0"));
                }

                //走纸5行
                os.write(PrintUtils.println(5));
                //切纸
                os.write(PrintUtils.cutPaper());
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    LogClerk.errLog.error(e.getMessage());
                    throw SSException.get(EmenuException.SystemException, e);
                }
            }
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    LogClerk.errLog.error(e.getMessage());
                    throw SSException.get(EmenuException.SystemException, e);
                }
            }
        }
    }
}