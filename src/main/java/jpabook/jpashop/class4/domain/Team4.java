package jpabook.jpashop.class4.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "team")
public class Team4 {

    @Id
    @GeneratedValue
    @Column(name = "team_id")
    private Long id;

    @Column(name = "team_name")
    private String teamName;

    /**
     * 4-5-1.
     * team과 userMember는
     * 1:N관계
     * oneToMany 어노테이션을 사용한다.
     * 이때 mappedBy 라는 속성이 있다.
     */

    /**
     * 4-5-2
     * mappeedBy는 Member4클래스의 필드중 team이라고 되어있는 필드를 가리킨다.
     * */
    @OneToMany(mappedBy = "team4")
    //초기화 해주는게 관례, 그래야지 add 할때 null poiontException이 안난다.
    private List<Member4> members = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public List<Member4> getMembers() {
        return members;
    }

    public void setMembers(List<Member4> members) {
        this.members = members;
    }
}
