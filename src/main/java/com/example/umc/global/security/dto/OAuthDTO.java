package com.example.umc.global.security.dto;

import com.example.umc.domain.member.enums.SocialType;

public interface OAuthDTO {
    SocialType getSocialType();
    String getSocialUid();
    String getSocialEmail();
    String getName();
}
