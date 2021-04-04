package com.haulmont.testtask.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "Author")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull()
    @Size(min = 1)
    @Pattern(regexp = "^\\D+$")
    @Column(name = "firstname", length = 50)
    private String firstName;

    @NotNull()
    @Size(min = 1)
    @Pattern(regexp = "^\\D+$")
    @Column(name = "middleName", length = 50)
    private String middleName;

    @NotNull()
    @Size(min = 1)
    @Pattern(regexp = "^\\D+$")
    @Column(name = "lastname", length = 50)
    private String lastName;

    @OneToMany(mappedBy = "author",
            fetch = FetchType.LAZY,
            cascade = CascadeType.REFRESH)
    private List<Book> books;

    public Author() {
    }

    public Author(@NotNull String lastName, @NotNull String firstName, @NotNull String middleName) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return lastName + " "
                + firstName + " "
                + middleName;
    }
}
