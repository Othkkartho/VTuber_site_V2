package com.othkkartho.vtuber_site_v2.factory.entity;

import com.othkkartho.vtuber_site_v2.domain.User.Member;
import com.othkkartho.vtuber_site_v2.domain.User.Role;

import java.util.List;

import static java.util.Collections.emptyList;

public class MemberFactory {
    public static Member createMember() {
        return new Member("email@email.com", "123456a!", "nickname", 0L, "", "local", emptyList());
    }

    public static Member createMember(String email, String password, String nickname, Long point, String introduce, String provider) {
        return new Member(email, password, nickname, point, introduce, provider, emptyList());
    }

    public static Member createMemberWithRoles(List<Role> roles) {
        return new Member("email@email.com", "123456a!", "nickname", 0L, "", "local", roles);
    }
}
