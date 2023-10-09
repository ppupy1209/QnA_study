package toyproject.qna.module.question.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import toyproject.qna.module.member.entity.Member;
import toyproject.qna.module.member.repository.MemberRepository;
import toyproject.qna.module.question.entity.Question;

import java.util.List;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
class QuestionRepositoryTest {
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("질믄들을 조회한다.")
    @Test
    void findWithMember() {
        // given
        Member member = createMember();
        Member savedMember = memberRepository.save(member);

        Question question1 = createQuestion("제목 1", "내용 1", savedMember);
        Question question2 = createQuestion("제목 2", "내용 2", savedMember);
        Question question3 = createQuestion("제목 3", "내용 3", savedMember);

        questionRepository.saveAll(List.of(question1,question2,question3));

        int page = 0;
        int size = 2;

        // when
        Page<Question> pageQuestions = questionRepository.findQuestionsWithMember(PageRequest.of(page, size, Sort.by("id").descending()));
        List<Question> questions = pageQuestions.getContent();

        // then
        assertThat(pageQuestions).hasSize(2);
        assertThat(pageQuestions.getTotalElements()).isEqualTo(3);
        assertThat(pageQuestions.getNumber()).isEqualTo(0);
        assertThat(pageQuestions.getTotalPages()).isEqualTo(2);
        assertThat(pageQuestions.isFirst()).isTrue();
        assertThat(pageQuestions.hasNext()).isTrue();

        assertThat(questions).hasSize(2)
                .extracting("title","content")
                .containsExactlyInAnyOrder(
                        tuple("제목 1","내용 1"),
                        tuple("제목 2","내용 2")
                );
    }


    private Member createMember() {
        return Member.builder()
                .age(10)
                .name("test")
                .build();
    }

    private Question createQuestion(String title, String content, Member member) {
        return Question.builder()
                .title(title)
                .content(content)
                .member(member)
                .build();
    }

}