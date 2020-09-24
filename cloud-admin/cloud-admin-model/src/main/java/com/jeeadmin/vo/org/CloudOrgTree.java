package com.jeeadmin.vo.org;

import com.jeeadmin.entity.CloudOrg;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class CloudOrgTree extends CloudOrg {

    private List<CloudOrg> orgChilds;
}
