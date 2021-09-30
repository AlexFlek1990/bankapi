package ru.af.mvc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.af.services.AccountService;
import ru.af.services.CardService;
import ru.af.services.dto.CardDto;
import ru.af.services.dto.AccountDto;
import ru.af.services.dto.RechargeRequest;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;
    private final CardService cardService;

    @Autowired
    public AccountController(AccountService accountService, CardService cardService) {
        this.accountService = accountService;
        this.cardService = cardService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDto> getAccount(@PathVariable("id") long id) {
        return ResponseEntity.ok(accountService.getAccount(id));
    }

    @GetMapping("/{id}/balance")
    public String getBalance(@PathVariable("id") long id) {
        return accountService.getBalance(id);
    }

    @GetMapping("/{id}/cards")
    public ResponseEntity<List<CardDto>> getCards(@PathVariable("id") long id) {
        return ResponseEntity.ok(cardService.getCards(id));
    }

    @PostMapping
    public ResponseEntity<AccountDto> createAccount(@RequestBody AccountDto accountDto) {
        return ResponseEntity.ok(accountService.createAccount(accountDto));
    }

    @PostMapping("/{id}/balance")
    public ResponseEntity<AccountDto> rechargeBalance(@PathVariable("id") long id, @RequestBody RechargeRequest request) {
        return ResponseEntity.ok(accountService.rechargeBalance(id, request));
    }

    @PostMapping("/{id}/cards")
    public ResponseEntity<CardDto> addCard(@PathVariable("id") long id) {
        return ResponseEntity.ok(cardService.addCard(id));
    }
}