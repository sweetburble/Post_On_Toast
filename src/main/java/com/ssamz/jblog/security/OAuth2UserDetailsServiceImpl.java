package com.ssamz.jblog.security;

import com.ssamz.jblog.domain.OAuthType;
import com.ssamz.jblog.domain.RoleType;
import com.ssamz.jblog.domain.User;
import com.ssamz.jblog.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class OAuth2UserDetailsServiceImpl extends DefaultOAuth2UserService {

    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private UserRepository userRepository;

    @Value("${google.default.password}")
    private String googlePassword;

    /**
     * 1. 사용자가 구글 로그인에 성공하면, 구글은 인증 클라이언트에 CODE 정보를 전달한다
     * 2. OAuth2 Client는 CODE 정보를 기반으로 자동으로 액세스 토큰을 요청하고,
     *    액세스 토큰이 저장된 OAuth2UserRequest를 받는다
     * 3. OAuth2 Client가 OAuth2UserRequest를 인자로 넘기면서 loadUser() 메서드를 호출한다
     */
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // 액세스 토큰이 저장되어 있는 userRequest를 이용하여, 구글로부터 회원 정보를 받아온다
        OAuth2User oauth2User = super.loadUser(userRequest);

        // 구글이 전달해준 회원 정보를 바탕으로, 내 서비스에 맞게 회원 정보를 구성한다
        String providerId = oauth2User.getAttribute("sub");
        String email = oauth2User.getAttribute("email");
        String username = email + "_" + providerId;
        String password = passwordEncoder.encode(googlePassword);

        // 이미 회원가입한 사용자인지 확인한다
        User findUser = userRepository.findByUsername(username).orElseGet(() -> {
            return new User();
        });

        if (findUser.getUsername() == null) {
            findUser = User.builder()
                    .username(username)
                    .password(password)
                    .email(email)
                    .role(RoleType.USER)
                    .oauth(OAuthType.GOOGLE)
                    .build();
            userRepository.save(findUser);
        }

        // OAuth2 Client가 UserDetailsImpl에 설정된 정보로
        // Authencation 객체를 SecurityContext에 자동으로 등록한다
        return new UserDetailsImpl(findUser, oauth2User.getAttributes()); // OAuth2 용 생성자 호출
    }
}
