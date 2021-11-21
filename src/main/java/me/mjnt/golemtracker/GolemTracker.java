package me.mjnt.golemtracker;

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
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = GolemTracker.MODID, version = GolemTracker.VERSION)
public class GolemTracker
{
    public static final String MODID = "golemtracker";
    public static final String VERSION = "1.0";
    public static final String NAME = "Golem Tracker";

    public static int golemCount = -1;
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
        String message = event.message.getUnformattedText();
        // [00:43:07] [Client thread/INFO]: [CHAT] [MVP+] mjnt has obtained [Lvl 1] Golem!
        // &r&b[MVP&r&c+&r&b] KatAura&r&f &r&ehas obtained &r&6&r&7[Lvl 1] &r&5Golem&r&e!&r
        // &r&r&r                 &r&eYour Damage: &r&a3,844,539 &r&7(Position #1)&r
        String name = Minecraft.getMinecraft().getSession().getUsername();
        // if message has golem drop
        // idfc about rarities anymore im tired of coding
        if (message.startsWith("[") && message.endsWith(" has obtained [Lvl 1] Golem!") && message.contains(name)) {
            int golems = ConfigHandler.getInt("drops", "golems");
            golems++;
            golemCount = golems;
            ConfigHandler.writeIntConfig("drops", "golems", golemCount);
        }
        // if message has "Core!"
        if (message.startsWith("[") && message.endsWith(" has obtained Tier Boost Core!") && message.contains(name)) {
            int TB = ConfigHandler.getInt("drops", "cores");
            TB++;
            tbCount = TB;
            ConfigHandler.writeIntConfig("drops", "cores", tbCount);
        }
    }

    @SubscribeEvent
    public void render(RenderGameOverlayEvent event) {
        // idfk what this does
        if (event.isCancelable() || event.type != RenderGameOverlayEvent.ElementType.EXPERIENCE) {
            return;
        }
        // loads once
        if (displayToggled == null) {
            displayToggled = ConfigHandler.getBoolean("toggles", "display");
        }
        if (golemCount == -1) {
            golemCount = ConfigHandler.getInt("drops", "golems_epic");
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
            // renders the gui thing
            fRender.drawString(EnumChatFormatting.GOLD + "Golem Pets: " + EnumChatFormatting.WHITE + golemCount, posX, posY, 0);
            fRender.drawString(EnumChatFormatting.RED + "Tier Boosts: " + EnumChatFormatting.WHITE + tbCount, posX, posY + 11, 0);
        }
    }

}
