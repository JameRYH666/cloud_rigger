package com.jeeadmin.mapper;

import com.jeeadmin.entity.CloudNoticeEnclosure;
import com.jeerigger.frame.base.mapper.BaseMapper;

import java.util.List;
/**
* @Author: Ryh
* @Description:     公告附件mapper接口类
* @Param: [null]
* @Date: Create in 2020/9/16
* @Return: null
* @Throws:
*/
public interface CloudNoticeEnclosureMapper extends BaseMapper<CloudNoticeEnclosure> {

    /**
     *  根据ID查询公告附件信息
     */
    List<CloudNoticeEnclosure> selectByNoticeId(Long id);


}