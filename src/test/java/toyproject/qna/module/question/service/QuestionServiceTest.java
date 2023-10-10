package toyproject.qna.module.question.service;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import toyproject.qna.module.member.entity.Member;
import toyproject.qna.module.member.repository.MemberRepository;
import toyproject.qna.module.question.entity.Question;
import toyproject.qna.module.question.entity.QuestionTag;
import toyproject.qna.module.question.repository.QuestionRepository;
import toyproject.qna.module.question.repository.QuestionTagRepository;
import toyproject.qna.module.tag.entity.Tag;
import toyproject.qna.module.tag.repository.TagRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
class QuestionServiceTest {
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private QuestionTagRepository questionTagRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("질문을 등록한다.")
    @Test
    void createQuestionTest() {
        // given
        Member member = createMember();
        Member savedMember = memberRepository.save(member);

        Question question = createQuestion(member);

        String[] tags = {"a","b"};

        // when
        Long questionId = questionService.createQuestion(savedMember.getId(), question, tags);

        // then
        assertThat(questionId).isNotNull();
    }

    @DisplayName("질문 등록 시 태그를 저장한다.")
    @Test
    void saveTagWhenCreateQuestion() {
        // given
        Member member = createMember();
        Member savedMember = memberRepository.save(member);

        Question question = createQuestion(member);

        String[] tags = {"a","b"};

        // when
        questionService.createQuestion(savedMember.getId(), question, tags);

        List<Tag> savedTags = tagRepository.findAll();
        List<QuestionTag> questionTags = questionTagRepository.findAll();

        // then
        assertThat(savedTags).hasSize(2)
                .extracting("name")
                .containsExactlyInAnyOrder(
                       "a","b"
                );

        assertThat(questionTags).hasSize(2);
    }

    @DisplayName("질문 제목을 수정한다.")
    @Test
    void updateTitleOfQuestion() {
        // given
        Question question = Question.builder()
                .title("제목")
                .content("내용")
                .build();
        Question savedQuestion = questionRepository.save(question);

        Question updateTitleOfQuestion = Question.builder()
                .title("제목 수정")
                .build();

        String[] tags = {"test"};

        // when
        Question updatedTitleQuestion = questionService.updateQuestion(savedQuestion.getId(), updateTitleOfQuestion, tags);

        // then
        assertThat(updatedTitleQuestion.getTitle()).isEqualTo("제목 수정");
        assertThat(updatedTitleQuestion.getContent()).isEqualTo("내용");
    }

    @DisplayName("질문 내용을 수정한다.")
    @Test
    void updateContentOfQuestion() {
        // given
        Question question = Question.builder()
                .title("제목")
                .content("내용")
                .build();
        Question savedQuestion = questionRepository.save(question);

        Question updatedContentQuestion = Question.builder()
                .content("내용 수정")
                .build();

        String[] tags = {};

        // when
        Question updatedQuestion = questionService.updateQuestion(savedQuestion.getId(), updatedContentQuestion, tags);

        // then
        assertThat(updatedQuestion.getContent()).isEqualTo("내용 수정");
        assertThat(updatedQuestion.getTitle()).isEqualTo("제목");
    }

    @DisplayName("질문 제목과 내용을 수정한다.")
    @Test
    void updateTitleAndContentOfQuestion() {
        // given
        Question question = Question.builder()
                .title("제목")
                .content("내용")
                .build();
        Question savedQuestion = questionRepository.save(question);

        Question updatedTitleAndContentQuestion = Question.builder()
                .title("제목 수정")
                .content("내용 수정")
                .build();

        String[] tags = {};

        // when
        Question updatedQuestion = questionService.updateQuestion(savedQuestion.getId(), updatedTitleAndContentQuestion, tags);

        // then
        assertThat(updatedQuestion.getTitle()).isEqualTo("제목 수정");
        assertThat(updatedQuestion.getContent()).isEqualTo("내용 수정");
    }

    private  Question createQuestion(Member member) {
        return Question.builder()
                .member(member)
                .title("제목")
                .content("내용")
                .build();
    }

    private  Member createMember() {
        return Member.builder()
                .name("test")
                .age(10)
                .build();
    }
}