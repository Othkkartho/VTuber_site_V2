package com.othkkartho.vtuber_site_v2.dto.membermanage.member;

import com.othkkartho.vtuber_site_v2.domain.User.Member;
import com.othkkartho.vtuber_site_v2.domain.User.MemberRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberDto {
    private Long id;
    private String email;
    private String nickname;
    private Long point;
    private String introduce;
    private String provider;

    public static MemberDto toEntity(Member member) {
        return new MemberDto(member.getId(), member.getEmail(), member.getNickname(), member.getPoint(), member.getIntroduce(), member.getProvider());
    }
}
