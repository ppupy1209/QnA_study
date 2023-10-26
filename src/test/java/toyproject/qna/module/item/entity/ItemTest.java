package toyproject.qna.module.item.entity;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import toyproject.qna.module.item.repository.ItemRepository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
@Transactional
@SpringBootTest
class ItemTest {

    @Autowired
    private ItemRepository itemRepository;

    @DisplayName("재고가 0개일 때 재고 감소시도하면 에러가 발생한다.")
    @Test
    void removeStockException() {
    // given
     Item item = Item.builder()
             .stockQuantity(2)
             .price(5000)
             .name("test")
             .build();
     itemRepository.save(item);

    // when & then
        assertThatThrownBy(() -> item.removeStock(3))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("need more stock");
    }
}