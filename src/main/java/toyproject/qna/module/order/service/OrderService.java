package toyproject.qna.module.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.qna.module.address.Address;
import toyproject.qna.module.delivery.entity.Delivery;
import toyproject.qna.module.item.entity.Item;
import toyproject.qna.module.item.repository.ItemRepository;
import toyproject.qna.module.item.servcie.ItemService;
import toyproject.qna.module.member.entity.Member;
import toyproject.qna.module.member.service.MemberService;
import toyproject.qna.module.order.dto.OrderPostDto;
import toyproject.qna.module.order.entity.Order;
import toyproject.qna.module.order.entity.OrderItem;
import toyproject.qna.module.order.repository.OrderRepository;

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
        Item item = itemService.findItem(orderPostDto.getItemId());

        Address address = Address.createAddress(orderPostDto.getCity(), orderPostDto.getStreet(), orderPostDto.getZipcode());

        Delivery delivery = Delivery.createDelivery(address);

        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(),orderPostDto.getQuantity());

        Order order = Order.createOrder(member,delivery,orderItem);

        Order savedOrder = orderRepository.save(order);

        return savedOrder.getId();
    }

    
}
