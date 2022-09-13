package com.othkkartho.vtuber_site_v2.domain.User;

import lombok.*;

import javax.persistence.Embeddable;
import javax.persistence.Transient;
import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MemberRoleId implements Serializable {
    @Transient
    private Member member;
    @Transient
    private Role role;
}
