package org.example.backend.config;

import org.example.backend.interceptor.TokenInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //资源映射路径：
        // addResourceHandler: 访问映射路径
        // addResourceLocations: 资源绝对路径
        registry.addResourceHandler("/user/avatar/**")
                .addResourceLocations("file:///E:/dev/tempImages/");
        WebMvcConfigurer.super.addResourceHandlers(registry);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor( new TokenInterceptor() {

            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                System.out.println("Interceptor preHandle");
                // exclude all OPTIONS requests
                if(request.getMethod().equalsIgnoreCase("OPTIONS")) {
                    return true;
                }
            return true;
            }
        }).addPathPatterns("/user/**")
                .excludePathPatterns("/user/register","/user/login");
    }
}
