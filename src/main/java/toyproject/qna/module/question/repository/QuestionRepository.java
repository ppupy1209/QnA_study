package toyproject.qna.module.question.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toyproject.qna.module.question.entity.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}
