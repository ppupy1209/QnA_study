package toyproject.qna.module.order.entity;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import toyproject.qna.module.address.Address;
import toyproject.qna.module.delivery.entity.Delivery;
import toyproject.qna.module.delivery.entity.DeliveryStatus;
import toyproject.qna.module.member.entity.Member;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
@Transactional
@SpringBootTest
class OrderTest {


    @DisplayName("이미 배송완료된 상품은 취소가 불가능하다.")
    @Test
    void cancelException() {
    // given
       Delivery delivery = Delivery.createDelivery(Address.createAddress("seoul","street1","12-123"));
        delivery.changeDeliveryStatus(DeliveryStatus.COMP);

     Order order = Order.builder()
             .member(createMember())
             .orderStatus(OrderStatus.ORDER)
             .delivery(delivery)
             .build();

    // when & then
        assertThatThrownBy(() -> order.cancel())
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("이미 배송완료된 상품입니다.");
    }

    private Member createMember() {
        return Member.builder()
                .name("kim")
                .age(10)
                .build();
    }

}