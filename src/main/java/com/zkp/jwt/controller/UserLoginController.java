package com.zkp.jwt.controller;

import com.zkp.jwt.pojo.User;
import com.zkp.jwt.until.JWTutils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

@RestController
@RequestMapping("user")
public class UserLoginController {

        @Value("admin")
        private String realUsername;
        @Value(("123456"))
        private String realPassword;


        @GetMapping("login")
        public String login(String username, String password,HttpServletResponse response,HttpServletRequest request) {
            if (username.equals(realUsername) && password.equals(realPassword)) {
                User u = new User();
                u.setPassword(password);
                u.setUsername(username);
//                HttpSession session = request.getSession();
//                session.setAttribute("uersname",JWTutils.getToken(u));
//                Cookie cookie = new Cookie(username, JWTutils.getToken(u));
//                response.addCookie(cookie);
                System.out.println(JWTutils.getClaim(JWTutils.getToken(u),"password"));

                return JWTutils.getToken(u);
            }

            return "登录失败！账号或者密码不对！";
        }

        @GetMapping("test")
        public String test(HttpServletRequest request) {
//            HttpSession session = request.getSession();
//            Object username = session.getAttribute("uersname");
//            System.out.println(username);
//            Cookie[] cookies = request.getCookies();
//            for (Cookie cookie:cookies
//                 ) {
//                if (cookie!=null){
//                    System.out.println(cookie.getName());
//                    System.out.println(cookie.getValue());
//                }
//
//            }
            return "访问test - API";
        }



}
