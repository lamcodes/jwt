package com.zkp.jwt.TEst;
import com.zkp.jwt.pojo.User;
import org.springframework.beans.BeanUtils;
import com.zkp.jwt.pojo.*;
import org.springframework.web.method.HandlerMethod;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;


public class t1 {
    String nma ="赵四";
    @PermissionTag
    String age = "15";
    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        User.getArrayList().add("s1");
//        System.out.println(User.getArrayList());
//        User.getArrayList().add("s2");
//        System.out.println(User.getArrayList());
//        string = new StringBuilder("diyci");
//        System.out.println(string);
//        ts();
//        System.out.println(string);
//        User user = new User(11,"zkp","mima");
//        myuser myuser1 = new myuser(10,"xiaomi");
//        myuser myuser2 = new myuser();
//        BeanUtils.copyProperties(myuser1,user);
//        System.out.println(user.toString());

//        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
//        ForkJoinTask<?> name = forkJoinPool.submit(() -> {
//            System.out.println("name");
//            return 11;
//        });
//      //  name.fork();
//        //name.join();
//        Object o = name.get();
//        System.out.println(o);

//        String name1 = "null";
//
//        System.out.println( Optional.ofNullable(name1).orElse("0"));
        Field[] declaredFields = t1.class.getDeclaredFields();
        for (Field declaredField : declaredFields) {
           // System.out.println(declaredField);
            if (declaredField.isAnnotationPresent(PermissionTag.class)){
                System.out.println(declaredField+" 有注解");
            }
        }

    }
    @PermissionTag
    public static void ts(   ){
//        HandlerMethod handlerMethod = (HandlerMethod)handler;
//        PermissionTag methodAnnotation = (PermissionTag)handlerMethod.getMethodAnnotation(PermissionTag.class);
//        PermissionTag classAnnotation = (PermissionTag)handlerMethod.getMethod().getDeclaringClass().getAnnotation(PermissionTag.class);

        System.out.println( "----");
    }
}
