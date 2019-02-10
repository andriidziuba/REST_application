package com.example.demo.dao;

import com.example.demo.model.Author;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.Null;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorDAO extends JpaRepository<Author, Integer> {

}
