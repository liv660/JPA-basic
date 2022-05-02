package chapter11.jpql.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "eleven_product")
public class Product {
    @Id @GeneratedValue
    @Column(name = "product_id")
    private Long id;

    private String name;

    private int price;

    @Column(name = "stock_amount")
    private int stockAmount;

    @OneToMany(mappedBy = "product")
    private List<Order> orders = new ArrayList<>();
}
