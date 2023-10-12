package toyproject.qna.global.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import toyproject.qna.module.answer.repository.AnswerRepository;
import toyproject.qna.module.member.entity.Member;
import toyproject.qna.module.member.repository.MemberRepository;
import toyproject.qna.module.question.repository.QuestionRepository;
import toyproject.qna.module.tag.repository.TagRepository;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer {

    private final MemberRepository memberRepository;

    @PostConstruct
    public void init() {
        Member member1 = createMember("1번", 10);
        Member member2 = createMember("2번", 20);
        Member member3 = createMember("3번", 30);
        Member member4 = createMember("4번", 40);
        Member member5 = createMember("5번", 50);

        memberRepository.saveAll(List.of(member1,member2,member3,member4,member5));
    }

    private Member createMember(String name, int age) {
        return Member.builder()
                .name(name)
                .age(age)
                .build();
    }
}
