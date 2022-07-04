package com.meow.accountant.entity.response;

import com.meow.accountant.AccountantMeowBackendApplication;
import com.meow.accountant.service.AccountServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AccountantMeowBackendApplication.class)
class ResponseResultTest {

    @Autowired
    private AccountServiceImpl accountService;

    @Test
    void fail() {
        Float result = accountService.getBudget("0");
        ResponseResult<Float> request = ResponseResult.fail(String.valueOf(result));
        Assertions.assertNotNull(request.toString());
        Assertions.assertEquals("null", request.getMessage());
        Assertions.assertEquals("500", request.getStatus());
    }
}