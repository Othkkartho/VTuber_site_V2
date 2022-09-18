package com.othkkartho.vtuber_site_v2.factory.dto;

import com.othkkartho.vtuber_site_v2.dto.membermanage.sign.SignInResponse;

public class SignInResponseFactory {
    public static SignInResponse createSignInResponse(String accessToken, String refreshToken) {
        return new SignInResponse(accessToken, refreshToken);
    }
}
