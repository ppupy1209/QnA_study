package toyproject.qna.module.question.dto;

import lombok.Getter;
import org.springframework.lang.Nullable;
import toyproject.qna.global.utils.NotBlankOrNull;
import toyproject.qna.module.question.entity.Question;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Getter
public class QuestionPostDto {

    @Positive
    private Long memberId;

    @NotBlank(message = "제목을 입력하세요.")
    private String title;
    @NotBlank(message = "내용을 입력하세요.")
    private String content;

    @Nullable
    private String[] tags;

    public Question toEntity() {
        return Question.builder()
                .content(content)
                .title(title)
                .build();
    }

}
