package com.meow.accountant.controller;

import com.ejlchina.searcher.MapSearcher;
import com.meow.accountant.service.AccountServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author 凌洛
 * @Description Controller层 Junit5 单元测试
 */
@ExtendWith(SpringExtension.class)
@WebMvcTest(value = AccountController.class)
class AccountControllerTest {

    private static final String USERID = "627875eda49a5d62769d415a";
    private static final float MONEY = 25;
    private static final int YEAR = 2022;
    private static final int MONTH = 5;
    private static final int DAY = 10;

    @Autowired(required = false)
    private MockMvc mockMvc;

    @MockBean
    private AccountServiceImpl accountService;

    @MockBean
    private MapSearcher mapSearcher;

    /**
     * 测试按userid获取记录
     */
    @Test
    void getBudget() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/accountant-meow/getBudget?userid=" + USERID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // null 结果
        mockMvc.perform(MockMvcRequestBuilders.get("/accountant-meow/getBudget?userid=0")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(accountService, times(1)).getBudget(USERID);
        verify(accountService, times(1)).getBudget("0");
        Assertions.assertEquals(0, accountService.getBudget("0"));
        Assertions.assertFalse(accountService.getBudget(USERID).isNaN());
    }

    /**
     * 测试按日期获取记录
     */
    @Test
    void getAccountByDate() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/accountant-meow/getAccountByDate?year=" + YEAR
                                + "&month=" + MONTH + "&day=" + DAY + "&userid=" + USERID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(accountService, times(1)).getAccountByDate(YEAR, MONTH, DAY, USERID);

    }

    /**
     * 测试插入记录
     */
    @Test
    void insertAccount() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/accountant-meow/insertAccount?typename=交通&sImageId=2131165399"
                                + "&beizhu=略略略" + "&money=" + MONEY + "&time=2022年05月23日 16:32"
                                + "&year=" + YEAR + "&month=" + MONTH + "&day=" + DAY
                                + "&kind=0" + "&userid=" + USERID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(accountService, times(1)).insertAccount("交通", 2131165399,
                "略略略", MONEY, "2022年05月23日 16:32", YEAR, MONTH, DAY, 0, USERID);
    }

    /**
     * 测试全局检索
     */
    @Test
    void index() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/accountant-meow/index?userid=" + USERID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getAccountByMonth() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/accountant-meow/getAccountByMonth?year=" + YEAR +
                                "&month=" + MONTH + "&userid=" + USERID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(accountService, times(1)).getAccountByMonth(YEAR, MONTH, USERID);
    }

    @Test
    void getSumMoneyOneDay() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/accountant-meow/getSumMoneyOneDay?year=" + YEAR +
                                "&month=" + MONTH + "&day=" + DAY + "&kind=0" + "&userid=" + USERID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // null 结果
        mockMvc.perform(MockMvcRequestBuilders.get("/accountant-meow/getSumMoneyOneDay?year=" + YEAR +
                                "&month=" + MONTH + "&day=" + DAY + "&kind=0" + "&userid=0")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(accountService, times(1)).getSumMoneyOneDay(YEAR, MONTH, DAY, 0, USERID);
        Assertions.assertFalse(accountService.getSumMoneyOneDay(YEAR, MONTH, DAY, 0, USERID).isNaN());
        verify(accountService, times(1)).getSumMoneyOneDay(YEAR, MONTH, DAY, 0, "0");
        Assertions.assertEquals(0, accountService.getSumMoneyOneDay(YEAR, MONTH, DAY, 0, "0"));
    }

    @Test
    void getSumMoneyOneMonth() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/accountant-meow/getSumMoneyOneMonth?year=" + YEAR +
                                "&month=" + MONTH + "&kind=0" + "&userid=" + USERID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.get("/accountant-meow/getSumMoneyOneMonth?year=" + YEAR +
                                "&month=1" + "&kind=0" + "&userid=" + USERID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(accountService, times(1)).getSumMoneyOneMonth(YEAR, MONTH, 0, USERID);
        verify(accountService, times(1)).getSumMoneyOneMonth(YEAR, 1, 0, USERID);
        Assertions.assertEquals(0, accountService.getSumMoneyOneMonth(YEAR, 1, 0, USERID));
    }

    @Test
    void getCountItemOneMonth() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/accountant-meow/getCountItemOneMonth?year=" + YEAR +
                                "&month=" + MONTH + "&kind=0" + "&userid=" + USERID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(accountService, times(1)).getCountItemOneMonth(YEAR, MONTH, 0, USERID);
    }

    @Test
    void getSumMoneyOneYear() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/accountant-meow/getSumMoneyOneYear?year=" + YEAR + "&kind=0" + "&userid=" + USERID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(accountService, times(1)).getSumMoneyOneYear(YEAR, 0, USERID);
    }

    @Test
    void deleteItemFromAccounttbById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/accountant-meow/deleteItemFromAccounttbById?id=" + 8 + "&userid=" + USERID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(accountService, times(1)).deleteItemFromAccounttbById(8, USERID);
    }

    @Test
    void updateItemFromAccounttbById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/accountant-meow/updateItemFromAccounttbById?id=16&typename=交通&sImageId=2131165399"
                                + "&beizhu=地铁" + "&money=" + MONEY + "&time=2022年05月23日 16:32"
                                + "&year=" + YEAR + "&month=" + MONTH + "&day=" + DAY
                                + "&kind=0" + "&userid=" + USERID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(accountService, times(1)).updateItemFromAccounttbById(16, "交通", 2131165399,
                "地铁", MONEY, "2022年05月23日 16:32", YEAR, MONTH, DAY, 0, USERID);
    }

    @Test
    void getYearListFromAccounttb() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/accountant-meow/getYearListFromAccounttb?userid=" + USERID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(accountService, times(1)).getYearListFromAccounttb(USERID);
    }

    @Test
    void deleteAllAccount() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/accountant-meow/deleteAllAccount?userid=" + USERID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(accountService, times(1)).deleteAllAccount(USERID);
    }

    @Test
    void getChartListFromAccounttb() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/accountant-meow/getChartListFromAccounttb?year=" + YEAR +
                                "&month=" + MONTH + "&kind=0" + "&userid=" + USERID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(accountService, times(1)).getChartListFromAccounttb(YEAR, MONTH, 0, USERID);
    }

    @Test
    void getMaxMoneyOneDayInMonth() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/accountant-meow/getMaxMoneyOneDayInMonth?year=" + YEAR +
                                "&month=" + MONTH + "&kind=0" + "&userid=" + USERID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(accountService, times(1)).getMaxMoneyOneDayInMonth(YEAR, MONTH, 0, USERID);
    }

    @Test
    void getSumMoneyOneDayInMonth() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/accountant-meow/getSumMoneyOneDayInMonth?year=" + YEAR +
                                "&month=" + MONTH + "&kind=0" + "&userid=" + USERID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(accountService, times(1)).getSumMoneyOneDayInMonth(YEAR, MONTH, 0, USERID);
    }

    @Test
    void getAccountListByRemarkFromAccounttb() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/accountant-meow/getAccountListByRemarkFromAccounttb?beizhu=" + "地铁" + "&userid=" + USERID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(accountService, times(1)).getAccountListByRemarkFromAccounttb("地铁", USERID);
    }

    @Test
    void getAccountListByTypeFromAccounttb() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/accountant-meow/getAccountListByTypeFromAccounttb?type=" + "其他" + "&userid=" + USERID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(accountService, times(1)).getAccountListByTypeFromAccounttb("其他", USERID);
    }

    @Test
    void insertBudget() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/accountant-meow/insertBudget?budget=" + "1500" + "&userid=" + USERID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(accountService, times(1)).insertBudget(USERID, 1500);
    }
}