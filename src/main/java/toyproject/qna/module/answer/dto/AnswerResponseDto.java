package toyproject.qna.module.answer.dto;

import lombok.Builder;
import lombok.Getter;
import toyproject.qna.module.answer.entity.Answer;
import toyproject.qna.module.member.entity.Member;

@Getter
@Builder
public class AnswerResponseDto {

    private Long memberId;
    private String memberName;
    private String content;

    public static AnswerResponseDto of(Answer answer) {
        return AnswerResponseDto.builder()
                .memberId(answer.getMember().getId())
                .memberName(answer.getMember().getName())
                .content(answer.getContent())
                .build();
    }
}
