package com.example.demo.model;

import javax.persistence.Id;

public class AuthorBookRelation {

    @Id
    private int id = -1;
    private int authorId;
    private int bookId;

    public AuthorBookRelation() {
    }

    public AuthorBookRelation(int id, int authorId, int bookId) {
        this.id = id;
        this.authorId = authorId;
        this.bookId = bookId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    @Override
    public boolean equals(Object obj) {
        AuthorBookRelation r = (AuthorBookRelation)obj;
        System.out.println(this.authorId + " " + r.getAuthorId() + " " + this.bookId + " " + r.getBookId() + " " + (this.authorId == r.getAuthorId() && this.bookId == r.getBookId()));
        return this.authorId == r.getAuthorId() && this.bookId == r.getBookId();
    }

    @Override
    public String toString() {
        return "AuthorBookRelation{" +
                "id=" + id +
                ", authorId=" + authorId +
                ", bookId=" + bookId +
                '}';
    }
}
