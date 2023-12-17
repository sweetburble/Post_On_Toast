package com.ssamz.jblog.controller;

import com.ssamz.jblog.domain.Reply;
import com.ssamz.jblog.domain.User;
import com.ssamz.jblog.dto.ResponseDTO;
import com.ssamz.jblog.security.UserDetailsImpl;
import com.ssamz.jblog.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class ReplyController {

    @Autowired private ReplyService replyService;

    /**
     * 댓글 등록
     */
    @PostMapping("/reply/{postId}")
    public @ResponseBody ResponseDTO<?> insertReply(@PathVariable int postId, @RequestBody Reply reply,
                        @AuthenticationPrincipal UserDetailsImpl principal) {
        replyService.insertReply(postId, reply, principal.getUser());

        return new ResponseDTO<>(HttpStatus.OK.value(),
                postId + "번 포스트에 댓글을 달았습니다.");
    }

    /**
     * 댓글 삭제
     */
    @DeleteMapping("/reply/{replyId}")
    public @ResponseBody ResponseDTO<?> deleteReply(@PathVariable int replyId) {
        replyService.deleteReply(replyId);
        return new ResponseDTO<>(HttpStatus.OK.value(),
                replyId + "번 댓글을 삭제했습니다.");
    }
}
