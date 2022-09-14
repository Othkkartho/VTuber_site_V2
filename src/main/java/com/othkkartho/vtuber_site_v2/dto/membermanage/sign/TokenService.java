package com.othkkartho.vtuber_site_v2.dto.membermanage.sign;

import com.othkkartho.vtuber_site_v2.handler.token.JwtHandler;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final JwtHandler jwtHandler;

    @Value("${jwt.max-age.access}")
    private long accessTokenMaxAgeSeconds;

    @Value("${jwt.max-age.refresh}")
    private long refreshTokenMaxAgeSeconds;

    private final SecretKey accessKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    private final SecretKey refreshKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public String createAccessToken(String subject) {
        return jwtHandler.createToken(accessKey, subject, accessTokenMaxAgeSeconds);
    }

    public String createRefreshToken(String subject) {
        return jwtHandler.createToken(refreshKey, subject, refreshTokenMaxAgeSeconds);
    }
}
