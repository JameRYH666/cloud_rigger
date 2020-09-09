package com.jeeadmin.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jeeadmin.api.ICloudUserOrgService;
import com.jeeadmin.api.ICloudOrgService;
import com.jeeadmin.entity.CloudOrg;
import com.jeeadmin.entity.CloudUserOrg;
import com.jeeadmin.mapper.CloudUserOrgMapper;
import com.jeerigger.frame.base.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Override
    public boolean saveOrgAdminOrg(List<CloudUserOrg> sysOrgAdminOrgList) {
        return this.saveBatch(sysOrgAdminOrgList);
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
}
