package com.zkp.jwt.testcopy;

import org.springframework.beans.BeanUtils;

public class t {
    public static void main(String[] args) {
//        user1 user1 = new user1();
//        user1.setX(1);
//        user1.setName("xxxx");
//        user1.setIds("12345");
//
//        USer2 uSer2 = new USer2();
//        BeanUtils.copyProperties(user1,uSer2);
//        System.out.println(uSer2.toString());
        new Thread(()->{
            System.out.println("name is"+Thread.currentThread().getName());
        }).start();
        System.out.println("snifasd name is"+Thread.currentThread().getName());
    }
}
