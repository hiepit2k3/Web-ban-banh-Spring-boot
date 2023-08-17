package com.tiembanhhoangtube.RestfulAPI;


import com.tiembanhhoangtube.Service.AccountService;
import com.tiembanhhoangtube.Service.StogareService;
import com.tiembanhhoangtube.entity.Account;
import com.tiembanhhoangtube.model.accountDto;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.UUID;

@CrossOrigin("*")
@RestController
@RequestMapping("users")
public class AccountProfileUserController {
    @Autowired
    HttpSession session;
    @Autowired
    AccountService accountService;
    @Autowired
    StogareService stogareService;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("profile")
    public ResponseEntity<Account> getProfile() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        Optional<Account> account = accountService.findByUsername(username);
        if (account.isPresent()) {
            return ResponseEntity.ok(account.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("profile/update/add")
    public ResponseEntity<?> updateProfile(accountDto dto, @RequestParam("imageFile") MultipartFile imageFile) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        Optional<Account> existingAccount = accountService.findByUsername(username);
        System.out.println("mật khẩu"+existingAccount.get().getPassword());
        System.out.println("email put về:" + dto.getEmail());
        if (dto.getEmail().equals(existingAccount.get().getEmail())) {
            return ResponseEntity.badRequest().body("Email already exists.");
        }
        // Rest of your code to update the profile
        dto.setRole(existingAccount.get().getRole());
        Account account = new Account();
        BeanUtils.copyProperties(dto, account);

        if (!imageFile.isEmpty()) {
            UUID uuid = UUID.randomUUID();
            String uuidString = uuid.toString();
            account.setImage(stogareService.getStogaredFilename(dto.getImageFile(), uuidString));
            stogareService.store(dto.getImageFile(), account.getImage());
        }
        accountService.save(account);
        return ResponseEntity.ok("Success");
    }

    @PutMapping("profile/changePassword")
    public ResponseEntity<?> changePassword(@RequestBody accountDto dto){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        Optional<Account> existingAccount = accountService.findByUsername(username);
        System.out.println("pass database:"+existingAccount.get().getPassword());
        System.out.println("pass người dùng nhập:"+dto);
        if(!bCryptPasswordEncoder.matches(dto.getPassword(),existingAccount.get().getPassword())){
            return ResponseEntity.badRequest().body("Mật khẩu cũ không đúng");
        }
        Account account = existingAccount.get();
        account.setPassword(bCryptPasswordEncoder.encode(dto.getNewpassword()));
        accountService.save(account);
        return ResponseEntity.ok("Success");
    }
}
