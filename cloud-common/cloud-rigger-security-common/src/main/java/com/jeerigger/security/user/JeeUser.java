package com.jeerigger.security.user;

import com.jeerigger.frame.enums.UserTypeEnum;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.List;

public class JeeUser extends User {
    @Getter
    private Long userId;
    /**
     * sessionId
     */
    @Getter
    @Setter
    private String sessionId;
    /**
     * 用户登录名
     */
    @Getter
    private String loginName;
    @Getter
    private String userName;
    /**
     * 组织id
     */
    @Getter
    private List<String> orgIds;
    /**
     * 单位id
     */
    @Getter
    private List<String> unitIds;
    /**
     * 用户类型
     */
    @Getter
    @Setter
    private UserTypeEnum userType;

    @Getter
    @Setter
    private Collection<GrantedAuthority> authorities;

    /**
     * 用户Token
     */
    @Getter
    @Setter
    private String token;

    public JeeUser(Long id, String loginName, String username, String password, List<String> orgIds,
                   List<String> unitIds, UserTypeEnum userType, boolean enabled, boolean accountNonExpired,
                   boolean credentialsNonExpired, boolean accountNonLocked,
                   Collection<? extends GrantedAuthority> authorities) {
        super(loginName, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.userId = id;
        this.loginName = loginName;
        // this.userName = username;
        this.orgIds = orgIds;
        this.unitIds = unitIds;
        //  this.userType = userType;
        this.authorities = super.getAuthorities();
    }
}
