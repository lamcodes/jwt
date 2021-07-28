package com.zkp.jwt;

import com.zkp.jwt.pojo.Action;
import lombok.SneakyThrows;

import java.util.Arrays;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class test {
    public static void main(String[] args) throws InterruptedException {
//        Action.week();
//        String name = Action.DELETE.name();
//        System.out.println(name);
//        //返回定义的常量的顺序，从0开始计数，例如：
//        System.out.println(Action.COMMENTS.ordinal());

        Runnable runnable = new Runnable() {
            @SneakyThrows
            @Override
            public void run() {

                System.out.println(System.currentTimeMillis());
                Thread.sleep(10);
                System.out.println(Thread.currentThread());
            }
        };
        runnable.run();
        long l = System.currentTimeMillis();
        System.out.println(l);
        TimeUnit.SECONDS.sleep(1);
        System.out.println(System.currentTimeMillis());



    }

}

