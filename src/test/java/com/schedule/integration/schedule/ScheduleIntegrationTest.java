package com.schedule.integration.schedule;

import com.schedule.common.config.PasswordEncoder;
import com.schedule.common.entity.User;
import com.schedule.domain.user.model.dto.SessionUser;
import com.schedule.domain.user.repository.UserRepository;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class ScheduleIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private UserRepository userRepository;

    @DisplayName(value = "일정 생성 및 조회 테스트")
    @Test
    void scheduleTest() throws Exception {

        //일정 생성
        //given
        //1. 회원 가입
        User user = new User("seo@jun.com", "이서준", encoder.encode("test"));
        userRepository.save(user);

        MockHttpSession session = new MockHttpSession();
        SessionUser sessionUser = new SessionUser(1L, "seo@jun.com");
        session.setAttribute("loginUser", sessionUser);

        //2. 일정 생성 바디 준비
        String json = "{\"title\":\"일정 제목 테스트\", \"content\": \"일정 내용 테스트\"}";

        //when
        MvcResult createScheduleResult = mockMvc.perform(post("/schedules")
                        .contentType(MediaType.APPLICATION_JSON)
                        .session(session)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.content.title").value("일정 제목 테스트"))
                .andExpect(jsonPath("$.content.content").value("일정 내용 테스트"))
                .andReturn();

        //then
        System.out.println("Create Schedule Body : " + createScheduleResult.getResponse().getContentAsString());

        //일정 조회
        //given

        //when
        MvcResult getScheduleResult = mockMvc.perform(get("/schedules")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("pageNumber", "0")
                        .param("pageSize", "3"))
                .andExpect(status().isOk())
                .andReturn();

        //then
        System.out.println("Get Schedules Body : " + getScheduleResult.getResponse().getContentAsString());
    }
}
