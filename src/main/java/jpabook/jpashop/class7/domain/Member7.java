package jpabook.jpashop.class7.domain;

import javax.persistence.*;

@Entity
@Table(name = "userMember")
public class Member7 {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    @Column(name = "user_name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team7 team7;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Team7 getTeam4() {
        return team7;
    }

    public void setTeam4(Team7 team7) {
        this.team7 = team7;
        team7.getMembers().add(this);
    }
}
