package study.datajpa.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.EntityGraph;
import study.datajpa.entity.Member;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom{

//커스텀 interface 생성시 주의사항
//spring data jpa 는 클래스이름에 대한 규칙을 정해놓고 있다.
//구현대상인 인터페이스 상속하는 인터페이스의 이름 + Impl 이라는 이름을 가지고 있어야 한다.

    private final EntityManager em;

    @Override
    public List<Member> findMemberCustom() {
        return em.createQuery("select m from Member m")
                .getResultList();
    }




}
