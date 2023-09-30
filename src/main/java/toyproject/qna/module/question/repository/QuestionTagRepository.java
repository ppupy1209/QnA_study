package toyproject.qna.module.question.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toyproject.qna.module.question.entity.QuestionTag;

public interface QuestionTagRepository extends JpaRepository<QuestionTag, Long> {
}
