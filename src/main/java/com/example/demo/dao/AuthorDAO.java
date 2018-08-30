package com.example.demo.dao;

import com.example.demo.model.Author;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.Null;
import java.util.Date;
import java.util.List;

@Repository
public class AuthorDAO {

    @Autowired
    private SessionFactory factory;

    /* Create */
    public void addAuthor(Author author) {
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.save(author);
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    /* Read */
    public List<Author> listAuthor( ){
        Session session = factory.openSession();
        Transaction tx = null;
        List authors = null;
        try {
            tx = session.beginTransaction();
            authors = session.createQuery("FROM Author").list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return authors;
    }

    /* Update */
    public void updateAuthor(Integer authorId, String name, String gender, Date born) {
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            Author author = session.get(Author.class, authorId);
            author.setAuthorName(name);
            author.setGender(gender);
            author.setBorn(born);
            session.update(author);
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    /* Delete */
    public void deleteAuthor(Integer authorId) {
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            Author author = session.get(Author.class, authorId);
            session.delete(author);
            tx.commit();
        } catch (HibernateException | NullPointerException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
