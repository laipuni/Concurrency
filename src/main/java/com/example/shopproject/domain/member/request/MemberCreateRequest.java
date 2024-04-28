package com.example.shopproject.domain.member.request;

import com.example.shopproject.domain.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberCreateRequest {

    private String nickname;
    private int age;

    @Builder
    private MemberCreateRequest(String nickname, int age) {
        this.nickname = nickname;
        this.age = age;
    }

    public Member toEntity(){
        return Member.builder()
                .nickname(nickname)
                .age(age)
                .build();
    }

}
