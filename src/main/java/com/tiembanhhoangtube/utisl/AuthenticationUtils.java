package com.tiembanhhoangtube.utisl;

import jakarta.servlet.http.HttpSession;

public class AuthenticationUtils {
    public static boolean isUserLoggedIn(HttpSession session) {
        System.out.println("có tồn tại không: "+session.getAttribute("username"));
        return session.getAttribute("username") != null;
    }
}