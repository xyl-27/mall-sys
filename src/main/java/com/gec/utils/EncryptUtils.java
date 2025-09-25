package com.gec.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 加密工具类，用于密码加密和验证
 */
public class EncryptUtils {
    
    /**
     * 加密方法 - 调用MD5实现
     * @param str 要加密的字符串
     * @return 加密后的字符串
     */
    public static String encrypt(String str) {
        return md5(str);
    }
    
    /**
     * MD5加密方法
     * @param str 要加密的字符串
     * @return 加密后的字符串
     */
    public static String md5(String str) {
        try {
            // 获取MD5实例
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 计算MD5哈希值
            byte[] bytes = md.digest(str.getBytes());
            // 将字节数组转换为十六进制字符串
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                String hex = Integer.toHexString(b & 0xFF);
                if (hex.length() == 1) {
                    sb.append('0');
                }
                sb.append(hex);
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * 验证密码是否匹配
     * @param inputPassword 输入的密码
     * @param storedPassword 存储的加密密码
     * @return 是否匹配
     */
    public static boolean verifyPassword(String inputPassword, String storedPassword) {
        if (inputPassword == null || storedPassword == null) {
            return false;
        }
        String encryptedInput = md5(inputPassword);
        return storedPassword.equals(encryptedInput);
    }
}