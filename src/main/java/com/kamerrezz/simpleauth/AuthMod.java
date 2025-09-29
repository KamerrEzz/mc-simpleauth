package com.kamerrezz.simpleauth;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Clase principal del mod SimpleAuth
 * Maneja la inicialización y configuración básica del mod de autenticación
 */
@Mod("simpleauth")
public class AuthMod {
    
    public static final String MOD_ID = "simpleauth";
    public static final Logger LOGGER = LogManager.getLogger();
    
    public AuthMod() {
        // Registrar eventos de inicialización
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        
        LOGGER.info("SimpleAuth mod initialized");
    }
    
    /**
     * Configuración inicial del mod
     * Se ejecuta durante la fase de setup común
     */
    private void setup(final FMLCommonSetupEvent event) {
        LOGGER.info("SimpleAuth setup phase");
        
        // TODO: Registrar comandos
        // TODO: Registrar event handlers
        // TODO: Inicializar sistema de almacenamiento
    }
}