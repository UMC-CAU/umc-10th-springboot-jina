package com.example.umc.domain.mission.dto;

import lombok.Builder;

import java.util.List;

public class MissionResDTO {
    @Builder
    public record MissionSuccessResult(
            Long missionId,
            String status
    ){}

    @Builder
    public record MissionList(
            MissionDetailDTO missions,
            String nextCursor,
            Boolean hasNext
    ){}

    @Builder
    public record MissionDetailDTO(
            Long missionId,
            Long storeId,
            String storeName,
            String distance,
            String category,
            Integer reward,
            List<String> storeImageUrls,
            String status
    ){}
}
