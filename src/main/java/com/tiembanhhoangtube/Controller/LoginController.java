package com.tiembanhhoangtube.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class LoginController {
    @GetMapping("api/login")
    public String index(){
        return "login-register";
    }
    @GetMapping("user/profile")
    public String profile(ModelMap model){
//        model.addAttribute("")
        return "profile";
    }
}
