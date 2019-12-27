package com.example.redisdemo.service;

import com.example.redisdemo.model.CustomerAccount;
import com.example.redisdemo.repository.CustomerAccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomerAccountService {

    private final CustomerAccountRepository repository;

    public void save(CustomerAccount account) {
        repository.save(account);
    }

    public List<CustomerAccount> getAll() {
        return (List<CustomerAccount>) repository.findAll();
    }

    public void deleteById(String id) {
        repository.deleteById(id);
    }


    public Optional<CustomerAccount> findById(String id) {
        return repository.findById(id);
    }

    public boolean existsById(String id) {
        return repository.existsById(id);
    }
}
