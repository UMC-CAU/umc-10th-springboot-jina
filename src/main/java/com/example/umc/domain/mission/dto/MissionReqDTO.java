package com.example.umc.domain.mission.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class MissionReqDTO {
    //가게 미션 생성
    public record CreateMission(
            @NotNull(message = "마감기한은 필수입니다") // 검증 어노테이션
            LocalDate deadline,
            @NotNull(message = "미션 성공 포인트는 필수입니다.")
            Integer point,
            @NotBlank(message = "조건은 빈칸일 수 없습니다.")
            String conditional
    ){}

    // 진행중인 내 미션 조회 요청 DTO
    // 과제 조건에서 사용자 ID를 Request Body로 받으라고 했기 때문에,
    // memberId, pageNumber, pageSize를 하나의 요청 객체로 묶어서 받습니다.
    public record ProgressMissionRequest(
            @NotNull(message = "사용자 ID는 필수입니다.")
            Long memberId,

            @NotNull(message = "페이지 번호는 필수입니다.")
            Integer pageNumber,

            @NotNull(message = "페이지 크기는 필수입니다.")
            Integer pageSize
    ){}
}
