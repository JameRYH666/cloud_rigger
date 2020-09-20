package com.jeeadmin.vo.activity;

import com.jeeadmin.entity.CloudActivity;
import com.jeeadmin.entity.CloudEnclosure;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

/**
 * @Company YXH
 * @Author ryh
 * @Date Create in 2020/9/14 11:21
 * @Description:
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CloudActivityVo extends CloudActivity {

    /**
     *  活动组织人
     */
    private String createName;

    /**
     *  附件信息
     */
    private List<CloudEnclosure> enclosureList;

    /**
     *  活动附件id
     */
    private Long cloudEnclosureId;

    private Date createDate;

}
