package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "author_id")
    private int id;
    @Column(name = "author_name")
    private String authorName;
    private String gender;
    @JsonIgnoreProperties(value = "authorSet")
    @ManyToMany
    @JoinTable(name = "author_book",
            joinColumns = @JoinColumn(name = "author_id", referencedColumnName = "author_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "book_id", referencedColumnName = "book_id", nullable = false))
    private Set<Book> bookSet = new HashSet<>();

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "birth")
    private Date born;

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", authorName='" + authorName + '\'' +
                ", gender='" + gender + '\'' +
                ", born=" + born +
                '}';
    }
}
