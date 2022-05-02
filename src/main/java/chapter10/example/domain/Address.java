package chapter10.example.domain;

import lombok.Getter;

import javax.persistence.Embeddable;
import java.util.Objects;

@Getter
@Embeddable
public class Address {
    private String city;
    private String street;
    private String zipcode;

    /*
        equals & hashCode 는 자동 구현 코드 사용 권장.
        +) proxy 사용 시에도 값에 접근할 수 있게 getter로 필드에 접근하는 코드 사용하기.
     */

    public String getFullAddress() {
        StringBuilder sb = new StringBuilder();
        sb.append(getCity()).append(" ").append(getStreet()).append(" ").append(getZipcode()).append(" ");
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(getCity(), address.getCity()) && Objects.equals(getStreet(), address.getStreet()) && Objects.equals(getZipcode(), address.getZipcode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCity(), getStreet(), getZipcode());
    }
}
