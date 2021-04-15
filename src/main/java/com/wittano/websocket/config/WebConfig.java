package com.wittano.websocket.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

import java.io.IOException;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    // Angular config
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/").
                resourceChain(true).addResolver(new PathResourceResolver() {
            @Override
            protected Resource getResource(String resourcePath, Resource location) throws IOException {
                Resource resource = location.createRelative(resourcePath);

                return resource.exists() && resource.isReadable() ? resource :
                        new ClassPathResource("/static/index.html");
            }
        });
    }

    // CORS config
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
                .addMapping("/friend/**")
                .allowedOrigins("http://localhost:4200")
                .allowedMethods("*")
                .exposedHeaders("Access-Control-Allow-Origin");
    }
}
