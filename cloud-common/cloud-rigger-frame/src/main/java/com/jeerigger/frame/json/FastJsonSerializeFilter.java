package com.jeerigger.frame.json;

import com.alibaba.fastjson.serializer.SerializeFilter;

/**
 * @author Ian
 */
public interface FastJsonSerializeFilter<T> {

    SerializeFilter[] getSerializeFilters();

    T getValue();
}
