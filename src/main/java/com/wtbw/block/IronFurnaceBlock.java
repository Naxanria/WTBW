package com.wtbw.block;

import com.wtbw.tile.furnace.BaseFurnaceTileEntity;
import com.wtbw.tile.furnace.FurnaceTier;
import net.minecraft.item.crafting.IRecipeType;

/*
  @author: Naxanria
*/
public class IronFurnaceBlock extends BaseFurnaceBlock<BaseFurnaceTileEntity>
{
  public IronFurnaceBlock(Properties properties)
  {
    super(properties, (world, state) -> new BaseFurnaceTileEntity(FurnaceTier.IRON, IRecipeType.SMELTING));
  }
}
