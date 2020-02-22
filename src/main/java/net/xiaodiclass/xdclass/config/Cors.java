package net.xiaodiclass.xdclass.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 跨域设置
 */
@Configuration
public class Cors extends WebMvcConfigurerAdapter {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("PUT","GET","POST","OPTIONS","DELETE","PATCH")
                .allowCredentials(true)//证书
                .maxAge(3600);
    }
}
