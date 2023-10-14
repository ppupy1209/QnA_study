package toyproject.qna.module.stock.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toyproject.qna.module.stock.entity.Stock;

public interface StockRepository extends JpaRepository<Stock, Long> {
}
