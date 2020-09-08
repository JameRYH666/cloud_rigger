package com.jeerigger.security;

import com.jeerigger.frame.util.StringUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.PatternMatchUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;

public abstract class BasePermissionService implements PermissionService {


    @Override
    public boolean hasPermission(String permission) {
        if (StringUtil.isEmpty(permission)) {
            return false;
        }
        Authentication authentication = SecurityUtil.getAuthentication();
        if (authentication == null) {
            return false;
        }
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        boolean hasPermission = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .filter(StringUtils::hasText)
                .anyMatch(x -> PatternMatchUtils.simpleMatch(permission, x));
        if (!hasPermission) {
            hasPermission = readUpdatePermission(permission);
        }
        return hasPermission;
    }

    @Override
    public abstract boolean readUpdatePermission(String permission);
}
