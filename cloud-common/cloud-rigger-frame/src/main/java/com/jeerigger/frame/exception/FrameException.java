package com.jeerigger.frame.exception;


import com.jeerigger.frame.base.controller.ResultCodeEnum;

/**
 * 系统异常
 */
public class FrameException extends BaseException {

    private ResultCodeEnum resultCode;

    /**
     * 构造函数 自定义异常类
     *
     * @param throwable
     */
    public FrameException(Throwable throwable) {
        super(throwable);
    }

    /**
     * 构造函数 自定义异常类
     *
     * @param message
     */
    public FrameException(String message) {
        super(message);
    }

    /**
     * 构造函数 自定义异常类
     *
     * @param message
     * @param throwable
     */
    public FrameException(String message, Throwable throwable) {
        super(message, throwable);
    }

    /**
     * 构造函数 自定义异常类
     *
     * @param resultCode
     */
    public FrameException(ResultCodeEnum resultCode) {
        super(resultCode.getMessage());
        this.resultCode = resultCode;
    }

    /**
     * 构造函数 自定义异常类
     *
     * @param resultCode
     * @param throwable
     */
    public FrameException(ResultCodeEnum resultCode, Throwable throwable) {
        super(throwable);
        this.resultCode = resultCode;
    }

    /**
     * 构造函数 自定义异常类
     *
     * @param resultCode
     * @param message
     */
    public FrameException(ResultCodeEnum resultCode, String message) {
        super(message);
        this.resultCode = resultCode;
    }

    /**
     * 构造函数 自定义异常类
     *
     * @param resultCode
     * @param message
     * @param throwable
     */
    public FrameException(ResultCodeEnum resultCode, String message, Throwable throwable) {
        super(message, throwable);
        this.resultCode = resultCode;
    }

    @Override
    public ResultCodeEnum getResultCode() {
        if (this.resultCode != null) {
            return this.resultCode;
        } else {
            return ResultCodeEnum.ERROR_SYSTEM_EXCEPTION;
        }
    }
}
