package com.emenu.test.table;

import com.emenu.common.entity.table.Area;
import com.emenu.service.table.AreaService;
import com.emenu.test.AbstractTestCase;
import com.pandawork.core.common.exception.SSException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * AreaTest
 *
 * @author: yangch
 * @time: 2015/10/23 18:41
 */
public class AreaTest extends AbstractTestCase {

    @Autowired
    private AreaService areaService;

    @Test
    public void newArea() throws SSException{
        Area area = new Area();
        area.setName("12楼");
        areaService.newArea(area);
    }
    
    @Test
    public void queryAllArea() throws SSException {
        List<Area> areaList = areaService.listAll();

        for (Area area:areaList){
            System.out.println(area.getName());
        }
    }

    @Test
    public void queryAreaById() throws SSException {
        Area area = areaService.queryById(1);

        System.out.println(area.getName());
    }

    @Test
    public void queryAreaStateById() throws SSException {
        int state = areaService.queryStateById(1);

        System.out.println(state);
    }

    @Test
    public void updateArea() throws SSException{
        Area area = new Area();
        area.setId(1);
        area.setName("11楼");
        areaService.updateArea(1, area);
    }

    @Test
    public void delAreaById() throws SSException{
        areaService.delById(8);
    }
}
