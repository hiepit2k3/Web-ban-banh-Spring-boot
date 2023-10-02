package com.tiembanhhoangtube.Ath;

import com.tiembanhhoangtube.Repository.AccountRepository;
import com.tiembanhhoangtube.config.JwtServicer;
import com.tiembanhhoangtube.entity.Account;
import com.tiembanhhoangtube.model.UserInfoUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.var;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;

@Service
@RequiredArgsConstructor
public class AuthenticationService {


    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtServicer jwtServicer;
    private final AuthenticationManager authenticationManager;
    public AuthenticationResponse register(RegisterRequest request) {
        System.out.println("fullname: "+request.getFullName());
        var user = Account.builder()
                .email(request.getEmail())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .fullname(request.getFullName())
                .role("ROLE_USER")
                .build();
        accountRepository.save(user);
        UserInfoUserDetails account = new UserInfoUserDetails(user);
        var jwtToken = jwtServicer.generateToken(account);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

//    public AuthenticationResponse authentication(AuthenticationResquest request) {
//        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword()));
//        var user = accountRepository.findByUsername(request.getUsername()).orElseThrow();
//        UserInfoUserDetails acc = new UserInfoUserDetails(user);
//        var jwt = jwtServicer.generateToken(acc);
//        return AuthenticationResponse.builder()
//                .token(jwt)
//                .build();
//    }
}
