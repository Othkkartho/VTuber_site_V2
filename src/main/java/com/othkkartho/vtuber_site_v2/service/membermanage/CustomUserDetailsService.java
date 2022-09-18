package com.othkkartho.vtuber_site_v2.service.membermanage;

import com.othkkartho.vtuber_site_v2.domain.User.Member;
import com.othkkartho.vtuber_site_v2.dto.membermanage.member.CustomUserDetails;
import com.othkkartho.vtuber_site_v2.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public CustomUserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        Member member = memberRepository.findById(Long.valueOf(userId))
                .orElseGet(() -> new Member(null, null, null, null, null, null, List.of()));
        return new CustomUserDetails(
                String.valueOf(member.getId()),
                member.getRoles().stream().map(memberRole -> memberRole.getRole())
                        .map(role -> role.getRoleType())
                        .map(roleType -> roleType.toString())
                        .map(SimpleGrantedAuthority::new).collect(Collectors.toSet())
        );
    }
}
