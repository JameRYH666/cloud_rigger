package com.jeeadmin.controller;


import com.jeeadmin.api.ICloudOrgService;
import com.jeeadmin.entity.CloudOrg;
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
 * 组织机构表 前端控制器
 * </p>
 *
 * @author wangcy
 * @since 2018-11-12
 */
@Controller
@RequestMapping("/sys/org")
@Api(value = "组织机构管理", tags = "组织机构管理")
public class SysOrgController extends BaseController {

    @Autowired
    private ICloudOrgService sysOrgService;

    @ResponseBody
    @RequestMapping(value = "/selectChild", method = RequestMethod.POST)
    @ApiOperation(value = "查询下级组织机构列表", notes = "查询下级组织机构列表")
    public ResultData selectChild(@SingleRequestBody(value = "orgId") Long orgId) {
        return this.success(sysOrgService.selectChildOrg(orgId));
    }

    @ResponseBody
    @RequestMapping(value = "/select", method = RequestMethod.POST)
    @ApiOperation(value = "查询组织机构列表", notes = "查询组织机构列表")
    public ResultData select(@RequestBody PageHelper<CloudOrg>  cloudOrg) {

        return this.success(sysOrgService.selectOrgList(cloudOrg));
    }


    @ResponseBody
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    @ApiOperation(value = "查看组织机构详细信息", notes = "查看组织机构详细信息")
    public ResultData detail(@SingleRequestBody(value = "orgId") Long orgId) {
        return this.success(sysOrgService.detailOrg(orgId));
    }
    @ResponseBody
    @RequestMapping(value = "/selectAll", method = RequestMethod.POST)
    @ApiOperation(value = "查询组织机构列表", notes = "查询组织机构列表")
    public ResultData selectAll(@RequestBody PageHelper<CloudOrg> cloudOrgPageHelper) {

        return this.success(sysOrgService.selectOrgList(cloudOrgPageHelper));
    }

    @ResponseBody
    @RequestMapping(value = "/selectOrgTrees", method = RequestMethod.POST)
    @ApiOperation(value = "查询组织机构树状列表", notes = "查询组织机构树状列表")
    public ResultData selectOrgTrees( ) {

        return this.success(sysOrgService.selectOrgTree());
    }


    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ApiOperation(value = "新增组织机构信息", notes = "新增组织机构信息")
    public ResultData save(@RequestBody CloudOrg sysOrg) {
        return this.success(sysOrgService.saveSysOrg(sysOrg));
    }

    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "更新组织机构信息", notes = "更新组织机构信息")
    public ResultData update(@RequestBody CloudOrg sysOrg) {
        if (sysOrgService.updateSysOrg(sysOrg)) {
            return this.success();
        } else {
            return this.success("error", "更新组织机构信息失败！");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/updateStatus", method = RequestMethod.POST)
    @ApiOperation(value = "更新组织机构状态", notes = "更新组织机构状态")
    public ResultData updateStatus(@RequestBody CloudOrg cloudOrg) {
        // TODO 判断状态码的值问题
        if (sysOrgService.updateStatus(cloudOrg)) {
            return this.success();
        } else {
            return this.failed(ResultCodeEnum.ERROR_UPDATE_FAIL, "更新组织机构状态失败！");
        }
    }


    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ApiOperation(value = "删除组织机构", notes = "删除组织机构")
    public ResultData delete(@SingleRequestBody(value = "orgId") Long orgId) {
        if (sysOrgService.deleteSysOrg(orgId)) {
            return this.success();
        } else {
            return this.failed(ResultCodeEnum.ERROR_DELETE_FAIL, "删除组织机构失败！");
        }
    }

}
