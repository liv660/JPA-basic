package chapter07.manytoone;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "seven_I_Team")
public class Team {
    @Id @GeneratedValue
    @Column(name = "team_id")
    private Long id;

    private String name;

    //Member-Team 다대일 양방향 (cf. 단방향은 따로 설정하지 않음.)
    @OneToMany(mappedBy = "team")
    private List<Member> members = new ArrayList<>();
}
