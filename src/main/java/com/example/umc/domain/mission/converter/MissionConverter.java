package com.example.umc.domain.mission.converter;

import com.example.umc.domain.member.dto.MemberResDTO;
import com.example.umc.domain.mission.dto.MissionResDTO;
import com.example.umc.domain.mission.entity.Mapping.MemberMission;

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
}
