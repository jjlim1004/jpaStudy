package jpabook.jpashop.jpaMain;


import jpabook.jpashop.class5.domain.Member5;
import jpabook.jpashop.class5.domain.Team5;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain5 {

    public static void main(String[] args){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {

            Member5 member5 = new Member5();
            member5.setName("member1");
            em.persist(member5);

            Team5 team5  = new Team5();
            team5.setTeamName("teamA");
            /**
             * 1-2.
             *  fk는 Mmember 테이블에 있다.
             *  fk의 update 는 MEMBER 테이블에서 이루어져야한다.
             *  create one-to-many row jpabook.jpashop.class5.domain.Team5.members
             *  라는 메세지와 함께 update 가 Member테이블에서 이루어진다.
             *  이런 1:N 단방향 매핑에서는 insert할때 update 쿼리가 한번 더 나간다.
             *  update쿼리로 인한 성능저하는 미비하나
             *  여러 테이블이 엮여있는 경우에는 쿼리 추적이 힘들어진다.
             * */
            team5.getMembers().add(member5);
            em.persist(team5);

            tx.commit();
        }catch (Exception e){
            e.printStackTrace();
            tx.rollback();
        }finally {
                em.close();
        }
        emf.close();
    }
}
