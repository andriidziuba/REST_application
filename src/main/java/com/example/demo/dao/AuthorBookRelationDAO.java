package com.example.demo.dao;

import com.example.demo.model.Author;
import com.example.demo.model.AuthorBookRelation;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class AuthorBookRelationDAO {

    @Autowired
    private SessionFactory factory;

    /* Create */
    public void addRelation(AuthorBookRelation relation) {
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.save(relation);
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    /* Read */
    public List<AuthorBookRelation> listRelations( ){
        Session session = factory.openSession();
        Transaction tx = null;
        List relList = null;
        try {
            tx = session.beginTransaction();
            relList = session.createQuery("FROM AuthorBookRelation ").list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return relList;
    }

    /* Update */
    public void updateRelation(Integer relId, int authorId, int bookId) {
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            AuthorBookRelation rel = session.get(AuthorBookRelation.class, relId);
            rel.setAuthorId(authorId);
            rel.setBookId(bookId);
            session.update(rel);
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    /* Delete */
    public void deleteRelation(Integer relId) {
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            AuthorBookRelation relation = session.get(AuthorBookRelation.class, relId);
            session.delete(relation);
            tx.commit();
        } catch (HibernateException | NullPointerException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void deleteAllBookAuthors(int bookId) {
        deleteQuery("DELETE FROM AuthorBookRelation WHERE bookId=" + bookId);
    }

    public void deleteAllAuthorBooks(int authorId) {
        deleteQuery("DELETE FROM AuthorBookRelation WHERE authorId=" + authorId);
    }

    private void deleteQuery(String hql) {
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.createQuery(hql).executeUpdate();
            tx.commit();
        } catch (HibernateException | NullPointerException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
