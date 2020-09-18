package com.jeeadmin.mapper;

import com.jeeadmin.entity.CloudEnclosure;
import com.jeeadmin.entity.CloudNotice;
import com.jeeadmin.vo.notice.CloudNoticeVo;
import com.jeerigger.frame.base.mapper.BaseMapper;

import java.util.List;

/**
* @Author: Ryh
* @Description:     通知公告mapper接口
* @Param: [null]
* @Date: Create in 2020/9/15
* @Return: null
* @Throws:
*/
public interface CloudNoticeMapper extends BaseMapper<CloudNotice> {

    /**
     *          查询所有正常的通知公告
     * @return
     */
    List<CloudNoticeVo> selectAllNotice(List<Long> ids);

    /**
     *  根据id查询公告
     */
    CloudNoticeVo selectOne(Long id);

    /**
     *          通过通知公告ID查询附件信息
     */
    List<CloudEnclosure> selectEnclosuresByNoticeId(Long id);

    /**
     *          通过公告ID查询具体的详细的公告信息
     */
    List<CloudNoticeVo> selectOneNotice(Long id);


}