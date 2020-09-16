package com.jeerigger.frame.enums;


public enum MeetingAndActivityEnum {

    NORMAL("1", "正常"),
    REMOVE("2", "删除"),
    TURN("3", "驳回"),
    NOREVIEWED("4", "未审核");
    /**
     * 码值
     */
    private String code;
    /**
     * 描述
     */
    private String desc;

    MeetingAndActivityEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public static String getDesc(String code) {
        for (MeetingAndActivityEnum meetingAndActivityEnum : MeetingAndActivityEnum.values()) {
            if (meetingAndActivityEnum.code.equals(code)) {
                return meetingAndActivityEnum.desc;
            }
        }
        return "";
    }
    public String getCode() {
        return code;
    }
}
