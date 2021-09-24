package com.zkp.jwt.TEst;
import com.zkp.jwt.pojo.User;
import org.springframework.beans.BeanUtils;
import com.zkp.jwt.pojo.*;


public class t1 {
    public static void main(String[] args) {
//        User.getArrayList().add("s1");
//        System.out.println(User.getArrayList());
//        User.getArrayList().add("s2");
//        System.out.println(User.getArrayList());
//        string = new StringBuilder("diyci");
//        System.out.println(string);
//        ts();
//        System.out.println(string);
        User user = new User(11,"zkp","mima");
        myuser myuser1 = new myuser(10,"xiaomi");
        myuser myuser2 = new myuser();
        BeanUtils.copyProperties(myuser1,user);
        System.out.println(user.toString());

        int x =10;
        for (int i =0;i<10;i++){
            x++;
        }
        System.out.println(x);
    }
//    public static void ts(){
//        string.append("ninhao");
//    }
}
