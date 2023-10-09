package toyproject.qna.module.answer.repository;

import toyproject.qna.module.answer.entity.Answer;

import java.util.List;

public interface AnswerRepositoryCustom {

    List<Answer> findAnswersByQuestionId(Long id);
}
