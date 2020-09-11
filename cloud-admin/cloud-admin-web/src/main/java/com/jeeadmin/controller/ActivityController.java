package com.jeeadmin.controller;

import com.jeeadmin.api.ICloudActivityService;
import com.jeeadmin.entity.CloudActivity;
import com.jeerigger.frame.base.controller.BaseController;
import com.jeerigger.frame.base.controller.ResultCodeEnum;
import com.jeerigger.frame.base.controller.ResultData;
import com.jeerigger.frame.page.PageHelper;
import com.jeerigger.frame.support.resolver.annotation.SingleRequestBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Company YXH
 * @Author ryh
 * @Date Create in 2020/9/9 17:05
 * @Description:
 */
@RestController
@RequestMapping("/activity")
@Api(value = "活动信息", tags = "活动信息")
public class ActivityController extends BaseController {

    @Autowired
    private ICloudActivityService cloudActivityService;

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ApiOperation(value = "获取活动信息数据列表", notes = "获取活动信息数据列表")
    public ResultData list(@RequestBody PageHelper<CloudActivity> pageHelper){
        return this.success(cloudActivityService.selectPage(pageHelper));
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ApiOperation(value = "新增活动信息数据",notes = "新增活动信息数据列表")
    public ResultData add(@RequestBody CloudActivity activity){
        return this.success(cloudActivityService.saveActivity(activity));
    }

    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ApiOperation(value = "更新活动信息数据",notes = "更新活动信息数据")
    public ResultData update(@RequestBody CloudActivity activity){
        if (cloudActivityService.updateActivity(activity)){
            return this.success();
        }else {
            return this.failed(ResultCodeEnum.ERROR_UPDATE_FAIL,"更新活动信息失败");
        }
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ApiOperation(value = "删除活动信息数据", notes = "删除活动信息数据")
    public ResultData updateStatus(@SingleRequestBody(value = "activityId") Long activityId) {
        if (cloudActivityService.deleteActivity(activityId)) {
            return this.success();
        } else {
            return this.failed(ResultCodeEnum.ERROR_UPDATE_FAIL, "删除活动信息数据数据失败！");
        }
    }

    @RequestMapping(value = "/selectOne",method = RequestMethod.POST)
    @ApiOperation(value = "查询单个活动信息",notes = "查询单个活动的信息")
        public  ResultData selectOneActivity(@SingleRequestBody(value = "activityId") Long activityId){
        return this.success(cloudActivityService.selectOneActivity(activityId));
    }





}
