package toyproject.qna.module.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import toyproject.qna.module.member.entity.Member;

@Getter
@AllArgsConstructor
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
}
