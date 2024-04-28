package com.example.shopproject.domain;

import com.example.shopproject.domain.member.Member;
import com.example.shopproject.domain.member.MemberRepository;
import com.example.shopproject.domain.member.MemberService;
import com.example.shopproject.domain.member.request.MemberCreateRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

//    @AfterEach
//    void tearDown(){
//        memberRepository.deleteAllInBatch();
//    }

    @DisplayName("정보를 받아서 새로운 유저를 등록한다.")
    @Test
    void test(){
        //given
        String nickname = "이진호";
        int age = 25;
        MemberCreateRequest request = MemberCreateRequest.builder()
                .nickname(nickname)
                .age(age)
                .build();

        memberService.join(request);

        //when
        List<Member> members = memberRepository.findAll();

        //then
        assertThat(members).hasSize(1)
                .extracting("nickname","age")
                .containsExactlyInAnyOrder(
                        tuple(nickname,age)
                );
    }

}