package com.tiembanhhoangtube.RestfulAPI;

import com.tiembanhhoangtube.Repository.AccountRepository;
import com.tiembanhhoangtube.Service.AccountService;
import com.tiembanhhoangtube.Service.StogareService;
import com.tiembanhhoangtube.entity.Account;
import com.tiembanhhoangtube.model.accountDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("admin/customers")
public class RestfulAccountController {
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    StogareService stogareService;

    @Autowired
    AccountService accountService;
    @GetMapping("/edit/id={accountId}")
    public ResponseEntity<?> edit(@PathVariable("accountId") Long accountId) {
        Optional<Account> opt = accountService.findById(accountId);
        accountDto dto = new accountDto();
        if (opt.isPresent()) {
            Account entity = opt.get();
            BeanUtils.copyProperties(entity, dto);

            dto.setRole(entity.getRole());
            System.out.println("data: "+opt);
            return ResponseEntity.ok(opt);
        }
        return ResponseEntity.ok("ko co tai khoan");
    }
    @DeleteMapping("/delete/id={customerId}")
    public ResponseEntity<?> deleteCustomer(@PathVariable("customerId") Long customerId) throws Exception {
        Account account = accountService.findByAccountId(customerId);
        if (account != null) {
            if (StringUtils.isEmpty(account.getImage())) {
                stogareService.delete(account.getImage());
            }
        }
        accountService.delete(account);
        return ResponseEntity.ok("Successfully deleted");
    }


    @PutMapping("update")
    public ResponseEntity<?> update(accountDto dto ,@RequestParam("imageFile") MultipartFile imageFile){
        Account account = new Account();
        BeanUtils.copyProperties(dto,account);
        if (!imageFile.isEmpty()) {
            UUID uuid = UUID.randomUUID();
            String uuidString = uuid.toString();
            System.out.println("Tên File: "+uuidString);
            account.setImage(stogareService.getStogaredFilename(dto.getImageFile(), uuidString));
            stogareService.store(dto.getImageFile(), account.getImage());
        }
        accountService.save(account);
        return ResponseEntity.ok(account);
    }

    @PostMapping("add")
    public ResponseEntity<?> addProject(@ModelAttribute accountDto dto,
                                              @RequestParam("imageFile") MultipartFile imageFile) {
        System.out.println("Account data: " + dto);
        Optional<Account> account = accountService.findByUsername(dto.getUsername());
        if(account.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username available");
        }
        try {
            Account entity = new Account();
            BeanUtils.copyProperties(dto, entity);
            if (!imageFile.isEmpty()) {
                UUID uuid = UUID.randomUUID();
                String uuidString = uuid.toString();
                System.out.println("Tên File: "+uuidString);
                entity.setImage(stogareService.getStogaredFilename(dto.getImageFile(), uuidString));
                stogareService.store(dto.getImageFile(), entity.getImage());
            }
            accountService.save(entity);
            return ResponseEntity.ok(entity);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("profile")
    public ResponseEntity<?> getProfile(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();

        Optional<Account> account = accountService.findByUsername(username);
        return ResponseEntity.ok(account);
    }
}
