package study.datajpa.entity;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.repository.MemberRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberTest {
    @PersistenceContext
    EntityManager em;

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void testEntity(){
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");

        em.persist(teamA);
        em.persist(teamB);

        Member member1 = new Member("member1",10,teamA);
        Member member2 = new Member("member1",20,teamA);
        Member member3 = new Member("member1",30,teamB);
        Member member4 = new Member("member1",40,teamB);

        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);

        em.flush();
        em.clear();

        //확인
        List<Member> members = em.createQuery("select m from Member m ", Member.class).getResultList();

        for(Member member : members){
            System.out.println(member);
            System.out.println(member.getTeam());
        }
    }

    @Test
    public void JpaEventBaseEntity() throws Exception{
        Member member = new Member("member1");
        memberRepository.save(member);

        Thread.sleep(100);

       // member.setUsername("member2");

        em.flush();
        em.clear();

        Member findMember = memberRepository.findById(member.getId()).get();

        System.out.println("findMember = !!! " + findMember.getCreatedDate());
        //System.out.println("findMember = !!! " + findMember.getUpdatedDate());
        System.out.println("findMember = !!! " + findMember.getLastModifiedDate());
        System.out.println("findMember = !!! " + findMember.getCreatedBy());
        System.out.println("findMember = !!! " + findMember.getLastModifiedBy());

        //@EnableJpaAuditing(modifyOnCreate = false) 로 하면 수정자 수정일 싹다 null 로 들어간다.
//        member.setUsername("member2");
//
//        em.flush();
//        em.clear();
//
//        findMember = memberRepository.findById(member.getId()).get();
//
//        System.out.println("findMember = !!!! " + findMember.getCreatedDate());
//        //System.out.println("findMember = !!! " + findMember.getUpdatedDate());
//        System.out.println("findMember = !!!! " + findMember.getLastModifiedDate());
//        System.out.println("findMember = !!!! " + findMember.getCreatedBy());
//        System.out.println("findMember = !!!! " + findMember.getLastModifiedBy());

    }
}
