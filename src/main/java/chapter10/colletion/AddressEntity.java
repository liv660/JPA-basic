package chapter10.colletion;

import chapter10.embeded.Address;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "ALT_ADDRESS")
public class AddressEntity {
    @Id @GeneratedValue
    private Long id;

    @Embedded
    private Address address;

    public AddressEntity(String city, String street, String zipcode) {
        this.address = new Address(city, street, zipcode);
    }
}
