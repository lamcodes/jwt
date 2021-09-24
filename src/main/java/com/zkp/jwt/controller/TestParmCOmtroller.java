package com.zkp.jwt.controller;

import com.zkp.jwt.pojo.User;
import com.zkp.jwt.pojo.myuser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestParmCOmtroller {

    @GetMapping("/testparm")
    public String t1(myuser myuser1, Integer x){
//
//        String s = user.toString();
//        StringBuilder stringBuilder = new StringBuilder(s);
//
//        return stringBuilder.append(String.valueOf(x)).toString();
        System.out.println(myuser1.getPassword());
        System.out.println(x);
        return myuser1.toString();
    }

    @GetMapping("/te")
    public String t2(){

        return "方法无参数，但是路径放了参数，不影响该方法。有参数我不用";
    }
}
