package com.tiembanhhoangtube.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class LoginController {
    @GetMapping("api/login")
    public String index(){
        return "login-register";
    }
}
