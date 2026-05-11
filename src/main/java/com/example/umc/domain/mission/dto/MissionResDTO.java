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

    @Builder
    public record MyMissionListDTO(
            List<MyMissionDTO> myMissions,
            String nextCursor,
            Boolean hasNext

    ){}
    @Builder
    public record MyMissionDTO(
            Long missionId,
            String storeName,
            Integer reward,
            String content,
            String status // 진행중, 진행완료 등의 상태

    ){}

    //가게 내 미션 조회
    @Builder
    public record GetMission(
            Long missionId,
            Integer point,
            String conditional
    ){}

    //페이지네이션 틀
    @Builder
    public record Pagination<T>(
            List<T> data,
            Boolean hasNext,
            String nextCursor,
            Integer pageSize
    ){}

    @Builder
    public record ProgressMissionDTO(

            Long missionId,
            String storeName,
            Integer reward,
            String content,
            String status
    ){}
    @Builder
    public record MissionPageResponseDTO<T>(

            List<T> data,
            Integer pageNumber,
            Integer pageSize
    ){}


}
