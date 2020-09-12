package com.jeerigger.frame.base.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jeerigger.frame.base.mapper.BaseMapper;
import com.jeerigger.frame.base.model.BaseModel;
import com.jeerigger.frame.base.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;


/**
 * 接口实现基类
 *
 * @param <M>
 * @param <T>
 */
public abstract class BaseServiceImpl<M extends BaseMapper<T>, T extends BaseModel> extends ServiceImpl<M, T> implements BaseService<T> {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    public IPage<Map<String, Object>> pageOwn(IPage<Map<String, Object>> page) {
        return null;
    }
}
