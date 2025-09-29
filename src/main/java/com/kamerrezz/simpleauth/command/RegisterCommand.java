package com.kamerrezz.simpleauth.command;

import com.kamerrezz.simpleauth.handler.LoginHandler;
import com.kamerrezz.simpleauth.storage.UserManager;
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
            return 0;
        }

        String playerName = player.getName().getString();
        String password = StringArgumentType.getString(context, "password");

        if (LoginHandler.isInCooldown(player.getUUID())) {
            player.sendSystemMessage(Component.literal("§cEstás en cooldown. Espera antes de intentar de nuevo."));
            return 0;
        }

        if (UserManager.isRegistered(playerName)) {
            player.sendSystemMessage(Component.literal("§cYa estás registrado. Usa /login <contraseña>"));
            return 0;
        }

        if (password.length() < 6 || password.length() > 32) {
            player.sendSystemMessage(Component.literal("§cLa contraseña debe tener entre 6 y 32 caracteres"));
            return 0;
        }

        boolean success = UserManager.registerUser(playerName, password, player.getIpAddress());
        if (success) {
            UserManager.setAuthenticated(playerName, player.getUUID());
            player.sendSystemMessage(Component.literal("§aRegistro exitoso. Ya estás autenticado."));
            LoginHandler.clearFailedAttempts(player.getUUID());
            return 1;
        } else {
            player.sendSystemMessage(Component.literal("§cError al registrar. Inténtalo de nuevo."));
            LoginHandler.recordFailedAttempt(player.getUUID());
            return 0;
        }
    }
}