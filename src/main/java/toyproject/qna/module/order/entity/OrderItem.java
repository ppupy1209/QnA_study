package toyproject.qna.module.order.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import toyproject.qna.module.item.entity.Item;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(name = "order_price")
    private int orderPrice;

    @Column(name = "count")
    private int count;

    // Builder
    @Builder
    public OrderItem(Item item,  int orderPrice, int count) {
        this.item = item;
        this.orderPrice = orderPrice;
        this.count = count;
    }

    // 생성 메서드
    public static OrderItem createOrderItem(Item item, int price, int quantity) {

        item.removeStock(quantity);

        return OrderItem.builder()
                .item(item)
                .orderPrice(price)
                .count(quantity)
                .build();

    }

    // Setter
    public void setOrder(Order order) {
        this.order = order;
    }

    // 주문 취소
    public void cancel() {
        this.getItem().addStock(count);
    }
}
