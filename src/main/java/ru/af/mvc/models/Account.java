package ru.af.mvc.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String name;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Card> cards;
    @Column
    private Long balance;

    public Account() {
    }

    public Account(String name) {
        this.name = name;
        this.balance = 0L;
        cards = new ArrayList<>();
    }

    public void setId(long id) {this.id = id;}

    public void setName(String name) {
        this.name = name;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Card> getCards() {
        return cards;
    }

    public Long getBalance() {
        return balance;
    }

    @Override
    public String toString() {
        return "models.Account{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", balance=" + balance +
                '}';
    }
}
