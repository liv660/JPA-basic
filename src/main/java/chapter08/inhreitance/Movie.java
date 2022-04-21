package chapter08.inhreitance;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "eight_movie_I")
//@DiscriminatorValue("")   //default = entity name
public class Movie extends Item {
    private String director;
    private String actor;
}
