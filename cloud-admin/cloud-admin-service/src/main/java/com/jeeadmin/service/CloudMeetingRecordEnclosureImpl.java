package com.jeeadmin.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jeeadmin.api.ICloudMeetingRecordEnclosureService;
import com.jeeadmin.entity.CloudMeetingRecordEnclosure;
import com.jeeadmin.mapper.CloudMeetingRecordEnclosureMapper;
import com.jeerigger.frame.base.service.impl.BaseServiceImpl;
import com.jeerigger.frame.exception.ValidateException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author: Sgz
 * @time: 2020/9/15 15:24
 * @description:
 */
@Service
public class CloudMeetingRecordEnclosureImpl extends BaseServiceImpl<CloudMeetingRecordEnclosureMapper, CloudMeetingRecordEnclosure>
                                        implements ICloudMeetingRecordEnclosureService {



    /**
     * 查询所有的会议记录附件信息
     *
     * @return
     */
    @Override
    public List<CloudMeetingRecordEnclosure> selectAllMeetingRecordEnclosure() {
        QueryWrapper<CloudMeetingRecordEnclosure> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().select();
        return this.list(queryWrapper);
    }

    /**
     * 根据会议记录附件id查询该会议记录附件
     *
     * @param id
     * @return
     */
    @Override
    public CloudMeetingRecordEnclosure selectOne(Long id) {
        QueryWrapper<CloudMeetingRecordEnclosure> queryWrapper = new QueryWrapper<>();
        if (Objects.isNull(id)){
            throw new ValidateException("会议附件的id不能为空");
        }
        queryWrapper.lambda().eq(CloudMeetingRecordEnclosure::getEnclosureId,id);
        if (Objects.isNull(queryWrapper)){
            throw new ValidateException("没有该会议附件记录");
        }
        return this.getOne(queryWrapper);

    }

    /**
     * 新增会议记录附件信息
     *
     * @param cloudMeetingRecordEnclosure
     * @return
     */
    @Override
    public boolean saveMeetingRecordEnclosure(CloudMeetingRecordEnclosure cloudMeetingRecordEnclosure) {
        if (Objects.isNull(cloudMeetingRecordEnclosure)){
            throw new ValidateException("没有获取到会议附件记录信息");
        }
        return this.save(cloudMeetingRecordEnclosure);
    }

    /**
     * 根据会议附件id删除会议记录附件
     *
     * @param id
     * @return
     */
    @Override
    public boolean deleteMeetingRecordEnclosure(Long id) {
        if (Objects.isNull(id)){
            throw new ValidateException("没有获取到会议记录附件信息");
        }
        return this.removeById(id);
    }

    /**
     * 更新会议记录附件信息
     *
     * @param cloudMeetingRecordEnclosure
     * @return
     */
    @Override
    public boolean updateMeetingRecordEnclosure(CloudMeetingRecordEnclosure cloudMeetingRecordEnclosure) {
        if (Objects.isNull(cloudMeetingRecordEnclosure)){
            throw new ValidateException("没有获取到会议记录附件信息");
        }
       return this.updateById(cloudMeetingRecordEnclosure);
    }
}
