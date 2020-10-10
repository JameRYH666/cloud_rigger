package com.zskj.engine.quartz.core.impl;

import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.zskj.engine.quartz.core.impl.EngineQuartzScheduler;
import java.util.*;


@Component
public class DefaultQuartzScheduler implements EngineQuartzScheduler {

    @Autowired
    private Scheduler scheduler;

    /**
     * 启动一个Job
     *
     * @param jobName
     * @param jobGroupName
     * @param jobClass
     * @param params
     * @param triggerName
     * @param triggerGroupName
     * @param cron
     * @throws SchedulerException
     */
    @Override
    public void startJob(String jobName, String jobGroupName, Class<? extends Job> jobClass,
                         Map<String, Object> params, String triggerName, String triggerGroupName, String cron) throws SchedulerException {
        vaildateParameter("jobName", jobName);
        vaildateParameter("jobGroupName", jobGroupName);
        vaildateParameter("jobClass", jobClass);
        vaildateParameter("triggerName", triggerName);
        vaildateParameter("triggerGroupName", triggerGroupName);
        vaildateParameter("cron", cron);
        this.startJob(createJobDetail(jobName, jobGroupName, jobClass, params),
                createTrigger(triggerName, triggerGroupName, cron));
    }

    /**
     * 启动一个job
     *
     * @param jobName
     * @param jobGroupName
     * @param jobClass
     * @param params
     * @param trigger
     * @throws SchedulerException
     */
    @Override
    public void startJob(String jobName, String jobGroupName, Class<? extends Job> jobClass,
                         Map<String, Object> params, Trigger trigger) throws SchedulerException {
        vaildateParameter("jobName", jobName);
        vaildateParameter("jobGroupName", jobGroupName);
        vaildateParameter("jobClass", jobClass);
        vaildateParameter("trigger", trigger);
        this.startJob(createJobDetail(jobName, jobGroupName, jobClass, params), trigger);
    }

    /**
     * 启动一个Job
     *
     * @param jobDetail
     * @param triggerName
     * @param triggerGroupName
     * @param cron
     * @throws SchedulerException
     */
    @Override
    public void startJob(JobDetail jobDetail, String triggerName, String triggerGroupName, String cron) throws SchedulerException {
        vaildateParameter("jobDetail", jobDetail);
        vaildateParameter("triggerName", triggerName);
        vaildateParameter("triggerGroupName", triggerGroupName);
        vaildateParameter("cron", cron);
        this.startJob(jobDetail, createTrigger(triggerName, triggerGroupName, cron));
    }


    /**
     * 启动一个Job
     *
     * @param jobDetail
     * @param trigger
     * @throws SchedulerException
     */
    @Override
    public void startJob(JobDetail jobDetail, Trigger trigger) throws SchedulerException {
        vaildateParameter("jobDetail", jobDetail);
        vaildateParameter("trigger", trigger);
        scheduler.scheduleJob(jobDetail, trigger);
        if (!scheduler.isShutdown()) {
            scheduler.start();
        }
    }

    /**
     * 创建JobDetail
     *
     * @param jobName
     * @param jobGroupName
     * @param jobClass
     * @param params
     * @return
     */
    private JobDetail createJobDetail(String jobName, String jobGroupName, Class<?
            extends Job> jobClass, Map<String, Object> params) {
        vaildateParameter("jobName", jobName);
        vaildateParameter("jobGroupName", jobGroupName);
        // 任务名，任务组，任务执行类
        JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobName, jobGroupName).build();
        // 任务参数
        if (params != null && !params.isEmpty()) {
            jobDetail.getJobDataMap().putAll(params);
        }
        return jobDetail;
    }


    /**
     * 构建触发器
     *
     * @param triggerName
     * @param triggerGroupName
     * @param cron
     * @return
     */
    private CronTrigger createTrigger(String triggerName, String triggerGroupName,
                                      String cron) {
        vaildateParameter("triggerName", triggerName);
        vaildateParameter("triggerGroupName", triggerGroupName);
        vaildateParameter("cron", cron);
        TriggerBuilder<CronTrigger> triggerBuilder = TriggerBuilder.newTrigger()
                .withIdentity(triggerName, triggerGroupName) // 触发器名,触发器组
                .startNow()// 构建完成立即启动
                .withSchedule(CronScheduleBuilder.cronSchedule(cron)); // 触发器时间设定
        return triggerBuilder.build(); // 构建Trigger对象
    }


    /**
     * 暂停一个job
     *
     * @param jobName
     * @param jobGroupName
     * @throws SchedulerException
     */
    @Override
    public void pauseJob(String jobName, String jobGroupName) throws SchedulerException {
        vaildateParameter("jobName", jobName);
        vaildateParameter("jobGroupName", jobGroupName);
        scheduler.pauseJob(getJobKey(jobName, jobGroupName));
    }


    /**
     * 恢复一个job
     *
     * @param jobName
     * @param jobGroupName
     * @throws SchedulerException
     */
    @Override
    public void resumeJob(String jobName, String jobGroupName) throws SchedulerException {
        vaildateParameter("jobName", jobName);
        vaildateParameter("jobGroupName", jobGroupName);
        scheduler.resumeJob(getJobKey(jobName, jobGroupName));
    }

    /**
     * 删除一个job
     *
     * @param jobName
     * @param jobGroupName
     * @throws SchedulerException
     */
    @Override
    public void deleteJob(String jobName, String jobGroupName) throws SchedulerException {
        vaildateParameter("jobName", jobName);
        vaildateParameter("jobGroupName", jobGroupName);
        scheduler.deleteJob(getJobKey(jobName, jobGroupName));

    }


    /**
     * 立即执行job
     *
     * @param jobName
     * @param jobGroupName
     * @throws SchedulerException
     */
    @Override
    public void runJobNow(String jobName, String jobGroupName) throws SchedulerException {
        vaildateParameter("jobName", jobName);
        vaildateParameter("jobGroupName", jobGroupName);
        scheduler.triggerJob(getJobKey(jobName, jobGroupName));
    }


    /**
     * 更新job时间表达式
     *
     * @param triggerName
     * @param triggerGroupName
     * @param cron
     * @throws SchedulerException
     */
    @Override
    public void updateTriggerCron(String triggerName, String triggerGroupName, String cron) throws SchedulerException {
        vaildateParameter("triggerName", triggerName);
        vaildateParameter("triggerGroupName", triggerGroupName);
        vaildateParameter("cron", cron);
        scheduler.rescheduleJob(TriggerKey.triggerKey(triggerName, triggerGroupName), createTrigger(triggerName,
                triggerGroupName, cron));
    }

    /**
     * 获取正在运行的Job。注意：是正在执行的job。
     */
    @Override
    public List<Map<String, Object>> getRunningJob() throws SchedulerException {
        List<Map<String, Object>> resultList = new ArrayList<>();
        List<JobExecutionContext> executingJobs = scheduler.getCurrentlyExecutingJobs();
        for (JobExecutionContext executingJob : executingJobs) {
            Map<String, Object> jobMap = new HashMap<>();
            JobDetail jobDetail = executingJob.getJobDetail();
            Trigger trigger = executingJob.getTrigger();
            JobKey jobKey = jobDetail.getKey();
            jobMap.put("jobName", jobKey.getName());
            jobMap.put("jobGroupName", jobKey.getGroup());
            TriggerKey triggerKey = trigger.getKey();
            jobMap.put("triggerName", triggerKey.getName());
            jobMap.put("triggerGroupName", triggerKey.getGroup());
            CronTrigger cronTrigger = (CronTrigger) trigger;
            jobMap.put("cronExpression", cronTrigger.getCronExpression());
            jobMap.put("jobDataMap", jobDetail.getJobDataMap());
            resultList.add(jobMap);
        }
        return resultList;
    }

    /**
     * 通过jobName和jobGroupName获取一个JobKey
     *
     * @param jobName
     * @param jobGroupName
     * @return
     */
    private JobKey getJobKey(String jobName, String jobGroupName) {
        return JobKey.jobKey(jobName, jobGroupName);
    }

    /**
     * 验证参数不能为空
     *
     * @param parameterName
     * @param parameterValue
     */
    private void vaildateParameter(String parameterName, Object parameterValue) {
        if (Objects.isNull(parameterValue)) {
            throw new IllegalArgumentException("The " + parameterName + " parameter cannot be empty!");
        }
    }

}
