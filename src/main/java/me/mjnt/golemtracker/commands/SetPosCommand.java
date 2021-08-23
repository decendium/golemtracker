package me.mjnt.golemtracker.commands;

import me.mjnt.golemtracker.ConfigHandler;
import me.mjnt.golemtracker.GolemTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class SetPosCommand extends CommandBase {
    @Override
    public String getCommandName() {
        return "setpos";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "setpos x y";
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
        String posXstr = args[0];
        String posYstr = args[1];
        GolemTracker.posX = Integer.parseInt(posXstr);
        GolemTracker.posY = Integer.parseInt(posYstr);
        ConfigHandler.writeIntConfig("location", "x", GolemTracker.posX);
        ConfigHandler.writeIntConfig("location", "y", GolemTracker.posY);
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN+"X position set to " + posXstr + ", and Y position set to " + posYstr + "."));
    }
}
