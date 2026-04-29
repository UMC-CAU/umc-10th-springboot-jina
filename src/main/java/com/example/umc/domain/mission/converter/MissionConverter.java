package com.example.umc.domain.mission.converter;

import com.example.umc.domain.member.dto.MemberResDTO;
import com.example.umc.domain.mission.dto.MissionResDTO;

public class MissionConverter {
    public static MissionResDTO.MissionSuccessResult toMissionSuccessResult(){
        return MissionResDTO.MissionSuccessResult.builder()
                .missionId(3L)
                .status("요청 완료")
                .build();
    }
}
