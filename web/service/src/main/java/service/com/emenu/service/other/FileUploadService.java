package com.emenu.service.other;

import com.emenu.common.enums.other.FileUploadPathEnums;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.framework.web.spring.fileupload.PandaworkMultipartFile;

/**
 * FileUploadService
 *
 * @author: zhangteng
 * @time: 2015/10/29 8:56
 **/
public interface FileUploadService {

    /**
     * 上传文件
     *
     * @param file      待上传的文件
     * @param pathEnums 上传文件存放的路径
     * @return          文件上传完之后的路径
     * @throws SSException
     */
    public String uploadFile(PandaworkMultipartFile file,
                             FileUploadPathEnums pathEnums) throws SSException;

    /**
     * 上传文件
     *
     * @param file      待上传的文件
     * @param pathEnums 上传文件存放的路径
     * @param width     压缩之后的宽度
     * @param height    压缩之后的高度
     * @return          文件上传之后的路径
     * @throws SSException
     */
    public String uploadFile(PandaworkMultipartFile file,
                             FileUploadPathEnums pathEnums,
                             int width,
                             int height) throws SSException;
}
