package com.jeerigger.frame.mybatis.annotation;

import java.lang.annotation.*;

/**
 * 用于核心扫描使用
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MapperSource { }
