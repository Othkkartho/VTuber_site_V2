package com.othkkartho.vtuber_site_v2.domain.User;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum RoleType {
    ADMIN("ROLE_ADMIN", "관리자"),
    GAME_ADMIN("ROLE_GAME_ADMIN", "게임 채널 관리자"),
    CHANNEL_ADMIN("ROLE_CHANNEL_ADMIN", "커뮤니티 채널 관리자"),
    TRANSLATOR("ROLE_TRANSLATOR", "번역가"),
    ILLUSTRATOR("ROLE_ILLUSTRATOR","일러스트레이터"),
    USER("ROLE_USER", "일반 사용자");

    private final String value;
    private final String key;
}
