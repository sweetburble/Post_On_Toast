package com.ssamz.jblog.controller;

import com.ssamz.jblog.domain.User;
import org.springframework.web.bind.annotation.*;

@RestController
public class RESTController {

    // GET : select
    @GetMapping("/jblog")
    public User httpGet() {
        User findUser = User.builder()
                .id(1)
                .username("bandi")
                .password("222")
                .email("bandi@gmail.com")
                .build();
        return findUser;
    }

    // POST : insert
    @PostMapping("/jblog")
    public String httpPost(@RequestBody User user) {
        return "Post 요청 처리, 입력값 : " + user.toString();
    }

    // PUT : update
    @PutMapping("/jblog")
    public String httpPut(@RequestBody User user) {
        return "PUT 요청 처리, 입력값 : " + user.toString();
    }

    // DELETE : delete
    @DeleteMapping("/jblog")
    public String httpDelete(@RequestParam int id) {
        return "DELETE 요청 처리, 입력값 : " + id;
    }
}
