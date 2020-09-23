package com.jeeadmin.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeadmin.api.ICloudNoticeEnclosureService;
import com.jeeadmin.api.ICloudNoticeService;
import com.jeeadmin.api.ICloudPartyMemberService;
import com.jeeadmin.entity.CloudEnclosure;
import com.jeeadmin.entity.CloudNotice;
import com.jeeadmin.entity.CloudNoticeEnclosure;
import com.jeeadmin.mapper.CloudNoticeEnclosureMapper;
import com.jeeadmin.mapper.CloudNoticeMapper;
import com.jeeadmin.vo.notice.CloudNoticeVo;
import com.jeerigger.core.common.core.SnowFlake;
import com.jeerigger.frame.base.service.impl.BaseServiceImpl;
import com.jeerigger.frame.enums.MeetingAndActivityEnum;
import com.jeerigger.frame.enums.StatusEnum;
import com.jeerigger.frame.exception.ValidateException;
import com.jeerigger.frame.page.PageHelper;
import com.jeerigger.frame.support.validate.ValidateUtil;
import com.jeerigger.frame.util.StringUtil;
import com.jeerigger.security.SecurityUtil;
import lombok.experimental.PackagePrivate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @Company YXH
 * @Author ryh
 * @Date Create in 2020/9/15 22:22
 * @Description:
 */
@Service
public class CloudNoticeServiceImpl extends BaseServiceImpl<CloudNoticeMapper, CloudNotice> implements ICloudNoticeService {

    @Autowired
    private SnowFlake snowFlake;
    @Autowired
    private CloudNoticeMapper cloudNoticeMapper;
    @Autowired
    private ICloudNoticeEnclosureService cloudNoticeEnclosureService;
    @Autowired
    private CloudNoticeEnclosureMapper cloudNoticeEnclosureMapper;

    /**
     *      查询所有正常的通知公告
     * @param pageHelper
     * @return
     */
    @Override
    public Page<CloudNoticeVo> selectPage(PageHelper<CloudNoticeVo> pageHelper) {
        Page<CloudNotice> page = new Page<>(pageHelper.getCurrent(), pageHelper.getSize());
        QueryWrapper<CloudNotice> queryWrapper = new QueryWrapper<>();
        if (pageHelper.getData() != null) {
            CloudNotice data = pageHelper.getData();
            if (StringUtil.isNotEmpty(data.getNoticeTitle())) {
                queryWrapper.lambda().like(CloudNotice::getNoticeTitle, data.getNoticeTitle());
            }

        }
        queryWrapper.lambda().eq(CloudNotice::getNoticeStatus,MeetingAndActivityEnum.NORMAL.getCode());
        queryWrapper.lambda().orderByAsc(CloudNotice::getCreateDate);
        IPage<CloudNotice> noticeIPage = this.page(page, queryWrapper);
        List<CloudNotice> cloudNoticeVoList = noticeIPage.getRecords();
        List<Long> ids = new ArrayList<>();
        for (CloudNotice cloudNotice:cloudNoticeVoList){
            ids.add(cloudNotice.getId());
        }
        Page<CloudNoticeVo> pageVo = new Page<>(pageHelper.getCurrent(), pageHelper.getSize());
        pageVo.setRecords(cloudNoticeMapper.selectAllNotice(ids));
        pageVo.setTotal(noticeIPage.getTotal());
        return pageVo;
    }

        /**
        * @Author: Ryh
        * @Description:         查询单个的通知公告带附件
        * @Param: [id]
        * @Date: Create in 2020/9/16
        * @Return: com.jeeadmin.vo.notice.CloudNoticeVo
        * @Throws:
        */
    @Override
    public CloudNoticeVo selectOneNotice(Long id) {
        if (Objects.isNull(id)){
            throw new ValidateException("通知公告的Id不能为空");
        }
        CloudNoticeVo noticeVo = cloudNoticeMapper.selectOne(id);
        if (null == noticeVo && "".equals(noticeVo)){
            throw new ValidateException("当前通知公告数据为空");
        }
        List<CloudNoticeEnclosure> cloudNoticeEnclosures = cloudNoticeEnclosureMapper.selectByNoticeId(id);
        if (null == cloudNoticeEnclosures && "".equals(cloudNoticeEnclosures)){
            throw new ValidateException("当前关系数据为空");
        }
        if (cloudNoticeEnclosures.size()>0){
            noticeVo.setNoticeEnclosures(cloudNoticeEnclosures);
        }
        List<CloudEnclosure> cloudEnclosures = cloudNoticeMapper.selectEnclosuresByNoticeId(id);
        if (cloudEnclosures.size() > 0){
            noticeVo.setEnclosureList(cloudEnclosures);
        }
        if (null == noticeVo){
            throw new ValidateException("当前附件数据为空");
        }
        return noticeVo;
    }

    /**
    * @Author: Ryh
    * @Description:         修改通知公告
    * @Param: [cloudNotice]
    * @Date: Create in 2020/9/16
    * @Return: boolean
    * @Throws:
    */
    @Override
    public boolean updateNotice(CloudNotice cloudNotice) {
        CloudNotice cloudNoticeData = this.getById(cloudNotice.getId());
        if (null == cloudNoticeData){
            throw new ValidateException("当前修改的通知公告数据为空");
        }
        ValidateUtil.validateObject(cloudNotice);
        cloudNotice.setUpdateDate(new Date());
        cloudNotice.setUpdateUser(SecurityUtil.getUserId());
        return this.updateById(cloudNotice);
    }

    /**
    * @Author: Ryh
    * @Description:         新增通知公告
    * @Param: [cloudNotice]
    * @Date: Create in 2020/9/16
    * @Return: com.jeeadmin.vo.notice.CloudNoticeVo
    * @Throws:
    */
    @Transactional(rollbackFor = ValidateException.class)
    @Override
    public CloudNoticeVo saveNotice(CloudNoticeVo cloudNotice) {
        ValidateUtil.validateObject(cloudNotice);
        CloudNotice cloudNoticeData = new CloudNotice();
        CloudNoticeEnclosure cloudNoticeEnclosure = new CloudNoticeEnclosure();
        long id = snowFlake.nextId();
        cloudNotice.setId(id);
        BeanUtils.copyProperties(cloudNotice,cloudNoticeData);
        BeanUtils.copyProperties(cloudNotice,cloudNoticeEnclosure);

        cloudNoticeEnclosure.setNoticeId(id);
        cloudNoticeEnclosure.setEnclosureId(cloudNotice.getCloudEnclosureId());
        cloudNoticeEnclosureService.saveNoticeEnclosure(cloudNoticeEnclosure);
        cloudNotice.setCreateUser(SecurityUtil.getUserId());
        cloudNotice.setUpdateDate(new Date());
        cloudNotice.setNoticeStatus(MeetingAndActivityEnum.NORMAL.getCode());
        if (this.save(cloudNotice)){
            return cloudNotice;
        }else {
            throw new ValidateException("新增通知公告失败");
        }
    }

    /**
    * @Author: Ryh
    * @Description:             修改通知公告状态，(逻辑删除)
    * @Param: [cloudNotice]
    * @Date: Create in 2020/9/17
    * @Return: boolean
    * @Throws:
    */
    @Override
    public boolean updateStatus(CloudNotice cloudNotice) {
        CloudNotice byIdData = this.getById(cloudNotice.getId());
        if (null == byIdData && "".equals(byIdData)){
            throw new ValidateException("当前通告数据为空");
        }
        UpdateWrapper<CloudNotice> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().set(CloudNotice::getNoticeStatus,cloudNotice.getNoticeStatus());
        updateWrapper.lambda().set(CloudNotice::getUpdateDate,new Date());
        updateWrapper.lambda().set(CloudNotice::getUpdateUser,SecurityUtil.getUserId());
        updateWrapper.lambda().eq(CloudNotice::getId,cloudNotice.getId());
        return this.update(new CloudNotice(),updateWrapper);
    }

    /**
     *          通过公告id查询对应的附件信息
     * @param id
     * @return
     */
    @Override
    public List<CloudEnclosure> selectByNoticeId(Long id) {
        if (Objects.isNull(id)){
            throw new ValidateException("通知公告的id为空");
        }
        List<CloudEnclosure> cloudEnclosures = cloudNoticeMapper.selectEnclosuresByNoticeId(id);
        if (cloudEnclosures == null ){
            throw new ValidateException("对应的附件信息为空");
        }else {
            return cloudEnclosures;
        }

    }


}
