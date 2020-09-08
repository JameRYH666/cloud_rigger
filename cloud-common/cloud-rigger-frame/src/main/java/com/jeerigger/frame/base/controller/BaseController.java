package com.jeerigger.frame.base.controller;

import com.jeerigger.frame.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseController {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * 返回成功结果集
     *
     * @return
     */
    protected ResultData success() {
        FastJSON fastJson = new FastJSON();
        fastJson.setCode(ResultCodeEnum.SUCCESS.getCode());
        fastJson.setMessage(ResultCodeEnum.SUCCESS.getMessage());
        return new ResultData(fastJson);
    }

    /**
     * 返回成功结果集
     *
     * @param message 指定返回消息
     * @return
     */
    protected ResultData success(String message) {
        FastJSON fastJson = new FastJSON();
        fastJson.setCode(ResultCodeEnum.SUCCESS.getCode());
        fastJson.setMessage(message);
        return new ResultData(fastJson);
    }

    /**
     * 返回成功结果集
     *
     * @param data 返回的数据结果集
     * @return
     */
    protected ResultData success(Object data) {
        FastJSON fastJson;
        if (data instanceof FastJSON){
            fastJson = (FastJSON) data;
        }else {
            fastJson = new FastJSON<>();
            fastJson.setData(data);
        }
        fastJson.setCode(ResultCodeEnum.SUCCESS.getCode());
        fastJson.setMessage(ResultCodeEnum.SUCCESS.getMessage());
        return new ResultData(fastJson);
    }

    /**
     * 返回成功结果集
     *
     * @param message 指定返回消息
     * @param data    返回的数据结果集
     * @return
     */
    protected ResultData success(Object data,String message) {
        FastJSON fastJson;
        if (data instanceof FastJSON){
            fastJson = (FastJSON) data;
        }else {
            fastJson = new FastJSON<>();
            fastJson.setData(data);
        }

        fastJson.setCode(ResultCodeEnum.SUCCESS.getCode());
        fastJson.setMessage(message);
        return new ResultData(fastJson);
    }

    /**
     * 指定类型返回结果
     *
     * @param resultCode
     * @return
     */
    protected ResultData failed(IResultCode resultCode) {
        FastJSON fastJson = new FastJSON();
        fastJson.setCode(resultCode.getCode());
        if (StringUtil.isNotEmpty(resultCode.getMessage())) {
            fastJson.setMessage(resultCode.getMessage());
        } else {
            fastJson.setMessage("未知错误信息！");
        }
        return new ResultData(fastJson);
    }

    /**
     * 指定类型返回结果
     *
     * @param resultCode
     * @param message
     * @return
     */
    protected ResultData failed(IResultCode resultCode, String message) {
        FastJSON fastJson = new FastJSON();
        fastJson.setCode(resultCode.getCode());
        if (StringUtil.isEmpty(message)) {
            message = resultCode.getMessage();
        }
        if (StringUtil.isNotEmpty(message)) {
            fastJson.setMessage(message);
        } else {
            fastJson.setMessage("未知错误信息！");
        }
        return new ResultData(fastJson);
    }

    /**
     * 指定类型返回结果
     * @param resultCode
     * @param message
     * @return
     */
    protected ResultData failed(IResultCode resultCode, String message,String details) {
        FastJSON fastJson = new FastJSON();
        fastJson.setCode(resultCode.getCode());
        if (StringUtil.isEmpty(message)) {
            message = resultCode.getMessage();
        }
        if (StringUtil.isNotEmpty(message)) {
            fastJson.setMessage(message);
        } else {
            fastJson.setMessage("未知错误信息！");
        }
        fastJson.setDetails(details);
        return new ResultData(fastJson);
    }
}
