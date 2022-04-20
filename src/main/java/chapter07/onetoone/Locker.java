package chapter07.onetoone;

import javax.persistence.*;

@Entity(name = "seven_I_locker")
public class Locker {
    @Id @GeneratedValue
    @Column(name = "locker_id")
    private Long id;

    private String name;

    //일대일 양방향
    @OneToOne(mappedBy = "locker")
    private Member member;
}
