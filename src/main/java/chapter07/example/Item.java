package chapter07.example;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "ex_seven_item")
public class Item {
    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;

    @Column(name = "stock_quantity")
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();
}
