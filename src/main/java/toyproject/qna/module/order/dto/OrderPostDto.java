package toyproject.qna.module.order.dto;

import lombok.Builder;
import lombok.Getter;
import toyproject.qna.module.order.entity.Order;

import java.util.List;
@Builder
@Getter
public class OrderPostDto {
    private Long memberId;
    private List<OrderItemDto> items;
    private String city;
    private String street;
    private String zipcode;
}
