package com.zskj.dap.jobs;

import com.zskj.dap.jobs.properties.JobCronProperties;
import com.zskj.engine.quartz.core.impl.EngineQuartzScheduler;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDetail;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @projectName: dap
 * @packageName: com.zskj.demo.controller
 * @description:
 * @author: chen
 * @date: 2020/7/22 17:15
 */
@Component
@Slf4j
public class QuartzJobManager {

    @Autowired(required = false)
    private EngineQuartzScheduler engineQuartzScheduler;
    /**
     * 注入所有的job
     */
    @Autowired(required = false)
    private List<JobDetail> jobDetails;

    @Autowired
    private JobCronProperties jobCronProperties;

    /**
     * spring中，bean的生命周期方法
     */
    @PostConstruct
    public void initDapJobs() {
        if (engineQuartzScheduler != null && jobDetails != null && jobDetails.size() > 0) {
            String triggerName = jobCronProperties != null && Objects.isNull(jobCronProperties.getTriggerName()) ?
                    jobCronProperties.DEFAULT_TRIGGER_NAME : jobCronProperties.getTriggerName();
            String triggerGroupName =
                    jobCronProperties != null && Objects.isNull(jobCronProperties.getTriggerGroupName()) ?
                            jobCronProperties.DEFAULT_TRIGGER_GROUP_NAME : jobCronProperties.getTriggerGroupName();
            for (JobDetail jobDetail : jobDetails) {
                String jobName = jobDetail.getKey().getName();
                if (jobCronProperties != null && jobCronProperties.getCron() != null && jobCronProperties.getCron().containsKey(jobName)) {
                    try {
                        long epochSecond = LocalDateTime.now().toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
                        engineQuartzScheduler.startJob(jobDetail, triggerName + "_" + epochSecond,
                                triggerGroupName + "_" + epochSecond, jobCronProperties.getCron().get(jobName));
                        log.info("{}任务添加成功，执行时间是:{}", jobName, jobCronProperties.getCron().get(jobName));
                        // 每个任务添加完成之后，线程休息1秒
                        TimeUnit.SECONDS.sleep(1);
                    } catch (SchedulerException | InterruptedException e) {
                        log.error("添加任务失败:{}", e.getMessage());
                    }
                }
            }
        }
    }

}
