package jpabook.jpashop.jpaMain;

import jpabook.jpashop.class8.domain.Address;
import jpabook.jpashop.class8.domain.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Set;


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


            /**
             * 3-2-3-3
             * 컬렉션 타입의 생성, 생성을 하면 컬렉션 타입의 테이블이 생성이된다.
             * 컬렉션 타입의 생명주기는
             * member에 의존한다.
             * cascade + 고아객체 제거기능을 필수로 가지고 있다.
             * */
            Member member3 = new Member();
            member3.setUsername("member3");
            member3.setAddress(new Address("city1","street1","11111"));
            
            member3.getFavoriteFoods().add("치킨");
            member3.getFavoriteFoods().add("피자");
            member3.getFavoriteFoods().add("햄버거");

            member3.getAddressHistory().add(new Address("city2","street2","2222"));
            member3.getAddressHistory().add(new Address("city3","street3","3333"));

            em.persist(member3);

            em.flush();
            em.clear();

            /**
             * 8-2-3-4
             * 컬렉션 타입을 가지고 있는 객체의 조회
             * 기본적으로 지연로딩을 사용한다.
             * 때문에 Member객체의 정보만 가지고 온다.
             * Member객체의 필드 중에서도 컬렉션 타입이 아닌 정보만 가지고 온다.
             * 임베디드 타입의 정보도 가지고 온다.
             *
             * */
            Member findMember = em.find(Member.class, member3.getId());
            List<Address> addressHistory = findMember.getAddressHistory();
            for(Address history: addressHistory){
                /**
                 * 8-2-3-5
                 * 지연로딩을 사용하기 때문에 getCity 같이 사용을 할 때
                 * 테이블에서 조회를 한다.
                 * */
                System.out.println(history.getCity());
            }

            Set<String> foods = findMember.getFavoriteFoods();
            for (String food :foods){
                System.out.println(food);
            }

            /**
             * 8-2-3-6
             * 일반적인 임베디드 타입 수정
             * 새롭게 임베디드 타입 객체를 만들어서 수정한다.
             * */
            Address add = findMember.getAddress();
            findMember.setAddress(new Address("new",add.getStreet(),add.getZipcode()));

            /**
             * 8-2-3-7
             * 값타입 컬렉션타입 의 수정
             * remove로 지운다음
             * 새로 넣어야 한다.
             *
             * 쿼리 역시 delete쿼리와 insert쿼리가 나간다.
             * */
            //치킨 -> 떡볶이
            findMember.getFavoriteFoods().remove("치킨");
            findMember.getFavoriteFoods().add("떡볶이");

            /**
             * 8-2-3-8
             * 임베디드 타입의 컬렉션타입 수정
             * 8-2-3-7 처럼 remove를 하되
             * remove의 파라미터로 객체를 넣어야 한다.
             * 기본적으로 컬렉션들은 대부분 equals나 hashcode를 통해 대상을 찾는다.
             * 때문에 equals나 hashcode를 제대로 구현되어 있어야한다.
             * 그렇지 않으면 지워지지 않을수도 있다.
             *
             * delete from ADDRESS where member_id = ?
             * 형태의 쿼리가 나간다.
             * 그리고 다시 지운데이터와 수정한 데이터를 insert 를 한다.
             * 즉, 테이블에 있는 address 자체를 갈아 끼운것
             *
             * */
            findMember.getAddressHistory().remove(new Address("city1","street1","11111"));
            findMember.getAddressHistory().add(new Address("new2","new2","new2"));

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

