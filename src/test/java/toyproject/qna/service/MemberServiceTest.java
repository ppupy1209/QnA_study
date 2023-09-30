package toyproject.qna.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import toyproject.qna.module.member.entity.Member;
import toyproject.qna.module.member.repository.MemberRepository;
import toyproject.qna.module.member.service.MemberService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MemberServiceTest {

    private MemberService memberService;
    private MemberRepository memberRepository;

    @BeforeEach
    public void beforeEach() {
        memberRepository = Mockito.mock(MemberRepository.class);

        memberService = new MemberService(memberRepository);
    }

    @Test
    @DisplayName("회원 등록 성공 테스트")
    public void createMemberTest() {

        // given
        Member member = Member.builder()
                .name("kim")
                .age(10)
                .build();

        ArgumentCaptor<Member> memberArgumentCaptor = ArgumentCaptor.forClass(Member.class);

        // when
        memberService.createMember(member);

        // then
        Mockito.verify(memberRepository,Mockito.times(1)).save(memberArgumentCaptor.capture());
        Member capturedMember = memberArgumentCaptor.getValue();
        assertEquals(member.getName(),capturedMember.getName());
        assertEquals(member.getAge(),capturedMember.getAge());
    }

    @Test
    @DisplayName("중복 회원 예외 테스트")
    public void createMemberIllegalStateExceptionTest() throws Exception{

        // given
        String givenName = "testName";

        Member member1 = Member.builder()
                .name(givenName)
                .age(10)
                .build();

        Member member2 = Member.builder()
                .name(givenName)
                .age(20)
                .build();

        Mockito.when(memberRepository.findByName(givenName)).thenReturn(List.of(
                        member1
                ));

        // when & then
        assertThrows(IllegalStateException.class, () -> {
          memberService.createMember(member2);
        });
    }
}
