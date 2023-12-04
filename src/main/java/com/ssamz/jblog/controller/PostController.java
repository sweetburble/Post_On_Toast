package com.ssamz.jblog.controller;

import com.ssamz.jblog.domain.Post;
import com.ssamz.jblog.domain.User;
import com.ssamz.jblog.dto.PostDTO;
import com.ssamz.jblog.dto.ResponseDTO;
import com.ssamz.jblog.security.UserDetailsImpl;
import com.ssamz.jblog.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Controller
public class PostController {

    @Autowired private PostService postService;
    @Autowired private ModelMapper modelMapper;

    @GetMapping("/post/insertPost")
    public String insertPost() {
        return "post/insertPost";
    }

    /**
     * 포스트 등록
     */
    @PostMapping("/post")
    public @ResponseBody ResponseDTO<?> insertPost(
            @Valid @RequestBody PostDTO postDTO, BindingResult bindingResult,
            @AuthenticationPrincipal UserDetailsImpl principal) {

        // PostDTO -> Post 객체로 변환
        Post post = modelMapper.map(postDTO, Post.class);

        // Post 객체를 영속화하기 전에, 연관된 User 엔티티 설정
        post.setUser(principal.getUser());
        post.setCnt(0);

        postService.insertPost(post);

        return new ResponseDTO<>(HttpStatus.OK.value(), "새로운 포스트를 등록했습니다.");
    }

    /**
     * 페이징 처리를 해서, 포스트 목록 화면 구성
     */
    @GetMapping({"", "/"})
    public String getPostList(Model model, @PageableDefault(size = 3, sort = "id",
    direction = Sort.Direction.DESC) Pageable pageable) {
        model.addAttribute("postList", postService.getPostList(pageable));
        return "index";
    }

    /**
     * 포스트 상세 조회
     */
    @GetMapping("/post/{id}")
    public String getPost(@PathVariable int id, Model model) {
        model.addAttribute("post", postService.getPost(id));
        return "post/getPost";
    }

    @GetMapping("/post/updatePost/{id}")
    public String updatePost(@PathVariable int id, Model model) {
        model.addAttribute("post", postService.getPost(id));
        return "post/updatePost";
    }

    /**
     * 포스트 수정
     */
    @PutMapping("/post")
    public @ResponseBody ResponseDTO<?> updatePost(@RequestBody Post post) {
        postService.updatePost(post);
        return new ResponseDTO<>(HttpStatus.OK.value(),
                post.getId() + "번 포스트를 수정했습니다.");
    }

    /**
     * 포스트 삭제
     */
    @DeleteMapping("/post/{id}")
    public @ResponseBody ResponseDTO<?> deletePost(@PathVariable int id) {
        postService.deletePost(id);
        return new ResponseDTO<>(HttpStatus.OK.value(),
                id + "번 포스트를 삭제했습니다.");
    }
}
