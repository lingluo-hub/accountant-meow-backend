package com.meow.accountant.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.meow.accountant.entity.Accounttb;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author 凌洛
 * @Description AccountController 单元测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
class AccountControllerTest {

    private static final String USERID = "627875eda49a5d62769d415a";

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    void index() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v2/index-account")
                        .param("userid", USERID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    void account() throws Exception {

        Accounttb accounttb = new Accounttb();
        accounttb.setUserid(USERID);
        accounttb.setKind(0);
        accounttb.setMoney(25.0);
        accounttb.setYear(2022);
        accounttb.setMonth(11);
        accounttb.setDay(11);
        accounttb.setBeizhu("test");
        accounttb.setSimageid(2131165399);
        accounttb.setTypename("交通");

        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String insertRequestJson = ow.writeValueAsString(accounttb);

        //insert
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v2/account")
                        .contentType(MediaType.APPLICATION_JSON).content(insertRequestJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        //update
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v2/account")
                        .param("id", "1")
                        .param("money", "35")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    void deleteAccount() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v2/del-account/{id}", 18)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v2/del-account/{id}", 100)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}