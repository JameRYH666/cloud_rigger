package com.jeerigger.core.system.mapper;

import com.jeerigger.frame.base.mapper.BaseMapper;
import com.jeerigger.frame.mybatis.annotation.MapperSource;
import com.jeerigger.core.system.entity.SysUser;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 用户信息表 Mapper 接口
 * </p>
 *
 * @author wangcy
 * @since 2018-11-12
 */
@MapperSource
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 根据用户UUID获取已分配角色UUID
     *
     * @param userUuid
     * @return
     */
    @Select(" select distinct r.uuid " +
            " from sys_user u,sys_user_role ur,sys_role r " +
            " where u.uuid = ur.user_uuid " +
            " and ur.role_uuid = r.uuid " +
            " and u.uuid =  #{user_uuid} ")
    List<String> getUserRoleUuid(@Param("user_uuid") String userUuid);

    /**
     * 根据组织机构管理员用户UUID获取 组织机构管理员分配的角色UUID
     *
     * @param userUuid
     * @return
     */
    @Select(" select distinct r.uuid " +
            " from sys_user u,sys_orgadmin_role oar,sys_role r " +
            " where u.uuid = oar.user_uuid " +
            " and oar.role_uuid = r.uuid " +
            " and u.uuid =  #{user_uuid} ")
    List<String> getOrgAdminRoleUuid(@Param("user_uuid") String userUuid);

    /**
     * 根据组织机构管理员用户UUID获取 组织机构管理员分配可管理组织机构
     *
     * @param userUuid
     * @return
     */
    @Select(" select distinct o.uuid" +
            " from sys_user u,sys_orgadmin_org oao,sys_org o" +
            " where u.uuid = oao.user_uuid" +
            " and oao.org_uuid = o.uuid" +
            " and u.uuid =  #{user_uuid}")
    List<String> getOrgAdminOrgUuid(@Param("user_uuid") String userUuid);

}
