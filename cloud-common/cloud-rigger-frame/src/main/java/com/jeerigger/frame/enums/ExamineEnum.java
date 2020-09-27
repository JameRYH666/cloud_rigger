package com.jeerigger.frame.enums;

/**
 * 审核状态枚举
 */
public enum ExamineEnum {
    NORMAL("1","正常"),
    REMOVE("2","删除"),
    REJECT("3","驳回"),
    UNREVIEWED("4","未审核"),
    PROCESSED("5","已处理"),
    INITIATED("6","已发起");

    /**
     * 状态码
     */
    private String code;

    /**
     * 描述
     */
    private String desc;



    ExamineEnum(String code,String desc){
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }
    public String getDesc() {
        return desc;
    }
}
