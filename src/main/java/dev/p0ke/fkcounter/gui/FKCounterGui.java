package dev.p0ke.fkcounter.gui;

import java.awt.Color;

import com.orangemarshall.hudproperty.IRenderer;
import com.orangemarshall.hudproperty.util.ScreenPosition;

import dev.p0ke.fkcounter.FKCounterMod;
import dev.p0ke.fkcounter.config.ConfigSetting;
import dev.p0ke.fkcounter.util.KillCounter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.EnumChatFormatting;

public class FKCounterGui extends Gui implements IRenderer {
	
	private static final String DUMMY_TEXT = EnumChatFormatting.RED + "RED" + EnumChatFormatting.WHITE + ": 1\n"
			+ EnumChatFormatting.GREEN + "GREEN" + EnumChatFormatting.WHITE + ": 2\n"
			+ EnumChatFormatting.YELLOW + "YELLOW" + EnumChatFormatting.WHITE + ": 3\n"
			+ EnumChatFormatting.BLUE + "BLUE" + EnumChatFormatting.WHITE + ": 4";
	private static final String DUMMY_TEXT_COMPACT = EnumChatFormatting.RED + "1" + EnumChatFormatting.GRAY + " / "
			+ EnumChatFormatting.GREEN + "2" + EnumChatFormatting.GRAY + " / "
			+ EnumChatFormatting.YELLOW + "3" + EnumChatFormatting.GRAY + " / "
			+ EnumChatFormatting.BLUE + "4";
	
	private boolean dummy = false;

	@Override
	public void save(ScreenPosition pos) {
		int x = pos.getAbsoluteX();
		int y = pos.getAbsoluteY();
		
		ConfigSetting.FKCOUNTER_HUD.getData().setScreenPos(x, y);
		FKCounterMod.instance().getConfigHandler().saveConfig();
	}

	@Override
	public ScreenPosition load() {
		return ConfigSetting.FKCOUNTER_HUD.getData().getScreenPos();
	}

	@Override
	public int getHeight() {
		if(ConfigSetting.COMPACT_HUD.getValue()) {
			return Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT;
		} else {
			return Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT*4;
		}
	}

	@Override
	public int getWidth() {
		if(dummy) {
			if(ConfigSetting.COMPACT_HUD.getValue())
				return Minecraft.getMinecraft().fontRendererObj.getStringWidth(DUMMY_TEXT_COMPACT);
			else
				return Minecraft.getMinecraft().fontRendererObj.getStringWidth("YELLOW: 3");
		}
			
		int width = 0;
		for(String m : getDisplayText().split("\n")) {
			int mWidth = Minecraft.getMinecraft().fontRendererObj.getStringWidth(m);
			if(mWidth > width) {
				width = mWidth;
			}
		}
		return width;
	}

	@Override
	public void render(ScreenPosition position) {
		dummy = false;
		
		int x = position.getAbsoluteX();
		int y = position.getAbsoluteY();
		
		if(ConfigSetting.DRAW_BACKGROUND.getValue())
			drawRect(x - 1, y - 1, x + getWidth(), y + getHeight(), new Color(0, 0, 0, 64).getRGB());
		drawMultilineString(getDisplayText(), x, y);
		
	}

	@Override
	public void renderDummy(ScreenPosition position) {
		dummy = true;
		
		int x = position.getAbsoluteX();
		int y = position.getAbsoluteY();
		
		drawRect(x - 1, y - 1, x + getWidth() + 1, y + getHeight() + 1, new Color(255, 255, 255, 127).getRGB());
		drawHorizontalLine(x - 1, x + getWidth() + 1, y - 1, Color.RED.getRGB());
		drawHorizontalLine(x - 1, x + getWidth() + 1, y + getHeight() + 1, Color.RED.getRGB());
		drawVerticalLine(x - 1, y - 1, y + getHeight() + 1, Color.RED.getRGB());
		drawVerticalLine(x + getWidth() + 1, y - 1, y + getHeight() + 1, Color.RED.getRGB());

		
		if(ConfigSetting.COMPACT_HUD.getValue())
			drawMultilineString(DUMMY_TEXT_COMPACT, x, y);
		else
			drawMultilineString(DUMMY_TEXT, x, y);
	}
	
	@Override
	public boolean isEnabled() {
		return (ConfigSetting.FKCOUNTER_HUD.getValue() && FKCounterMod.instance().getKillCounter() != null);
	}
	
	private void drawMultilineString(String msg, int x, int y) {
		for(String m : msg.split("\n")) {
			Minecraft.getMinecraft().fontRendererObj.drawString(m, x, y, 0xFFFFFF);
			y+=Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT;
		}
	}
	
	private String getDisplayText() {
		String msg = "";
		KillCounter kc = FKCounterMod.instance().getKillCounter();
		if(kc != null) {
			if(ConfigSetting.COMPACT_HUD.getValue()) {
				msg += "" + EnumChatFormatting.RED + kc.getKills(KillCounter.RED_TEAM) + EnumChatFormatting.GRAY + " / "
						+ EnumChatFormatting.GREEN + kc.getKills(KillCounter.GREEN_TEAM) + EnumChatFormatting.GRAY + " / "
						+ EnumChatFormatting.YELLOW + kc.getKills(KillCounter.YELLOW_TEAM) + EnumChatFormatting.GRAY + " / "
						+ EnumChatFormatting.BLUE + kc.getKills(KillCounter.BLUE_TEAM);
			} else {
				msg += EnumChatFormatting.RED + "RED" + EnumChatFormatting.WHITE + ": " + kc.getKills(KillCounter.RED_TEAM) + "\n";
				msg += EnumChatFormatting.GREEN + "GREEN" + EnumChatFormatting.WHITE + ": " + kc.getKills(KillCounter.GREEN_TEAM) + "\n";
				msg += EnumChatFormatting.YELLOW + "YELLOW" + EnumChatFormatting.WHITE + ": " + kc.getKills(KillCounter.YELLOW_TEAM) + "\n";
				msg += EnumChatFormatting.BLUE + "BLUE" + EnumChatFormatting.WHITE + ": " + kc.getKills(KillCounter.BLUE_TEAM);
			}
		}
		return msg;
	}

}
