package com.jeeadmin.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jeeadmin.api.ISysRoleService;
import com.jeeadmin.api.ISysUserRoleService;
import com.jeeadmin.api.ISysUserService;
import com.jeeadmin.entity.SysUser;
import com.jeeadmin.vo.user.AssignRoleVo;
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
 * 用户信息表 前端控制器
 * </p>
 *
 * @author wangcy
 * @since 2018-11-12
 */
@Controller
@RequestMapping("/sys/user")
@Api(value = "用户管理", tags = "用户管理")
public class SysUserController extends BaseController {

    @Autowired
    private ISysUserService sysUserService;
    @Autowired
    private ISysRoleService sysRoleService;
    @Autowired
    private ISysUserRoleService sysUserRoleService;

    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ApiOperation(value = "获取用户信息列表", notes = "获取用户信息列表")
    public ResultData selectAllUsers(@RequestBody PageHelper<QueryUserVo> pageHelper) {
        IPage<SysUser> sysUserIPage = sysUserService.selectPage(pageHelper);
        return this.success(sysUserIPage);
    }


    @ResponseBody
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    @ApiOperation(value = "获取用户详细信息", notes = "获取用户详细信息")
    public ResultData selectUserByUserCode(@SingleRequestBody(value = "userId") Long userId) {
        return this.success(sysUserService.getUserById(userId));
    }


    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ApiOperation(value = "新增用户信息", notes = "新增用户信息")
    public ResultData save(@RequestBody SysUser sysUser) {
        if (sysUserService.saveUser(sysUser)) {
            return this.success();
        } else {
            return this.failed(ResultCodeEnum.ERROR_SAVE_FAIL, "新增用户失败！");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "更新用户信息", notes = "更新用户信息")
    public ResultData updateUser(@RequestBody SysUser sysUser) {
        if (sysUserService.updateUser(sysUser)) {
            return this.success();
        } else {
            return this.failed(ResultCodeEnum.ERROR_UPDATE_FAIL, "更新用户信息失败！");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ApiOperation(value = "删除用户", notes = "删除用户")
    public ResultData updateUserByUserCode(@SingleRequestBody(value = "userId") Long userId) {
        if (sysUserService.deleteUser(userId)) {
            return this.success();
        } else {
            return this.failed(ResultCodeEnum.ERROR_DELETE_FAIL, "删除用户失败！");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/updateStatus", method = RequestMethod.POST)
    @ApiOperation(value = "更新用户状态", notes = "更新用户状态")
    public ResultData updateStatus(@RequestBody SysUser sysUser) {
        if (sysUserService.updateUserStatus(sysUser)) {
            return this.success();
        } else {
            return this.failed(ResultCodeEnum.ERROR_UPDATE_FAIL, "用户状态更新失败！");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/reset", method = RequestMethod.POST)
    @ApiOperation(value = "重置用户密码", notes = "重置用户密码")
    public ResultData resetUserPassword(@SingleRequestBody(value = "userId") Long userId) {
        if (sysUserService.resetUserPwd(userId)) {
            return this.success();
        } else {
            return this.failed(ResultCodeEnum.ERROR_UPDATE_FAIL, "重置用户密码失败！");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/roleList", method = RequestMethod.POST)
    @ApiOperation(value = "获取用户可分配角色", notes = "获取用户可分配角色")
    public ResultData roleList() {
        return this.success(sysRoleService.selectRoleList());
    }


    @ResponseBody
    @RequestMapping(value = "/detailRoleList", method = RequestMethod.POST)
    @ApiOperation(value = "查看用户已分配角色列表", notes = "查看用户已分配角色列表")
    public ResultData detailRole(@SingleRequestBody(value = "userId") Long userId) {
        return this.success(sysUserRoleService.detailRoleList(userId));
    }

    @ResponseBody
    @RequestMapping(value = "/assignRole", method = RequestMethod.POST)
    @ApiOperation(value = "用户分配角色", notes = "用户分配角色")
    public ResultData assignRole(@RequestBody AssignRoleVo assignRoleVo) {
        if (sysUserService.assignRole(assignRoleVo)) {
            return this.success();
        } else {
            return this.failed(ResultCodeEnum.ERROR_SAVE_FAIL, "用户分配角色失败！");
        }
    }

}
