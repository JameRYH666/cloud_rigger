package com.jeeadmin.controller;


import com.jeeadmin.api.*;
import com.jeeadmin.entity.SysRole;
import com.jeeadmin.vo.role.AssignMenuVo;
import com.jeeadmin.vo.role.AssignUserVo;
import com.jeeadmin.vo.role.CancelUserVo;
import com.jeeadmin.vo.user.QueryUserVo;
import com.jeerigger.frame.base.controller.BaseController;
import com.jeerigger.frame.base.controller.ResultCodeEnum;
import com.jeerigger.frame.base.controller.ResultData;
import com.jeerigger.frame.page.PageHelper;
import com.jeerigger.frame.support.resolver.annotation.SingleRequestBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author wangcy
 * @since 2018-11-12
 */
@Controller
@RequestMapping("/sys/role")
@Api(value = "角色管理", tags = "角色管理")
public class SysRoleController extends BaseController {
    @Autowired
    private ICloudRoleService sysRoleService;
    @Autowired
    private ICloudPartyMemberService sysUserService;
    @Autowired
    private ICloudMenuService sysMenuService;
    @Autowired
    private ISysUserRoleService sysUserRoleService;
    @Autowired
    private ICloudRoleMenuService sysRoleMenuService;

    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ApiOperation(value = "获取角色列表", notes = "获取角色列表")
    public ResultData list(@RequestBody PageHelper<SysRole> pageHelper) {
        return this.success(sysRoleService.selectPage(pageHelper));
    }

    @ResponseBody
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    @ApiOperation(value = "查看角色详细信息", notes = "查看角色详细信息")
    public ResultData detail(@SingleRequestBody(value = "roleUuid") String roleUuid) {
        return this.success(sysRoleService.getById(roleUuid));
    }

    @ResponseBody
    @RequestMapping(value = "/updateStatus", method = RequestMethod.POST)
    @ApiOperation(value = "修改角色状态", notes = "修改角色状态")
    public ResultData updateStatus(@RequestBody SysRole sysRole) {
        if (sysRoleService.updateStatus(sysRole.getId(), sysRole.getRoleStatus())) {
            return this.success();
        } else {
            return this.failed(ResultCodeEnum.ERROR_UPDATE_FAIL, "修改角色状态失败！");
        }
    }


    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ApiOperation(value = "新增角色信息", notes = "新增角色信息")
    public ResultData save(@RequestBody SysRole sysRole) {
        return this.success(sysRoleService.saveSysRole(sysRole));
    }

    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "更新角色信息", notes = "更新角色信息")
    public ResultData update(@RequestBody SysRole sysRole) {
        if (sysRoleService.updateSysRole(sysRole)) {
            return this.success();
        } else {
            return this.failed(ResultCodeEnum.ERROR_UPDATE_FAIL, "更新角色信息失败！");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ApiOperation(value = "删除角色", notes = "删除角色")
    public ResultData delete(@SingleRequestBody(value = "roleId") Long roleId) {
        if (sysRoleService.deleteSysRole(roleId)) {
            return this.success();
        } else {
            return this.failed(ResultCodeEnum.ERROR_DELETE_FAIL, "删除角色信息失败！");
        }
    }


    @ResponseBody
    @RequestMapping(value = "/detailUserList", method = RequestMethod.POST)
    @ApiOperation(value = "查看角色已分配用户", notes = "查看角色已分配用户")
    public ResultData detailUserList(@SingleRequestBody(value = "roleId") Long roleId) {
        return this.success(sysUserService.detailUserList(roleId));
    }

    @ResponseBody
    @RequestMapping(value = "/getUserList", method = RequestMethod.POST)
    @ApiOperation(value = "获取用户列表", notes = "获取用户列表")
    public ResultData userList(@RequestBody PageHelper<QueryUserVo> pageHelper) {
        return this.success(sysRoleService.selectUserPage(pageHelper));
    }

    @ResponseBody
    @RequestMapping(value = "/assignUser", method = RequestMethod.POST)
    @ApiOperation(value = "角色添加用户", notes = "角色添加用户")
    public ResultData assignUser(@RequestBody AssignUserVo assignUserVo) {
        boolean assignFlag = sysRoleService.assignUser(assignUserVo);
        if (assignFlag) {
            return this.success();
        } else {
            return this.failed(ResultCodeEnum.ERROR_SAVE_FAIL, "用户分配角色失败！");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/cancelUser", method = RequestMethod.POST)
    @ApiOperation(value = "批量取消角色已分配用户", notes = "批量取消角色已分配用户")
    public ResultData cancelUser(@RequestBody CancelUserVo cancelUserVo) {
        if (sysUserRoleService.cancelRoleUser(cancelUserVo)) {
            return this.success();
        } else {
            return this.failed(ResultCodeEnum.ERROR_SAVE_FAIL, "批量取消角色已分配用户失败！");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/menuList", method = RequestMethod.POST)
    @ApiOperation(value = "获取角色可分配菜单", notes = "获取角色可分配菜单")
    public ResultData menuList() {
        return this.success(sysMenuService.getMenuList());
    }

    @ResponseBody
    @RequestMapping(value = "/detailMenuList", method = RequestMethod.POST)
    @ApiOperation(value = "查看角色已分配菜单", notes = "查看角色已分配菜单")
    public ResultData detailMenuList(@SingleRequestBody(value = "roleId") Long roleId) {
        return this.success(sysRoleMenuService.detailMenuList(roleId));
    }

    @ResponseBody
    @RequestMapping(value = "/assignMenu", method = RequestMethod.POST)
    @ApiOperation(value = "角色分配菜单", notes = "角色分配菜单")
    public ResultData assignMenu(@RequestBody AssignMenuVo assignMenuVo) {
        if (sysRoleService.assignMenu(assignMenuVo)) {
            return this.success();
        } else {
            return this.failed(ResultCodeEnum.ERROR_SAVE_FAIL, "角色分配菜单失败！");
        }
    }

}
