package chapter07.onetoone;

import javax.persistence.*;

@Entity(name = "seven_III_member")
public class Member {
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    //주 테이블에 외래키 단방향 (cf.대상 테이블에는 단방향 대신 양방향을 지원.)
    @OneToOne
    @JoinColumn(name = "locker_id")
    private Locker locker;

}
