package com.jeerigger.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * security配置属性读入变成bean
 */
@Component
@ConfigurationProperties(prefix = "spring.security")
public class SecurityConfig {

    private String ignoring;
    private String logoutSuccessUrl;
    private String loginProcessingUrl;
    private String loginPage;
    private String logoutUrl;

    public String getIgnoring() {
        return ignoring;
    }

    public void setIgnoring(String ignoring) {

        this.ignoring = ignoring;
    }

    public String getLogoutSuccessUrl() {
        return logoutSuccessUrl;
    }

    public void setLogoutSuccessUrl(String logoutSuccessUrl) {
        this.logoutSuccessUrl = logoutSuccessUrl;
    }

    public String getLoginProcessingUrl() {
        return loginProcessingUrl;
    }

    public void setLoginProcessingUrl(String loginProcessingUrl) {
        this.loginProcessingUrl = loginProcessingUrl;
    }

    public String getLoginPage() {
        return loginPage;
    }

    public void setLoginPage(String loginPage) {
        this.loginPage = loginPage;
    }

    public String getLogoutUrl() {
        return logoutUrl;
    }

    public void setLogoutUrl(String logoutUrl) {
        this.logoutUrl = logoutUrl;
    }
}
