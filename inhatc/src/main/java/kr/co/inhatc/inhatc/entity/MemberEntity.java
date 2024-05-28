package kr.co.inhatc.inhatc.entity;

import org.hibernate.mapping.List;

import com.mysql.cj.jdbc.Blob;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table; // Table 어노테이션을 import해야 합니다.
import kr.co.inhatc.inhatc.dto.MemberDTO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import java.util.Set;
@Entity
@Setter
@Getter
@Table(name = "member_entity") // Table 어노테이션을 올바르게 사용합니다.
public class MemberEntity {
    // jpa => database 객체처럼 사용가능
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    private long id;

    @Column(unique = true)
    private String memberEmail;

    @Column
    private String memberPassword;

    @Column
    private String memberName;

    @ManyToMany(mappedBy = "lovedBy")
    private Set<PostEntity> likedPosts;

    @Column
    private String profilePicturePath;

    // toMemberEntity 메서드 수정
    public static MemberEntity toMemberEntity(MemberDTO memberDTO) {
        MemberEntity memberEntity = new MemberEntity(); // 변수명을 수정합니다.
        memberEntity.setId(memberDTO.getId()); // 변수명을 수정합니다.
        memberEntity.setMemberEmail(memberDTO.getMemberEmail()); // 변수명을 수정합니다.
        memberEntity.setMemberPassword(memberDTO.getMemberPassword()); // 변수명을 수정합니다.
        memberEntity.setMemberName(memberDTO.getMemberName()); // 변수명을 수정합니다.
        memberEntity.setProfilePicturePath(memberDTO.getProfilePicturePath());
        return memberEntity; // 반환값을 추가합니다.
    }
}
