package com.jeerigger.core.module.sys.service;

import com.jeerigger.frame.base.service.BaseService;
import com.jeerigger.core.module.sys.entity.SysFile;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 * 文件上传 服务类
 * </p>
 */
public interface ISysFileService extends BaseService<SysFile> {
    /**
     * 上传单个文件
     *
     * @param multipartFile
     * @return 附件UUID
     */
    SysFile uploadFile(MultipartFile multipartFile);

    /**
     * 上传多个文件
     *
     * @param multipartFileList
     * @return
     */
    boolean uploadFileList(List<MultipartFile> multipartFileList);

    /**
     * 文件下载
     *
     * @param fileId
     * @param httpServletResponse
     */
    void downloadFile(Long fileId, HttpServletResponse httpServletResponse);

    /**
     * 查看文件
     *
     * @param fileId
     * @return
     */
    SysFile viewFile(Long fileId);
}
