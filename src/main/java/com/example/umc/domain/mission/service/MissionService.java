package com.example.umc.domain.mission.service;

import com.example.umc.domain.member.converter.MemberConverter;
import com.example.umc.domain.member.dto.MemberResDTO;
import com.example.umc.domain.mission.converter.MissionConverter;
import com.example.umc.domain.mission.dto.MissionReqDTO;
import com.example.umc.domain.mission.dto.MissionResDTO;
import com.example.umc.domain.mission.entity.Mapping.MemberMission;
import com.example.umc.domain.mission.entity.Mission;
import com.example.umc.domain.mission.enums.MissionStatus;
import com.example.umc.domain.mission.exception.MissionException;
import com.example.umc.domain.mission.exception.code.MissionErrorCode;
import com.example.umc.domain.mission.repository.MissionRepository;
import com.example.umc.domain.store.entity.Store;
import com.example.umc.domain.store.exception.StoreException;
import com.example.umc.domain.store.exception.code.StoreErrorCode;
import com.example.umc.domain.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MissionService {
    private final MissionRepository missionRepository;
    private final StoreRepository storeRepository;

    public MissionResDTO.MissionSuccessResult getMissionSuccessResult(String token, Long missionId){
        return MissionConverter.toMissionSuccessResult();
    }

    public MissionResDTO.MissionList getMissionList(Long memberId, String cursor, Integer size, String status){
        return MissionConverter.toMissionList();
    }

    public MissionResDTO.MyMissionListDTO getMyMissions(Long memberId, MissionStatus status, String cursorStr, Integer size) {

        // 1. 커서 파싱 (처음 요청 시 null, 이후엔 마지막 ID값)
        Long cursor = (cursorStr != null && !cursorStr.isEmpty()) ? Long.parseLong(cursorStr) : null;
        //Long.parsLong은 글자를 숫자로 바꿔라!
        // 2. DB에서 데이터 조회 (size보다 1개 더 가져오지 않으므로, 레포지토리 쿼리 결과를 그대로 사용)
        // 만약 정교한 hasNext 판단을 원하시면 레포지토리 메서드 이름을 findTop11... 처럼 고치거나
        // 쿼리에 LIMIT을 주어야 하지만, 레포지토리 코드 그대로 연결하겠습니다.
        List<MemberMission> missions = missionRepository.findMemberMissions(memberId, status, cursor);

        // [3] "다음 페이지가 있을까?"를 판단합니다.
        // 우리는 size(예: 10개)만큼만 보여줄 거니까, 가져온 개수가 size보다 많은지 봅니다.
        boolean hasNext = false;
        if (missions.size() > size) {
            hasNext = true;
            // 다음 페이지 여부만 확인하고, 실제로 보여줄 리스트에서는 넘치는 데이터(11번째)를 뺍니다.
            missions = missions.subList(0, size);
        }

        // 4. 컨버터를 통해 DTO로 변환하여 반환
        return MissionConverter.toMyMissionListDTO(missions, hasNext);
    }

    // 7주차 미션 생성 api
    public Void createMission(
            Long storeId,
            MissionReqDTO.CreateMission dto
    ) {
        // 가게 찾기
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new StoreException(StoreErrorCode.NOT_FOUND));

        // 미션 생성
        Mission mission = MissionConverter.toMission(store, dto);

        // 미션 DB 저장
        missionRepository.save(mission);
        return null;
    }
    // 가게 내 미션들 조회
    public MissionResDTO.Pagination<MissionResDTO.GetMission> getMissions(
            Long storeId,
            Integer pageSize,
            String cursor,
            String query
    ) {

        //페이지 정보들을 PageRequest로 만들기
        PageRequest pageRequest = PageRequest.of(0,pageSize);

        long idCursor;
        Slice<Mission> missionList;
        String nextCursor;



        // 커서가 있는 경우
        if (!cursor.equals("-1")) {

            // 커서 분리
            String[] cursorSplit = cursor.split(":");
            switch (query.toLowerCase()) {
                case "id":

                    // 커서 타입 변환
                    Long prevCursor = Long.parseLong(cursorSplit[0]);
                    idCursor = Long.parseLong(cursorSplit[1]);

                    // 가게 내 미션들 조회 & where절에 커서값 기입
                    missionList = missionRepository.findMissionsByStore_IdAndIdLessThanOrderByIdDesc(
                            storeId,
                            idCursor,
                            pageRequest
                    );
                    break;
                default:
                    throw new MissionException(MissionErrorCode.QUERY_NOT_VALID);
            }

        } else {

            // 커서 없이 조회
            missionList = missionRepository.findMissionsByStore_IdOrderByIdDesc(
                    storeId,
                    pageRequest
            );
        }

        List<Mission> content = missionList.getContent();
        if (content.isEmpty()) {
            nextCursor = "-1";
        } else {
            Mission lastMission = content.get(content.size() - 1);
            nextCursor = lastMission.getId()
                    + ":"
                    + lastMission.getId();
        }

        // 미션들 응답 DTO로 포장하기
        return MissionConverter.toPagination(
                missionList.map(MissionConverter::toGetMission).toList(),
                missionList.hasNext(),
                nextCursor,
                missionList.getSize()
        );
    }

}
