package ru.af.mvc.models;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "cards")

public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String cardNumber;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "acount_id")
    private Account account;
    @Column(name = "card_limit")
    private BigDecimal limit;

    public Card() {
    }

    public Card(String cardNumber, Account account, BigDecimal limit) {
        this.cardNumber = cardNumber;
        this.account = account;
        this.limit = limit;
    }

    public long getId() {
        return id;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public Account getAccount() {return account;}

    public BigDecimal getLimit() {
        return limit;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void setLimit(BigDecimal limit) {
        this.limit = limit;
    }

    public String generateCardNumber(){
        return "cardNumber";
    }

    @Override
    public String toString() {
        return "models.Card{" +
                "id=" + id +
                ", cardNumber=" + cardNumber +
                ", accountId=" + account.getId() +
                ", limit=" + limit +
                '}';
    }
}
