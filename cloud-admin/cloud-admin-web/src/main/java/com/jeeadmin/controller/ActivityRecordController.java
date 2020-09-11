package com.jeeadmin.controller;

import com.jeeadmin.api.ICloudActivityRecordService;
import com.jeeadmin.entity.CloudActivityRecord;
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
 * @Date Create in 2020/9/10 14:46
 * @Description:
 */
@RestController
@RequestMapping("/activity/record")
@Api(value = "活动记录信息", tags = "活动记录信息")
public class ActivityRecordController extends BaseController {

    @Autowired
    private ICloudActivityRecordService cloudActivityRecordService;

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ApiOperation(value = "活动记录信息数据列表", notes = "活动记录信息数据列表")
    public ResultData list(@RequestBody PageHelper<CloudActivityRecord> pageHelper){
        return this.success(cloudActivityRecordService.selectData(pageHelper));
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ApiOperation(value = "新增活动记录信息数据",notes = "新增活动记录信息数据列表")
    public ResultData add(@RequestBody CloudActivityRecord activityRecord){
        return this.success(cloudActivityRecordService.saveRecord(activityRecord));
    }

    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ApiOperation(value = "修改活动记录信息数据",notes = "新增活动记录信息数据列表")
    public ResultData update(@RequestBody CloudActivityRecord activityRecord){
        if (cloudActivityRecordService.updateRecord(activityRecord)){
            return this.success();
        }else {
            return this.failed(ResultCodeEnum.ERROR_UPDATE_FAIL,"更新活动记录信息失败");
        }
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ApiOperation(value = "删除活动记录信息数据", notes = "删除活动记录信息数据")
    public ResultData delete(@SingleRequestBody(value = "recordId") Long recordId) {
        if (cloudActivityRecordService.deleteRecord(recordId)) {
            return this.success();
        } else {
            return this.failed(ResultCodeEnum.ERROR_UPDATE_FAIL, "删除活动信息数据数据失败！");
        }
    }

    @RequestMapping(value = "/selectOne", method = RequestMethod.POST)
    @ApiOperation(value = "查询单个活动记录信息数据", notes = "查询单个活动记录信息数据")
    public ResultData selectOne(@SingleRequestBody("recordId") Long recordId) {
        return this.success(cloudActivityRecordService.selectOneRecord(recordId));
    }








}
