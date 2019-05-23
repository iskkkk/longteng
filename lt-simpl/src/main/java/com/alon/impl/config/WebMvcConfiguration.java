package com.alon.impl.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @ClassName WebMvcConfiguration
 * @Description TODO
 * @Author 一股清风
 * @Date 2019/5/21 14:58
 * @Version 1.0
 **/
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/MP_verify_1qC3Vg8lmyHBTMBT.txt").addResourceLocations("classpath:/MP_verify_1qC3Vg8lmyHBTMBT.txt");
    }
}
