package chapter05.example.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "ex_order_I")
public class Order {
    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "order_date")
    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;
}
