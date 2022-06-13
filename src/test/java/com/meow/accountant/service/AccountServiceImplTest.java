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
    void getAccountByID() {
        List<Account> result = accountService.getAccountByID(1);
        Assertions.assertEquals(1, result.get(0).getId());
        Assertions.assertEquals("医疗", result.get(0).getTypename());
        Assertions.assertEquals(2131165429, result.get(0).getSImageId());
        Assertions.assertEquals(50, result.get(0).getMoney());
        Assertions.assertEquals("2022年05月16日 00:00", result.get(0).getTime());
        Assertions.assertEquals(2022, result.get(0).getYear());
        Assertions.assertEquals(5, result.get(0).getMonth());
        Assertions.assertEquals(16, result.get(0).getDay());
        Assertions.assertEquals(0, result.get(0).getKind());
        Assertions.assertEquals("626e437b9dc68d14cdb4e2f6", result.get(0).getUserid());
        Assertions.assertNotNull(result.get(0).getBeizhu());
    }

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
        Assertions.assertNotEquals(0, result.get(0).getTotal());
        Assertions.assertNotNull(result.get(0).getTypename());
        Assertions.assertNotEquals(0, result.get(0).getSImageId());
        Assertions.assertNotEquals(-1, result.get(0).getRatio());
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
        Assertions.assertEquals(YEAR, result.get(0).getYear());
        Assertions.assertEquals(MONTH, result.get(0).getMonth());
        Assertions.assertNotEquals(0, result.get(0).getDay());
        Assertions.assertNotEquals(0, result.get(0).getSummoney());
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

    @Test
    void getBudgetRecordByUserid() {
        accountService.insertBudget(USERID, 1600);
        List<Budget> result = accountService.getBudgetRecordByUserid(USERID);
        Assertions.assertEquals(USERID, result.get(0).getUserid());
        Assertions.assertEquals(1600, result.get(0).getBudget());
        Assertions.assertNotNull(result.get(0).getTimest());
    }
}