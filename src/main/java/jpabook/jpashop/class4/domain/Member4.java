package jpabook.jpashop.class4.domain;

import javax.persistence.*;

@Entity
@Table(name = "userMember")
public class Member4 {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    @Column(name = "user_name")
    private String name;

    /**
     * 4-3. fk를 제어하게 되는 참조관계
     *
     * @Column(name = "team_id")
     * private Long teamId;
     *
     * */

    /**
     * 4-4-1
     * 다대일 관계일때 현클래스To참조클래스
     * To 앞에것이 현재 어노테이션을 쓰는 클래스의 관계(N or 1) 이라 생각하면 된다.
     */
    @ManyToOne
    /** 4-4-2
     * Team4 클래스에서도 연관관계에서 필요한건 join시 필요한 칼럼이다.
     * joincolumn 을 지정해준다.
     * */
    @JoinColumn(name = "team_id")
    private Team4 team4;

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

    public Team4 getTeam4() {
        return team4;
    }

    public void setTeam4(Team4 team4) {
        this.team4 = team4;
        /**
         * 4-5-7.
         * 메소드를 원자적으로 쓸 수 있다.
         * */
        team4.getMembers().add(this);
    }
}
