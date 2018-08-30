package com.example.demo.dao;

import com.example.demo.model.Books;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Repository
public class BooksDAO {

    @Autowired
    private SessionFactory factory;

    /* Create */
    public void addBook(Books book) {
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.save(book);
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    /* Read */
    public List<Books> listBooks( ){
        Session session = factory.openSession();
        Transaction tx = null;
        List books = null;
        try {
            tx = session.beginTransaction();
            books = session.createQuery("FROM Books").list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return books;
    }

    /* Update */
    public void updateBook(Integer bookId, String bookName, String bookGenre, Date datePublished, int bookRating) {
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            Books book = session.get(Books.class, bookId);
            book.setBookName(bookName);
            book.setGenre(bookGenre);
            book.setPublished(datePublished);
            book.setRating(bookRating);
            session.update(book);
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    /* Delete */
    public void deleteBook(Integer bookId) {
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            Books book = session.get(Books.class, bookId);
            session.delete(book);
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
