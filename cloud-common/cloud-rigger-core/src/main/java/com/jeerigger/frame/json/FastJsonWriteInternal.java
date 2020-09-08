package com.jeerigger.frame.json;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONPObject;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.support.spring.FastJsonContainer;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.alibaba.fastjson.support.spring.MappingFastJsonValue;
import com.alibaba.fastjson.support.spring.PropertyPreFilters;
import com.jeerigger.frame.base.controller.ResultData;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Ian
 */
public class FastJsonWriteInternal extends FastJsonHttpMessageConverter {


    @Override
    protected void writeInternal(Object object, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {

        try (ByteArrayOutputStream outnew = new ByteArrayOutputStream();) {
            HttpHeaders headers = outputMessage.getHeaders();
            SerializeFilter[] globalFilters = this.getFastJsonConfig().getSerializeFilters();
            List<SerializeFilter> allFilters = new ArrayList(Arrays.asList(globalFilters));
            boolean isJsonp = false;
            boolean isString = false;
            Object value = this.strangeCodeForJackson(object);
            if (value instanceof FastJsonContainer) {
                FastJsonContainer fastJsonContainer = (FastJsonContainer) value;
                PropertyPreFilters filters = fastJsonContainer.getFilters();
                allFilters.addAll(filters.getFilters());
                value = fastJsonContainer.getValue();
            }
            //FastJsonSerializeFilter
            if (value instanceof FastJsonSerializeFilter) {
                allFilters.addAll(Arrays.asList(((FastJsonSerializeFilter) value).getSerializeFilters()));

            }

            if (value instanceof MappingFastJsonValue) {
                if (!StringUtils.isEmpty(((MappingFastJsonValue) value).getJsonpFunction())) {
                    isJsonp = true;
                }
            } else if (value instanceof JSONPObject) {
                isJsonp = true;
            }
            if (value instanceof ResultData) {
                ResultData resultData = (ResultData) value;
                value = resultData.getValue();
                allFilters.addAll(Arrays.asList(resultData.getSerializeFilters()));
            }
            if (value instanceof String) {
                isString = true;
            }
            if (!isString) {
                int len = com.alibaba.fastjson.JSON.writeJSONString(outnew, this.getFastJsonConfig().getCharset(), value, this.getFastJsonConfig().getSerializeConfig(), (SerializeFilter[]) allFilters.toArray(new SerializeFilter[allFilters.size()]), this.getFastJsonConfig().getDateFormat(), JSON.DEFAULT_GENERATE_FEATURE, this.getFastJsonConfig().getSerializerFeatures());
                if (isJsonp) {
                    headers.setContentType(APPLICATION_JAVASCRIPT);
                }

                if (this.getFastJsonConfig().isWriteContentLength()) {
                    headers.setContentLength(len);
                }

                outnew.writeTo(outputMessage.getBody());
            } else {
                StreamUtils.copy((String) value, this.getFastJsonConfig().getCharset(), outputMessage.getBody());
            }
        } catch (JSONException var14) {
            throw new HttpMessageNotWritableException("Could not write JSON: " + var14.getMessage(), var14);
        }

    }

    private Object strangeCodeForJackson(Object obj) {
        if (obj != null) {
            String className = obj.getClass().getName();
            if ("com.fasterxml.jackson.databind.node.ObjectNode".equals(className)) {
                return obj.toString();
            }
        }

        return obj;
    }
}