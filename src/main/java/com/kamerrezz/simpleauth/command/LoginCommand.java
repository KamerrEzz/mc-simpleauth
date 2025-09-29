package com.kamerrezz.simpleauth.command;

import com.kamerrezz.simpleauth.handler.LoginHandler;
import com.kamerrezz.simpleauth.manager.UserManager;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

import java.util.UUID;

public class LoginCommand {
    
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("login")
            .then(Commands.argument("password", StringArgumentType.string())
                .executes(LoginCommand::execute)));
    }
    
    private static int execute(CommandContext<CommandSourceStack> context) {
        if (!(context.getSource().getEntity() instanceof ServerPlayer player)) {
            context.getSource().sendFailure(Component.literal("§cSolo los jugadores pueden usar este comando"));
            return 0;
        }
        
        UUID playerId = player.getUUID();
        String playerName = player.getName().getString();
        String password = StringArgumentType.getString(context, "password");
        
        if (LoginHandler.isInCooldown(playerId)) {
            player.sendSystemMessage(Component.literal("§cEstás en cooldown. Espera antes de intentar de nuevo"));
            return 0;
        }
        
        if (!UserManager.isRegistered(playerName)) {
            player.sendSystemMessage(Component.literal("§cNo estás registrado. Usa /register <contraseña>"));
            return 0;
        }
        
        if (UserManager.isAuthenticated(playerId)) {
            player.sendSystemMessage(Component.literal("§cYa estás autenticado"));
            return 0;
        }
        
        String playerIP = player.getIpAddress();
        if (UserManager.verifyCredentials(playerName, password, playerIP)) {
            UserManager.setAuthenticated(playerId, true);
            LoginHandler.clearFailedAttempts(playerId);
            player.sendSystemMessage(Component.literal("§aLogin exitoso. Bienvenido de vuelta"));
            return 1;
        } else {
            LoginHandler.recordFailedAttempt(playerId);
            player.sendSystemMessage(Component.literal("§cContraseña incorrecta"));
            return 0;
        }
    }
}