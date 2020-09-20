package com.jeeadmin.api;

import com.jeeadmin.entity.CloudEnclosure;
import com.jeerigger.frame.base.service.BaseService;

public interface ICloudEnclosureService extends BaseService<CloudEnclosure> {

    boolean deleteEnclosure (Long meetingId);


    boolean saveEnclosure(CloudEnclosure cloudEnclosure);

}
