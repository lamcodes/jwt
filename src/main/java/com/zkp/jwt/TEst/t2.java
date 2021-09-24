package com.zkp.jwt.TEst;

import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.security.Security;

/**
 * @FileName: AESUtil.java
 * @Description: AES加解密工具类，使用详见main方法
 * @Author: yinbing.qiu
 * @Date: 2018/6/12 9:25
 */
@Slf4j
@Component
public class t2 {

    public static String AES_SALT = "vndfjk_&&$_DDVGN#$";
    //public static String AES_SALT;
    static {
        if (Security.getProvider("BC") == null) {
            Security.addProvider(new BouncyCastleProvider());
        } else {
            Security.removeProvider("BC");
            Security.addProvider(new BouncyCastleProvider());
        }
    }
    public void setAesSalt(String aesSalt) {
        AES_SALT = aesSalt;
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        String s = "74ABAEB398962B5FEDC47B0A19BFF797";
        String encyptStr = getEncyptStr(s);
        String decryptStr = getDecryptStr(encyptStr);
        String ss = getDecryptStr(s);
        System.out.println(s);
        System.out.println(ss);
        String encyptStr1 = getEncyptStr(ss);
        System.out.println(encyptStr1);
        // System.out.println("加密前数据：" + s  + "\n加密后数据：" + encyptStr + "\n解密数据：" +decryptStr );
    }
    /**
     * 获得AES加密后的字符串
     *
     * @param content
     * @return
     */
    public static String getEncyptStr(String content) {
        byte[] encryptResult = encrypt(content);
        if (encryptResult != null) {
            return parseByte2HexStr(encryptResult);
        }
        return null;

    }

    /**
     * 解密字符串
     *
     * @param content
     * @return
     */
    public static String getDecryptStr(String content) throws UnsupportedEncodingException {
        byte[] decryptFrom = parseHexStr2Byte(content);
        byte[] decryptResult = decrypt(decryptFrom);
        return new String(decryptResult,"utf-8");

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
             Cipher cipher = Cipher.getInstance("AES");// 创建密码器
            //Cipher cipher = Cipher.getInstance("AES/ECB/PKCS7Padding", "BC");
            byte[] byteContent = content.getBytes("utf-8");
            cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
            byte[] result = cipher.doFinal(byteContent);
            return result; // 加密
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
             Cipher cipher = Cipher.getInstance("AES");// 创建密码器
          //  Cipher cipher = Cipher.getInstance("AES/ECB/PKCS7Padding", "BC");
            cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
            byte[] result = cipher.doFinal(content);
            return result; // 加密
        } catch (Exception e) {
            log.error("AES decrypt failed", e);
        }
        return null;
    }

    /**
     * 将二进制转换成16进制
     *
     * @param buf
     * @return
     */
    public static String parseByte2HexStr(byte buf[]) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 将16进制转换为二进制
     *
     * @param hexStr
     * @return
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1) {
            return null;
        }
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }


}
