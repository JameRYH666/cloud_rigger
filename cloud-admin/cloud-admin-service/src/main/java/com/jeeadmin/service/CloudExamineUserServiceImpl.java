package com.jeeadmin.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jeeadmin.api.ICloudExamineUserService;
import com.jeeadmin.api.ICloudOrgService;
import com.jeeadmin.api.ICloudPartyMemberService;
import com.jeeadmin.entity.CloudExamineUser;
import com.jeeadmin.entity.CloudOrg;
import com.jeeadmin.entity.CloudPartyMember;
import com.jeeadmin.mapper.CloudExamineUserMapper;
import com.jeerigger.core.common.core.SnowFlake;
import com.jeerigger.frame.base.service.impl.BaseServiceImpl;
import com.jeerigger.frame.exception.ValidateException;
import com.jeerigger.security.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Service
public class CloudExamineUserServiceImpl extends BaseServiceImpl<CloudExamineUserMapper,CloudExamineUser> implements ICloudExamineUserService {

    @Autowired
    private SnowFlake snowFlake;

    @Autowired
    private ICloudOrgService cloudOrgService;

    @Autowired
    private ICloudPartyMemberService cloudPartyMemberService;

    /**
     * 新增审核用户关系
     * @param cloudExamineUser
     * @return
     */
    @Transactional(rollbackFor = ValidateException.class)
    @Override
    public CloudExamineUser saveExamineUser(CloudExamineUser cloudExamineUser) {
        //主键id
        cloudExamineUser.setId(snowFlake.nextId());
        Long userId = /*SecurityUtil.getUserId()*/1L;
        //通过用户id查询党员信息
        CloudPartyMember partyMember = cloudPartyMemberService.getPartyMemberByUserId(userId);
        if (StringUtils.isEmpty(partyMember)){
            throw new ValidateException("党员信息为空");
        }
        //该党员所在党支部的信息
        CloudOrg cloudOrg = cloudOrgService.detailOrg(partyMember.getOrgId());
        if (StringUtils.isEmpty(cloudOrg)){
            throw new ValidateException("党支部信息为空");
        }
        //获取当前党支部的负责人党员id
        Long orgPartyMemberId = cloudOrg.getOrgPartyMemberId();
        //该负责人的党员id就是审核人id
        cloudExamineUser.setPartyMemberId(orgPartyMemberId);
        cloudExamineUser.setCreateUser(userId);
        cloudExamineUser.setCreateDate(new Date());
        if(this.save(cloudExamineUser)){
            return cloudExamineUser;
        }else {
            throw new ValidateException("新增审核用户关系失败");
        }
    }

    /**
     * 根据审核人id查询该审核人需要审核的数据的集合
     * @param partMemberId
     * @return
     */
    @Override
    public List<CloudExamineUser> selectByPartMemberId(Long partMemberId) {
        LambdaQueryWrapper<CloudExamineUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CloudExamineUser::getPartyMemberId,partMemberId);
        return this.list(wrapper);
    }

    /**
     * 通过examine_id删除用户审核关系表中的数据
     * @param examineId
     * @return
     */
    @Override
    public boolean deleteByExamineId(Long examineId) {
        LambdaQueryWrapper<CloudExamineUser> wrapper = new LambdaQueryWrapper();
        wrapper.eq(CloudExamineUser::getExamineId,examineId);
        CloudExamineUser examineUser = this.getOne(wrapper);
        if (null == examineUser){
            return true;
        }
        return this.removeById(examineUser.getId());
    }

}
