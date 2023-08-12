package study.datajpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.List;

//sping 은 이 인터페이스를 보고 유령 클래스를 만들어준다.
//즉, 개발자가 직접 구현체를 만들필요가 없다.
//spring jpa 가 구현체를 만들어준다.
//spring jpa에서 지원하는 인터페이스는 구현체를 만들어준다.
public interface MemberRepository extends JpaRepository<Member,Long> {
    //interface에 이미 정의된 많은 기능이 있음

    List<Member> findByUsername(String username);
    List<Member> findByUsernameAndAgeGreaterThan(String username, int age );

    //by에 조건이 없으면 Member 전체 조회
    List<Member> findHelloBy();
    @Query("select m from Member m where m.username = :username and m.age = :age")
    List<Member> findUser(@Param("username")String username, @Param("age")int age);

    @Query("select m.username from Member m")
    List<String> findUserNameList();

    @Query("select new study.datajpa.dto.MemberDto(m.id, m.username,t.name) from Member m join m.team t")
    List<MemberDto> findMemberDto();

    @Query("select m from Member m where m.username in :names")
    List<Member> findByNames(@Param("names") List<String> names);

    //findByAge_v1
    //pageable 에는 쿼리에 대한 조건이 들어간다.
    //Page<Member> findByAge(int age, Pageable pageable);

    //findByAge_v2
    //select 쿼리와 totalCount 쿼리를 분리해서 작동시킬수 있다.
    @Query(value = "select m from Member m left join m.team t", countQuery = "select count(m) from Member m")
    Page<Member> findByAge(int age, Pageable pageable);

    //pageable 파라미터는 페이징 쿼리를 위한 객체
    //실제 totalcount등 페이징 스타일에 필요한 구현 방식은 리턴타입을 통해 결정이 된다.
    Slice<Member> findSliceByAge(int age, Pageable pageable);
    List<Member> findListByAge(int age, Pageable pageable);

    //update 는 @Modifying 어노테이션을 적어야한다. 그래야 executeUpdate 를 실행한다.
    //얘가 없으면 이상한걸 호출한다.
    //영속성 컨텍스트에 반영이 안되는 내용이 있어 문제가 발생 가능 -> 영속성컨텍스트를 초기화 해주는 옵션
    @Modifying(clearAutomatically = true)
    @Query("update Member m set m.age = m.age +1 where m.age >= :age")
    int bulkAgePlus(@Param("age") int age);

    @Query("select m from Member m inner join fetch m.team")
    List<Member> findMemberFetchJoin();

    //fetch join 을 위해
    //Member와 함께 Team도 같이 조회하고 싶을때
    //attributePaths 의 속성은 엔티티에 있는 필드명 대로
    @Override
    @EntityGraph(attributePaths = {"team"})
    List<Member> findAll();

    //이렇게 부분적으로 적용, 혼용도 가능
    //결과적으로는 fetch join 이 나간다.
    @EntityGraph(attributePaths = {"team"})
    @Query("select m from Member m")
    List<Member> findMemberEntityGraph();

    
    @EntityGraph(attributePaths = {"team"})
        //namedEntityGrapgh 사용시. EntityGraph는 jpa 기술
        //@EntityGraph("Member.all")
    List<Member> findEntityGraphByUsername(@Param("username") String username);

    //쿼리 힌트
    //@QueryHints(value= @QueryHint(name = "org.hibernate.readOnly",value = "true"))
    Member findReadOnlyByUsername(String username);

    //락
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<Member> findLockByUsername(String username);
}
