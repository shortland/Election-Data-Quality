package com.electiondataquality.restservice;

import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.everyItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class StateControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void allStatesShouldReturnOkStatus() throws Exception {
        this.mockMvc.perform(get("/allStates")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("ok"));
    }

    @Test
    public void allStatesShouldReturnValidData() throws Exception {
        this.mockMvc.perform(get("/allStates")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray()).andExpect(jsonPath("$.content", hasSize(3)))
                .andExpect(jsonPath("$.content", everyItem(hasKey("geometry"))))
                .andExpect(jsonPath("$.content", everyItem(hasKey("name"))))
                .andExpect(jsonPath("$.content", everyItem(hasKey("id"))))
                .andExpect(jsonPath("$.content", everyItem(hasKey("abreviation"))))
                .andExpect(jsonPath("$.content", everyItem(hasKey("countyIds"))))
                .andExpect(jsonPath("$.content", everyItem(hasKey("congressionalDistrictIds"))));
    }
}
