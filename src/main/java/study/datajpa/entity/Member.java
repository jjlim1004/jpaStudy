package study.datajpa.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
//연관관계필드는 무한루프 가능성이 있음 안하는게 좋음
@NamedQuery(
        name = "Member.findByUsername",
        query = "select m from Member m where m.username = :username"
)
@NamedEntityGraph(
        name="Member.all",
        attributeNodes = @NamedAttributeNode("team")
)
@ToString(of = {"id","username","age"})
public class Member {

    @Id @GeneratedValue
    @Column(name="member_id")
    private Long id;
    private String username;
    private int age;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    //엔티티는 기본생성자 필수
    //접근은 protected까지, private 는 하면 안됨 - 프록시 객체 만들때 오류가 있음
    //protected Member() {}
    public Member(String username){
        this.username = username;
    }

    public Member(String username, int age, Team team) {
        this.username = username;
        this.age= age;
        if(team != null){
            changeTeam(team);
        }
    }
    public Member(String username, int age) {
        this.username = username;
        this.age = age;
    }

    public void changeTeam(Team team){
        this.team = team;
        team.getMembers().add(this);
    }
}
