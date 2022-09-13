package com.othkkartho.vtuber_site_v2.domain.User;

import com.othkkartho.vtuber_site_v2.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

@Entity
@Table(name = "member")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "member_id")
    @Comment("유저 고유 DB 아이디")
    private Long id;

    @Column(unique = true, nullable = false, length = 50)
    @Comment("유저 이메일 밑 아이디")
    private String email;

    @Column(unique = true, nullable = false, length = 50)
    @Comment("유저 닉네임")
    private String nickname;

    @Comment("유저 비밀번호")
    private String password;

    @Comment("유저 보유 포인트")
    private Long point;

    @Comment("유저 주소")
    private String address;

    @Comment("유저 소개글")
    private String introduce;

    @Comment("유저가 그냥 회원가입 했으면 NULL값 소셜 가입했으면 어떤걸로 가입 했는지")
    private String provider;

    @Comment("유저 권한")
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MemberRole> roles;

    @Builder
    public Member(String email, String password, String nickname, Long point, String address, String introduce, String provider, List<Role> roles) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.point = point;
        this.address = address;
        this.introduce = introduce;
        this.provider = provider;
        this.roles = roles.stream().map(r -> new MemberRole(this, r)).collect(toSet());
    }

    public void updateNickname(String nickname){
        this.nickname = nickname;
    }
}
