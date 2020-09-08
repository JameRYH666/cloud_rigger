package com.jeerigger.frame.exception;

public class FileException  extends RuntimeException{

    /**
     * 构造函数 自定义异常类
     *
     * @param throwable
     */
    public FileException(Throwable throwable) {
        super(throwable);
    }

    /**
     * 构造函数 自定义异常类
     *
     * @param message
     */
    public FileException(String message) {
        super(message);
    }


}
