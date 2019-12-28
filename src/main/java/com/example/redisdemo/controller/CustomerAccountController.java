package com.example.redisdemo.controller;

import com.example.redisdemo.exceptions.CustomerAccountNotFoundException;
import com.example.redisdemo.model.CustomerAccount;
import com.example.redisdemo.service.CustomerAccountService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@AllArgsConstructor
public class CustomerAccountController {

    private final CustomerAccountService service;
    private final UriComponentsBuilder uriBuilder = UriComponentsBuilder.newInstance()
            .scheme("http")
            .host("localhost")
            .port(8080)
            .path("/customers/{id}");

    @PostMapping("/customers")
    public ResponseEntity<CustomerAccount> createAccount(@RequestBody CustomerAccount account) {
        service.save(account);
        log.info("Created customer account: " + account);
        return ResponseEntity.created(uriBuilder.build(account.getId())).build();
    }

    @PutMapping("/customers/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateAccount(@RequestBody CustomerAccount updateData, @PathVariable String id) {
        Optional<CustomerAccount> maybeAccount = service.findById(id);
        CustomerAccount account;

        if(maybeAccount.isPresent()) {
            account = maybeAccount.get();
        } else {
            log.warn("An attempt was made to update a nonexistent resource. Customer ID: " + id);
            throw new CustomerAccountNotFoundException();
        }

        account.setFirstName(updateData.getFirstName());
        account.setLastName(updateData.getLastName());
        account.setAge(updateData.getAge());

        service.save(account);
        log.info("Account with ID '"+ id +"' updated");
    }

    @GetMapping("/customers/{id}")
    public CustomerAccount getAccountById(@PathVariable String id) {
        Optional<CustomerAccount> maybeAccount = service.findById(id);
        CustomerAccount account;

        if(maybeAccount.isPresent()) {
            account = maybeAccount.get();
        } else {
            log.warn("An attempt was made to get a nonexistent resource. Customer ID: " + id);
            throw new CustomerAccountNotFoundException();
        }
        return account;
    }

    @GetMapping("/customers")
    public List<CustomerAccount> getAllAccounts() {
        List<CustomerAccount> accounts = service.getAll();
        log.info("Request a list of all customer accounts. Total count: "+ accounts.size());
        return accounts;
    }

    @DeleteMapping("/customers/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAccount(@PathVariable String id) {
        if (!service.existsById(id)) {
            log.warn("An attempt was made to delete a nonexistent resource. Customer ID: " + id);
            throw new CustomerAccountNotFoundException();
        }
        service.deleteById(id);
        log.info("Account with ID '"+ id +"' deleted");
    }
}
