package ru.af.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.af.dao.CardDao;
import ru.af.mvc.models.Card;
import ru.af.services.dto.CardDto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class CardService {

    @Autowired
    AccountService accountService;
    @Autowired
    CardDao cardDao;

    public CardDto convertToDto(Card card) {
         CardDto cardDto = new CardDto();
         cardDto.setId(card.getId());
         cardDto.setCardHolder(card.getAccount().getName());
         cardDto.setCardNumber(card.getCardNumber());
         cardDto.setAccountId(card.getAccount().getId());
         cardDto.setLimit(card.getLimit());
        return cardDto;
    }

    public Card convertFromDto(CardDto cardDto) {
        return new Card(
                cardDto.getCardNumber(),
                accountService.findById(cardDto.getAccountId()),
                cardDto.getLimit());
    }

    public List<CardDto> getCards(long id) {
        List<CardDto> cardsDto = new ArrayList<>();
        List<Card> cards = cardDao.getCards(id);
        for (Card card : cards) {
            cardsDto.add(convertToDto(card));
        }
        return cardsDto;
    }

    public CardDto addCard(long accountId) {
        Card card = new Card();
        card.setCardNumber(card.generateCardNumber());
        card.setAccount(accountService.findById(accountId));
        card.setLimit(new BigDecimal(0));
        long id = save(card);
        System.out.println(id);
        card.setId(id);
        return convertToDto(card);
    }

    public Card findById(long id) {
        return cardDao.findById(id);
    }

    public long save(Card card) {
        return cardDao.save(card);
    }

    public void update(Card card) {
        cardDao.update(card);
    }

    public void delete(Card card) {
        cardDao.delete(card);
    }
}