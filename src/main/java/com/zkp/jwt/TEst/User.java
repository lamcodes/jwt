package com.zkp.jwt.TEst;

import java.util.ArrayList;

public class User {
    private static String s = null;
    private static ArrayList<String> arrayList = new ArrayList<>();
    public static StringBuilder string =null;

    public User(String s, String zkp, String sss) {
    }

    public static String getS() {
        return s;
    }

    public static ArrayList<String> getArrayList() {
        return arrayList;
    }
}
