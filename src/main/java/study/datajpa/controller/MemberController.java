package study.datajpa.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;
import study.datajpa.repository.MemberRepository;

import javax.annotation.PostConstruct;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;

    @GetMapping("/members/{id}")
    public String findMember(@PathVariable("id")Long id){
        Member member = memberRepository.findById(id).get();
        return member.getUsername();
    }

    //도메인 클래스 컨버터
    //파라미터로 Member 로 정의해도 된다.
    //member 대로 매핑한 후에 컨트롤러에 넘겨준다.
    @GetMapping("/members2/{id}")
    public String findMember2(@PathVariable("id")Member member){
        return member.getUsername();
    }


    //web에서 페이징을 사용할때 어떻게 지원을 해주는가?
    //파라미터로 Pageable 이 정의되어있으면
    //spring boot가 PageRequest 객체를 생성해서 injection을 해준다.
    @GetMapping("/members")
    public Page<MemberDto> list(@PageableDefault(size= 5 , sort ="username")Pageable pageable){
        Page<Member> page = memberRepository.findAll(pageable);
        Page<MemberDto> map = page.map(MemberDto::new);
        return map;
    }

    @PostConstruct
    public void init(){
       // memberRepository.save(new Member("userA"));
        for (int i = 0; i < 100; i++) {
            memberRepository.save(new Member("user"+i,i));;
        }
    }


}
