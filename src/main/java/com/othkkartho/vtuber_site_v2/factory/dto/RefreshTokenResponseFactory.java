package com.othkkartho.vtuber_site_v2.factory.dto;

import com.othkkartho.vtuber_site_v2.dto.membermanage.sign.RefreshTokenResponse;

public class RefreshTokenResponseFactory {
    public static RefreshTokenResponse createRefreshTokenResponse(String accessToken) {
        return new RefreshTokenResponse(accessToken);
    }
}
