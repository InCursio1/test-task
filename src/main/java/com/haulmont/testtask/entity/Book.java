package com.haulmont.testtask.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "Book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 1)
    @Column(name = "title", length = 50)
    private String title;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY,
            cascade = CascadeType.REFRESH)
    @JoinColumn(name = "author_id")
    private Author author;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY,
            cascade = CascadeType.REFRESH)
    @JoinColumn(name = "genre_id")
    private Genre genre;

    @NotNull
    @Pattern(regexp = "^\\D+$")
    @Column(name = "city")
    private String city;

    @NotNull
    @Pattern(regexp = "^[0-9]{0,3}$|^1[0-9]{3}$|^20[0-1]+[0-9]+$|^202[0-1]+$")
    @Column(name = "year")
    private String year;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "publisher", length = 15)
    private Publisher publisher;

    public Book() {
    }

    public Book(@NotNull @Size(min = 1) String title, @NotNull Author author, @NotNull Genre genre,
                @NotNull Publisher publisher, @NotNull String year, @NotNull String city) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.city = city;
        this.year = year;
        this.publisher = publisher;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }
}