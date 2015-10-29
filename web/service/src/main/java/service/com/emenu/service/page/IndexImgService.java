package com.emenu.service.page;

import com.emenu.common.entity.page.IndexImg;
import com.emenu.common.enums.page.IndexImgEnum;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.framework.web.spring.fileupload.PandaworkMultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 点餐平台首页图片管理Service
 *
 * @author Wang LiMing
 * @date 2015/10/23 14:15
 */
public interface IndexImgService {

    /**
     * 添加首页图片-上传文件
     *
     * @param file
     * @param request
     * @return
     * @throws SSException
     */
    public IndexImg newIndexImg(PandaworkMultipartFile file, HttpServletRequest request) throws SSException;

    /**
     * 添加首页图片-新增记录
     *
     * @param indexImg
     * @return
     * @throws SSException
     */
    public IndexImg newIndexImg(IndexImg indexImg) throws SSException;

    /**
     * 根据图片id修改展示状态
     *
     * @param id
     * @param state
     * @throws SSException
     */
    public void updateStateById(int id, int state) throws SSException;

    /**
     * 查询所有首页图片
     *
     * @return
     * @throws SSException
     */
    public List<IndexImg> listAll() throws SSException;

    /**
     * 根据状态查询首页图片
     *
     * @param state
     * @return
     * @throws SSException
     */
    public IndexImg queryByState(int state) throws SSException;

    /**
     * 根据id删除首页图片
     *
     * @param id
     * @throws SSException
     */
    public void delById(int id) throws SSException;
}
