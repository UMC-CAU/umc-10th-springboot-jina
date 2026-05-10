package com.example.umc.domain.mission.repository;

import com.example.umc.domain.mission.entity.Mapping.MemberMission;
import com.example.umc.domain.mission.entity.Mission;
import com.example.umc.domain.mission.enums.MissionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.data.domain.Pageable;
import java.util.List;

public interface MissionRepository extends JpaRepository<Mission, Long> {

    @Query("SELECT mm FROM MemberMission mm " +
            "JOIN mm.mission m " +    // MemberMission에서 Mission으로 연결 (MemberMission의 missoion객체로이동)
            "JOIN m.store s " +       // Mission에서 Store로 연결
            "WHERE mm.member.id = :memberId AND mm.missionStatus = :status " +
            "AND (:cursor IS NULL OR mm.id < :cursor) " +
            "ORDER BY mm.id DESC") //(:memberId)는 @Param("memberId")로 들어온 값
    List<MemberMission> findMemberMissions(
            @Param("memberId") Long memberId,
            @Param("status") MissionStatus missionStatus,
            @Param("cursor") Long cursor // @Param은 쿼리문의 :~안의 값에 연결해 값 넣어주는 어노.
    );

        @Query("SELECT m FROM Mission m " +
                "WHERE (:cursor IS NULL OR m.id < :cursor) " +
                "ORDER BY m.id DESC")
        List<Mission> findHomeMissions(@Param("cursor") Long cursor, Pageable pageable);
        //즉 메서드 인자값을 받아와서 쿼리문에 넣어 쿼리문 실행.

        // 2. 진행도 카운트 (MemberMissionRepository를 따로 안 만들고 여기에 합침)
        @Query("SELECT COUNT(mm) FROM MemberMission mm " +
                "WHERE mm.member.id = :memberId AND mm.missionStatus = 'CHALLENGING'")
        Integer countChallengingMissions(@Param("memberId") Long memberId);



}

