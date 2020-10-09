package com.jeeadmin.controller;

import com.jeeadmin.api.ICloudActivityRecordService;
import com.jeeadmin.entity.CloudActivityRecord;
import com.jeeadmin.vo.activity.CloudActivityRecordVo;
import com.jeerigger.frame.base.controller.BaseController;
import com.jeerigger.frame.base.controller.ResultCodeEnum;
import com.jeerigger.frame.base.controller.ResultData;
import com.jeerigger.frame.page.PageHelper;
import com.jeerigger.frame.support.resolver.annotation.SingleRequestBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javafx.scene.chart.ValueAxis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Company YXH
 * @Author ryh
 * @Date Create in 2020/9/10 14:46
 * @Description:
 */
@RestController
@RequestMapping("/activity/record")
@Api(value = "活动记录信息", tags = "活动记录信息")
@CrossOrigin(origins = "*",methods = {RequestMethod.GET,RequestMethod.POST},allowCredentials = "true")
public class CloudActivityRecordController extends BaseController {

    @Autowired
    private ICloudActivityRecordService cloudActivityRecordService;

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ApiOperation(value = "活动记录信息数据列表", notes = "活动记录信息数据列表")
    public ResultData list(@RequestBody PageHelper<CloudActivityRecord> pageHelper){
        return this.success(cloudActivityRecordService.selectData(pageHelper));
    }

    @RequestMapping(value = "/selectByActivityId",method = RequestMethod.POST)
    @ApiOperation(value = "根据活动ID查询对应的活动记录",notes = "根据活动ID查询对应的活动记录")
    public ResultData selectByActivityId(PageHelper<CloudActivityRecord> pageHelper){
        return this.success(cloudActivityRecordService.selectByActivityId(pageHelper));
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ApiOperation(value = "新增活动记录信息数据",notes = "新增活动记录信息数据列表")
    public ResultData add(@RequestBody CloudActivityRecordVo activityRecord){
        return this.success(cloudActivityRecordService.saveRecord(activityRecord));
    }

    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ApiOperation(value = "修改活动记录信息数据",notes = "新增活动记录信息数据列表")
    public ResultData update(@RequestBody CloudActivityRecordVo activityRecord){
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


    @RequestMapping(value = "/updateStatus", method = RequestMethod.POST)
    @ApiOperation(value = "逻辑删除活动记录信息数据", notes = "逻辑删除活动记录信息数据")
    public ResultData delete(@RequestBody CloudActivityRecord cloudActivityRecord) {
        if (cloudActivityRecordService.updateStatus(cloudActivityRecord)) {
            return this.success();
        } else {
            return this.failed(ResultCodeEnum.ERROR_UPDATE_FAIL, "删除活动信息数据数据失败！");
        }
    }



    @RequestMapping(value = "/selectOne", method = RequestMethod.POST)
    @ApiOperation(value = "查询单个活动记录信息数据", notes = "查询单个活动记录信息数据")
    public ResultData selectOne(@SingleRequestBody("recordId") Long id) {
        return this.success(cloudActivityRecordService.selectOneRecord(id));
    }








}
