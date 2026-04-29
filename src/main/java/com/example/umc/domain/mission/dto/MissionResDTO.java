package com.example.umc.domain.mission.dto;

import lombok.Builder;

public class MissionResDTO {
    @Builder
    public record MissionSuccessResult(
            Long missionId,
            String status
    ){}
}
