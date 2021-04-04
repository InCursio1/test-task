package com.haulmont.testtask.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "Genre")
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull()
    @Size(min = 1)
    @Pattern(regexp = "^\\D+$")
    @Column(name = "title", length = 50)
    private String title;

    @OneToMany(mappedBy = "genre",
            fetch = FetchType.LAZY,
            cascade = CascadeType.REFRESH)
    private List<Book> books;

    public Genre() {}

    public Genre(@NotNull() String title) {
        this.title = title;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return title;
    }
}