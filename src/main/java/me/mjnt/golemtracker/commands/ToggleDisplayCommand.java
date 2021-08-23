package me.mjnt.golemtracker.commands;

import me.mjnt.golemtracker.GolemTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import me.mjnt.golemtracker.ConfigHandler;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class ToggleDisplayCommand extends CommandBase {
    @Override
    public String getCommandName() {
        return "toggledisplay";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "toggledisplay";
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
        Boolean toggled = ConfigHandler.getBoolean("toggles", "display");
        if (toggled == true) {
            GolemTracker.displayToggled = false;
            ConfigHandler.writeBooleanConfig("toggles", "display", false);
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN+"Display toggle set to FALSE."));
        } else {
            GolemTracker.displayToggled = true;
            ConfigHandler.writeBooleanConfig("toggles", "display", true);
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN+"Display toggle set to TRUE."));
        }
    }
}
