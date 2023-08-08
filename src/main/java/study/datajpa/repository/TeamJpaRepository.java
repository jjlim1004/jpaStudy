package study.datajpa.repository;

import org.springframework.stereotype.Repository;
import study.datajpa.entity.Team;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class TeamJpaRepository {

    @PersistenceContext // 영속성 컨텍스트 주입 - jpa가 제공해주는 어노테이션
    private EntityManager em;

    public Team save(Team team){
        em.persist(team);
        return team;
    }

    public Team find(Long id){
        return em.find(Team.class,id);
    }

    public List<Team> findAll(){
        //jpql
        return em.createQuery("select t from Team t", Team.class)
                .getResultList();
    }

    public Optional<Team> findById(Long id){
        Team Team = em.find(Team.class, id);
        return Optional.ofNullable(Team);
    }

    public long count(){
        //count 는 long 타입으로 반환한다.
        return em.createQuery("select count(t) from Team t",Long.class)
                .getSingleResult();
    }

    public void delete (Team team){
        em.remove(team);
    }

}
