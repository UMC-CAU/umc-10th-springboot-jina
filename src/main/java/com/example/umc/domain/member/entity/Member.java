package com.example.umc.domain.member.entity;

import com.example.umc.domain.Question.entity.Question;
import com.example.umc.domain.Question.entity.QuestionPhoto;
import com.example.umc.domain.member.enums.Address;
import com.example.umc.domain.member.enums.Gender;
import com.example.umc.domain.member.enums.SocialProvider;
import com.example.umc.domain.review.entity.Review;
import com.example.umc.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "members")
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "gender", nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "birth", nullable = false)
    private LocalDateTime birth;


    @Column(name = "social_provider", nullable = false)
    @Enumerated(EnumType.STRING)
    private SocialProvider socialProvider;

    @Column(name = "social_uid", nullable = false)
    private String socialUid;


    @Column(name = "address", nullable = false)
    @Enumerated(EnumType.STRING)
    private Address address;

    @Column(name = "point", nullable = false)
    private Integer point;

    @Column(name = "profile_url", nullable = false)
    private String profileUrl;


    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Question> questionList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Review> reviewList = new ArrayList<>();
}
