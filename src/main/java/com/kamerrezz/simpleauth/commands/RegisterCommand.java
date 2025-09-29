package com.kamerrezz.simpleauth.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

/**
 * Comando /register <password>
 * Permite a los jugadores registrarse en el servidor con una contraseña
 */
public class RegisterCommand {
    
    /**
     * Registra el comando en el dispatcher
     */
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("register")
            .then(Commands.argument("password", StringArgumentType.string())
                .executes(RegisterCommand::execute)
            )
        );
    }
    
    /**
     * Ejecuta el comando de registro
     */
    private static int execute(CommandContext<CommandSourceStack> context) {
        // TODO: Obtener el jugador que ejecuta el comando
        // TODO: Verificar que el jugador no esté ya registrado
        // TODO: Validar la contraseña (longitud mínima, etc.)
        // TODO: Hashear la contraseña usando PasswordUtils
        // TODO: Guardar el usuario en UserManager
        // TODO: Marcar al jugador como autenticado
        // TODO: Enviar mensaje de confirmación
        
        String password = StringArgumentType.getString(context, "password");
        
        // Placeholder response
        context.getSource().sendSuccess(() -> 
            Component.literal("§aRegistro exitoso! Ahora estás autenticado."), false);
        
        return 1;
    }
    
    /**
     * Valida que la contraseña cumpla con los requisitos mínimos
     */
    private static boolean isValidPassword(String password) {
        // TODO: Implementar validación de contraseña
        // - Longitud mínima
        // - Caracteres permitidos
        return password != null && password.length() >= 4;
    }
}