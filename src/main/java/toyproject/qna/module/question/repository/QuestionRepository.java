package toyproject.qna.module.question.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import toyproject.qna.module.member.entity.Member;
import toyproject.qna.module.question.entity.Question;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {


    @Query("select q from Question q" +
            " join fetch q.member")
    List<Question> findWithMember(Pageable pageable);
}
