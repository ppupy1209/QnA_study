package toyproject.qna.module.delivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toyproject.qna.module.delivery.entity.Delivery;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
}
