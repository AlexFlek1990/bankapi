package ru.af.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.af.mvc.models.Account;

@Component
public class AccountDao {
    private final SessionFactory sessionFactory;

    @Autowired
    public AccountDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Account findById(long id) {
        Session session = sessionFactory.openSession();
        Account account = session.get(Account.class, id);
        session.close();
        return account;
    }

    public long save(Account account) {
        Session session = sessionFactory.openSession();
        Transaction t = session.beginTransaction();
        long id = (long) session.save(account);
        t.commit();
        session.close();
        return id;
    }

    public void update(Account account) {
        Session session = sessionFactory.openSession();
        Transaction t = session.beginTransaction();
        session.update(account);
        t.commit();
        session.close();
    }

    public void delete(Account account) {
        Session session = sessionFactory.openSession();
        Transaction t = session.beginTransaction();
        session.delete(account);
        t.commit();
        session.close();
    }

    public void deleteAll(){
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("delete from Account ");
        session.close();
    }
}