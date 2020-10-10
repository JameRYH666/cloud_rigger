package com.zskj.engine.quartz.core.impl;

import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.SchedulerException;
import org.quartz.Trigger;

import java.util.List;
import java.util.Map;


public interface EngineQuartzScheduler {

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
    void startJob(String jobName, String jobGroupName, Class<? extends Job> jobClass,
                  Map<String, Object> params, String triggerName, String triggerGroupName, String cron) throws SchedulerException;

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
    void startJob(String jobName, String jobGroupName, Class<? extends Job> jobClass,
                  Map<String, Object> params, Trigger trigger) throws SchedulerException;

    /**
     * 启动一个Job
     *
     * @param jobDetail
     * @param triggerName
     * @param triggerGroupName
     * @param cron
     * @throws SchedulerException
     */
    void startJob(JobDetail jobDetail, String triggerName, String triggerGroupName, String cron) throws SchedulerException;


    /**
     * 启动一个Job
     *
     * @param jobDetail
     * @param trigger
     * @throws SchedulerException
     */
    void startJob(JobDetail jobDetail, Trigger trigger) throws SchedulerException;


    /**
     * 暂停一个job
     *
     * @param jobName
     * @param jobGroupName
     * @throws SchedulerException
     */
    void pauseJob(String jobName, String jobGroupName) throws SchedulerException;


    /**
     * 恢复一个job
     *
     * @param jobName
     * @param jobGroupName
     * @throws SchedulerException
     */
    void resumeJob(String jobName, String jobGroupName) throws SchedulerException;

    /**
     * 删除一个job
     *
     * @param jobName
     * @param jobGroupName
     * @throws SchedulerException
     */
    void deleteJob(String jobName, String jobGroupName) throws SchedulerException;


    /**
     * 立即执行job
     *
     * @param jobName
     * @param jobGroupName
     * @throws SchedulerException
     */
    void runJobNow(String jobName, String jobGroupName) throws SchedulerException;

    /**
     * 更新job时间表达式
     *
     * @param triggerName
     * @param triggerGroupName
     * @param cron
     * @throws SchedulerException
     */
    void updateTriggerCron(String triggerName, String triggerGroupName, String cron) throws SchedulerException;

    /**
     * 获取正在运行的Job。注意：是正在执行的job。
     */
    List<Map<String, Object>> getRunningJob() throws SchedulerException;
}
