package toyproject.qna.module.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.qna.global.dto.MultiResponseDto;
import toyproject.qna.module.member.dto.MemberListResponseDto;
import toyproject.qna.module.member.entity.Member;
import toyproject.qna.module.member.repository.MemberRepository;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberQueryService {

    private final MemberRepository memberRepository;


    public MultiResponseDto findMembers(int page, int size) {
        Page<Member> pagedMembers = memberRepository.findAll(PageRequest.of(page, size, Sort.by("id").descending()));
        List<Member> members = pagedMembers.getContent();

        return new MultiResponseDto<>(MemberListResponseDto.of(members),pagedMembers);
    }
}
