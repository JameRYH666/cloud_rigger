package com.jeerigger.frame.base.controller;

import com.alibaba.fastjson.serializer.SerializeFilter;
import com.jeerigger.frame.json.FastJsonSerializeFilter;
import com.jeerigger.frame.json.JSON;


/**
 * @author Ian
 */
public class ResultData implements FastJsonSerializeFilter {

    private JSON value;

    public ResultData(JSON body) {
        this.value = body;
    }

    @Override
    public Object getValue() {
        return this.value;
    }

    public void setValue(JSON value) {
        this.value = value;
    }


    @Override
    public SerializeFilter[] getSerializeFilters() {
        return value.filters();
    }


}
