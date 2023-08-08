package study.datajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.datajpa.entity.Team;

//sping 은 이 인터페이스를 보고 유령 클래스를 만들어준다.
//즉, 개발자가 직접 구현체를 만들필요가 없다.
//spring jpa 가 구현체를 만들어준다.
//spring jpa에서 지원하는 인터페이스는 구현체를 만들어준다.

//@Repository 가 없어도 된다.
//spring boot 가 spring jpa 인터에이스에 대해서는 다 알아서 해준다.
public interface TeamRepository extends JpaRepository<Team,Long> {
    //JpaRepository<대상객체,엔티티pk>
    //interface에 이미 정의된 많은 기능이 있음



}
