package com.emenu.service.party.group.vip.impl;

import com.emenu.common.entity.party.group.Party;
import com.emenu.common.entity.party.security.SecurityUser;
import com.emenu.common.entity.party.group.vip.VipInfo;
import com.emenu.common.enums.party.AccountTypeEnums;
import com.emenu.common.enums.party.PartyTypeEnums;
import com.emenu.common.enums.party.UserStatusEnums;
import com.emenu.common.exception.EmenuException;
import com.emenu.common.exception.PartyException;
import com.emenu.common.utils.CommonUtil;
import com.emenu.mapper.party.group.vip.VipInfoMapper;
import com.emenu.service.party.group.PartyService;
import com.emenu.service.party.security.SecurityUserService;
import com.emenu.service.party.group.vip.VipInfoService;
import com.pandawork.core.common.exception.ExceptionMes;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.core.framework.dao.CommonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * 会员信息管理Service实现
 *
 * @author chenyuting
 * @time 2015/10/23  14:20
 */
@Service("vipInfoService")
public class VipInfoServiceImpl implements VipInfoService{

    @Autowired
    private VipInfoMapper vipInfoMapper;

    @Autowired
    @Qualifier("commonDao")
    private CommonDao commonDao;

    @Autowired
    private PartyService partyService;

    @Autowired
    private SecurityUserService securityUserService;


    @Override
    public List<VipInfo> listByPage(int curPage, int pageSize) throws SSException{
        curPage = curPage <= 0 ? 0 : curPage - 1;
        int offset = curPage * pageSize;
        if (Assert.lessZero(offset)) {
            return Collections.emptyList();
        }
        List<VipInfo> vipInfoList = Collections.<VipInfo>emptyList();
        try{
            vipInfoList = vipInfoMapper.listByPage(offset, pageSize);
        }catch(Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.ListUnitFailed, e);
        }
        return vipInfoList;
    }

    @Override
    public int countAll() throws SSException{
        Integer count = 0;
        try{
            count = vipInfoMapper.countAll();
        } catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(ExceptionMes.SYSEXCEPTION, e);
        }
        return count == null ? 0 : count;
    }

    @Override
    public List<VipInfo> listByKeyword(String keyword, int curPage, int pageSize) throws SSException{
        curPage = curPage <= 0 ? 0 : curPage - 1;
        int offset = curPage * pageSize;
        if (Assert.lessZero(offset)) {
            return Collections.emptyList();
        }
        Assert.isNotNull(keyword, EmenuException.VipInfoKeywordNotNull);
        try{
            return vipInfoMapper.listByKeyword(keyword, curPage, pageSize);
        } catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(ExceptionMes.SYSEXCEPTION, e);
        }
    }

    @Override
    public int countByKeyword(String keyword) throws SSException{
        Integer count = 0;
        try{
            count = vipInfoMapper.countByKeyword(keyword);
        } catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(ExceptionMes.SYSEXCEPTION, e);
        }
        return count == null ? 0 : count;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class,RuntimeException.class,SSException.class},propagation = Propagation.REQUIRED)
    public VipInfo newVipInfo(VipInfo vipInfo) throws SSException{
        if (!checkBeforeSave(vipInfo)){
            return null;
        }

        try{
            //首先判断手机号是否存在
            String phone = vipInfo.getPhone();
            if (this.checkPhoneIsExist(phone)){
                return null;
            }

            //1.先向t_party表添加一条当事人信息
            Party party = new Party();
            party.setPartyTypeId(PartyTypeEnums.Vip.getId());//会员
            party.setCreatedUserId(1);//此处应为当前登录者的id
            Party newParty = this.partyService.newParty(party);
            int partyId = newParty.getId();//获取刚插入数据的partyId

            //2.再向t_party_security_user表添加一条登录信息，默认登录名为手机号码，密码为000000；
            SecurityUser securityUser = new SecurityUser();
            String password = com.pandawork.core.common.util.CommonUtil.md5("123456");
            securityUser.setPartyId(partyId);
            securityUser.setLoginName(phone);
            securityUser.setPassword("000000");
            securityUser.setAccountType(AccountTypeEnums.Normal.getId());//正常账户
            this.securityUserService.newSecurityUser(securityUser);

            //3.添加t_party_vip_info会员基本信息表
            vipInfo.setPartyId(partyId);
            commonDao.insert(vipInfo);

            return vipInfo;
        } catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(ExceptionMes.SYSEXCEPTION, e);
        }
    }

    @Override
    public boolean checkPhoneIsExist(String phone) throws SSException{
        Integer count = 0;
        try{
            count = vipInfoMapper.countByPhone(phone);
        } catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(ExceptionMes.SYSEXCEPTION, e);
        }
        return count > 0;
    }

    @Override
    public void updateVipInfo(VipInfo vipInfo) throws SSException{
        Assert.isNotNull(vipInfo);

        try{
            commonDao.update(vipInfo);
        } catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(ExceptionMes.SYSEXCEPTION, e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void updateStateById(int id, UserStatusEnums state) throws SSException{
        CommonUtil.checkId(id, PartyException.UserIdNotNull);
        Assert.isNotNull(state, PartyException.UserStatusIllegal);

        try {
            vipInfoMapper.updateStateById(id, state.getId());
        } catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(ExceptionMes.SYSEXCEPTION, e);
        }
    }

    @Override
    public VipInfo queryById(int id) throws SSException{
        CommonUtil.checkId(id, EmenuException.VipIdNotNull);

        try {
            return vipInfoMapper.queryById(id);
        } catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(ExceptionMes.SYSEXCEPTION, e);
        }
    }

    private boolean checkBeforeSave(VipInfo vipInfo)throws SSException{
        if (Assert.isNull(vipInfo)){
            return false;
        }
        Assert.isNotNull(vipInfo.getName(), EmenuException.VipNameNotNUll);
        Assert.isNotNull(vipInfo.getPhone(), EmenuException.VipPhoneNotNull);
        return true;
    }

}