package toyproject.qna.module.answer.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.qna.global.exception.BusinessLogicException;
import toyproject.qna.global.exception.ExceptionCode;
import toyproject.qna.module.answer.entity.Answer;
import toyproject.qna.module.answer.repository.AnswerRepository;
import toyproject.qna.module.member.entity.Member;
import toyproject.qna.module.member.service.MemberService;
import toyproject.qna.module.question.entity.Question;
import toyproject.qna.module.question.service.QuestionService;

import java.util.Optional;

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

        Answer createdAnswer = Answer.createAnswer(answer.getContent(), member, question);

        Answer savedAnswer = answerRepository.save(createdAnswer);

        return savedAnswer.getId();
    }

    public Answer updateAnswer(Long answerId, Answer answer) {
        Answer findAnswer = findVerifiedAnswer(answerId);

        Optional.ofNullable(answer.getContent())
                .ifPresent(content -> findAnswer.changeContent(content));

        return findAnswer;
    }

   public void deleteAnswer(Long answerId) {
        Answer findAnswer = findVerifiedAnswer(answerId);

        answerRepository.delete(findAnswer);
   }



    private Answer findVerifiedAnswer(Long answerId) {
        Optional<Answer> optionalAnswer = answerRepository.findById(answerId);

        Answer answer = optionalAnswer.orElseThrow(() -> new BusinessLogicException(ExceptionCode.ANSWER_NOT_FOUND));

        return answer;
    }
}
