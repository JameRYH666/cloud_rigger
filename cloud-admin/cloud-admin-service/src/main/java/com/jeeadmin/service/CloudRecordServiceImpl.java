package com.jeeadmin.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeadmin.api.ICloudExamineService;
import com.jeeadmin.api.ICloudPartyMemberService;
import com.jeeadmin.api.ICloudRecordService;
import com.jeeadmin.entity.CloudExamine;
import com.jeeadmin.entity.CloudPartyMember;
import com.jeeadmin.entity.CloudRecord;
import com.jeeadmin.mapper.CloudRecordMapper;
import com.jeeadmin.vo.record.CloudRecordVo;
import com.jeerigger.core.common.core.SnowFlake;
import com.jeerigger.frame.base.service.impl.BaseServiceImpl;
import com.jeerigger.frame.enums.ExamineEnum;
import com.jeerigger.frame.exception.ValidateException;
import com.jeerigger.frame.page.PageHelper;
import com.jeerigger.security.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class CloudRecordServiceImpl extends BaseServiceImpl<CloudRecordMapper, CloudRecord> implements ICloudRecordService {

    @Autowired
    private CloudRecordMapper cloudRecordMapper;

    @Autowired
    private SnowFlake snowFlake;

    @Autowired
    private ICloudExamineService cloudExamineService;

    @Autowired
    private ICloudPartyMemberService cloudPartyMemberService;

    /**
     * 新增外出报备数据
     * @param cloudRecord
     * @return
     */
    @Transactional(rollbackFor = ValidateException.class)
    @Override
    public CloudRecord saveCloudRecord(CloudRecord cloudRecord) {
        long id = snowFlake.nextId();
        cloudRecord.setId(id);
        //通过用户的id查询该用户的党员信息
        CloudPartyMember partyMember = cloudPartyMemberService.getPartyMemberByUserId(/*SecurityUtil.getUserId()*/1L);
        if (StringUtils.isEmpty(partyMember)){
            throw new ValidateException("党员信息为空");
        }
        //发起人id为党员id
        cloudRecord.setPartyMemberId(partyMember.getId());
        cloudRecord.setCreateUser(partyMember.getId());
        cloudRecord.setCreateDate(new Date());
        //报备状态(1：正常，2：删除，3：驳回，4：未审核)
        cloudRecord.setRecordStatus(ExamineEnum.UNREVIEWED.getCode());
        if (this.save(cloudRecord)){
            CloudExamine cloudExamine = new CloudExamine();
            //审核类型：外出报备
            cloudExamine.setExamineTypeCode(cloudRecord.getTypeCode());
            cloudExamine.setForeignId(id);
            cloudExamineService.saveExamine(cloudExamine);
            return cloudRecord;
        }else {
            throw new ValidateException("新增外出报备数据失败");
        }
    }

    /**
     * 根据党员id查询该党员未被删除的所有外出报备数据
     * @param pageHelper
     * @return
     */
    @Override
    public Page<CloudRecordVo> selectList(PageHelper<CloudRecord> pageHelper) {
        Page<CloudRecordVo> page = new Page<>(pageHelper.getCurrent(), pageHelper.getSize());
        //通过用户的id查询该用户的党员信息
        CloudPartyMember partyMember = cloudPartyMemberService.getPartyMemberByUserId(/*SecurityUtil.getUserId()*/1L);
        if (StringUtils.isEmpty(partyMember)){
            throw new ValidateException("党员信息为空");
        }
        //根据党员id查询该党员未被删除的所有外出报备数据
        List<CloudRecordVo> list = cloudRecordMapper.selectAll(partyMember.getId());
        page.setRecords(list);
        /*if (null != pageHelper.getData()){
            CloudRecord cloudRecord = pageHelper.getData();
            //查询当前党员所提交的外出报备，所以需要党员id
            if (!Objects.isNull(cloudRecord.getPartyMemberId())){
                //根据党员id查询外出报备数据
                wrapper.lambda().eq(CloudRecord::getPartyMemberId,cloudRecord.getPartyMemberId());
                //如果状态不为空就根据状态查询
                if (!StringUtils.isEmpty(cloudRecord.getRecordStatus())){
                    List<CloudRecord> list = new ArrayList<>();
                    //wrapper.lambda().eq(CloudRecord::getRecordStatus,cloudRecord.getRecordStatus());
                    this.page(page,wrapper);
                    page.getRecords().forEach(record->{
                        if (record.getRecordStatus().equals(cloudRecord.getRecordStatus())){
                            list.add(record);
                        }
                    });
                    page.setRecords(list);
                }else {
                    //如果状态为空，就查询未被删除的数据
                    wrapper.lambda().ne(CloudRecord::getRecordStatus,ExamineEnum.REMOVE.getCode());
                    this.page(page,wrapper);
                }
            }else {
                throw new ValidateException("查询外出报备数据时党员id不能为空");
            }
        }else {
            throw new ValidateException("查询外出报备数据时数据不能为空");
        }*/

        return page;
    }

    /**
     * 根据党员id查询该党员待审核的外出报备数据
     * @param pageHelper
     * @return
     */
    @Override
    public Page<CloudRecordVo> selectNotReview(PageHelper<CloudRecord> pageHelper) {
        Page<CloudRecordVo> page = new Page<>(pageHelper.getSize(),pageHelper.getCurrent());
        //通过用户的id查询该用户的党员信息
        CloudPartyMember partyMember = cloudPartyMemberService.getPartyMemberByUserId(/*SecurityUtil.getUserId()*/1L);
        if (StringUtils.isEmpty(partyMember)){
            throw new ValidateException("党员信息为空");
        }
        //根据党员id查询该党员未被删除的所有外出报备数据
        List<CloudRecordVo> list = cloudRecordMapper.selectNotReview(partyMember.getId());
        page.setRecords(list);
        return page;
    }

    /**
     * 根据党员id查询已处理的外出报备数据
     * @param pageHelper
     * @return
     */
    @Override
    public Page<CloudRecordVo> selectProcessed(PageHelper<CloudRecord> pageHelper) {
        Page<CloudRecordVo> page = new Page<>(pageHelper.getSize(),pageHelper.getCurrent());
        //通过用户的id查询该用户的党员信息
        CloudPartyMember partyMember = cloudPartyMemberService.getPartyMemberByUserId(/*SecurityUtil.getUserId()*/1L);
        if (StringUtils.isEmpty(partyMember)){
            throw new ValidateException("党员信息为空");
        }
        //根据党员id查询该党员未被删除的所有外出报备数据
        List<CloudRecordVo> list = cloudRecordMapper.selectProcessed(partyMember.getId());
        page.setRecords(list);
        return page;
    }

    /**
     * 查询单个外出报备信息
     * @param recordId
     * @return
     */
    @Override
    public CloudRecordVo selectOne(Long recordId) {
        if (Objects.isNull(recordId)){
            throw new ValidateException("查询单个外出报备信息id不能为空");
        }
        CloudRecordVo recordVo = cloudRecordMapper.getDetail(recordId);
        if (null == recordVo){
            throw new ValidateException("外出报备信息不存在");
        }
        return recordVo;
    }

    /**
     * 根据实体类中不为空的字段查询
     * @param cloudRecord
     * @return
     */
    @Override
    public List<CloudRecord> selectByCondition(CloudRecord cloudRecord) {
        LambdaQueryWrapper<CloudRecord> wrapper = new LambdaQueryWrapper<>();
        //如果主键id不为空
        wrapper.eq(!StringUtils.isEmpty(cloudRecord.getId()),CloudRecord::getId,cloudRecord.getId());
        this.getObj(wrapper);
        return null;
    }

    /**
     * 审核完成后更改外出报备数据的状态
     * @param cloudRecord
     * @return
     */
    @Transactional(rollbackFor = ValidateException.class)
    @Override
    public boolean updateStatus(CloudRecord cloudRecord) {
        if (null == this.getById(cloudRecord.getId())){
            throw new ValidateException("外出报备信息不存在");
        }
        UpdateWrapper<CloudRecord> wrapper = new UpdateWrapper<>();
        wrapper.lambda().set(CloudRecord::getRecordStatus,cloudRecord.getRecordStatus());
        wrapper.lambda().set(CloudRecord::getUpdateUser,/*SecurityUtil.getUserId()*/1L);
        wrapper.lambda().set(CloudRecord::getUpdateDate,new Date());
        wrapper.lambda().eq(CloudRecord::getId,cloudRecord.getId());
        return this.update(new CloudRecord(),wrapper);
    }

    /**
     * 逻辑删除外出报备信息
     * @param recordId
     * @return
     */
    @Transactional(rollbackFor = ValidateException.class)
    @Override
    public boolean delete(Long recordId) {
        if (Objects.isNull(recordId)){
            throw new ValidateException("查询单个外出报备信息id不能为空");
        }
        CloudRecord cloudRecord = this.getById(recordId);
        if (null == cloudRecord){
            throw new ValidateException("外出报备信息不存在");
        }
        UpdateWrapper<CloudRecord> wrapper = new UpdateWrapper<>();
        wrapper.lambda().eq(CloudRecord::getId,recordId);
        wrapper.lambda().set(CloudRecord::getRecordStatus,ExamineEnum.REMOVE);
        wrapper.lambda().set(CloudRecord::getUpdateDate,new Date());
        wrapper.lambda().set(CloudRecord::getUpdateUser,/*SecurityUtil.getUserId()*/1L);
        if (this.update(new CloudRecord(),wrapper)){
            return cloudExamineService.deleteByForeignId(recordId);
        }
        return false;
    }
}
