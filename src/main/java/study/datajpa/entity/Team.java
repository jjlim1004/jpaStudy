package study.datajpa.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
//연관관계필드는 무한루프 가능성이 있음 안하는게 좋음
@ToString(of = {"id","name"})
//public class Team extends JpaBaseEntity{ // 그냥 Jpa 를 사용해서 처리 할때
public class Team extends BaseEntity{ // spring data Jpa 를 사용해서 처리 할때

    @Id @GeneratedValue
    @Column(name="team_id")
    private Long id;
    private String name;

    //fk가 없는쪽에 mappedBy를 거는게 좋다.
    @OneToMany(mappedBy = "team")
    private List<Member> members = new ArrayList();

    public Team(String name){
        this.name = name;
    }

}
