package com.othkkartho.vtuber_site_v2.learn.security;

import com.othkkartho.vtuber_site_v2.handler.token.JwtHandler;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;
import java.util.Base64;

import static org.assertj.core.api.Assertions.assertThat;

public class JwtHandlerTest {
    JwtHandler jwtHandler = new JwtHandler();

    @Test
    void createTokenTest() {
        // given, when
        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        String token = createToken(key, "subject", 60L);
        System.out.println(token);

        // then
        assertThat(token).contains("Bearer ");
    }

    @Test
    void extractSubjectTest() {
        // given
        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        String subject = "subject";
        String token = createToken(key, subject, 60L);

        // when
        String extractedSubject = jwtHandler.extractSubject(key, token);

        // then
        assertThat(extractedSubject).isEqualTo(subject);
    }

    @Test
    void validateTest() {
        // given
        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        String token = createToken(key, "subject", 60L);

        // when
        boolean isValid = jwtHandler.validate(key, token);

        // then
        assertThat(isValid).isTrue();
    }

    @Test
    void invalidateByInvalidKeyTest() {
        // given
        SecretKey key1 = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        SecretKey key2 = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        String token = createToken(key1, "subject", 60L);

        // when
        boolean isValid = jwtHandler.validate(key2, token);

        // then
        assertThat(isValid).isFalse();
    }

    @Test
    void invalidateByExpiredTokenTest() {
        // given
        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        String token = createToken(key, "subject", 0L);

        // when
        boolean isValid = jwtHandler.validate(key, token);

        // then
        assertThat(isValid).isFalse();
    }

    private String createToken(SecretKey encodedKey, String subject, long maxAgeSeconds) {
        return jwtHandler.createToken(
                encodedKey,
                subject,
                maxAgeSeconds);
    }
}
