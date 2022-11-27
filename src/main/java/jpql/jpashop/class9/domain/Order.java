package jpql.jpashop.class9.domain;

import jpql.jpashop.class4.domain.OrderStatus;

import javax.persistence.*;

@Entity
//order 가 예약어일수도 있어서 ORDERS로
@Table(name="ORDERS")
public class Order {
    @Id
    @GeneratedValue
    @Column(name="ORDER_ID")
    private long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}
