package chapter07.onetomany;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "seven_II_team")
public class Team {
    @Id @GeneratedValue
    @Column(name = "team_id")
    private Long id;

    private String name;

    //Team-Member 일대다 단방향 (cf.일대다 양방향 대신 다대일 양방향 사용하기)
    @OneToMany
    @JoinColumn(name = "member_id")
    private List<Member> members = new ArrayList<>();

}
