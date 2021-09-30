package ru.af.services.dto;

import java.math.BigDecimal;
import java.util.Objects;

public class CardDto {
    private long id;
    private String cardNumber;
    private long accountId;
    private String cardHolder;
    private BigDecimal limit;

    public CardDto() {
        this.limit = BigDecimal.ZERO;
    }

    public long getId() {
        return id;
    }

    public String getCardNumber() {return cardNumber;}

    public long getAccountId() {
        return accountId;
    }

    public BigDecimal getLimit() {
        return limit;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }

    public void setLimit(BigDecimal limit) {
        this.limit = limit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CardDto cardDto = (CardDto) o;
        return id == cardDto.id &&
                accountId == cardDto.accountId &&
                Objects.equals(cardNumber, cardDto.cardNumber) &&
                Objects.equals(cardHolder, cardDto.cardHolder) &&
                Objects.equals(limit, cardDto.limit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cardNumber, accountId, cardHolder, limit);
    }

    @Override
    public String toString() {
        return "CardDto{" +
                "id=" + id +
                ", cardNumber='" + cardNumber + '\'' +
                ", accountId=" + accountId +
                ", cardHolder='" + cardHolder + '\'' +
                ", limit=" + limit +
                '}';
    }
}