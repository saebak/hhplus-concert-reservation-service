package com.hhplus.backend.support.config;

import com.hhplus.backend.support.interceptor.AuthTokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final AuthTokenInterceptor authTokenInterceptor;

    public WebConfig(AuthTokenInterceptor authTokenInterceptor) {
        this.authTokenInterceptor = authTokenInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authTokenInterceptor)
                .addPathPatterns("/concert/**")
                .addPathPatterns("/payment/**")
                .excludePathPatterns("/h2-console/**")
                .excludePathPatterns("/user/**");

    }
}