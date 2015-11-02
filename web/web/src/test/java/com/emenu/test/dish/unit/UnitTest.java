package com.emenu.test.dish.unit;

import com.emenu.common.entity.dish.unit.Unit;
import com.emenu.service.dish.unit.UnitService;
import com.emenu.test.AbstractTestCase;
import com.pandawork.core.common.exception.SSException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.ArrayList;
import java.util.List;

/**
 * UnitTest
 * @author xubaorong
 * @date 2015/10/23
 */
public class UnitTest extends AbstractTestCase {

    @Autowired
    @Qualifier("unitService")
    private UnitService unitService;

    @Test
    public void addUnit() throws SSException {
        Unit unit = new Unit();
        unit.setName("包");
        unit.setType(2);
        unitService.newUnit(unit);
    }

    @Test
    public void delUnitById() throws SSException {
        unitService.delById(4);
    }

    @Test
    public void updateUnit() throws SSException {
        Unit unit = new Unit();
        unit.setId(5);
        unit.setName("碗");
        unit.setType(2);
        unitService.updateUnit(unit);
    }

    @Test
    public void listAllUnit() throws SSException {
        List<Unit> list = new ArrayList<Unit>();
//        list = unitService.listAllUnit();
        list = unitService.listUnitByPage(1, 10);
        for (Unit unit : list) {
            System.out.println(unit.getName()+"  "+unit.getCreatedTime());
        }
        System.out.println(unitService.countUnit());
    }

    @Test
    public void queryUnitById() throws SSException {
        Unit unit = new Unit();
        unit = unitService.queryById(2);
        System.out.print(unit.getName());
    }
}




