package toyproject.qna.module.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toyproject.qna.module.item.entity.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
