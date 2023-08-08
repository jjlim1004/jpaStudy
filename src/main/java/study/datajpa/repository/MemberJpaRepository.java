package study.datajpa.repository;

import org.springframework.stereotype.Repository;
import study.datajpa.entity.Member;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class MemberJpaRepository {

    @PersistenceContext // 영속성 컨텍스트 주입 - jpa가 제공해주는 어노테이션
    private EntityManager em;

    public Member save(Member member){
        em.persist(member);
        return member;
    }

    public Member find(Long id){
        return em.find(Member.class,id);
    }

    public List<Member> findAll(){
        //jpql
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public Optional<Member> findById(Long id){
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }

    public long count(){
        //count 는 long 타입으로 반환한다.
        return em.createQuery("select count(m) from Member m",Long.class)
                .getSingleResult();
    }

    public List<Member> TeamnameAndAgeGreaterThen(String username, int age){
        return em.createQuery("select m from Member m where m.user_name =:username and m.age >:age")
                .setParameter("username",username)
                .setParameter("age",age)
                .getResultList();
    }

    public void delete (Member member){
        em.remove(member);
    }

    //수정 메소드는 필요가 없다.
    //엔티티 수정을 하면
    //캐시에서 변경감지를 변경하고 commit 할 때 변경하는 쿼리가 날아가기 때문

    //컨텐츠 가져오는 쿼리
    public List<Member> findByPage(int age, int offset, int limit){
       return em.createQuery("select m from Member m where m.age = :age order by m.username desc")
                .setParameter("age",age)
                .setFirstResult(offset) //어디서부터 가져올건가, 몇번째부터 넘길
                .setMaxResults(limit)  //개수를 몇개 가져올 것인가
                .getResultList();
    }

    //토탈 카운트 조회하는 쿼리
    public long totalCount(int age){
        return em.createQuery("select count(m) from Member m where m.age = :age",Long.class)
                .setParameter("age",age)
                .getSingleResult(); //한건은 singleResult
    }

    public int bulkAgePlus(int age){
        //executeupdate 를 하면 return 값으로 성공 결과 개수가 int로 나옴
        return em.createQuery("update Member m set m.age = m.age+1 where m.age >= :age")
                .setParameter("age", age)
                .executeUpdate(); //update 시 executeUpdate 를 해줘야한다.
    }
}
