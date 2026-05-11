package com.example.umc.domain.mission.controller;


import com.example.umc.domain.mission.dto.MissionReqDTO;
import com.example.umc.domain.mission.dto.MissionResDTO;
import com.example.umc.domain.mission.enums.MissionStatus;
import com.example.umc.domain.mission.exception.code.MissionSuccessCode;
import com.example.umc.domain.mission.service.MissionService;
import com.example.umc.global.apiPayload.ApiResponse;
import com.example.umc.global.apiPayload.code.BaseSuccessCode;
import com.example.umc.global.apiPayload.code.GeneralSuccessCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ApiResponse<MissionResDTO.MyMissionListDTO> missionList(
            @RequestParam(name = "memberId") Long memberId,
            //@RequestHeader("Authorization") String token 으로 하는개 나을듯?
            // 1. cursor: 선택사항(Optional)이므로 required = false
            @RequestParam(name = "cursor", required = false) String cursor,

            // 2. size: 선택사항이며 기본값이 10이므로 defaultValue 설정
            @RequestParam(name = "size", required = false, defaultValue = "10") Integer size,

            // 3. status: 필수값이며 특정 Enum(ACTIVE, COMPLETE) 문자열을 받음
            @RequestParam(name = "status") MissionStatus status
    ){
        MissionResDTO.MyMissionListDTO result = missionService.getMyMissions(memberId, status, cursor, size);

        BaseSuccessCode code = GeneralSuccessCode.OK;
        return ApiResponse.onSuccess(code, result);
    }

    @PostMapping("/v1/stores/{storeId}/missions")
    public ApiResponse<Void> createMission( //반환할거 없으니까 void
            @PathVariable Long storeId,
            @RequestBody @Valid MissionReqDTO.CreateMission dto
    ){
        BaseSuccessCode code = MissionSuccessCode.CREATED;
        return ApiResponse.onSuccess(code, missionService.createMission(storeId, dto));
    }

    // 가게 내 미션들 조회
    @GetMapping("/v1/stores/{storeId}/missions")
    public ApiResponse<MissionResDTO.Pagination<MissionResDTO.GetMission>> getMissions(
            @PathVariable Long storeId,
            @RequestParam Integer pageSize,
            @RequestParam String cursor,
            @RequestParam String query
    ) {
        BaseSuccessCode code = MissionSuccessCode.OK;
        return ApiResponse.onSuccess(code, missionService.getMissions(storeId, pageSize, cursor, query));
    }




}
