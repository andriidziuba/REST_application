package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


public class Author {

    @Id
    private int id = -1;
    private String authorName;
    private String gender;
    @JsonIgnoreProperties(value = "authorSet")
    private Set<Books> booksSet = new HashSet<Books>(0);

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date born;
    private int bookCount;

    public Author() {}

    public Author(String authorName, String gender, Date born) {
        this.authorName = authorName;
        this.gender = gender;
        this.born = born;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBorn() {
        return born;
    }

    public void setBorn(Date born) {
        this.born = born;
    }

    public Set<Books> getBooksSet() {
        return booksSet;
    }

    public void setBooksSet(Set<Books> booksSet) {
        bookCount = booksSet.size();
        this.booksSet = booksSet;
    }

    public int getBookCount() {
        return bookCount;
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", authorName='" + authorName + '\'' +
                ", gender='" + gender + '\'' +
                ", born=" + born +
                '}';
    }

    public boolean equalsWithId(Object obj) {
        if(obj == null) return false;
        Author a = (Author)obj;
        return getId() == a.getId() && equals(obj);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        Author a = (Author)obj;
        try {
            LocalDate ld1 = Instant.ofEpochMilli(getBorn().getTime())
                    .atZone(ZoneId.of("UTC"))
                    .toLocalDate();
            LocalDate ld2 = a.getBorn().toInstant().atZone(ZoneId.of("UTC")).toLocalDate();

            return getAuthorName().equals(a.getAuthorName()) &&
                    getGender().equals(a.getGender()) && (ld1.compareTo(ld2) == 0);
        } catch (NullPointerException e) {
            return false;
        }
    }
}
