package com.jeeadmin.vo.org;


import com.jeeadmin.entity.CloudOrg;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;



@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class CloudOrgVo extends CloudOrg {

    private Integer count;
    private String orgLeader;
    private Long phoneNumer;
}
