package jpabook.jpashop.class8.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
     * 같은 Address 타입이 두개이다.
     * 이렇게 임베디드 타입을 두번 쓰면 오류가 난다.
     * 이때 @AttributeOverride(name=매핑할 필드 이름, column=@Column(name="db에서 쓸 칼럼이름"))
     * 을 해줘야된다.
     @Embedded
     private Address address;
     @Embedded
     private Address workAddress;
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


    /**
     * 8-2-3-1
     *  값 타입 컬렉션이라는 걸 명시하기 위해 @ElementCollection 을 사용한다.
     *  테이블이 만들어지기 때문에 table의 정보도 지정해줘야된다.
     *  테이블 정보중에 fk를 지정해줘야되기 때문에 @JoinColumn 을 사용한다.
     *
     *  @ElementCollection 의 기본 fetch값은 LAZY다.
     *
     * */
    @ElementCollection
    @CollectionTable(
            name = "ADDRESS_HISTORY"
            ,joinColumns = @JoinColumn(name = "member_id")
    )
    @Embedded
    private List<Address> addressHistory;


    /**
     * 8-2-4-2
     *  값 타입보다 훨씬 더 좋다.
     *  쿼리 최적화에도 좋고
     *
     @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
     @JoinColumn(name = "MEMBER_ID")
     private List<AddressEntity> addressHistory =  new ArrayList<>();
     * */


    /**
     * 8-2-3-2
     * 제네릭이 String같이 값이 하나인경우애는 
     * @Column 어노테이션을 허용해준다.
     * */
    @ElementCollection
    @CollectionTable(
            name = "FAVORITE_FOOD"
            ,joinColumns = @JoinColumn(name = "member_id")
    )
    @Column(name = "FOOD_NAME")
    private Set<String> favoriteFoods = new HashSet();

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

    public List<Address> getAddressHistory() {
        return addressHistory;
    }

    public void setAddressHistory(List<Address> addressHistory) {
        this.addressHistory = addressHistory;
    }

    public Set<String> getFavoriteFoods() {
        return favoriteFoods;
    }

    public void setFavoriteFoods(Set<String> favoriteFoods) {
        this.favoriteFoods = favoriteFoods;
    }
}
