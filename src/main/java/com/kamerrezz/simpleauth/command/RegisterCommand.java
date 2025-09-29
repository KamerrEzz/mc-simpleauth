package com.kamerrezz.simpleauth.command;

import com.kamerrezz.simpleauth.handler.LoginHandler;
import com.kamerrezz.simpleauth.manager.UserManager;
import com.kamerrezz.simpleauth.util.PasswordUtils;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

import java.util.UUID;

public class RegisterCommand {
    
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("register")
            .then(Commands.argument("password", StringArgumentType.string())
                .executes(RegisterCommand::execute)));
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
        
        if (UserManager.isRegistered(playerName)) {
            player.sendSystemMessage(Component.literal("§cYa estás registrado. Usa /login <contraseña>"));
            return 0;
        }
        
        if (!PasswordUtils.isValidPassword(password)) {
            player.sendSystemMessage(Component.literal("§cLa contraseña debe tener entre 4 y 32 caracteres sin espacios"));
            return 0;
        }
        
        String playerIP = player.getIpAddress();
        if (UserManager.registerUser(playerName, password, playerIP)) {
            UserManager.setAuthenticated(playerId, true);
            LoginHandler.clearFailedAttempts(playerId);
            player.sendSystemMessage(Component.literal("§aRegistro exitoso. Ya estás autenticado"));
            return 1;
        } else {
            player.sendSystemMessage(Component.literal("§cError al registrar. Inténtalo de nuevo"));
            return 0;
        }
    }
}