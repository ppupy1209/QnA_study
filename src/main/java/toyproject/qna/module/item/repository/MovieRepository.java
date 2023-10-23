package toyproject.qna.module.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toyproject.qna.module.item.entity.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {
}
