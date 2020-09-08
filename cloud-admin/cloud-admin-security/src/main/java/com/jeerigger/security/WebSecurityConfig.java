package com.jeerigger.security;

import com.jeerigger.security.handler.*;
import com.jeerigger.security.service.JeeUserDetailsService;
import com.jeerigger.security.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Objects;

import static com.jeerigger.security.StringUtil.splitclearSpace;

/**
 * Created by sang on 2017/12/28.
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Lazy
    private JeeUserDetailsService userDetailsService;
    @Autowired
    private JeeAuthenticationAccessDeniedHandler deniedHandler;
    @Autowired
    private JeeAuthenticationFailureHandler failureHandler;
    @Autowired
    private JeeAuthenticationSuccessHandler successHandler;
    @Autowired
    private JeeLogoutSuccessHandler logoutSuccessHandler;
    @Autowired
    private JeeAuthenticationEntryPoint jeeAuthenticationEntryPoint;
    @Autowired
    private SecurityConfig securityConfig;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                // 通过Md5对表单提交的密码进行加密
                return MD5Util.encode((String) rawPassword);
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                // 因为表单提交的密码并没有加密。所以，这里需要对表单提交的密码进行加密
                String formPasswd = MD5Util.encode((String) rawPassword);
                return Objects.equals(formPasswd, encodedPassword);
            }

            @Override
            public boolean upgradeEncoding(String encodedPassword) {
                return true;
            }
        };
    }


    @Override
    public void configure(WebSecurity webSecurity) {
        if (StringUtil.isNotEmpty(securityConfig.getIgnoring())) {
            // 配置忽略的地址
            webSecurity.ignoring().antMatchers(splitclearSpace(securityConfig.getIgnoring()));
        }
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().exceptionHandling().authenticationEntryPoint(jeeAuthenticationEntryPoint).accessDeniedHandler(deniedHandler)
                .and().authorizeRequests()
                .antMatchers(splitclearSpace(securityConfig.getIgnoring())).permitAll()
                .anyRequest().authenticated().and()
                .formLogin().loginPage(securityConfig.getLoginPage()).loginProcessingUrl(securityConfig.getLoginProcessingUrl())
                .failureHandler(failureHandler)
                .successHandler(successHandler)
                .permitAll().and()
                .logout().logoutSuccessHandler(logoutSuccessHandler).permitAll();
    }

}
