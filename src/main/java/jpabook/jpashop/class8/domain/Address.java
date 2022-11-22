package jpabook.jpashop.class8.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * 8-2-2-3
 * 임베디드 타입으로 쓰인다는 @Embeddable 어노테이션 설정
 * */
@Embeddable
public class Address {
    private String city;
    @Column(name = "zip_code")
    private String zipcode;
    private String street;

    public Address() {
    }

    public Address(String city, String zipcode, String street) {
        this.city = city;
        this.zipcode = zipcode;
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    private void setCity(String city) {
        this.city = city;
    }

    public String getZipcode() {
        return zipcode;
    }

    private void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getStreet() {
        return street;
    }

    private void setStreet(String street) {
        this.street = street;
    }
}
