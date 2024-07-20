package com.hhplus.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hhplus.backend.domain.user.UserRepository;
import com.hhplus.backend.domain.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    MockMvc mockMvc;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository lectureRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private WebApplicationContext wac;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

//    @Test
//    @DisplayName("사용자 포인트 조회 테스트")
//    void getUserPointTest() throws Exception {
//        this.mockMvc
//                .perform(get("/user/point/1"))
//                .andExpect(status().isOk())
//                .andDo(print());
//    }
//
//    @Test
//    @DisplayName("사용자 포인트 충전 테스트")
//    void chargePointTest() throws Exception {
//        this.mockMvc
//                .perform(post("/user/point/1/charge"))
//                .andExpect(status().isOk())
//                .andDo(print());
//    }
}
