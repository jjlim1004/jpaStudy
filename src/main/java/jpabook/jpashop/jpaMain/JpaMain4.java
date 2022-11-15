package jpabook.jpashop.jpaMain;

import jpabook.jpashop.class4.domain.Member4;
import jpabook.jpashop.class4.domain.Team4;

import javax.persistence.*;
import java.util.List;

public class JpaMain4 {

    public static void main(String[] args){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {

            Team4 team4 = new Team4();
            team4.setTeamName("TeamA");
            em.persist(team4);

            Member4 member4 = new Member4();
            member4.setName("userName");
            /**
             * 4-5-5.
             * 꼭 참조관계에 있는 클래스를 넣어주자
             * */
            member4.setTeam4(team4);
            /**
             * 4-5-6-1.
             * mappedBy 된 필드에 값을 넣어도 작동하는 쿼리는 없다.
             * 그래도 mappedBy가 된 필드에도 값을 넣어줘야 한다.
             */
            team4.getMembers().add(member4);
            em.persist(member4);
            /**
             * 4-5-6-3.
             * 만약에 em.flush와 clear가 주석처리가 되어있다고 가정해보자
             * 그럼 여태까지 한 코드는 1차캐시에만 들어가 있을것이다.
             * 그럼 Team4 findTeam = em.find(Team4.class, team4.getId());
             * 여기에서는 1차캐시에 있는 값만 조회를 할 것이다.
             * 그럼 컬렉션에는 값이 없는 상황에서 조회를 하는 상황이 발생한다.
             *
             * 1차 캐시에는 존재하되 flush가 되지 않으니 db에는 연관관계가 없는 상태이다.
             * em.flush와 clean이 없으면 select 쿼리가 조회되지 않는다.
             *
             */
            team4.getMembers().add(member4);
            em.flush();
            em.clear();

            /**
             * 4-5-6-2.
             * find로 조회를 한 뒤
             * members 를 실제 사용하는 시점에 select 쿼리를 db에 한번 날린다.
             * 어차피 db에는 fk로 연관관계가 있으므로 날려도 가지고 올 수 있다.
             * */
            Team4 findTeam = em.find(Team4.class, team4.getId());
            List<Member4> members = findTeam.getMembers();
            for (Member4 member: members) {
                System.out.println("findMember = " + member.getId());
            }


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
