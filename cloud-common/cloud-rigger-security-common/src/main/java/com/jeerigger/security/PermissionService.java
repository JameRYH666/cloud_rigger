

package com.jeerigger.security;


/**
 * 接口权限判断工具
 */
public interface PermissionService {
    /**
     * 判断接口是否有xxx:xxx权限
     *
     * @param permission 权限
     * @return {boolean}
     */
    boolean hasPermission(String permission);

    /**
     * 更新权限
     */
    boolean readUpdatePermission(String permission);
}
