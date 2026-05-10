package com.example.umc.domain.member.converter;

import com.example.umc.domain.member.dto.MemberResDTO;
import com.example.umc.domain.member.entity.Member;
import com.example.umc.domain.mission.entity.Mission;
import com.example.umc.domain.store.enums.RegionName;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
     public static MemberResDTO.GetInfo toGetInfo(
            Member member
    ){
         String phoneStr = (member.getPhoneNumber() != null) ? member.getPhoneNumber() : "미인증";
        return MemberResDTO.GetInfo.builder()
                .email(member.getEmail())
                .name(member.getName())
                .point(member.getPoint())
                .phoneNumber(phoneStr)
                .profileUrl(member.getProfileUrl())
                .build();
    }

    //아래는 홈화면 조회 컨버터
    // 1. 미션 낱개를 DTO로 변환
    public static MemberResDTO.MyMissionDTO toMyMissionDTO(Mission mission) {
        return MemberResDTO.MyMissionDTO.builder()
                .missionId(mission.getId())
                .storeName(mission.getStore().getName()) // 식당 이름
                .category("중식") // 카테고리는 엔티티 구조에 맞게 수정 필요!
                .reward(mission.getPoint().intValue())
                .deadlineDay(7) // 마감일 계산 로직 필요 (임시로 7 고정)
                .build();
    }

    // 2. 홈 화면 전체를 조립
    public static MemberResDTO.Home toHome(
            Member member,
            Integer currentCount,
            Integer goal,
            List<Mission> missions,
            boolean hasNext)
    {

        // 진행도 세팅
        MemberResDTO.MissionProgressDTO progress = MemberResDTO.MissionProgressDTO.builder()
                .current(currentCount)
                .goal(goal)
                .build();

        // 리스트 변환 (for문 역할)
        List<MemberResDTO.MyMissionDTO> missionDTOList = missions.stream()
                .map(MemberConverter::toMyMissionDTO)
                .collect(Collectors.toList()); // 추가 고민해보기

        // 커서 결정
        String nextCursor = null;
        if (!missions.isEmpty()) {
            nextCursor = String.valueOf(missions.get(missions.size() - 1).getId());
        }

        return MemberResDTO.Home.builder()
                .point(member.getPoint())
                .region("안암동") // 일단 화면처럼 고정
                .missionProgress(progress)
                .myMissions(missionDTOList) // 💡 리스트로 담아줍니다.
                .nextCursor(nextCursor)
                .hasNext(hasNext)
                .build();
    }
    //여기까지가 홈화면 조회 컨버터

    public static MemberResDTO.SignUp toSignUpResult() {
        return MemberResDTO.SignUp.builder()
                .memberId(1L)
                .createdAt(LocalDateTime.now())
                .preferenceFoods(List.of("한식", "양식", "분식"))
                .build();
    }
}
