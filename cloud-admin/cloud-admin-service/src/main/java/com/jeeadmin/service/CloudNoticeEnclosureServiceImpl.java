package com.jeeadmin.service;

import com.jeeadmin.api.ICloudNoticeEnclosureService;
import com.jeeadmin.entity.CloudNoticeEnclosure;
import com.jeeadmin.mapper.CloudNoticeEnclosureMapper;
import com.jeerigger.core.common.core.SnowFlake;
import com.jeerigger.frame.base.service.impl.BaseServiceImpl;
import com.jeerigger.frame.exception.ValidateException;
import com.jeerigger.frame.support.validate.ValidateUtil;
import com.jeerigger.security.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.Objects;

/**
 * @Company YXH
 * @Author ryh
 * @Date Create in 2020/9/16 17:48
 * @Description:
 */
@Service
public class CloudNoticeEnclosureServiceImpl extends BaseServiceImpl<CloudNoticeEnclosureMapper, CloudNoticeEnclosure> implements ICloudNoticeEnclosureService {

    @Autowired
    private SnowFlake snowFlake;
    /**
    * @Author: Ryh
    * @Description:                 保存公告附件的关系
    * @Param: [cloudNoticeEnclosure]
    * @Date: Create in 2020/9/16
    * @Return: com.jeeadmin.entity.CloudNoticeEnclosure
    * @Throws:
    */
    @Override
    public CloudNoticeEnclosure saveNoticeEnclosure(CloudNoticeEnclosure cloudNoticeEnclosure) {
        ValidateUtil.validateObject(cloudNoticeEnclosure);
        cloudNoticeEnclosure.setId(snowFlake.nextId());
        cloudNoticeEnclosure.setCreateDate(new Date());
        cloudNoticeEnclosure.setCreateUser(SecurityUtil.getUserId());
        if (this.save(cloudNoticeEnclosure)){
            return cloudNoticeEnclosure;
        }else {
            throw new ValidateException("新增公告附件关系失败");
        }
    }





}
