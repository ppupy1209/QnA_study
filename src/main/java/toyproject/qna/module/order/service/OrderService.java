package toyproject.qna.module.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.qna.global.dto.MultiResponseDto;
import toyproject.qna.global.exception.BusinessLogicException;
import toyproject.qna.global.exception.ExceptionCode;
import toyproject.qna.module.address.Address;
import toyproject.qna.module.delivery.entity.Delivery;
import toyproject.qna.module.item.entity.Item;
import toyproject.qna.module.item.servcie.ItemService;
import toyproject.qna.module.member.entity.Member;
import toyproject.qna.module.member.service.MemberService;
import toyproject.qna.module.order.dto.OrderItemDto;
import toyproject.qna.module.order.dto.OrderPostDto;
import toyproject.qna.module.order.dto.OrderResponseDto;
import toyproject.qna.module.order.entity.Order;
import toyproject.qna.module.order.entity.OrderItem;
import toyproject.qna.module.order.repository.OrderRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Transactional
@RequiredArgsConstructor
@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final MemberService memberService;
    private final ItemService itemService;

    // 주문 생성
    public Long createOrder(OrderPostDto orderPostDto) {
        Member member = memberService.findVerifiedMember(orderPostDto.getMemberId());
        Address address = Address.createAddress(orderPostDto.getCity(), orderPostDto.getStreet(), orderPostDto.getZipcode());
        Delivery delivery = Delivery.createDelivery(address);

        List<OrderItem> orderItems = createOrderItems(orderPostDto.getItems());

        Order order = Order.createOrder(member,delivery,orderItems);
        Order saveOrder = orderRepository.save(order);

        return saveOrder.getId();
    }


    // 주문 취소
    public void cancelOrder(Long orderId) {
        Order order = findVerifiedOrder(orderId);

        order.cancel();
    }

    // 주문 리스트 조회
    @Transactional(readOnly = true)
    public MultiResponseDto findOrders(int page, int size) {
        Page<Order> pagedOrders = orderRepository.findOrdersWithMemberAndDelivery(PageRequest.of(page,size));
        List<Order> orders = pagedOrders.getContent();

        return new MultiResponseDto<>(OrderResponseDto.of(orders),pagedOrders);
    }

    // 단건 주문 조회
    private Order findVerifiedOrder(Long orderId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);

        Order order = optionalOrder.orElseThrow(() -> new BusinessLogicException(ExceptionCode.ORDER_NOT_FOUND));

        return order;
    }

    // OrderItem 생성
    private List<OrderItem> createOrderItems(List<OrderItemDto> items) {
        List<OrderItem> orderItems = new ArrayList<>();
        for (OrderItemDto item : items) {
            Item findItem = itemService.findItem(item.getItemId());
            OrderItem orderItem = OrderItem.createOrderItem(findItem, findItem.getPrice(),item.getQuantity());
            orderItems.add(orderItem);
        }
        return orderItems;
    }

}
