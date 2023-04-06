package com.DWmarket.market.dto;

import com.DWmarket.market.constant.Gender;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class MemberFormDto {
    @NotBlank(message="이름은 필수 입력값입니다.")
    private String name;

    @NotEmpty(message="이메일은 필수 입력값입니다.")
    @Email(message="이메일 형식으로 입력해주세요.")
    private String email;

    @NotEmpty(message="비밀번호는 필수 입력값입니다.")
    @Length(min=8, max=20)
    @Pattern(regexp="(?=.*[0-9])(?=.*\\W)(?=\\S+$).{8,20}",
            message = "비밀번호는 영문와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다.")
    private String password;


    @Length(min=6, max=16, message="비밀번호가 다릅니다.")
    private String pass;

    @NotEmpty(message="주소는 필수 입력값입니다.")
    private String address;


    private String interest;

    @NotEmpty
    @Length(message = "13자리 까지 입력하세요")
    private String phone;

    private Gender gender;

}
