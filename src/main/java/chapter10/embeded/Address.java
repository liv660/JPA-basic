package chapter10.embeded;

import lombok.*;

import javax.persistence.Embeddable;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Address {
    private String city;
    private String street;
    private String zipcode;
}
