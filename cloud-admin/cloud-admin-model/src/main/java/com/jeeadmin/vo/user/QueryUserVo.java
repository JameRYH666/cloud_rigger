package com.jeeadmin.vo.user;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class QueryUserVo {
    /**
     * 用户名
     */
    private String userName;

    /**
     * 联系手机
     */
    private String userMobile;

    /**
     * 邮箱地址
     */
    private String userEmail;

    /**
     * 组织机构ID
     */
    private Long orgId;

    /**
     * 用户状态
     */
    private String userStatus;
}
