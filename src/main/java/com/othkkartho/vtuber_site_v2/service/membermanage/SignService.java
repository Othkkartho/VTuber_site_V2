package com.othkkartho.vtuber_site_v2.service.membermanage;

import com.othkkartho.vtuber_site_v2.config.security.token.TokenHelper;
import com.othkkartho.vtuber_site_v2.domain.User.Member;
import com.othkkartho.vtuber_site_v2.domain.User.RoleType;
import com.othkkartho.vtuber_site_v2.dto.membermanage.sign.*;
import com.othkkartho.vtuber_site_v2.exception.*;
import com.othkkartho.vtuber_site_v2.repository.member.MemberRepository;
import com.othkkartho.vtuber_site_v2.repository.role.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SignService {
    private final MemberRepository memberRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenHelper accessTokenHelper;
    private final TokenHelper refreshTokenHelper;

    @Transactional
    public void signUp(SignUpRequest req) {
        validateSignUpInfo(req);
        memberRepository.save(SignUpRequest.toEntity(req,
                roleRepository.findByRoleType(RoleType.USER).orElseThrow(RoleNotFoundException::new),
                passwordEncoder));
    }

    @Transactional(readOnly = true)
    public SignInResponse signIn(SignInRequest req) {
        Member member = memberRepository.findByEmail(req.getEmail()).orElseThrow(LoginFailureException::new);
        validatePassword(req, member);
        String subject = createSubject(member);
        String accessToken = accessTokenHelper.createToken(subject);
        String refreshToken = refreshTokenHelper.createToken(subject);
        return new SignInResponse(accessToken, refreshToken);
    }

    public RefreshTokenResponse refreshToken(String rToken) {
        validateRefreshToken(rToken);
        String subject = refreshTokenHelper.extractSubject(rToken);
        String accessToken = accessTokenHelper.createToken(subject);
        return new RefreshTokenResponse(accessToken);
    }

    private void validateRefreshToken(String rToken) {
        if(!refreshTokenHelper.validate(rToken)) {
            throw new AuthenticationEntryPointException();
        }
    }

    private void validateSignUpInfo(SignUpRequest req) {
        if(memberRepository.existsByEmail(req.getEmail()))
            throw new MemberEmailAlreadyExistsException(req.getEmail());
        if(memberRepository.existsByNickname(req.getNickname()))
            throw new MemberNicknameAlreadyExistsException(req.getNickname());
    }

    private void validatePassword(SignInRequest req, Member member) {
        if(!passwordEncoder.matches(req.getPassword(), member.getPassword())) {
            throw new LoginFailureException();
        }
    }

    private String createSubject(Member member) {
        return String.valueOf(member.getId());
    }
}
