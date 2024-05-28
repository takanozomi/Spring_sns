package kr.co.inhatc.inhatc.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import kr.co.inhatc.inhatc.entity.PostEntity;
import kr.co.inhatc.inhatc.entity.PostEntity;
import kr.co.inhatc.inhatc.entity.commentEntity;

@Getter
@Setter
@Builder
public class CommentRequestDTO {
    private Long id;
    private String comment;
    private LocalDateTime createdDate = LocalDateTime.now();
    private LocalDateTime modifiedDate = LocalDateTime.now();
    private String user;
    private Long article;

    public CommentRequestDTO toEntity() {
        return CommentRequestDTO.builder()
                .comment(comment)
                .createdDate(createdDate)
                .modifiedDate(modifiedDate)
                .user(user)
                .article(article)
                .build();
    }
}
