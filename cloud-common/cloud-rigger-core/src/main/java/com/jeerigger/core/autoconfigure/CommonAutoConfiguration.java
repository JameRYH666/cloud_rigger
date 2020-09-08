package com.jeerigger.core.autoconfigure;

import com.jeerigger.core.common.core.SnowFlake;
import com.jeerigger.core.common.file.FileUploadProgressListener;
import com.jeerigger.core.common.file.FileUploadProgressMultipartResolver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@EnableCaching
@Configuration
@ComponentScan({"com.jeerigger.core"})
@PropertySource("classpath:config/jeerigger.properties")
public class CommonAutoConfiguration {

    @Value("${jeerigger.dataCenterId:2}")
    private Long dataCenterId;
    @Value("${jeerigger.machineId:3}")
    private Long machineId;


    @Bean
    public FileUploadProgressListener progressListener() {
        return new FileUploadProgressListener();
    }

    @Bean
    public FileUploadProgressMultipartResolver multipartResolver() {
        FileUploadProgressMultipartResolver fileUploadProgressMultipartResolver =
                new FileUploadProgressMultipartResolver();
        fileUploadProgressMultipartResolver.setDefaultEncoding("utf-8");
        fileUploadProgressMultipartResolver.setMaxInMemorySize(40960);
        fileUploadProgressMultipartResolver.setMaxUploadSize(10485760000L);
        return fileUploadProgressMultipartResolver;
    }


    @Bean
    public SnowFlake snowFlake() {
        return new SnowFlake(dataCenterId, machineId);
    }


}

