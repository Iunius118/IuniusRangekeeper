package iunius118.mods.iuniusrangekeeper.client;

import org.lwjgl.opengl.GL11;

import iunius118.mods.iuniusrangekeeper.IuniusRangekeeper;
import iunius118.mods.iuniusrangekeeper.IuniusRangekeeper.ITEMS;
import iunius118.mods.iuniusrangekeeper.client.gunfirecontrolsystem.GunFireControlSystemIuniusRangekeeper;
import iunius118.mods.iuniusrangekeeper.config.Configs;
import iunius118.mods.iuniusrangekeeper.item.ItemRangekeeper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.Item;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.config.Config.Type;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;

public class ClientEventHandler
{

    @SubscribeEvent
    public void onConfigChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event)
    {
        if (event.getModID().equals(IuniusRangekeeper.MOD_ID))
        {
            ConfigManager.sync(IuniusRangekeeper.MOD_ID, Type.INSTANCE);
        }
    }

    @SubscribeEvent
    public void registerModels(ModelRegistryEvent event)
    {
        ModelLoader.setCustomModelResourceLocation(ITEMS.rangekeeper, 0, new ModelResourceLocation(ITEMS.rangekeeper.getRegistryName(), "inventory"));
    }

    @SubscribeEvent
    public void onClientTickEvent(ClientTickEvent event)
    {
        if (event.phase == ClientTickEvent.Phase.END)
        {
            return;
        }

        World world = Minecraft.getMinecraft().world;
        IuniusRangekeeper.rangekeeper.updateDirectorAndComputer(world);
    }

    @SubscribeEvent
    public void onRenderWorldLastEvent(RenderWorldLastEvent event)
    {
        World world = Minecraft.getMinecraft().world;
        IuniusRangekeeper.rangekeeper.updateIndicator(world, event.getPartialTicks());
    }

    @SubscribeEvent
    public void onRenderGameOverlayEventPre(RenderGameOverlayEvent.Pre event)
    {
        Minecraft mc = Minecraft.getMinecraft();

        if (event.getType() == ElementType.HOTBAR && mc.getRenderManager().getFontRenderer() != null && mc.getRenderManager().options.thirdPersonView < 1)
        {
            Item itemMainHand = null;

            if(mc.player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemRangekeeper || mc.player.getHeldItem(EnumHand.OFF_HAND).getItem() instanceof ItemRangekeeper)
            {
            }
            else
            {
                return;
            }

            GunFireControlSystemIuniusRangekeeper gfcs = IuniusRangekeeper.rangekeeper;

            Vec3d vec3Target = gfcs.indicator.getTargetScreenPos();
            Vec3d vec3Marker = gfcs.indicator.getTargetFutureScreenPos();

            if (vec3Target == null && vec3Marker == null)
            {
                return;
            }

            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder vertexbuffer = tessellator.getBuffer();
            double markerSize = 4.0D;

            GlStateManager.disableDepth();
            GlStateManager.enableBlend();
            GlStateManager.disableTexture2D();
            GlStateManager.color(
                    (float) Configs.marker_color.colorRed,
                    (float) Configs.marker_color.colorGreen,
                    (float) Configs.marker_color.colorBlue,
                    (float) Configs.marker_color.colorAlpha);
            GlStateManager.glLineWidth(1.0F);

            if (vec3Target != null)
            {
                double x = vec3Target.x;
                double y = vec3Target.y;
                vertexbuffer.begin(GL11.GL_LINE_LOOP, DefaultVertexFormats.POSITION);
                vertexbuffer.pos(x - markerSize, y - markerSize, 0.0D).endVertex();
                vertexbuffer.pos(x + markerSize, y - markerSize, 0.0D).endVertex();
                vertexbuffer.pos(x + markerSize, y + markerSize, 0.0D).endVertex();
                vertexbuffer.pos(x - markerSize, y + markerSize, 0.0D).endVertex();
                tessellator.draw();
            }

            if (vec3Marker != null)
            {
                double x = vec3Marker.x;
                double y = vec3Marker.y;
                vertexbuffer.begin(GL11.GL_LINE_LOOP, DefaultVertexFormats.POSITION);
                vertexbuffer.pos(x, y - markerSize, 0.0D).endVertex();
                vertexbuffer.pos(x + markerSize, y, 0.0D).endVertex();
                vertexbuffer.pos(x, y + markerSize, 0.0D).endVertex();
                vertexbuffer.pos(x - markerSize, y, 0.0D).endVertex();
                tessellator.draw();
            }

            GlStateManager.enableTexture2D();
            GlStateManager.enableDepth();
        }
    }

}
