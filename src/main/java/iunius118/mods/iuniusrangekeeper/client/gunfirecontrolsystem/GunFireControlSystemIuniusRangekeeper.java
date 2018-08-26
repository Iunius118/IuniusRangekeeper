package iunius118.mods.iuniusrangekeeper.client.gunfirecontrolsystem;

import iunius118.mods.iuniusrangekeeper.config.Configs;
import net.minecraft.world.World;

public class GunFireControlSystemIuniusRangekeeper
{
    public DirectorIuniusRangekeeper director = new DirectorIuniusRangekeeper();
    public ComputerIuniusRangekeeper computer = new ComputerIuniusRangekeeper();
    public IndicatorIuniusRangekeeper indicator = new IndicatorIuniusRangekeeper();

    public void updateDirectorAndComputer(World world)
    {
        computer.setBallisticParameters(
                Configs.ballistic.maxFlightTick,
                Configs.ballistic.initialVelocity,
                Configs.ballistic.gravity,
                Configs.ballistic.resistance);
        director.update(world, computer);
    }

    public void updateIndicator(World world, float partialTicks)
    {
        indicator.setComputer(computer);
        indicator.update(world, partialTicks);
    }
}
