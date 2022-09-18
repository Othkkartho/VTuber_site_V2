package com.othkkartho.vtuber_site_v2.repository.membermamage.member;

import com.othkkartho.vtuber_site_v2.domain.User.Member;
import com.othkkartho.vtuber_site_v2.dto.membermanage.sign.SignInRequest;
import com.othkkartho.vtuber_site_v2.dto.membermanage.sign.SignInResponse;
import com.othkkartho.vtuber_site_v2.exception.MemberNotFoundException;
import com.othkkartho.vtuber_site_v2.repository.member.MemberRepository;
import com.othkkartho.vtuber_site_v2.service.membermanage.SignService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static com.othkkartho.vtuber_site_v2.factory.dto.SignInRequestFactory.createSignInRequest;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(value = "test")
@Transactional
public class MemberControllerIntegrationTest {
    @Autowired WebApplicationContext context;
    @Autowired MockMvc mockMvc;

    @Autowired TestInitDB initDB;
    @Autowired SignService signService;
    @Autowired MemberRepository memberRepository;

    @BeforeEach
    void beforeEach() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(SecurityMockMvcConfigurers.springSecurity()).build();
        initDB.initDB();
    }

    @Test
    void readTest() throws Exception {
        // given
        Member member = memberRepository.findByEmail(initDB.getMember1Email()).orElseThrow(MemberNotFoundException::new);

        // when, then
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/members/{id}", member.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void deleteTest() throws Exception {
        // given
        Member member = memberRepository.findByEmail(initDB.getMember1Email()).orElseThrow(MemberNotFoundException::new);
        SignInResponse signInRes = signService.signIn(new SignInRequest(initDB.getMember1Email(), initDB.getPassword()));

        // when, then
        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/api/members/{id}", member.getId()).header("Authorization", signInRes.getAccessToken()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void deleteByAdminTest() throws Exception {
        // given
        Member member = memberRepository.findByEmail(initDB.getMember1Email()).orElseThrow(MemberNotFoundException::new);
        SignInResponse adminSignInRes = signService.signIn(new SignInRequest(initDB.getAdminEmail(), initDB.getPassword()));

        // when, then
        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/api/members/{id}", member.getId()).header("Authorization", adminSignInRes.getAccessToken()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void deleteUnauthorizedByNoneTokenTest() throws Exception {
        // given
        Member member = memberRepository.findByEmail(initDB.getMember1Email()).orElseThrow(MemberNotFoundException::new);

        // when, then
        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/api/members/{id}", member.getId()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/exception/entry-point"));
    }

    @Test
    void deleteAccessDeniedByNotResourceOwnerTest() throws Exception {
        // given
        Member member = memberRepository.findByEmail(initDB.getMember1Email()).orElseThrow(MemberNotFoundException::new);
        SignInResponse attackerSignInRes = signService.signIn(new SignInRequest(initDB.getMember2Email(), initDB.getPassword()));

        // when, then
        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/api/members/{id}", member.getId()).header("Authorization", attackerSignInRes.getAccessToken()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/exception/access-denied"));
    }

    @Test
    void deleteUnauthorizedByRefreshTokenTest() throws Exception {
        // given
        Member member = memberRepository.findByEmail(initDB.getMember1Email()).orElseThrow(MemberNotFoundException::new);
        SignInResponse signInRes = signService.signIn(createSignInRequest(initDB.getMember1Email(), initDB.getPassword()));
        System.out.println("refresh: "+ signInRes.getRefreshToken());
        System.out.println("access: "+ signInRes.getAccessToken());

        // when, then
        mockMvc.perform(
                        delete("/api/members/{id}", member.getId()).header("Authorization", signInRes.getRefreshToken()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/exception/entry-point"));
    }
}
