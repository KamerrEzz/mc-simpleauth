package com.kamerrezz.simpleauth;

import com.kamerrezz.simpleauth.command.ChangePasswordCommand;
import com.kamerrezz.simpleauth.command.LoginCommand;
import com.kamerrezz.simpleauth.command.RegisterCommand;
import com.kamerrezz.simpleauth.command.UnregisterCommand;
import com.kamerrezz.simpleauth.handler.LoginHandler;
import com.kamerrezz.simpleauth.manager.UserManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("simpleauth")
public class AuthMod {
    
    public AuthMod() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(LoginHandler.class);
    }
    
    private void setup(final FMLCommonSetupEvent event) {
        UserManager.initialize();
    }
    
    @SubscribeEvent
    public void onRegisterCommands(RegisterCommandsEvent event) {
        RegisterCommand.register(event.getDispatcher());
        LoginCommand.register(event.getDispatcher());
        UnregisterCommand.register(event.getDispatcher());
        ChangePasswordCommand.register(event.getDispatcher());
    }
}