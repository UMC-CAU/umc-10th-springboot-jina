package com.example.umc.domain.mission.controller;


import com.example.umc.domain.mission.dto.MissionResDTO;
import com.example.umc.domain.mission.exception.code.MissionSuccessCode;
import com.example.umc.domain.mission.service.MissionService;
import com.example.umc.global.apiPayload.ApiResponse;
import com.example.umc.global.apiPayload.code.BaseSuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MissionController {
    private final MissionService missionService;

    @PostMapping("/missions/{missionId}/success")
    public ApiResponse<MissionResDTO.MissionSuccessResult> missionSuccessResult(
            @PathVariable(name = "missionId") Long missionId
    ){
        BaseSuccessCode code = MissionSuccessCode.MISSION_SUCCESS_REQUEST;
        return ApiResponse.onSuccess(code, missionService.getMissionSuccessResult(missionId));
    }
}
