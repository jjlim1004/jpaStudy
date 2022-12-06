package jpql.jpashop.jpaMain;


import jpql.jpashop.class9.domain.*;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;


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
             * m이라는 객체를 조회해 오라는 뜻이다.*

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
             */


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


            /**
             * 9-0-3
             * nativeQuery*
            String query ="SELECT MEMBER_ID , USERNAME, AGE FROM MEMBER";
            List<Member> result = em.createNativeQuery(query).getResultList();
             */


            /**
             * 9-0-4* */
             Member member = new Member();
                          member.setUsername("member1");
                          member.setAge(10);
                          em.persist(member);

             //flush 가 되는 시점은 commit할 때와 jpql의 query가 날아갈때
             //dbconnection.executeQuery(select * from member)
             //쿼리가 flush가 된게 아니다.
             //결과는 0


            /**
             * 9-1-1 ~ 9-2
             *
            Member member = new Member();
            member.setUsername("member1");
            member.setAge(10);
            em.persist(member);
             * */

            /**
             * 9-1-1
             * 기본적으로 createQuery 두번째 파라미터는 엔티티의 클래스이다.
             * 엔티티가 아닌 클래스도 들어갈 수는 있다.(프로젝션)
             * TypedQuery는 타입정보를 명기할 수 있을때 사용한다.

            TypedQuery<Member> typequery = em.createQuery("select m from Member m",Member.class);
            TypedQuery<String> typequery1 = em.createQuery("select m.username from Member m",String.class);
             * */

            /**
             * 9-1-4-1
             * 파라미터 바인딩
             * 이름에 따른 바인딩이 있고
             * 위치에 따른 바인딩도 있다.
             * 메소드 체이닝도 가능하다.
             *
             * 아래는 이름기반이다.
            TypedQuery<Member> typequery2 = em.createQuery("select m from Member m where m.username =:username",Member.class);
            typequery2.setParameter("username","member1");
            Member typeQuery2Result = typequery2.getSingleResult();
             * */
            /** 위치기반의 파라미터 바인딩
            TypedQuery<Member> typequery2 = em.createQuery("select m from Member m where m.username =?1",Member.class)
                                                .setParameter(1,"member1")
                                                .getSingleResult();
             */


            /**
             * 9-1-2
             * Query는 타입정보를 명시할수 없을때 사용한다.
             * 예를 들어 아래의 createQuery에서
             * m.username은 String 타입으로 리턴할거고
             * m.age int 타입으로 리턴할거다.
             * 그럼 어떤 타입으로 리턴해야할지 결정하기 힘들다.

            Query query = em.createQuery("select m.username, m.age from Member m");
             * */


            /**
             *9-1-3
             * 조회 api
            List<Member> resultList = typequery.getResultList();
            //Member singleResult = typequery.getSingleResult();

            for(Member m : resultList){
                System.out.println("member1 = "  + m);
            }
             * */


            /**
             * 9-2-1
             * 프로젝션의 대상들은 다 영속성 컨텍스트에 관리가 된다.
            List<Member> result = em.createQuery("select m from Member m",Member.class)
                                    .getResultList();
            Member findMember = result.get(0);
            //update  쿼리가 나간다.
            findMember.setAge(10);
             * */


            /**
             * 9-2-2
             * sql에는 join이 없어도
             * 이때는 join을 한 쿼리가 나간다.
             * 묵시적 조인이라 한다.
            List<Team> result = em.createQuery("select m.team from Member m", Team.class)
                    .getResultList();
             * */

            /**
             * 9-2-3
             * 그래도 아래와 같은 형식으로 join을 걸어서 사용하는게 좋다.
             * 그래야 join을 했다는 것을 인식할 수 있다.
             * 그냥 m.team을 하면 예측을 하기 힘들다.
             * 명시적 조인이라 한다.
            List<Team> result = em.createQuery("select t from Member m join m.team t", Team.class)
             * */

            /**
             * 9-2-4
             * 임베디드 타입의 프로젝션이 경우이다.
             * 임베디드 타입은 어디에 소속된 타입이기 때문에
             * select a.address from Address a 의 형태로는 쓰지 못한다.
             * 속해 있는 entity 에서 조회를 해와야한다.

            em.createQuery("select o.address from Order o", Address.class)
                    .getResultList();
             **/


            /**
             * 9-2-5
             * 스칼라 타입의 쿼리이다. 타입 파라미터가 제외된다.
             * 반환형일때 query형은 Obejct[] List로 담겨져 온다.
            List resultList = em.createQuery("select m.username, m.age from Member m")
                    .getResultList();
            Object object = resultList.get(0);
            Object[] result = (Object[]) object;
            System.out.println("result[0] = " + result[0]);
            System.out.println("result[1] = " + result[1]);
             * */

            /**
             * 9-2-6
             * 타입을 제네릭에 Object[] 로 지정하는 방법도 있다.

            List<Object[]> resultList = em.createQuery("select m.username, m.age from Member m")
                    .getResultList();
            Object object = resultList.get(0);
            Object[] result = (Object[]) object;
            System.out.println("result[0] = " + result[0]);
            System.out.println("result[1] = " + result[1]);
             * */

            /**
             * 9-2-7
             * new 형으로 조회
             * DTO를 하나 만든다.
             * 그리고 조회를 할때 new 키워드로 인스턴스를 만드는 것처럼
             * 프로젝션을 new 키워드로 만든다.
             * entity 가 아니면 new 키워드를 통해서 만들어야한다.
             * 때문에 그에 따른 생성자를 만들어야한다.
             * 그리고 당연히 생성자를 사용할때 순서와 타입이 일치해야한다.
             * 패키지 명이 길어지면 적기가 힘들다.
             * 그래도 이런점은 querydsl에서 간단하게도 쓸 수 있다고 한다.

            List<MemberDTO> result = em.createQuery("select new jpql.jpashop.class9.domain.MemberDTO( m.username, m.age) from Member m", MemberDTO.class)
                    .getResultList();

            MemberDTO memberDTO = result.get(0);
            System.out.println("memberDTO.username = " + memberDTO.getUsername());
            System.out.println("memberDTO.age = " + memberDTO.getAge());
             * */

            /**
             * 9-3-1
               페이징을 위한 코드

            for (int i = 0; i < 100; i++) {
                Member member = new Member();
                member.setUsername("member"+i);
                member.setAge(i);
                em.persist(member);
            }
            List<Member> result = em.createQuery("select m from Member m order by m.age desc")
                    .setFirstResult(0)
                    .setMaxResults(10)
                    .getResultList();

            System.out.println("result.size = " + result.size());
            for (Member member1 : result){
                System.out.println("member1 = " + member1);
            }
             * */

            /**
             * 9-4-1 
             * 조인

            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("member");
            member.setAge(10);
            member.setTeam(team);
            em.persist(member);
             * */
            //Team과 Member간 연관관계가 없다고 가정할 때
            //String query = "select m from Member m, Team t where m.username= t.name";
            String query = "select m from Member m left outer join Useless u on m.username = u.naame";
            List<Member> result = em.createQuery(query, Member.class)
                    .getResultList();


            /**
             * 9-5-1
             * 서브쿼리

            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("member");
            member.setAge(10);
            member.setTeam(team);
            em.persist(member);

            //String query = "select (select avg(m1.age) From Member m1) as average from Member m join Team t on m.username = t.name";
            String query = "select (select avg(m1.age) From Member m1) as average from Member m join Team t on m.username = t.name";
            List<Double> result = em.createQuery(query, Double.class)
                    .getResultList();
             * */

            /**
             * 9-6-1
             *  타입

            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("member");
            member.setAge(10);
            member.setType(MemberType.ADMIN);
            member.setTeam(team);
            em.persist(member);

            //문자, boolean, Enum 타입
            //String query = "select m.username,'HELLO',true from Member m where m.type=:userType";
            //Entity 타입 표현
            String query = "select i from Item i where type(i) = Book";
            List<Item> result = em.createQuery(query,Item.class)
                    .getResultList();
             * */

            /**
             * 9-7-1
             * 분기문

            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            Member member = new Member();
            //member.setUsername("member");
            member.setAge(11);
            member.setTeam(team);
            em.persist(member);

            //case문
//            String query = "select " +
//                    "case when m.age <=10 then '10이하' "+
//                    "when m.age >=12 then '12이상' "+
//                    "else '11' end "+
//                    "from Member m";
            //coalesce 문
//          String query = "select coalesce(m.username, '유령회원') from Member m ";
            //null if문
            String query = "select nullif(m.username, '관리자') from Member m ";
            List<String> result = em.createQuery(query,String.class)
                    .getResultList();
            for (String s : result){
                System.out.println("result = " + s);
            }
             * */


            /**
             * 9-8-1
             * 기본함수

            //concat 예시
//            String query = "select 'a'||'b' From Member m ";
//            String query = "select concat('a','b') From Member m ";
            //size예시
//            String query = "select size(t.member) From Team t ";
            //index 대충 예시 이런 느낌이다~~
//            @OrderColumn
//            String query = "select size(t.member) From Team t ";


            Member member1 = new Member();
            member1.setUsername("member1");
            member1.setAge(10);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("member2");
            member2.setAge(11);
            em.persist(member2);

            //사용자 정의 함수 예시
            //그래도 group_concat은 h2 기본 함수이다.
            //String query = "select function('group_concat',m.username) From Member m ";
            String query = "select group_concat(m.username) From Member m ";
            List<String> result = em.createQuery(query,String.class)
                    .getResultList();
            for (String s : result){
                System.out.println("result = " + s);
            }
             * */

            /**
             * 10-1
             * 다음과 같은 코드도 가능하다.
             String query = "select t.members from Team t";
             List<Collection> result = em.createQuery(query,Collection.class);
             * */

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

