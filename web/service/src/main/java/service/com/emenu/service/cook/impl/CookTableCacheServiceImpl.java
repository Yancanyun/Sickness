package com.emenu.service.cook.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.emenu.common.entity.table.Table;
import com.emenu.common.exception.EmenuException;
import com.emenu.service.cook.CookTableCacheService;
import com.emenu.service.order.OrderDishService;
import com.emenu.service.order.OrderService;
import com.emenu.service.table.TableService;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 后厨管理桌子版本号控制 CookTableCacheServiceImpl
 * 前端根据桌子对应的版本号来判断是否刷页
 * 对应桌子的版本号改变的话则刷新当前桌子
 * 前端发ajax请求不断的获取桌子的版本号
 *
 * @author quanyibo
 * @date 2016/6/22.
 */
@Service("cookTableCacheService")
public class CookTableCacheServiceImpl implements CookTableCacheService{

    @Autowired
    OrderService orderService;

    @Autowired
    OrderDishService orderDishService;

    @Autowired
    TableService tableService;

    //点餐时存入的菜品缓存
    private Map<Integer,Long> tableVersionMap = new ConcurrentHashMap<Integer, Long>();

    /**
     * 服务器启动时将桌子的版本初始化
     * @PostConstruct注解的方法将在类实例化后调用
     */
    //@PostConstruct
    public void init() throws Exception
    {
        List<Table> tables = new ArrayList<Table>();
        try {
            tables=tableService.listAll();//获取所有的餐桌
            long version=System.currentTimeMillis();//版本号用计算机时间为单位
            if(tables!=null){
                for(Table dto:tables)
                {
                    //餐台状态：0、停用；1、可用；2、占用已结账；3、占用未结账；4、已并桌；5、已预订；6、已删除
                    if(dto.getId()!=0&&dto.getId()!=6)
                    {
                        int count = orderDishService.isTableHaveOrderDish(dto.getId());
                        if(count>0)//若该餐桌存在未上的菜品
                            tableVersionMap.put(dto.getId(),version);
                    }
                }
            }
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.InitCookTableCacheFail, e);
        }
    }

    @Override
    public JSONObject getAllTableVersion() throws SSException {

        try {
            JSONObject jsonObject=new JSONObject();
            JSONArray jsonArray=new JSONArray();

            for(Integer e:tableVersionMap.keySet()){
                JSONObject j=new JSONObject();
                j.put("id",e);
                j.put("version",tableVersionMap.get(e));
                jsonArray.add(j);
            }
            jsonObject.put("tables",jsonArray);
            return jsonObject;
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.ListAllTableVersionFail, e);
        }
    }

    @Override
    public Long getVersionByTableId(int tableId) throws SSException {
        return tableVersionMap.get(tableId);
    }

    @Override
    public void updateTableVersion(int tableId) throws SSException {
        long version=System.currentTimeMillis();
        tableVersionMap.put(tableId, version);
    }

    @Override
    public void deleteTable(int tableId) throws SSException {
        tableVersionMap.remove(tableId);
    }

}
