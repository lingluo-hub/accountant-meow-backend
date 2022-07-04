package com.meow.accountant.controller;

import com.ejlchina.searcher.MapSearcher;
import com.meow.accountant.AccountantMeowBackendApplication;
import com.meow.accountant.entity.response.ResponseResult;
import com.meow.accountant.service.AccountServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AccountantMeowBackendApplication.class)
class AccountControllerTest {

    private MockMvc mockMvc;

    private static final String USERID = "627875eda49a5d62769d415a";
    private static final String MONEY = "25";
    private static final String YEAR = "2022";
    private static final String MONTH = "5";
    private static final String DAY = "10";
    
    @MockBean
    private AccountServiceImpl accountService;

    @MockBean
    private MapSearcher mapSearcher;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new AccountController(accountService, mapSearcher)).build();
    }

    @Autowired
    private CachingController cachingController;

    @BeforeEach
    void clearAllCaches() {
        ResponseResult<Void> result = cachingController.clearAllCaches();
        Assertions.assertEquals("success", result.getMessage());
    }

    @Test
    void index() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/accountant-meow/index")
                        .param("userid", USERID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.get("/accountant-meow/index")
                        .param("userid", USERID)
                        .param("page", "2")
                        .param("size", "10")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void insertAccount() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/accountant-meow/insertAccount")
                        .param("userid", USERID)
                        .param("typename", "交通")
                        .param("sImageId", "2131165399")
                        .param("beizhu", "略略略")
                        .param("money", MONEY)
                        .param("time", "2022年05月23日 16:32")
                        .param("year", YEAR)
                        .param("month", MONTH)
                        .param("day", DAY)
                        .param("kind", "0")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getAccountByDate() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/accountant-meow/getAccountByDate")
                        .param("userid", USERID)
                        .param("year", YEAR)
                        .param("month", MONTH)
                        .param("day", DAY)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getAccountByMonth() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/accountant-meow/getAccountByMonth")
                        .param("userid", USERID)
                        .param("year", YEAR)
                        .param("month", MONTH)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getSumMoneyOneDay() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/accountant-meow/getSumMoneyOneDay")
                        .param("userid", USERID)
                        .param("year", YEAR)
                        .param("month", MONTH)
                        .param("day", DAY)
                        .param("kind", "0")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getSumMoneyOneMonth() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/accountant-meow/getSumMoneyOneMonth")
                        .param("userid", USERID)
                        .param("year", YEAR)
                        .param("month", MONTH)
                        .param("kind", "0")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getCountItemOneMonth() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/accountant-meow/getCountItemOneMonth")
                        .param("userid", USERID)
                        .param("year", YEAR)
                        .param("month", MONTH)
                        .param("kind", "0")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getSumMoneyOneYear() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/accountant-meow/getSumMoneyOneYear")
                        .param("userid", USERID)
                        .param("year", YEAR)
                        .param("kind", "0")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void deleteItemFromAccounttbById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/accountant-meow/deleteItemFromAccounttbById")
                        .param("userid", USERID)
                        .param("id", "1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void updateItemFromAccounttbById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/accountant-meow/updateItemFromAccounttbById")
                        .param("id", "1")
                        .param("userid", USERID)
                        .param("typename", "交通")
                        .param("sImageId", "2131165399")
                        .param("beizhu", "地铁")
                        .param("money", MONEY)
                        .param("time", "2022年05月23日 16:32")
                        .param("year", YEAR)
                        .param("month", MONTH)
                        .param("day", DAY)
                        .param("kind", "0")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getYearListFromAccounttb() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/accountant-meow/getYearListFromAccounttb")
                        .param("userid", USERID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getChartListFromAccounttb() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/accountant-meow/getChartListFromAccounttb")
                        .param("userid", USERID)
                        .param("year", YEAR)
                        .param("month", MONTH)
                        .param("kind", "0")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getMaxMoneyOneDayInMonth() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/accountant-meow/getMaxMoneyOneDayInMonth")
                        .param("userid", USERID)
                        .param("year", YEAR)
                        .param("month", MONTH)
                        .param("kind", "0")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getSumMoneyOneDayInMonth() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/accountant-meow/getSumMoneyOneDayInMonth")
                        .param("userid", USERID)
                        .param("year", YEAR)
                        .param("month", MONTH)
                        .param("kind", "0")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getAccountListByRemarkFromAccounttb() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/accountant-meow/getAccountListByRemarkFromAccounttb")
                        .param("userid", USERID)
                        .param("beizhu", "地铁")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getAccountListByTypeFromAccounttb() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/accountant-meow/getAccountListByTypeFromAccounttb")
                        .param("userid", USERID)
                        .param("type", "其他")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getBudget() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/accountant-meow/getBudget")
                        .param("userid", USERID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void insertBudget() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/accountant-meow/insertBudget")
                        .param("userid", USERID)
                        .param("budget", "1600")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}