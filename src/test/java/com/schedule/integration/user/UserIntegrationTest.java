package com.schedule.integration.user;

import com.schedule.common.config.PasswordEncoder;
import com.schedule.common.entity.User;
import com.schedule.domain.user.model.dto.SessionUser;
import com.schedule.domain.user.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class UserIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder encoder;


    @DisplayName(value = "유저 생성 테스트")
    @Test
    void signupTest() throws Exception {

        //given
        String json = "{\"email\":\"test@test.com\"," +
                "    \"username\":\"이준범\",\n" +
                "    \"password\":\"test\"\n" +
                "}";

        //when //then
        ResultActions result = mockMvc.perform(post("/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.content.email").value("test@test.com"))
                .andExpect(jsonPath("$.content.username").value("이준범"));

        // 응답 바디 직렬화후 찍어보기
        String resultJson = result.andReturn().getResponse().getContentAsString();
        System.out.println("회원 가입 Response : " + resultJson);
    }

    @DisplayName(value = "로그인 테스트")
    @Test
    void loginTest() throws Exception {

        //given
        //1. 회원가입 먼저 진행
        User user = new User("seo@jun.com", "이서준", encoder.encode("test"));
        userRepository.save(user);

        //2. 로그인 요청 바디
        String json = "{ \"email\":\"seo@jun.com\", \"password\":\"test\"}";

        //when 로그인 수행
        MvcResult result = mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.email").value("seo@jun.com"))
                .andReturn();

        //then 세션 확인
        MockHttpSession session = (MockHttpSession) result.getRequest().getSession();

        Assertions.assertNotNull(session);  //세션 있나요
        SessionUser sessionUser = (SessionUser) session.getAttribute("loginUser");
        Assertions.assertNotNull(sessionUser);  //DTO 있나요

        //1. 이메일 확인
        assertEquals("seo@jun.com", sessionUser.getEmail());
    }


    @DisplayName(value = "로그아웃")
    @Test
    void logoutTest() throws Exception {

        //given
        //1. 세션 만들기
        MockHttpSession session = new MockHttpSession();
        SessionUser sessionUser = new SessionUser(1L, "seo@jun.com");
        session.setAttribute("loginUser", sessionUser);

        //when
        MvcResult mvcResult = mockMvc.perform(post("/logout")
                        .session(session))  // 세션 주입
                .andExpect(status().isNoContent())
                .andReturn();

        //then
        HttpSession sessionLogout = mvcResult.getRequest().getSession(false);
        assertNull(sessionLogout);
    }
}
