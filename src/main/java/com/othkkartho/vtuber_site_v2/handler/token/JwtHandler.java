package com.othkkartho.vtuber_site_v2.handler.token;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtHandler {
    private String type = "Bearer ";

    public String createToken(String secret, String subject, long maxAgeSeconds) {
        Date now = new Date();
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());
        return type + Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + maxAgeSeconds * 1000L))
//                .signWith(SignatureAlgorithm.HS256, encodedKey)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public String extractSubject(String encodedKey, String token) {
        return parse(encodedKey, token).getBody().getSubject();
    }

    public boolean validate(String encodedKey, String token) {
        try {
            parse(encodedKey, token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    private Jws<Claims> parse(String secret, String token) {
//        return Jwts.parser()
//                .setSigningKey(keys)
//                .parseClaimsJws(untype(token));
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build().parseClaimsJws(untype(token));
    }

    private String untype(String token) {
        return token.substring(type.length());
    }
    // 즐겨찾기 4번째에 설명 있음. 주석의 코드들은 Deprecated이 선언되었기에 대체 코드를 만듬.
    // 나중에 정확한 키 선언 방법을 공부한 후 코드를 재 변경할 예정(변수로 받는 encodekey나 keys의 값들이 어떻게 들어오는지 확인해야 함)
}
