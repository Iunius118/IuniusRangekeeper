package iunius118.mods.iuniusrangekeeper;

import org.apache.logging.log4j.Logger;

import iunius118.mods.iuniusrangekeeper.client.ClientEventHandler;
import iunius118.mods.iuniusrangekeeper.client.gunfirecontrolsystem.GunFireControlSystemIuniusRangekeeper;
import iunius118.mods.iuniusrangekeeper.config.Configs;
import iunius118.mods.iuniusrangekeeper.item.ItemRangekeeper;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Config.Type;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;

@Mod(
        modid = IuniusRangekeeper.MOD_ID,
        name = IuniusRangekeeper.MOD_NAME,
        version = IuniusRangekeeper.MOD_VERSION,
        dependencies = IuniusRangekeeper.MOD_DEPENDENCIES,
        guiFactory = "iunius118.mods.iuniusrangekeeper.client.gui.ConfigGuiFactory",
        useMetadata = true)
@EventBusSubscriber
public class IuniusRangekeeper
{

    public static final String MOD_ID = "iuniusrangekeeper";
    public static final String MOD_NAME = "Iunius's Rangekeeper";
    public static final String MOD_VERSION = "1.12.2-0.0.0.0";
    public static final String MOD_DEPENDENCIES = "required-after:forge@[1.12.2-14.23.3.2655,)";

    public static final Configs CONFIGS = new Configs();
    public static Logger logger;

    public static GunFireControlSystemIuniusRangekeeper rangekeeper = new GunFireControlSystemIuniusRangekeeper();

    @ObjectHolder(MOD_ID)
    public static class ITEMS
    {
        public static final Item rangekeeper = null;
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
        MinecraftForge.EVENT_BUS.register(this);

        if (event.getSide().isClient())
        {
            MinecraftForge.EVENT_BUS.register(new ClientEventHandler());
        }
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event)
    {
        event.getRegistry().register(new ItemRangekeeper().setRegistryName("rangekeeper").setTranslationKey(MOD_ID + ".rangekeeper").setCreativeTab(CreativeTabs.TOOLS));
    }

    @SubscribeEvent
    public void onConfigChangedEvent(OnConfigChangedEvent event)
    {
        if (event.getModID().equals(MOD_ID))
        {
            ConfigManager.sync(MOD_ID, Type.INSTANCE);
        }
    }

}
