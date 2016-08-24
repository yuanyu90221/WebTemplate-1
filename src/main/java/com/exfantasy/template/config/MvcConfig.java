package com.exfantasy.template.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Ref. https://spring.io/guides/gs/securing-web/
 * 
 * @author tommy.feng
 *
 */
@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {

	/**
	 * 設定輸入什麼網址會被導到哪個頁面
	 */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/hello").setViewName("hello");

        registry.addViewController("/").setViewName("login");
        registry.addViewController("/home").setViewName("login");
        registry.addViewController("/login").setViewName("login");
        
        registry.addViewController("/examples").setViewName("examples/ori_home");
    }
}