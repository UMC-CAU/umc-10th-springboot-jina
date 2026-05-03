package com.example.umc.domain.mission.converter;

import com.example.umc.domain.member.dto.MemberResDTO;
import com.example.umc.domain.mission.dto.MissionResDTO;

import java.util.List;

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
}
