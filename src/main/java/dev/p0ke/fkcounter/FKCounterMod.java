package dev.p0ke.fkcounter;

import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = FKCounterMod.MODID, version = FKCounterMod.VERSION)
public class FKCounterMod {
	
    public static final String MODID = "fkcounter";
    public static final String VERSION = "1.1";
    
    private static FKCounterMod instance;
    
    public static final String MW_GAME_MESSAGE = "                                 Mega Walls";
    
    private KillCounter killCounter = null;
    
    @EventHandler
    public void init(FMLInitializationEvent event) {
    	instance = this;
		MinecraftForge.EVENT_BUS.register(this);
		
    }
    
    @EventHandler
	public void postInit(FMLPostInitializationEvent event){
		ClientCommandHandler.instance.registerCommand(new FKCounterCommand());
	}
    
    @SubscribeEvent
    public void onChatMessage(ClientChatReceivedEvent event) {
    	if(event.message.getUnformattedText().equals(MW_GAME_MESSAGE)) {
    		killCounter = new KillCounter();
    	}
    	
    	if(killCounter != null) {
    		killCounter.onChatMessage(event);
    	}
    }
    
    public KillCounter getKillCounter() {
    	return killCounter;
    }
    
    public static FKCounterMod instance() {
    	return instance;
    }
}
