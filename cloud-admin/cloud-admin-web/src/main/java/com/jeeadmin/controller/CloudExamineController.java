package com.jeeadmin.controller;

import com.jeeadmin.api.ICloudExamineService;
import com.jeeadmin.entity.CloudExamine;
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
@RequestMapping("/examine")
@Api(value = "审核",tags = "审核")
public class CloudExamineController extends BaseController {

    @Autowired
    private ICloudExamineService cloudExamineService;

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ApiOperation(value = "新增审核数据",notes = "新增审核数据")
    public ResultData add(@RequestBody CloudExamine cloudExamine){
        return this.success(cloudExamineService.saveExamine(cloudExamine));
    }

    @RequestMapping(value = "/list",method = RequestMethod.POST)
    @ApiOperation(value = "查询所有审核数据",notes = "查询所有审核数据")
    public ResultData list(@RequestBody PageHelper<CloudExamine> pageHelper){
        return this.success(cloudExamineService.selectAll(pageHelper));
    }

    @RequestMapping(value = "/selectOne",method = RequestMethod.POST)
    @ApiOperation(value = "参考审核数据的详情",tags = "参考审核数据的详情")
    public ResultData selectOne(@SingleRequestBody(value = "examineId") Long examineId){
        return this.success(cloudExamineService.getDetail(examineId));
    }

    @RequestMapping(value = "/examine",method = RequestMethod.POST)
    @ApiOperation(value = "进行审核,更改审核状态",notes = "进行审核,更改审核状态")
    public ResultData examine(@RequestBody CloudExamine cloudExamine){
        if (cloudExamineService.updateStatus(cloudExamine)){
            return this.success(cloudExamineService.updateStatus(cloudExamine));
        }else {
            return this.failed(ResultCodeEnum.ERROR_UPDATE_FAIL,"审核信息失败");
        }
    }
}
