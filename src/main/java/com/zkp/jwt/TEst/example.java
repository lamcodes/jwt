package com.zkp.jwt.TEst;

import org.bouncycastle.util.encoders.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.Security;

public class example {
    //添加AES/ECB/PKCS7Padding的加密与解密方式
    static {
        if (Security.getProvider("BC") == null) {
            Security.addProvider(new BouncyCastleProvider());
        } else {
            Security.removeProvider("BC");
            Security.addProvider(new BouncyCastleProvider());
        }
    }

    //AES/ECB/PKCS5Padding加密
    private static String encode(String data, String privateKey) {
        try {
            Key key = generateKey(privateKey);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] bytes = cipher.doFinal(data.getBytes());
            return new String(Base64.encode(bytes));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //AES/ECB/PKCS5Padding解密
    private static String decode(String data, String privateKey) {
        try {
            Key key = generateKey(privateKey);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] bytes = cipher.doFinal(Base64.decode(data.getBytes()));
            return new String(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //AES/ECB/PKCS7Padding加密
    private static byte[] encodeWithPKCS7Padding(String data, String privateKey) {
        try {
            Key key = generateKey(privateKey);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS7Padding", "BC");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] bytes = cipher.doFinal(data.getBytes());
            return Base64.encode(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //AES/ECB/PKCS7Padding解密
    private static String decodeWithPKCS7Padding(byte[] data, String privateKey) {
        try {
            Key key = generateKey(privateKey);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS7Padding", "BC");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] bytes = cipher.doFinal(Base64.decode(data));
            return new String(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Key generateKey(String privateKey) {
        return new SecretKeySpec(privateKey.getBytes(), "AES");
    }

    public static void main(String[] args) {
        //密钥需要16位
        String privateKey = "12349876abcdzyxw";
        String data = "This is a demo.";

        String encode = encode(data, privateKey);
        String decode = decode(encode, privateKey);
        System.out.println("加密前数据：" + data + "\n加密后数据：" + encode + "\n解密数据：" + decode);

        System.out.println("----------------------------------------------------------------------");

        byte[] encodeWithPKCS7Padding = encodeWithPKCS7Padding(data, privateKey);
        String decodeWithPKCS7Padding = decodeWithPKCS7Padding(encodeWithPKCS7Padding, privateKey);
        System.out.println("加密前数据：" + data + "\n加密后数据：" + encodeWithPKCS7Padding + "\n解密数据：" + decodeWithPKCS7Padding);
    }
}
