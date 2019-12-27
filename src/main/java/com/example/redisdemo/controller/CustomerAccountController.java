package com.example.redisdemo.controller;

import com.example.redisdemo.CustomerAccountNotFoundException;
import com.example.redisdemo.model.CustomerAccount;
import com.example.redisdemo.repository.CustomerAccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class CustomerAccountController {

    private final CustomerAccountRepository repository;

    @PostMapping("/customers")
    public void createAccount(@RequestBody CustomerAccount account) {
        repository.save(account);
    }

    @PutMapping("/customers/{id}")
    public void updateAccount(@RequestBody CustomerAccount account, @PathVariable String id) {
        Optional<CustomerAccount> maybeAccount = repository.findById(id);
        CustomerAccount customerAccount = maybeAccount.orElseThrow(CustomerAccountNotFoundException::new);

        customerAccount.setFirstName(account.getFirstName());
        customerAccount.setLastName(account.getLastName());
        customerAccount.setAge(account.getAge());

        repository.save(customerAccount);
    }

    @GetMapping("/customers")
    public List<CustomerAccount> getAllAccounts() {
        return ((List<CustomerAccount>) repository.findAll());
    }

    @DeleteMapping("/customers/{id}")
    public void deleteAccount(@PathVariable String id) {
        repository.deleteById(id);
    }
}
