package com.jeeadmin.controller;

import com.jeeadmin.api.ICloudEnclosureSerivce;
import com.jeerigger.frame.base.controller.BaseController;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Company YXH
 * @Author ryh
 * @Date Create in 2020/9/13 23:32
 * @Description:
 */
@RestController
@RequestMapping("/enclosure")
@Api(value = "附件信息", tags = "附件信息")
public class CloudEnclosureController extends BaseController {

    @Autowired
    private ICloudEnclosureSerivce cloudEnclosure;

}
