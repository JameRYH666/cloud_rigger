package com.jeeadmin.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeadmin.api.ISysPostService;
import com.jeeadmin.api.ISysUserPostService;
import com.jeeadmin.entity.SysPost;
import com.jeeadmin.entity.SysUserPost;
import com.jeeadmin.mapper.SysPostMapper;
import com.jeerigger.frame.base.controller.ResultCodeEnum;
import com.jeerigger.frame.base.service.impl.BaseServiceImpl;
import com.jeerigger.frame.enums.StatusEnum;
import com.jeerigger.frame.exception.FrameException;
import com.jeerigger.frame.exception.ValidateException;
import com.jeerigger.frame.page.PageHelper;
import com.jeerigger.frame.support.validate.ValidateUtil;
import com.jeerigger.frame.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 岗位表 服务实现类
 * </p>
 *
 * @author wangcy
 * @since 2019-01-22
 */
@Service
public class SysPostServiceImpl extends BaseServiceImpl<SysPostMapper, SysPost> implements ISysPostService {
    @Autowired
    private ISysUserPostService sysUserPostService;

    @Override
    public Page<SysPost> selectPage(PageHelper<SysPost> pageHelper) {
        Page<SysPost> page = new Page<>(pageHelper.getCurrent(), pageHelper.getSize());
        QueryWrapper<SysPost> queryWrapper = new QueryWrapper<>();
        if (pageHelper.getData() != null) {
            SysPost sysPost = pageHelper.getData();
            //岗位名称
            if (StringUtil.isNotEmpty(sysPost.getPostName())) {
                queryWrapper.lambda().like(SysPost::getPostName, sysPost.getPostName());
            }
            //岗位编码
            if (StringUtil.isNotEmpty(sysPost.getPostCode())) {
                queryWrapper.lambda().like(SysPost::getPostCode, sysPost.getPostCode());
            }
            //岗位状态
            if (StringUtil.isNotEmpty(sysPost.getPostStatus())) {
                queryWrapper.lambda().eq(SysPost::getPostStatus, sysPost.getPostStatus());
            }
            //岗位类型
            if (StringUtil.isNotEmpty(sysPost.getPostType())) {
                queryWrapper.lambda().eq(SysPost::getPostType, sysPost.getPostType());
            }
        }
        queryWrapper.lambda().orderByAsc(SysPost::getPostSort);
        this.page(page, queryWrapper);
        return page;
    }

    @Override
    public List<SysPost> selectPostList() {
        QueryWrapper<SysPost> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SysPost::getPostStatus, StatusEnum.NORMAL.getCode());
        queryWrapper.lambda().orderByAsc(SysPost::getPostSort);
        return this.list(queryWrapper);
    }


    @Override
    public boolean updateStatus(Long postId, String postStatus) {
        SysPost oldPost = this.getById(postId);
        if (oldPost == null) {
            throw new ValidateException("该岗位已不存在不能修改状态！");
        }
        SysPost sysPost = new SysPost();
        sysPost.setId(postId);
        sysPost.setPostStatus(postStatus);
        return this.updateById(sysPost);
    }

    @Override
    public SysPost saveSysPost(SysPost sysPost) {
        //数据验证
        ValidateUtil.validateObject(sysPost);
        //验证岗位编码
        validatorPostCode(sysPost);
        //设置状态
        sysPost.setPostStatus(StatusEnum.NORMAL.getCode());
        //保存数据
        if (this.save(sysPost)) {
            return sysPost;
        } else {
            throw new FrameException(ResultCodeEnum.ERROR_SAVE_FAIL, "新增岗位信息失败！");
        }
    }

    /**
     * 验证岗位编码
     */
    private void validatorPostCode(SysPost sysPost) {
        QueryWrapper<SysPost> queryWrapper = new QueryWrapper<>();
        if (Objects.nonNull(sysPost.getId())) {
            queryWrapper.lambda().ne(SysPost::getId, sysPost.getId());
        }
        queryWrapper.lambda().eq(SysPost::getPostCode, sysPost.getPostCode());
        if (this.count(queryWrapper) > 0) {
            throw new ValidateException("该岗位编码(" + sysPost.getPostCode() + ")已存在！");
        }
    }

    @Override
    public boolean updateSysPost(SysPost sysPost) {
        SysPost oldPost = this.getById(sysPost.getId());
        if (oldPost == null) {
            throw new ValidateException("该岗位(" + sysPost.getPostCode() + ")已不存在，不能进行编辑！");
        }
        //数据验证
        ValidateUtil.validateObject(sysPost);
        //验证岗位编码
        validatorPostCode(sysPost);
        return this.updateById(sysPost);
    }

    @Override
    public boolean deleteSysPost(Long postId) {
        SysPost sysPost = this.getById(postId);
        if (sysPost == null) {
            return true;
        }
        //查询该角色已分配人员
        QueryWrapper<SysUserPost> userRoleWrapper = new QueryWrapper<>();
        userRoleWrapper.lambda().eq(SysUserPost::getPostId, postId);
        if (sysUserPostService.count(userRoleWrapper) > 0) {
            throw new ValidateException("该岗位（" + sysPost.getPostCode() + "）已分配人员使用，不能删除！");
        }
        //删除岗位表
        if (this.removeById(postId)) {
            return true;
        }
        return false;
    }
}
