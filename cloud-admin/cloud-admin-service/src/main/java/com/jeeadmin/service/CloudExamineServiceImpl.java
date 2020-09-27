package com.jeeadmin.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeadmin.api.ICloudExamineService;
import com.jeeadmin.api.ICloudExamineUserService;
import com.jeeadmin.api.ICloudPartyMemberService;
import com.jeeadmin.api.ICloudRecordService;
import com.jeeadmin.entity.CloudExamine;
import com.jeeadmin.entity.CloudExamineUser;
import com.jeeadmin.entity.CloudPartyMember;
import com.jeeadmin.entity.CloudRecord;
import com.jeeadmin.mapper.CloudExamineMapper;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CloudExamineServiceImpl extends BaseServiceImpl<CloudExamineMapper, CloudExamine> implements ICloudExamineService {

    @Autowired
    private SnowFlake snowFlake;

    @Autowired
    private ICloudExamineUserService cloudExamineUserService;

    @Autowired
    private ICloudPartyMemberService cloudPartyMemberService;

    @Autowired
    private ICloudRecordService cloudRecordService;

    @Autowired
    private CloudExamineMapper cloudExamineMapper;

    /**
     * 查询所有审核信息，进行分页
     * @param pageHelper
     * @return
     */
    @Override
    public Page<CloudExamine> selectAll(PageHelper<CloudExamine> pageHelper) {
        Page<CloudExamine> page = new Page<>(pageHelper.getCurrent(), pageHelper.getSize());
        CloudExamine cloudExamine = pageHelper.getData();
        Long userId = /*SecurityUtil.getUserId()*/2L;
        //通过用户id查询该用户对应的党员id
        CloudPartyMember partyMember = cloudPartyMemberService.getPartyMemberByUserId(userId);
        if (null == partyMember){
            throw new ValidateException("党员信息为空");
        }
        //查询用户审核关系表中该党员可以审核的examine_id
        List<CloudExamineUser> examineUserList = cloudExamineUserService.selectByPartMemberId(partyMember.getId());
        List<CloudExamine> examineList = new ArrayList<>();
        //examine_id就是审核表的主键id，根据主键id查询可以审核的数据集合
        if (null != examineUserList && !examineUserList.isEmpty()){
            for (CloudExamineUser examineUser : examineUserList) {
                LambdaQueryWrapper<CloudExamine> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(CloudExamine::getId,examineUser.getExamineId());
                //如果用户选择了某个状态的审核数据，就去匹配，没有选择就默认查询出未被删除的数据
                if (null != cloudExamine && !StringUtils.isEmpty(cloudExamine.getExamineStatus())){
                    String examineStatus = cloudExamine.getExamineStatus();
                    // 未审核状态/待处理状态
                    if(examineStatus.equals(ExamineEnum.UNREVIEWED)){
                        wrapper.eq(CloudExamine::getExamineStatus,ExamineEnum.UNREVIEWED.getCode());
                    }else if (examineStatus.equals(ExamineEnum.PROCESSED)){
                        //已处理状态
                        wrapper.in(CloudExamine::getExamineStatus,ExamineEnum.NORMAL,ExamineEnum.REJECT);
                    }else if (examineStatus.equals(ExamineEnum.INITIATED)){
                        //已发起状态，默认查询未被删除的全部数据
                        wrapper.ne(CloudExamine::getExamineStatus,ExamineEnum.REMOVE.getCode());
                    }
                }else {
                    wrapper.ne(CloudExamine::getExamineStatus,ExamineEnum.REMOVE.getCode());
                }
                examineList.add(this.getOne(wrapper));
            }
        }
        //examineList:审核数据的集合
        if (!examineList.isEmpty()){
            for (CloudExamine examine : examineList) {
                //this.getDetail(examine);
                //外出报备的状态码为：7
                if (null != examine.getExamineTypeCode() && "7".equals(examine.getExamineTypeCode())){
                    //根据外出报备的主键查询详情
                    CloudRecordVo recordVo = cloudRecordService.selectOne(examine.getForeignId());
                    if (null != recordVo && recordVo.getId().equals(examine.getForeignId())){
                        List<CloudRecordVo> list = new ArrayList<>();
                        list.add(recordVo);
                        examine.setRecordList(list);
                    }
                }
            }
            page.setRecords(examineList);
        }
        return page;
    }

    /**
     * 查询单个审核的详细信息
     * @param examineId
     * @return
     */
    @Override
    public CloudExamine getDetail(Long examineId) {
        //通过审核主键获取数据
        CloudExamine cloudExamine = this.getById(examineId);
        if (null == cloudExamine){
            throw new ValidateException("无法获取审核数据");
        }
        ArrayList<CloudRecordVo> list = new ArrayList<>();
        if ("7".equals(cloudExamine.getExamineTypeCode())){
            CloudRecordVo cloudRecordVo = cloudRecordService.selectOne(cloudExamine.getForeignId());
            list.add(cloudRecordVo);
        }
        cloudExamine.setRecordList(list);
        return cloudExamine;
    }

    /**
     * 新增审核信息
     * @param cloudExamine
     * @return
     */
    @Transactional(rollbackFor = ValidateException.class)
    @Override
    public CloudExamine saveExamine(CloudExamine cloudExamine) {
        long id = snowFlake.nextId();
        cloudExamine.setId(id);
        //1：正常，2：删除，3：驳回，4：未审核
        cloudExamine.setExamineStatus(ExamineEnum.UNREVIEWED.getCode());
        //外出报备类型
        cloudExamine.setExamineTypeCode(cloudExamine.getExamineTypeCode());
        cloudExamine.setCreateUser(/*SecurityUtil.getUserId()*/1L);
        cloudExamine.setCreateDate(new Date());
        if (this.save(cloudExamine)){
            CloudExamineUser cloudExamineUser = new CloudExamineUser();
            //审核id
            cloudExamineUser.setExamineId(id);
            cloudExamineUserService.saveExamineUser(cloudExamineUser);
            return cloudExamine;
        }else {
            throw new ValidateException("新增审核信息失败");
        }
    }

    /**
     * 进行审核,更改审核状态
     * @author ymz
     * @param cloudExamine
     * @return
     */
    @Transactional(rollbackFor = ValidateException.class)
    @Override
    public boolean updateStatus(CloudExamine cloudExamine) {
        if (null == cloudExamine){
            throw new ValidateException("审核数据不能为空");
        }
        if (null == this.getById(cloudExamine.getId())){
            throw new ValidateException("未查询到该审核数据");
        }
        UpdateWrapper<CloudExamine> wrapper = new UpdateWrapper<>();
        wrapper.lambda().eq(CloudExamine::getId,cloudExamine.getId());
        //更新该条审核数据的状态
        wrapper.lambda().set(CloudExamine::getExamineStatus,cloudExamine.getExamineStatus());
        wrapper.lambda().set(CloudExamine::getUpdateDate,new Date());
        wrapper.lambda().set(CloudExamine::getUpdateUser,SecurityUtil.getUserId());
        //如果驳回信息不为空
        wrapper.lambda().set(!StringUtils.isEmpty(cloudExamine.getExamineRejectReason()),CloudExamine::getExamineRejectReason,cloudExamine.getExamineRejectReason());
        if (this.update(new CloudExamine(),wrapper)){
            CloudRecord cloudRecord = new CloudRecord();
            cloudRecord.setRecordStatus(cloudExamine.getExamineStatus());
            cloudRecord.setId(cloudExamine.getForeignId());
            cloudRecordService.updateStatus(cloudRecord);
            return true;
        }else {
            throw new ValidateException("审核失败");
        }
    }

    /**
     * 根据外键id逻辑删除审核数据
     * @param foreignId
     * @return
     */
    @Transactional(rollbackFor = ValidateException.class)
    @Override
    public boolean deleteByForeignId(Long foreignId) {
        QueryWrapper<CloudExamine> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(CloudExamine::getForeignId,foreignId);
        wrapper.lambda().ne(CloudExamine::getExamineStatus,ExamineEnum.REMOVE);
        CloudExamine examine = this.getOne(wrapper);
        if (null == examine){
            throw new ValidateException("查询不到审批数据");
        }
        //examine.setExamineStatus()
        UpdateWrapper<CloudExamine> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().set(CloudExamine::getExamineStatus,ExamineEnum.REMOVE);
        updateWrapper.lambda().eq(CloudExamine::getId,examine.getId());
        updateWrapper.lambda().eq(CloudExamine::getForeignId,foreignId);
        if (this.update(new CloudExamine(),updateWrapper)){
            return cloudExamineUserService.deleteByExamineId(examine.getId());
        }
        return false;
    }
}
