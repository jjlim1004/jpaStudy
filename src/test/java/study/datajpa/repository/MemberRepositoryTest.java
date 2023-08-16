package study.datajpa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;
import study.datajpa.entity.Team;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback(false)
public class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    TeamRepository teamRepository;
    @PersistenceContext
    EntityManager em;

    @Test
    public void testMember() {
        Member member = new Member("memberA");
        Member saveMember = memberRepository.save(member);

        //있을수도 있고 없을수도 있어서 Optional 로 받아온다.
        //Optional<Member> findMember = memberRepository.findById(saveMember.getId());
        Member findMember = memberRepository.findById(saveMember.getId()).get();
    }

    @Test
    public void paging() {

        memberRepository.save(new Member("member1", 10));
        memberRepository.save(new Member("member2", 10));
        memberRepository.save(new Member("member3", 10));
        memberRepository.save(new Member("member4", 10));
        memberRepository.save(new Member("member5", 10));

        int age = 10;

        // 0부터 페이지를 시작함
        PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "username"));

        //PageRequest 는 Pageable 을 부모로 갖는다
        //Page<Member> page =memberRepository.findByAge(age,pageRequest);
        //totalcount 는 totalcount 조회하는 쿼리를 저절로 날린다. Page로 반환타입을 설정했기 떄문ㅇ

        Page<Member> page = memberRepository.findByAge(age, pageRequest);

        //entity가 아닌 dto로 반환하고 싶을때
        Page<MemberDto> toMap = page.map(member -> new MemberDto(member.getId(), member.getUsername(), null));


        //데이터를 꺼내올때
        //컨텐츠를 가져오고 싶을때
        List<Member> content = page.getContent();
        //totalcount
        long totalelement = page.getTotalElements();

        for (Member member : content) {
            System.out.println("*****  " + member);
        }
        System.out.println("!!!!! " + totalelement);

        //현재 페이지 번호도 가지고 온다.
        assertThat(page.getNumber()).isEqualTo(0);
        //전체페이지 개수
        assertThat(page.getTotalPages()).isEqualTo(2);
        //첫번째 페이지인가
        assertThat(page.isFirst()).isTrue();
        //다음페이지가 있는가(마지막 페이지인지)
        assertThat(page.hasNext()).isTrue();
    }

    @Test
    public void pagingSlice() {

        memberRepository.save(new Member("member1", 10));
        memberRepository.save(new Member("member2", 10));
        memberRepository.save(new Member("member3", 10));
        memberRepository.save(new Member("member4", 10));
        memberRepository.save(new Member("member5", 10));

        int age = 10;

        // size 가 3이면 4개를 요청함. limit +1 로 요청
        PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "username"));
        Slice<Member> page = memberRepository.findSliceByAge(age, pageRequest);

        //데이터를 꺼내올때
        //컨텐츠를 가져오고 싶을때
        List<Member> content = page.getContent();

        for (Member member : content) {
            System.out.println("*****  " + member);
        }

        //현재 페이지 번호도 가지고 온다.
        assertThat(page.getNumber()).isEqualTo(0);
        //첫번째 페이지인가
        assertThat(page.isFirst()).isTrue();
        //다음페이지가 있는가(마지막 페이지인지)
        assertThat(page.hasNext()).isTrue();
    }

    @Test
    public void pagingList() {

        memberRepository.save(new Member("member1", 10));
        memberRepository.save(new Member("member2", 10));
        memberRepository.save(new Member("member3", 10));
        memberRepository.save(new Member("member4", 10));
        memberRepository.save(new Member("member5", 10));

        int age = 10;

        // size 가 3이면 4개를 요청함. limit +1 로 요청
        PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "username"));
        List<Member> page = memberRepository.findListByAge(age, pageRequest);


    }

    @Test
    public void bulkUpdate() {
        // save,update,select등 하면 jpql 나가기 전에 flush
        // flush를 하고 jpql을 실행한다.
        memberRepository.save(new Member("member1", 10));
        memberRepository.save(new Member("member2", 20));
        memberRepository.save(new Member("member3", 30));
        memberRepository.save(new Member("member4", 40));
        memberRepository.save(new Member("member5", 50));

        //벌크 연산시 save 한 객체들은 영속성 컨텍스트에 있음
        //하지만 반영은 안되었음. db 에 반영이 안된상태 = commit이 안된 상태

        int resultCount = memberRepository.bulkAgePlus(20);
        //근데 이때 벌크연산을 하면 문제가 될 수 있다.
        //벌크연산 후에는 영속성 컨텍스트를 다 날려버려야 한다.

        em.flush();
        em.clear(); //얘만 있어도 된다.
        //ex) 이렇게 조회하면 member5 의 age 는 41이 아닌 40이다.
        List<Member> result = memberRepository.findByUsername("member4");
        Member member5 = result.get(0);
        System.out.println("!!!! " + member5);

        assertThat(resultCount).isEqualTo(4);
    }

    @Test
    public void findMemberLazy() throws Exception {
        //given
        //member1 -> teamA
        //member2 -> teamB
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        teamRepository.save(teamA);
        teamRepository.save(teamB);
        memberRepository.save(new Member("member1", 10, teamA));
        memberRepository.save(new Member("member2", 20, teamB));
        em.flush();
        em.clear();
        //when
        List<Member> members = memberRepository.findAll();
        //then
        for (Member member : members) {
            System.out.println( "!!!!!! " + member.getId());
           System.out.println( "!!!!!! " + member.getTeam().getName());
        }
    }

    @Test
    public void findMemberFetchJoin() throws Exception {
        //given
        //member1 -> teamA
        //member2 -> teamB
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        teamRepository.save(teamA);
        teamRepository.save(teamB);
        memberRepository.save(new Member("member1", 10, teamA));
        memberRepository.save(new Member("member2", 20, teamB));
        em.flush();
        em.clear();
        //when
        List<Member> members = memberRepository.findMemberEntityGraph();
        //then
        for (Member member : members) {
            System.out.println( "*member.getId()* " + member.getId());
            System.out.println( "*member.getTeam().getClass()* " + member.getTeam().getClass());
            System.out.println( "*member.getTeam().getName()* " + member.getTeam().getName());
        }
    }

    @Test
    public void queryHint() throws Exception {
        //given
        memberRepository.save(new Member("member1", 10));
        em.flush();
        em.clear();
        //when
        Member member = memberRepository.findReadOnlyByUsername("member1");
        member.setUsername("member2");
        em.flush(); //Update Query 실행X
    }

    @Test
    public void lock() throws Exception {
        //given
        memberRepository.save(new Member("member1", 10));
        em.flush();
        em.clear();
        //when
        List<Member> member = memberRepository.findLockByUsername("member1");
    }

    @Test
    public void callCustom(){
        List<Member> result = memberRepository.findMemberCustom();
    }


}
