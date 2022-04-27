package chapter10.embeded;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "ten_member")
public class Member {
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded
    private Period workPeriod;

    @Embedded
    private Address homeAddress;

    @Embedded
    @AttributeOverrides({@AttributeOverride(name = "city", column = @Column(name = "work_city"))
                        , @AttributeOverride(name = "street", column = @Column(name = "work_street"))
                        , @AttributeOverride(name = "zipcode", column = @Column(name = "work_zipcode"))
                        })
    private Address workAddress;
}
