package com.jeeadmin.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeadmin.entity.CloudEnclosure;
import com.jeeadmin.entity.CloudNotice;
import com.jeeadmin.vo.notice.CloudNoticeVo;
import com.jeerigger.frame.base.service.BaseService;
import com.jeerigger.frame.page.PageHelper;

import java.util.List;

/**
* @Author: Ryh
* @Description:     通知公告的实现类
* @Param: [null]
* @Date: Create in 2020/9/15
* @Return: null
* @Throws:
*/
public interface ICloudNoticeService extends BaseService<CloudNotice> {

    /**
     *  查询所有的通知公告
     */
    Page<CloudNoticeVo> selectPage(PageHelper<CloudNoticeVo> pageHelper);

    /**
     *  查询当个的通知公告带附件信息
     */
    CloudNoticeVo selectOneNotice(Long id);

    /**
     *  更新通知公告
     */
    boolean updateNotice(CloudNotice cloudNotice);

    /**
     *  新增通知公告
     */
    CloudNoticeVo saveNotice(CloudNoticeVo cloudNotice);

    /**
     *  修改通知公告状态(逻辑删除)
     */
    boolean updateStatus(CloudNotice cloudNotice);

    /**
     *  通过公告ID查询对应的附件信息
     */
    List<CloudEnclosure> selectByNoticeId(Long id);


}
