package chapter08.inhreitance;

import javax.persistence.Entity;

@Entity(name = "eight_album")
public class Album extends Item {
    private String artist;
}
