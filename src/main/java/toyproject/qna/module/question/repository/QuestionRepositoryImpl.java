package toyproject.qna.module.question.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import toyproject.qna.module.question.dto.QuestionSearchCondition;
import toyproject.qna.module.question.entity.QQuestionTag;
import toyproject.qna.module.question.entity.Question;
import toyproject.qna.module.tag.entity.QTag;

import javax.persistence.EntityManager;
import java.util.List;

import static org.thymeleaf.util.StringUtils.isEmpty;
import static toyproject.qna.module.member.entity.QMember.*;
import static toyproject.qna.module.question.entity.QQuestion.*;
import static toyproject.qna.module.question.entity.QQuestionTag.*;
import static toyproject.qna.module.tag.entity.QTag.tag;

public class QuestionRepositoryImpl implements QuestionRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public QuestionRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


    @Override
    public Page<Question> findQuestionsWithMember(Pageable pageable) {
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

    @Override
    public Page<Question> searchQuestions(QuestionSearchCondition condition, Pageable pageable) {
        QueryResults<Question> results = queryFactory.selectDistinct(question)
                .from(question)
                .innerJoin(question.member, member).fetchJoin()
                .leftJoin(questionTag).on(questionTag.question.id.eq(question.id))
                .leftJoin(questionTag.tag, tag)
                .where(tagEq(condition.getTag()),
                        searchKeywordLike(condition.getKeyword()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<Question> questions = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(questions,pageable,total);
    }

    private BooleanExpression tagEq(String tag) {
        if (isEmpty(tag)) return null;

            String[] tags = tag.split(",");
            BooleanExpression tagExpressions = null;

        for (String s : tags) {
            tagExpressions = QTag.tag.name.eq(s);

            if(tagExpressions!=null) {
                tagExpressions = tagExpressions.and(tagExpressions);
            }
        }

        return tagExpressions;
        }


    private BooleanExpression searchKeywordLike(String keyword) {
        if(isEmpty(keyword)) {
            return null;
        } else {
            return question.title.containsIgnoreCase(keyword).or(question.member.name.containsIgnoreCase(keyword));
        }
    }

}
