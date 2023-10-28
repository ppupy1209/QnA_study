package toyproject.qna.module.delivery.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import toyproject.qna.module.address.Address;
import toyproject.qna.module.order.entity.Order;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
public class Delivery {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery",fetch = LAZY)
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;

    // Setter
    public void changeDeliveryStatus(DeliveryStatus deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    // Builder
    @Builder
    public Delivery(Address address) {
        this.address = address;
        this.deliveryStatus = DeliveryStatus.READY;
    }

    // 생성 메서드
    public static Delivery createDelivery(Address address) {
        return Delivery.builder()
                .address(address)
                .build();
    }
    
}
