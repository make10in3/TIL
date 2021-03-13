package jpabook.jpashop.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="ORDERS") // 디비에 예약어로 order 가 들어가 있어서
public class Order {
    @Id
    @GeneratedValue
    @Column(name="ORDER_ID")
    private Long id;
    @Column(name="MEMBER_ID")
    private Long memberId;
    private LocalDateTime orderDate;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
}
