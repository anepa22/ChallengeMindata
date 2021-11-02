package com.mindata.challenge.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.mindata.challenge.annotations.TimedInterceptor;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new TimedInterceptor()).excludePathPatterns("/swagger**", "/webjars/springfox-swagger-ui/**");
    }
}
