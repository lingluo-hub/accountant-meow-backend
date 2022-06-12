package com.meow.accountant;

import com.meow.accountant.entity.Account;
import com.meow.accountant.service.AccountServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class AccountantMeowBackendApplicationTests {

    @Autowired
    private AccountServiceImpl accountService;

    private static final String USERID = "627875eda49a5d62769d415a";

    @Test
    void contextLoads() {
        List<Account> result = accountService.getAccountByUserid(USERID);
        Assertions.assertFalse(result.isEmpty());
    }

}
