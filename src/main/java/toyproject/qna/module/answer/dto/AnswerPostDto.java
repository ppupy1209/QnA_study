package toyproject.qna.module.answer.dto;


import lombok.Getter;
import toyproject.qna.module.answer.entity.Answer;

import javax.validation.constraints.NotBlank;

@Getter
public class AnswerPostDto {

    private Long memberId;
    private Long questionId;
    @NotBlank(message = "내용을 입력해주세요.")
    private String content;

    public Answer toEntity() {
        return Answer.builder()
                .content(content)
                .build();
    }
}
