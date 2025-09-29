package com.kamerrezz.simpleauth.handler;

import com.kamerrezz.simpleauth.storage.UserManager;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Mod.EventBusSubscriber(modid = "simpleauth")
public class LoginHandler {
    
    private static final Map<UUID, Long> loginAttempts = new ConcurrentHashMap<>();
    private static final Map<UUID, Integer> failedAttempts = new ConcurrentHashMap<>();
    private static final Map<UUID, Long> cooldowns = new ConcurrentHashMap<>();
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);
    
    private static final int MAX_LOGIN_TIME = 60;
    private static final int MAX_FAILED_ATTEMPTS = 3;
    private static final int COOLDOWN_TIME = 60;
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        
        UUID playerId = player.getUUID();
        String playerName = player.getName().getString();
        
        if (!UserManager.isRegistered(playerName)) {
            player.sendSystemMessage(Component.literal("§cDebes registrarte con /register <contraseña>"));
        } else if (!UserManager.isAuthenticated(playerName)) {
            player.sendSystemMessage(Component.literal("§cDebes autenticarte con /login <contraseña>"));
        }
        
        if (!UserManager.isAuthenticated(playerName)) {
            loginAttempts.put(playerId, System.currentTimeMillis());
            
            scheduler.schedule(() -> {
                if (!UserManager.isAuthenticated(playerName) && player.isAlive()) {
                    player.connection.disconnect(Component.literal("§cTiempo de login agotado (60 segundos)"));
                }
                loginAttempts.remove(playerId);
            }, MAX_LOGIN_TIME, TimeUnit.SECONDS);
        }
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onPlayerLeave(PlayerEvent.PlayerLoggedOutEvent event) {
        UUID playerId = event.getEntity().getUUID();
        String playerName = event.getEntity().getName().getString();
        loginAttempts.remove(playerId);
        failedAttempts.remove(playerId);
        cooldowns.remove(playerId);
        UserManager.removeAuthenticated(playerName);
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onPlayerMove(LivingEvent.LivingTickEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        
        String playerName = player.getName().getString();
        if (!UserManager.isAuthenticated(playerName)) {
            double x = player.getX();
            double y = player.getY();
            double z = player.getZ();
            
            if (player.getDeltaMovement().lengthSqr() > 0.01) {
                player.teleportTo(x, y, z);
                player.setDeltaMovement(0, 0, 0);
            }
        }
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onPlayerChat(ServerChatEvent event) {
        ServerPlayer player = event.getPlayer();
        String playerName = player.getName().getString();
        
        if (!UserManager.isAuthenticated(playerName)) {
            event.setCanceled(true);
            player.sendSystemMessage(Component.literal("§cDebes autenticarte antes de chatear"));
        }
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onPlayerInteract(PlayerInteractEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        
        String playerName = player.getName().getString();
        if (!UserManager.isAuthenticated(playerName)) {
            event.setCanceled(true);
            player.sendSystemMessage(Component.literal("§cDebes autenticarte antes de interactuar"));
        }
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onCommand(CommandEvent event) {
        if (!(event.getParseResults().getContext().getSource().getEntity() instanceof ServerPlayer player)) return;
        
        String playerName = player.getName().getString();
        String command = event.getParseResults().getReader().getString().toLowerCase();
        
        if (!UserManager.isAuthenticated(playerName)) {
            boolean isRegisterCommand = command.startsWith("/register") || command.startsWith("register");
            boolean isLoginCommand = command.startsWith("/login") || command.startsWith("login");
            
            if (!isRegisterCommand && !isLoginCommand) {
                event.setCanceled(true);
                player.sendSystemMessage(Component.literal("§cSolo puedes usar /register o /login"));
            }
        }
    }
    
    @SubscribeEvent
    public static void onServerChatEvent(ServerChatEvent event) {
        String playerName = event.getPlayer().getName().getString();
        
        if (!UserManager.isAuthenticated(playerName)) {
            event.setCanceled(true);
            event.getPlayer().sendSystemMessage(Component.literal("Debes autenticarte antes de poder chatear."));
        }
    }
    
    public static void recordFailedAttempt(UUID playerId) {
        int attempts = failedAttempts.getOrDefault(playerId, 0) + 1;
        failedAttempts.put(playerId, attempts);
        
        if (attempts >= MAX_FAILED_ATTEMPTS) {
            cooldowns.put(playerId, System.currentTimeMillis() + (COOLDOWN_TIME * 1000L));
            failedAttempts.remove(playerId);
            
            ServerPlayer player = getPlayerByUUID(playerId);
            if (player != null) {
                player.connection.disconnect(Component.literal("§cDemasiados intentos fallidos. Cooldown de " + COOLDOWN_TIME + " segundos"));
            }
        }
    }
    
    public static boolean isInCooldown(UUID playerId) {
        Long cooldownEnd = cooldowns.get(playerId);
        if (cooldownEnd == null) return false;
        
        if (System.currentTimeMillis() >= cooldownEnd) {
            cooldowns.remove(playerId);
            return false;
        }
        return true;
    }
    
    public static void clearFailedAttempts(UUID playerId) {
        failedAttempts.remove(playerId);
    }
    
    private static ServerPlayer getPlayerByUUID(UUID playerId) {
        return null;
    }
}