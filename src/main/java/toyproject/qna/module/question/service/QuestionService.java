package toyproject.qna.module.question.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.qna.global.exception.BusinessLogicException;
import toyproject.qna.global.exception.ExceptionCode;
import toyproject.qna.module.answer.dto.AnswerResponseDto;
import toyproject.qna.module.answer.entity.Answer;
import toyproject.qna.module.answer.repository.AnswerRepository;
import toyproject.qna.module.member.entity.Member;
import toyproject.qna.module.member.service.MemberService;
import toyproject.qna.module.question.dto.QuestionResponseDto;
import toyproject.qna.module.question.entity.Question;
import toyproject.qna.module.question.entity.QuestionTag;
import toyproject.qna.module.question.repository.QuestionRepository;
import toyproject.qna.module.question.repository.QuestionTagRepository;
import toyproject.qna.module.tag.entity.Tag;
import toyproject.qna.module.tag.repository.TagRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final QuestionTagRepository questionTagRepository;
    private final AnswerRepository answerRepository;
    private final TagRepository tagRepository;
    private final MemberService memberService;


    public Long createQuestion(Long memberId, Question question, String[] tags) {

        Member member = memberService.findMember(memberId);

        question.setMember(member);

        Question savedQuestion = questionRepository.save(question);

        // Tag 가 포함되어 있다면 Tag 저장
        if (tags.length != 0) saveTag(question, tags);

        return savedQuestion.getId();
    }

    public Question updateQuestion(Long questionId, Question question, String[] tags) {
        Question findQuestion = findVerifiedQuestion(questionId);

        Optional.ofNullable(question.getContent())
                .ifPresent(content -> findQuestion.changeContent(content));
        Optional.ofNullable(question.getTitle())
                .ifPresent(title -> findQuestion.changeTitle(title));

        // Tag 를 수정한다면 기존의 QuestionTag 를 삭제한 후 새로 저장
        if (tags.length != 0) {
            questionTagRepository.deleteByQuestion(findQuestion);
            saveTag(findQuestion, tags);
        }

        return findQuestion;
    }

    @Transactional(readOnly = true)
    public QuestionResponseDto findQuestion(Long questionId) {
        Question question = findVerifiedQuestion(questionId);

        List<Answer> answers = answerRepository.findWithMemberByQuestionId(questionId); // 페치 조인

        List<QuestionTag> questionTags = questionTagRepository.findByQuestionId(questionId);

        List<String> tags = getTagNameList(questionTags);

        List<AnswerResponseDto> answerResponseDtos = AnswerResponseDto.of(answers);

        return QuestionResponseDto.of(question, answerResponseDtos,tags);
    }

    private static List<String> getTagNameList(List<QuestionTag> questionTags) {
        return questionTags.stream()
                .map(questionTag -> questionTag.getTag().getName())
                .collect(Collectors.toList());
    }

    public void deleteQuestion(Long questionId) {
        Question question = findVerifiedQuestion(questionId);

        questionRepository.delete(question);
    }


    public Question findVerifiedQuestion(Long questionId) {
        Optional<Question> question = questionRepository.findById(questionId);

        Question findQuestion = question.orElseThrow(() -> new BusinessLogicException(ExceptionCode.QUESTION_NOT_FOUND));

        return findQuestion;
    }

    // Tag 저장 메서드
    private void saveTag(Question question, String[] tags) {
        QuestionTag questionTag;

        for (String tag : tags) {
            Optional<Tag> findTag = tagRepository.findByName(tag);


            if (findTag.isEmpty()) {
                Tag createdTag = Tag.createTag(tag);
                tagRepository.save(createdTag);
                questionTag = QuestionTag.createQuestionTag(question, createdTag);
            }

            else {
                questionTag = QuestionTag.createQuestionTag(question, findTag.get());
            }
            questionTagRepository.save(questionTag);
        }
    }

}
