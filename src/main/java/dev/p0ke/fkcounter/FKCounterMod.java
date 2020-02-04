package dev.p0ke.fkcounter;

import com.orangemarshall.hudproperty.HudPropertyApi;

import dev.p0ke.fkcounter.command.FKCounterCommand;
import dev.p0ke.fkcounter.config.ConfigHandler;
import dev.p0ke.fkcounter.gui.FKCounterGui;
import dev.p0ke.fkcounter.util.KillCounter;
import net.minecraft.client.gui.GuiDownloadTerrain;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = FKCounterMod.MODID, version = FKCounterMod.VERSION)
public class FKCounterMod {
	
    public static final String MODID = "fkcounter";
    public static final String VERSION = "2.0";
    
    private static FKCounterMod instance;
    
    private ConfigHandler configHandler;
    private HudPropertyApi hudManager;
    
    public static final String MW_GAME_START_MESSAGE = "       You have 6 minutes until the walls fall down!";
    public static final String MW_GAME_END_MESSAGE = "                                 Mega Walls";
    
    private KillCounter killCounter = null;
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
    	configHandler = new ConfigHandler(event.getSuggestedConfigurationFile());
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event) {
    	instance = this;
		MinecraftForge.EVENT_BUS.register(this);
		
		hudManager = HudPropertyApi.newInstance();
		hudManager.register(new FKCounterGui());
		
		configHandler.loadConfig();
		
    }
    
    @EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		ClientCommandHandler.instance.registerCommand(new FKCounterCommand());
	}
    
    @SubscribeEvent
    public void onChatMessage(ClientChatReceivedEvent event) {
    	if(event.message.getUnformattedText().equals(MW_GAME_END_MESSAGE)){
    		killCounter = null;
    	}
    	
    	if(event.message.getUnformattedText().equals(MW_GAME_START_MESSAGE)) {
    		killCounter = new KillCounter();
    	}
    	
    	if(killCounter != null) {
    		killCounter.onChatMessage(event);
    	}
    }
    
    @SubscribeEvent
    public void onGuiShow(GuiOpenEvent event) {
    	if(event.gui instanceof GuiDownloadTerrain) {
    		killCounter = null;
    	}
    }
    
    public void forceToggle() {
    	if(killCounter != null) {
    		killCounter = null;
    	} else {
    		killCounter = new KillCounter();
    	}
    }
    
    public KillCounter getKillCounter() {
    	return killCounter;
    }
    
    public ConfigHandler getConfigHandler() {
    	return configHandler;
    }
    
    public HudPropertyApi getHudManager() {
    	return hudManager;
    }
    
    public static FKCounterMod instance() {
    	return instance;
    }
}
