package com.wtbw.block;

import com.wtbw.tile.furnace.BaseFurnaceTileEntity;
import com.wtbw.tile.furnace.FurnaceTier;
import net.minecraft.item.crafting.IRecipeType;

/*
  @author: Naxanria
*/
public class TieredFurnaceBlock extends BaseFurnaceBlock<BaseFurnaceTileEntity> {
    public TieredFurnaceBlock(Properties properties, FurnaceTier tier) {
        super(properties, (world, state) -> new BaseFurnaceTileEntity(tier, IRecipeType.SMELTING));
    }
}
