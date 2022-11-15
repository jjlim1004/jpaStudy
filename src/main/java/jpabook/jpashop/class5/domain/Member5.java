package jpabook.jpashop.class5.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "userMember")
public class Member5 {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    @Column(name = "user_name")
    private String name;

    /**
     * 5-2.
     *  4강과는 다르게 mappedBy가 쓰이지 않았다.
     *  대신에 joinColumn을 하나 더 쓴다.
     *  머리가 아프다.
     *  N:1 양방향 매핑은 insertable =false, updateable = false 를 하면된다.
     *  읽기 전용으로 강제적으로 만드는것
     *
     **/
    @ManyToOne
    @JoinColumn(name = "TEAM_ID", insertable = false, updatable = false)
    private Team5 team5;

    /**
     * 5-2. 주 테이블에 외래키가 있는 경우 단방향
     * */
    @OneToOne
    @JoinColumn(name = "LOCKER_ID")
    private Locker locker;

    /**
     * 5-4. n:n을 위해 @ManyToMany 를 쓰고
     * @JoinTable 을 쓴다.
     * name을 정하지 않으면 이상한 이름으로 중간테이블이 생성된다.
     * 연결테이블은 각fk들이 pk가 되는 형태로 생성이 된다.
     *
     * */
    @ManyToMany
    @JoinTable(name = "MEMBER_PRODUCT")
    private List<Product> products = new ArrayList<>();

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

}
