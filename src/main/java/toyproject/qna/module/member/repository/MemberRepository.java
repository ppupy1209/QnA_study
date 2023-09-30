package toyproject.qna.module.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toyproject.qna.module.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
