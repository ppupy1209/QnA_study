package toyproject.qna.module.question.repository;

import toyproject.qna.module.question.entity.QuestionTag;

import java.util.List;

public interface QuestionTagRepositoryCustom {

    List<QuestionTag> findByQuestionId(Long questionId);
}
