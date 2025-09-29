package com.kamerrezz.simpleauth.util;

import org.mindrot.jbcrypt.BCrypt;

import java.security.SecureRandom;

public class PasswordUtils {
    private static final int BCRYPT_ROUNDS = 12;
    private static final SecureRandom random = new SecureRandom();
    
    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(BCRYPT_ROUNDS));
    }
    
    public static boolean verifyPassword(String password, String hash) {
        try {
            return BCrypt.checkpw(password, hash);
        } catch (Exception e) {
            return false;
        }
    }
    
    public static boolean isValidPassword(String password) {
        if (password == null || password.length() < 4) {
            return false;
        }
        if (password.length() > 32) {
            return false;
        }
        return !password.contains(" ");
    }
    
    public static String generateTemporaryPassword() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder password = new StringBuilder();
        
        for (int i = 0; i < 8; i++) {
            password.append(chars.charAt(random.nextInt(chars.length())));
        }
        
        return password.toString();
    }
}