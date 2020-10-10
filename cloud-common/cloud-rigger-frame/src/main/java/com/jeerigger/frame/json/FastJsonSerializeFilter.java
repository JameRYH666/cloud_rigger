package com.jeerigger.frame.json;

import com.alibaba.fastjson.serializer.SerializeFilter;


public interface FastJsonSerializeFilter<T> {

    SerializeFilter[] getSerializeFilters();

    T getValue();
}
