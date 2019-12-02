package com.wtbw.client.rendering;

import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

/*
  @author: Naxanria
*/
public abstract class ItemStackWorldRenderer
{
  public static Vec3d getPlayerPos()
  {
    return new Vec3d(TileEntityRendererDispatcher.staticPlayerX, TileEntityRendererDispatcher.staticPlayerY, TileEntityRendererDispatcher.staticPlayerZ);
  }
  
  public static Vec3d getPosTranslated(BlockPos pos)
  {
    return getPlayerPos().subtract(pos.getX(), pos.getY(), pos.getZ());
  }
  
  public abstract void render(ItemStack stack, PlayerEntity player);
}
