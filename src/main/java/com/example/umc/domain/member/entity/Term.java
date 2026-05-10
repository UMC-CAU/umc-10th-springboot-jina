package com.example.umc.domain.member.entity;

import com.example.umc.domain.member.entity.mapping.MemberPreferenceFood;
import com.example.umc.domain.member.entity.mapping.MemberTerm;
import com.example.umc.domain.member.enums.TermName;
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
@Table(name = "term")
public class Term extends BaseEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "name", nullable = false)
    @Enumerated(EnumType.STRING)
    private TermName name;

    @Column(name = "is_required", nullable = false)
    private Boolean isRequired;

    @OneToMany(mappedBy = "term", cascade = CascadeType.ALL)
    private List<MemberTerm> memberTermList = new ArrayList<>();
}
