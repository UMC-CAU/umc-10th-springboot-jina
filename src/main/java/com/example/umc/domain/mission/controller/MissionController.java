package com.example.umc.domain.mission.controller;


import com.example.umc.domain.mission.dto.MissionResDTO;
import com.example.umc.domain.mission.exception.code.MissionSuccessCode;
import com.example.umc.domain.mission.service.MissionService;
import com.example.umc.global.apiPayload.ApiResponse;
import com.example.umc.global.apiPayload.code.BaseSuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MissionController {
    private final MissionService missionService;

    @PostMapping("/missions/{missionId}/success")
    public ApiResponse<MissionResDTO.MissionSuccessResult> missionSuccessResult(
            @RequestHeader("Authorization") String token,
            @PathVariable(name = "missionId") Long missionId
    ){
        BaseSuccessCode code = MissionSuccessCode.MISSION_SUCCESS_REQUEST;
        return ApiResponse.onSuccess(code, missionService.getMissionSuccessResult(token, missionId));
    }
}
