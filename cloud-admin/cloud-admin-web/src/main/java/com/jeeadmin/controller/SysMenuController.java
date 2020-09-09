package com.jeeadmin.controller;


import com.jeeadmin.api.ICloudMenuService;
import com.jeeadmin.entity.CloudMenu;
import com.jeeadmin.vo.menu.QueryMenuVo;
import com.jeeadmin.vo.menu.SaveMenuSortVo;
import com.jeerigger.frame.base.controller.BaseController;
import com.jeerigger.frame.base.controller.ResultCodeEnum;
import com.jeerigger.frame.base.controller.ResultData;
import com.jeerigger.frame.support.resolver.annotation.SingleRequestBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * <p>
 * 系统菜单表 前端控制器
 * </p>
 *
 * @author wangcy
 * @since 2018-11-12
 */
@Controller
@RequestMapping("/sys/menu")
@Api(value = "菜单管理", tags = "菜单管理")
public class SysMenuController extends BaseController {

    @Autowired
    private ICloudMenuService cloudMenuService;

    @ResponseBody
    @RequestMapping(value = "/selectChild", method = RequestMethod.POST)
    @ApiOperation(value = "查询下级菜单列表", notes = "查询下级菜单列表")
    public ResultData selectChildMenu(@RequestBody QueryMenuVo queryMenuVo) {
        return this.success(cloudMenuService.selectChildMenu(queryMenuVo));
    }

    @ResponseBody
    @RequestMapping(value = "/selectAll", method = RequestMethod.POST)
    @ApiOperation(value = "查询菜单列表", notes = "查询菜单列表")
    public ResultData selectMenuList(@RequestBody QueryMenuVo queryMenuVo) {
        return this.success(cloudMenuService.selectMenuList(queryMenuVo));
    }

    @ResponseBody
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    @ApiOperation(value = "查看菜单详细信息", notes = "查看菜单详细信息")
    public ResultData detailMenu(@SingleRequestBody(value = "menuId") Long menuId) {
        return this.success(cloudMenuService.getById(menuId));
    }

    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ApiOperation(value = "新增菜单信息", notes = "新增菜单信息")
    public ResultData addMenu(@RequestBody CloudMenu sysMenu) {
        return this.success(cloudMenuService.saveMenu(sysMenu));
    }

    @ResponseBody
    @RequestMapping(value = "/saveMenuSort", method = RequestMethod.POST)
    @ApiOperation(value = "批量保存菜单排序", notes = "批量保存菜单排序")
    public ResultData saveMenuSort(@RequestBody List<SaveMenuSortVo> menuSortVoList) {
        if (cloudMenuService.saveMenuSort(menuSortVoList)) {
            return this.success();
        } else {
            return this.failed(ResultCodeEnum.ERROR_SAVE_FAIL, "批量保存菜单排序失败！");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "更新菜单信息", notes = "更新菜单信息")
    public ResultData updateMenuByMenuCode(@RequestBody CloudMenu cloudMenu) {
        if (cloudMenuService.updateMenu(cloudMenu)) {
            return this.success();
        } else {
            return this.failed(ResultCodeEnum.ERROR_UPDATE_FAIL, "更新菜单信息失败!");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ApiOperation(value = "删除菜单", notes = "删除菜单")
    public ResultData deleteMenu(@SingleRequestBody(value = "menuId") Long menuId) {
        if (cloudMenuService.deleteMenu(menuId)) {
            return this.success();
        } else {
            return this.failed(ResultCodeEnum.ERROR_DELETE_FAIL, "删除菜单失败！");
        }
    }
}
