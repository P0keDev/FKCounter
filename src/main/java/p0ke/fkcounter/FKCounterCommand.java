package p0ke.fkcounter;

import java.util.stream.Collectors;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class FKCounterCommand extends CommandBase {

	@Override
	public String getCommandName() {
		return "fks";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "";
	}
	
	@Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

	@Override
	public void processCommand(ICommandSender sender, String[] args) throws CommandException {
		KillCounter killCounter = FKCounterMod.instance().getKillCounter();
		if(killCounter == null) { return; }
		
		if(args.length > 0 && args[0].equalsIgnoreCase("players")) {
			
			String msg = "";
			msg += EnumChatFormatting.RED + "RED" + EnumChatFormatting.WHITE + ": " + 
					(killCounter.getPlayers(KillCounter.RED_TEAM).entrySet().stream().map(entry -> entry.getKey() + " (" + entry.getValue() + ")").collect(Collectors.joining(", "))) + "\n";
			msg += EnumChatFormatting.GREEN + "GREEN" + EnumChatFormatting.WHITE + ": " + 
					(killCounter.getPlayers(KillCounter.GREEN_TEAM).entrySet().stream().map(entry -> entry.getKey() + " (" + entry.getValue() + ")").collect(Collectors.joining(", "))) + "\n";
			msg += EnumChatFormatting.YELLOW + "YELLOW" + EnumChatFormatting.WHITE + ": " + 
					(killCounter.getPlayers(KillCounter.YELLOW_TEAM).entrySet().stream().map(entry -> entry.getKey() + " (" + entry.getValue() + ")").collect(Collectors.joining(", "))) + "\n";
			msg += EnumChatFormatting.BLUE + "BLUE" + EnumChatFormatting.WHITE + ": " + 
					(killCounter.getPlayers(KillCounter.BLUE_TEAM).entrySet().stream().map(entry -> entry.getKey() + " (" + entry.getValue() + ")").collect(Collectors.joining(", ")));
			
			sender.addChatMessage(new ChatComponentText(msg));
			
		} else {
		
			String msg = "";
			msg += EnumChatFormatting.RED + "RED" + EnumChatFormatting.WHITE + ": " + killCounter.getKills(KillCounter.RED_TEAM) + "\n";
			msg += EnumChatFormatting.GREEN + "GREEN" + EnumChatFormatting.WHITE + ": " + killCounter.getKills(KillCounter.GREEN_TEAM) + "\n";
			msg += EnumChatFormatting.YELLOW + "YELLOW" + EnumChatFormatting.WHITE + ": " + killCounter.getKills(KillCounter.YELLOW_TEAM) + "\n";
			msg += EnumChatFormatting.BLUE + "BLUE" + EnumChatFormatting.WHITE + ": " + killCounter.getKills(KillCounter.BLUE_TEAM);
			
			sender.addChatMessage(new ChatComponentText(msg));
		
		}
		
	}

}
