package com.ssamz.jblog.config;

import com.ssamz.jblog.security.OAuth2UserDetailsServiceImpl;
import com.ssamz.jblog.security.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

// WebSecurityConfigurerAdapter 가 Deprecated 되었으므로, 대체한다
@Configuration
@EnableWebSecurity
public class JBlogWebSecurityConfiguration {

    // 사용자가 입력한 username으로 사용자를 인증하는 객체 (카카오 용)
    @Autowired private UserDetailsServiceImpl userDetailsService;

    // 사용자가 입력한 username으로 사용자를 인증하는 객체 (구글 용)
    @Autowired private OAuth2UserDetailsServiceImpl oauth2DetailsService;

    // 순환 참조 문제를 해결하기 위해 JBlogWebCommonConfiguration에서 PasswordEncoder를 받아온다
    @Autowired private PasswordEncoder passwordEncoder;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // 인증 없이 접근을 허용하는 경로
        http.authorizeRequests().antMatchers("/webjars/**", "/js/**",
                "/image/**", "/", "/auth/**", "/oauth/**").permitAll();

        // 나머지 경로의 접근은 인증이 필요하다
        http.authorizeRequests().anyRequest().authenticated();

        // CSRF 토큰을 받지 않음
        http.csrf().disable();

        // 사용자 정의 로그인 화면 제공
        http.formLogin().loginPage("/auth/login");
        http.formLogin().loginProcessingUrl("/auth/securitylogin");

        // 로그아웃 설정
        http.logout().logoutUrl("/auth/logout").logoutSuccessUrl("/");

        // 구글 로그인 설정을 시작
        http.oauth2Login()
                .userInfoEndpoint() // OAuth2로 사용자 정보를 가져온다
                .userService(oauth2DetailsService);
                // 가져온 사용자 정보를 이용해서 oauth2DetailsService 객체로 사후처리한다

        return http.build();
    }

    // AuthenticationManager도 Bean으로 생성한다
    @Bean
    AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}