package com.kamerrezz.simpleauth.command;

import com.kamerrezz.simpleauth.storage.UserManager;
import com.kamerrezz.simpleauth.util.PasswordUtils;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

public class ChangePasswordCommand {
    
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("changepassword")
            .requires(source -> source.hasPermission(2))
            .then(Commands.argument("player", StringArgumentType.string())
                .then(Commands.argument("newPassword", StringArgumentType.string())
                    .executes(ChangePasswordCommand::execute))));
    }
    
    private static int execute(CommandContext<CommandSourceStack> context) {
        String targetPlayer = StringArgumentType.getString(context, "player");
        String newPassword = StringArgumentType.getString(context, "newPassword");
        
        if (!UserManager.isRegistered(targetPlayer)) {
            context.getSource().sendFailure(Component.literal("§cEl jugador " + targetPlayer + " no está registrado"));
            return 0;
        }
        
        if (!PasswordUtils.isValidPassword(newPassword)) {
            context.getSource().sendFailure(Component.literal("§cLa contraseña debe tener entre 4 y 32 caracteres sin espacios"));
            return 0;
        }
        
        if (UserManager.changePassword(targetPlayer, newPassword)) {
            context.getSource().sendSuccess(() -> Component.literal("§aContraseña de " + targetPlayer + " cambiada exitosamente"), true);
            return 1;
        } else {
            context.getSource().sendFailure(Component.literal("§cError al cambiar la contraseña"));
            return 0;
        }
    }
}