package chapter07.manytoone;

import javax.persistence.*;

@Entity(name = "seven_I_member")
public class Member {
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    //다대일 단방향 & 양방향
    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    @Column(name = "user_name")
    private String userName;

}
