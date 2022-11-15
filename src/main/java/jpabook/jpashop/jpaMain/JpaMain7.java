package jpabook.jpashop.jpaMain;

import jpabook.jpashop.class6.Movie;
import jpabook.jpashop.class7.domain.Member7;
import org.hibernate.Hibernate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain7 {
    public static void main(String args[] ){

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try{
            Member7 member = new Member7();
            member.setName("hello");

            em.persist(member);
            em.flush();
            em.clear();

            /**
             * 7-1-1
             * em.flush와 clear를 함으로써 em에 더이상 뭐가 담겨 있지 않은 상태이다.
             * em.getReference 하면 flush를 하지 않았는데도 select 쿼리가 전송이 된다.
             * getId를 하는 부분은 getReference의 두번째 파라미터라서 굳이 db에서 조회를 하지 않는다.
             * 그런데 name은 db에 있는데도 가져온다.
             * getName은 getName에 값이 없기 때문에 db에 일단 조회한다.
             * */
            Member7 findMember = em.getReference(Member7.class, member.getId());
            System.out.println("findeMember Id = " + findMember.getId());
            System.out.println("findeMember name = " + findMember.getName());

            /**
             * 7-1-2
             * hibernate 가 만든 가짜 클래스다. 그게 프록시이다.
             * em.getReference를 쓰면 프록시라는 가짜 엔티티 객체를 준다.
             * 프록시는 실제 엔티티를 상속해서 만들어진다.
             * 때문에 실제 클래스와 겉 모양은 같다.
             *
             * 프록시 객체는 실제 객체의 참조를 가지고 있따.
             * 프록시 객체를 호출하면 프록시에서 실제 객체의 메소드를 호출하게 된다.
             * */
            System.out.println("findeMember Id = " + findMember.getClass());

            em.flush();
            em.clear();

            /**
             * 7-1-3-3
             * 아래 코드로 system.out.println 을 찍어보면 true가 나온다.
             Member7 member1 = em.find(Member7.class, member.getId());
             Member7 member2 = em.find(Member7.class, member.getId());
             System.out.println("member1 == member2 " + (member1.getClass() == member2.getClass()));
             * 하지만 다음과 같을때는 false가 나온다.
             * 비교를 하려면 == 이 아니라 instance of 를 사용해야 한다.
             Member7 member1 = em.find(Member7.class, member.getId());
             Member7 member2 = em.getReference(Member7.class, member.getId());
             System.out.println("member1 == member2 " + (member1.getClass() == member2.getClass()));
             System.out.println("member1 == member2 " + (member1 instance of Member7);
             * */

            /**
             * 7-1-3-4
             * 프록시와 엔티티는 == 비교시 같아야하며, 1차캐시에 엔티티가 있을경우
             * getRefernece 를 하여도 entity를 가지고 온다.
             Member7 member3 = em.getReference(Member7.class, member.getId());
             Member7 reference = em.getReference(Member7.class, member.getId());
             System.out.println("proxy == entity  " + (member3 == reference));
             * */

            Member7 referenceMember = em.getReference(Member7.class, member.getId());
            System.out.println("referenceMember  " + referenceMember.getClass());
            Member7 finMember = em.find(Member7.class, member.getId());
            System.out.println("finMember  " + finMember.getClass());

            /**
             *7-1-3-4
             * 이때도 true가 나온다.
             * 한번 위에서 getReference로 프록시를 가지고 오면
             * 그 뒤에 em.find를 한다해도 프록시를 가지고 온다.
             * 프록시 == 엔티티 는 true 라는 jpa의 방침때문
             * */
            System.out.println("proxy == entity  " + (referenceMember == finMember));

            /**
             * 영속성 컨텍스트에세 분리하거나 영속성컨텍스트를 비운뒤에 프록시를 호출하면 오류가 난다.
             * 프록시는 영속성 컨텍스트의 도움을 받아 초기화를 하기 때문
             * 영속성 컨텍스트의 관리를 벗어나는 순간 사용하지 못한다.
            Member7 referenceMember2 = em.getReference(Member7.class, member.getId());
            System.out.println("referenceMember2  " + referenceMember.getClass());
            em.detach(referenceMember2);
            //혹은 em.close();
            //혹은 em.clear();
            referenceMember2.getName();
             * */

            /**
             * 초기화 여부는 다음 메소드로 알 수 있다.
             * emf.getPersistenceUnitUtil().isLoaded(referenceMember3)
             * 만약 초기화가 되지 않으면 false, 초기화가 되면 true
             * */
            Member7 referenceMember3 = em.getReference(Member7.class, member.getId());
            System.out.println("isLoaded  " + emf.getPersistenceUnitUtil().isLoaded(referenceMember3)); //false
            referenceMember3.getName();
            System.out.println("isLoaded  " + emf.getPersistenceUnitUtil().isLoaded(referenceMember3)); //true

            /**
             * 프록시의 강제적 초기화
             * 강제적으로 초기화를 할 때 쿼리가 나간다.
             * jpa 표준은 강제초기화가 없다.
             * 때문에 각 프록시의 getName과 같은 메소드를 통해 강제적으로 호출을 해야한다.
             */
            Hibernate.initialize(referenceMember3);



            tx.commit();
        }catch(Exception e ){
            tx.rollback();
            e.printStackTrace();
        }finally{
            em.close();
        }
        emf.close();
    }
}
