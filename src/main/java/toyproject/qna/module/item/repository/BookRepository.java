package toyproject.qna.module.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toyproject.qna.module.item.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
}
