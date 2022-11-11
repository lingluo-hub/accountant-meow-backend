package com.meow.accountant.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.meow.accountant.entity.Budget;
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
 * @Description BudgetController 单元测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
class BudgetControllerTest {

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
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v2/index-budget")
                        .param("userid", USERID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void budget() throws Exception {

        Budget budget = new Budget();
        budget.setUserid(USERID);
        budget.setBudget(1500.0);

        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String insertRequestJson = ow.writeValueAsString(budget);

        //insert
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v2/budget")
                        .contentType(MediaType.APPLICATION_JSON).content(insertRequestJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        //update
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v2/budget")
                        .param("userid", USERID)
                        .param("budget", "1800")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}