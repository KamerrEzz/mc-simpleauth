package com.kamerrezz.simpleauth.storage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.kamerrezz.simpleauth.util.PasswordUtils;
import net.minecraftforge.fml.loading.FMLPaths;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class UserManager {
    private static final Map<String, UUID> authenticatedPlayers = new ConcurrentHashMap<>();
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_DIR = FMLPaths.CONFIGDIR.get().resolve("simpleauth");
    private static final File USERS_FILE = CONFIG_DIR.resolve("users.json").toFile();
    
    static {
        try {
            Files.createDirectories(CONFIG_DIR);
        } catch (IOException e) {
            throw new RuntimeException("Failed to create config directory", e);
        }
    }
    
    public static boolean registerUser(String playerName, String password, String ip) {
        Map<String, UserData> users = loadUsers();
        
        if (users.containsKey(playerName.toLowerCase())) {
            return false;
        }
        
        UUID internalUuid = UUID.randomUUID();
        
        String hashedPassword = PasswordUtils.hashPassword(password);
        if (hashedPassword == null) {
            return false;
        }
        
        UserData userData = new UserData(playerName, internalUuid, hashedPassword, ip);
        users.put(playerName.toLowerCase(), userData);
        
        return saveUsers(users);
    }
    
    public static boolean verifyCredentials(String playerName, String password) {
        Map<String, UserData> users = loadUsers();
        UserData userData = users.get(playerName.toLowerCase());
        
        if (userData == null) {
            return false;
        }
        
        return PasswordUtils.verifyPassword(password, userData.passwordHash);
    }
    
    public static UserData getUserData(String playerName) {
        Map<String, UserData> users = loadUsers();
        return users.get(playerName.toLowerCase());
    }
    
    public static boolean changePassword(String playerName, String newPassword) {
        Map<String, UserData> users = loadUsers();
        UserData userData = users.get(playerName.toLowerCase());
        
        if (userData == null) {
            return false;
        }
        
        userData.passwordHash = PasswordUtils.hashPassword(newPassword);
        users.put(playerName.toLowerCase(), userData);
        
        return saveUsers(users);
    }
    
    public static boolean deleteUser(String playerName) {
        Map<String, UserData> users = loadUsers();
        UserData removed = users.remove(playerName.toLowerCase());
        
        if (removed == null) {
            return false;
        }
        
        return saveUsers(users);
    }
    
    public static void setAuthenticated(String playerName, UUID internalUuid) {
        authenticatedPlayers.put(playerName, internalUuid);
    }
    
    public static boolean isAuthenticated(String playerName) {
        return authenticatedPlayers.containsKey(playerName);
    }
    
    public static void removeAuthenticated(String playerName) {
        authenticatedPlayers.remove(playerName);
    }
    
    public static UUID getInternalUuid(String playerName) {
        return authenticatedPlayers.get(playerName);
    }
    
    public static boolean isRegistered(String playerName) {
        Map<String, UserData> users = loadUsers();
        return users.containsKey(playerName.toLowerCase());
    }
    
    private static Map<String, UserData> loadUsers() {
        if (!USERS_FILE.exists()) {
            return new HashMap<>();
        }
        
        try (FileReader reader = new FileReader(USERS_FILE)) {
            Type type = new TypeToken<Map<String, UserData>>(){}.getType();
            Map<String, UserData> users = gson.fromJson(reader, type);
            return users != null ? users : new HashMap<>();
        } catch (IOException e) {
            return new HashMap<>();
        }
    }
    
    private static boolean saveUsers(Map<String, UserData> users) {
        try (FileWriter writer = new FileWriter(USERS_FILE)) {
            gson.toJson(users, writer);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
    
    public static class UserData {
        public String name;
        public UUID internalUuid;
        public String passwordHash;
        public String lastIp;
        
        public UserData() {}
        
        public UserData(String name, UUID internalUuid, String passwordHash, String lastIp) {
            this.name = name;
            this.internalUuid = internalUuid;
            this.passwordHash = passwordHash;
            this.lastIp = lastIp;
        }
    }
}