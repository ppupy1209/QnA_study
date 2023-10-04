package toyproject.qna.module.question.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import toyproject.qna.module.question.entity.Question;

import java.util.List;

public interface QuestionRepositoryCustom {
    Page<Question> findWithMember(Pageable pageable);
}
