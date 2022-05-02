package chapter10.example.domain;

import chapter08.example.domain.DeliveryStatus;

import javax.persistence.*;

@Entity(name = "ex_ten_delivery")
public class Delivery {
    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;
}
