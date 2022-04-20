package chapter06;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "six_member")
public class Member {
    @Id @GeneratedValue
    private Long id;

    @Column(name = "name")
    private String userName;

    //단방향 연관관계 설정 후
    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    /*  단방향 연관관계 설정 전
    @Column(name = "team_id")
    private Long teamId;
    */
}
