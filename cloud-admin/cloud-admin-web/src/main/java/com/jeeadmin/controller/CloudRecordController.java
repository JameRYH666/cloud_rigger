package com.jeeadmin.controller;

import com.jeeadmin.api.ICloudRecordService;
import com.jeeadmin.entity.CloudRecord;
import com.jeeadmin.vo.record.CloudRecordVo;
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

@RestController
@RequestMapping("/record")
@Api(value = "外出报备",tags = "外出报备")
public class CloudRecordController extends BaseController {

    @Autowired
    private ICloudRecordService cloudRecordService;


    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ApiOperation(value = "新增外出报备数据",notes = "新增外出报备数据")
    public ResultData add(@RequestBody CloudRecord cloudRecord){
        return this.success(cloudRecordService.saveCloudRecord(cloudRecord));
    }

    @RequestMapping(value = "/list",method = RequestMethod.POST)
    @ApiOperation(value = "查询未被删除的所有外出报备数据",notes = "查询未背删除所有的所有外出报备数据")
    public ResultData list(@RequestBody PageHelper<CloudRecord> pageHelper){
        return this.success(cloudRecordService.selectList(pageHelper));
    }

    @RequestMapping(value = "/notReview",method = RequestMethod.POST)
    @ApiOperation(value = "根据党员id查询该党员待审核的外出报备数据",notes = "根据党员id查询该党员待审核的外出报备数据")
    public ResultData selectNotReview(@RequestBody PageHelper<CloudRecord> pageHelper){
        return this.success(cloudRecordService.selectNotReview(pageHelper));
    }

    @RequestMapping(value = "/processed",method = RequestMethod.POST)
    @ApiOperation(value = "根据党员id查询已处理的外出报备数据",notes = "根据党员id查询已处理的外出报备数据")
    public ResultData selectProcessed(@RequestBody PageHelper<CloudRecord> pageHelper){
        return this.success(cloudRecordService.selectProcessed(pageHelper));
    }

    @RequestMapping(value = "/selectOne",method = RequestMethod.POST)
    @ApiOperation(value = "查询单个外出报备信息详情",notes = "查询单个外出报备信息详情")
    public ResultData selectOne(@SingleRequestBody(value = "recordId") Long recordId){
        return this.success(cloudRecordService.selectOne(recordId));
    }

    @RequestMapping(value = "/selectByCondition",method = RequestMethod.POST)
    @ApiOperation(value = "根据实体类中不为空的字段查询",notes = "根据实体类中不为空的字段查询")
    public ResultData selectByCondition(@RequestBody CloudRecord cloudRecord){
        return this.success(cloudRecordService.selectByCondition(cloudRecord));
    }

    @RequestMapping(value = "/updateStatus",method = RequestMethod.POST)
    @ApiOperation(value = "审核完成后更改外出报备数据的状态",notes = "审核完成后更改外出报备数据的状态")
    public ResultData updateStatus(@RequestBody CloudRecord cloudRecord){
        return this.success();
    }

    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ApiOperation(value = "逻辑删除外出报备信息",notes = "逻辑删除外出报备信息")
    public ResultData delete(@SingleRequestBody(value = "recordId") Long recordId){
        if (cloudRecordService.delete(recordId)){
            return this.success();
        }else {
            return this.failed(ResultCodeEnum.ERROR_DELETE_FAIL,"删除信息失败");
        }
    }
}
