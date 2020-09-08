package com.jeerigger.core.autoconfigure;

import com.jeerigger.frame.mybatis.annotation.MapperSource;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class MyBatisAutoConfiguration {

    @Bean
    public static MapperScannerConfigurer mapperScannerConfigurer(Environment environment){
        MapperScannerConfigurer mapperScannerConfigurer=new MapperScannerConfigurer();
        String basePackage=environment.getProperty("jeerigger.mybatis.scanBasePackage");
        if(basePackage==null || basePackage.equals("")){
            basePackage="com.jeerigger.core";
        }
        if(!basePackage.contains("com.jeerigger")){
            basePackage="com.jeerigger.core," + basePackage;
        }
        mapperScannerConfigurer.setBasePackage(basePackage);
        mapperScannerConfigurer.setAnnotationClass(MapperSource.class);
        return mapperScannerConfigurer;
    }
}
