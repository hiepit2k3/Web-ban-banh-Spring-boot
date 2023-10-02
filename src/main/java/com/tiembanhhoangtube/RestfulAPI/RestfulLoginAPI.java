package com.tiembanhhoangtube.RestfulAPI;

import com.tiembanhhoangtube.Repository.AccountRepository;
import com.tiembanhhoangtube.Service.AccountService;
import com.tiembanhhoangtube.Service.CartitemService;
import com.tiembanhhoangtube.Service.impl.AccountServiceimpl;
import com.tiembanhhoangtube.entity.Account;
import com.tiembanhhoangtube.model.accountDto;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class RestfulLoginAPI {
    @Autowired
    HttpSession session;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AccountService accountService;

    @Autowired
    CartitemService cartitemService;

    @Autowired
    AccountServiceimpl accountServiceimpl;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/authentication/register")
    public ResponseEntity<?> register( @RequestBody accountDto dto, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body("Invalid data format.");
        }

        Optional<Account> existingAccount = accountService.findByUsername(dto.getUsername());
        if (existingAccount.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists.");
        }

        Account newAccount = new Account();
        BeanUtils.copyProperties(dto, newAccount);
        newAccount.setPassword(bCryptPasswordEncoder.encode(newAccount.getPassword()));
        newAccount.setRole("ROLE_USER");
        accountRepository.save(newAccount);

        return ResponseEntity.ok("Successful account registration");
    }

    @GetMapping("api/login/google")
    public ResponseEntity<?> success(OAuth2AuthenticationToken oauth2){
        System.out.println("data google: "+oauth2);
        String username = oauth2.getPrincipal().getAttribute("sub");
        Account account =  new Account();
        account.setEmail(oauth2.getPrincipal().getAttribute("email"));
        account.setFullname(oauth2.getPrincipal().getAttribute("name"));
        account.setUsername(username);
        account.setPassword("123");
        account.setRole("ROLE_USER");
        System.out.println("Account:"+account);
        accountServiceimpl.AuthenGoogle(account);
        return ResponseEntity.status(HttpStatus.FOUND)
                .header("Location", "http://localhost:8080/tiembanhhoangtube/index")
                .build();
    }

    @GetMapping("/seach/id={username}")
    public Optional<Account> seach(@PathVariable("username") String username){
        Optional<Account> account = accountRepository.findByUsername(username);
        return account;
    }

}
