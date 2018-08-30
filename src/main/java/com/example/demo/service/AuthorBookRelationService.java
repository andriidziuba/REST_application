package com.example.demo.service;

import com.example.demo.dao.AuthorBookRelationDAO;
import com.example.demo.dao.AuthorDAO;
import com.example.demo.model.Author;
import com.example.demo.model.AuthorBookRelation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service
public class AuthorBookRelationService {

    @Autowired
    private AuthorBookRelationDAO relationDAO;

    public List<AuthorBookRelation> getAllRelations() {
        return relationDAO.listRelations();
    }

    public AuthorBookRelation getRelationByID(int id) {
        Iterator it = getAllRelations().iterator();
        while(it.hasNext()) {
            AuthorBookRelation rel = (AuthorBookRelation) it.next();
            if(rel.getId() == id) return rel;
        }
        return null;
    }

    public Integer getRelationByData(int a, int b) {
        for(AuthorBookRelation _rel : this.getAllRelations()) {
            if(_rel.getAuthorId() == a && _rel.getBookId() == b) return _rel.getId();
        }
        return null;
    }

    public boolean save(AuthorBookRelation a) {
        for(AuthorBookRelation rel : getAllRelations()) if(rel.equals(a)) return false;
        if(a.getId() == -1) {
            relationDAO.addRelation(a);
        } else {
            relationDAO.updateRelation(a.getId(), a.getAuthorId(), a.getBookId());
        }
        return true;
    }

    public void delete(int relId) {
        relationDAO.deleteRelation(relId);
    }

    public void deleteAllBookAuthors(int bookId) {
        relationDAO.deleteAllBookAuthors(bookId);
    }

    public void deleteAllAuthorBooks(int authorId) {
        relationDAO.deleteAllAuthorBooks(authorId);
    }
}
