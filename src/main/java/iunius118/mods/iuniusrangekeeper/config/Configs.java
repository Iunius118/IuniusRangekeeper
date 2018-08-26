package iunius118.mods.iuniusrangekeeper.config;

import iunius118.mods.iuniusrangekeeper.IuniusRangekeeper;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.LangKey;
import net.minecraftforge.common.config.Config.RangeDouble;
import net.minecraftforge.common.config.Config.RangeInt;

@Config(modid = IuniusRangekeeper.MOD_ID)
public class Configs
{

    @Comment("Color values for the markers on HUD")
    @LangKey(IuniusRangekeeper.MOD_ID + ".config.marker_color")
    public static MarkerColor marker_color = new MarkerColor();

    @Comment("Ballistic parameters for a projactile")
    @LangKey(IuniusRangekeeper.MOD_ID + ".config.ballistic")
    public static Ballistic ballistic = new Ballistic();

    public static class MarkerColor
    {

        @Comment("Alpha color value for the markers on HUD")
        @LangKey(IuniusRangekeeper.MOD_ID + ".config.marker_color.alpha")
        @RangeDouble(min = 0.0, max = 1.0)
        public double colorAlpha = 1.0;

        @Comment("Red color value for the markers on HUD")
        @LangKey(IuniusRangekeeper.MOD_ID + ".config.marker_color.red")
        @RangeDouble(min = 0.0, max = 1.0)
        public double colorRed = 0.0;

        @Comment("Green color value for the markers on HUD")
        @LangKey(IuniusRangekeeper.MOD_ID + ".config.marker_color.green")
        @RangeDouble(min = 0.0, max = 1.0)
        public double colorGreen = 1.0;

        @Comment("Blua color value for the markers on HUD")
        @LangKey(IuniusRangekeeper.MOD_ID + ".config.marker_color.blue")
        @RangeDouble(min = 0.0, max = 1.0)
        public double colorBlue = 0.0;

    }

    public static class Ballistic
    {

        @Comment("Max flight time of the projectile, in ticks")
        @LangKey(IuniusRangekeeper.MOD_ID + ".config.ballistic.max_flight_tick")
        @RangeInt(min = 0, max = 100)
        public int maxFlightTick = 100;

        @Comment("Initial velocity of the projectile, in meters per tick")
        @LangKey(IuniusRangekeeper.MOD_ID + ".config.ballistic.initial_velocity")
        @RangeDouble(min = 0.0, max = 20.0)
        public double initialVelocity = 3.0;

        @Comment("Gravity factor")
        @LangKey(IuniusRangekeeper.MOD_ID + ".config.ballistic.gravity")
        @RangeDouble(min = 0.0, max = 5.0)
        public double gravity = 0.05;

        @Comment("Resistance factor")
        @LangKey(IuniusRangekeeper.MOD_ID + ".config.ballistic.resistance")
        @RangeDouble(min = 0.0, max = 1.0)
        public double resistance = 0.01;

    }

 }
