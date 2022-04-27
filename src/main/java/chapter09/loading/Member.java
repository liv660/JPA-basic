package chapter09.loading;

import chapter09.proxy.Team;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "nine_member_II")
public class Member {
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    //@ManyToOne(fetch = FetchType.LAZY)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "team_id")
    private Team team;

    @Column(name = "user_name")
    private String userName;

}
