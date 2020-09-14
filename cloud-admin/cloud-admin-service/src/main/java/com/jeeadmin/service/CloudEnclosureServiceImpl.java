package com.jeeadmin.service;

import com.jeeadmin.api.ICloudEnclosure;
import com.jeeadmin.entity.CloudEnclosure;
import com.jeeadmin.mapper.CloudEnclosureMapper;
import com.jeerigger.frame.base.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @Company YXH
 * @Author ryh
 * @Date Create in 2020/9/13 23:47
 * @Description:
 */
@Service
public class CloudEnclosureServiceImpl extends BaseServiceImpl<CloudEnclosureMapper, CloudEnclosure> implements ICloudEnclosure {
    @Override
    public boolean deleteEnclosure(Long id) {
        return false;
    }
}
