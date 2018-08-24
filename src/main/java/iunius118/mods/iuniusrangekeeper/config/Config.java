package iunius118.mods.iuniusrangekeeper.config;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class Config {

    public static Configuration config;

    public static Property propIsEnabledLaserBlade3DModel;

    public static void loadConfig(FMLPreInitializationEvent event)
    {
        config = new Configuration( event.getSuggestedConfigurationFile() );
        config.load();

        // TODO: Add config items

        config.save();
    }

 }
