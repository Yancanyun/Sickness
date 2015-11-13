package com.emenu.common.entity.printer;

import com.pandawork.core.common.entity.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * printer
 *
 * @author Wang Liming
 * @date 2015/11/12 14:55
 */

@Entity
@Table(name = "t_printer")
public class Printer extends AbstractEntity {

    @Id
    private Integer id;

    //打印机名称
    private String name;

    //打印机品牌
    //1-佳博(GPrinter)，2-爱普生(Epson)/北洋(Beiyang)，3-固网，4-其他
    private Integer brand;

    //打印机型号
    //1-80，2-58
    @Column(name = "printer_model")
    private Integer printerModel;

    //打印机编号
    @Column(name = "device_number")
    private String deviceNumber;

    //打印机ip
    @Column(name = "ip_address")
    private String ipAddress;

    //打印机类型
    //1-吧台打印机，2-退菜打印机，3-分类打印机
    private Integer type;

    //打印机状态
    //0-未启用，1-启用
    private Integer state;

    //是否收银打印机
    //0-不是，1-是
    @Column(name = "is_cashier_printer")
    private Integer isCashierPrinter;

    // 创建时间
    @Column(name = "created_time")
    private Date createdTime;

    // 最近修改时间
    @Column(name = "last_modified_time")
    private Date lastModifiedTime;

    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBrand() {
        return brand;
    }

    public void setBrand(Integer brand) {
        this.brand = brand;
    }

    public Integer getPrinterModel() {
        return printerModel;
    }

    public void setPrinterModel(Integer printerModel) {
        this.printerModel = printerModel;
    }

    public String getDeviceNumber() {
        return deviceNumber;
    }

    public void setDeviceNumber(String deviceNumber) {
        this.deviceNumber = deviceNumber;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getIsCashierPrinter() {
        return isCashierPrinter;
    }

    public void setIsCashierPrinter(Integer isCashierPrinter) {
        this.isCashierPrinter = isCashierPrinter;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(Date lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }
}
