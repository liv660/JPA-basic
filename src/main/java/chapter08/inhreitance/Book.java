package chapter08.inhreitance;

import javax.persistence.Entity;

@Entity(name = "eight_book_I")
public class Book extends Item {
    private String author;
    private String isbn;
}
