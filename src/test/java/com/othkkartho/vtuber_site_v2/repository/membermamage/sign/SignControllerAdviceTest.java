package com.othkkartho.vtuber_site_v2.repository.membermamage.sign;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.othkkartho.vtuber_site_v2.advice.ExceptionAdvice;
import com.othkkartho.vtuber_site_v2.controller.sign.SignController;
import com.othkkartho.vtuber_site_v2.dto.membermanage.sign.SignInRequest;
import com.othkkartho.vtuber_site_v2.dto.membermanage.sign.SignUpRequest;
import com.othkkartho.vtuber_site_v2.exception.*;
import com.othkkartho.vtuber_site_v2.service.membermanage.SignService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class SignControllerAdviceTest {
    @InjectMocks SignController signController;
    @Mock SignService signService;
    MockMvc mockMvc;
    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void beforeEach() {
        mockMvc = MockMvcBuilders.standaloneSetup(signController).setControllerAdvice(new ExceptionAdvice()).build();
    }

    @Test
    void signInLoginFailureExceptionTest() throws Exception {
        // given
        SignInRequest req = new SignInRequest("email@email.com", "123456a!");
        given(signService.signIn(any())).willThrow(LoginFailureException.class);

        // when, then
        mockMvc.perform(
                        post("/api/sign-in")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void signInMethodArgumentNotValidExceptionTest() throws Exception {
        // given
        SignInRequest req = new SignInRequest("email", "1234567");

        // when, then
        mockMvc.perform(
                        post("/api/sign-in")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void signUpMemberEmailAlreadyExistsExceptionTest() throws Exception {
        // given
        SignUpRequest req = new SignUpRequest("email@email.com", "123456a!", "nickname", 0L, "", "local");
        doThrow(MemberEmailAlreadyExistsException.class).when(signService).signUp(any());

        // when, then
        mockMvc.perform(
                        post("/api/sign-up")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isConflict());
    }

    @Test
    void signUpMemberNicknameAlreadyExistsExceptionTest() throws Exception {
        // given
        SignUpRequest req = new SignUpRequest("email@email.com", "123456a!", "nickname", 0L, "", "local");
        doThrow(MemberNicknameAlreadyExistsException.class).when(signService).signUp(any());

        // when, then
        mockMvc.perform(
                        post("/api/sign-up")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isConflict());
    }

    @Test
    void signUpRoleNotFoundExceptionTest() throws Exception {
        // given
        SignUpRequest req = new SignUpRequest("email@email.com", "123456a!", "nickname", 0L, "", "local");
        doThrow(RoleNotFoundException.class).when(signService).signUp(any());

        // when, then
        mockMvc.perform(
                        post("/api/sign-up")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isNotFound());
    }

    @Test
    void signUpMethodArgumentNotValidExceptionTest() throws Exception {
        // given
        SignUpRequest req = new SignUpRequest("", "", "", 0L, "", "");

        // when, then
        mockMvc.perform(
                        post("/api/sign-up")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void refreshTokenAuthenticationEntryPointException() throws Exception { // 1
        // given
        given(signService.refreshToken(anyString())).willThrow(AuthenticationEntryPointException.class);

        // when, then
        mockMvc.perform(
                        post("/api/refresh-token")
                                .header("Authorization", "refreshToken"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.code").value(401));
    }

    @Test
    void refreshTokenMissingRequestHeaderException() throws Exception { // 2
        // given, when, then
        mockMvc.perform(
                        post("/api/refresh-token"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(409));
    }
}
