package ru.unclediga.hb.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
public class Author implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String name;
    @Column(name = "second_name")
    String secondName;
    //    @OneToMany  -> org.postgresql.util.PSQLException:
//    ERROR: relation "author_book" does not exist
//    @OneToMany(mappedBy = "author")

//    one query: SELECT FROM Author JOIN Book ...
//    @OneToMany(mappedBy = "author", fetch = FetchType.EAGER)

    //    two query: SELECT FROM Author,SELECT FROM Book WHERE author_id = ?
    @OneToMany(mappedBy = "author")
    private List<Book> books = new ArrayList<>();

    public Author() {
    }

    public Author(String name) {
        this.name = name;
    }

    public Author(String name, String secondName) {
        this.name = name;
        this.secondName = secondName;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
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

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    @Override
    public String toString() {
        // StackOverFlow,  if books are included into toString()
        // Author -> Books -> book.author -> Author -> Books.book -> author...
        return "Author{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", secondName='" + secondName + '\'' +
                ", books='" +
                books.stream()
                        .map(it -> "" + it.toString())
                        .collect(Collectors.joining(",")) +
                '}';
    }
}

