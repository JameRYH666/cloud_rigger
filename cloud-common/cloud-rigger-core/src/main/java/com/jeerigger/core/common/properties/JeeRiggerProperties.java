package com.jeerigger.core.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Data
@Component
@ConfigurationProperties(prefix = "jeerigger")
public class JeeRiggerProperties {
    private MybatisProperty mybatis=new MybatisProperty();
    private Map<String,String> fileUploadPath;

    public String getFileUploadPath(){
        String fileUploadLocation = System.getProperty("user.dir");
        if(fileUploadPath!=null && fileUploadPath.size()>0){
            String os = System.getProperty("os.name");
            if(os.toLowerCase().startsWith("win")) {
                fileUploadLocation = fileUploadPath.get("windows");
            } else {
                fileUploadLocation = fileUploadPath.get("linux");
            }
        }
        return fileUploadLocation;
    }

}

