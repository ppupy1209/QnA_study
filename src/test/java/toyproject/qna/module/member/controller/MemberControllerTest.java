package toyproject.qna.module.member.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import toyproject.qna.module.member.dto.MemberPostDto;
import toyproject.qna.module.member.service.MemberService;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = MemberController.class)
class MemberControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private MemberService memberService;


    @DisplayName("멤버를 등록한다.")
    @Test
    void postMember() throws Exception {
        // given
        MemberPostDto request = MemberPostDto.builder()
                .name("홍길동")
                .age(10)
                .build();

        // when & then
        mockMvc.perform(post("/members")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @DisplayName("멤버를 등록할 때 이름은 필수값이다.")
    @Test
    void postMemberWithoutName() throws Exception {
        // given
        MemberPostDto request = MemberPostDto.builder()
                .age(10)
                .build();

        // when & then
        mockMvc.perform(post("/members")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.fieldErrors[0].field").value("name"))
                .andExpect(jsonPath("$.fieldErrors[0].reason").value("이름은 필수 항목입니다."));
    }

    @DisplayName("멤버를 등록할 때 나이의 최솟값은 1이다.")
    @Test
    void postMemberWith0Age() throws Exception {
        // given
        MemberPostDto request = MemberPostDto.builder()
                .name("홍길동")
                .age(0)
                .build();

        // when & then
        mockMvc.perform(post("/members")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.fieldErrors[0].field").value("age"))
                .andExpect(jsonPath("$.fieldErrors[0].rejectedValue").value(0))
                .andExpect(jsonPath("$.fieldErrors[0].reason").value("나이 최솟값은 1 입니다."));
    }


}