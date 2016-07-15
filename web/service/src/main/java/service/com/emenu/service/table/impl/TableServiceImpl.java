package com.emenu.service.table.impl;

import com.emenu.common.dto.table.TableDto;
import com.emenu.common.entity.meal.MealPeriod;
import com.emenu.common.entity.order.Order;
import com.emenu.common.entity.order.OrderDish;
import com.emenu.common.entity.table.Table;
import com.emenu.common.entity.table.TableMealPeriod;
import com.emenu.common.enums.table.TableStatusEnums;
import com.emenu.common.exception.EmenuException;
import com.emenu.mapper.table.TableMapper;
import com.emenu.service.meal.MealPeriodService;
import com.emenu.service.order.OrderDishCacheService;
import com.emenu.service.table.*;
import com.emenu.service.order.OrderDishService;
import com.emenu.service.order.OrderService;
import com.emenu.service.table.AreaService;
import com.emenu.service.table.QrCodeService;
import com.emenu.service.table.TableMealPeriodService;
import com.emenu.service.table.TableService;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.core.framework.dao.CommonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * TableServiceImpl
 *
 * @author: yangch
 * @time: 2015/10/23 10:47
 */
@Service("tableService")
public class TableServiceImpl implements TableService {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderDishService orderDishService;

    @Autowired
    private TableMapper tableMapper;

    @Autowired
    private AreaService areaService;

    @Autowired
    private QrCodeService qrCodeService;

    @Autowired
    private CommonDao commonDao;

    @Autowired
    private TableMealPeriodService tableMealPeriodService;

    @Autowired
    private MealPeriodService mealPeriodService;

    @Autowired
    private TableMergeCacheService mergeTableCacheService;

    @Autowired
    private OrderDishCacheService orderDishCacheService;

    @Override
    public List<TableDto> listAllTableDto() throws SSException {
        List<TableDto> tableDtoList = new ArrayList<TableDto>();
        List<Table> tableList = Collections.emptyList();
        try {
            tableList = tableMapper.listAll();

            if (!tableList.isEmpty()) {
                for (Table table : tableList) {
                    TableDto tableDto = new TableDto();
                    tableDto.setTable(table);
                    tableDto.setAreaName(areaService.queryById(table.getAreaId()).getName());

                    //查询餐台对应的餐段ID
                    int id = table.getId();
                    List<Integer> mealPeriodIdList = tableMealPeriodService.listMealPeriodIdByTableId(id);
                    //将餐段List存入TableDto
                    List<MealPeriod> mealPeriodList = new ArrayList<MealPeriod>();
                    for (int mealPeriodId : mealPeriodIdList) {
                        MealPeriod mealPeriod = mealPeriodService.queryById(mealPeriodId);
                        mealPeriodList.add(mealPeriod);
                    }
                    tableDto.setMealPeriodList(mealPeriodList);

                    tableDtoList.add(tableDto);
                }
            }
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryTableFail, e);
        }
        return tableDtoList;
    }

    @Override
    public List<Table> listAll() throws SSException {
        List<Table> tableList = Collections.emptyList();
        try {
            tableList = tableMapper.listAll();
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryTableFail, e);
        }
        return tableList;
    }

    @Override
    public List<TableDto> listTableDtoByAreaId(int areaId) throws SSException {
        //检查AreaID是否合法
        if (Assert.lessOrEqualZero(areaId)) {
            return null;
        }
        List<TableDto> tableDtoList = new ArrayList<TableDto>();
        List<Table> tableList = Collections.emptyList();
        try {
            tableList = tableMapper.listByAreaId(areaId);
            if (!tableList.isEmpty()) {
                for (Table table : tableList) {
                    TableDto tableDto = new TableDto();
                    tableDto.setTable(table);
                    tableDto.setAreaName(areaService.queryById(table.getAreaId()).getName());

                    //查询餐台对应的餐段ID
                    int id = table.getId();
                    List<Integer> mealPeriodIdList = tableMealPeriodService.listMealPeriodIdByTableId(id);
                    //将餐段List存入TableDto
                    List<MealPeriod> mealPeriodList = new ArrayList<MealPeriod>();
                    for (int mealPeriodId : mealPeriodIdList) {
                        MealPeriod mealPeriod = mealPeriodService.queryById(mealPeriodId);
                        mealPeriodList.add(mealPeriod);
                    }
                    tableDto.setMealPeriodList(mealPeriodList);

                    tableDtoList.add(tableDto);
                }
            }
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryTableFail, e);
        }
        return tableDtoList;
    }

    @Override
    public List<Table> listByAreaId(int areaId) throws SSException {
        //检查AreaID是否合法
        if (Assert.lessOrEqualZero(areaId)) {
            return null;
        }
        List<Table> tableList = Collections.emptyList();
        try {
            tableList = tableMapper.listByAreaId(areaId);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryTableFail, e);
        }
        return tableList;
    }

    @Override
    public List<TableDto> listTableDtoByStatus(TableStatusEnums status) throws SSException {
        //检查Status是否合法(不小于0且不大于Deleted)
        if (Assert.lessZero(status.getId()) || status.getId() > TableStatusEnums.Deleted.getId()) {
            return null;
        }
        List<TableDto> tableDtoList = new ArrayList<TableDto>();
        List<Table> tableList = Collections.emptyList();
        try {
            tableList = tableMapper.listByStatus(status.getId());
            if (!tableList.isEmpty()) {
                for (Table table : tableList) {
                    TableDto tableDto = new TableDto();
                    tableDto.setTable(table);
                    tableDto.setAreaName(areaService.queryById(table.getAreaId()).getName());

                    //查询餐台对应的餐段ID
                    int id = table.getId();
                    List<Integer> mealPeriodIdList = tableMealPeriodService.listMealPeriodIdByTableId(id);
                    //将餐段List存入TableDto
                    List<MealPeriod> mealPeriodList = new ArrayList<MealPeriod>();
                    for (int mealPeriodId : mealPeriodIdList) {
                        MealPeriod mealPeriod = mealPeriodService.queryById(mealPeriodId);
                        mealPeriodList.add(mealPeriod);
                    }
                    tableDto.setMealPeriodList(mealPeriodList);

                    tableDtoList.add(tableDto);
                }
            }
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryTableFail, e);
        }
        return tableDtoList;
    }

    @Override
    public List<Table> listByStatus(TableStatusEnums status) throws SSException {
        //检查Status是否合法(不小于0且不大于Deleted)
        if (Assert.lessZero(status.getId()) || status.getId() > TableStatusEnums.Deleted.getId()) {
            return null;
        }
        List<Table> tableList = Collections.emptyList();
        try {
            tableList = tableMapper.listByStatus(status.getId());
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryTableFail, e);
        }
        return tableList;
    }

    @Override
    public List<TableDto> listTableDtoByAreaIdAndStatus(int areaId, TableStatusEnums status) throws SSException {
        //检查Status是否合法(不小于0且不大于Deleted)
        if (Assert.lessZero(status.getId()) || status.getId() > TableStatusEnums.Deleted.getId()) {
            return null;
        }
        //检查AreaID是否合法
        if (Assert.lessOrEqualZero(areaId)) {
            return null;
        }
        List<TableDto> tableDtoList = new ArrayList<TableDto>();
        List<Table> tableList = Collections.emptyList();
        try {
            tableList = tableMapper.listByAreaIdAndStatus(areaId, status.getId());
            if (!tableList.isEmpty()) {
                for (Table table : tableList) {
                    TableDto tableDto = new TableDto();
                    tableDto.setTable(table);
                    tableDto.setAreaName(areaService.queryById(table.getAreaId()).getName());

                    //查询餐台对应的餐段ID
                    int id = table.getId();
                    List<Integer> mealPeriodIdList = tableMealPeriodService.listMealPeriodIdByTableId(id);
                    //将餐段List存入TableDto
                    List<MealPeriod> mealPeriodList = new ArrayList<MealPeriod>();
                    for (int mealPeriodId : mealPeriodIdList) {
                        MealPeriod mealPeriod = mealPeriodService.queryById(mealPeriodId);
                        mealPeriodList.add(mealPeriod);
                    }
                    tableDto.setMealPeriodList(mealPeriodList);

                    tableDtoList.add(tableDto);
                }
            }
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryTableFail, e);
        }
        return tableDtoList;
    }

    @Override
    public List<Table> listByAreaIdAndStatus(int areaId, TableStatusEnums status) throws SSException {
        //检查Status是否合法(不小于0且不大于Deleted)
        if (Assert.lessZero(status.getId()) || status.getId() > TableStatusEnums.Deleted.getId()) {
            return null;
        }
        //检查AreaID是否合法
        if (Assert.lessOrEqualZero(areaId)) {
            return null;
        }
        List<Table> tableList = Collections.emptyList();
        try {
            tableList = tableMapper.listByAreaIdAndStatus(areaId, status.getId());
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryTableFail, e);
        }
        return tableList;
    }

    @Override
    public TableDto queryTableDtoById(int id) throws SSException {
        //检查ID是否合法
        if (Assert.lessOrEqualZero(id)) {
            return null;
        }
        try {
            TableDto tableDto = new TableDto();
            Table table = commonDao.queryById(Table.class, id);

            tableDto.setTable(table);
            tableDto.setAreaName(areaService.queryById(table.getAreaId()).getName());

            //查询餐台对应的餐段ID
            List<Integer> mealPeriodIdList = tableMealPeriodService.listMealPeriodIdByTableId(id);
            //将餐段List存入TableDto
            List<MealPeriod> mealPeriodList = new ArrayList<MealPeriod>();
            for (int mealPeriodId : mealPeriodIdList) {
                MealPeriod mealPeriod = mealPeriodService.queryById(mealPeriodId);
                mealPeriodList.add(mealPeriod);
            }
            tableDto.setMealPeriodList(mealPeriodList);

            return tableDto;
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryTableFail, e);
        }
    }

    @Override
    public Table queryById(int id) throws SSException {
        //检查ID是否合法
        if (Assert.lessOrEqualZero(id)) {
            return null;
        }
        try {
            return commonDao.queryById(Table.class, id);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryTableFail, e);
        }
    }

    @Override
    public int queryStatusById(int id) throws SSException {
        //检查ID是否合法
        if (Assert.lessOrEqualZero(id)) {
            return 0;
        }
        try {
            return tableMapper.queryStatusById(id);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryTableFail, e);
        }
    }

    @Override
    public void checkStatusById(int id) throws SSException {
        //检查ID是否合法
        if (Assert.lessOrEqualZero(id)) {
            return;
        }
        try {
            int status = queryStatusById(id);
            if (status != TableStatusEnums.Enabled.getId() && status != TableStatusEnums.Disabled.getId()) {
                throw SSException.get(EmenuException.TableHasUsed);
            }
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.UpdateTableFail, e);
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, SSException.class}, propagation = Propagation.REQUIRED)
    public Table newTable(TableDto tableDto, HttpServletRequest request) throws SSException {
        try {
            //从TableDto中获取Table
            Table table = tableDto.getTable();
            //从TableDto中获取MealPeriodList
            List<MealPeriod> mealPeriodList = tableDto.getMealPeriodList();

            //判断AreaId是否存在
            if (Assert.isNull(areaService.queryById(table.getAreaId()))) {
                throw SSException.get(EmenuException.AreaNotExist);
            }
            //判断是否重名
            if (checkNameIsExist(table.getName())) {
                throw SSException.get(EmenuException.TableNameExist);
            }
            //判断名称是否为空
            if (Assert.isNull(table.getName())) {
                throw SSException.get(EmenuException.TableNameIsNull);
            }
            //将状态设为"可用"
            table.setStatus(TableStatusEnums.Enabled.getId());
            //先插入餐台表
            commonDao.insert(table);
            //插入餐台表后，从插入的新餐台中获得ID，设置二维码地址
            updateQrCode(table.getId(), qrCodeService.newQrCode(table.getId(), null, request));

            //若勾选了餐段，则设置餐台-餐段信息
            if (mealPeriodList.size() != 0) {
                List<TableMealPeriod> tableMealPeriodList = new ArrayList<TableMealPeriod>();
                for (int i = 0; i < mealPeriodList.size(); i++) {
                    MealPeriod mealPeriod = mealPeriodList.get(i);
                    TableMealPeriod tableMealPeriod = new TableMealPeriod();
                    //设置TableID
                    tableMealPeriod.setTableId(table.getId());
                    //设置MealPeriodID
                    tableMealPeriod.setMealPeriodId(mealPeriod.getId());
                    tableMealPeriodList.add(tableMealPeriod);
                }
                tableMealPeriodService.newTableMealPeriod(tableMealPeriodList);
            }
            return table;
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.InsertTableFail, e);
        }
    }

    @Override
    public boolean checkNameIsExist(String name) throws SSException {
        //检查Name是否为空
        if (Assert.isNull(name)) {
            return false;
        }
        try {
            if (tableMapper.countByName(name) > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.SystemException, e);
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, SSException.class}, propagation = Propagation.REQUIRED)
    public void updateTable(Integer id, TableDto tableDto) throws SSException {
        //检查ID是否合法
        if (Assert.lessOrEqualZero(id)) {
            return ;
        }
        try {
            //从TableDto中获取Table
            Table table = tableDto.getTable();
            //从TableDto中获取MealPeriodList
            List<MealPeriod> mealPeriodList = tableDto.getMealPeriodList();

            int status = queryStatusById(table.getId());
            //仅当餐台状态为停用及可用时可以修改餐台
            if ((status != TableStatusEnums.Disabled.getId() && status != TableStatusEnums.Enabled.getId())) {
                throw SSException.get(EmenuException.TableHasUsed);
            }
            //判断AreaId是否合法
            if (Assert.lessOrEqualZero(table.getAreaId())) {
                throw SSException.get(EmenuException.AreaNotExist);
            }
            //若未传来ID，则为增加页，直接判断该名称是否在数据库中已存在
            if (id == null && checkNameIsExist(table.getName())) {
                throw SSException.get(EmenuException.TableNameExist);
            }
            //若传来ID，则为编辑页
            //判断传来的Name与相应ID在数据库中对应的名称是否一致，若不一致，再判断该名称是否在数据库中已存在
            if (id != null && !table.getName().equals(queryById(id).getName()) && checkNameIsExist(table.getName())) {
                throw SSException.get(EmenuException.TableNameExist);
            }
            //判断是否为空
            if (Assert.isNull(table.getName())) {
                throw SSException.get(EmenuException.TableNameIsNull);
            }
            //更新餐台表
            commonDao.update(table);
            //若将开台时间修改为null，则更新餐台表中的开台时间（commonDao对null不修改数据库，只能进行单独修改）
            if (table.getOpenTime() == null) {
                tableMapper.updateOpenTime(id, table.getOpenTime());
            }

            //若勾选了餐段，则设置餐台-餐段信息
            if (mealPeriodList.size() != 0) {
                List<TableMealPeriod> tableMealPeriodList = new ArrayList<TableMealPeriod>();
                for (int i = 0; i < mealPeriodList.size(); i++) {
                    MealPeriod mealPeriod = mealPeriodList.get(i);
                    TableMealPeriod tableMealPeriod = new TableMealPeriod();
                    //设置TableID
                    tableMealPeriod.setTableId(table.getId());
                    //设置MealPeriodID
                    tableMealPeriod.setMealPeriodId(mealPeriod.getId());
                    tableMealPeriodList.add(tableMealPeriod);
                }
                //更新餐台-餐段表
                tableMealPeriodService.updateTableMealPeriod(tableMealPeriodList);
            } else {
                //若餐段被修改为空，则删除所有对应的餐台-餐段信息
                tableMealPeriodService.delByTableId(id);
            }
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.UpdateTableFail, e);
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, SSException.class}, propagation = Propagation.REQUIRED)
    public void forceUpdateTable(Integer id, TableDto tableDto) throws SSException {
        //检查ID是否合法
        if (Assert.lessOrEqualZero(id)) {
            return ;
        }
        try {
            //从TableDto中获取Table
            Table table = tableDto.getTable();
            //从TableDto中获取MealPeriodList
            List<MealPeriod> mealPeriodList = tableDto.getMealPeriodList();

            int status = queryStatusById(table.getId());
            //判断AreaId是否合法
            if (Assert.lessOrEqualZero(table.getAreaId())) {
                throw SSException.get(EmenuException.AreaNotExist);
            }
            //若未传来ID，则为增加页，直接判断该名称是否在数据库中已存在
            if (id == null && checkNameIsExist(table.getName())) {
                throw SSException.get(EmenuException.TableNameExist);
            }
            //若传来ID，则为编辑页
            //判断传来的Name与相应ID在数据库中对应的名称是否一致，若不一致，再判断该名称是否在数据库中已存在
            if (id != null && !table.getName().equals(queryById(id).getName()) && checkNameIsExist(table.getName())) {
                throw SSException.get(EmenuException.TableNameExist);
            }
            //判断是否为空
            if (Assert.isNull(table.getName())) {
                throw SSException.get(EmenuException.TableNameIsNull);
            }
            //更新餐台表
            commonDao.update(table);
            //若将开台时间修改为null，则更新餐台表中的开台时间（commonDao对null不修改数据库，只能进行单独修改）
            if (table.getOpenTime() == null) {
                tableMapper.updateOpenTime(id, table.getOpenTime());
            }

            //若勾选了餐段，则设置餐台-餐段信息
            if (mealPeriodList.size() != 0) {
                List<TableMealPeriod> tableMealPeriodList = new ArrayList<TableMealPeriod>();
                for (int i = 0; i < mealPeriodList.size(); i++) {
                    MealPeriod mealPeriod = mealPeriodList.get(i);
                    TableMealPeriod tableMealPeriod = new TableMealPeriod();
                    //设置TableID
                    tableMealPeriod.setTableId(table.getId());
                    //设置MealPeriodID
                    tableMealPeriod.setMealPeriodId(mealPeriod.getId());
                    tableMealPeriodList.add(tableMealPeriod);
                }
                //更新餐台-餐段表
                tableMealPeriodService.updateTableMealPeriod(tableMealPeriodList);
            } else {
                //若餐段被修改为空，则删除所有对应的餐台-餐段信息
                tableMealPeriodService.delByTableId(id);
            }
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.UpdateTableFail, e);
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, SSException.class}, propagation = Propagation.REQUIRED)
    public void updateQrCode(int id, String qrCodePath) throws SSException {
        //检查ID是否合法
        if (Assert.lessOrEqualZero(id)) {
            return;
        }
        //检查QrCodePath是否合法
        if (Assert.isNull(qrCodePath)) {
            return;
        }
        try {
            tableMapper.updateQrCode(id, qrCodePath);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.UpdateTableFail, e);
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, SSException.class}, propagation = Propagation.REQUIRED)
    public void updateStatus(int id, int status) throws SSException {
        //检查ID是否合法
        if (Assert.lessOrEqualZero(id)) {
            return;
        }
        //检查Status是否合法
        if (Assert.lessZero(status)) {
            return;
        }
        try {
            //获取修改前的Status值
            int nowStatus = tableMapper.queryStatusById(id);
            //仅当修改前餐台状态为停用及可用时可以修改餐台
            if ((nowStatus != TableStatusEnums.Disabled.getId() && nowStatus != TableStatusEnums.Enabled.getId())) {
                throw SSException.get(EmenuException.TableHasUsed);
            }
            tableMapper.updateStatus(id, status);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.UpdateTableFail, e);
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, SSException.class}, propagation = Propagation.REQUIRED)
    public void delById(int id) throws SSException {
        //检查ID是否合法
        if (Assert.lessOrEqualZero(id)) {
            return;
        }
        try {
            int status = queryStatusById(id);
            //仅当餐台状态为停用及可用时可以删除餐台
            if ((status != TableStatusEnums.Disabled.getId() && status != TableStatusEnums.Enabled.getId())) {
                throw SSException.get(EmenuException.TableHasUsed);
            }
            //将状态设为"删除"
            tableMapper.updateStatus(id, TableStatusEnums.Deleted.getId());
            //删除餐台-餐段表中的内容
            tableMealPeriodService.delByTableId(id);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.DeleteTableFail, e);
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, SSException.class}, propagation = Propagation.REQUIRED)
    public void delByIds(List<Integer> idList) throws SSException {
        //检查IDList是否合法
        if (Assert.isNull(idList)) {
            return;
        }
        try {
            if (idList != null) {
                //先判断是否全部餐台均可以删除，若不能，报错
                for (int i : idList) {
                    if ((queryStatusById(i) != TableStatusEnums.Enabled.getId()) &&
                            (queryStatusById(i) != TableStatusEnums.Disabled.getId())) {
                        throw SSException.get(EmenuException.TableHasUsed);
                    }
                }
                //第二遍循环，逐个删除
                for (int i : idList) {
                    delById(i);
                }
            }
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.DeleteTableFail, e);
        }
    }

    @Override
    public int countByAreaId(int areaId) throws SSException {
        //检查AreaID是否合法
        if (Assert.lessOrEqualZero(areaId)) {
            return 0;
        }
        try {
            return tableMapper.countByAreaId(areaId);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.SystemException, e);
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, SSException.class}, propagation = Propagation.REQUIRED)
    public void openTable(int id, int personNum) throws SSException {
        //检查TableID是否是否合法
        if (Assert.lessOrEqualZero(id)) {
            return;
        }
        try {
            //检查是否为"可用"状态
            if (queryStatusById(id) != TableStatusEnums.Enabled.getId()) {
                throw SSException.get(EmenuException.TableIsNotEnabled);
            }

            //设置餐台信息
            TableDto tableDto = queryTableDtoById(id);
            tableDto.getTable().setPersonNum(personNum);
            tableDto.getTable().setOpenTime(new Date());
            tableDto.getTable().setStatus(TableStatusEnums.Uncheckouted.getId());

            //更新餐台
            updateTable(id, tableDto);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.OpenTableFail, e);
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, SSException.class}, propagation = Propagation.REQUIRED)
    public void changeTable(int oldTableId, int newTableId) throws SSException {
        //检查两个餐台ID是否是否合法
        if (Assert.lessOrEqualZero(oldTableId)) {
            return;
        }
        if (Assert.lessOrEqualZero(newTableId)) {
            return;
        }
        try {
            //检查旧餐台是否为"占用未结账"状态
            if (queryStatusById(oldTableId) != TableStatusEnums.Uncheckouted.getId()) {
                throw SSException.get(EmenuException.TableIsNotUncheckouted);
            }

            //检查新餐台是否为"可用"状态
            if (queryStatusById(newTableId) != TableStatusEnums.Enabled.getId()) {
                throw SSException.get(EmenuException.TableIsNotEnabled);
            }

            //获取旧餐台信息
            TableDto oldTableDto = queryTableDtoById(oldTableId);

            //更新新餐台信息
            TableDto newTableDto = queryTableDtoById(newTableId);
            newTableDto.getTable().setPersonNum(oldTableDto.getTable().getPersonNum());
            newTableDto.getTable().setOpenTime(oldTableDto.getTable().getOpenTime());
            newTableDto.getTable().setStatus(TableStatusEnums.Uncheckouted.getId());

            //换台后订单对应的tableId也要改变,并且要把ordeDish的isChange字段设置为1
            List<Order> orders = new ArrayList<Order>();
            List<OrderDish> orderDishs = new ArrayList<OrderDish>();

            //查询出旧餐桌未结账但是已下单的订单
            orders=orderService.listByTableIdAndStatus(oldTableDto.getTable().getId(),1);
            for(Order dto :orders)
            {
                dto.setTableId(newTableDto.getTable().getId());//订单要改变成新的桌号
                orderDishs=orderDishService.listByOrderId(dto.getId());//获取订单菜品
                for(OrderDish orderDish : orderDishs)
                {
                    //订单菜品已下单或者正在做再设置换台属性,已上菜了的话没有必要把isChange属性设为1
                    if(orderDish.getStatus()==1||orderDish.getStatus()==2)
                    {
                        orderDish.setIsChange(1);
                        orderDishService.updateOrderDish(orderDish);
                    }
                }
                orderService.updateOrder(dto);
            }

            updateTable(newTableId, newTableDto);

            //重置旧餐台信息
            oldTableDto.getTable().setPersonNum(0);
            oldTableDto.getTable().setOpenTime(null);
            oldTableDto.getTable().setStatus(TableStatusEnums.Enabled.getId());

            forceUpdateTable(oldTableId, oldTableDto);

            // 把新旧餐台的缓存交换


        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.ChangeTableFail, e);
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, SSException.class}, propagation = Propagation.REQUIRED)
    public void cleanTable(int id) throws SSException {
        //检查ID是否合法
        if (Assert.lessOrEqualZero(id)) {
            return ;
        }
        try {
            //更新新餐台信息
            TableDto tableDto = queryTableDtoById(id);
            tableDto.getTable().setPersonNum(0);
            tableDto.getTable().setOpenTime(null);
            tableDto.getTable().setStatus(TableStatusEnums.Enabled.getId());

            forceUpdateTable(id, tableDto);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.CleanTableFail, e);
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, SSException.class}, propagation = Propagation.REQUIRED)
    public void mergeTable(List<Integer> tableIdList) throws SSException {
        try {
            if (Assert.isNull(tableIdList) || tableIdList.size() < 2) {
                throw SSException.get(EmenuException.MergeTableNumLessThanTwo);
            }

            for (int tableId : tableIdList) {
                // 检查是否所有餐台均为可用、占用未结账、已并桌、占用已结账状态
                if (queryStatusById(tableId) != TableStatusEnums.Enabled.getId() &&
                        queryStatusById(tableId) != TableStatusEnums.Uncheckouted.getId() &&
                        queryStatusById(tableId) != TableStatusEnums.Merged.getId() &&
                        queryStatusById(tableId) != TableStatusEnums.Checkouted.getId()) {
                    throw SSException.get(EmenuException.TableIsNotEnabled);
                }
            }

            // 更新餐台信息
            for (int tableId : tableIdList) {
                TableDto tableDto = queryTableDtoById(tableId);
                tableDto.getTable().setOpenTime(new Date());
                tableDto.getTable().setStatus(TableStatusEnums.Merged.getId());
                updateTable(tableId, tableDto);

                // 加入到并台表
            }

        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.MergeTableFail, e);
        }
    }
}