package toyproject.qna.module.answer.repository;

import org.springframework.data.repository.query.Param;
import toyproject.qna.module.answer.entity.Answer;

import java.util.List;

public interface AnswerRepositoryCustom {

    List<Answer> findWithMemberByQuestionId(Long id);
}
