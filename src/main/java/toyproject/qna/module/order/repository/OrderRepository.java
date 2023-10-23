package toyproject.qna.module.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toyproject.qna.module.order.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
