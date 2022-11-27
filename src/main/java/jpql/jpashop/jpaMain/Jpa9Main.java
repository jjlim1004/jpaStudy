package jpql.jpashop.jpaMain;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;


public class Jpa9Main {

    public static void main(String args[]){



        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            /**
             * 9-0-1
             * 이때의 Member는 테이블이 아니다. 엔티티이다.
             * *대신에 m과 같은 alias로 해야한다.
             * m이라는 객체를 조회해 오라는 뜻이다.

            List<Member> result = em.createQuery(
                    "select m from Member m where m.username like '%kim%' "
                    ,Member.class
            ).getResultList();


            String jpql = "select m from Member m where m.username like '%kim%' ";
            List<Member> result = em.createQuery(
                    jpql, Member.class
            ).getResultList();

            for(Member m : result){
                System.out.println(m.getUsername());
            }
             * */


            /**
             * 9-0-2
             * CriteriaBuilder 는 자바 표준에서 제공하는 기능이다.
             * sql같은 경우에는 한눈에 알아보기 쉽지만
             * 자바코드로 작성하면 한눈에 알아보기는 어렵다.
             * 하지만 메소드를 잘못쓰는 경우에는 컴파일 오류가 나서 오타로 인한 위험성이 적다.
             * 그리고 동적 쿼리를 사용할때도 좋다.
             * 하지만 sql형식이 아니라서 알아보기 힘들다는게 문제 ,디버깅이 힘들다.
             * 그래서 유지보수가 어렵다.
             * (망한 스펙....?)
             *
             * 밑의 코드는 username이 username이 아닐때 where 절을 실행시키는 쿼리이다.

            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Member> query = cb.createQuery(Member.class);

            Root<Member> m = query.from(Member.class);

            CriteriaQuery<Member> cq = query.select(m).where(cb.equal(m.get("username"),"kim"));

            String username = "samplename";
            if(username != username){
                cq.where(cb.equal(m.get("username"),"kim"));
            }

            List<Member> resultList = em.createQuery(cq).getResultList();
             * */

            em.createNativeQuery("SELECT MEMBER_ID , CITY , STREET , ZIPCODE , USERNAME FROM MEMBER").getResultList();

            //dbconnection.executeQuery(select * from member)
            //쿼리가 flush가 된게 아니다.
            //결과는 0

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

