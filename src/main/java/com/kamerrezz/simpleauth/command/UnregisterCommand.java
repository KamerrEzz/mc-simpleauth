package com.kamerrezz.simpleauth.command;

import com.kamerrezz.simpleauth.storage.UserManager;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class UnregisterCommand {
    
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("unregister")
            .requires(source -> source.hasPermission(2))
            .then(Commands.argument("player", StringArgumentType.string())
                .executes(UnregisterCommand::execute)));
    }
    
    private static int execute(CommandContext<CommandSourceStack> context) {
        String targetPlayer = StringArgumentType.getString(context, "player");
        
        if (!UserManager.isRegistered(targetPlayer)) {
            context.getSource().sendFailure(Component.literal("§cEl jugador " + targetPlayer + " no está registrado"));
            return 0;
        }
        
        UserManager.UserData userData = UserManager.getUserData(targetPlayer);
        if (userData == null) {
            context.getSource().sendFailure(Component.literal("§cError al obtener datos del jugador"));
            return 0;
        }
        
        if (UserManager.deleteUser(targetPlayer)) {
            deletePlayerData(userData.internalUuid.toString());
            context.getSource().sendSuccess(() -> Component.literal("§aJugador " + targetPlayer + " eliminado exitosamente"), true);
            return 1;
        } else {
            context.getSource().sendFailure(Component.literal("§cError al eliminar el jugador"));
            return 0;
        }
    }
    
    private static void deletePlayerData(String internalUuid) {
        try {
            Path worldPath = Paths.get("world", "playerdata", internalUuid + ".dat");
            if (Files.exists(worldPath)) {
                Files.delete(worldPath);
            }
            
            Path advancementsPath = Paths.get("world", "advancements", internalUuid + ".json");
            if (Files.exists(advancementsPath)) {
                Files.delete(advancementsPath);
            }
            
            Path statsPath = Paths.get("world", "stats", internalUuid + ".json");
            if (Files.exists(statsPath)) {
                Files.delete(statsPath);
            }
        } catch (Exception e) {
            System.err.println("Error al eliminar datos del jugador: " + e.getMessage());
        }
    }
}