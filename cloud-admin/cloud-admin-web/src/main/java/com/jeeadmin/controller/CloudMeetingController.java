package com.jeeadmin.controller;


import com.jeeadmin.api.ICloudMeetingService;

import com.jeeadmin.entity.CloudMeeting;
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
 * @author sgz
 *  会议记录前端控制器
 */
@RestController
@RequestMapping("/meeting")
@Api(value = "会议信息", tags = "会议信息")
public class CloudMeetingController extends BaseController {

    @Autowired
    private ICloudMeetingService cloudMeetingService;

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ApiOperation(value = "获取活动会议数据列表", notes = "获取活动会议数据列表")
    public ResultData list(@RequestBody PageHelper<CloudMeeting> pageHelper){
        return this.success(cloudMeetingService.selectPage(pageHelper));
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ApiOperation(value = "新增活动会议数据",notes = "新增活动会议数据列表")
    public ResultData add(@RequestBody CloudMeeting meeting){
        return this.success(cloudMeetingService.saveMeeting(meeting));
    }

    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ApiOperation(value = "更新活动会议数据",notes = "更新活动会议数据")
    public ResultData update(@RequestBody CloudMeeting meeting){
        if (cloudMeetingService.updateMeeting(meeting)){
            return this.success();
        }else {
            return this.failed(ResultCodeEnum.ERROR_UPDATE_FAIL,"更新活动信息失败");
        }
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ApiOperation(value = "删除会议信息数据", notes = "删除会议信息数据")
    public ResultData updateStatus(@SingleRequestBody(value = "id") Long id) {
        if (cloudMeetingService.deleteMeeting(id)) {
            return this.success();
        } else {
            return this.failed(ResultCodeEnum.ERROR_UPDATE_FAIL, "删除活动信息数据数据失败！");
        }
    }

    @RequestMapping(value = "/selectOne",method = RequestMethod.POST)
    @ApiOperation(value = "查询单个会议信息",notes = "查询单个会议的信息")
        public  ResultData selectOneActivity(@SingleRequestBody(value = "id") Long id){
        return this.success(cloudMeetingService.selectOneMeeting(id));
    }





}
