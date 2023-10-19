package toyproject.qna.module.question.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;
import toyproject.qna.module.member.entity.QMember;
import toyproject.qna.module.question.dto.QuestionSearchCondition;
import toyproject.qna.module.question.entity.QQuestion;
import toyproject.qna.module.question.entity.QQuestionTag;
import toyproject.qna.module.question.entity.Question;
import toyproject.qna.module.question.entity.QuestionTag;
import toyproject.qna.module.tag.entity.QTag;

import javax.persistence.EntityManager;
import java.util.List;

import static org.thymeleaf.util.StringUtils.*;
import static toyproject.qna.module.member.entity.QMember.*;
import static toyproject.qna.module.question.entity.QQuestion.*;
import static toyproject.qna.module.question.entity.QQuestionTag.*;
import static toyproject.qna.module.tag.entity.QTag.*;

public class QuestionTagRepositoryImpl implements QuestionTagRepositoryCustom {

   private final JPAQueryFactory queryFactory;

   public QuestionTagRepositoryImpl(EntityManager em) {
       this.queryFactory = new JPAQueryFactory(em);
   }

    @Override
    public List<QuestionTag> findByQuestionId(Long questionId) {
        return queryFactory.selectFrom(questionTag)
                .innerJoin(questionTag.question, question)
                .innerJoin(questionTag.tag, tag)
                .fetchJoin()
                .where(question.id.eq(questionId))
                .fetch();
    }




}
