package com.othkkartho.vtuber_site_v2.domain.User;

import com.othkkartho.vtuber_site_v2.exception.RoleNotFoundException;
import com.othkkartho.vtuber_site_v2.repository.member.MemberRepository;
import com.othkkartho.vtuber_site_v2.repository.role.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

//@Component
//@RequiredArgsConstructor
//@Slf4j
//@Profile("local")
public class InitDB {
//    private final RoleRepository roleRepository;
//    private final MemberRepository memberRepository;
//    private final PasswordEncoder passwordEncoder;
//
//    @EventListener(ApplicationReadyEvent.class)
//    @Transactional
//    public void initDB() {
//        log.info("initialize database");
//        initRole();
//        initTestAdmin();
//        initTestMember();
//    }
//
//    private void initRole() {
//        roleRepository.saveAll(
//                List.of(RoleType.values()).stream().map(roleType -> new Role(roleType)).collect(Collectors.toList())
//        );
//    }
//
//    private void initTestAdmin() {
//        memberRepository.save(
//                new Member("admin@admin.com", passwordEncoder.encode("123456a!"), "admin", 0L, "", "local",
//                        List.of(roleRepository.findByRoleType(RoleType.USER).orElseThrow(RoleNotFoundException::new),
//                                roleRepository.findByRoleType(RoleType.ADMIN).orElseThrow(RoleNotFoundException::new)))
//        );
//    }
//
//    private void initTestMember() {
//        memberRepository.saveAll(
//                List.of(
//                        new Member("member1@member.com", passwordEncoder.encode("123456a!"), "member1", 0L, "", "local",
//                                List.of(roleRepository.findByRoleType(RoleType.USER).orElseThrow(RoleNotFoundException::new))),
//                        new Member("member2@member.com", passwordEncoder.encode("123456a!"), "member2", 0L, "", "local",
//                                List.of(roleRepository.findByRoleType(RoleType.USER).orElseThrow(RoleNotFoundException::new))))
//        );
//    }
}
