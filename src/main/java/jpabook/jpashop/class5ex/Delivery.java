package jpabook.jpashop.class5ex;

import javax.persistence.*;

@Entity
@Table(name = "delivery")
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String city;

    private String street;

    private String zipcode;

    @OneToOne(mappedBy = "delivery")
    private Order order;

}
