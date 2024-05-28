package kr.co.inhatc.inhatc.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class CommentResponseDTO {
    private Long id; // 댓글 ID
    private Long postId; // 댓글이 속한 게시글 ID
    private String comment; // 내용
    private String writer; // 작성자
    private LocalDateTime createdDate; // 생성일
    private LocalDateTime modifiedDate; // 수정일

    @Builder
    public CommentResponseDTO(Long id, Long postId, String comment, String writer, LocalDateTime createdDate,
            LocalDateTime modifiedDate) {
        this.id = id;
        this.postId = postId;
        this.comment = comment;
        this.writer = writer;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }
}
