package com.tiembanhhoangtube.Controller;

import com.tiembanhhoangtube.Repository.AccountRepository;
import com.tiembanhhoangtube.Service.AccountService;
import com.tiembanhhoangtube.Service.CartitemService;
import com.tiembanhhoangtube.entity.Account;
import com.tiembanhhoangtube.model.accountDto;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("account")
public class LoginController {
    @Autowired
    HttpSession session;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AccountService accountService;

    @Autowired
    CartitemService cartitemService;

    @GetMapping("loginandregister")
    public String loadformLogin() {
        return "login-register";
    }

    @PostMapping("register")
    public ModelAndView register(ModelMap model, @Valid accountDto Dto, BindingResult result) {
        System.out.println("Data:" + Dto);
        if (result.hasErrors()) {
            System.out.println("Looix: " + result);
            return new ModelAndView("login-register", model);
        }
        Account entity = accountRepository.findByUsername(Dto.getUsername());
        if (entity != null) {
            model.addAttribute("messageerror", "User đã tồn tại!");
            return new ModelAndView("login-register", model);
        }
        if (!Dto.getPassword().equals(Dto.getComfirmpassword())) {
            model.addAttribute("messageerror", "Mật khẩu xác nhận không trùng khớp");
            return new ModelAndView("login-register", model);
        }
        Account otp = new Account();
        BeanUtils.copyProperties(Dto, otp);
        accountRepository.save(otp);
        model.addAttribute("success", "User đã tồn tại!");

        return new ModelAndView("login-register", model);
    }

    @PostMapping("login")
    public ModelAndView login(ModelMap model, @Valid accountDto Dto, BindingResult result) {
        System.out.println("Acc: "+Dto);
        Account account = accountService.login(Dto.getUsername(), Dto.getPassword());
        if(account != null && account.getRole() == false){
            session.setAttribute("username",account.getUsername());
            return new ModelAndView("redirect:/tiembanhhoangtube/index",model);
        }
        if(account != null && account.getRole() == true){
            session.setAttribute("username",account.getUsername());
            return new ModelAndView("redirect:/admin/index",model);
        }
        model.addAttribute("messageerror","Tài khoản hoặc mật khẩu sai");
        return new ModelAndView("login-register",model);
    }
}
