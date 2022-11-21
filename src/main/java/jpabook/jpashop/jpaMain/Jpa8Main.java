package jpabook.jpashop.jpaMain;

import jpabook.jpashop.class8.domain.Address;
import jpabook.jpashop.class8.domain.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;


public class Jpa8Main {

    public static void main(String args[]){



        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {

            int a =10;
            int b =a;

            b=20;
            //8-2-1
            System.out.println(a +" != "+b);

            /**
             *  8-2-2-5
             *  임베디드 타입중에 같은 참조를 가지고 있는 경우
            Address address = new Address("city","street","1000");
            Member member1 = new Member();
            member1.setUsername("member1");
            member1.setWorkAddress(address);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("member1");
            member2.setWorkAddress(address);
            em.persist(member2);


            //전의 코드를 모르는 사람이 짰을때
            //member1의 정보를 바꿨는데 member2의 정보도 바뀌어진다.
            //이런 경우는 address를 하나의 entity로 만드는게 낫다.
            member1.getWorkAddress().setCity("newCity");
             * */

            /**
             * 8-2-2-6
             * 올바른 임베디드 타입 공통 참조
             * */
            Address address1 = new Address("city","street","1000");
            Member member1 = new Member();
            member1.setUsername("member1");
            member1.setWorkAddress(address1);
            em.persist(member1);

            //값을 복사해야된다. 그래야 member1 과 member2 의 값이 따로 변한다.
            //그래도 참조 값을 직접 대입하는 것을 완전히 막는건 어렵다.
            //ex) Address address3 = address1;
            Address address = new Address("city","street","1000");
            Address address2 = new Address(address1.getCity(),address1.getStreet(),address1.getZipcode());
            Member member2 = new Member();
            member2.setUsername("member1");
            member2.setWorkAddress(address2);
            em.persist(member2);


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
