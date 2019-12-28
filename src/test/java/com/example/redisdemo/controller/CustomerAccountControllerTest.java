package com.example.redisdemo.controller;

import com.example.redisdemo.TestUtils;
import com.example.redisdemo.exceptions.CustomerAccountNotFoundException;
import com.example.redisdemo.model.CustomerAccount;
import com.example.redisdemo.repository.CustomerAccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.http.HttpServletResponse;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
class CustomerAccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CustomerAccountRepository accountRepository;

    @Test
    public void accountShouldBeCreated() throws Exception {
        HttpServletResponse response = mockMvc.perform(post("/customers")
            .contentType("application/json")
            .content(TestUtils.readJsonFromResource("com.example.redisdemo.controller/create_update_account.json")))
            .andExpect(status().isCreated())
            .andExpect(header().string("location", containsString("http://localhost:8080/customers/")))
            .andReturn().getResponse();

        String id = Objects.requireNonNull(response.getHeader("location")).replace("http://localhost:8080/customers/", "");
        assertThat(accountRepository.findById(id).isPresent());
    }

    @Test
    public void accountShouldBeUpdated() throws Exception {
        String accountId = accountRepository.save(new CustomerAccount("Vasya", "Pupkin", 21)).getId();

        mockMvc.perform(put("/customers/{id}", accountId)
                .contentType("application/json")
                .content(TestUtils.readJsonFromResource("com.example.redisdemo.controller/create_update_account.json")))
                .andExpect(status().isNoContent());
        CustomerAccount account = accountRepository.findById(accountId).orElseThrow(CustomerAccountNotFoundException::new);
        assertThat(account.getFirstName()).isEqualTo("John");
        assertThat(account.getLastName()).isEqualTo("Dou");
        assertThat(account.getAge()).isEqualTo(26);
    }

    @Test
    public void updateAccountShouldReturn404() throws Exception {
        String accountId = "unknown_id";

        mockMvc.perform(put("/customers/{id}", accountId)
                .contentType("application/json")
                .content(TestUtils.readJsonFromResource("com.example.redisdemo.controller/create_update_account.json")))
                .andExpect(status().isNotFound());
    }

    @Test
    public void accountShouldBeDeleted() throws Exception {
        String accountId = accountRepository.save(new CustomerAccount("Vasya", "Pupkin", 21)).getId();

        mockMvc.perform(delete("/customers/{id}", accountId))
                .andExpect(status().isNoContent());
        assertThat(accountRepository.findById(accountId).isEmpty());
    }

    @Test
    public void deleteAccountShouldReturn404() throws Exception {
        String accountId = "unknown_id";

        mockMvc.perform(delete("/customers/{id}", accountId))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldFindAccountById() throws Exception {
        String accountId = accountRepository.save(new CustomerAccount("Vasya", "Pupkin", 21)).getId();

        mockMvc.perform(get("/customers/{id}", accountId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is("Vasya")))
                .andExpect(jsonPath("$.lastName", is("Pupkin")))
                .andExpect(jsonPath("$.age", is(21)));
    }

    @Test
    public void shouldFindAllAccounts() throws Exception {
        accountRepository.deleteAll();
        accountRepository.save(new CustomerAccount("Vasya", "Pupkin", 21));
        accountRepository.save(new CustomerAccount("Petya", "Vasichkin", 19));


        mockMvc.perform(get("/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }
}