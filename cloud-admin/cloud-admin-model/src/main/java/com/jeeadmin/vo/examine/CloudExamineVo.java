package com.jeeadmin.vo.examine;

import com.jeeadmin.entity.CloudExamine;
import com.jeeadmin.entity.CloudRecord;
import com.jeeadmin.vo.record.CloudRecordVo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CloudExamineVo extends CloudExamine {

    List<CloudRecordVo> recordList;
}
