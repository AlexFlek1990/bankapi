package ru.af;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.af.dao.AccountDao;
import ru.af.dao.CardDao;
import ru.af.mvc.models.Account;
import ru.af.mvc.models.Card;
import ru.af.services.AccountService;
import ru.af.services.CardService;
import ru.af.services.dto.AccountDto;
import ru.af.services.dto.CardDto;
import ru.af.services.dto.RechargeRequest;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AccountTest {

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private CardDao cardDao;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AccountService accountService;

    @Autowired
    private CardService cardService;

    @BeforeEach
    public void init(){
        cardDao.deleteAll();
        accountDao.deleteAll();
    }

    @Test
    public void testCreateAccount() throws Exception {
        AccountDto account = new AccountDto(0, "test");
        MvcResult result = mockMvc.perform(
                        post("/accounts")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(account)))
                .andExpect(status().isOk()).andReturn();

        AccountDto createdAccount = objectMapper.readValue(result.getResponse().getContentAsString(), AccountDto.class);
        Assertions.assertNotNull(createdAccount);

        Account accountEntity = accountDao.findById(createdAccount.getId());
        Assertions.assertNotNull(accountEntity);

        Assertions.assertEquals(account.getName(), accountEntity.getName());
        Assertions.assertEquals(account.getBalance().longValue() * 100, accountEntity.getBalance());
    }

    @Test
    public void testAddCard() throws Exception {
        Account account = new Account();
        account.setName("test");
        account.setBalance(0L);
        long id = accountDao.save(account);
        String url = "/accounts/" + id + "/cards";
        CardDto cardDto = new CardDto();
        cardDto.setAccountId(id);
        MvcResult result = mockMvc.perform(
                        post(url)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(cardDto)))
                .andExpect(status().isOk()).andReturn();
        CardDto createdCard = objectMapper.readValue(result.getResponse().getContentAsString(), CardDto.class);
        Assertions.assertNotNull(createdCard);

        Card cardFromDB = cardDao.findById(createdCard.getId());
        Assertions.assertNotNull(cardFromDB);

        Assertions.assertEquals(cardDto.getAccountId(), cardFromDB.getAccount().getId());
    }

    @Test
    public void testRechargeBalance() throws Exception {
        Account account = new Account();
        account.setName("test");
        account.setBalance(0L);
        long id = accountDao.save(account);
        String url = "/accounts/" + id +"/balance";
        BigDecimal fund = new BigDecimal(10);

        BigDecimal checkedBalance =
                new BigDecimal(account.getBalance()).divide(new BigDecimal(100), 2, RoundingMode.DOWN).add(fund);
        RechargeRequest request = new RechargeRequest(id, fund);
        MvcResult result = mockMvc.perform(
                        post(url)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk()).andReturn();
        AccountDto accountDto = objectMapper.readValue(result.getResponse().getContentAsString(), AccountDto.class);
        Assertions.assertNotNull(accountDto);

        Assertions.assertEquals(accountDto.getBalance(), checkedBalance);
    }

    @Test
    public void testGetBalance() throws Exception {
        Account account = new Account();
        account.setName("test");
        account.setBalance(0L);
        long id = accountDao.save(account);
        String url = "/accounts/" + id +"/balance";
        BigDecimal checkedBalance = new BigDecimal(accountDao.findById(id).getBalance()).divide(new BigDecimal(100), 2, RoundingMode.DOWN);

        MvcResult result = mockMvc.perform(
                        get(url))
                .andExpect(status().isOk()).andReturn();
        BigDecimal balance = objectMapper.readValue(result.getResponse().getContentAsString(), BigDecimal.class);
        Assertions.assertNotNull(balance);

        Assertions.assertEquals(balance, checkedBalance);
    }

    @Test
    public void testGetAccount() throws Exception {
        Account account = new Account();
        account.setName("test");
        account.setBalance(0L);
        long id = accountDao.save(account);
        String url = "/accounts/" + id;
        cardService.addCard(id);

        MvcResult result = mockMvc.perform(
                        get(url))
                .andExpect(status().isOk()).andReturn();
        AccountDto accountDto = objectMapper.readValue(result.getResponse().getContentAsString(),
                AccountDto.class);

        Assertions.assertEquals(accountDto, accountService.convertToDto(accountDao.findById(id)));
    }

    @Test
    public void testGetCards() throws Exception{
        Account account = new Account();
        account.setName("test");
        account.setBalance(0L);
        long id = accountDao.save(account);
        String url = "/accounts/" + id;
        cardService.addCard(id);
        cardService.addCard(id);
        List<Card> cards = accountDao.findById(id).getCards();
        List<CardDto> checkedList = new ArrayList<>();
        for (Card card : cards) {
            checkedList.add(cardService.convertToDto(card));
        }

        MvcResult result = mockMvc.perform(
                        get(url))
                .andExpect(status().isOk()).andReturn();
        AccountDto accountDto = objectMapper.readValue(result.getResponse().getContentAsString(),
                AccountDto.class);
        List<CardDto> cardsDto = accountDto.getCards();

        Assertions.assertEquals(checkedList, cardsDto);
    }
}