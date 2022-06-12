package com.meow.accountant.service;

import com.meow.accountant.AccountantMeowBackendApplication;
import com.meow.accountant.entity.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AccountantMeowBackendApplication.class)
class AccountServiceImplTest {

    @Autowired
    private AccountServiceImpl accountService;

    private static final String USERID = "627875eda49a5d62769d415a";
    private static final int YEAR = 2022;
    private static final int MONTH = 5;
    private static final int DAY = 10;

    @Test
    void getAccountByUserid() {
        List<Account> result = accountService.getAccountByUserid(USERID);
        Assertions.assertFalse(result.isEmpty());
    }

    @Test
    void getAccountByDate() {
        List<Account> result = accountService.getAccountByDate(YEAR, MONTH, DAY, USERID);
        Assertions.assertFalse(result.isEmpty());
    }

    @Test
    void getAccountByMonth() {
        List<Account> result = accountService.getAccountByMonth(YEAR, MONTH, USERID);
        Assertions.assertFalse(result.isEmpty());
    }

    @Test
    void getSumMoneyOneDay() {
        Float result = accountService.getSumMoneyOneDay(YEAR, MONTH, DAY, 0, USERID);
        Assertions.assertFalse(result.isNaN());
    }

    @Test
    void getSumMoneyOneMonth() {
        Float result = accountService.getSumMoneyOneMonth(YEAR, MONTH, 0, USERID);
        Assertions.assertFalse(result.isNaN());
    }

    @Test
    void getCountItemOneMonth() {
        Integer result = accountService.getCountItemOneMonth(YEAR, MONTH, 0, USERID);
        Assertions.assertNotEquals(0, result);
    }

    @Test
    void getSumMoneyOneYear() {
        Float result = accountService.getSumMoneyOneYear(YEAR, 0, USERID);
        Assertions.assertFalse(result.isNaN());
    }

    @Test
    void getYearListFromAccounttb() {
        List<Integer> result = accountService.getYearListFromAccounttb(USERID);
        Assertions.assertFalse(result.isEmpty());
    }

    @Test
    void getResultFromAccounttb() {
        List<ResultFromAccounttb> result = accountService.getResultFromAccounttb(YEAR, MONTH, 0, USERID);
        Assertions.assertFalse(result.isEmpty());
    }

    @Test
    void getChartListFromAccounttb() {
        List<ChartItem> result = accountService.getChartListFromAccounttb(YEAR, MONTH, 0, USERID);
        List<ChartItem> resultnull = accountService.getChartListFromAccounttb(YEAR, MONTH, 0, "0");
        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals(0, resultnull.size());
    }

    @Test
    void getMaxMoneyOneDayInMonth() {
        List<Float> result = accountService.getMaxMoneyOneDayInMonth(YEAR, MONTH, 0, USERID);
        Assertions.assertFalse(result.isEmpty());
    }

    @Test
    void getResultSumMoneyOneDayInMonth() {
        List<ResultSumMoneyOneDayInMonth> result = accountService.getResultSumMoneyOneDayInMonth(YEAR, MONTH, 0, USERID);
        Assertions.assertFalse(result.isEmpty());
    }

    @Test
    void getSumMoneyOneDayInMonth() {
        List<BarCharItem> result = accountService.getSumMoneyOneDayInMonth(YEAR, MONTH, 0, USERID);
        Assertions.assertFalse(result.isEmpty());
    }

    @Test
    void getAccountListByRemarkFromAccounttb() {
        List<Account> result = accountService.getAccountListByRemarkFromAccounttb("薯片", "626e437b9dc68d14cdb4e2f6");
        Assertions.assertFalse(result.isEmpty());
    }

    @Test
    void getAccountListByTypeFromAccounttb() {
        List<Account> result = accountService.getAccountListByTypeFromAccounttb("其他", USERID);
        Assertions.assertFalse(result.isEmpty());
    }

    @Test
    void getBudget() {
        Float budget = accountService.getBudget(USERID);
        Float budgetnull = accountService.getBudget("0");
        Assertions.assertFalse(budget.isNaN());
        Assertions.assertNull(budgetnull);
    }
}