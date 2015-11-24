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
public class PrintServiceImpl implements PrintService{
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

            if (printerModel == PrinterModelEnums.Is58.getId()) {

            }


            if (socket.isConnected()) {

                //由系统标准输入设备构造BufferedReader对象
                os = socket.getOutputStream();

//            打印str
                os.write(PrintUtils.printText("1223323322131123"));
//            走纸
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