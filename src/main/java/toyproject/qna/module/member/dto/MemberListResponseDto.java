package toyproject.qna.module.member.dto;

import lombok.Builder;
import lombok.Getter;
import toyproject.qna.module.member.entity.Member;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class MemberListResponseDto {

    private String name;
    private Integer age;
    private Integer orderCnt;

    public static List<MemberListResponseDto> of(List<Member> members) {
        return members.stream()
                .map(member -> MemberListResponseDto
                        .builder()
                        .name(member.getName())
                        .age(member.getAge())
                        .orderCnt(member.getOrders().size())
                        .build())
                .collect(Collectors.toList());
    }
}
