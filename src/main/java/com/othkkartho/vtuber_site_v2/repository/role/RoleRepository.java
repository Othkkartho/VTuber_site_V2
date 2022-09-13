package com.othkkartho.vtuber_site_v2.repository.role;

import com.othkkartho.vtuber_site_v2.domain.User.Role;
import com.othkkartho.vtuber_site_v2.domain.User.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleType(RoleType roleType);
}
