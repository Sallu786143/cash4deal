package com.example.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Value("${app.static.img.path:classpath:/static/images/}")
    private String imgPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Serve images from any folder under the classpath
        registry.addResourceHandler("/api/images/**")
                .addResourceLocations(
                        imgPath // Absolute path
                )
                .setCachePeriod(0);


    }
}







