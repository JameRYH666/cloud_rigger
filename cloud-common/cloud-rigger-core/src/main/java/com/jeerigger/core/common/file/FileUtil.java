package com.jeerigger.core.common.file;

import com.jeerigger.frame.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.DigestUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * 文件工具类
 */
@Slf4j
public class FileUtil {

    /**
     * 获取文件系统分隔符
     *
     * @return
     */
    public static String getFileSeparator() {
        return System.getProperty("file.separator");
    }

    /**
     * 获取文件保存的文件夹
     *
     * @return
     */
    public static String getFolderPath() {
        String dateStr = new SimpleDateFormat("yyyyMMdd").format(new Date());
        return dateStr + getFileSeparator() + getFileUuidName();
    }


    /**
     * 获取随机文件名
     *
     * @return
     */
    public static String getFileUuidName() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 返回文件名后缀
     *
     * @param fileName
     * @return
     */
    public static String getFileNameSub(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }


    /**
     * 匹配文件的后缀
     *
     * @param fileType
     * @param exts
     * @return
     */
    public static boolean match(String fileType, String... exts) {
        if (StringUtil.isEmpty(fileType)) {
            return false;
        }
        for (String ext1 : exts) {
            if (fileType.equals(ext1)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 文件路径取md5
     *
     * @param path
     * @return
     */
    public static String md5File(String path) {
        String md5Str = null;
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(path);
            md5Str = DigestUtils.md5DigestAsHex(fileInputStream);
        } catch (IOException e) {
            log.error("calc md5 failed", e);
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    log.error("close resource failed");
                }
            }
        }
        return md5Str;
    }

}
