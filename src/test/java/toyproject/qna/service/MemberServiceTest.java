package toyproject.qna.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import toyproject.qna.global.exception.BusinessLogicException;
import toyproject.qna.global.exception.ExceptionCode;
import toyproject.qna.module.member.dto.MemberResponseDto;
import toyproject.qna.module.member.entity.Member;
import toyproject.qna.module.member.repository.MemberRepository;
import toyproject.qna.module.member.service.MemberService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MemberServiceTest {

    private MemberService memberService;
    private MemberRepository memberRepository;

    @BeforeEach
    public void beforeEach() {
        memberRepository = mock(MemberRepository.class);

        memberService = new MemberService(memberRepository);
    }

    @Test
    @DisplayName("회원 등록 테스트")
    public void createMemberTest() throws Exception {
        // given
        Member member = Member.builder()
                .name("kim")
                .age(10)
                .build();

        ArgumentCaptor<Member> memberArgumentCaptor = ArgumentCaptor.forClass(Member.class);

        when(memberRepository.save(member)).thenReturn(member);

        // when
        memberService.createMember(member);

        // then
        verify(memberRepository, times(1)).findByName(member.getName());

        verify(memberRepository, times(1)).save(memberArgumentCaptor.capture());
        Member capturedMember = memberArgumentCaptor.getValue();
        assertEquals(member.getName(),capturedMember.getName());
        assertEquals(member.getAge(),capturedMember.getAge());
    }

    @Test
    @DisplayName("중복 회원 예외 테스트")
    public void  validateDuplicateMemberIllegalStateExceptionTest() throws Exception {
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

        when(memberRepository.findByName(givenName)).thenReturn(List.of(
                        member1
                ));

        // when & then
        assertThrows(IllegalStateException.class, () -> {
          memberService.createMember(member2);
        });

    }

    @Test
    @DisplayName("회원 수정 테스트")
    public void updateMemberTest() {
        // given
        Member member = Member.builder()
                .name("kim")
                .age(10)
                .build();

        Member changedMember = Member.builder()
                .name("lee")
                .age(20)
                .build();

        when(memberRepository.findById(anyLong())).thenReturn(Optional.ofNullable(member));
        when(memberRepository.findByName(changedMember.getName())).thenReturn(List.of());

        // when
        memberService.updateMember(changedMember, anyLong());

        // then
        verify(memberRepository, times(1)).findByName(changedMember.getName());
        verify(memberRepository, times(1)).findById(anyLong());


        assertEquals(changedMember.getName(),member.getName());
        assertEquals(changedMember.getAge(),member.getAge());
    }

    @Test
    @DisplayName("MEMBER_NOT_FOUND 예외 테스트")
    public void memberNotFoundTest() {
        // given
        when(memberRepository.findById(anyLong())).thenReturn(Optional.empty());

        // when & then
        BusinessLogicException businessLogicException = assertThrows(BusinessLogicException.class, () -> {
            memberService.updateMember(Member.builder().build(), anyLong());
        });

        ExceptionCode exceptionCode = businessLogicException.getExceptionCode();
        assertEquals(ExceptionCode.MEMBER_NOT_FOUND,exceptionCode);
    }

    @Test
    @DisplayName("회원 단건 조회 테스트")
    public void findMemberTest() {
        // given
        Member member = Member.builder()
                .name("kim")
                .age(10)
                .build();

        when(memberRepository.findById(anyLong())).thenReturn(Optional.of(member));

        // when
        Member findMember = memberService.findMember(anyLong());

        // then
        verify(memberRepository,times(1)).findById(anyLong());
        assertEquals(member,findMember);
    }

    @Test
    @DisplayName("회원 리스트 조회 테스트")
    public void findMembersTest() {
        // given
        Member member1 = Member.builder()
                .name("kim")
                .age(10)
                .build();

        Member member2 = Member.builder()
                .name("lee")
                .age(20)
                .build();

        Member member3 = Member.builder()
                .name("park")
                .age(30)
                .build();

        Page<Member> pageOfMembers = new PageImpl<>(List.of(member3,member2,member1));

        when(memberRepository.findAll(PageRequest.of(0, 3, Sort.by("id").descending()))).thenReturn(
                           pageOfMembers
         );

        // when
        List<MemberResponseDto> expectedMembers = List.of(member3,member2,member1)
                .stream()
                .map(member -> new MemberResponseDto(member.getId(),member.getName(),member.getAge()))
                .collect(Collectors.toList());

        Page<Member> actualMembersPage = memberService.findMembers(0,3);

        List<MemberResponseDto> actualMembers = actualMembersPage.getContent()
                .stream()
                .map(member -> new MemberResponseDto(member.getId(),member.getName(),member.getAge()))
                .collect(Collectors.toList());

        // then
        assertIterableEquals(expectedMembers,actualMembers);
    }


}
