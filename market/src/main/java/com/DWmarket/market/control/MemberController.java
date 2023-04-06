package com.DWmarket.market.control;



import com.DWmarket.market.constant.Gender;
import com.DWmarket.market.dto.MemberFormDto;
import com.DWmarket.market.entity.Member;
import com.DWmarket.market.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;



@RequestMapping("/members")
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    private final PasswordEncoder passwordEncoder;

    @GetMapping(value = "/new")
    public String memberForm(Model model){
        model.addAttribute("memberFormDto",new MemberFormDto());
        return "member/memberForm";
    }

    @PostMapping(value = "/new")
    public String memberForm(@Valid MemberFormDto memberFormDto, BindingResult br, Model model){
        System.out.println( memberFormDto.getAddress());
        if(br.hasErrors()){
            return "member/memberForm";
        }
        try{
            Member mem = Member.createMember(memberFormDto, passwordEncoder );
            memberService.saveMember(mem);
        }catch(Exception e){
            model.addAttribute("errorMessage", e.getMessage());
            return "member/memberForm";
        }
        return "redirect:/";

    }
    @GetMapping("/login")
    public String loginMember(){
        return "/member/memberLoginForm";
    }

    @GetMapping("/login/error")
    public String loginError(Model model){
        model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요");
        return "/member/memberLoginForm";
    }


    @ModelAttribute("interest")
    public Map<String, String> interest(){
        Map<String, String> interest = new LinkedHashMap<>();
        interest.put("sports","스포츠");
        interest.put("furniture","가구");
        interest.put("baby","유아");
        interest.put("game","게임");
        interest.put("digital","디지털");
        interest.put("ticket","티켓/쿠폰");
        interest.put("pet","반려동물용품");
        interest.put("car","자동차용품");
        interest.put("free","무료나눔");

        return interest;
    }

    @GetMapping(value = "/mypage")
    public String updateMember(){
        return "member/MemberMypageForm";
    }


//    @GetMapping(value = "/mypage")
//    public String updateMember(@AuthenticationPrincipal User user){ //현재 계정 로그인한거 볼수있음.
//
//        System.out.println(user.getUsername());
//        return "member/MemberMypageForm";
//    }

    // 수정부분
    @PostMapping("/change/{memberId}")
    public String memberUpdate(@Valid MemberFormDto memberFormDto, BindingResult br,
                               Model model){
        if(br.hasErrors()){
            return "member/memberForm";
        }

//        if(memberFormDto.getAddress()== null && itemImgFileList.get(0).isEmpty()){
//            model.addAttribute("errorMessage", "");
//            return "item/itemForm";
//        }

//        if(memberFormDto.get ()== null){
//            model.addAttribute("errorMessage", "");
//            return "item/itemForm";
//        }

        try{
            memberService.memberUpdate(memberFormDto);
        }catch (Exception e){
            model.addAttribute("errorMessage", "정보 수정 중 에러가 발생하였습니다.");
            return "item/itemForm";
        }
        return "redirect:/";
    }


}
