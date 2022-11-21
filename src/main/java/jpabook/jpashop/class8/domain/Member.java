package jpabook.jpashop.class8.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Member {

    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name")
    private String username;

    /**
     * 8-2-2-1
     * 맨 처음의 Member 클래스의 필드 상태

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    private String city;
    private String zipcode;
    private String street;
     * */

    /**
     * 8-2-2-2
     * 바뀐 후의 상태
     * @Embedded 라는 어노테이션을 써서 임베디드 타입이라는걸 알려준다.
     * */
    @Embedded
    private Period period;

    /**
     * 8-2-2-4
     * 이렇게 임베디드 타입을 두번 쓰면 오류가 난다.
     * 이때 @AttributeOverride(name=매핑할 필드 이름, column=@Column(name="db에서 쓸 칼럼이름"))
     * 을 해줘야된다.
     @Embedded
     private Adress adress;
     @Embedded
     private Adress workAdress;
     */
    @Embedded
    private Address address;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "city", column = @Column(name = "work_city")),
            @AttributeOverride(name = "street", column = @Column(name = "work_street")),
            @AttributeOverride(name = "zipcode", column = @Column(name = "zip_code"))
    })
    private Address workAddress;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Address getWorkAddress() {
        return workAddress;
    }

    public void setWorkAddress(Address workAddress) {
        this.workAddress = workAddress;
    }
}
