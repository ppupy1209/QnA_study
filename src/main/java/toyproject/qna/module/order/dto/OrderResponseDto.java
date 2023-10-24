package toyproject.qna.module.order.dto;

import lombok.Builder;
import lombok.Getter;
import toyproject.qna.module.order.entity.Order;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class OrderResponseDto {
    private Long orderId;
    private String memberName;
    private List<OrderItemDto> items;
    private String city;
    private String street;
    private String zipcode;


    public static OrderResponseDto of(Order order) {
        return OrderResponseDto.builder()
                .orderId(order.getId())
                .memberName(order.getMember().getName())
                .items(order.getOrderItems().stream().map(
                       OrderItemDto::of
                ).collect(Collectors.toList()))
                .city(order.getDelivery().getAddress().getCity())
                .street(order.getDelivery().getAddress().getStreet())
                .zipcode(order.getDelivery().getAddress().getZipcode())
                .build();
    }

    public static List<OrderResponseDto> of(List<Order> orders) {
        return orders.stream().map(
               OrderResponseDto::of
        ).collect(Collectors.toList());
    }
}
