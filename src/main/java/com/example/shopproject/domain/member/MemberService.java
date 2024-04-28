package com.example.shopproject.domain.member;

import com.example.shopproject.domain.member.Member;
import com.example.shopproject.domain.member.MemberRepository;
import com.example.shopproject.domain.member.request.MemberCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Member join(final MemberCreateRequest request){
        return memberRepository.save(request.toEntity());
    }



}
