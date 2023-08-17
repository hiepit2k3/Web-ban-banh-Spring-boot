package com.tiembanhhoangtube.Ath;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import java.io.IOException;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private RequestCache requestCache = new HttpSessionRequestCache();

    HttpSession session;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));


        boolean isUser = authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_USER"));
        System.out.println("role "+isAdmin);
        System.out.println("role "+isUser);
        if (isAdmin) {
            // Nếu là admin, chuyển hướng tới trang cho admin
            response.sendRedirect("/admin/index");
            System.out.println("chuyến hướng Admin");
        } else if (isUser) {
            // Nếu là người dùng, chuyển hướng tới trang cho người dùng
            response.sendRedirect("/tiembanhhoangtube/index");
            System.out.println("chuyến hướng User");

        } else {
            // Trường hợp khác, chuyển hướng về trang mặc định
            SavedRequest savedRequest = requestCache.getRequest(request, response);
            if (savedRequest != null) {
                String redirectUrl = savedRequest.getRedirectUrl();
                response.sendRedirect(redirectUrl);
            } else {
                response.sendRedirect("/default"); // Đường dẫn mặc định khi không xác định được vai trò
            }
        }
    }

}


