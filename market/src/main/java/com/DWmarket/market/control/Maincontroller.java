package com.DWmarket.market.control;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class Maincontroller {
    @GetMapping("/")
    public String home(){
        return "main";
    }


}
