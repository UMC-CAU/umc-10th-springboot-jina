package com.example.umc.domain.member.service;

import com.example.umc.domain.member.converter.MemberConverter;
import com.example.umc.domain.member.dto.MemberReqDTO;
import com.example.umc.domain.member.dto.MemberResDTO;
import com.example.umc.domain.member.entity.Member;
import com.example.umc.domain.member.exception.MemberException;
import com.example.umc.domain.member.exception.code.MemberErrorCode;
import com.example.umc.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

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

    /*public MemberResDTO.GetInfo getInfo(MemberReqDTO.GetInfo dto) {
        // DTO에서 유저 ID를 추출
        Long memberId = dto.id();

        // DB에서 해당 유저 ID로 데이터 조회
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        // 컨버터를 이용해서 응답 DTO 생성 & return
        return MemberConverter.toGetInfo(member);
    }*/

    public MemberResDTO.SignUp getSignUp(MemberReqDTO.SignUp dto) {
        // 실제 로직에서는 dto를 Entity로 변환해 DB에 저장하겠지만,
        // 지금은 빨간 줄을 없애고 Swagger 확인을 위해 더미 데이터를 반환.
        return MemberConverter.toSignUpResult();
    }

    public  MemberResDTO.Home getHome(Long memberId){
        return MemberConverter.toHome();
    }
}