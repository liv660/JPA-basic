package chapter10.embeded;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Period {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
