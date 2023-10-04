package toyproject.qna.module.question.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toyproject.qna.module.question.entity.Question;
import toyproject.qna.module.question.entity.QuestionTag;

import java.util.List;

public interface QuestionTagRepository extends JpaRepository<QuestionTag, Long>,QuestionTagRepositoryCustom {

    void deleteByQuestion(Question question);
}
