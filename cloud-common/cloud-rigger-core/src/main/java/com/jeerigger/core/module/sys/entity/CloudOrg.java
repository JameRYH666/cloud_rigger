package com.jeerigger.core.module.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jeerigger.frame.base.model.BaseTreeModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("cloud_org")
public class CloudOrg extends BaseTreeModel<CloudOrg> {
    /**
     * 组织机构代码
     */
    private String orgCode;

    /**
     * 组织机构名称
     */
    private String orgName;

    /**
     * 组织机构简称
     */
    private String orgShortName;

    /**
     * 组织机构类型
     */
    private String orgType;

    /**
     * 显示顺序
     */
    private Integer orgSort;

    /**
     * 使用状态（0:正常 2:停用）
     */
    private String orgStatus;
}
