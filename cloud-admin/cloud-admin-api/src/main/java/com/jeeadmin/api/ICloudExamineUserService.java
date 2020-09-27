package com.jeeadmin.api;

import com.jeeadmin.entity.CloudExamineUser;
import com.jeerigger.frame.base.service.BaseService;

import java.util.List;

public interface ICloudExamineUserService extends BaseService<CloudExamineUser> {

    /**
     *      新增审核用户关系
     */
    CloudExamineUser saveExamineUser(CloudExamineUser cloudExamineUser);

    /**
     * 根据审核人id查询该审核人需要审核的数据的集合
     * @param partMemberId
     * @return
     */
    List<CloudExamineUser> selectByPartMemberId(Long partMemberId);

    /**
     * 通过examine_id删除用户审核关系表中的数据
     * @param examineId
     * @return
     */
    boolean deleteByExamineId(Long examineId);
}
