package com.jeeadmin.controller;

import com.jeeadmin.api.ICloudMeetingActiveTypeService;
import com.jeeadmin.entity.CloudMeetingActiveType;
import com.jeerigger.frame.base.controller.BaseController;
import com.jeerigger.frame.base.controller.ResultData;
import com.jeerigger.frame.exception.ValidateException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.transform.Result;
import java.util.List;

/**
 * @author: Sgz
 * @time: 2020/9/16 15:17
 * @description:
 *  会议记录类型前端控制器
 */
@RestController
@RequestMapping("/type")
@Api(value = "会议活动类型信息", tags = "会议活动类型信息")
public class CloudMeetingActiveTypeController extends BaseController {
    @Autowired
    private ICloudMeetingActiveTypeService cloudMeetingActiveTypeServiceImpl;

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ApiOperation(value = "获取活动会议类型列表", notes = "获取活动会议类型列表")
    public ResultData selectAll(@RequestBody CloudMeetingActiveType cloudMeetingActiveType){
        List<CloudMeetingActiveType> cloudMeetingActiveTypes = cloudMeetingActiveTypeServiceImpl.selectAll(cloudMeetingActiveType);
        return this.success(cloudMeetingActiveTypes);

    }
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ApiOperation(value = "保存活动会议类型", notes = "保存活动会议类型")
    public ResultData saveMeetingAndActiveType(@RequestBody CloudMeetingActiveType cloudMeetingActiveType){
        boolean b = cloudMeetingActiveTypeServiceImpl.saveMeetingAndActiveType(cloudMeetingActiveType);
        if (b){
            return this.success("保存成功");
        }
        throw new ValidateException("保存失败");

    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "更新活动会议类型列表", notes = "更新活动会议类型列表")
    public ResultData updateMeetingAndActiveType(@RequestBody CloudMeetingActiveType cloudMeetingActiveType){
        if (cloudMeetingActiveTypeServiceImpl.updateMeetingAndActiveType(cloudMeetingActiveType)){
            return this.success();
        }
        throw new ValidateException("更新失败");
    }
    @RequestMapping(value = "/updateStatus", method = RequestMethod.POST)
    @ApiOperation(value = "更新活动会议类型列表", notes = "更新活动会议类型列表")
    public ResultData updateMeetingAndActiveTypeStatus(@RequestBody CloudMeetingActiveType cloudMeetingActiveType){
        if (cloudMeetingActiveTypeServiceImpl.updateMeetingAndActiveTypeStatus(cloudMeetingActiveType)){
            return this.success();
        }
        throw new ValidateException("删除失败");
    }

}
