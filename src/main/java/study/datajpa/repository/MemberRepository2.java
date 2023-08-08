package study.datajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.datajpa.entity.Member;

import java.util.List;

public interface MemberRepository2  extends JpaRepository<Member,Long> {

    List<Member> findByUsername(String name);
}
