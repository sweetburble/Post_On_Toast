package com.ssamz.jblog.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
public class JBlogWebMvcConfiguration implements WebMvcConfigurer {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    // 메시지 파일을 로딩하는 MessageSource 객체
    @Bean("messageSource")
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("message.messageSource");
        return messageSource;
    }
    
    // 브라우저에서 전송한 로케일 정보를 추출하여 유지하는 LocaleResolver 객체
    @Bean
    public LocaleResolver localeResolver() {
        return new SessionLocaleResolver(); // 세션이 종료될 때까지 해당 로케일을 유지시킨다
    }

    // 브라우저의 로케일과 상관없이 다른 언어로 메시지를 변경하고 싶을 때
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("lang"); // 로케일 정보를 전달받을 인자
        return localeChangeInterceptor; // LocaleChangeInterceptor 객체가 기존의 로케일을 전송받은 로케일로 변경해준다
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // HttpSession 기반으로 인증과 인가를 다루었지만, 7장부터는 스프링 시큐리티를 적용한다.
        // registry.addInterceptor(new AuthenticateInterceptor()).addPathPatterns("/", "/post/**");
        registry.addInterceptor(localeChangeInterceptor());
    }
}
