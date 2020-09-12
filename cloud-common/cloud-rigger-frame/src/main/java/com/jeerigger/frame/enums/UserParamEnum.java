package com.jeerigger.frame.enums;

/**
 * @Author Seven Lee
 * @Date Create in 2020/9/12 13:39
 * @Description
 **/
public enum UserParamEnum {

    LOGIN_NAME("loginName"),
    PASSWORD("password");

    /**
     * 参数值
     */
    private String paramValue;

    UserParamEnum(String paramValue) {
        this.paramValue = paramValue;
    }

    public String getParamValue() {
        return paramValue;
    }

}
