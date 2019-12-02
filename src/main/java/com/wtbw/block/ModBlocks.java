package com.wtbw.block;

import com.wtbw.WTBW;
import com.wtbw.block.trashcan.EnergyTrashCanBlock;
import com.wtbw.block.trashcan.FluidTrashCanBlock;
import com.wtbw.block.trashcan.TrashCanBlock;
import com.wtbw.tile.MagnetInhibitorTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraftforge.registries.ObjectHolder;

/*
  @author: Naxanria
*/
@ObjectHolder(WTBW.MODID)
public class ModBlocks
{
  public static final Block CHARCOAL_BLOCK = Blocks.AIR;
  public static final Block BLAZE_BLOCK = Blocks.AIR;

  public static final TieredFurnaceBlock IRON_FURNACE = null;
  public static final TieredFurnaceBlock GOLD_FURNACE = null;
  public static final TieredFurnaceBlock DIAMOND_FURNACE = null;
  public static final TieredFurnaceBlock END_FURNACE = null;
  
  public static final Block REDSTONE_TIMER = Blocks.AIR;
  
  public static final TrashCanBlock TRASHCAN = null;
  public static final FluidTrashCanBlock FLUID_TRASHCAN = null;
  public static final EnergyTrashCanBlock ENERGY_TRASHCAN = null;
  
  public static final BaseTileBlock<MagnetInhibitorTileEntity> MAGNET_INHIBITOR = null;
}
