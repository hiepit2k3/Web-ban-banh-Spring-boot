package com.tiembanhhoangtube.Controller.admin;


import com.tiembanhhoangtube.Repository.AccountRepository;
import com.tiembanhhoangtube.Service.AccountService;
import com.tiembanhhoangtube.Service.StogareService;
import com.tiembanhhoangtube.entity.Account;
import com.tiembanhhoangtube.model.accountDto;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("admin/customers")
public class CustomerController {
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    StogareService  stogareService;

    @Autowired
    AccountService accountService;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("add")
    public String add(Model model) {
        model.addAttribute("customer", new Account());
        return "admin/Admin-account";
    }

    @PostMapping("/AddOrEdit")
    public ModelAndView saveOrUpdate(ModelMap model, @Valid @ModelAttribute("customer") accountDto dto, BindingResult result) {
        if (result.hasErrors()) {
            System.out.println("Loi: " + result.toString());
            return new ModelAndView("admin/Admin-account");
        }
        Account account = accountService.findByAccountId(dto.getAccountId());
        Account entity = new Account();
        BeanUtils.copyProperties(dto, entity);
        if (!dto.getImageFile().isEmpty()) {
            UUID uuid = UUID.randomUUID();
            String uuidString = uuid.toString();
            System.out.println("Tên File: "+uuidString);
            entity.setImage(stogareService.getStogaredFilename(dto.getImageFile(), uuidString));
            stogareService.store(dto.getImageFile(), entity.getImage());
        }
        accountService.save(entity);
        return new ModelAndView("redirect:/admin/customers", model);
    }


    @GetMapping("/edit/id={accountId}")
    public ModelAndView edit(ModelMap model, @PathVariable("accountId") Long accountId) {
        Optional<Account> opt = accountService.findById(accountId);
        accountDto dto = new accountDto();
        System.out.println("Qua");
        if (opt.isPresent()) {
            Account entity = opt.get();
            BeanUtils.copyProperties(entity, dto);

            dto.setRole(entity.getRole());
            System.out.println("chức vụ: " + dto.getRole());
            model.addAttribute("customer", dto);
            return new ModelAndView("admin/Admin-account", model);

        }
        model.addAttribute("messages", "CA");
        return new ModelAndView("forward:/admin/customers", model);
    }

    @GetMapping("/delete/id={customerId}")
    public  ModelAndView deleteCustomer(ModelMap model, @PathVariable("customerId") Long customerId) throws Exception {
        Optional<Account> otp = accountService.findById(customerId);
        if(otp.isPresent()) {
            if(!StringUtils.isEmpty(otp.get().getImage())) {
                stogareService.delete(otp.get().getImage());
            }
            else {
                return new ModelAndView("admin/Admin-account", model);
            }
        }
        accountService.delete(otp.get());
        return new ModelAndView("forward:/admin/customers",model);
    }

    @GetMapping("")
    public String list(ModelMap model, @ModelAttribute("customer") Account customer) {
        /*model.addAttribute("")*/
        System.out.println("data");
        return "admin/Admin-account";
    }

    @ModelAttribute("customers")
    public List<Account> getAllcategory() {
        List<Account> list = accountService.findAll();
        return list;
    }

    @GetMapping("/images/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename){
        System.out.println("--------------------------------------------------------------------------------- -" + filename);
        Resource file = stogareService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\""+file.getFilename() +"\"").body(file);
    }

    @PostMapping("update")
    public ResponseEntity<String> update(ModelMap model, @Valid @ModelAttribute("customer") accountDto dto, BindingResult result) {
        System.out.println("pass mơi: " + dto);
        if (result.hasErrors()) {
            System.out.println("Loi: " + result.toString());
            return ResponseEntity.badRequest().body("Có lỗi xảy ra"); // Trả về lỗi nếu có lỗi xảy ra
        }
        // Các xử lý khác...
        Account entity = new Account();
        Account account = accountService.findByUsername(dto.getUsername());
        System.out.println("Account cũ:"+account);
        if(!bCryptPasswordEncoder.matches(dto.getPassword(), account.getPassword())){
            System.out.println("Mật khẩu cũ không đúng");
            return ResponseEntity.badRequest().body("Có lỗi xảy ra"); // Trả về lỗi nếu có lỗi xảy ra
        }
        BeanUtils.copyProperties(dto, entity);
        entity.setPassword(dto.getNewpassword());
        if (!dto.getImageFile().isEmpty()) {
            UUID uuid = UUID.randomUUID();
            String uuidString = uuid.toString();
            System.out.println("Tên File: "+uuidString);
            entity.setImage(stogareService.getStogaredFilename(dto.getImageFile(), uuidString));
            stogareService.store(dto.getImageFile(), entity.getImage());
        }
        accountService.save(entity);
        return ResponseEntity.ok("Cập nhật thành công"); // Trả về thành công nếu không có lỗi
    }
}
