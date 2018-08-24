package iunius118.mods.iuniusrangekeeper;

import net.minecraftforge.fml.common.Mod;

@Mod(
        modid = IuniusRangekeeper.MOD_ID,
        name = IuniusRangekeeper.MOD_NAME,
        version = IuniusRangekeeper.MOD_VERSION,
        dependencies = IuniusRangekeeper.MOD_DEPENDENCIES,
        guiFactory = "iunius118.mods.iuniusrangekeeper.client.gui.ConfigGuiFactory",
        useMetadata = true)
public class IuniusRangekeeper
{
    public static final String MOD_ID = "iuniusrangekeeper";
    public static final String MOD_NAME = "Iunius's Rangekeeper";
    public static final String MOD_VERSION = "1.12.2-1.0.0.0";
    public static final String MOD_DEPENDENCIES = "required-after:forge@[1.12.2-14.23.3.2655,)";


}
