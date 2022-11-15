package jpabook.jpashop.jpaMain;



import jpabook.jpashop.class6.Movie;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;


public class JpaMain6 {
    public static void main(String args[] ){

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try{
            Movie movie = new Movie();
            /**
             * 6-1-1-2
             * name과 price 는 Item 테이블에
             * actor와 director는 movie 테이블에 들어간다.
             * movie 테이블의 id 는 item 테이블의 id와 동일하다.
             * */
            movie.setDirector("Wachowskis");
            movie.setActor("Keanu Reeves");
            movie.setName("matrix");
            movie.setPrice(10000);

            em.persist(movie);
            em.flush();
            em.clear();
            /**
             * 6-1-1-3
             * 조회를 해올때는 테이블간 조인을 해서 데이터를 조회하게 된다.
             * 아래의 코드도 Item 테이블과 movie 테이블간 조인을 통해 조회가 된다.
             * */

            /**
             * 6-1-3-2
             * Item item = em.find(Item.class , movie.getId());
             * 객체지향적으로 생각하면 Item으로도 Movie 를 조회할 수 있어야한다.
             * 각각테이블 전략은 조회를 할때 모든테이블을 조회하고
             * 그 조회 결과를 union all 로 찾아온다.
             * 즉 성능에 영향이 있을 수 있다.
             * */
            Movie findMovie = em.find(Movie.class, movie.getId());

            tx.commit();
        }catch(Exception e ){
            tx.rollback();
        }finally{
            em.close();
        }
        emf.close();
    }
}
