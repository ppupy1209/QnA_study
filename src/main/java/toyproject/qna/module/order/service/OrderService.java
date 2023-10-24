package toyproject.qna.module.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.qna.global.exception.BusinessLogicException;
import toyproject.qna.global.exception.ExceptionCode;
import toyproject.qna.module.address.Address;
import toyproject.qna.module.delivery.entity.Delivery;
import toyproject.qna.module.item.entity.Item;
import toyproject.qna.module.item.repository.ItemRepository;
import toyproject.qna.module.item.servcie.ItemService;
import toyproject.qna.module.member.entity.Member;
import toyproject.qna.module.member.service.MemberService;
import toyproject.qna.module.order.dto.OrderItemDto;
import toyproject.qna.module.order.dto.OrderPostDto;
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
    public Long createOrder(OrderPostDto orderPostDto) {
        Member member = memberService.findMember(orderPostDto.getMemberId());
        Address address = Address.createAddress(orderPostDto.getCity(), orderPostDto.getStreet(), orderPostDto.getZipcode());
        Delivery delivery = Delivery.createDelivery(address);

        List<OrderItemDto> items = orderPostDto.getItems();
        List<OrderItem> orderItems = new ArrayList<>();
        for (OrderItemDto item : items) {
            Item findItem = itemService.findItem(item.getItemId());
            OrderItem orderItem = OrderItem.createOrderItem(findItem, findItem.getPrice(),item.getQuantity());
            orderItems.add(orderItem);
        }

        Order order = Order.createOrder(member,delivery,orderItems);
        Order saveOrder = orderRepository.save(order);

        return saveOrder.getId();
    }

    public void cancelOrder(Long orderId) {
        Order order = findVerifiedOrder(orderId);

        order.cancel();
    }

    private Order findVerifiedOrder(Long orderId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);

        Order order = optionalOrder.orElseThrow(() -> new BusinessLogicException(ExceptionCode.ORDER_NOT_FOUND));

        return order;
    }




}
