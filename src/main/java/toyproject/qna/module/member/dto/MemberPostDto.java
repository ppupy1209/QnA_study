package toyproject.qna.module.member.dto;


import lombok.Getter;
import toyproject.qna.module.member.entity.Member;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
public class MemberPostDto {

    @NotBlank(message = "이름은 필수 항목입니다.")
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
