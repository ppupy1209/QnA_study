package toyproject.qna.module.delivery;

import lombok.Getter;
import org.springframework.data.domain.PageRequest;
import toyproject.qna.module.address.Address;
import toyproject.qna.module.order.entity.Order;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

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
}
