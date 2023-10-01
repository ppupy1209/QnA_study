package toyproject.qna.module.member.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import toyproject.qna.module.member.entity.Member;
import toyproject.qna.module.member.repository.MemberRepository;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class MemberResponseDto {

    private Long id;
    private String name;
    private Integer age;

    public static MemberResponseDto of(Member member) {
        return new MemberResponseDto(
                member.getId(),
                member.getName(),
                member.getAge()
        );
    }

    public static List<MemberResponseDto> of(List<Member> members) {
        return members.stream()
                .map(member -> new MemberResponseDto(member.getId(), member.getName(), member.getAge()))
                .collect(Collectors.toList());
    }
}
