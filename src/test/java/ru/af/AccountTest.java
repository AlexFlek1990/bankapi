package ru.af;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
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
import ru.af.services.dto.AccountDto;
import ru.af.services.dto.CardDto;
import ru.af.services.dto.RechargeRequest;

import java.math.BigDecimal;

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

        Assertions.assertEquals(account.getCardHolder(), accountEntity.getName());
        Assertions.assertEquals(account.getBalance().longValue() * 100, accountEntity.getBalance());
    }

    @Test
    public void testAddCard() throws Exception {
        CardDto cardDto = new CardDto();
        cardDto.setAccountId(2);
        MvcResult result = mockMvc.perform(
                        post("/accounts/2/cards")
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

        RechargeRequest request = new RechargeRequest(3, BigDecimal.TEN);
        MvcResult result = mockMvc.perform(
                        post("/accounts/3/balance")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk()).andReturn();
        AccountDto accountDto = objectMapper.readValue(result.getResponse().getContentAsString(), AccountDto.class);
        Assertions.assertNotNull(accountDto);

        Account accountFromDB = accountDao.findById(accountDto.getId());
        Assertions.assertNotNull(accountFromDB);

        Assertions.assertEquals(accountDto.getBalance().longValue() * 100, accountFromDB.getBalance());
    }

    @Test
    public void testGetBalance() throws Exception {

        MvcResult result = mockMvc.perform(
                        get("/accounts/3/balance"))
                .andExpect(status().isOk()).andReturn();
        BigDecimal balance = objectMapper.readValue(result.getResponse().getContentAsString(), BigDecimal.class);
        Assertions.assertNotNull(balance);

        BigDecimal balanceFromDB = new BigDecimal(accountDao.findById(3).getBalance() / 100);
        Assertions.assertNotNull(balanceFromDB);

        Assertions.assertEquals(balance, balanceFromDB);
    }

    @Test
    public void testGetCards() throws Exception {
        MvcResult result = mockMvc.perform(
                        get("/accounts/1"))
                .andExpect(status().isOk()).andReturn();
        AccountDto accountDto = objectMapper.readValue(result.getResponse().getContentAsString(),
                AccountDto.class);

        Account account = accountDao.findById(1);
        Assertions.assertNotNull(account);

        Assertions.assertEquals(accountDto, accountService.convertToDto(account));
    }


}