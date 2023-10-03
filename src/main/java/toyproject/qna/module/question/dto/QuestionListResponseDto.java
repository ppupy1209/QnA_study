package toyproject.qna.module.question.dto;

import lombok.Builder;
import lombok.Getter;
import toyproject.qna.module.member.entity.Member;
import toyproject.qna.module.question.entity.Question;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class QuestionListResponseDto {

    private String memberName;
    private String title;
    private int answerCount;
    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;

    public static QuestionListResponseDto of(Question question) {
        return QuestionListResponseDto.builder()
                .memberName(question.getMember().getName())
                .title(question.getTitle())
                .answerCount(question.getAnswers().size())
                .createdAt(question.getCreatedAt())
                .lastModifiedAt(question.getLastModifiedAt())
                .build();
    }
}
