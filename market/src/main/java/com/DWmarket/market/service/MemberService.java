package com.DWmarket.market.service;

import com.DWmarket.market.Repository.MemberRepository;
import com.DWmarket.market.dto.MemberFormDto;
import com.DWmarket.market.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository mrep;

    public Member saveMember(Member mem){
        validatedup(mem);

        return mrep.save(mem);
    }

    private void validatedup(Member member){
        Member findm = mrep.findByEmail(member.getEmail());
        if( findm !=null ){
            throw new IllegalStateException("이미 가입된 이메일 입니다.");
        }

    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = mrep.findByEmail(email);
        if(member ==null){  //이메일 조회 실패
            throw new UsernameNotFoundException(email);
        }
        return User.builder().username(member.getEmail()).password(member.getPassword()).roles(member.getRole().toString()).build();
    }


    // 회원정보 수정
    // 회원정보 수정

}
