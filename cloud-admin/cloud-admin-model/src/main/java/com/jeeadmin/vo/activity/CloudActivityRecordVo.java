package com.jeeadmin.vo.activity;

import com.jeeadmin.entity.CloudActivityRecord;
import com.jeeadmin.entity.CloudEnclosure;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Company YXH
 * @Author ryh
 * @Date Create in 2020/9/13 14:17
 * @Description:
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CloudActivityRecordVo extends CloudActivityRecord {

    /**
     *  活动的ID
     */
    private Long activityId;

    /**
     *  活动记录的创建人
     */
    private String createName;

    /**
     *  附件信息
     */
    private List<CloudEnclosure> enclosureList;

    /**
     *    附件的ID
     */
    private Long cloudEnclosureId;
}
