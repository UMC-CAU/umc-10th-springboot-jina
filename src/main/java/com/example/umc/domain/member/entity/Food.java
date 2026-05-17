package com.example.umc.domain.member.entity;

import com.example.umc.domain.member.entity.mapping.MemberPreferenceFood;
import com.example.umc.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "food")
public class Food extends BaseEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "food", cascade = CascadeType.ALL)
    @Builder.Default
    private List<MemberPreferenceFood> memberFoodList = new ArrayList<>();

}
