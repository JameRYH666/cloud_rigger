package com.jeerigger.core.module.sys.entity;

import com.jeerigger.frame.base.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 角色表
 * </p>
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserRole extends BaseModel<UserRole> {
    private static final long serialVersionUID = 1L;
    /**
     * 角色编码
     */
    private String roleCode;
    /**
     * 角色名称
     */
    private String roleName;
    /**
     * 角色状态（0:正常 2:停用）
     */
    private String roleStatus;

}
