package com.ssamz.jblog.service;

import com.ssamz.jblog.domain.Post;
import com.ssamz.jblog.persistence.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PostService {

    @Autowired private PostRepository postRepository;

    /**
     * 포스트 등록
     */
    @Transactional
    public void insertPost(Post post) {
        post.setCnt(0);;
        postRepository.save(post);
    }

    /**
     * 페이징 처리를 한, 포스트 목록 출력
     */
    @Transactional(readOnly = true)
    public Page<Post> getPostList(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    /**
     * 포스트 상세 정보 조회
     */
    @Transactional(readOnly = true)
    public Post getPost(int id) {
        return postRepository.findById(id).get();
    }

    /**
     * 포스트 수정
     */
    @Transactional
    public void updatePost(Post post) {
        Post findPost = postRepository.findById(post.getId()).get();
        findPost.setTitle(post.getTitle());
        findPost.setContent(post.getContent()); // 이렇게만 해도 영속성 컨텍스트에서 더티 체킹에 의해 변경사항이 저장된다
    }

    /**
     * 포스트 삭제
     */
    @Transactional
    public void deletePost(int id) {
        postRepository.deleteById(id);
    }
}
