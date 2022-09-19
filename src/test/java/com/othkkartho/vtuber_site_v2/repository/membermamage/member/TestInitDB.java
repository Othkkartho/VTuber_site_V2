package com.othkkartho.vtuber_site_v2.repository.membermamage.member;

import com.othkkartho.vtuber_site_v2.domain.User.Member;
import com.othkkartho.vtuber_site_v2.domain.User.Role;
import com.othkkartho.vtuber_site_v2.domain.User.RoleType;
import com.othkkartho.vtuber_site_v2.exception.RoleNotFoundException;
import com.othkkartho.vtuber_site_v2.repository.member.MemberRepository;
import com.othkkartho.vtuber_site_v2.repository.role.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class TestInitDB {
    @Autowired RoleRepository roleRepository;
    @Autowired MemberRepository memberRepository;
    @Autowired PasswordEncoder passwordEncoder;

    private String adminEmail = "admin@admin.com";
    private String member1Email = "member1@member.com";
    private String member2Email = "member2@member.com";
    private String password = "123456a!";

    @Transactional
    public void initDB() {
        initRole();
        initTestAdmin();
        initTestMember();
    }

    private void initRole() {
        roleRepository.saveAll(
                Stream.of(RoleType.values()).map(Role::new).collect(Collectors.toList())
        );
    }

    private void initTestAdmin() {
        memberRepository.save(
                new Member(adminEmail, passwordEncoder.encode(password), "admin", 0L, "", "local",
                        List.of(roleRepository.findByRoleType(RoleType.USER).orElseThrow(RoleNotFoundException::new),
                                roleRepository.findByRoleType(RoleType.ADMIN).orElseThrow(RoleNotFoundException::new)))
        );
    }

    private void initTestMember() {
        memberRepository.saveAll(
                List.of(
                        new Member(member1Email, passwordEncoder.encode(password), "member1", 0L, "", "local",
                                List.of(roleRepository.findByRoleType(RoleType.USER).orElseThrow(RoleNotFoundException::new))),
                        new Member(member2Email, passwordEncoder.encode(password), "member2", 0L, "", "local",
                                List.of(roleRepository.findByRoleType(RoleType.USER).orElseThrow(RoleNotFoundException::new))))
        );
    }

    public String getAdminEmail() {
        return adminEmail;
    }

    public String getMember1Email() {
        return member1Email;
    }

    public String getMember2Email() {
        return member2Email;
    }

    public String getPassword() {
        return password;
    }
}
