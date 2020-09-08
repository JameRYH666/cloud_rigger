package com.jeerigger.frame.support.resolver;

import com.alibaba.fastjson.JSONObject;
import com.jeerigger.frame.exception.ValidateException;
import com.jeerigger.frame.support.resolver.annotation.SingleRequestBody;
import com.jeerigger.frame.util.StringUtil;
import org.apache.commons.io.IOUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public class SingleRequestBodyResolver implements HandlerMethodArgumentResolver {
    private static final String JSON_REQUEST_BODY = "JSON_REQUEST_BODY";

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(SingleRequestBody.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) {
        SingleRequestBody singleRequestBody = methodParameter.getParameterAnnotation(SingleRequestBody.class);
        final String jsonBody = getRequestBody(nativeWebRequest);
        JSONObject jsonObject = JSONObject.parseObject(jsonBody);
        String parameterName = methodParameter.getParameterName();
        if (StringUtil.isNotEmpty(singleRequestBody.value())) {
            parameterName = singleRequestBody.value();
        }

        if (parameterName.contains(".")) {
            String[] parameterNameStr = parameterName.split("\\.");
            parameterName = parameterNameStr[parameterNameStr.length - 1];
            for (int i = 0; i < parameterNameStr.length - 1; i++) {
                jsonObject = jsonObject.getJSONObject(parameterNameStr[i]);
                if (jsonObject == null) {
                    break;
                }
            }
        }
        Object object = null;
        if (jsonObject != null) {
            object = jsonObject.getObject(parameterName, methodParameter.getParameterType());
            if (methodParameter.getParameterType() == List.class) {
                ParameterizedType pt = null;
                try {
                    pt = (ParameterizedType) methodParameter.getGenericParameterType();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (pt != null) {
                    Class itemClass = (Class) pt.getActualTypeArguments()[0];
                    if (!itemClass.isPrimitive()) {
                        object = JSONObject.parseArray(jsonObject.getString(parameterName), itemClass);
                    }
                }
            }
        }
        if (singleRequestBody.required()) {
            if (object == null) {
                throw new ValidateException(String.format("required param %s is not present", parameterName));
            }
        }

        return object;
    }

    /**
     * 获取请求体JSON字符串
     */

    private String getRequestBody(NativeWebRequest webRequest) {
        HttpServletRequest servletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
        // 有就直接获取
        String jsonBody = (String) webRequest.getAttribute(JSON_REQUEST_BODY, NativeWebRequest.SCOPE_REQUEST);
        // 没有就从请求中读取
        if (jsonBody == null) {
            try {
                jsonBody = IOUtils.toString(servletRequest.getReader());
                webRequest.setAttribute(JSON_REQUEST_BODY, jsonBody, NativeWebRequest.SCOPE_REQUEST);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return jsonBody;
    }
}
