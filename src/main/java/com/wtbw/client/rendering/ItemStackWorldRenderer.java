package com.wtbw.client.rendering;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.wtbw.util.ColorUtil;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
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
  
  public static void renderOutline(WorldRenderer renderer, ActiveRenderInfo info, World world, BlockState blockstate, BlockPos blockpos)
  {
    if (!blockstate.isAir(world, blockpos) && world.getWorldBorder().contains(blockpos))
    {
      // fixme: reimplement

      Vec3d view = info.getProjectedView();
      double x = view.getX();
      double y = view.getY();
      double z = view.getZ();
      
      Minecraft minecraft = Minecraft.getInstance();
      RenderTypeBuffers buffers = minecraft.func_228019_au_();
      IRenderTypeBuffer.Impl impl = buffers.func_228487_b_();
      IVertexBuilder vertexBuilder =  impl.getBuffer(RenderType.func_228659_m_()); //IRenderTypeBuffer.func_228455_a_(Tessellator.getInstance().getBuffer()).getBuffer(RenderType.func_228659_m_());

      drawShape(new MatrixStack(), vertexBuilder, info.getRenderViewEntity(), x, y, z, blockpos, blockstate);
      // func_228429_a_(
      
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
  
  private static void drawShape(MatrixStack matrixStack, IVertexBuilder vertexBuilder, Entity renderViewEntity, double x, double y, double z, BlockPos pos, BlockState state)
  {
    drawShape(matrixStack, vertexBuilder, renderViewEntity, x, y, z, pos, state, 0x66000000);
  }
  
  private static void drawShape(MatrixStack matrixStack, IVertexBuilder vertexBuilder, Entity renderViewEntity, double x, double y, double z, BlockPos pos, BlockState state, int color)
  {
    Minecraft minecraft = Minecraft.getInstance();
    World world = minecraft.world;
    if (world == null)
    {
      return;
    }
    
    float[] rgba = ColorUtil.getRGBAf(color);
    drawShape
    (
      matrixStack, vertexBuilder,
      state.getShape(world, pos, ISelectionContext.forEntity(renderViewEntity)),
      pos.getX() - x, pos.getY() - y, pos.getZ() - z,
      rgba[0], rgba[1], rgba[2], rgba[3]
    );
  }
  
  private static void drawShape(MatrixStack matrixStack, IVertexBuilder vertexBuilder, VoxelShape shape, double x, double y, double z, float r, float g, float b, float a)
  {
    Matrix4f matrix4f = matrixStack.func_227866_c_().func_227870_a_();
    shape.forEachEdge((px1, py1, pz1, px2, py2, pz2) ->
    {
      vertexBuilder.func_227888_a_(matrix4f, (float)(px1 + x), (float)(py1 + y), (float)(pz1 + z)).func_227885_a_(r, g, b, a).endVertex();
      vertexBuilder.func_227888_a_(matrix4f, (float)(px2 + x), (float)(py2 + y), (float)(pz2 + z)).func_227885_a_(r, g, b, a).endVertex();
    });
  }
}
