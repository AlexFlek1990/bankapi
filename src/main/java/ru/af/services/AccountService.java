package ru.af.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.af.dao.AccountDao;
import ru.af.mvc.models.Card;
import ru.af.mvc.models.Account;
import ru.af.services.dto.AccountDto;
import ru.af.services.dto.RechargeRequest;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Component
public class AccountService {

    @Autowired
    CardService cardService;
    @Autowired
    AccountDao accountDao;

    public AccountDto convertToDto(Account account) {
        AccountDto accountDto = new AccountDto(
                account.getId(),
                account.getName()
        );
        accountDto.setBalance(
                new BigDecimal(account.getBalance()).divide(new BigDecimal(100), 2, RoundingMode.DOWN));
        List<Card> cards = account.getCards();
        for (Card card : cards) {
            accountDto.addCardDto(cardService.convertToDto(card));
        }
        return accountDto;
    }

    public Account convertFromDto(AccountDto accountDto) {
        Account account = new Account();
        account.setName(accountDto.getName());
        account.setBalance(accountDto.getBalance().longValue() * 100);
        return account;
    }

    public AccountDto createAccount(AccountDto accountDto) {
        Account account = new Account(accountDto.getName());
        long id = save(account);
        account.setId(id);
        return convertToDto(account);
    }

    public AccountDto getAccount(long id) {
        return convertToDto(findById(id));
    }

    public AccountDto rechargeBalance(long id, RechargeRequest request) {
        Account account = findById(id);
        Long b = account.getBalance() + request.getFund().longValue() * 100;
        account.setBalance(b);
        update(account);
        return convertToDto(account);
    }

    public String getBalance(long id) {
        return new BigDecimal(
                findById(id).getBalance()).divide(new BigDecimal(100),2,RoundingMode.DOWN).toString();
    }

    public Account findById(long id) {
        return accountDao.findById(id);
    }

    public long save(Account account) {
        return accountDao.save(account);
    }

    public void update(Account account) {
        accountDao.update(account);
    }

    public void delete(Account account) {
        accountDao.delete(account);
    }
}