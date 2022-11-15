package jpabook.jpashop.class5.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "team")
public class Team5 {

    @Id
    @GeneratedValue
    @Column(name = "team_id")
    private Long id;

    @Column(name = "team_name")
    private String teamName;

    @OneToMany
    @JoinColumn(name = "MEMBER_ID")
    /**
     * 1-1.
     *  4강과는 다르게 mappedBy가 쓰이지 않았다.
     **/
    private List<Member5> members = new ArrayList<>();

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

    public List<Member5> getMembers() {
        return members;
    }

    public void setMembers(List<Member5> members) {
        this.members = members;
    }
}
