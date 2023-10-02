package toyproject.qna.module.answer.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.qna.module.answer.entity.Answer;
import toyproject.qna.module.answer.repository.AnswerRepository;
import toyproject.qna.module.member.entity.Member;
import toyproject.qna.module.member.service.MemberService;
import toyproject.qna.module.question.entity.Question;
import toyproject.qna.module.question.service.QuestionService;

@Service
@Transactional
@RequiredArgsConstructor
public class AnswerService {
    private final AnswerRepository answerRepository;
    private final MemberService memberService;
    private final QuestionService questionService;
    public Long createAnswer(Long memberId, Long questionId, Answer answer) {
        Member member = memberService.findMember(memberId);
        Question question = questionService.findVerifiedQuestion(questionId);

        Answer createdAnswer = Answer.builder()
                .content(answer.getContent())
                .member(member)
                .question(question)
                .build();

        Answer savedAnswer = answerRepository.save(createdAnswer);

        return savedAnswer.getId();
    }
}
