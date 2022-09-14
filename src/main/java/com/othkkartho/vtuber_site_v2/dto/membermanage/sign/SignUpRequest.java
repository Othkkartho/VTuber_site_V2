package com.othkkartho.vtuber_site_v2.dto.membermanage.sign;

import com.othkkartho.vtuber_site_v2.domain.User.Member;
import com.othkkartho.vtuber_site_v2.domain.User.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Data
@AllArgsConstructor
public class SignUpRequest {
    private String email;
    private String password;
    private String nickname;
    private Long point;
    private String address;
    private String introduce;
    private String provider;
    public static Member toEntity(SignUpRequest req, Role role, PasswordEncoder encoder) {
        return new Member(req.email, encoder.encode(req.password), req.nickname, req.point, req.address, req.introduce, req.provider, List.of(role));
    }
}
