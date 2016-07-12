package com.emenu.service.order.impl;

import com.emenu.common.dto.dish.DishDto;
import com.emenu.common.dto.dish.DishPackageDto;
import com.emenu.common.dto.order.PrintOrderDishDto;
import com.emenu.common.entity.dish.DishPackage;
import com.emenu.common.entity.dish.DishTaste;
import com.emenu.common.entity.order.OrderDish;
import com.emenu.common.entity.table.Table;
import com.emenu.common.enums.order.ServeTypeEnums;
import com.emenu.common.exception.EmenuException;
import com.emenu.common.utils.CommonUtil;
import com.emenu.common.utils.DateUtils;
import com.emenu.common.utils.PrintUtils;
import com.emenu.service.cook.CookTableCacheService;
import com.emenu.service.dish.DishPackageService;
import com.emenu.service.dish.DishService;
import com.emenu.service.dish.DishTasteService;
import com.emenu.service.dish.TasteService;
import com.emenu.service.dish.tag.TagService;
import com.emenu.service.order.OrderDishPrintService;
import com.emenu.service.order.OrderDishService;
import com.emenu.service.order.OrderService;
import com.emenu.service.printer.DishTagPrinterService;
import com.emenu.service.table.TableService;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * OrderDishPrintService
 * 后厨管理端打印订单菜品
 *
 * @author: quanyibo
 * @time: 2016/6/24
 */
@Service("orderDishPrintService")
public class OrderDishPrintServiceImpl implements OrderDishPrintService{

    @Autowired
    DishService dishService;

    @Autowired
    TagService tagService;

    @Autowired
    DishTasteService dishTasteService;

    @Autowired
    DishPackageService dishPackageService;

    @Autowired
    DishTagPrinterService dishTagPrinterService;

    @Autowired
    OrderDishService orderDishService;

    @Autowired
    TableService tableService;

    @Autowired
    OrderService orderService;

    @Autowired
    TasteService tasteService;

    @Autowired
    CookTableCacheService cookTableCacheService;

    @Override
    public void printOrderDishById(Integer orderDishId) throws SSException
    {
        List<PrintOrderDishDto> printOrderDishDtos = new ArrayList<PrintOrderDishDto>();
        try{
            printOrderDishDtos = this.getPrintOrderDishDtoById(orderDishId);//获取菜品打印信息
            if(printOrderDishDtos!=null)
            {
                for(PrintOrderDishDto dto :printOrderDishDtos)
                {
                    this.printOrderDish(dto);//打印菜品
                }
            }
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.ListPrintOrderDishDtoFail,e);
        }
    }

    @Override
    public List<PrintOrderDishDto> getPrintOrderDishDtoById(Integer orderDishId) throws SSException
    {
        List<PrintOrderDishDto> printOrderDishDtos = new ArrayList<PrintOrderDishDto>();
        OrderDish orderDish = new OrderDish();
        DishDto dishDto = new DishDto();
        Table table = new Table();
        try{
            orderDish = orderDishService.queryById(orderDishId);//查询出订单菜品
            dishDto = dishService.queryById(orderDish.getDishId());//查询出菜品详细信息

            //非套餐,即只是一个单独的菜品
            if(orderDish.getIsPackage()==0)
            {
                PrintOrderDishDto temp = new PrintOrderDishDto();//临时变量
                temp.setOrderDishId(orderDishId);//订单菜品主键
                temp.setDishName(dishDto.getName());//菜品名字
                temp.setDishBigTagName(tagService.queryLayer2TagByDishId(orderDish.getDishId()).getName());//菜品大类即菜品二级分类
                temp.setNum(orderDish.getDishQuantity());//菜品数量
                temp.setRemark(orderDish.getRemark());//订单菜品备注
                temp.setServerType(ServeTypeEnums.valueOf(orderDish.getServeType()).getType());//上菜方式
                temp.setTableName(tableService.queryById(orderDishService.queryOrderDishTableId(orderDishId)).getName());//点菜餐桌的名称

                String tasteName = new String();//菜品口味名称
                if(orderDish.getTasteId()!=null)
                    tasteName=tasteService.queryById(dishDto.getTasteId()).getName();
                if(tasteName!=null)
                    temp.setTaste(tasteName);//菜品口味
                //菜品大类对应的打印机的ip地址
                if(tagService.queryLayer2TagByDishId(dishDto.getId())!=null)
                {
                    if(dishTagPrinterService.queryByTagId(tagService.queryLayer2TagByDishId(dishDto.getId()).getId())!=null)
                    {
                        temp.setPrinterIp(dishTagPrinterService.queryByTagId(tagService.queryLayer2TagByDishId(dishDto.getId()).getId()).getIpAddress());
                    }
                }
                printOrderDishDtos.add(temp);
            }
            else//为套餐
            {
                DishPackageDto dishPackageDto = new DishPackageDto();
                //根据套餐Id查询出套餐具体信息
                dishPackageDto=dishPackageService.queryDishPackageById(orderDish.getPackageId());
                List<DishDto> dishDtos = new ArrayList<DishDto>();
                dishDtos=dishPackageDto.getChildDishDtoList();//获得套餐所有的子菜品
                for(DishDto dto :dishDtos)
                {
                    PrintOrderDishDto temp = new PrintOrderDishDto();//临时变量
                    temp.setOrderDishId(orderDishId);//订单菜品主键
                    temp.setDishName(dto.getName());//套餐菜品名字
                    temp.setDishBigTagName(tagService.queryLayer2TagByDishId(dto.getId()).getName());//菜品大类即菜品二级分类
                    temp.setNum(dto.getDishPackage().getDishQuantity()*orderDish.getPackageQuantity());//套餐菜品总数量为套餐数量*套餐单个菜品数量
                    temp.setRemark(orderDish.getRemark());//订单菜品备注对应到套餐中的每一个菜品上
                    temp.setServerType(ServeTypeEnums.valueOf(orderDish.getServeType()).getType());//上菜方式
                    temp.setTableName(tableService.queryById(orderDishService.queryOrderDishTableId(orderDishId)).getName());//点菜餐桌的名称

                    String tasteName = new String();//菜品口味名称
                    if(dishDto.getTasteId()!=null)
                        tasteName=tasteService.queryById(dishDto.getTasteId()).getName();
                    if(tasteName!=null)
                    temp.setTaste(tasteName);//菜品口味
                    //菜品大类对应的打印机的ip地址
                    if(tagService.queryLayer2TagByDishId(dishDto.getId())!=null)
                    {
                        if(dishTagPrinterService.queryByTagId(tagService.queryLayer2TagByDishId(dishDto.getId()).getId())!=null)
                        {
                            temp.setPrinterIp(dishTagPrinterService.queryByTagId(tagService.queryLayer2TagByDishId(dishDto.getId()).getId()).getIpAddress());
                        }
                    }
                    printOrderDishDtos.add(temp);
                }
            }
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.ListPrintOrderDishDtoFail,e);
        }
        return printOrderDishDtos;
    }

    @Override
    public void printOrderDish(PrintOrderDishDto printOrderDishDto) throws SSException
    {
        Socket socket = new Socket();
        InputStream is = null;
        OutputStream os = null;
        try{
            // 服务器的IP和端口,和服务器建立通信
            if(printOrderDishDto.getPrinterIp()==null)
                throw SSException.get(EmenuException.PrintOrderDishFail);
            socket.connect(new InetSocketAddress(printOrderDishDto.getPrinterIp(), 9100), 10000);
            if (socket.isConnected()) {//成功建立了连接
                os = socket.getOutputStream();
                String str = "";
                //套餐下的所有菜品的orderDishId均相同
                //第一条显示,用来提醒上菜的服务员这个菜品可能为套餐,套餐的话服务员应该等所有菜品都做完了后再进行上菜扫码
                str+= "菜品大类: " + printOrderDishDto.getDishBigTagName() + "\n";
                str += "桌名: " + printOrderDishDto.getTableName() + "\n";
                str += "菜品名称: " + printOrderDishDto.getDishName() + "\n";
                str += "数量: " + String.valueOf(printOrderDishDto.getNum()) + "\n";
                str += " 口味: ";
                if (printOrderDishDto.getTaste() != null)
                {
                    str+=printOrderDishDto.getTaste()+"\n";
                }
                else//没有选择菜品口味
                {
                    str+="正常\n";
                }
                str += "上菜方式: " + printOrderDishDto.getServerType()+ "\n";
                if (printOrderDishDto.getRemark() != null && !printOrderDishDto.getRemark().equals(""))
                    str += "备注: " + printOrderDishDto.getRemark() + "\n";

                // 打印
                os.write(PrintUtils.initPrinter());//初始化打印机

                //设置0为左对齐,1的话为设置为居中,2为右对齐
                os.write(PrintUtils.setLocation(0));

                os.write(PrintUtils.printText(str));//打印信息

                // 打印条码
                String orderDishIdStr = printOrderDishDto.getOrderDishId().toString();//条形码信息为OrderDishId
                os.write(PrintUtils.printBarCode(orderDishIdStr));

                // 打印订单id和时间
                os.write(PrintUtils.printText(orderDishIdStr + "\n"));
                os.write(PrintUtils.printText(DateUtils.formatDate(new Date()) + "\n"));//打印菜品的时间

                os.write(PrintUtils.printText("--------------------------------\n"));
                os.write(PrintUtils.printText("聚客多移动电子点餐系统由吉林省裕昌恒科技有限公司提供，合作洽谈请拨打热线电话:13234301365\n"));

                os.write(PrintUtils.println(4));
                //切纸
                os.write(PrintUtils.cutPaper());

                //修改订单菜品的状态
                OrderDish orderDish = new OrderDish();
                orderDish=orderDishService.queryById(printOrderDishDto.getOrderDishId());//查询出订单菜品
                orderDish.setStatus(2);//订单菜品状态：1.已下单  2.正在做  3.已上菜
                orderDishService.updateOrderDish(orderDish);//更新订单菜品的状态

                //修改餐台版本号
                Integer tableId = orderDishService.queryOrderDishTableId(orderDish.getId());//根据订单菜品id获取到tableId
                cookTableCacheService.updateTableVersion(tableId);//更新餐桌版本号
            }
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.PrintOrderDishFail,e);
        }
        finally {
            if (os != null) {
                try {
                    os.close();//关闭输出流
                } catch (IOException e) {
                    LogClerk.errLog.error(e.getMessage());
                    throw SSException.get(EmenuException.SystemException, e);
                }
            }
            if (socket != null) {
                try {
                    socket.close();//断开连接
                } catch (IOException e) {
                    LogClerk.errLog.error(e.getMessage());
                    throw SSException.get(EmenuException.SystemException, e);
                }
            }
        }
    }
}
