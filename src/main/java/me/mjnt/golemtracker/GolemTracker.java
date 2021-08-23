package me.mjnt.golemtracker;

import jdk.nashorn.internal.runtime.regexp.joni.Config;
import me.mjnt.golemtracker.commands.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = GolemTracker.MODID, version = GolemTracker.VERSION)
public class GolemTracker
{
    public static final String MODID = "golemtracker";
    public static final String VERSION = "1.0";
    public static final String NAME = "Golem Tracker";

    public static int epicGolemCount = -1;
    public static int legGolemCount = -1;
    public static int tbCount = -1;
    public static int posX = -1;
    public static int posY = -1;
    public static Boolean displayToggled = null;

    public static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
        ClientCommandHandler.instance.registerCommand(new ResetStatsCommand());
        ClientCommandHandler.instance.registerCommand(new ToggleDisplayCommand());
        ClientCommandHandler.instance.registerCommand(new SetPosCommand());
    }

    // tommy init //tommy neg
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        logger.info("initialise FMLServerStartingEvent: " + NAME);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        String messageTB = event.message.getUnformattedText();
        // [00:43:07] [Client thread/INFO]: [CHAT] [MVP+] mjnt has obtained [Lvl 1] Golem!
        String messageGolem = event.message.getFormattedText();
        String name = Minecraft.getMinecraft().getSession().getUsername();
        if (messageTB.startsWith("[") && messageGolem.endsWith("&5Golem&r&e!") && messageTB.contains(name)) {
            int epicGolem = ConfigHandler.getInt("drops", "golems_epic");
            int totalEpicGolems = 1 + epicGolem;
            epicGolemCount = totalEpicGolems;
            ConfigHandler.writeIntConfig("drops", "golems_epic", totalEpicGolems);
        }
        if (messageTB.startsWith("[") && messageGolem.endsWith("&6Golem&r&e!") && messageTB.contains(name)) {
            int legGolem = ConfigHandler.getInt("drops", "golems_leg");
            int totalLegGolems = 1 + legGolem;
            epicGolemCount = totalLegGolems;
            ConfigHandler.writeIntConfig("drops", "golems_leg", totalLegGolems);
        }
        if (messageTB.startsWith("[") && messageTB.endsWith("Core&r&e!") && messageTB.contains(name)) {
            int TB = ConfigHandler.getInt("drops", "cores");
            int totalTB = 1 + TB;
            tbCount = totalTB;
            ConfigHandler.writeIntConfig("drops", "cores", tbCount);
        }
    }

    @SubscribeEvent
    public void render(RenderGameOverlayEvent event) {
        if (event.isCancelable() || event.type != RenderGameOverlayEvent.ElementType.EXPERIENCE) {
            return;
        }
        if (displayToggled == null) {
            displayToggled = ConfigHandler.getBoolean("toggles", "display");
        }
        if (epicGolemCount == -1) {
            epicGolemCount = ConfigHandler.getInt("drops", "golems");
        }
        if (legGolemCount == -1) {
            legGolemCount = ConfigHandler.getInt("drops", "golems_leg");
        }
        if (tbCount == -1) {
            tbCount = ConfigHandler.getInt("drops", "cores");
        }
        if (posX == -1 && posY == -1) {
            posX = ConfigHandler.getInt("location", "x");
            posY = ConfigHandler.getInt("location", "y");
        }
        if (displayToggled == true) {
            FontRenderer fRender = Minecraft.getMinecraft().fontRendererObj;

            // TODO  fix fucking stupid ass lf box BY not using \n (maybe done?)
            //                     + EnumChatFormatting.RED + "\nTier Boosts: " + EnumChatFormatting.WHITE + tbCount,
            //                     + EnumChatFormatting.GOLD + "\nLeg Golems: " + EnumChatFormatting.WHITE + legGolemCount
            fRender.drawString(EnumChatFormatting.DARK_PURPLE + "Epic Golems: " + EnumChatFormatting.WHITE + epicGolemCount, posX, posY, 0);
            // i wonder
            fRender.drawString(EnumChatFormatting.GOLD + "Leg Golems: " + EnumChatFormatting.WHITE + legGolemCount, posX, posY + 11, 0);
            // i really wonder                                                                                               v i am best co..der btw
            fRender.drawString(EnumChatFormatting.RED + "Tier Boosts: " + EnumChatFormatting.WHITE + tbCount, posX, posY + 22, 0);
        }
    }

}
