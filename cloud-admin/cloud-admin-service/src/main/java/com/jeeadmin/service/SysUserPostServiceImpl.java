package com.jeeadmin.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jeeadmin.api.ISysUserPostService;
import com.jeeadmin.entity.SysUserPost;
import com.jeeadmin.mapper.SysUserPostMapper;
import com.jeerigger.frame.base.service.impl.BaseServiceImpl;
import com.jeerigger.frame.util.StringUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author wangcy
 * @since 2019-01-22
 */
@Service
public class SysUserPostServiceImpl extends BaseServiceImpl<SysUserPostMapper, SysUserPost> implements ISysUserPostService {

    @Override
    public boolean saveUserPost(List<SysUserPost> sysUserPostList) {
        return this.saveBatch(sysUserPostList);
    }

    @Override
    public boolean deleteUserPost(Long userId) {
        if (Objects.nonNull(userId)) {
            QueryWrapper<SysUserPost> whereWrapper = new QueryWrapper<>();
            whereWrapper.lambda().eq(SysUserPost::getUserId, userId);
            return this.remove(whereWrapper);
        } else {
            return true;
        }
    }

    @Override
    public List<SysUserPost> detailPostList(Long userId) {
        QueryWrapper<SysUserPost> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SysUserPost::getUserId, userId);
        return this.list(queryWrapper);
    }
}
