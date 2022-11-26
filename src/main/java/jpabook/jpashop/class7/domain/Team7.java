package jpabook.jpashop.class7.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "team")
public class Team7 {

    @Id
    @GeneratedValue
    @Column(name = "team_id")
    private Long id;

    @Column(name = "team_name")
    private String teamName;

    @OneToMany(mappedBy = "team7")

    private List<Member7> members = new ArrayList<>();

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

    public List<Member7> getMembers() {
        return members;
    }

    public void setMembers(List<Member7> members) {
        this.members = members;
    }
}
