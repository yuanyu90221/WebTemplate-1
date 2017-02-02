package com.exfantasy.template.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * <pre>
 * Mvc 設定
 * 
 * 參考:
 * 	<a href="https://spring.io/guides/gs/securing-web/">Spring Security 範例</a>
 * </pre>
 * 
 * @author tommy.feng
 *
 */
@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {

	/**
	 * 設定輸入什麼網址會被導到哪個頁面
	 * 
	 * @param registry
	 */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
    	registry.addViewController("/register").setViewName("register");
    	registry.addViewController("/").setViewName("login");
    	registry.addViewController("/login").setViewName("login");
        registry.addViewController("/main").setViewName("main");
    }
    
    // http://stackoverflow.com/questions/31335146/configure-gson-in-spring-before-using-gsonhttpmessageconverter
//    @Override
//    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//        converters.add(createGsonHttpMessageConverter());
//        super.configureMessageConverters(converters);
//    }
//
//    private GsonHttpMessageConverter createGsonHttpMessageConverter() {
//        Gson gson 
//        	= new GsonBuilder()
//                .setDateFormat("yyyy-MM-dd HH:mm:ss")
//                .create();
//
//        GsonHttpMessageConverter gsonConverter = new GsonHttpMessageConverter();
//        gsonConverter.setGson(gson);
//
//        return gsonConverter;
//    }

}