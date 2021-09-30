package ru.af.services.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AccountDto {
    private long id;
    private String cardHolder;
    private BigDecimal balance;
    private List<CardDto> cards;

    public AccountDto(long id, String cardHolder) {
        this.id = id;
        this.cardHolder = cardHolder;
        this.balance = BigDecimal.ZERO;
        cards = new ArrayList<>();
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public List<CardDto> getCards() {
        return cards;
    }

    public void addCardDto(CardDto cardDto) {
        cards.add(cardDto);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountDto that = (AccountDto) o;
        return id == that.id &&
                Objects.equals(cardHolder, that.cardHolder) &&
                Objects.equals(balance, that.balance) &&
                Objects.equals(cards, that.cards);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cardHolder, balance, cards);
    }

    @Override
    public String toString() {
        return "AccountDto{" +
                "id=" + id +
                ", cardHolder='" + cardHolder + '\'' +
                ", balance=" + balance +
                ", cards=" + cards +
                '}';
    }
}