package toyproject.qna.module.answer.dto;

import lombok.Getter;
import toyproject.qna.module.answer.entity.Answer;

import javax.validation.constraints.NotBlank;

@Getter
public class AnswerPatchDto {

    @NotBlank(message = "내용을 입력하세요.")
    private String content;

    public Answer toEntity() {
        return Answer.builder()
                .content(content)
                .build();
    }
}
