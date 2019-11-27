package com.wtbw.tile;

import com.wtbw.WTBW;
import com.wtbw.tile.furnace.BaseFurnaceTileEntity;
import com.wtbw.tile.redstone.RedstoneTimerTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.registries.ObjectHolder;

/*
  @author: Naxanria
*/
@ObjectHolder(WTBW.MODID)
public class ModTiles
{
  public static final TileEntityType<BaseFurnaceTileEntity> IRON_FURNACE = null;
  public static final TileEntityType<RedstoneTimerTileEntity> REDSTONE_TIMER = null;
}
