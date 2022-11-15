package jpabook.jpashop.jpaMain;


import jpabook.jpashop.class6Ex.Book;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Jpa6ExMain {
    public static void main(String args[] ){

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try{
            Book book = new Book();
            book.setName("JPA");
            book.setAuthor("저자");
            em.persist(book);


            tx.commit();
        }catch(Exception e ){
            tx.rollback();
        }finally{
            em.close();
        }
        emf.close();
    }
}
