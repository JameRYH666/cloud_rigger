package com.jeeadmin.controller;


import com.jeerigger.frame.base.controller.BaseController;
import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>
 * 岗位表 前端控制器
 * </p>
 *
 * @author wangcy
 * @since 2019-01-22
 */
@Controller
@RequestMapping("/sys/post")
@Api(value = "岗位管理", tags = "岗位管理")
public class SysPostController extends BaseController {

   /* @Autowired
    private ISysPostService sysPostService;

    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ApiOperation(value = "获取岗位列表", notes = "获取岗位列表")
    public ResultData list(@RequestBody PageHelper<SysPost> pageHelper) {
        return this.success(sysPostService.selectPage(pageHelper));
    }

    @ResponseBody
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    @ApiOperation(value = "查看岗位详细信息", notes = "查看岗位详细信息")
    public ResultData detail(@SingleRequestBody(value = "postUuid") String postUuid) {
        return this.success(sysPostService.getById(postUuid));
    }

    @ResponseBody
    @RequestMapping(value = "/updateStatus", method = RequestMethod.POST)
    @ApiOperation(value = "修改岗位状态", notes = "修改岗位状态")
    public ResultData updateStatus(@RequestBody SysPost sysPost) {
        if (sysPostService.updateStatus(sysPost.getId(), sysPost.getPostStatus())) {
            return this.success();
        } else {
            return this.failed(ResultCodeEnum.ERROR_UPDATE_FAIL, "修改岗位状态失败！");
        }
    }


    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ApiOperation(value = "新增岗位信息", notes = "新增岗位信息")
    public ResultData save(@RequestBody SysPost sysPost) {
        return this.success(sysPostService.saveSysPost(sysPost));
    }

    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "更新岗位信息", notes = "更新岗位信息")
    public ResultData update(@RequestBody SysPost sysPost) {
        if (sysPostService.updateSysPost(sysPost)) {
            return this.success();
        } else {
            return this.failed(ResultCodeEnum.ERROR_UPDATE_FAIL, "更新岗位信息失败！");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ApiOperation(value = "删除岗位", notes = "删除岗位")
    public ResultData delete(@SingleRequestBody(value = "postId") Long postId) {
        if (sysPostService.deleteSysPost(postId)) {
            return this.success();
        } else {
            return this.failed(ResultCodeEnum.ERROR_DELETE_FAIL, "删除岗位信息失败！");
        }
    }*/
}
