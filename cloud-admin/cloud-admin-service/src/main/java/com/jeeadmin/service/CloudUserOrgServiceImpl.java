package com.jeeadmin.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jeeadmin.api.ICloudUserOrgService;
import com.jeeadmin.api.ICloudOrgService;
import com.jeeadmin.entity.CloudOrg;
import com.jeeadmin.entity.CloudUserOrg;
import com.jeeadmin.mapper.CloudUserOrgMapper;
import com.jeerigger.core.common.core.SnowFlake;
import com.jeerigger.frame.base.service.impl.BaseServiceImpl;
import com.jeerigger.frame.exception.ValidateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author Seven Lee
 * @description
 *      机构管理员  组织机构分配表 服务实现类
 * @date 2020/9/9
**/
@Service
public class CloudUserOrgServiceImpl extends BaseServiceImpl<CloudUserOrgMapper, CloudUserOrg> implements ICloudUserOrgService {

    @Autowired
    private ICloudOrgService sysOrgService;
    @Autowired
    private SnowFlake snowFlake;

    @Override
    public boolean saveOrgAdminOrg(List<CloudUserOrg> sysOrgAdminOrgList) {
        return this.saveBatch(sysOrgAdminOrgList);
    }

    /**
     * 新增用户党组织信息
     * 在新增用户之后，同时新增党员信息
     * @param cloudUserOrg
     * @return
     */
    @Override
    public boolean saveOrgUser(CloudUserOrg cloudUserOrg) {
        if (Objects.isNull(cloudUserOrg)){
            throw new ValidateException("用户党组织信息不能为空");
        }
        if (Objects.isNull(cloudUserOrg.getOrgId())){
            throw new ValidateException("党组织id不能为空" );
        }
        cloudUserOrg.setCreateDate(new Date())
                // TODO 这里创建用户先写死，其实应从security中获取
                .setCreateUser(1L)
                .setId(snowFlake.nextId());
       return this.save(cloudUserOrg);

    }

    @Override
    public boolean deleteOrgAdminOrg(Long userId) {
        if (Objects.nonNull(userId)) {
            QueryWrapper<CloudUserOrg> whereWrapper = new QueryWrapper<>();
            whereWrapper.lambda().eq(CloudUserOrg::getUserId, userId);
            return this.remove(whereWrapper);
        } else {
            return true;
        }
    }

    @Override
    public List<CloudUserOrg> detailOrgList(Long userId) {
        QueryWrapper<CloudUserOrg> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(CloudUserOrg::getUserId, userId);
        List<CloudUserOrg> sysOrgAdminOrgList = this.list(queryWrapper);
        List<CloudUserOrg> orgAdminOrgList = new ArrayList<CloudUserOrg>();
        for (CloudUserOrg sysOrgAdminOrg : sysOrgAdminOrgList) {
            QueryWrapper<CloudOrg> queryOrgWrapper = new QueryWrapper<CloudOrg>();
            queryOrgWrapper.lambda().eq(CloudOrg::getParentId, sysOrgAdminOrg.getOrgId());
            if (sysOrgService.count(queryOrgWrapper) < 1) {
                orgAdminOrgList.add(sysOrgAdminOrg);
            }
        }
        return orgAdminOrgList;
    }

    @Override
    public CloudUserOrg selectOrgByUserId() {

            // todo 根据用户id获取到所在党支部的信息
            //  Long userId = SecurityUtil.getUserId();
            Long userId = 1L;
            QueryWrapper<CloudUserOrg> queryWrapper = new QueryWrapper<>();
            // 通过用户id获取到党支部的信息
            queryWrapper.lambda().eq(CloudUserOrg::getUserId,userId);
            // 转换为党支部用户
             CloudUserOrg cloudUserOrg = this.getOne(queryWrapper);
             // 判断是否获取到数据
            if (Objects.isNull(cloudUserOrg)){
                throw new ValidateException("没有该用户的党支部信息");
            }
            return cloudUserOrg;


    }

    @Override
    public List<CloudUserOrg> selectOrgByOrgId(Long orgId) {
        QueryWrapper<CloudUserOrg> queryWrapper = new QueryWrapper<>();
        if (Objects.isNull(orgId)){
            throw new ValidateException("党组织id不能为空");
        }
        queryWrapper.lambda().eq(CloudUserOrg::getOrgId,orgId);
        List<CloudUserOrg> cloudUserOrgs = this.list(queryWrapper);
        if (null != cloudUserOrgs && cloudUserOrgs.size()>0){
            return cloudUserOrgs;
        }
        throw new ValidateException("查询不到党组织信息");
    }
}
