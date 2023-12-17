package com.ssamz.jblog.security;

import com.ssamz.jblog.domain.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Getter @Setter
public class UserDetailsImpl implements UserDetails, OAuth2User {

    private static final long serialVersionUID = 1L;
    private User user; // USERS 테이블과 매핑된 엔티티

    // 구글에서 조회한 사용자 정보를 담을 컬렉션
    private Map<String, Object> attributes;

    public UserDetailsImpl(User user) {
        this.user = user;
    }

    // 구글 OAuth 로그인 시, 사용할 생성자
    public UserDetailsImpl(User user, Map<String, Object> attributes) {
        this.user = user;
        this.attributes = attributes;
    }

    // 로그인한 유저 계정이 가지고 있는 권한 목록을 저장하여 반환한다

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 권한 목록
        Collection<GrantedAuthority> roleList = new ArrayList<>();

        // 권한 목록 설정
        roleList.add(() -> {
            return "ROLE_" + user.getRole();
        });

        return roleList;
    }
    @Override
    public String getPassword() {
        // noop : 암호화하지 않기 위한 설정
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    // 계정이 만료되지 않았는지 반환한다

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    // 계정이 잠겨있는지 반환한다

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    // 비밀번호가 만료되지 않았는지 반환한다

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    // 계정이 활성화되었는지 반환한다

    @Override
    public boolean isEnabled() {
        return true;
    }


    /**
     * 여기서부터는 OAuth2 Client가 제공하는 OAuth2User 인테페이스 구현 파트
     */
    // 구글에서 조회한 사용자 정보가 저장된 컬렉션 반환
    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    // 이름은 사용하지 않는 정보이므로 null을 반환한다
    @Override
    public String getName() {
        return null;
    }
}
