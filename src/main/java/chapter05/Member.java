package chapter05;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "newMember")
public class Member {
    @Id @GeneratedValue
    private Long id;

    @Column(name = "name", nullable = false)
    private String userName;

    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    private LocalDateTime lastModifiedDate; //LocalDate => date, LocalDateTime => timestamp

    @Lob
    private String description;

    @Transient
    private int temp;   //DB에 반영 제외 컬럼
}
