package com.jeerigger.core.module.sys.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.jeerigger.frame.base.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.InputStream;

/**
 * <p>
 * 文件上传
 * </p>
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysFile extends BaseModel<SysFile> {

    private static final long serialVersionUID = 1L;
    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 文件类型
     */
    private String fileType;

    /**
     * 文件地址
     */
    private String filePath;

    /**
     * 文件MD5码
     */
    private String fileMd5;

    /**
     * 文件内容
     */
    @TableField(exist = false)
    private InputStream inputStream;

}
