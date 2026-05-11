package com.example.umc.domain.mission.converter;

import com.example.umc.domain.member.dto.MemberResDTO;
import com.example.umc.domain.mission.dto.MissionReqDTO;
import com.example.umc.domain.mission.dto.MissionResDTO;
import com.example.umc.domain.mission.entity.Mapping.MemberMission;
import com.example.umc.domain.mission.entity.Mission;
import com.example.umc.domain.store.entity.Store;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class MissionConverter {
    public static MissionResDTO.MissionSuccessResult toMissionSuccessResult(){
        return MissionResDTO.MissionSuccessResult.builder()
                .missionId(3L)
                .status("요청 완료")
                .build();
    }

    public static MissionResDTO.MissionList toMissionList() {
        MissionResDTO.MissionDetailDTO mission1 = MissionResDTO.MissionDetailDTO.builder()
                .missionId(10L)
                .storeId(2L)
                .storeName("마라탕")
                .distance("2km")
                .category("중식")
                .reward(200)
                .storeImageUrls(List.of("http. . "))
                .status("ACTIVE")
                .build();

        return MissionResDTO.MissionList.builder()
                .missions(mission1)
                .nextCursor("2026. . ")
                .hasNext(true)
                .build();

    }

    // 1. 엔티티 하나를 MyMissionDTO(낱개)로 바꾸는 메서드
    public static MissionResDTO.MyMissionDTO toMyMissionDTO(MemberMission memberMission) {
        return MissionResDTO.MyMissionDTO.builder()
                .missionId(memberMission.getMission().getId())
                .storeName(memberMission.getMission().getStore().getName())
                .reward(memberMission.getMission().getPoint())
                .content(memberMission.getMission().getText())
                .status(memberMission.getMissionStatus().toString()) // 레포지토리 쿼리에 쓴 필드명 기준
                .build();
    }

    // 2. 엔티티 리스트를 MyMissionListDTO(전체 목록)로 바꾸는 메서드
    public static MissionResDTO.MyMissionListDTO toMyMissionListDTO(List<MemberMission> memberMissions, boolean hasNext) {

        // 리스트 안의 각 항목들을 위에서 만든 toMyMissionDTO로 변환
        List<MissionResDTO.MyMissionDTO> myMissionDTOList = memberMissions.stream()
                .map(MissionConverter::toMyMissionDTO)
                .collect(Collectors.toList()); //여기는 잘 모르겠음.

        // 다음 커서 결정 (리스트가 비어있지 않다면 가장 마지막 데이터의 ID를 String으로 변환)
        String nextCursor = null;
        if (!memberMissions.isEmpty()) {
            nextCursor = String.valueOf(memberMissions.get(memberMissions.size() - 1).getId());
        }

        return MissionResDTO.MyMissionListDTO.builder()
                .myMissions(myMissionDTOList)
                .nextCursor(nextCursor)
                .hasNext(hasNext)
                .build(); // 이부분 추가공부 필요.
    }

    public static Mission toMission(
            Store store,
            MissionReqDTO.CreateMission dto
    ){
        return Mission.builder()
                .store(store)
                .conditional(dto.conditional())
                .point(dto.point())
                .deadline(dto.deadline())
                .build();
    }
    // 가게 내 미션 조회
    public static MissionResDTO.GetMission toGetMission(
            Mission mission
    ) {
        return MissionResDTO.GetMission.builder()
                .conditional(mission.getConditional())
                .point(mission.getPoint())
                .missionId(mission.getId())
                .build();
    }

    // 페이지네이션 틀 생성
    public static <T> MissionResDTO.Pagination<T> toPagination(
            List<T> data,
            Boolean hasNext,
            String nextCursor,
            Integer pageSize
    ) {

        return MissionResDTO.Pagination.<T>builder()
                .data(data)
                .hasNext(hasNext)
                .nextCursor(nextCursor)
                .pageSize(pageSize)
                .build();
    }

    // 진행중인 미션 하나를 응답 DTO 하나로 변환합니다.
    // MemberMission은 사용자와 미션 사이의 매핑 정보이고,
    // 실제 미션 내용은 memberMission.getMission()을 통해 꺼냅니다.
    public static MissionResDTO.ProgressMissionDTO toProgressMissionDTO(
            MemberMission memberMission
    ) {
        Mission mission = memberMission.getMission();

        return MissionResDTO.ProgressMissionDTO.builder()
                .missionId(mission.getId())
                .storeName(mission.getStore().getName())
                .reward(mission.getPoint())
                .content(mission.getText())
                .status(memberMission.getMissionStatus().name())
                .build();
    }

    // Page<MemberMission>을 최종 페이지 응답 DTO로 변환합니다.
    // Page 객체 안에는 현재 페이지 데이터, 페이지 번호, 페이지 크기가 들어 있습니다.
    public static MissionResDTO.MissionPageResponseDTO<MissionResDTO.ProgressMissionDTO> toProgressMissionPageResponseDTO(
            Page<MemberMission> progressMissions
    ) {
        List<MissionResDTO.ProgressMissionDTO> data = progressMissions.getContent().stream()
                .map(MissionConverter::toProgressMissionDTO)
                .collect(Collectors.toList());

        return MissionResDTO.MissionPageResponseDTO.<MissionResDTO.ProgressMissionDTO>builder()
                .data(data)
                .pageNumber(progressMissions.getNumber())
                .pageSize(progressMissions.getSize())
                .build();
    }
}
