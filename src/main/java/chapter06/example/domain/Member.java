package chapter06.example.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "ex_member_II")
public class Member {
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;
    private String city;
    private String street;
    private String zipcode;

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();
}
