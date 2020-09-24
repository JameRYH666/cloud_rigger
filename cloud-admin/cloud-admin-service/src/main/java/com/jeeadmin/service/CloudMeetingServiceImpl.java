package com.jeeadmin.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.jeeadmin.api.*;

import com.jeeadmin.entity.*;

import com.jeeadmin.mapper.CloudMeetingMapper;
import com.jeeadmin.vo.meeting.CloudMeetingDetailVo;
import com.jeeadmin.vo.meeting.CloudMeetingPartyMemberVo;
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
    private ICloudExamineService cloudExamineService;

    /**
     * @param pageHelper
     * @Author: Sgz
     * @Time: 16:56 2020/9/11
     * @Params: [pageHelper]
     * @Return: com.baomidou.mybatisplus.extension.plugins.pagination.Page<com.jeeadmin.entity.CloudMeeting>
     * @Throws:
     * @Description: 查询所有的会议信息，并进行分页处理
     * 根据多条件进行查询
     *
     */

    @Override
    public Page<CloudMeetingVo> selectPage(PageHelper<CloudMeetingVo> pageHelper) {
        Page<CloudMeeting> page = new Page<>(pageHelper.getCurrent(), pageHelper.getSize());
        QueryWrapper<CloudMeeting> queryWrapper = new QueryWrapper<CloudMeeting>();
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
        queryWrapper.lambda().ne(CloudMeeting::getMeetingStatus,MeetingAndActivityEnum.REMOVE.getCode());
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
     *          根据会议id查询会议的详细信息
     */
    @Override
    public CloudMeetingDetailVo selectOneMeeting(Long id) {
        QueryWrapper<CloudMeeting> queryWrapper = new QueryWrapper<>();
        CloudMeetingDetailVo meeting = new CloudMeetingDetailVo();

        if(Objects.isNull(id)){
            throw new ValidateException("会议的Id不能为空");
        }
        // 通过会议id获取到会议信息
        queryWrapper.lambda().eq(CloudMeeting::getId,id);
        CloudMeeting cloudMeeting = this.getOne(queryWrapper);
        if (Objects.nonNull(cloudMeeting)){
            meeting.setCloudMeeting(cloudMeeting);
        }

        // 查询该次会议参与人(所有的参与人信息中会议的id是一样的)
        List<CloudMeetingPartyMemberVo> memberList = cloudMeetingMapper.selectJoinMembersByMeetingId(id);
        if(memberList.size() > 0) {
            meeting.setJoinMember(memberList);
        }
        // 查询会议发起人信息
        List<CloudMeetingPartyMemberVo> cloudMeetingPromoters = cloudMeetingMapper.selectMeetingPromotersByMeetingId(id);
        if (null != cloudMeetingPromoters && cloudMeetingPromoters.size()>0){
            meeting.setMeetingSponsor(cloudMeetingPromoters);
        }
        // 查询该次会议的附件(所有的附件信息中会
        //
        //
        // 议的id是一样的)
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
    *
    */
    @Override
    public boolean saveMeeting(CloudMeetingVo meeting) {
        CloudMeeting cloudMeeting = new CloudMeeting();
        CloudMeetingPartyMember cloudMeetingPartyMember = new CloudMeetingPartyMember();
        // 检验会议数据是否存在
        ValidateUtil.validateObject(meeting);
        // 由雪花算法生成主键id
        meeting.setId(snowFlake.nextId());
        // 通过安全框架获取到userId
        meeting.setCreateUser(SecurityUtil.getUserId());
        meeting.setCreateDate(new Date());
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
            // 新增审核信息
            CloudExamine cloudExamine = new CloudExamine();
            cloudExamine.setForeignId(cloudMeeting.getId());
            cloudExamine.setExamineTypeCode("3");
            cloudExamineService.saveExamine(cloudExamine);

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
    *
    *
    */
    @Override
    @Transactional(rollbackFor = ValidateException.class)
    public boolean deleteMeeting(Long meetingId) {
        CloudMeeting oldData = this.getById(meetingId);
        if (oldData == null){
           throw new ValidateException("该会议不存在");
        }
        // TODO 如果删除会议成功，就同步删除参会人员和附件信息
        if (this.removeById(meetingId)){
            // 删除参会人员信息
           if (cloudMeetingPartyMemberServiceImpl.deleteMeetingMember(meetingId)){
                // 删除附件以及会议附件中间表
              return cloudEnclosureServiceImpl.deleteEnclosure(meetingId);
           }
        }
        throw new ValidateException("删除会议失败");
    }

    /**
     * @Author: Sgz
     * @Time: 14:04 2020/9/14
     * @Params: [cloudMeeting]
     * @Return: boolean
     * @Throws:
     * @Description:
     * todo 已经完成
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

    @Override
    public Page<CloudMeeting> selectByUserId(PageHelper<CloudMeeting> pageHelper) {

        Page<CloudMeeting> page = new Page<>(pageHelper.getCurrent(), pageHelper.getSize());
        Long userId = SecurityUtil.getUserId();
        userId=1L;
        List<CloudMeeting> cloudMeetingList = cloudMeetingMapper.selectByUserId(userId);
        if (null == cloudMeetingList || "".equals(cloudMeetingList)){
            throw new ValidateException("当前用户没有发起会议");
        }
        page.setRecords(cloudMeetingList);
        return page;


    }

    /**
     * 通过用户id查询该用户处理过的会议
     * @param pageHelper
     * @return
     */
    @Override
    public Page<CloudMeeting> selectMeetingProcessed(PageHelper<CloudMeeting> pageHelper) {
        Page<CloudMeeting> page = new Page<>(pageHelper.getCurrent(), pageHelper.getSize());
        Long userId = SecurityUtil.getUserId();
        userId=1L;
        List<CloudMeeting> cloudMeetingList = cloudMeetingMapper.selectMeetingProcessed(userId);
        if (null == cloudMeetingList || "".equals(cloudMeetingList)){
            throw new ValidateException("当前用户还没有处理会议");
        }
        page.setRecords(cloudMeetingList);
        return page;


    }

    /**
     * 通过用户id查询该用户没有处理过的会议
     * @param pageHelper
     * @return
     */

    @Override
    public Page<CloudMeeting> selectMeetingUntreated(PageHelper<CloudMeeting> pageHelper) {
        Page<CloudMeeting> page = new Page<>(pageHelper.getCurrent(), pageHelper.getSize());



        Long userId = SecurityUtil.getUserId();
        userId=1L;
        List<CloudMeeting> cloudMeetingList = cloudMeetingMapper.selectMeetingUntreated(userId);
        if (null == cloudMeetingList || "".equals(cloudMeetingList)){
            throw new ValidateException("当前用户没有未处理的会议");
        }

        page.setRecords(cloudMeetingList);
        return page;





    }


}
