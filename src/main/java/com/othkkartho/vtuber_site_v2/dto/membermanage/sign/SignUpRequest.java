package com.othkkartho.vtuber_site_v2.dto.membermanage.sign;

import com.othkkartho.vtuber_site_v2.domain.User.Member;
import com.othkkartho.vtuber_site_v2.domain.User.Role;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
public class SignUpRequest {
    @Email(message = "이메일 형식을 맞춰주세요.")
    @NotBlank(message = "이메일을 입력해주세요.")
    private String email;
    @NotBlank(message = "비밀번호를 입력하해주세요.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&.])[A-Za-z[0-9]$@$!%*#?&.]{8,20}$",
    message = "비밀번호는 최소 8자리면서 1개 이상의 알파벳, 숫자, 특수문자를 포함해야 합니다.")
    private String password;
    @NotBlank(message = "닉네임을 입력해 주세요.")
    @Size(min=2, message = "닉네임이 너무 짧습니다.")
    @Pattern(regexp = "^[A-Za-z가-힣1-9]+$", message = "닉네임은 한글,알파벳과 숫자만 입력해주세요.")
    private String nickname;
    private Long point;
    private String introduce;
    private String provider;
    public SignUpRequest() {}
    public static Member toEntity(SignUpRequest req, Role role, PasswordEncoder encoder) {
        return new Member(req.email, encoder.encode(req.password), req.nickname, req.point, req.introduce, req.provider, List.of(role));
    }
}
