package com.zskj.dap.jobs.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @projectName: dap
 * @packageName: com.zskj.dap.jobs.properties
 * @description:
 * @author: huayang.bai
 * @date: 2020/8/21 9:43
 */
@Component
@ConfigurationProperties(prefix = "dap.job.time")
public class JobCronProperties {

    public final String DEFAULT_TRIGGER_GROUP_NAME = "triggerGroupName";

    public final String DEFAULT_TRIGGER_NAME = "triggerName";

    /**
     * 配置job和cron之间对应关系，key为job对应的Bean的beanName，value为cron表达式
     */
    private Map<String, String> cron;

    private String triggerGroupName;

    private String triggerName;


    public Map<String, String> getCron() {
        return cron;
    }

    public void setCron(Map<String, String> cron) {
        this.cron = cron;
    }

    public String getTriggerGroupName() {
        return triggerGroupName;
    }

    public void setTriggerGroupName(String triggerGroupName) {
        this.triggerGroupName = triggerGroupName;
    }

    public String getTriggerName() {
        return triggerName;
    }

    public void setTriggerName(String triggerName) {
        this.triggerName = triggerName;
    }
}
