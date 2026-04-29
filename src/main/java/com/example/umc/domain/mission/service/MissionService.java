package com.example.umc.domain.mission.service;

import com.example.umc.domain.mission.converter.MissionConverter;
import com.example.umc.domain.mission.dto.MissionResDTO;
import com.example.umc.domain.mission.repository.MissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MissionService {
    private final MissionRepository missionRepository;

    public MissionResDTO.MissionSuccessResult getMissionSuccessResult(String token, Long missionId){
        return MissionConverter.toMissionSuccessResult();
    }
}
