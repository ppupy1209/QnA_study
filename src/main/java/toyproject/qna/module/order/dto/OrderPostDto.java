package toyproject.qna.module.order.dto;

import lombok.Getter;

@Getter
public class OrderPostDto {
    private Long memberId;
    private Long itemId;
    private int quantity;
    private String city;
    private String street;
    private String zipcode;
}
