package com.zkp.jwt.TEst;

import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.SecureRandom;
import java.security.Security;
import java.util.logging.Logger;
@Slf4j
public class jiemi {
    // public static Logger logger = Logger.getLogger("jiemi.class");
    public static String AES_SALT = "vndfjk_&&$_DDVGN#$";
    static {
        if (Security.getProvider("BC") == null) {
            Security.addProvider(new BouncyCastleProvider());
        } else {
            Security.removeProvider("BC");
            Security.addProvider(new BouncyCastleProvider());
        }
    }
    public static void main(String[] args) {
        String date1 = "你好啊";
        byte[] s1 = encrypt(date1);
        String en = new String(s1);

        byte[] decrypt = decrypt(s1);

        String de = new String(decrypt);
        System.out.println("加密前数据：" + date1  + "\n加密后数据：" + s1 + "\n解密数据：" +de );
       // System.out.println("加密前数据：" + date1  + "\n加密后数据：" + en + "\n解密数据：" +de );
        System.out.println("------------------------------");
        String privateKey = "12349876abcdzyxw";
        String data = "This is a demo.";

        String encode = encode(data, privateKey);
        String decode = decode(encode, privateKey);
        System.out.println("加密前数据：" + data + "\n加密后数据：" + encode + "\n解密数据：" + decode);
    }
    /***
     * 加密
     * @param content
     * @return
     */
    public static byte[] encrypt(String content) {

        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(AES_SALT.getBytes());
            kgen.init(128, random);
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            // Cipher cipher = Cipher.getInstance("AES");// 创建密码器
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS7Padding", "BC");
            byte[] byteContent = content.getBytes("utf-8");
            cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
            byte[] result = cipher.doFinal(byteContent);
            return Base64.encode(result); // 加密
        } catch (Exception e) {
            log.error("AES encrypt failed", e);
        }
        return null;
    }

    /**
     * 解密
     *
     * @param content 待解密内容
     * @return
     */
    public static byte[] decrypt(byte[] content) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(AES_SALT.getBytes());
            kgen.init(128, random);
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            // Cipher cipher = Cipher.getInstance("AES");// 创建密码器
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS7Padding", "BC");
            cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
            byte[] result = cipher.doFinal(Base64.decode(content));
            return result; // 加密
        } catch (Exception e) {
            log.error("AES decrypt failed", e);
        }
        return null;
    }
    private static Key generateKey(String privateKey) {
        return new SecretKeySpec(privateKey.getBytes(), "AES");
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
}