package toyproject.qna.module.answer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import toyproject.qna.module.answer.entity.Answer;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
}
