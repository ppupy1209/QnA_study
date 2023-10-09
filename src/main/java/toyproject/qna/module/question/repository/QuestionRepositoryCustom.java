package toyproject.qna.module.question.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import toyproject.qna.module.question.entity.Question;

public interface QuestionRepositoryCustom {
    Page<Question> findQuestionsWithMember(Pageable pageable);
}
