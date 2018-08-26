package iunius118.mods.iuniusrangekeeper.item;

import iunius118.mods.iuniusrangekeeper.IuniusRangekeeper;
import iunius118.mods.iuniusrangekeeper.client.gunfirecontrolsystem.IGunDirector;
import iunius118.mods.iuniusrangekeeper.client.gunfirecontrolsystem.Target;
import iunius118.mods.iuniusrangekeeper.client.util.ClientUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RayTraceResult;

public class ItemRangekeeper extends Item
{

    @Override
    public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack)
    {
        if (entityLiving.world.isRemote && entityLiving == Minecraft.getMinecraft().player)
        {
            // Targeting process on Client
            RayTraceResult result = ClientUtils.getMouseOver(256.0D, 1.0F);
            IGunDirector director = IuniusRangekeeper.rangekeeper.director;

            if (Minecraft.getMinecraft().player.isSneaking())
            {
                // When sneaking, release target
                director.setTarget(null);
            }
            else if (result != null && result.typeOfHit != RayTraceResult.Type.MISS)
            {
                // When found target, set target to range-keeper
                double d = result.hitVec.squareDistanceTo(entityLiving.posX, entityLiving.posY, entityLiving.posZ);

                if (d > 36.0D)
                {
                    // Targeting only over some distance (6 meters)
                    director.setTarget(new Target(entityLiving.world, result));
                    // System.out.println(result);
                }
            }
            else
            {
                // The other case: Release target
                director.setTarget(null);
            }
        }

        return super.onEntitySwing(entityLiving, stack);
    }

}
