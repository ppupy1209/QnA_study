package toyproject.qna.module.question.entity;

import lombok.*;
import toyproject.qna.global.entity.BaseEntity;
import toyproject.qna.module.tag.entity.Tag;

import javax.persistence.*;

import static javax.persistence.FetchType.*;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "question_tags")
public class QuestionTag extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_tag_id",updatable = false)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "question_id")
    private Question question;


    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "tag_id")
    private Tag tag;

    @Builder
    public QuestionTag(Question question, Tag tag) {
        this.question = question;
        this.tag = tag;
    }

    public static QuestionTag createQuestionTag(Question question, Tag tag) {
        return  QuestionTag.builder()
                .question(question)
                .tag(tag)
                .build();
    }
}
