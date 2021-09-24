package com.zkp.jwt;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.json.GsonBuilderUtils;

import java.math.BigDecimal;
import java.net.URLDecoder;

@Slf4j
public class tt {


    private static final Logger logger = LoggerFactory.getLogger(tt.class);

    public static void main(String[] args) throws Exception {
//        Runnable runnable = () -> System.out.println(Thread.currentThread().getName());
//        Thread thread = new Thread(runnable);
//        thread.start();
//        //logger.info("Hello, this is a line of log message logged by Logback");
//        String s = " 浙江大学你好知道不知道这个  ";
//        StringBuffer s1 = new StringBuffer(s);
//        System.out.println(StringUtils.trim(s)+"----");
//
//        String substring = StringUtils.substring(s1.toString(), 0, 5);
//        System.out.println(substring);



        String myStr = "Hello World";

        try{
            throw new IllegalAccessException("yichangceshi1");
        }catch (Exception e){
            log.info("wode1 ----",e);
        }
// ...
        int pos = myStr.indexOf("d");
        int x1 = myStr.indexOf('d');
        System.out.println(pos);
        System.out.println(x1);
        int q = 10;
        float a = 189.99f;
        log.info("nihao{}",a);
        log.info( ss());

        BigDecimal a1 = new BigDecimal("189.99");
        BigDecimal x =a1.divide(new BigDecimal("10"));
        System.out.println(x);
        String name=java.net.URLEncoder.encode("测试", "UTF-8");
        System.out.println("编码"+name);
        System.out.println(URLDecoder.decode(name,"utf-8"));
        name=java.net.URLEncoder.encode(name,"UTF-8");
        System.out.println("二次编码"+name);
        name=java.net.URLDecoder.decode(name, "UTF-8");
        System.out.println("一次解码"+name);
        System.out.println("二次解码"+java.net.URLDecoder.decode(name, "UTF-8"));

        int b=1;
        for (int i = 0 ;i<10;i++){
            if (i==80){
                b=100;
            }
        }
//        if (true){
//            throw new Exception("zheshgie1cuow");
//         //   logger.error("sssss");
//        }
//        try{
//            throw new Exception("参数越界");
//
//        }catch(Exception e) {
//            e.printStackTrace();
//            logger.info("sss",e);
//
//        }

        System.out.println(b);
        b=4;
        System.out.println(b);
    }
    public static String ss(){
        return "sssssss";
    }

}
