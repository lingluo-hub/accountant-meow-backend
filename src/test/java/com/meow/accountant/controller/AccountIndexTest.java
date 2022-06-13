package com.meow.accountant.controller;

import com.ejlchina.searcher.MapSearcher;
import com.meow.accountant.AccountantMeowBackendApplication;
import com.meow.accountant.service.AccountServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AccountantMeowBackendApplication.class)
class AccountIndexTest {

    private MockMvc mvc;

    private static final String USERID = "627875eda49a5d62769d415a";

    @MockBean
    private AccountServiceImpl accountService;

    @MockBean
    private MapSearcher mapSearcher;

    @BeforeEach
    public void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(new AccountController(accountService, mapSearcher)).build();
    }

    @Test
    void index() throws Exception {
        RequestBuilder request;
        request = get("/accountant-meow/index")
                .param("userid", USERID);
        mvc.perform(request)
                .andExpect(status().isOk());
        request = get("/accountant-meow/index")
                .param("userid", USERID)
                .param("page","2")
                .param("size","10");
        mvc.perform(request)
                .andExpect(status().isOk());
    }
}