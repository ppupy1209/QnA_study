package toyproject.qna.module.answer.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import toyproject.qna.global.entity.BaseEntity;
import toyproject.qna.module.member.entity.Member;
import toyproject.qna.module.question.entity.Question;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "answers")
public class Answer extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "answer_id", updatable = false)
    private Long id;

    @Column(name = "content")
    private String content;


    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "question_id")
    private Question question;
}