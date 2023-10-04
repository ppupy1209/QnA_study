package toyproject.qna.module.question.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import toyproject.qna.module.member.entity.QMember;
import toyproject.qna.module.question.entity.QQuestion;
import toyproject.qna.module.question.entity.Question;

import javax.persistence.EntityManager;
import java.util.List;

import static toyproject.qna.module.member.entity.QMember.*;
import static toyproject.qna.module.question.entity.QQuestion.*;

public class QuestionRepositoryImpl implements QuestionRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public QuestionRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


    @Override
    public Page<Question> findWithMember(Pageable pageable) {
        QueryResults<Question> results = queryFactory.selectFrom(question)
                .innerJoin(question.member, member)
                .fetchJoin()
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<Question> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content,pageable,total);
    }
}
