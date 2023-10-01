package toyproject.qna.module.member.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import toyproject.qna.global.utils.NotBlankOrNull;
import toyproject.qna.module.member.entity.Member;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
@Builder
public class MemberPatchDto {

    @NotBlankOrNull
    private String name;
    @Min(value = 1,message = "나이 최솟값은 1 입니다.")
    private Integer age;

    public Member toEntity() {
        return Member.builder()
                .name(name)
                .age(age)
                .build();
    }

}
