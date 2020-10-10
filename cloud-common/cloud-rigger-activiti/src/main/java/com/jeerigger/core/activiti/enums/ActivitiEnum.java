package com.jeerigger.core.activiti.enums;

import lombok.Getter;

/**
 * @ClassName: ActivitiEnum
 * @Description: 流程变量
 * @author: ryh
 */
@Getter
public enum ActivitiEnum {

    COMMENTS("comments", "审批意见"),
    ASSIGNEE("assignee", "审批人"),
    ASSIGNEENAME("assigneeName", "审批人名字"),
    INITIATOR("$INITIATOR", "流程发起人"),
    PASS("pass", "流程判断条件"),
    ORG("org", "流程节点优先级"),
    MONEY("money", "金额"),
    BUSINESSKEY("${businessKey}", "业务主键"),
    STARTUSERID("${startUserId}", "发起人登录名"),
    OUTGOING("outgoing", "流程走向");

    private String variableType;

    private String variableDesc;

    ActivitiEnum(String variableType, String variableDesc) {
        this.variableType = variableType;
        this.variableDesc = variableDesc;
    }

}
