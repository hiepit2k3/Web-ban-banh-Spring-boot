package com.tiembanhhoangtube.config;

import com.tiembanhhoangtube.Repository.AccountRepository;
import com.tiembanhhoangtube.entity.Account;
import com.tiembanhhoangtube.model.UserInfoUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AccountScurity implements UserDetailsService {
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    BCryptPasswordEncoder pe;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> account = accountRepository.findByUsername(username);
        return account.map(UserInfoUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("user not found" + username));
    }

//    public void loginFromOAuth2(OAuth2AuthenticationToken oauth2) {
// String fullname = oauth2.getPrincipal().getAttribute(“name");
//        String username = oauth2.getPrincipal().getAttribute("name");
//        String password = Long.toHexString(System.currentTimeMillis());
//
//        UserDetails user = User.withUsername(username)
//                .password(pe.encode(password)).roles("GUEST").build();
//
//        Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
//        SecurityContextHolder.getContext().setAuthentication(auth);
//    }
}
