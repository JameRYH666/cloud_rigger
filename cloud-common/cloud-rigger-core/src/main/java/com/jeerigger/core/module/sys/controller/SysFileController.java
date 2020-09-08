package com.jeerigger.core.module.sys.controller;

import com.jeerigger.core.common.file.FileUtil;
import com.jeerigger.core.module.sys.entity.SysFile;
import com.jeerigger.core.module.sys.service.ISysFileService;
import com.jeerigger.frame.base.controller.BaseController;
import com.jeerigger.frame.base.controller.ResultCodeEnum;
import com.jeerigger.frame.base.controller.ResultData;
import com.jeerigger.frame.exception.FrameException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@Controller
@RequestMapping("/sys/file")
@Api(value = "文件上传下载", tags = "文件上传下载")
public class SysFileController extends BaseController {

    @Autowired
    private ISysFileService sysFileService;


    @ResponseBody
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ApiOperation(value = "单文件上传", notes = "单文件上传")
    public ResultData fileUpload(@RequestParam("file") MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            return this.failed(ResultCodeEnum.ERROR_FILE_UPLOAD_FAIL, "上传文件列表为空！");
        }
        SysFile sysFile = sysFileService.uploadFile(multipartFile);
        if (sysFile != null && Objects.nonNull(sysFile.getId())) {
            return this.success(sysFile);
        } else {
            return this.failed(ResultCodeEnum.ERROR_FILE_UPLOAD_FAIL);
        }
    }

    @GetMapping("/download/{fileUuid}")
    @ApiOperation(value = "文件下载", notes = "文件下载")
    public void download(@PathVariable Long fileId, HttpServletResponse httpServletResponse) {
        if (Objects.isNull(fileId)) {
            throw new FrameException(ResultCodeEnum.ERROR_FILE_DOWNLOAD_FAIL, "文件标识不能为空!");
        }
        sysFileService.downloadFile(fileId, httpServletResponse);
    }

    @GetMapping("/view/{fileId}")
    @ApiOperation(value = "文件查看", notes = "文件查看")
    public ResponseEntity<InputStreamResource> view(@PathVariable Long fileId) {
        if (Objects.isNull(fileId)) {
            throw new FrameException(ResultCodeEnum.ERROR_FILE_DOWNLOAD_FAIL, "文件标识不能为空!");
        }
        SysFile sysFile = sysFileService.viewFile(fileId);
        if (sysFile != null) {
            HttpHeaders header = new HttpHeaders();
            if (FileUtil.match(sysFile.getFileType(), "jpg", "png", "gif", "bmp", "tif")) {
                header.setContentType(MediaType.IMAGE_JPEG);
            } else if (FileUtil.match(sysFile.getFileType(), "html", "htm","svg")) {
                header.setContentType(MediaType.TEXT_HTML);
            } else if (FileUtil.match(sysFile.getFileType(), "pdf")) {
                header.setContentType(MediaType.APPLICATION_PDF);
            } else {
                header.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            }
            header.add("X-Filename", sysFile.getFileName());
            header.add("X-MD5", sysFile.getFileMd5());
            return new ResponseEntity<>(new InputStreamResource(sysFile.getInputStream()), header, HttpStatus.OK);
        } else {
            throw new FrameException(ResultCodeEnum.ERROR_FILE_DOWNLOAD_FAIL, "文件已不存在或被埙坏!");
        }
    }

}
