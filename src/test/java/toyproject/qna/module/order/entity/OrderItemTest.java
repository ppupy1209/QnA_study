package toyproject.qna.module.order.entity;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import toyproject.qna.module.item.entity.Item;
import toyproject.qna.module.item.repository.ItemRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
@Transactional
@SpringBootTest
class OrderItemTest {
    @Autowired
    private ItemRepository itemRepository;

    @DisplayName("주문 시 해당 아이템 재고가 감소한다.")
    @Test
    void createOrderItem() {
    // given
        Item item1 = createItem("item1", 5000, 50);

        itemRepository.save(item1);
    // when
        OrderItem.createOrderItem(item1,item1.getPrice(),3);
    // then
        assertThat(item1.getStockQuantity()).isEqualTo(47);
    }

    private static Item createItem(String item1, int price, int stockQuantity) {
        return Item.builder()
                .name(item1)
                .price(price)
                .stockQuantity(stockQuantity)
                .build();
    }
}