package com.othkkartho.vtuber_site_v2.factory.entity;

import com.othkkartho.vtuber_site_v2.domain.User.Role;
import com.othkkartho.vtuber_site_v2.domain.User.RoleType;

public class RoleFactory {
    public static Role createRole() {
        return new Role(RoleType.USER);
    }
}
