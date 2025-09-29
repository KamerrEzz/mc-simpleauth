package com.kamerrezz.simpleauth.storage;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * Gestor de usuarios y datos de autenticación
 * Maneja el almacenamiento y recuperación de información de usuarios
 */
public class UserManager {
    
    // Almacenamiento en memoria (temporal)
    // TODO: Implementar persistencia en archivo JSON o base de datos
    private static final Map<String, UserData> registeredUsers = new HashMap<>();
    private static final Set<String> authenticatedPlayers = new java.util.HashSet<>();
    
    /**
     * Registra un nuevo usuario en el sistema
     */
    public static boolean registerUser(String playerName, UUID playerUUID, String hashedPassword) {
        // TODO: Verificar que el usuario no exista
        // TODO: Crear UserData y almacenar
        // TODO: Persistir datos en archivo
        
        if (registeredUsers.containsKey(playerName.toLowerCase())) {
            return false; // Usuario ya existe
        }
        
        UserData userData = new UserData(playerName, playerUUID, hashedPassword);
        registeredUsers.put(playerName.toLowerCase(), userData);
        
        return true;
    }
    
    /**
     * Verifica si un usuario está registrado
     */
    public static boolean isUserRegistered(String playerName) {
        return registeredUsers.containsKey(playerName.toLowerCase());
    }
    
    /**
     * Obtiene los datos de un usuario registrado
     */
    public static UserData getUserData(String playerName) {
        return registeredUsers.get(playerName.toLowerCase());
    }
    
    /**
     * Marca un jugador como autenticado
     */
    public static void setPlayerAuthenticated(String playerName, boolean authenticated) {
        if (authenticated) {
            authenticatedPlayers.add(playerName.toLowerCase());
        } else {
            authenticatedPlayers.remove(playerName.toLowerCase());
        }
    }
    
    /**
     * Verifica si un jugador está autenticado
     */
    public static boolean isPlayerAuthenticated(String playerName) {
        return authenticatedPlayers.contains(playerName.toLowerCase());
    }
    
    /**
     * Limpia la autenticación de un jugador (al desconectarse)
     */
    public static void clearPlayerAuthentication(String playerName) {
        authenticatedPlayers.remove(playerName.toLowerCase());
    }
    
    /**
     * Carga los datos de usuarios desde archivo
     */
    public static void loadUserData() {
        // TODO: Implementar carga desde archivo JSON
        // TODO: Manejar errores de lectura
    }
    
    /**
     * Guarda los datos de usuarios en archivo
     */
    public static void saveUserData() {
        // TODO: Implementar guardado en archivo JSON
        // TODO: Manejar errores de escritura
    }
    
    /**
     * Clase interna para almacenar datos de usuario
     */
    public static class UserData {
        private final String playerName;
        private final UUID playerUUID;
        private final String hashedPassword;
        private final long registrationTime;
        
        public UserData(String playerName, UUID playerUUID, String hashedPassword) {
            this.playerName = playerName;
            this.playerUUID = playerUUID;
            this.hashedPassword = hashedPassword;
            this.registrationTime = System.currentTimeMillis();
        }
        
        // Getters
        public String getPlayerName() { return playerName; }
        public UUID getPlayerUUID() { return playerUUID; }
        public String getHashedPassword() { return hashedPassword; }
        public long getRegistrationTime() { return registrationTime; }
    }
}