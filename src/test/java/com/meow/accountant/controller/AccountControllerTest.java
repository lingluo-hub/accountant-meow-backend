package com.meow.accountant.controller;

import com.ejlchina.searcher.MapSearcher;
import com.meow.accountant.AccountantMeowBackendApplication;
import com.meow.accountant.entity.Account;
import com.meow.accountant.entity.BarCharItem;
import com.meow.accountant.entity.ChartItem;
import com.meow.accountant.entity.response.ResponseResult;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author 凌洛
 * @Description Controller层 Junit5 单元测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AccountantMeowBackendApplication.class)
class AccountControllerTest {

    private static final String USERID = "627875eda49a5d62769d415a";
    private static final float MONEY = 25;
    private static final int YEAR = 2022;
    private static final int MONTH = 5;
    private static final int DAY = 10;

    @Autowired
    private AccountController accountController;

    @MockBean
    private MapSearcher mapSearcher;

    /**
     * 测试按userid获取记录
     */
    @Test
    void getBudget() {
        ResponseResult<Float> result = accountController.getBudget(USERID);
        ResponseResult<Float> resultnull = accountController.getBudget("0");
        Assertions.assertEquals("success", result.getMessage());
        Assertions.assertEquals(1500, result.getData());
        Assertions.assertNull(resultnull.getData());
    }

    /**
     * 测试按日期获取记录
     */
    @Test
    void getAccountByDate() {
        ResponseResult<List<Account>> result = accountController.getAccountByDate(YEAR, MONTH, DAY, USERID);
        Assertions.assertEquals("success", result.getMessage());
    }

    /**
     * 测试插入记录
     */
    @Test
    void insertAccount() {
        ResponseResult<List<Account>> result = accountController.insertAccount("交通", 2131165399,
                "略略略", MONEY, "2022年05月23日 16:32", YEAR, MONTH, DAY, 0, USERID);
        Assertions.assertEquals("success", result.getMessage());
    }

    @Test
    void getAccountByMonth() {
        ResponseResult<List<Account>> result = accountController.getAccountByMonth(YEAR, MONTH, USERID);
        Assertions.assertEquals("success", result.getMessage());
    }

    @Test
    void getSumMoneyOneDay() {
        ResponseResult<Float> result = accountController.getSumMoneyOneDay(YEAR, MONTH, DAY, 0, USERID);
        ResponseResult<Float> resultnull = accountController.getSumMoneyOneDay(YEAR, MONTH, DAY, 0, "0");
        Assertions.assertEquals("success", result.getMessage());
        Assertions.assertNull(resultnull.getData());
    }

    @Test
    void getSumMoneyOneMonth() {
        ResponseResult<Float> result = accountController.getSumMoneyOneMonth(YEAR, MONTH, 0, USERID);
        ResponseResult<Float> resultnull = accountController.getSumMoneyOneMonth(YEAR, 1, 0, USERID);
        Assertions.assertEquals("success", result.getMessage());
        Assertions.assertNull(resultnull.getData());
    }

    @Test
    void getCountItemOneMonth() {
        ResponseResult<Integer> result = accountController.getCountItemOneMonth(YEAR, MONTH, 0, USERID);
        Assertions.assertEquals("success", result.getMessage());
    }

    @Test
    void getSumMoneyOneYear() {
        ResponseResult<Float> result = accountController.getSumMoneyOneYear(YEAR, 0, USERID);
        Assertions.assertEquals("success", result.getMessage());
    }

    @Test
    void updateItemFromAccounttbById() {
        ResponseResult<List<Account>> result = accountController.updateItemFromAccounttbById(16, "交通", 2131165399,
                "地铁", MONEY, "2022年05月23日 16:32", YEAR, MONTH, DAY, 0, USERID);
        Assertions.assertEquals("success", result.getMessage());
    }

    @Test
    void getYearListFromAccounttb() {
        ResponseResult<List<Integer>> result = accountController.getYearListFromAccounttb(USERID);
        Assertions.assertEquals("success", result.getMessage());
    }

    @Test
    void getChartListFromAccounttb() {
        ResponseResult<List<ChartItem>> result = accountController.getChartListFromAccounttb(YEAR, MONTH, 0, USERID);
        Assertions.assertEquals("success", result.getMessage());
    }

    @Test
    void getMaxMoneyOneDayInMonth() {
        ResponseResult<List<Float>> result = accountController.getMaxMoneyOneDayInMonth(YEAR, MONTH, 0, USERID);
        Assertions.assertEquals("success", result.getMessage());
    }

    @Test
    void getSumMoneyOneDayInMonth() {
        ResponseResult<List<BarCharItem>> result = accountController.getSumMoneyOneDayInMonth(YEAR, MONTH, 0, USERID);
        Assertions.assertEquals("success", result.getMessage());
    }

    @Test
    void getAccountListByRemarkFromAccounttb() {
        ResponseResult<List<Account>> result = accountController.getAccountListByRemarkFromAccounttb("地铁", USERID);
        Assertions.assertEquals("success", result.getMessage());
    }

    @Test
    void getAccountListByTypeFromAccounttb() {
        ResponseResult<List<Account>> result = accountController.getAccountListByTypeFromAccounttb("其他", USERID);
        Assertions.assertEquals("success", result.getMessage());
    }

    @Test
    void insertBudget() {
        ResponseResult<Float> result = accountController.insertBudget(USERID, 1500);
        Assertions.assertEquals("success", result.getMessage());
        Assertions.assertEquals(1500, result.getData());
    }

    @Test
    void deleteItemFromAccounttbById() {
        ResponseResult<String> result = accountController.deleteItemFromAccounttbById(8, USERID);
        Assertions.assertEquals("success", result.getMessage());
    }
}