package com.example.demo.dto;

import com.example.demo.controller.BookController;
import com.example.demo.model.Author;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

import java.util.Date;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class AuthorDTO extends ResourceSupport {

    private Author author;

    public AuthorDTO(Author author, Link selfLink) {
        this.author = author;
        add(selfLink);
        add(linkTo(methodOn(BookController.class).getBooksByAuthorID(author.getId())).withRel("bookSet"));
    }

    public int getAuthorId() {
        return author.getId();
    }

    public String getAuthorName() {
        return author.getAuthorName();
    }

    public String getGender() {
        return author.getGender();
    }

    public Date getBirthDate() {
        return author.getBorn();
    }
}
