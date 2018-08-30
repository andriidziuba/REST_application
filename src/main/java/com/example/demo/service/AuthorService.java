package com.example.demo.service;

import com.example.demo.dao.AuthorBookRelationDAO;
import com.example.demo.dao.AuthorDAO;
import com.example.demo.model.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service
public class AuthorService {

    @Autowired
    private AuthorDAO authorDAO;

    @Autowired
    private AuthorBookRelationDAO relationDAO;

    public List<Author> getAllAuthors() {
        return authorDAO.listAuthor();
    }

    public Author getAuthorByID(int id) {
        Iterator it = getAllAuthors().iterator();
        while(it.hasNext()) {
            Author author = (Author) it.next();
            if(author.getId() == id) return author;
        }
        return null;
    }

    public boolean save(Author a) {
        if(a.getId() == -1) {
            for(Author _a : getAllAuthors()) if(_a.equals(a)) return false;
            authorDAO.addAuthor(a);
        } else {
            for(Author _a : getAllAuthors()) if(_a.equalsWithId(a)) return false;
            authorDAO.updateAuthor(a.getId(), a.getAuthorName(), a.getGender(), a.getBorn());
        }
        return true;
    }

    public void delete(int authorId) {
//        relationDAO.deleteAllAuthorBooks(authorId); // TODO: Replace with service
        authorDAO.deleteAuthor(authorId);
    }
}
