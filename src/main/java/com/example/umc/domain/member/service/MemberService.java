package com.example.umc.domain.member.service;

import com.example.umc.domain.member.converter.MemberConverter;
import com.example.umc.domain.member.dto.MemberReqDTO;
import com.example.umc.domain.member.dto.MemberResDTO;
import com.example.umc.domain.member.entity.Member;
import com.example.umc.domain.member.exception.MemberException;
import com.example.umc.domain.member.exception.code.MemberErrorCode;
import com.example.umc.domain.member.repository.MemberRepository;
import com.example.umc.domain.mission.entity.Mission;
import com.example.umc.domain.mission.repository.MissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final MissionRepository missionRepository;

    // Query Parameter
    public String singleParameter(
            String singleParameter
    ){
        return singleParameter;
    }

    // Request Body
    public MemberResDTO.RequestBody requestBody(
            MemberReqDTO.RequestBody dto
    ) {
        return MemberConverter.toRequestBody(dto.stringTest(), dto.longTest());
    }

    @Transactional
    public String createUser(

    ){
        Member member = Member.builder()
                .name("test")
                .build();
        memberRepository.save(member);
        return "OK";
    }

    @Transactional
    public String deleteUser(

    ){
        memberRepository.deleteById(5L);
        return "OK";
    }

    public MemberResDTO.GetInfo getInfo(Long memberId) {

        // DB에서 해당 유저 ID로 데이터 조회
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        // 컨버터를 이용해서 응답 DTO 생성 & return
        return MemberConverter.toGetInfo(member);
    }

    public MemberResDTO.SignUp getSignUp(MemberReqDTO.SignUp dto) {
        // 실제 로직에서는 dto를 Entity로 변환해 DB에 저장하겠지만,
        // 지금은 빨간 줄을 없애고 Swagger 확인을 위해 더미 데이터를 반환.
        return MemberConverter.toSignUpResult();
    }

    public  MemberResDTO.Home getHome(Long memberId, String cursorStr, Integer size){

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));
        Integer currentCount = missionRepository.countChallengingMissions(memberId);
        Integer goal = 10;

        Long cursor = (cursorStr != null && !cursorStr.isEmpty()) ? Long.parseLong(cursorStr) : null;
        // missionRepository.findHomeMissions(...) 같이 레포지토리에 새로 만들어야 해요!
        Pageable pageable = PageRequest.of(0, size + 1); // 10개 요청 시 11개 가져와서 hasNext 판단
        List<Mission> missions = missionRepository.findHomeMissions(cursor, pageable);
        boolean hasNext = false;
        if (missions.size() > size) {
            hasNext = true;
            missions = missions.subList(0, size);
        }

        // 4. 컨버터에게 전부 넘겨서 최종 결과물 만들기!
        return MemberConverter.toHome(member, currentCount, goal, missions, hasNext);
        }
}