package ru.af.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.af.mvc.models.Card;

import java.util.List;


@Component
public class CardDao {
    private final SessionFactory sessionFactory;

    @Autowired
    public CardDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Card findById(long id) {
        Session session = sessionFactory.openSession();
        Card card = session.get(Card.class, id);
        session.close();
        return card;
    }

    public long save(Card card) {
        Session session = sessionFactory.openSession();
        Transaction t = session.beginTransaction();
        long id = (long) session.save(card);
        t.commit();
        session.close();
        return id;
    }

    public void update(Card card) {
        Session session = sessionFactory.openSession();
        Transaction t = session.beginTransaction();
        session.update(card);
        t.commit();
        session.close();
    }

    public void delete(Card card) {
        Session session = sessionFactory.openSession();
        Transaction t = session.beginTransaction();
        session.delete(card);
        t.commit();
        session.close();
    }

    public List<Card> getCards(long accountId){
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("from Card where account.id = :accountId");
        query.setParameter("accountId", accountId);
        List<Card> cards = query.list();
        session.close();
        return cards;
    }
}