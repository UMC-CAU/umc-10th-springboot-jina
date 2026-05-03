package com.example.umc.domain.member.converter;

import com.example.umc.domain.member.dto.MemberResDTO;
import com.example.umc.domain.member.entity.Member;

import java.time.LocalDateTime;
import java.util.List;

public class MemberConverter {
    public static MemberResDTO.RequestBody toRequestBody(
            String stringTest,
            Long longTest
    ){
        return MemberResDTO.RequestBody.builder()
                .stringTest(stringTest)
                .longTest(longTest)
                .build();
    }
    // 마이페이지 응답 변환 로직
    /* public static MemberResDTO.GetInfo toGetInfo(
            Member member
    ){
        return MemberResDTO.GetInfo.builder()
                .email(member.getEmail())
                .name(member.getName())
                .point(member.getPoint())
                .phoneNumber(member.getPhoneNumber())
                .profileUrl(member.getProfileUrl())
                .build();
    } */

    public static MemberResDTO.Home toHome() {
        MemberResDTO.MissionProgressDTO progress = MemberResDTO.MissionProgressDTO.builder()
                .current(7)
                .goal(10)
                .build();

        MemberResDTO.MyMissionDTO mission = MemberResDTO.MyMissionDTO.builder()
                .missionId(1L)
                .storeName("반이학생마라탕")
                .category("중식")
                .reward(500)
                .deadlineDay(7)
                .build();

        return MemberResDTO.Home.builder()
                .point(999999)
                .alarm(true)
                .missionProgress(progress)
                .myMission(mission)
                .nextCursor("20260324120000105")
                .hasNext(true)
                .region("안암동")
                .build();
    }

    public static MemberResDTO.SignUp toSignUpResult() {
        return MemberResDTO.SignUp.builder()
                .memberId(1L)
                .createdAt(LocalDateTime.now())
                .preferenceFoods(List.of("한식", "양식", "분식"))
                .build();
    }
}
