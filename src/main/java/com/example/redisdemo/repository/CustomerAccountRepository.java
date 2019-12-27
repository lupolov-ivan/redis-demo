package com.example.redisdemo.repository;

import com.example.redisdemo.model.CustomerAccount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerAccountRepository extends CrudRepository<CustomerAccount, String> {
}
