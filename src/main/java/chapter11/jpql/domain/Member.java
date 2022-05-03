package chapter11.jpql.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity(name = "eleven_member")
public class Member {
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String username;

    private int age;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

    //연관관계 메소드
    public void changeTeam(Team team) {
        this.team = team;
        this.team.getMembers().add(this);
    }

    public Member() {}
    public Member(String username, int age) {
        this.username = username;
        this.age = age;
    }

    //[주의] 양방향 매핑 컬럼 제외할 것.
    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", age=" + age +
                '}';
    }
}
