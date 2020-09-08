package com.jeerigger.core.module.sys.service.impl;

import com.jeerigger.core.common.file.FileUtil;
import com.jeerigger.core.common.properties.JeeRiggerProperties;
import com.jeerigger.frame.base.service.impl.BaseServiceImpl;
import com.jeerigger.frame.base.controller.ResultCodeEnum;
import com.jeerigger.frame.exception.FrameException;
import com.jeerigger.frame.util.StringUtil;
import com.jeerigger.core.module.sys.entity.SysFile;
import com.jeerigger.core.module.sys.mapper.SysFileMapper;
import com.jeerigger.core.module.sys.service.ISysFileService;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * <p>
 * 文件上传 服务实现类
 * </p>
 */
@Service
public class SysFileServiceImpl extends BaseServiceImpl<SysFileMapper, SysFile> implements ISysFileService {
    @Resource
    private JeeRiggerProperties properties;

    @Override
    public SysFile uploadFile(MultipartFile multipartFile) {
        String fileName = multipartFile.getOriginalFilename();
        String fileType = FileUtil.getFileNameSub(fileName).toLowerCase();
        String filePath = FileUtil.getFolderPath() + "." + fileType;
        String fileCopyPath = properties.getFileUploadPath() + FileUtil.getFileSeparator() + filePath;
        File file = new File(fileCopyPath);
        if (!file.getParentFile().exists()) { //判断文件父目录是否存在
            file.getParentFile().mkdir();
        }
        try {
            //保存文件
            multipartFile.transferTo(file);
            String fileMd5 = FileUtil.md5File(fileCopyPath);
            if (StringUtil.isEmpty(fileMd5)) {
                return null;
            } else {
                SysFile sysFile = new SysFile();
                sysFile.setFileName(fileName);
                sysFile.setFileType(fileType);
                sysFile.setFilePath(filePath);
                sysFile.setFileMd5(fileMd5);
                if (this.save(sysFile)) {
                    return sysFile;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean uploadFileList(List<MultipartFile> multipartFileList) {
        for (MultipartFile multipartFile : multipartFileList) {

        }
        return false;
    }

    @Override
    public void downloadFile(Long fileId, HttpServletResponse httpServletResponse) {
        SysFile sysFile = this.getById(fileId);
        if (sysFile != null) {
            String filePath = properties.getFileUploadPath() + FileUtil.getFileSeparator() + sysFile.getFilePath();
            String fileMd5 = FileUtil.md5File(filePath);
            if (StringUtil.isNotEmpty(sysFile.getFileMd5()) && sysFile.getFileMd5().equals(fileMd5)) {
                InputStream inputStream = null;
                try {
                    inputStream = new FileInputStream(new File(filePath));
                    OutputStream outputStream = httpServletResponse.getOutputStream();
                    String fileName = new String(sysFile.getFileName().getBytes("gbk"), "iso-8859-1");
                    httpServletResponse.setContentType("application/x-download");
                    httpServletResponse.addHeader("Content-Disposition", "attachment;filename=" + fileName);
                    IOUtils.copy(inputStream, outputStream);
                    outputStream.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new FrameException(ResultCodeEnum.ERROR_FILE_DOWNLOAD_FAIL, "文件已不存在或被埙坏!");
                } finally {
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (Exception e) {

                        }
                    }
                }
            } else {
                throw new FrameException(ResultCodeEnum.ERROR_FILE_DOWNLOAD_FAIL, "文件已不存在或被埙坏!");
            }

        } else {
            throw new FrameException(ResultCodeEnum.ERROR_FILE_DOWNLOAD_FAIL, "文件已不存在!");
        }
    }

    /**
     * 文件查看
     */
    public SysFile viewFile(Long fileId){
        SysFile sysFile = this.getById(fileId);
        if (sysFile != null) {
            String filePath = properties.getFileUploadPath() + FileUtil.getFileSeparator() + sysFile.getFilePath();
            String fileMd5 = FileUtil.md5File(filePath);
            if (StringUtil.isNotEmpty(sysFile.getFileMd5()) && sysFile.getFileMd5().equals(fileMd5)) {
                Path path = Paths.get(filePath);
                try {
                    sysFile.setInputStream(Files.newInputStream(path));
                    return sysFile;
                }catch (Exception ex){
                    throw new FrameException(ResultCodeEnum.ERROR_FILE_DOWNLOAD_FAIL, "文件查看失败!");
                }
            } else {
                throw new FrameException(ResultCodeEnum.ERROR_FILE_DOWNLOAD_FAIL, "文件已不存在或被埙坏!");
            }
        } else {
            throw new FrameException(ResultCodeEnum.ERROR_FILE_DOWNLOAD_FAIL, "文件已不存在!");
        }
    }
}
