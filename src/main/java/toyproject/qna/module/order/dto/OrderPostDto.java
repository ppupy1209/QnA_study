package toyproject.qna.module.order.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class OrderPostDto {
    private Long memberId;
    private List<OrderItemDto> items;
    private String city;
    private String street;
    private String zipcode;
}
