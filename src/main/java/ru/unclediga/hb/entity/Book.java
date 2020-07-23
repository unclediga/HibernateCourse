package ru.unclediga.hb.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
public class Book implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @Transient
    private long author_id;

    @ManyToOne
//    @JoinColumn  (Hiber will get default -> author_id)
//    @JoinColumn(name="author_id") -> explicitly set name
    private Author author;

    public Book() {
    }

    public Book(String name, long author_id) {
        this.name = name;
        this.author_id = author_id;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(long author_id) {
        this.author_id = author_id;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", author_id=" + author_id +
                ", author=" + author +
                '}';
    }
}
