package com.jeerigger.frame.base.service.impl;


import com.jeerigger.frame.base.mapper.BaseMapper;
import com.jeerigger.frame.base.model.Model;
import com.jeerigger.frame.base.service.IService;

/**
 * @param <M>
 * @param <T>
 */
public abstract class ServiceImpl<M extends BaseMapper<T>, T extends Model> extends com.baomidou.mybatisplus.extension.service.impl.ServiceImpl<M, T> implements IService<T> {

}
