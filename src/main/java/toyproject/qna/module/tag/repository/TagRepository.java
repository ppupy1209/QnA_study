package toyproject.qna.module.tag.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toyproject.qna.module.tag.entity.Tag;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {

    Optional<Tag> findByName(String name);
}
