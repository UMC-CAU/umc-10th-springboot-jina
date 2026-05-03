package com.example.umc.domain.mission.controller;


import com.example.umc.domain.mission.dto.MissionResDTO;
import com.example.umc.domain.mission.service.MissionService;
import com.example.umc.global.apiPayload.ApiResponse;
import com.example.umc.global.apiPayload.code.BaseSuccessCode;
import com.example.umc.global.apiPayload.code.GeneralSuccessCode;
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
        BaseSuccessCode code = GeneralSuccessCode.OK;
        return ApiResponse.onSuccess(code, missionService.getMissionSuccessResult(token, missionId));
    }

    @GetMapping("/mission/me")
    public ApiResponse<MissionResDTO.MissionList> missionList(
            @RequestParam(name = "memberId") Long memberId,
            // 1. cursor: 선택사항(Optional)이므로 required = false
            @RequestParam(name = "cursor", required = false) String cursor,

            // 2. size: 선택사항이며 기본값이 10이므로 defaultValue 설정
            @RequestParam(name = "size", required = false, defaultValue = "10") Integer size,

            // 3. status: 필수값이며 특정 Enum(ACTIVE, COMPLETE) 문자열을 받음
            @RequestParam(name = "status") String status
    ){
        return ApiResponse.onSuccess(GeneralSuccessCode.OK, missionService.getMissionList(memberId, cursor, size, status));

    }
}
