package com.kamerrezz.simpleauth.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * Utilidades para el manejo seguro de contraseñas
 * Proporciona funciones de hash y verificación usando algoritmos seguros
 * 
 * Opciones de implementación:
 * 1. BCrypt (recomendado) - requiere dependencia externa
 * 2. Argon2 (más moderno) - requiere dependencia externa
 * 
 * Por simplicidad inicial, se usa SHA-256 con salt
 * TODO: Migrar a BCrypt o Argon2 en producción
 */
public class PasswordUtils {
    
    private static final String HASH_ALGORITHM = "SHA-256";
    private static final int SALT_LENGTH = 16;
    private static final SecureRandom random = new SecureRandom();
    
    /**
     * Genera un hash seguro de la contraseña con salt
     */
    public static String hashPassword(String password) {
        try {
            // Generar salt aleatorio
            byte[] salt = new byte[SALT_LENGTH];
            random.nextBytes(salt);
            
            // Crear hash con salt
            MessageDigest md = MessageDigest.getInstance(HASH_ALGORITHM);
            md.update(salt);
            byte[] hashedPassword = md.digest(password.getBytes());
            
            // Combinar salt + hash y codificar en Base64
            byte[] combined = new byte[salt.length + hashedPassword.length];
            System.arraycopy(salt, 0, combined, 0, salt.length);
            System.arraycopy(hashedPassword, 0, combined, salt.length, hashedPassword.length);
            
            return Base64.getEncoder().encodeToString(combined);
            
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al hashear contraseña", e);
        }
    }
    
    /**
     * Verifica si una contraseña coincide con el hash almacenado
     */
    public static boolean verifyPassword(String password, String storedHash) {
        try {
            // Decodificar el hash almacenado
            byte[] combined = Base64.getDecoder().decode(storedHash);
            
            // Extraer salt y hash
            byte[] salt = new byte[SALT_LENGTH];
            byte[] storedPasswordHash = new byte[combined.length - SALT_LENGTH];
            System.arraycopy(combined, 0, salt, 0, SALT_LENGTH);
            System.arraycopy(combined, SALT_LENGTH, storedPasswordHash, 0, storedPasswordHash.length);
            
            // Hashear la contraseña proporcionada con el mismo salt
            MessageDigest md = MessageDigest.getInstance(HASH_ALGORITHM);
            md.update(salt);
            byte[] testHash = md.digest(password.getBytes());
            
            // Comparar hashes
            return MessageDigest.isEqual(testHash, storedPasswordHash);
            
        } catch (Exception e) {
            return false; // Error en verificación = contraseña incorrecta
        }
    }
    
    /**
     * Valida que una contraseña cumpla con los requisitos de seguridad
     */
    public static boolean isValidPassword(String password) {
        if (password == null || password.length() < 4) {
            return false;
        }
        
        // TODO: Agregar más validaciones según necesidades:
        // - Longitud máxima
        // - Caracteres especiales requeridos
        // - Mayúsculas/minúsculas
        // - Números requeridos
        
        return true;
    }
    
    /**
     * Genera una contraseña temporal aleatoria
     * Útil para sistemas de recuperación de contraseña
     */
    public static String generateTemporaryPassword(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder password = new StringBuilder();
        
        for (int i = 0; i < length; i++) {
            password.append(chars.charAt(random.nextInt(chars.length())));
        }
        
        return password.toString();
    }
}