package com.jeerigger.core.module.sys.controller;

import com.jeerigger.core.module.sys.util.CloudOrgUtil;
import com.jeerigger.core.module.sys.util.SysDictUtil;
import com.jeerigger.frame.base.controller.BaseController;
import com.jeerigger.frame.base.controller.ResultData;
import com.jeerigger.frame.support.resolver.annotation.SingleRequestBody;
import com.jeerigger.frame.util.DateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 公共API 前端控制器
 * 获取系统参数字典数据等等
 * </p>
 */
@RestController
@RequestMapping("/api")
@Api(value = "基础数据API", tags = "基础数据API")
public class SysApiController extends BaseController {

    @RequestMapping(value = "/dictDataList", method = RequestMethod.POST)
    @ApiOperation(value = "根据字典类型获取字典数据列表", notes = "根据字典类型获取字典数据列表")
    public ResultData dictDataList(@SingleRequestBody(value = "dictType") String dictType) {
        return this.success(SysDictUtil.getSysDictDataList(dictType));
    }

    @RequestMapping(value = "/orgList", method = RequestMethod.POST)
    @ApiOperation(value = "获取组织机构列表", notes = "获取组织机构列表")
    public ResultData orgList() {
        return this.success(CloudOrgUtil.getSysOrgList());
    }


    @RequestMapping(value = "/sysTime", method = RequestMethod.POST)
    @ApiOperation(value = "获取系统时间", notes = "获取系统时间")
    public ResultData sysTime() {
        return this.success(DateUtil.getDateNow(), "系统时间");
    }

}
