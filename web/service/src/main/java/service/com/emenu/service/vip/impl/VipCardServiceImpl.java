package com.emenu.service.vip.impl;

import com.emenu.common.dto.vip.VipCardDto;
import com.emenu.common.entity.party.group.employee.Employee;
import com.emenu.common.entity.party.group.vip.VipInfo;
import com.emenu.common.entity.vip.VipCard;
import com.emenu.common.enums.vip.VipCardStatusEnums;
import com.emenu.common.exception.EmenuException;
import com.emenu.mapper.party.group.employee.EmployeeMapper;
import com.emenu.mapper.party.group.vip.VipInfoMapper;
import com.emenu.mapper.vip.VipCardMapper;
import com.emenu.service.vip.VipCardService;
import com.pandawork.core.common.exception.ExceptionMes;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.core.framework.dao.CommonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * VipCardServiceImpl
 *
 * @author: yangch
 * @time: 2016/1/18 19:18
 */
@Service("vipCardService")
public class VipCardServiceImpl implements VipCardService {
    @Autowired
    private VipCardMapper vipCardMapper;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private VipInfoMapper vipInfoMapper;

    @Autowired
    private CommonDao commonDao;

    @Override
    public List<VipCard> listAll() throws SSException {
        List<VipCard> vipCardList = Collections.emptyList();
        try {
            vipCardList = vipCardMapper.listAll();
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryVipCardFail, e);
        }
        return vipCardList;
    }

    @Override
    public List<VipCardDto> listAllVipCardDto() throws SSException {
        List<VipCardDto> vipCardDtoList = new ArrayList<VipCardDto>();
        List<VipCard> vipCardList = Collections.emptyList();
        try {
            vipCardList = vipCardMapper.listAll();

            for(VipCard vipCard : vipCardList) {
                VipInfo vipInfo = vipInfoMapper.queryByPartyId(vipCard.getVipPartyId());
                Employee employee = employeeMapper.queryByPartyId(vipCard.getOperatorPartyId());
                String operator = employee.getName();

                VipCardDto vipCardDto = new VipCardDto();
                vipCardDto.setVipCard(vipCard);
                vipCardDto.setVipInfo(vipInfo);
                vipCardDto.setOperator(operator);

                vipCardDtoList.add(vipCardDto);
            }
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryVipCardFail, e);
        }
        return vipCardDtoList;
    }

    @Override
    public List<VipCard> listByPage(int curPage, int pageSize) throws SSException {
        List<VipCard> vipCardList = Collections.emptyList();
        curPage = curPage <= 0 ? 0 : curPage - 1;
        int offset = curPage * pageSize;
        if (Assert.lessZero(offset)) {
            return vipCardList;
        }

        try {
            vipCardList = vipCardMapper.listByPage(offset, pageSize);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryVipCardFail, e);
        }
        return vipCardList;
    }

    @Override
    public List<VipCardDto> listVipCardDtoByPage(int curPage, int pageSize) throws SSException {
        List<VipCardDto> vipCardDtoList = new ArrayList<VipCardDto>();
        curPage = curPage <= 0 ? 0 : curPage - 1;
        int offset = curPage * pageSize;
        if (Assert.lessZero(offset)) {
            return vipCardDtoList;
        }

        List<VipCard> vipCardList = Collections.emptyList();
        try {
            vipCardList = vipCardMapper.listByPage(offset, pageSize);

            for(VipCard vipCard : vipCardList) {
                VipInfo vipInfo = vipInfoMapper.queryByPartyId(vipCard.getVipPartyId());
                Employee employee = employeeMapper.queryByPartyId(vipCard.getOperatorPartyId());
                String operator = employee.getName();

                VipCardDto vipCardDto = new VipCardDto();
                vipCardDto.setVipCard(vipCard);
                vipCardDto.setVipInfo(vipInfo);
                vipCardDto.setOperator(operator);

                vipCardDtoList.add(vipCardDto);
            }
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryVipCardFail, e);
        }
        return vipCardDtoList;
    }

    @Override
    public List<VipCard> listByKeywordAndDate(String keyword, Date startTime, Date endTime,
                                              int curPage, int pageSize) throws SSException {
        List<VipCard> vipCardList = Collections.emptyList();
        curPage = curPage <= 0 ? 0 : curPage - 1;
        int offset = curPage * pageSize;
        if (Assert.lessZero(offset)) {
            return vipCardList;
        }

        try {
            vipCardList = vipCardMapper.listByKeywordAndDate(keyword, startTime, endTime, offset, pageSize);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryVipCardFail, e);
        }
        return vipCardList;
    }
    @Override
    public List<VipCardDto> listVipCardDtoByKeywordAndDate(String keyword, Date startTime,
                                                           Date endTime, int curPage,
                                                           int pageSize) throws SSException {
        List<VipCardDto> vipCardDtoList = new ArrayList<VipCardDto>();
        curPage = curPage <= 0 ? 0 : curPage - 1;
        int offset = curPage * pageSize;
        if (Assert.lessZero(offset)) {
            return vipCardDtoList;
        }

        List<VipCard> vipCardList = Collections.emptyList();
        try {
            vipCardList = vipCardMapper.listByKeywordAndDate(keyword, startTime, endTime, offset, pageSize);

            for(VipCard vipCard : vipCardList) {
                VipInfo vipInfo = vipInfoMapper.queryByPartyId(vipCard.getVipPartyId());
                Employee employee = employeeMapper.queryByPartyId(vipCard.getOperatorPartyId());
                String operator = employee.getName();

                VipCardDto vipCardDto = new VipCardDto();
                vipCardDto.setVipCard(vipCard);
                vipCardDto.setVipInfo(vipInfo);
                vipCardDto.setOperator(operator);

                vipCardDtoList.add(vipCardDto);
            }
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryVipCardFail, e);
        }
        return vipCardDtoList;
    }

    @Override
    public int countAll() throws SSException{
        try{
            return vipCardMapper.countAll();
        } catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.SystemException, e);
        }
    }

    @Override
    public int countByKeywordAndDate(String keyword, Date startTime, Date endTime) throws SSException{
        try{
            return vipCardMapper.countByKeywordAndDate(keyword, startTime, endTime);
        } catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.SystemException, e);
        }
    }

    @Override
    public VipCard queryById(int id) throws SSException {
        //检查ID是否合法
        if (Assert.lessOrEqualZero(id)) {
            return null;
        }
        try {
            return commonDao.queryById(VipCard.class, id);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryVipCardFail, e);
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, SSException.class}, propagation = Propagation.REQUIRED)
    public VipCard newVipCard(VipCard vipCard) throws SSException {
        try {
            //将状态设为"可用"
            vipCard.setStatus(VipCardStatusEnums.Enabled.getId());
            return commonDao.insert(vipCard);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.InsertVipCardFail, e);
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, SSException.class}, propagation = Propagation.REQUIRED)
    public void updateVipCard(int id, VipCard vipCard) throws SSException {
        //检查ID是否合法
        if (Assert.lessOrEqualZero(id)) {
            return;
        }
        try {
            commonDao.update(vipCard);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.UpdateVipCardFail, e);
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, SSException.class}, propagation = Propagation.REQUIRED)
    public void delById(int id) throws SSException {
        //检查ID是否合法
        if (Assert.lessOrEqualZero(id)) {
            return ;
        }
        try {
            //将状态设为"删除"
            vipCardMapper.updateStatus(id, VipCardStatusEnums.Deleted.getId());
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.DeleteVipCardFail, e);
        }
    }
}
