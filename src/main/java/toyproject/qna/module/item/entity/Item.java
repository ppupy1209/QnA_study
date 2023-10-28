package toyproject.qna.module.item.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import toyproject.qna.global.entity.BaseEntity;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Item extends BaseEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "item_id")
    private Long id;
    private String name;
    private int price;
    private int stockQuantity;

    // Builder
    @Builder
    public Item(String name, int price, int stockQuantity) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    // 재고 추가 메서드

    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    // 재고 감소 메서드
    public void removeStock(int quantity) {
        int restStock = this.stockQuantity;
        if(restStock-quantity<0) {
            throw new RuntimeException("need more stock");
        }
        this.stockQuantity -= quantity;
    }


}
