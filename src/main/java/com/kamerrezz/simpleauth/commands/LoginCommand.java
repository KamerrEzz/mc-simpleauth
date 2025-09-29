package com.kamerrezz.simpleauth.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

/**
 * Comando /login <password>
 * Permite a los jugadores registrados autenticarse en el servidor
 */
public class LoginCommand {
    
    /**
     * Registra el comando en el dispatcher
     */
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("login")
            .then(Commands.argument("password", StringArgumentType.string())
                .executes(LoginCommand::execute)
            )
        );
    }
    
    /**
     * Ejecuta el comando de login
     */
    private static int execute(CommandContext<CommandSourceStack> context) {
        // TODO: Obtener el jugador que ejecuta el comando
        // TODO: Verificar que el jugador esté registrado
        // TODO: Verificar que el jugador no esté ya autenticado
        // TODO: Obtener la contraseña hasheada del UserManager
        // TODO: Verificar la contraseña usando PasswordUtils
        // TODO: Marcar al jugador como autenticado
        // TODO: Enviar mensaje de confirmación o error
        
        String password = StringArgumentType.getString(context, "password");
        
        // Placeholder response
        context.getSource().sendSuccess(() -> 
            Component.literal("§aLogin exitoso! Ahora estás autenticado."), false);
        
        return 1;
    }
    
    /**
     * Verifica las credenciales del jugador
     */
    private static boolean verifyCredentials(String playerName, String password) {
        // TODO: Implementar verificación de credenciales
        // - Obtener hash almacenado
        // - Verificar contraseña con PasswordUtils
        return false;
    }
}