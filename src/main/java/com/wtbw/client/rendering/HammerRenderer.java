package com.wtbw.client.rendering;

import com.wtbw.util.BiValue;
import com.wtbw.util.Utilities;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;

/*
  @author: Naxanria
*/
public class HammerRenderer extends ItemStackWorldRenderer
{
  @Override
  public void render(ItemStack stack, PlayerEntity player)
  {
    BlockRayTraceResult lookingAt = Utilities.getLookingAt(player, 5);
    if (lookingAt.getType() == RayTraceResult.Type.MISS)
    {
      return;
    }

    BiValue<BlockPos, BlockPos> region = Utilities.getRegion(lookingAt.getPos(), lookingAt.getFace(), 3);

    WorldRenderer.drawBoundingBox(region.a.getX(), region.a.getY(), region.a.getZ(), region.b.getX(), region.b.getY(), region.b.getZ(), 1, 1, 1, 1);
  }
}
