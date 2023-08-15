package com.tiembanhhoangtube.Controller;

import com.tiembanhhoangtube.Repository.ProductRepository;
import com.tiembanhhoangtube.Service.AccountService;
import com.tiembanhhoangtube.Service.CartitemService;
import com.tiembanhhoangtube.Service.ProductService;
import com.tiembanhhoangtube.entity.Account;
import com.tiembanhhoangtube.entity.Product;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("tiembanhhoangtube")
public class indexController {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    AccountService accountService;

    @Autowired
    ProductService productService;

    @Autowired
    HttpSession session;

    @Autowired
    CartitemService cartitemService;

    @GetMapping("scrub")
    public String scrub(){
        return "crud";
    }


    @GetMapping("index")
    public ModelAndView getProductPage(ModelMap model, @Valid @RequestParam(value = "page", defaultValue = "0") int page) {
        String name = (String) session.getAttribute("username");
        System.out.println("name :"+name);
        int sl = cartitemService.countByCustomerId(name);
        session.setAttribute("quantity",sl);
        int pageSize = 6;
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Product> listPage = productService.findAll(pageable);
        int totalPages = listPage.getTotalPages();

        model.addAttribute("products", listPage);
        model.addAttribute("page", page + 1);
        System.out.println("A: "+page);
        System.out.println("page: " + listPage);
        model.addAttribute("totalPages", listPage.getTotalPages());
        System.out.println("số page: " + listPage.getTotalPages());
        return new ModelAndView("index", model);
    }

//    @GetMapping("profile")
//    public ModelAndView getProfile(ModelMap model){
//        String username = (String) session.getAttribute("username");
//        if(username == null){
//            return new ModelAndView("redirect:/account/loginandregister",model);
//        }
//        Account account = accountService.findByUsername(username);
//        model.addAttribute("account", account);
//        return new ModelAndView("profile", model);
//
//    }
}
