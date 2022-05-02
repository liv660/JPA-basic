package chapter10.example.domain;

import chapter08.example.domain.Item;

import javax.persistence.*;

@Entity(name = "ex_ten_orderitem")
public class OrderItem {
    @Id @GeneratedValue
    @Column(name = "oderitem_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice;
    private int count;
}
