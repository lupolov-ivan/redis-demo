package com.example.redisdemo.controller;

import com.example.redisdemo.exceptions.CustomerAccountNotFoundException;
import com.example.redisdemo.model.CustomerAccount;
import com.example.redisdemo.service.CustomerAccountService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

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
        return ResponseEntity.created(uriBuilder.build(account.getId())).build();
    }

    @PutMapping("/customers/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateAccount(@RequestBody CustomerAccount updateData, @PathVariable String id) {
        Optional<CustomerAccount> maybeAccount = service.findById(id);
        CustomerAccount account = maybeAccount.orElseThrow(CustomerAccountNotFoundException::new);

        account.setFirstName(updateData.getFirstName());
        account.setLastName(updateData.getLastName());
        account.setAge(updateData.getAge());

        service.save(account);
    }

    @GetMapping("/customers/{id}")
    public CustomerAccount getAccountById(@PathVariable String id) {
        Optional<CustomerAccount> maybeAccount = service.findById(id);
        return maybeAccount.orElseThrow(CustomerAccountNotFoundException::new);
    }

    @GetMapping("/customers")
    public List<CustomerAccount> getAllAccounts() {
        return service.getAll();
    }

    @DeleteMapping("/customers/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAccount(@PathVariable String id) {
        if (!service.existsById(id)) {
            throw new CustomerAccountNotFoundException();
        }

        service.deleteById(id);
    }
}
