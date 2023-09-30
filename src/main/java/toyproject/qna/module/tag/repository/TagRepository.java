package toyproject.qna.module.tag.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toyproject.qna.module.tag.entity.Tag;

public interface TagRepository extends JpaRepository<Tag, Long> {
}
