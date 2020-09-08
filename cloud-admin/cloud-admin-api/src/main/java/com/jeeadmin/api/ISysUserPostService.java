package com.jeeadmin.api;

import com.jeeadmin.entity.SysUserPost;
import com.jeerigger.frame.base.service.BaseService;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author wangcy
 * @since 2019-01-22
 */
public interface ISysUserPostService extends BaseService<SysUserPost> {
    /**
     * 保存用户岗位
     *
     * @param sysUserPostList
     * @return
     */
    boolean saveUserPost(List<SysUserPost> sysUserPostList);

    /**
     * 根据用户UUID删除岗位
     *
     * @param userId
     * @return
     */
    boolean deleteUserPost(Long userId);

    /**
     * 获取用户岗位列表
     *
     * @param userId
     * @return
     */
    List<SysUserPost> detailPostList(Long userId);
}
