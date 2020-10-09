package com.jeerigger.security;

import com.jeerigger.security.handler.*;
import com.jeerigger.security.service.JeeUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static com.jeerigger.frame.enums.UserParamEnum.*;
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
        auth.userDetailsService(userDetailsService).passwordEncoder(NoOpPasswordEncoder.getInstance());
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
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        //修改为添加而不是设置，* 最好改为实际的需要，我这是非生产配置，所以粗暴了一点
        corsConfiguration.addAllowedOrigin("*");
        //修改为添加而不是设置
        corsConfiguration.addAllowedMethod("*");
        //这里很重要，起码需要允许 Access-Control-Allow-Origin
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource corsConfigurationSource = new
                UrlBasedCorsConfigurationSource();
        corsConfigurationSource.registerCorsConfiguration("/**",
                corsConfiguration);

        http.cors().configurationSource(corsConfigurationSource);
        // 以上是跨域问题

        http.csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(jeeAuthenticationEntryPoint)
                .accessDeniedHandler(deniedHandler)
                .and().authorizeRequests()
                .antMatchers(splitclearSpace(securityConfig.getIgnoring())).permitAll()
                .anyRequest().authenticated().and()
                .formLogin()
                .loginPage(securityConfig.getLoginPage())
                .usernameParameter(LOGIN_NAME.getParamValue())
                .passwordParameter(PASSWORD.getParamValue())
                .loginProcessingUrl(securityConfig.getLoginProcessingUrl())
                .failureHandler(failureHandler)
                .successHandler(successHandler)
                .permitAll().and()
                .logout().logoutSuccessHandler(logoutSuccessHandler).permitAll();
    }

}
