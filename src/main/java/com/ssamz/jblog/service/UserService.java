package com.ssamz.jblog.service;

import com.ssamz.jblog.domain.OAuthType;
import com.ssamz.jblog.domain.RoleType;
import com.ssamz.jblog.domain.User;
import com.ssamz.jblog.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Supplier;

@Service
public class UserService {

    @Autowired private UserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    @Transactional
    public void insertUser(User user) {
        // 비밀번호를 암호화하여 설정한다
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        user.setRole(RoleType.USER);
        if (user.getOauth() == null) { // 이미 정해져 있는 OAuth가 없다면, 무조건 일반 가입 유저이다
            user.setOauth(OAuthType.JBLOG);
        }
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User getUser(String username) {
        // 검색 결과가 없으면, 빈 User 객체 반환
        User findUser = userRepository.findByUsername(username).orElseGet(() -> {
            return new User();
        });

        return findUser;
    }

    /**
     * 회원 정보 수정
     */
    @Transactional
    public User updateUser(User user) {
        User findUser = userRepository.findById(user.getId()).get();
        findUser.setUsername(user.getUsername());
        findUser.setPassword(passwordEncoder.encode(user.getPassword()));
        findUser.setEmail(user.getEmail());

        return findUser; // 세션을 갱신하기 위해서
    }
}
