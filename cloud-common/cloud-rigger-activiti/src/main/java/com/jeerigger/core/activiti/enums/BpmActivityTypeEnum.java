package com.jeerigger.core.activiti.enums;

import lombok.Getter;

/**
 * 节点和关口枚举类
 */
@Getter
public enum BpmActivityTypeEnum {

    START_EVENT("startEvent", "开始事件"),
    END_EVENT("endEvent", "结束事件"),
    USER_TASK("userTask", "用户任务"),
    EXCLUSIVE_GATEWAY("exclusiveGateway", "排他网关"),
    PARALLEL_GATEWAY("parallelGateway", "并行网关"),
    INCLUSIVE_GATEWAY("inclusiveGateway", "包含网关");

    private String type;
    private String name;

    BpmActivityTypeEnum(String type, String name) {
        this.type = type;
        this.name = name;
    }
}
