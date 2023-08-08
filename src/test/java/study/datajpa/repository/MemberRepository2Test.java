package study.datajpa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
@Rollback(false)
class MemberRepository2Test {

    @Autowired
    MemberRepository2 memberRepository2;
    @Autowired
    TeamRepository teamRepository;
    @PersistenceContext
    EntityManager em;


    @Test
    public void test1(){
        String name = "name";
        memberRepository2.findByUsername(name);

    }
}