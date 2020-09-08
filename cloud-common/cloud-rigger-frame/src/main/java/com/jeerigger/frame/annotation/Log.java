package com.jeerigger.frame.annotation;


import com.jeerigger.frame.enums.LogTypeEnum;

import java.lang.annotation.*;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Log {
    /**
     * 日志类型
     */
    LogTypeEnum logType();

    /**
     * 日志标题
     */
    String logTitle() default "";
}
