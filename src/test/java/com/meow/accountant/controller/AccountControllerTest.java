package com.meow.accountant.controller;

import com.ejlchina.searcher.MapSearcher;
import com.meow.accountant.service.AccountServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

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

        verify(accountService, times(1)).getBudget(USERID);
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
}