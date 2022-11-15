package jpabook.jpashop.class5ex;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Category parent;

    //자기 자신에다가도 oneToMany 를 걸 수 있다.
    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList();

    //inverseJoinColumns 는 중간 테이블을 만들기 위한 pk 정보 값 기입 어노테이션
    @ManyToMany
    @JoinTable(name = "category_item"
            ,joinColumns = @JoinColumn(name = "category_id")
            ,inverseJoinColumns = @JoinColumn(name = "item_id")
    )
    private List<Item> items = new ArrayList<>();
}
