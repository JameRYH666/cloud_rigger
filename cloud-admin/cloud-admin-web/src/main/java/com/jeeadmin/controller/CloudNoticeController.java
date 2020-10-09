package com.jeeadmin.controller;


import com.jeeadmin.api.ICloudNoticeService;
import com.jeeadmin.entity.CloudNotice;
import com.jeeadmin.vo.notice.CloudNoticeVo;
import com.jeerigger.frame.base.controller.BaseController;
import com.jeerigger.frame.base.controller.ResultCodeEnum;
import com.jeerigger.frame.base.controller.ResultData;
import com.jeerigger.frame.page.PageHelper;
import com.jeerigger.frame.support.resolver.annotation.SingleRequestBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @Company YXH
 * @Author ryh
 * @Date Create in 2020/9/16 10:12
 * @Description:
 */
@RestController
@RequestMapping("/notice")
@Api(value = "通知公告信息", tags = "通知公告信息")
@CrossOrigin(origins = "*",methods = {RequestMethod.GET,RequestMethod.POST},allowCredentials = "true")
public class CloudNoticeController extends BaseController {

    @Autowired
    private ICloudNoticeService cloudNoticeService;

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ApiOperation(value = "获取通知公告数据列表", notes = "获取通知公告数据列表")
    public ResultData list(@RequestBody PageHelper<CloudNoticeVo> pageHelper){
        return this.success(cloudNoticeService.selectPage(pageHelper));
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ApiOperation(value = "新增通知公告信息",notes = "新增通知公告信息")
    public ResultData add(@RequestBody CloudNoticeVo cloudNotice){
        return this.success(cloudNoticeService.saveNotice(cloudNotice));
    }

    @RequestMapping(value = "/selectOne",method = RequestMethod.POST)
    @ApiOperation(value = "查询单个通知公告信息",notes = "查询单个通知公告信息")
    public ResultData add(@SingleRequestBody Long id){
        return this.success(cloudNoticeService.selectOneNotice(id));
    }

    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ApiOperation(value = "修改通知公告信息",notes = "修改通知公告信息 ")
    public ResultData update(@RequestBody CloudNotice cloudNotice){
        if (cloudNoticeService.updateNotice(cloudNotice)){
            return this.success();
        }else {
            return this.failed(ResultCodeEnum.ERROR_UPDATE_FAIL,"更新通知公告失败");
        }
    }

    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ApiOperation(value = "删除通知公告",notes = "删除通知公告")
    public ResultData delete(@RequestBody CloudNotice cloudNotice){
        if (cloudNoticeService.updateStatus(cloudNotice)){
            return this.success();
        }else {
            return this.failed(ResultCodeEnum.ERROR_DELETE_FAIL,"删除更新通知公告失败");
        }
    }

    @RequestMapping(value = "/selectEnclosure",method = RequestMethod.POST)
    @ApiOperation(value = "通过公告ID查询对应的附件信息",notes = "通过公告ID查询对应的附件信息")
    public ResultData selectEnclosureByNoticeId(@SingleRequestBody Long id){
        return this.success(cloudNoticeService.selectByNoticeId(id));
    }


}
