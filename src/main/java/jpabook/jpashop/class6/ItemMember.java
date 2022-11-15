package jpabook.jpashop.class6;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class ItemMember extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    /**
     * 6-3-1
     * 모든테이블에 공통적으로 들어가야할 속성들이라고 할 때
     * 계속 객체마다 속성을 작성하기에는 무리가 있다.
     * 그런경우 부모클래스를 만든 뒤 공통속성을 정의하고
     * 부모클래스를 상속하는 것이 낫다.
     *
    private String createBy;
    private LocalDateTime createDate;
    private String updateBy;
    private LocalDateTime updateDate;
     */
}
