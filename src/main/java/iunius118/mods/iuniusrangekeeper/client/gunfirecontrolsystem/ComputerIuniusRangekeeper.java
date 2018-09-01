package iunius118.mods.iuniusrangekeeper.client.gunfirecontrolsystem;

import javax.annotation.Nullable;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ComputerIuniusRangekeeper implements IGunComputer
{

    private boolean isValid = false;
    private IGunDirector director;
    private Vec3d futureLoF;

    private int tickMaxFuse = 100;
    private double initialVelocity = 3.0;
    private double gravity = 0.05;
    private double resistance = 0.01;

    private int tickFuse = 0;   // Fuse tick to set
    private double[] v0Rate = new double[128];
    private double gRRate;

    private static final double MAX_DISTANCE = 256.0;

    public ComputerIuniusRangekeeper()
    {
        updateBallisticParameters(true, true);
    }

    public void setBallisticParameters(int tickMaxFuse, double initialVelocity, double gravity, double resistance)
    {
        boolean hasGravityChanged = (this.gravity != gravity);
        boolean hasResistanceChanged = (this.resistance != resistance);

        this.tickMaxFuse = tickMaxFuse;
        this.initialVelocity = initialVelocity;
        this.gravity = gravity;
        this.resistance = resistance;
        updateBallisticParameters(hasGravityChanged, hasResistanceChanged);
    }

    private void updateBallisticParameters(boolean hasGravityChanged, boolean hasResistanceChanged)
    {
        if (hasResistanceChanged)
        {
            for (int i = 0; i < v0Rate.length; i++)
            {
                v0Rate[i] = 1.0 / (1.0 - Math.exp(-resistance * i));
            }
        }

        if (hasGravityChanged || hasResistanceChanged)
        {
            gRRate = gravity / resistance;
        }
    }

    @Override
    public boolean isValid()
    {
        return this.isValid;
    }

    @Override
    public void setDirector(@Nullable IGunDirector director)
    {
        this.director = director;
    }

    @Override
    @Nullable
    public IGunDirector getDirector()
    {
        return this.director;
    }

    @Override
    public Vec3d getTargetFutureLineOfFire()
    {
        return this.futureLoF;
    }


    @Override
    public int getFuse()
    {
        return this.tickFuse;
    }

    @Override
    public void update(@Nullable World world)
    {
        /*
         * Compute future target direction from the player and the fuse time for EntityThrowable (gravity velocity: 0.03, attenuation rate: 0.99) of which initial velocity is 4 m/ticks.
         */

        if (director == null && !director.isValid(world))
        {
            this.isValid = false;
            return;
        }

        Vec3d vec3TargetDelta = director.getTargetMotion(world);

        if (vec3TargetDelta == null)
        {
            this.isValid = false;
            return;
        }

        Vec3d vec3Target1 = director.getTargetPos(world).add(vec3TargetDelta);

        if (vec3Target1 == null)
        {
            this.isValid = false;
            return;
        }

        Entity player = Minecraft.getMinecraft().player;

        if (player == null)
        {
            this.isValid = false;
            return;
        }

        Vec3d vec3Player = new Vec3d(player.posX, player.posY + player.getEyeHeight(), player.posZ);
        final double v0sq = this.initialVelocity * this.initialVelocity;

        int t = 0; // Fuse tick to set

        // Skip tick by distance
        if(this.director.getTarget().type != Target.Type.ENTITY) {
            double r = vec3Player.distanceTo(vec3Target1);

            if (r > MAX_DISTANCE) {
                // Out of range
                this.isValid = false;
                return;
            }
        } else {
            for (t = 1; t <= this.tickMaxFuse; t++) {
                double r = vec3Player.distanceTo(vec3Target1);

                if (r <= MAX_DISTANCE) {
                    break;
                }

                vec3Target1 = vec3Target1.add(vec3TargetDelta);
            }
        }

        // Calculate initial velocity from tick, height and distance
        double x1 = vec3Target1.x - vec3Player.x;
        double z1 = vec3Target1.z - vec3Player.z;
        double tx1 = Math.sqrt(x1 * x1 + z1 * z1);
        double ty1 = vec3Target1.y - vec3Player.y;
        double v0x1 = resistance * tx1 * ticksToV0Rate(t);
        double v0y1 = (resistance * ty1 + gravity * t) * ticksToV0Rate(t) - gRRate;
        double v0sq1 = v0x1 * v0x1 + v0y1 * v0y1;

        for (; t <= tickMaxFuse; t++)
        {
            // Calculate initial velocity from tick + 1, height and distance
            Vec3d vec3Target2 = vec3Target1.add(vec3TargetDelta);
            double x2 = vec3Target2.x - vec3Player.x;
            double z2 = vec3Target2.z - vec3Player.z;
            double tx2 = Math.sqrt(x2 * x2 + z2 * z2);
            double ty2 = vec3Target2.y - vec3Player.y;
            double v0x2 = resistance * tx2 * ticksToV0Rate(t + 1);
            double v0y2 = (resistance * ty2 + gravity * (t + 1)) * ticksToV0Rate(t + 1) - gRRate;
            double v0sq2 = v0x2 * v0x2 + v0y2 * v0y2;

            // If the initial velocity which calculated is closest to the real one (4 m/ticks), update the future target direction from the player and the fuse tick, and return
            if ((v0sq1 > v0sq2 && v0sq1 >= v0sq && v0sq2 <= v0sq) || (v0sq1 < v0sq2 && v0sq1 <= v0sq && v0sq2 >= v0sq))
            {
                if (Math.abs(v0sq1 - v0sq) <= Math.abs(v0sq2 - v0sq))
                {
                    this.futureLoF = new Vec3d(vec3Target1.x, vec3Player.y + (v0y1 / v0x1 * tx1), vec3Target1.z);
                }
                else
                {
                    if (t < tickMaxFuse)
                    {
                        t++;
                    }

                    this.futureLoF = new Vec3d(vec3Target2.x, vec3Player.y + (v0y2 / v0x2 * tx2), vec3Target2.z);
                }

                this.tickMaxFuse = t;
                this.isValid = (t > 0);
                return;
            }

            // Tick progress
            vec3Target1 = vec3Target2;
            tx1 = tx2;
            ty1 = ty2;
            v0x1 = v0x2;
            v0y1 = v0y2;
            v0sq1 = v0sq2;
        }

        // Out of range
        this.isValid = false;
        return;
    }

    private double ticksToV0Rate(int t)
    {
        if (t >= 0 && t < 256)
        {
            return v0Rate[t];
        }
        else
        {
            return 1.0 / (1.0 - Math.exp(-resistance * t));
        }
    }

}
