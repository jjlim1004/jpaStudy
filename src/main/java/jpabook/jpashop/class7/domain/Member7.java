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

    /**
     * 7-2-1-1
     * LAZY로 설정하면 Team7 객체는 프록시로 가지고 온다.
     * */
    @ManyToOne(fetch = FetchType.LAZY)
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

    public void setTeam7(Team7 team7) {
        this.team7 = team7;
        team7.getMembers().add(this);
    }
}
