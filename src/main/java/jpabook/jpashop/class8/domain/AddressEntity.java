package jpabook.jpashop.class8.domain;

import javax.persistence.*;

/**
 * 8-2-4-1
 * 차라리 entity를 하나 만드는게 낫다.
 * */
@Entity
@Table(name = "ADDRESS")
public class AddressEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Address address;

    public AddressEntity() {
    }

    public AddressEntity(String city,String address,String zipCode) {
        this.address = new Address(city,address,zipCode);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
