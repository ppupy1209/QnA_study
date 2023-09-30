package toyproject.qna.module.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toyproject.qna.module.member.entity.Member;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findByName(String name);
}
