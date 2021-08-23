package me.mjnt.golemtracker.commands;

import me.mjnt.golemtracker.ConfigHandler;
import me.mjnt.golemtracker.GolemTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class ResetStatsCommand extends CommandBase {
    @Override
    public String getCommandName() {
        return "rs";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "rs";
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        GolemTracker.tbCount = 0;
        GolemTracker.epicGolemCount = 0;
        GolemTracker.legGolemCount = 0;
        ConfigHandler.writeIntConfig("drops", "golems_epic", 0);
        ConfigHandler.writeIntConfig("drops", "golems_leg", 0);
        ConfigHandler.writeIntConfig("drops", "cores", 0);
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN+"Drops set to 0."));
    }
}
