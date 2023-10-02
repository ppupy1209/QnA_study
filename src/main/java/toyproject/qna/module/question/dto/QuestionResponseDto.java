package toyproject.qna.module.question.dto;

import lombok.Builder;
import lombok.Getter;
import toyproject.qna.module.answer.dto.AnswerResponseDto;
import toyproject.qna.module.member.entity.Member;
import toyproject.qna.module.question.entity.Question;

import java.util.List;

@Getter
@Builder
public class QuestionResponseDto {
    private Long memberId;
    private String memberName;
    private String title;
    private String content;
    private List<AnswerResponseDto> answers;

    public static QuestionResponseDto of(Question question,List<AnswerResponseDto> answerResponseDtos) {
        return QuestionResponseDto.builder()
                .memberId(question.getMember().getId())
                .memberName(question.getMember().getName())
                .title(question.getTitle())
                .content(question.getContent())
                .answers(answerResponseDtos)
                .build();
    }
}
