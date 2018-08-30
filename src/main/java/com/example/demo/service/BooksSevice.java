package com.example.demo.service;

import com.example.demo.dao.AuthorBookRelationDAO;
import com.example.demo.dao.BooksDAO;
import com.example.demo.model.Author;
import com.example.demo.model.Books;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service
public class BooksSevice {

    @Autowired
    private BooksDAO booksDAO;

    @Autowired
    private AuthorBookRelationDAO relationDAO;

    public List<Books> getAllBooks() {
        return booksDAO.listBooks();
    }

    public Books getBookByID(int id) {
        Iterator it = getAllBooks().iterator();
        while(it.hasNext()) {
            Books book = (Books) it.next();
            if(book.getId() == id) return book;
        }
        return null;
    }

    public void save(Books b) {
        if(b.getId() == -1) {
            booksDAO.addBook(b);
        } else {
            booksDAO.updateBook(b.getId(), b.getBookName(), b.getGenre(), b.getPublished(), b.getRating());
        }
    }

    public void delete(int bookId) {
//        relationDAO.deleteAllBookAuthors(bookId); // TODO: Replace with service
        booksDAO.deleteBook(bookId);
    }

}
