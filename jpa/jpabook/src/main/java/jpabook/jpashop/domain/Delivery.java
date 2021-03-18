package jpabook.jpashop.domain;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
public class Delivery {
    @Id @GeneratedValue
    private Long Id;

    @OneToOne(mappedBy = "delivery", fetch = LAZY)
    private Order order;
    private DeliveryStatus status;

    private Address address;

}
