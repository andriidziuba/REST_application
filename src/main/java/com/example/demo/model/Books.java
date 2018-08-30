package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Books {

    @Id
    private int id = -1;
    private String bookName;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date published;
    private String genre;
    private int rating;
    @JsonIgnoreProperties(value = "booksSet")
    private Set<Author> authorSet = new HashSet<Author>(0);
    private int authorCount;

    public Books() {}

    public Books(String bookName, Date published, String genre, int rating) {
        this.bookName = bookName;
        this.published = published;
        this.genre = genre;
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public Date getPublished() {
        return published;
    }

    public void setPublished(Date published) {
        this.published = published;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Set<Author> getAuthorSet() {
        return authorSet;
    }

    public void setAuthorSet(Set<Author> authorSet) {
        authorCount = authorSet.size();
        this.authorSet = authorSet;
    }

    public int getAuthorCount() {
        return authorCount;
    }

    @Override
    public String toString() {
        return "Books{" +
                "id=" + id +
                ", bookName='" + bookName + '\'' +
                ", published=" + published +
                ", genre='" + genre + '\'' +
                ", rating=" + rating +
                '}';
    }
}
