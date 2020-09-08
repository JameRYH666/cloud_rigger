package com.jeeadmin.controller;


import com.jeeadmin.api.ISysDictDataService;
import com.jeeadmin.entity.SysDictData;
import com.jeerigger.frame.base.controller.BaseController;
import com.jeerigger.frame.base.controller.ResultCodeEnum;
import com.jeerigger.frame.base.controller.ResultData;
import com.jeerigger.frame.support.resolver.annotation.SingleRequestBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 字典数据表 前端控制器
 * </p>
 *
 * @author wangcy
 * @since 2018-11-12
 */
@RestController
@RequestMapping("/sys/dictData")
@Api(value = "字典数据管理", tags = "字典数据管理")
public class SysDictDataController extends BaseController {
    @Autowired
    private ISysDictDataService sysDictDataService;

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ApiOperation(value = "获取字典数据列表", notes = "获取字典数据列表")
    public ResultData list(@RequestBody SysDictData sysDictData) {
        return this.success(sysDictDataService.selectDictDataList(sysDictData));
    }

    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    @ApiOperation(value = "查看字典数据详细信息", notes = "查看字典数据详细信息")
    public ResultData detail(@SingleRequestBody(value = "dictDataId") Long dictDataId) {
        return this.success(sysDictDataService.getById(dictDataId));
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ApiOperation(value = "新增字典数据信息", notes = "新增字典数据信息")
    public ResultData add(@RequestBody SysDictData sysDictData) {
        return this.success(sysDictDataService.saveDictData(sysDictData));
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "更新字典数据信息", notes = "更新字典数据信息")
    public ResultData update(@RequestBody SysDictData sysDictData) {
        if (sysDictDataService.updateDictData(sysDictData)) {
            return this.success();
        } else {
            return this.failed(ResultCodeEnum.ERROR_UPDATE_FAIL, "更新字典数据信息失败！");
        }
    }

    @RequestMapping(value = "/updateStatus", method = RequestMethod.POST)
    @ApiOperation(value = "更新字典数据状态", notes = "更新字典数据状态")
    public ResultData updateStatus(@RequestBody SysDictData sysDictData) {
        if (sysDictDataService.updateStatus(sysDictData.getId(), sysDictData.getDictStatus())) {
            return this.success();
        } else {
            return this.failed(ResultCodeEnum.ERROR_UPDATE_FAIL, "更新字典数据信息失败！");
        }
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ApiOperation(value = "删除字典数据", notes = "删除字典数据")
    public ResultData updateStatus(@SingleRequestBody(value = "dictDataId") Long dictDataId) {
        if (sysDictDataService.deleteDictData(dictDataId)) {
            return this.success();
        } else {
            return this.failed(ResultCodeEnum.ERROR_UPDATE_FAIL, "删除字典数据失败！");
        }
    }
}
