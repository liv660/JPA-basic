package chapter08.inhreitance;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "eight_item_I")
@Inheritance(strategy = InheritanceType.JOINED /* JOINED | SINGLE_TABLE | TABLE_PER_CLASS */)
@DiscriminatorColumn
public abstract class Item {
    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
}
