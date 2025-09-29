package com.kamerrezz.simpleauth.events;

import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Handler de eventos relacionados con el login y autenticación
 * Se encarga de interceptar acciones del jugador hasta que se autentique
 */
@Mod.EventBusSubscriber(modid = "simpleauth")
public class LoginHandler {
    
    /**
     * Evento que se ejecuta cuando un jugador se conecta al servidor
     * Aquí se debe bloquear las acciones hasta que se autentique
     */
    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        // TODO: Verificar si el jugador está registrado
        // TODO: Marcar jugador como no autenticado
        // TODO: Enviar mensaje de bienvenida con instrucciones
        // TODO: Bloquear movimiento y acciones
    }
    
    /**
     * Evento que se ejecuta cuando un jugador se desconecta
     * Limpia el estado de autenticación del jugador
     */
    @SubscribeEvent
    public static void onPlayerLogout(PlayerEvent.PlayerLoggedOutEvent event) {
        // TODO: Limpiar estado de autenticación del jugador
        // TODO: Remover de listas de jugadores autenticados
    }
    
    /**
     * Bloquea las acciones del jugador mientras no esté autenticado
     * Se debe llamar en eventos de movimiento, interacción, etc.
     */
    private static boolean isPlayerAuthenticated(String playerName) {
        // TODO: Verificar si el jugador está autenticado
        return false;
    }
    
    /**
     * Envía mensaje al jugador indicando que debe autenticarse
     */
    private static void sendAuthenticationMessage(String playerName) {
        // TODO: Enviar mensaje con instrucciones de registro/login
    }
}