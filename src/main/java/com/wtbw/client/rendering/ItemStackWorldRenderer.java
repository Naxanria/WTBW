package com.wtbw.client.rendering;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.world.World;

/*
  @author: Naxanria
*/
public abstract class ItemStackWorldRenderer
{
  protected static Minecraft minecraft = Minecraft.getInstance();
  
  public static Vec3d getPlayerPos()
  {
//    return new Vec3d(TileEntityRendererDispatcher.staticPlayerX, TileEntityRendererDispatcher.staticPlayerY, TileEntityRendererDispatcher.staticPlayerZ);
    // fixme: proper position etc
    return new Vec3d(0, 0, 0);
  }

  public static Vec3d getPosTranslated(BlockPos pos)
  {
    return getPlayerPos().subtract(pos.getX(), pos.getY(), pos.getZ());
  }

  public abstract boolean render(ItemStack stack, PlayerEntity player, RayTraceResult hit, WorldRenderer context, ActiveRenderInfo info);
  
  public static void renderOutline(ActiveRenderInfo info, World world, BlockState blockstate, BlockPos blockpos)
  {
    if (!blockstate.isAir(world, blockpos) && world.getWorldBorder().contains(blockpos))
    {
      // fixme: reimplement
//      RenderSystem.enableBlend();
//      RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
//      RenderSystem.lineWidth(Math.max(2.5F, (float) minecraft.mainWindow.getFramebufferWidth() / 1920.0F * 2.5F));
//      RenderSystem.disableTexture();
//      RenderSystem.depthMask(false);
//      RenderSystem.matrixMode(5889);
//      RenderSystem.pushMatrix();
//      RenderSystem.scalef(1.0F, 1.0F, 0.999F);
//      double d0 = info.getProjectedView().x;
//      double d1 = info.getProjectedView().y;
//      double d2 = info.getProjectedView().z;
//
//      WorldRenderer.drawShape(
//        blockstate.getShape(world, blockpos, ISelectionContext.forEntity(info.getRenderViewEntity())),
//        (double) blockpos.getX() - d0, (double) blockpos.getY() - d1, (double) blockpos.getZ() - d2, 0.0F, 0.0F, 0.0F, 0.4F);
//      RenderSystem.popMatrix();
//      RenderSystem.matrixMode(5888);
//      RenderSystem.depthMask(true);
//      RenderSystem.enableTexture();
//      RenderSystem.disableBlend();
    }
  }
}
