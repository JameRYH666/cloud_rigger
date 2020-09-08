package com.zskj.engine.quartz.config;

import org.quartz.spi.JobFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * @projectName: dap
 * @packageName: com.zskj.engine.quartz.config
 * @description:
 * @author: huayang.bai
 * @date: 2020/7/22 17:06
 */
@Configuration
public class QuartzConfig {

    @Autowired
    private JobFactory jobFactory;

    /**
     * 定义调度器工厂
     *
     * @return
     */
    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setJobFactory(jobFactory);
        return schedulerFactoryBean;
    }
}
