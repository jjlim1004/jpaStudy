package jpabook.jpashop.class8.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * 8-2-2-3
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

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }
}
