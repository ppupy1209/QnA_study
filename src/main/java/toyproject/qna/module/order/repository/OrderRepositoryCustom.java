package toyproject.qna.module.order.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import toyproject.qna.module.order.entity.Order;

import java.util.List;

public interface OrderRepositoryCustom {
    // 주문 조회 | fetch join 최적화
    Page<Order> findOrdersWithMemberAndDelivery(Pageable pageable);
}
