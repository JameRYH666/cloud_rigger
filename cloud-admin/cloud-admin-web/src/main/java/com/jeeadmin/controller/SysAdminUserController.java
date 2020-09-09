package com.jeeadmin.controller;


import com.jeeadmin.api.ICloudUserService;
import com.jeeadmin.entity.CloudUser;
import com.jeerigger.frame.base.controller.BaseController;
import com.jeerigger.frame.base.controller.ResultCodeEnum;
import com.jeerigger.frame.base.controller.ResultData;
import com.jeerigger.frame.page.PageHelper;
import com.jeerigger.frame.support.resolver.annotation.SingleRequestBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 系统管理员信息表 前端控制器
 * </p>
 *
 * @author wangcy
 * @since 2018-11-12
 */
@RestController
@RequestMapping("/sys/adminUser")
@Api(value = "系统管理员", tags = "系统管理员")
public class SysAdminUserController extends BaseController {
    @Autowired
    private ICloudUserService cloudUserService;

    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ApiOperation(value = "获取系统管理员列表", notes = "获取系统管理员列表")
    public ResultData list(@RequestBody PageHelper<CloudUser> pageHelper) {
        return this.success(cloudUserService.selectPage(pageHelper));
    }

    @ResponseBody
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    @ApiOperation(value = "查看系统管理员详细信息", notes = "查看系统管理员详细信息")
    public ResultData detail(@SingleRequestBody(value = "userId") Long userId) {
        return this.success(cloudUserService.getById(userId));
    }

    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ApiOperation(value = "新增系统管理员信息", notes = "新增系统管理员信息")
    public ResultData add(@RequestBody CloudUser cloudUser) {
        if (cloudUserService.saveAdminUser(cloudUser)) {
            return this.success();
        } else {
            return this.failed(ResultCodeEnum.ERROR_SAVE_FAIL, "新增系统管理员信息！");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "更新系统管理员信息", notes = "更新系统管理员信息")
    public ResultData update(@RequestBody CloudUser cloudUser) {
        if (cloudUserService.updateAdminUser(cloudUser)) {
            return this.success();
        } else {
            return this.failed(ResultCodeEnum.ERROR_UPDATE_FAIL, "更新系统管理员信息失败！");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ApiOperation(value = "删除系统管理员", notes = "删除系统管理员")
    public ResultData delete(@SingleRequestBody(value = "userId") Long userId) {
        if (cloudUserService.removeById(userId)) {
            return this.success();
        } else {
            return this.failed(ResultCodeEnum.ERROR_DELETE_FAIL, "删除系统管理员失败！");
        }
    }

    @RequestMapping(value = "/updateStatus", method = RequestMethod.POST)
    @ApiOperation(value = "修改系统管理员状态", notes = "修改系统管理员状态")
    public ResultData updateStatus(@RequestBody CloudUser cloudUser) {
        if (cloudUserService.updateAdminUserStatus(cloudUser)) {
            return this.success();
        } else {
            return this.failed(ResultCodeEnum.ERROR_UPDATE_FAIL, "修改系统管理员状态失败！");
        }
    }

    @RequestMapping(value = "/reset", method = RequestMethod.POST)
    @ApiOperation(value = "重置密码", notes = "重置密码")
    public ResultData resetPwd(@SingleRequestBody(value = "userId") Long userId) {
        if (cloudUserService.resetPwd(userId)) {
            return this.success();
        } else {
            return this.failed(ResultCodeEnum.ERROR_UPDATE_FAIL, "重置密码失败！");
        }
    }

}
