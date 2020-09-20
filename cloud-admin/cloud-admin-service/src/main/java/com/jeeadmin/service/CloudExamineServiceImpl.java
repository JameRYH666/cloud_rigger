package com.jeeadmin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeadmin.api.ICloudExamineService;
import com.jeeadmin.api.ICloudExamineUserService;
import com.jeeadmin.entity.CloudExamine;
import com.jeeadmin.entity.CloudExamineUser;
import com.jeeadmin.mapper.CloudExamineMapper;
import com.jeerigger.core.common.core.SnowFlake;
import com.jeerigger.frame.base.service.impl.BaseServiceImpl;
import com.jeerigger.frame.enums.MeetingAndActivityEnum;
import com.jeerigger.frame.exception.ValidateException;
import com.jeerigger.frame.page.PageHelper;
import com.jeerigger.security.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @Company YXH
 * @Author ryh
 * @Date Create in 2020/9/19 10:54
 * @Description:
 */
public class CloudExamineServiceImpl extends BaseServiceImpl<CloudExamineMapper, CloudExamine> implements ICloudExamineService {

   @Autowired
   private SnowFlake snowFlake;
   @Autowired
   private ICloudExamineUserService cloudExamineUserService;


    @Override
    public Page<CloudExamine> selectAll(PageHelper<CloudExamine> cloudExmaine) {
        return null;
    }

    @Override
    public CloudExamine getDetail(CloudExamine cloudExamine) {
        return null;
    }

    /**
    * @Author: Ryh
    * @Description:         新增审核信息
    * @Param: [cloudExmaine]
    * @Date: Create in 2020/9/19
    * @Return: com.jeeadmin.entity.CloudExmaine
    * @Throws:
    */
    @Override
    public CloudExamine saveExamine(CloudExamine cloudExamine) {
        cloudExamine.setId(snowFlake.nextId());
        cloudExamine.setExamineStatus(MeetingAndActivityEnum.NOREVIEWED.getCode());
        cloudExamine.setCreateDate(new Date());
        cloudExamine.setCreateUser(SecurityUtil.getUserId());
        // 创建审核用户关系
        CloudExamineUser cloudExamineUser = new CloudExamineUser();
        cloudExamineUser.setExamineId(cloudExamine.getId());
        cloudExamineUser.setId(snowFlake.nextId());
        // todo 根据当前用户的ID获取上级用户的ID    先写死
        Long userId = SecurityUtil.getUserId();
        cloudExamineUser.setId(SecurityUtil.getUserId());

        cloudExamineUser.setCreateUser(SecurityUtil.getUserId());
        cloudExamineUser.setCreateDate(new Date());
        cloudExamineUserService.saveExamineUser(cloudExamineUser);
        if (this.save(cloudExamine)){
            return cloudExamine;
        }else {
            throw new ValidateException("新增审核数据失败");
        }
    }



    @Override
    public boolean updateStatus(CloudExamine cloudExamine) {
        return false;
    }
}
