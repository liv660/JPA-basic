package chapter10.example.domain;

import chapter08.example.domain.Item;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "ex_ten_category")
public class Category {
    @Id @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;

    @ManyToMany
    @JoinColumn(name = "item_id")
    private List<Item> items = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();
}
