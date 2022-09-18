package com.othkkartho.vtuber_site_v2.factory.dto;

import com.othkkartho.vtuber_site_v2.dto.membermanage.sign.SignUpRequest;

public class SignUpRequestFactory {
    public static SignUpRequest createSignUpRequest() {
        return new SignUpRequest("email@email.com", "123456a!", "nickname", 0L, "", "local");
    }

    public static SignUpRequest createSignUpRequest(String email, String password, String nickname, Long point, String introduce, String provider) {
        return new SignUpRequest(email, password, nickname, point, introduce, provider);
    }

    public static SignUpRequest createSignUpRequestWithEmail(String email) {
        return new SignUpRequest(email, "123456a!", "nickname", 0L, "", "local");
    }

    public static SignUpRequest createSignUpRequestWithPassword(String password) {
        return new SignUpRequest("email@email.com", password, "nickname", 0L, "", "local");
    }

    public static SignUpRequest createSignUpRequestWithNickname(String nickname) {
        return new SignUpRequest("email@email.com", "123456a!", nickname, 0L, "", "local");
    }
}
