package com.emenu.service.party.security.impl;

import com.emenu.common.entity.party.security.SecurityPermission;
import com.emenu.common.exception.PartyException;
import com.emenu.mapper.party.security.SecurityPermissionMapper;
import com.emenu.service.party.security.SecurityPermissionService;
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
 * 权限service实现
 *
 * @author: zhangteng
 * @time: 15/10/10 下午9:14
 */
@Service("securityPermissionService")
public class SecurityPermissionServiceImpl implements SecurityPermissionService {

    @Autowired
    @Qualifier("commonDao")
    private CommonDao commonDao;

    @Autowired
    private SecurityPermissionMapper securityPermissionMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public SecurityPermission newPermission(SecurityPermission securityPermission) throws SSException {
        if (!checkBeforeSave(securityPermission)) {
            return null;
        }

        try {
            return commonDao.insert(securityPermission);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(PartyException.SystemException, e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void delById(int id) throws SSException {
        if (Assert.lessOrEqualZero(id)){
            return ;
        }
        try {
            commonDao.deleteById(SecurityPermission.class, id);
        } catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(PartyException.SystemException, e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void update(SecurityPermission securityPermission) throws SSException {
        if (Assert.isNull(securityPermission)) {
            return ;
        }
        // 权限表达式不能为空
        Assert.isNotNull(securityPermission.getExpression(), PartyException.PermissionExpressionNotNull);

        if (Assert.isNull(securityPermission.getId())
                || Assert.lessOrEqualZero(securityPermission.getId())) {
            throw SSException.get(PartyException.PermissionIdNotNull);
        }

        // 判断权限表达式是否和重复
        SecurityPermission oldPermission;
        try {
            oldPermission = commonDao.queryById(SecurityPermission.class, securityPermission.getId());
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(PartyException.SystemException, e);
        }
        Assert.isNotNull(oldPermission, PartyException.PermissionNotExist);
        if (!oldPermission.getExpression().equals(securityPermission.getExpression())) {
            // 表达式重复判断
            if (this.queryExpressionIsExist(securityPermission.getExpression())) {
                throw SSException.get(PartyException.PermissionExpressionExist);
            }
        }

        try{
            commonDao.update(securityPermission);
        } catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(PartyException.SystemException, e);
        }
    }

    @Override
    public List<SecurityPermission> listByPage(int page, int pageSize) throws SSException {
        int offset = page * pageSize;
        List<SecurityPermission> list = Collections.emptyList();
        if (Assert.lessZero(offset)){
            return list;
        }

        try {
            list = securityPermissionMapper.listByPage(offset, pageSize);
        } catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(PartyException.SystemException, e);
        }

        return list;
    }

    @Override
    public List<SecurityPermission> listAll() throws SSException {
        try {
            return securityPermissionMapper.listAll();
        } catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(PartyException.SystemException, e);
        }
    }

    @Override
    public List<Integer> listAllIds() throws SSException {
        try {
            return securityPermissionMapper.listAllIds();
        } catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(PartyException.SystemException, e);
        }
    }

    @Override
    public List<SecurityPermission> listByIdList(List<Integer> idList, boolean isInList) throws SSException {
        List<SecurityPermission> list = Collections.emptyList();
        if (Assert.isEmpty(idList)) {
            return list;
        }

        try {
            list = securityPermissionMapper.listByIdList(idList, isInList);
        } catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(PartyException.SystemException, e);
        }

        return list;
    }

    @Override
    public int count() throws SSException {
        Integer count = 0;
        try {
            count = securityPermissionMapper.count();
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(PartyException.SystemException);
        }
        return count == null ? 0 : count;
    }




    // －－－－－－－私有方法

    /**
     * 判断权限表达式是否重复
     * @param expression
     * @return
     * @throws SSException
     */
    private boolean queryExpressionIsExist(String expression) throws SSException{
        int num = 0;
        try{
            num = securityPermissionMapper.countByExpression(expression);
        }catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(PartyException.SystemException, e);
        }
        return num > 0;
    }

    private boolean checkBeforeSave(SecurityPermission securityPermission) throws SSException {
        if (Assert.isNull(securityPermission)) {
            return false;
        }

        // 权限表达式不能为空
        Assert.isNotNull(securityPermission.getExpression(), PartyException.PermissionExpressionNotNull);

        // 表达式重复判断
        if (this.queryExpressionIsExist(securityPermission.getExpression())){
            throw SSException.get(PartyException.PermissionExpressionExist);
        }

        return true;
    }
}
