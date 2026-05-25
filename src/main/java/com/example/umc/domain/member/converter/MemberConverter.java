package com.example.umc.domain.member.converter;

import com.example.umc.domain.member.dto.MemberReqDTO;
import com.example.umc.domain.member.dto.MemberResDTO;
import com.example.umc.domain.member.entity.Member;
import com.example.umc.domain.member.enums.SocialType;
import com.example.umc.domain.mission.entity.Mission;
import com.example.umc.global.security.dto.OAuthDTO;

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

    public static Member toMember(
            MemberReqDTO.SignUp dto,
            String encodedPassword
    ) {
        // 회원가입 요청 DTO를 DB에 저장할 Member Entity로 변환.
        // password에는 사용자가 입력한 원문이 아니라 Service에서 BCrypt로 암호화한 값을 넣기.
        return Member.builder()
                .email(dto.email())
                .password(encodedPassword)
                .name(dto.name())
                .nickname(dto.name())
                .phoneNumber("")
                .gender(dto.gender())
                .birth(dto.birth().atStartOfDay())
                // email/password로 가입한 일반 회원이므로 소셜 제공자는 LOCAL로 저장.
                .socialType(SocialType.LOCAL)
                // socialUid는 현재 Member에서 nullable=false라 임시로 email을 넣습니다.
                // 나중에 소셜 로그인과 일반 로그인을 분리하면 구조를 더 깔끔하게 바꿀 수 있습니다.
                .socialUid(dto.email())
                .address(dto.address())
                // 처음 가입한 회원의 기본값.
                .point(0)
                .profileUrl("")
                .build();
    }

    public static Member toMember(OAuthDTO dto) {
        return Member.builder()
                .email(dto.getSocialEmail())
                .password("")
                .name(dto.getName())
                .nickname(dto.getName())
                .phoneNumber("")
                .gender(null)
                .birth(null)
                .socialType(dto.getSocialType())
                .socialUid(dto.getSocialUid())
                .address(null)
                .point(0)
                .profileUrl("")
                .build();
    }

    public static MemberResDTO.SignUp toSignUpResult(
            Member member,
            List<Long> preferenceFoodIds
    ) {
        // 아직 선호 음식 저장 Repository까지 연결하지 않았기 때문에,
        // 이번 미션에서는 요청으로 들어온 음식 id 목록을 문자열 목록으로 변환해서 응답에 담습니다.
        List<String> preferenceFoods = preferenceFoodIds == null
                ? List.of()
                : preferenceFoodIds.stream()
                .map(String::valueOf)
                .toList();

        return MemberResDTO.SignUp.builder()
                .memberId(member.getId())
                .createdAt(member.getCreatedAt())
                .preferenceFoods(preferenceFoods)
                .build();
    }

    public static MemberResDTO.Login toLogin(String accessToken) {
        return MemberResDTO.Login.builder()
                .accessToken(accessToken)
                .build();
    }
}
