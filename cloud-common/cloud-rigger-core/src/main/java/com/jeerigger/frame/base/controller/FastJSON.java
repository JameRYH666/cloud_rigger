package com.jeerigger.frame.base.controller;


import com.jeerigger.frame.json.JSON;
import com.jeerigger.frame.util.StringUtil;
import io.swagger.annotations.ApiModelProperty;

public class FastJSON<T> extends JSON<T> {


    @ApiModelProperty(value = "响应编码")
    private String code;

    @ApiModelProperty(value = "响应信息")
    private String message;

    @ApiModelProperty(value = "响应详细信息")
    private String details;


    public String getCode() {
        return code;
    }

    public FastJSON<T> setCode(String code) {
        this.code = code;
        return this;
    }

    public FastJSON<T> resultcode(IResultCode resultCode) {

        this.setCode(resultCode.getCode());
        if (StringUtil.isNotEmpty(resultCode.getMessage())) {
            this.setMessage(resultCode.getMessage());
        } else {
            this.setMessage("未知错误信息！");
        }
        return this;
    }

    public String getMessage() {
        return message;
    }

    public FastJSON<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public boolean isSuccess() {
        return ResultCodeEnum.SUCCESS.getCode().equals(this.code);
    }

}