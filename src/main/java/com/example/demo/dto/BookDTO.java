package com.example.demo.dto;

import com.example.demo.controller.AuthorController;
import com.example.demo.model.Book;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

import java.util.Date;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class BookDTO extends ResourceSupport {

    private Book book;

    public BookDTO(Book book, Link link) {
        this.book = book;
        add(link);
        add(linkTo(methodOn(AuthorController.class).getAuthorsByBookID(book.getId())).withRel("authorSet"));
    }

    public int getBookId() {
        return book.getId();
    }

    public String getBookName() {
        return book.getBookName();
    }

    public String getGenre() {
        return book.getGenre();
    }

    public Date getPublishingDate() {
        return book.getPublished();
    }

    public int getRating() {
        return book.getRating();
    }

}
