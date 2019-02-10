package com.example.demo.service;

import com.example.demo.dao.AuthorDAO;
import com.example.demo.dao.BookDAO;
import com.example.demo.exceptions.NoSuchAuthorException;
import com.example.demo.exceptions.NoSuchBookException;
import com.example.demo.model.Author;
import com.example.demo.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class AuthorService {

    private final AuthorDAO authorDAO;

    private final BookDAO bookDAO;

    @Autowired
    public AuthorService(AuthorDAO authorDAO, BookDAO bookDAO) {
        this.authorDAO = authorDAO;
        this.bookDAO = bookDAO;
    }

    public List<Author> getAllAuthors() {
        return authorDAO.findAll();
    }

    public Author getAuthorByID(int id) {
        return authorDAO.findById(id).orElse(null);
    }

    public Set<Author> getAllAuthorsOfBook(int bookId) {
        Book book = bookDAO.findById(bookId).orElseThrow(NoSuchBookException::new);
        return book.getAuthorSet();
    }

    public boolean save(Author a) {
        for (Author _a : getAllAuthors()) if (_a.equals(a)) return false;
        authorDAO.save(a);
        return true;
    }

    public void delete(int authorId) {
        authorDAO.deleteById(authorId);
    }

    @Transactional
    public void createAuthor(Author newAuthor) {
        authorDAO.save(newAuthor);
    }

    @Transactional
    public void updateAuthor(Author uAuthor, int authorId) {
        Author author = authorDAO.findById(authorId).orElseThrow(NoSuchAuthorException::new);
        if (uAuthor.getAuthorName() != null)
            author.setAuthorName(uAuthor.getAuthorName());
        if (uAuthor.getBorn() != null)
            author.setBorn(uAuthor.getBorn());
        if (uAuthor.getGender() != null)
            author.setGender(uAuthor.getGender());
    }

    @Transactional
    public void deleteAuthor(int authorId) {
        authorDAO.findById(authorId).orElseThrow(NoSuchAuthorException::new);
        authorDAO.deleteById(authorId);
    }
}
