package com.jeeadmin.api;

import com.jeeadmin.entity.CloudExamineUser;
import com.jeerigger.frame.base.service.BaseService;

public interface ICloudExamineUserService extends BaseService<CloudExamineUser> {

    /**
     *      新增审核用户关系
     */
    CloudExamineUser saveExamineUser(CloudExamineUser cloudExamineUser);





}
