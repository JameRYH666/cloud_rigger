package com.jeeadmin.vo.activity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Company YXH
 * @Author ryh
 * @Date Create in 2020/9/9 13:50
 * @Description: 活动信息记录附件VO
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class QueryActivityVo {
    /**
     * 活动标题
     */
    private String activityTile;

    /**
     * 活动记录标题
     */
    private String recordTitle;

    /**
     * 活动类型
     */
    private String activityCode;

    /**
     * 党员id
     */
    private Long partyMemberId;

    /**
     * 党员姓名
     */
    private String memberName;

    /**
     * 活动形式
     */
    private String formCode;
    /**
     * 活动地址
     */
    private String activityAddress;
}
