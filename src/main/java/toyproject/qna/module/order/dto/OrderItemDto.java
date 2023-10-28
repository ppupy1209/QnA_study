package toyproject.qna.module.order.dto;

import lombok.Builder;
import lombok.Getter;
import toyproject.qna.module.order.entity.Order;
import toyproject.qna.module.order.entity.OrderItem;

@Getter
@Builder
public class OrderItemDto {
    // 주문할 아이템 ID
    private Long itemId;

    // 주문 수량
    private int quantity;

    public static OrderItemDto of(OrderItem orderItem) {
        return OrderItemDto.builder()
                .itemId(orderItem.getItem().getId())
                .quantity(orderItem.getCount())
                .build();
    }
}
