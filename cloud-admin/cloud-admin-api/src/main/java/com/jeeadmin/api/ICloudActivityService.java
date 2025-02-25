package com.jeeadmin.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeadmin.entity.CloudActivity;
import com.jeeadmin.vo.activity.CloudActivityVo;
import com.jeerigger.frame.base.service.BaseService;
import com.jeerigger.frame.page.PageHelper;

import java.util.List;

/**
 * @Author: Ryh
 * @Description: 活动信息表的实现类
 * @Param: [null]
 * @Date: Create in 2020/9/9
 * @Return: null
 * @Throws:
 */
public interface ICloudActivityService extends BaseService<CloudActivity> {

    /**
     *   获取活动信息
     * @param pageHelper
     */
    Page<CloudActivity> selectPage(PageHelper<CloudActivity> pageHelper);
    /**
     * 新增活动数据
     */
    CloudActivityVo saveActivity(CloudActivityVo activity);
    /**
     * 修改活动信息
     */
    boolean updateActivity(CloudActivity activity);
    /**
     * 删除活动信息
     */
    boolean deleteActivity(Long id);

    /**
     *  修改活动的状态(逻辑删除)
     */
    boolean updateStatus(CloudActivity cloudActivity);

    /**
     *  根据用户ID查询已经发起的活动
     */
    Page<CloudActivity> selectByUserId(PageHelper<CloudActivity> pageHelper);

    /**
     *  根据用户id查询已经处理的活动
     */
    Page<CloudActivity> selectProcessed(PageHelper<CloudActivity> pageHelper);

    /**
     *  根据用户id查询未处理的活动
     */
    Page<CloudActivity> selectUntreated(PageHelper<CloudActivity> pageHelper);

    /**
     *  根据党员ID查询需要处理的活动
     */
    Page<CloudActivity> selectUntreatedByPartyMemberId(PageHelper<CloudActivity> pageHelper);

}
