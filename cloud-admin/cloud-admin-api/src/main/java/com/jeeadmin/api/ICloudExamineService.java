package com.jeeadmin.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeadmin.entity.CloudExamine;
import com.jeeadmin.vo.examine.CloudExamineVo;
import com.jeerigger.frame.base.service.BaseService;
import com.jeerigger.frame.page.PageHelper;

/**
 * @author: Sgz
 * @time: 2020/9/16 17:40
 * @description:
 *  审核表
 */
public interface ICloudExamineService extends BaseService<CloudExamine> {
    /**
     * @Author: Sgz
     * @Time: 17:48 2020/9/16
     * @Params: []
     * @Return: com.baomidou.mybatisplus.extension.plugins.pagination.Page<com.jeeadmin.entity.CloudExmaine>
     * @Throws:
     * @Description:
     *  查询所有审核信息，并进行分页处理
     *
     */
    Page<CloudExamine> selectAll(PageHelper<CloudExamine> pageHelper);


    /**
     * @Author: Sgz
     * @Time: 18:02 2020/9/16
     * @Params: [cloudExmaine]
     * @Return: com.jeeadmin.entity.CloudExmaine
     * @Throws:
     * @Description:
     *  查询单个审核的详细信息
     *
     */
    CloudExamine getDetail(Long examineId);


    /**
     * @Author: Sgz
     * @Time: 18:05 2020/9/16
     * @Params: [cloudExmaine]
     * @Return: boolean
     * @Throws:
     * @Description:
     * 新增审核信息，新增审核应该在活动、会议、以及外出报备中实现
     */
    CloudExamine saveExamine(CloudExamine cloudExamine);
    /**
     * @Author: Sgz
     * @Time: 18:03 2020/9/16
     * @Params: [cloudExmaine]
     * @Return: boolean
     * @Throws:
     * @Description:
     *  更改审核状态
     *  1：正常，2：删除，3：驳回，4：未审核
     *
     */

    boolean updateStatus(CloudExamine cloudExamine);

    /**
     * 根据外键id逻辑删除审核数据
     * @param foreignId
     * @return
     */
    boolean deleteByForeignId(Long foreignId);
}
