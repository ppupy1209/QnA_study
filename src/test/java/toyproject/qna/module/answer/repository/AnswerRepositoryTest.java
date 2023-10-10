package toyproject.qna.module.answer.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import toyproject.qna.module.answer.entity.Answer;
import toyproject.qna.module.member.entity.Member;
import toyproject.qna.module.member.repository.MemberRepository;
import toyproject.qna.module.question.entity.Question;
import toyproject.qna.module.question.repository.QuestionRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AnswerRepositoryTest {
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private QuestionRepository questionRepository;

    @DisplayName("질문에 대한 답변을 조회한다.")
    @Test
    void findAnswersByQuestionId() {
        // given
        Member member = memberRepository.save(createMember());

        Question question1 = questionRepository.save(createQuestion(member));
        Question question2 = questionRepository.save(createQuestion(member));

        Answer answer1 = createAnswer("답변 내용 1",question1,member);
        Answer answer2 = createAnswer("답변 내용 2",question1,member);
        Answer answer3 = createAnswer("답변 내용 3",question2,member);
        answerRepository.saveAll(List.of(answer1,answer2,answer3));

        // when
        List<Answer> answers = answerRepository.findAnswersByQuestionId(question1.getId());

        // then
        assertThat(answers).hasSize(2)
                .extracting("content")
                .containsExactlyInAnyOrder("답변 내용 1","답변 내용 2");
    }

    private static Member createMember() {
        return Member.builder()
                .age(10)
                .name("kim")
                .build();
    }

    private Question createQuestion(Member member) {
        return Question.builder()
                .title("제목")
                .content("내용")
                .member(member)
                .build();
    }

    private Answer createAnswer(String content, Question question,Member member) {
       return Answer.builder()
               .content(content)
               .question(question)
               .member(member)
               .build();
    }
}