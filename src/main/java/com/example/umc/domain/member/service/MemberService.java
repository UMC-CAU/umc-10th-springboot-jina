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
import com.example.umc.global.security.entity.AuthMember;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final MissionRepository missionRepository;

    // SecurityConfig에 등록한 BCryptPasswordEncoder Bean이 여기로 주입.
    // 회원가입 시 비밀번호 원문을 BCrypt 해시 값으로 바꿀 때 사용.
    private final PasswordEncoder passwordEncoder;

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

    public MemberResDTO.GetInfo getInfo(AuthMember member) {

        // 컨버터를 이용해서 응답 DTO 생성 & return
        return MemberConverter.toGetInfo(member.getMember());
    }

    @Transactional
    public MemberResDTO.SignUp getSignUp(MemberReqDTO.SignUp dto) {
        // 화면상 필수 약관인 연령 확인, 서비스 이용약관, 개인정보 처리방침은 반드시 true여야 함.
        // Boolean.TRUE.equals(...)를 쓰면 값이 null이어도 NullPointerException 없이 false처럼 처리할 수 있습니다.
        if (!Boolean.TRUE.equals(dto.ageConfirm())
                || !Boolean.TRUE.equals(dto.serviceAgree())
                || !Boolean.TRUE.equals(dto.privacyAgree())) {
            throw new MemberException(MemberErrorCode.TERM_NOT_AGREED);
        }

        // 폼 로그인에서 같은 email로 중복 가입되지 않게 막기.
        if (memberRepository.existsByEmail(dto.email())) {
            throw new MemberException(MemberErrorCode.MEMBER_ALREADY_EXISTS);
        }

        // 솔트 사용
        String encodedPassword = passwordEncoder.encode(dto.password());

        // DTO를 Entity로 바꾸고, 암호화된 비밀번호를 넣어서 DB에 저장.
        Member member = MemberConverter.toMember(dto, encodedPassword);
        Member savedMember = memberRepository.save(member);

        // 저장된 memberId, createdAt을 응답으로 돌려줍니다.
        return MemberConverter.toSignUpResult(savedMember, dto.preferenceFoods());
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
