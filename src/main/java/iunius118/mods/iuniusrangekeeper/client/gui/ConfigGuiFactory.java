package iunius118.mods.iuniusrangekeeper.client.gui;

import java.util.List;
import java.util.Set;

import iunius118.mods.iuniusrangekeeper.IuniusRangekeeper;
import iunius118.mods.iuniusrangekeeper.config.Configs;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.IModGuiFactory;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;

public class ConfigGuiFactory implements IModGuiFactory {

	@Override
	public void initialize(Minecraft minecraftInstance) {

	}

	@Override
	public boolean hasConfigGui() {
		return true;
	}

	@Override
	public GuiScreen createConfigGui(GuiScreen parentScreen) {
        List<IConfigElement> elements;
        elements = ConfigElement.from(Configs.class).getChildElements();

        return new GuiConfig(parentScreen, elements, IuniusRangekeeper.MOD_ID, false, false, IuniusRangekeeper.MOD_NAME);
	}

	@Override
	public Set<RuntimeOptionCategoryElement> runtimeGuiCategories() {
		return null;
	}

}
