package com.jeerigger.core.common.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.jeerigger.core.common.user.UserData;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * mybatisplus自定义填充公共字段
 */
@Component
public class JeeRiggerMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        if (metaObject.hasGetter("createUser")) {
            Object createUser = metaObject.getValue("createUser");
            if (createUser == null) {
                setFieldValByName("createUser", null /*ShiroUtil.getUserUuid()*/, metaObject);
            }
        }
        if (metaObject.hasGetter("createDate")) {
            Object createDate = metaObject.getValue("createDate");
            if (createDate == null) {
                setFieldValByName("createDate", new Date(), metaObject);
            }
        }

        if (metaObject.hasGetter("createUserName")) {
            Object createUserName = metaObject.getValue("createUserName");
            if (createUserName == null) {
                UserData userData = /*ShiroUtil.getUserData()*/null;
                String userName = "";
                if (userData != null) {
                    userName = userData.getUserName();
                }
                setFieldValByName("createUserName", userName, metaObject);
            }
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        if (metaObject.hasGetter("updateUser")) {
            Object updateUser = metaObject.getValue("updateUser");
            if (updateUser == null) {
                setFieldValByName("updateUser", null/* ShiroUtil.getUserUuid()*/, metaObject);
            }
        }
        if (metaObject.hasGetter("updateDate")) {
            Object updateDate = metaObject.getValue("updateDate");
            if (updateDate == null) {
                setFieldValByName("updateDate", new Date(), metaObject);
            }
        }
        if (metaObject.hasGetter("updateUserName")) {
            Object updateUserName = metaObject.getValue("updateUserName");
            if (updateUserName == null) {
                UserData userData = /*ShiroUtil.getUserData()*/null;
                String userName = "";
                if (userData != null) {
                    userName = userData.getUserName();
                }
                setFieldValByName("updateUserName", userName, metaObject);
            }
        }
    }
}
