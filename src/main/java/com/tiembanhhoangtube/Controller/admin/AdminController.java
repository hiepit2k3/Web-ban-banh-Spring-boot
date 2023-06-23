package com.tiembanhhoangtube.Controller.admin;

import com.tiembanhhoangtube.Service.AccountService;
import com.tiembanhhoangtube.entity.Account;
import com.tiembanhhoangtube.utisl.AuthenticationUtils;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@RequestMapping("admin")
public class AdminController {

    @Autowired
    HttpSession session;

    @Autowired
    AccountService accountService;

    @GetMapping("index")
    public String index() {
        String username = (String) session.getAttribute("username");
        Account account = accountService.findByUsername(username);
        if(!AuthenticationUtils.isUserLoggedIn(session)){
            return "redirect:/account/loginandregister";
        }
        if(account.getRole() == false){
            return "redirect:/account/loginandregister";
        }
        return "admin/index";
    }
    @GetMapping("logout")
    public ModelAndView logout(ModelMap model){
        session.removeAttribute("username");
        return new ModelAndView("redirect:/account/loginandregister");
    }

    @GetMapping("profile")
    public ModelAndView getProfile(ModelMap model){
        String username = (String) session.getAttribute("username");
        Account account = accountService.findByUsername(username);
        model.addAttribute("account", account);
        return new ModelAndView("admin/users-profile",model);
    }
    @GetMapping("contact")
    public ModelAndView getContact(ModelMap model){
        return new ModelAndView("admin/pages-contact");
    }
    @GetMapping("err/404")
    public ModelAndView get404(ModelMap model){
        return new ModelAndView("admin/pages-error-404");
    }
    @GetMapping("faq")
    public ModelAndView getfaq(ModelMap model){
        return new ModelAndView("admin/pages-faq");
    }
}
