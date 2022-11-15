package jpabook.jpashop.class5.domain;

import com.sun.javafx.geom.transform.Identity;

import javax.persistence.*;

@Entity
public class Locker {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;

    private String name;

    /**
     * 5-2. 주 테이블에 외래키가 있는 경우 단방향
     * */
    @OneToOne(mappedBy = "locker")
    private Member5 member5;

}
