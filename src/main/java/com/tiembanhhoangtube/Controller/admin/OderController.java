package com.tiembanhhoangtube.Controller.admin;


import com.tiembanhhoangtube.Service.AccountService;
import com.tiembanhhoangtube.Service.OrderService;
import com.tiembanhhoangtube.Service.StogareService;
import com.tiembanhhoangtube.entity.Account;
import com.tiembanhhoangtube.utisl.AuthenticationUtils;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("admin")
public class OderController {
    @Autowired
    OrderService orderService;

    @Autowired
    StogareService stogareService;

    @Autowired
    HttpSession session;

    @Autowired
    AccountService accountService;

    @GetMapping("order")
    public String oder(Model model) {
        String username = (String) session.getAttribute("username");
        Account account = accountService.findByUsername(username);
        if(!AuthenticationUtils.isUserLoggedIn(session)){
            return "redirect:/account/loginandregister";
        }
        if(account.getRole() == false){
            return "redirect:/account/loginandregister";
        }

        model.addAttribute("orders", orderService.getOrdersWithDetailsAndProducts());
        return "admin/Admin-Oder";
    }

    @GetMapping("/images/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename){
        System.out.println("--------------------------------------------------------------------------------- -" + filename);
        Resource file = stogareService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\""+file.getFilename() +"\"").body(file);
    }
}
