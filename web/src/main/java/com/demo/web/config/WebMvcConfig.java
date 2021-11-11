package com.demo.web.config;

import com.demo.web.interceptor.AuthInterceptor;
import com.demo.web.interceptor.GlobalInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 项目: spring-boot
 * 时间: 2021/11/9 15:08
 * WebMvc配置
 *
 * @author codingob
 * @version 1.0.0
 * @since JDK1.8
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final GlobalInterceptor globalInterceptor;

    private final AuthInterceptor authInterceptor;

    public WebMvcConfig(GlobalInterceptor globalInterceptor, AuthInterceptor authInterceptor) {
        this.globalInterceptor = globalInterceptor;
        this.authInterceptor = authInterceptor;
    }

    // 拦截器

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(globalInterceptor).addPathPatterns("/**").excludePathPatterns("/error");
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/error")
                .excludePathPatterns("/captcha");
    }

    // 跨域处理

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "POST", "DELETE", "PUT", "PATCH")
                .exposedHeaders(HttpHeaders.AUTHORIZATION)
                .allowCredentials(true);
    }
}
