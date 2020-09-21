package com.jeeadmin.service;

import com.jeeadmin.api.ICloudExamineUserService;
import com.jeeadmin.entity.CloudExamineUser;
import com.jeeadmin.mapper.CloudExamineUserMapper;
import com.jeerigger.core.common.core.SnowFlake;
import com.jeerigger.frame.base.service.impl.BaseServiceImpl;
import com.jeerigger.frame.exception.ValidateException;
import com.jeerigger.frame.support.validate.ValidateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Company YXH
 * @Author ryh
 * @Date Create in 2020/9/19 11:02
 * @Description:
 */
@Service
public class CloudExamineUserServiceImpl extends BaseServiceImpl<CloudExamineUserMapper, CloudExamineUser> implements ICloudExamineUserService {

    @Autowired
    private SnowFlake snowFlake;

    /**
     *      新增审核用户关系
     * @param cloudExamineUser
     * @return
     */
    @Override
    public CloudExamineUser saveExamineUser(CloudExamineUser cloudExamineUser) {
        ValidateUtil.validateObject(cloudExamineUser);
        if (cloudExamineUser == null){
            throw new ValidateException("当前新增数据为空");
        }
        if (this.save(cloudExamineUser)){
            return cloudExamineUser;
        }else {
            throw new ValidateException("新增审核用户关系失败");
        }
    }

}
