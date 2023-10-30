package toyproject.qna.module.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.qna.global.exception.BusinessLogicException;
import toyproject.qna.global.exception.ExceptionCode;
import toyproject.qna.module.member.dto.MemberResponseDto;
import toyproject.qna.module.member.entity.Member;
import toyproject.qna.module.member.repository.MemberRepository;
import toyproject.qna.module.order.entity.Order;
import toyproject.qna.module.order.repository.OrderRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;

    public Long createMember(Member member) {
        validateDuplicateMember(member); // 중복 회원 체크
        Member savedMember = memberRepository.save(member);

        return savedMember.getId();

    }

    public Member updateMember(Member member,Long memberId) {
        Member findMember = findVerifiedMember(memberId);

        Optional.ofNullable(member.getName())
                .ifPresent(name -> findMember.changeName(name));
        Optional.ofNullable(member.getAge())
                .ifPresent(age -> findMember.changeAge(age));

        validateDuplicateMember(findMember);

        return findMember;
    }

    @Transactional(readOnly = true)
    public MemberResponseDto findMember(Long memberId) {
        Member member = findVerifiedMember(memberId);
        List<Order> orders = orderRepository.findOrdersByMemberId(memberId);

        return MemberResponseDto.of(member,orders);
    }

    @Transactional(readOnly = true)
    public Page<Member> findMembers(int page, int size) {
        return memberRepository.findAll(PageRequest.of(page, size, Sort.by("id").descending()));
    }

    public void deleteMember(Long memberId) {
        Member member = findVerifiedMember(memberId);

        memberRepository.delete(member);
    }


    public Member findVerifiedMember(Long memberId) {
        Optional<Member> member = memberRepository.findById(memberId);

        Member findMember = member.orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));

        return findMember;
    }


    private void validateDuplicateMember(Member member) {
        List<Member> members = memberRepository.findByName(member.getName());

        // 동일인이 있다면 예외 발생
        if(!members.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }
}
