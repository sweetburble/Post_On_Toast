package com.ssamz.jblog.controller;

import com.ssamz.jblog.domain.OAuthType;
import com.ssamz.jblog.domain.User;
import com.ssamz.jblog.dto.ResponseDTO;
import com.ssamz.jblog.dto.UserDTO;
import com.ssamz.jblog.security.UserDetailsImpl;
import com.ssamz.jblog.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class UserController {

    @Autowired private UserService userService;
    @Autowired private ModelMapper modelMapper;

    // 카카오 비밀번호 수정 방지
    @Value("${kakao.default.password}")
    private String kakaoPassword;

    // 구글 비밀번호 수정 방지
    @Value("${google.default.password}")
    private String googlePassword;


    @GetMapping("/auth/insertUser")
    public String insertUser() {
        return "user/insertUser";
    }

    /**
     * 회원 등록
     */
    @PostMapping("/auth/insertUser")
    public @ResponseBody ResponseDTO<?> insertUser(
            @Valid @RequestBody UserDTO userDTO, BindingResult bindingResult) {

        // UserDTO -> User 객체로 변환
        User user = modelMapper.map(userDTO, User.class);
        User findUser = userService.getUser(user.getUsername());

        if (findUser.getUsername() == null) {
            userService.insertUser(user);;
            return new ResponseDTO<>(HttpStatus.OK.value(), user.getUsername() + "님, 정상적으로 회원가입 되었습니다!");
        } else {
            return new ResponseDTO<>(HttpStatus.BAD_REQUEST.value(), user.getUsername() + "님은 이미 회원입니다.");
        }
    }

    @GetMapping("/user/updateUser")
    public String updateUser() {
        return "user/updateUser";
    }

    /**
     * 회원 정보 수정
     */
    @PutMapping("/user")
    public @ResponseBody ResponseDTO<?> updateUser(@RequestBody User user,
                       @AuthenticationPrincipal UserDetailsImpl principal) {
        // 회원 정보 수정 전에, 이 사용자가 카카오 또는 구글 회원인지 확인
        if (principal.getUser().getOauth().equals(OAuthType.KAKAO)) {
            // 카카오 회원인 경우, 비밀번호는 항상 고정
            user.setPassword(kakaoPassword);
        } else if (principal.getUser().getOauth().equals(OAuthType.GOOGLE)) {
            // 구글 회원이어도 비밀번호 고정
            user.setPassword(googlePassword);
        }

        // 회원 정보 수정과 동시에 세션을 갱신한다
        principal.setUser(userService.updateUser(user));

        return new ResponseDTO<>(HttpStatus.OK.value(),
                user.getUsername() + "님, 성공적으로 수정되었습니다.");
    }

    /**
     * 로그인 요청
     */
    @GetMapping("/auth/login")
    public String login() {
        return "system/login";
    }
}
