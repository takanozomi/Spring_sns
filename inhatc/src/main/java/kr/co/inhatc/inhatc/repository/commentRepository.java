package kr.co.inhatc.inhatc.repository;
import kr.co.inhatc.inhatc.entity.commentEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface commentRepository extends JpaRepository<commentEntity, Long> {
    List<commentEntity> findByPostId(Long postId);

}
