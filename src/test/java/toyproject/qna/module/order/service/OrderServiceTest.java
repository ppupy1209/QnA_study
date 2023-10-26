package toyproject.qna.module.order.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import toyproject.qna.module.address.Address;
import toyproject.qna.module.delivery.entity.Delivery;
import toyproject.qna.module.delivery.repository.DeliveryRepository;
import toyproject.qna.module.item.entity.Item;
import toyproject.qna.module.item.repository.ItemRepository;
import toyproject.qna.module.item.servcie.ItemService;
import toyproject.qna.module.member.entity.Member;
import toyproject.qna.module.member.repository.MemberRepository;
import toyproject.qna.module.member.service.MemberService;
import toyproject.qna.module.order.dto.OrderItemDto;
import toyproject.qna.module.order.dto.OrderPostDto;
import toyproject.qna.module.order.entity.Order;
import toyproject.qna.module.order.entity.OrderItem;
import toyproject.qna.module.order.repository.OrderItemRepository;
import toyproject.qna.module.order.repository.OrderRepository;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
@Transactional
@SpringBootTest
class OrderServiceTest {

    @Autowired
    private OrderService orderService;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private DeliveryRepository deliveryRepository;
    @Autowired
    private ItemRepository itemRepository;



    @DisplayName("주문을 생성한다.")
    @Test
    void createOrder() {
        // given
        Member member = memberRepository.save(createMember());

        Item item1 = createItem(5000,100,"item1");
        Item item2 = createItem(4000,50,"item2");
        itemRepository.saveAll(List.of(item1,item2));

        OrderItemDto orderItemDto1 = createOrderItemDto(1L, 3);
        OrderItemDto orderItemDto2 = createOrderItemDto(2L, 4);
        List<OrderItemDto> orderItemDtos = List.of(orderItemDto1,orderItemDto2);

        // when
        Long orderId = orderService.createOrder(member.getId(), orderItemDtos, "seoul", "street1", "123-123");
        List<OrderItem> orderItems = orderItemRepository.findAll();
        List<Delivery> delivery = deliveryRepository.findAll();
        
        // then
        assertThat(orderId).isNotNull();
        assertThat(orderItems).hasSize(2);
        assertThat(delivery).hasSize(1);
    }

    private static OrderItemDto createOrderItemDto(long itemId, int quantity) {
        return OrderItemDto.builder()
                .itemId(itemId)
                .quantity(quantity)
                .build();
    }

    private Member createMember() {
        return Member.builder()
                .age(10)
                .name("kim")
                .build();
    }

    private Item createItem(int price,int stockQuantity,String name) {
        return Item.builder()
                .price(5000)
                .stockQuantity(stockQuantity)
                .name(name)
                .build();

    }
}