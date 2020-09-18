package com.jeeadmin.vo.notice;

import com.jeeadmin.entity.CloudEnclosure;
import com.jeeadmin.entity.CloudNotice;
import com.jeeadmin.entity.CloudNoticeEnclosure;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

/**
 * @Company YXH
 * @Author ryh
 * @Date Create in 2020/9/15 18:15
 * @Description:
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CloudNoticeVo extends CloudNotice {

    /**
     *  上传的党员姓名
     */
    private String MemberName;

    /**
     *  附件信息
     */
    private List<CloudEnclosure> enclosureList;

    /**
     *  公告附件关系
     */
    private List<CloudNoticeEnclosure> noticeEnclosures;

    /**
     *   附件的ID
     */
    private Long cloudEnclosureId;

    /**
     * a文件名称
     */
    private String enclosureName;

    /**
     *  是否图片页面上显示
     */
    private String portalFlag;


    private Date createDate;

}
