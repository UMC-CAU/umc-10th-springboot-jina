package com.example.umc.domain.member.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

public class MemberResDTO {

    @Builder
    public record RequestBody(
            String stringTest,
            long longTest
    ){}

    @Builder
    public record GetInfo(
            String name,
            String profileUrl,
            String email,
            String phoneNumber,
            Integer point
    ){}
    @Builder
    public record SignUp(
            Long memberId,
            LocalDateTime createdAt,
            List<String> preferenceFoods
    ){}

    @Builder
    public record Home(
            Integer point,
            Boolean alarm,
            String nextCursor,
            Boolean hasNext,
            String region,
            MissionProgressDTO missionProgress,
            MyMissionDTO myMission
    ){}

    @Builder
    public record MissionProgressDTO(
            Integer current,
            Integer goal
    ){}

    @Builder
    public record MyMissionDTO(
            Long missionId,
            String storeName,
            String category,
            Integer reward,
            Integer deadLineDay
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
