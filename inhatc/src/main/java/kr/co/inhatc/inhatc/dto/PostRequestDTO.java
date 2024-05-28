package kr.co.inhatc.inhatc.dto;

import kr.co.inhatc.inhatc.entity.PostEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostRequestDTO {

    private String content; // 내용
    private String writer; // 작성자
    private char deleteYn; // 삭제 여부
    private String imgsource; // 이미지 경로
    private MultipartFile file; // 업로드 파일
    private int love;

    // 생성자를 통해 작성자 정보를 받도록 수정
    public PostRequestDTO(String content, String writer, char deleteYn, String imgsource, MultipartFile file, int love) {
        this.content = content;
        this.writer = writer;
        this.deleteYn = deleteYn;
        this.imgsource = imgsource;
        this.file = file;
        this.love = love;
    }

    // 작성자 정보를 설정하는 setter 메서드
    public void setWriter(String writer) {
        this.writer = writer;
    }

    public void setDeleteYn(char deleteYn) {
        this.deleteYn = deleteYn;
    }

    public void setImgsource(String imgsource) {
        this.imgsource = imgsource;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public PostEntity toEntity() {
        return PostEntity.builder()
                .content(content)
                .writer(writer)
                .imgsource(imgsource)
                .hits(0)
                .deleteYn(deleteYn)
                .love(love)
                .build();
    }
}
