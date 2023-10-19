package toyproject.qna.module.question.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import toyproject.qna.module.question.dto.QuestionSearchCondition;
import toyproject.qna.module.question.entity.Question;
import toyproject.qna.module.question.entity.QuestionTag;

import java.util.List;

public interface QuestionTagRepositoryCustom {

    List<QuestionTag> findByQuestionId(Long questionId);



}
