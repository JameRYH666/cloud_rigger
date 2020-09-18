package com.jeeadmin.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.jeeadmin.api.*;

import com.jeeadmin.entity.*;

import com.jeeadmin.mapper.CloudMeetingMapper;
import com.jeeadmin.vo.meeting.CloudMeetingDetailVo;
import com.jeeadmin.vo.meeting.CloudMeetingVo;
import com.jeerigger.core.common.core.SnowFlake;
import com.jeerigger.frame.base.service.impl.BaseServiceImpl;
import com.jeerigger.frame.enums.MeetingAndActivityEnum;
import com.jeerigger.frame.exception.FrameException;
import com.jeerigger.frame.exception.ValidateException;
import com.jeerigger.frame.page.PageHelper;
import com.jeerigger.frame.support.validate.ValidateUtil;
import com.jeerigger.frame.util.StringUtil;
import com.jeerigger.security.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author: Sgz
 * @time: 2020/9/11 17:19
 * @description:
 *  会议信息类
 */
@Service
public class CloudMeetingServiceImpl extends BaseServiceImpl<CloudMeetingMapper, CloudMeeting> implements ICloudMeetingService {

    @Autowired
    private SnowFlake snowFlake;

    @Autowired
    private CloudMeetingMapper cloudMeetingMapper;
    @Autowired
    private ICloudMeetingEnclosureService cloudMeetingEnclosureServiceImpl;
    @Autowired
    private ICloudMeetingPartyMemberService cloudMeetingPartyMemberServiceImpl;
    @Autowired
    private ICloudEnclosureService cloudEnclosureServiceImpl;
    @Autowired
    private ICloudMeetingActiveTypeService cloudMeetingActiveTypeServiceImpl;
    @Autowired
    private ICloudMeetingRecordService cloudMeetingRecordServiceImpl;
    @Autowired
    private ICloudMeetingRecordEnclosureService cloudMeetingRecordEnclosureServiceImpl;
    /**
     * @param pageHelper
     * @Author: Sgz
     * @Time: 16:56 2020/9/11
     * @Params: [pageHelper]
     * @Return: com.baomidou.mybatisplus.extension.plugins.pagination.Page<com.jeeadmin.entity.CloudMeeting>
     * @Throws:
     * @Description: 查询所有的会议信息，并进行分页处理
     */
    @Override
    public Page<CloudMeetingVo> selectPage(PageHelper<CloudMeetingVo> pageHelper) {
        Page<CloudMeeting> page = new Page<>(pageHelper.getCurrent(), pageHelper.getSize());
        QueryWrapper<CloudMeeting> queryWrapper = new QueryWrapper<>();
        if(pageHelper.getData() != null){
            CloudMeeting meetingData = pageHelper.getData();
            // 根据活动地址进行条件查询
            if(StringUtil.isNotEmpty(meetingData.getMeetingAddress())){
                queryWrapper.lambda().like(CloudMeeting::getMeetingAddress,meetingData.getMeetingAddress());
            }
            //根据活动形式进行条件查询
            if (StringUtil.isNotEmpty(meetingData.getFormCode())){
                queryWrapper.lambda().eq(CloudMeeting::getFormCode,meetingData.getFormCode());
            }
            // 根据活动类型进行条件查询
            if (StringUtil.isNotEmpty(meetingData.getTypeCode())){
                queryWrapper.lambda().eq(CloudMeeting::getTypeCode,meetingData.getTypeCode());
            }
        }
        queryWrapper.lambda().orderByAsc(CloudMeeting::getMeetingTime);
        // 查询所有符合条件的会议分页信息
        IPage<CloudMeeting> meetingPage = this.page(page, queryWrapper);
        // 获取所有会议信息
        List<CloudMeeting> cloudMeetingList = meetingPage.getRecords();
        // 获取所有的会议id信息存入新的集合中
        List<Long> ids = new ArrayList<Long>();
        for(CloudMeeting cloudMeeting : cloudMeetingList) {
            ids.add(cloudMeeting.getId());
        }
        // 查询复合信息
        Page<CloudMeetingVo> meetingVoPage = new Page<>(pageHelper.getCurrent(), pageHelper.getSize());
        meetingVoPage.setRecords(cloudMeetingMapper.selectAllMeetings(ids));
        return meetingVoPage;
    }

    /**
     * @Author: Sgz
     * @Time:  2020/9/11
     * @Params: [id]
     * @Return: com.jeeadmin.entity.CloudMeeting
     * @Throws:
     * @Description:
     *  根据会议id查询会议的详细信息
     *
     */
    @Override
    public CloudMeetingDetailVo selectOneMeeting(Long id) {
        if(Objects.isNull(id)){
            throw new ValidateException("会议的Id不能为空");
        }
        // 查询会议详情信息(没有参与人和附件信息)
        CloudMeetingDetailVo meeting = cloudMeetingMapper.selectMeetingDetail(id);
        // 查询该次会议参与人(所有的参与人信息中会议的id是一样的)
        List<String> memberNameList = cloudMeetingMapper.selectJoinMembersByMeetingId(id);
        if(memberNameList.size() > 0) {
            meeting.setJoinMemberName(memberNameList);
        }
        // 查询该次会议的附件(所有的附件信息中会议的id是一样的)
        List<CloudEnclosure> cloudEnclosureList = cloudMeetingMapper.selectEnclosuresByMeetingId(id);
        if(cloudEnclosureList.size() > 0) {
            meeting.setEnclosureList(cloudEnclosureList);
        }
        if (null == meeting){
            throw new ValidateException("会议数据为空");
        }
        return meeting;
    }

   /**
    * @Author: Sgz
    * @Time: 17:38 2020/9/11
    * @Params: [meeting]
    * @Return: com.jeeadmin.entity.CloudMeeting
    * @Throws:
    * @Description:
    *   增加会议信息
    *todo bug Cause: java.sql.SQLException: No value specified for parameter 1
    */
    @Override
    public boolean saveMeeting(CloudMeetingVo meeting) {
        CloudMeeting cloudMeeting = new CloudMeeting();
        CloudMeetingPartyMember cloudMeetingPartyMember = new CloudMeetingPartyMember();


        // 检验会议数据是否存在
        ValidateUtil.validateObject(meeting);




        // todo 增加会议信息的同时增加参会人员和附件信息 以及会议类型
       // 判断是否拿到了会议信息
        if (Objects.nonNull(meeting)) {
            // 新增会议信息
            cloudMeeting.setId(snowFlake.nextId());
            cloudMeeting.setMeetingTile(meeting.getMeetingTile())
                    .setTypeCode(meeting.getTypeCode())
                    .setMeetingAddress(meeting.getMeetingAddress())
                    .setMeetingComment(meeting.getMeetingComment())
                    .setFormCode(meeting.getFormCode())
                    .setRemark(meeting.getRemark())
                    .setMeetingTime(meeting.getMeetingTime())
                    .setCreateUser(SecurityUtil.getUserId())
                    .setCreateDate(new Date());
            cloudMeeting.setMeetingStatus(MeetingAndActivityEnum.NOREVIEWED.getCode());
            // 保存会议信息
            if (this.saveOne(cloudMeeting)) {

                    // 如果保存成功，就增加人员信息
                    List<CloudMeetingPartyMember> cloudMeetingPartyMembers = meeting.getCloudMeetingPartyMembers();
                    // 判断新增参会人员信息
                    boolean b = cloudMeetingPartyMemberServiceImpl.saveMeetingMember(cloudMeetingPartyMembers);
                    if (!b) {
                        throw new ValidateException("新增参会人员信息失败");
                    }

                    // 新增参会人员信息成功，增加附件信息
                    List<CloudEnclosure> cloudEnclosures = meeting.getCloudEnclosures();
                    // 判断是否拿到附件信息
                    if (cloudEnclosures.size() > 0) {
                        // 如果拿到附件信息 ，遍历循环拿取数据
                        for (CloudEnclosure cloudEnclosure : cloudEnclosures) {
                            // 新增附件信息
                            return cloudEnclosureServiceImpl.saveEnclosure(cloudEnclosure);
                        }
                    }




            }
        }
            throw new FrameException("新增会议信息数据失败");

    }

    /**
     * @Author: Sgz
     * @Time: 17:38 2020/9/11
     * @Params: [meeting]
     * @Return: boolean
     * @Throws:
     * @Description:
     * 更新会议信息
     * TODO 更新会议不需要
     *
     */
    @Override
    public boolean updateMeeting(CloudMeetingVo meeting) {
        // 通过主键获取到会议数据信息
        CloudMeeting oldData = this.getById(meeting.getId());
        // 判断数据是否存在
        if (oldData == null){
            // 如果会议信息不存在 直接抛出异常
            throw new ValidateException("更新的会议信息不存在");
        }
        // 通过校验数据是否存在
        ValidateUtil.validateObject(meeting);

        if (!oldData.getCreateUser().equals(meeting.getCreateUser())){
            throw new ValidateException("会议发起人不能进行修改");
        }
        // 通过security获取到userId，并把数据同步更新到数据库
        meeting.setUpdateUser(SecurityUtil.getUserId());
        meeting.setUpdateDate(new Date());
        // 如果数据更新成功，要判断参会人员是否更新，如果更新，要同步到参会人员表


            return true;

    }

   /**
    * @Author: Sgz
    * @Time: 17:38 2020/9/11
    * @Params: [meetingId]
    * @Return: boolean
    * @Throws:
    * @Description:
    * 删除会议信息,同时也要删除参会人员信息和附件信息
    * 如果中间有一个环节出错，就执行代码回滚，全部返回数据
    *   todo 删除会议记录及其附件
    *
    */
    @Override
    @Transactional(rollbackFor = ValidateException.class)
    public boolean deleteMeeting(Long meetingId) {
        CloudMeeting oldData = this.getById(meetingId);
        if (oldData == null){
           throw new ValidateException("该会议不存在");
        }
        // 如果删除会议成功，就同步删除参会人员和附件信息
        boolean b = this.removeById(meetingId);
        if(!b){
            throw new ValidateException("删除会议失败");
        }
        // 先证明参会人员存在，才能进行删除操作
        List<CloudMeetingDetailVo> allMeetingMembers = cloudMeetingPartyMemberServiceImpl.getAllMeetingMembers(meetingId);
        if (allMeetingMembers.size()>0) {
            // 删除参会人员信息
            b = cloudMeetingPartyMemberServiceImpl.deleteMeetingMember(meetingId);
            if (!b) {
                throw new ValidateException("删除参会人员失败");
            }

        }
        // 删除附件以及会议附件中间表
        // 这里也需要验证会议附件是存在的
        List<CloudEnclosure> cloudEnclosures = cloudEnclosureServiceImpl.selectEnclosuresByMeetingId(meetingId);

        if (cloudEnclosures.size()>0) {
            b = cloudEnclosureServiceImpl.deleteEnclosure(meetingId);
            if (!b) {
                throw new ValidateException("删除会议附件失败");
            }
            // 通过会议id拿到会议附件的信息
            List<CloudMeetingEnclosure> cloudMeetingEnclosures = cloudMeetingEnclosureServiceImpl.selectMeetingEnclosuresByMeetingId(meetingId);
            if (cloudEnclosures.size() > 0) {
                for (CloudMeetingEnclosure cloudMeetingEnclosure : cloudMeetingEnclosures) {
                    b = cloudMeetingEnclosureServiceImpl.deleteMeetingEnclosure(cloudMeetingEnclosure.getId());
                    if (!b) {
                        throw new ValidateException("删除会议附件信息失败");
                    }
                }
            }

        }
        // 删除会议记录
        List<CloudMeetingRecord> cloudMeetingRecords = cloudMeetingRecordServiceImpl.selectRecords(meetingId);
       // list用于存储会议记录的id，方便下面使用
        List<Long> idList = new ArrayList<>();
        if (cloudEnclosures.size()>0){
            // 只有当会议记录存在的时候才能删除
            for (CloudMeetingRecord cloudMeetingRecord : cloudMeetingRecords) {
                b = cloudMeetingRecordServiceImpl.deleteRecord(cloudMeetingRecord.getId());
                idList.add(cloudMeetingRecord.getId());
                if (!b){
                    throw new ValidateException("删除会议记录失败");
                }
            }
        }
        // 删除会议记录附件表
            // 存储的是附件id
        ArrayList<Long> ids = new ArrayList<>();
        if (idList.size()>0){
          // 如果可以获取到list说明会议记录含有数据
          for (Long meetingRecordId : idList) {
              // 通过遍历循环获取数据
              List<CloudMeetingRecordEnclosure> cloudMeetingRecordEnclosures = cloudMeetingRecordEnclosureServiceImpl.selectMeetingRecordEnclosures(meetingRecordId);
              if (cloudMeetingRecordEnclosures.size()>0){
                  for (CloudMeetingRecordEnclosure cloudMeetingRecordEnclosure : cloudMeetingRecordEnclosures) {
                      ids.add(cloudMeetingRecordEnclosure.getEnclosureId());
                      b = cloudMeetingRecordEnclosureServiceImpl.deleteMeetingRecordEnclosure(cloudMeetingRecordEnclosure.getId());
                      if (!b){
                          throw new ValidateException("删除会议记录附件失败");
                      }
                  }
              }

          }
      }
        // 删除会议附件信息
        if(ids.size()>0){
            for (Long id : ids) {
                b = cloudEnclosureServiceImpl.deleteEnclosures(id);
                if (!b){
                    throw new ValidateException("删除会议附件失败");
                }
            }
        }

        return true;


    }

    /**
     * @Author: Sgz
     * @Time: 14:04 2020/9/14
     * @Params: [cloudMeeting]
     * @Return: boolean
     * @Throws:
     * @Description:
     *  新增会议信息 同时需要增加参会人员和附件记录
     */
    @Override
    public boolean saveOne(CloudMeeting cloudMeeting) {
        cloudMeeting.setId(snowFlake.nextId())
                    .setCreateUser(SecurityUtil.getUserId());
        cloudMeeting.setMeetingStatus(MeetingAndActivityEnum.NOREVIEWED.getCode());
        cloudMeeting.setCreateDate(new Date());



        // 判断是否获取到会议信息
       if (Objects.nonNull(cloudMeeting)){
            return cloudMeetingMapper.saveOne(cloudMeeting);
       }

       throw new ValidateException("没有会议信息");
    }

    /**
     * @Author: Sgz
     * @Time: 11:28 2020/9/15
     * @Params: [cloudMeeting]
     * @Return: boolean
     * @Throws:
     * @Description:
     * 改变会议状态，实现逻辑删除
     *  第一步，改变会议状态码，从待审核状态码4---》删除状态码2
     *  第二步，直接删除会议附件信息，外键是meeting_id，
     *          这边需要先查询会议附件信息是否存在
     *  第三步，删除参会人员信息
     *
     */
    @Transactional(rollbackFor = ValidateException.class)
    @Override
    public boolean updateStatus(CloudMeeting cloudMeeting) {
        // 判断是否获取到了会议信息
        if (Objects.nonNull(cloudMeeting)){

            // 修改会议状态
            boolean b = cloudMeetingMapper.updateStatus(cloudMeeting);
            if (!b){
                throw new ValidateException("更改会议状态失败");
            }
             b = cloudMeetingPartyMemberServiceImpl.deleteMeetingMember(cloudMeeting.getId());
            if (!b){
                throw new ValidateException("参会人员删除失败");
            }
            // 删除会议附件信息
          // 首先通过会议附件表获取到附件表的id
            List<CloudMeetingEnclosure> cloudMeetingEnclosures = cloudMeetingEnclosureServiceImpl.selectMeetingEnclosuresByMeetingId(cloudMeeting.getId());
            for (CloudMeetingEnclosure cloudMeetingEnclosure : cloudMeetingEnclosures) {
                b = cloudEnclosureServiceImpl.deleteEnclosure(cloudMeetingEnclosure.getEnclosureId());
                if (!b){
                    throw  new ValidateException("删除附件失败");
                }
                return true;
            }

        }
        throw new ValidateException("没有获取到该会议的数据");
    }

}
