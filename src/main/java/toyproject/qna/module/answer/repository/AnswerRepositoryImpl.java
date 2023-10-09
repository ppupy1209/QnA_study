package toyproject.qna.module.answer.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import toyproject.qna.module.answer.entity.Answer;
import toyproject.qna.module.answer.entity.QAnswer;
import toyproject.qna.module.member.entity.QMember;

import javax.persistence.EntityManager;
import java.util.List;

import static toyproject.qna.module.answer.entity.QAnswer.*;
import static toyproject.qna.module.member.entity.QMember.*;

public class AnswerRepositoryImpl implements AnswerRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public AnswerRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Answer> findAnswersByQuestionId(Long id) {
        return queryFactory.selectFrom(answer)
                .join(answer.member, member).fetchJoin()
                .where(answer.question.id.eq(id))
                .fetch();

    }
}
