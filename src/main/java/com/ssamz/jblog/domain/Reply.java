package com.ssamz.jblog.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Reply {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // 댓글 일련번호
    
    @Column(nullable = false, length = 200)
    private String content; // 댓글 내용
    
    @CreationTimestamp
    private Timestamp createdDate; // 댓글 등록일
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userid")
    private User user;    // 댓글을 작성한 User 정보

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "postid")
    private Post post;    // 댓글이 달린 Post 정보
}
