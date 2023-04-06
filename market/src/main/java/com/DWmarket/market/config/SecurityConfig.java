package com.DWmarket.market.config;

import com.DWmarket.market.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    MemberService memberService;

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.formLogin()
                .loginPage("/members/login")
                .defaultSuccessUrl("/")
                .usernameParameter("email")
                .failureUrl("/members/login/error")   // 로그인 실패시?
                .and().logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/members/logout"))
                .logoutSuccessUrl("/");   // 로그인 성공하면 메인 페이지로 가라


        // 상품 등록
        http.authorizeRequests()
                .mvcMatchers("/", "/members/**", "/item/**", "/images/**")   //주소에 이런식으로 표현되는 경로에 대해서는 모든 유저에 대하여 권한을 주겠다.
                .permitAll().mvcMatchers("/admin/**").hasRole("ADMIN")  //주소에 이런식으로 표현되는 경로에 대해서는 관리자에 대하여만 권한을 주겠다.
                .anyRequest().authenticated();


        http.exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint());
        // 인증되지 않은 사용자가 접근할 경우 수행되는 작업




    }


    // 상품 등록관련
    @Override
    public void configure(WebSecurity web) throws Exception{
        web.ignoring().antMatchers("/css/**", "/js/**", "/image/**");
        // static 디렉토리 하위 파일에 대해 인증 없이 접근 가능하게 설정
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(memberService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
