package com.wtbw.tile;

import com.wtbw.WTBW;
import com.wtbw.tile.furnace.BaseFurnaceTileEntity;
import com.wtbw.tile.redstone.RedstoneTimerTileEntity;
import com.wtbw.tile.trashcan.FluidTrashCanTileEntity;
import com.wtbw.tile.trashcan.TrashCanTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.registries.ObjectHolder;

/*
  @author: Naxanria
*/
@ObjectHolder(WTBW.MODID)
public class ModTiles
{
  public static final TileEntityType<BaseFurnaceTileEntity> IRON_FURNACE = null;
  public static final TileEntityType<BaseFurnaceTileEntity> GOLD_FURNACE = null;
  public static final TileEntityType<BaseFurnaceTileEntity> DIAMOND_FURNACE = null;
  public static final TileEntityType<BaseFurnaceTileEntity> END_FURNACE = null;

  public static final TileEntityType<RedstoneTimerTileEntity> REDSTONE_TIMER = null;

  public static final TileEntityType<TrashCanTileEntity> TRASHCAN = null;
  public static final TileEntityType<FluidTrashCanTileEntity> FLUID_TRASHCAN = null;
  public static final TileEntityType<TrashCanTileEntity> ENERGY_TRASHCAN = null;

  public static final TileEntityType<MagnetInhibitorTileEntity> MAGNET_INHIBITOR = null;
  
  public static final TileEntityType<EntityPusherTileEntity> PUSHER = null;
  public static final TileEntityType<EntityPusherTileEntity> PULLER = null;
}
