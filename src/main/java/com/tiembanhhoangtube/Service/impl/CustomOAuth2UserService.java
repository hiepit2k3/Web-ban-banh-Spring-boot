package com.tiembanhhoangtube.Service.impl;

import com.tiembanhhoangtube.Repository.AccountRepository;
import com.tiembanhhoangtube.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    @Autowired
    private AccountRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2AuthenticationToken authenticationToken = (OAuth2AuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        // Lấy email của người dùng
        String email = authenticationToken.getPrincipal().getAttribute("email");

        // Kiểm tra trong cơ sở dữ liệu nếu người dùng đã tồn tại và lấy thông tin vai trò
        Account user = userRepository.findByEmail(email);
        List<GrantedAuthority> authorities = new ArrayList<>();

        if (user != null) {
            authorities.add(new SimpleGrantedAuthority(user.getRole()));
        } else {
            // Nếu người dùng mới, đặt vai trò mặc định là ROLE_USER
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
            // Tạo một bản ghi mới trong cơ sở dữ liệu cho người dùng mới nếu cần
            // user = userRepository.save(new Account(email, "ROLE_USER"));
        }

        // Chuyển hướng dựa trên vai trò của người dùng
        if (authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            // Chuyển hướng đến trang admin
            return new DefaultOAuth2User(authorities, authenticationToken.getPrincipal().getAttributes(), "email");
        } else if (authorities.contains(new SimpleGrantedAuthority("ROLE_USER"))) {
            // Chuyển hướng đến trang user
            return new DefaultOAuth2User(authorities, authenticationToken.getPrincipal().getAttributes(), "email");
        } else {
            throw new IllegalStateException("Unknown role");
        }
    }
}
