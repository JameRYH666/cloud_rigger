package com.jeeadmin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeadmin.api.ICloudPartyMemberService;
import com.jeeadmin.entity.CloudPartyMember;
import com.jeeadmin.vo.member.PartyMemberVo;
import com.jeerigger.frame.base.controller.BaseController;
import com.jeerigger.frame.base.controller.ResultData;
import com.jeerigger.frame.page.PageHelper;
import com.jeerigger.frame.support.resolver.annotation.SingleRequestBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author: Sgz
 * @time: 2020/9/10 11:06
 * @description:
 *  党员信息表 前端控制器
 */
@Controller
@RequestMapping("/cloud/partyMember")
@Api(value = "党员管理",tags = "党员管理")
public class CloudPartyMemberController extends BaseController {
    @Autowired
    private ICloudPartyMemberService cloudPartyMemberService;

    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ApiOperation(value = "获取党员信息列表", notes = "获取党员信息列表")
    public ResultData selectAllPartyMembers(@RequestBody PageHelper<PartyMemberVo> pageHelper ){
        Page<CloudPartyMember> cloudPartyMemberPage = cloudPartyMemberService.selectPage(pageHelper);
        return this.success(cloudPartyMemberPage);
    }


    @ResponseBody
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    @ApiOperation(value = "根据党员id获取党员详细信息列表", notes = "根据党员id获取党员详细信息列表")
    public ResultData getPartyMemberById(@SingleRequestBody(value = "id") Long id){
        return this.success(cloudPartyMemberService.getPartyMemberByUserId(id));
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ApiOperation(value = "新增党员信息", notes = "新增党员信息")
    public ResultData saveUser (@RequestBody CloudPartyMember cloudPartyMember){
        return this.success(cloudPartyMemberService.saveUser(cloudPartyMember));
    }

    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "更新 党员信息", notes = "更新党员信息")
    public ResultData updateUser(@RequestBody CloudPartyMember cloudPartyMember){
        return this.success(cloudPartyMemberService.updateUser(cloudPartyMember));
    }
    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ApiOperation(value = "删除党员信息", notes = "删除党员信息")
    public ResultData deleteUser(@RequestBody CloudPartyMember cloudPartyMember){
        return this.success(cloudPartyMemberService.deleteUser(cloudPartyMember));
    }

}
