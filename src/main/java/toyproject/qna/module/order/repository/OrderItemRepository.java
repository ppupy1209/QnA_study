package toyproject.qna.module.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toyproject.qna.module.order.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
