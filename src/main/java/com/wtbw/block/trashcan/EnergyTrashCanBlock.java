package com.wtbw.block.trashcan;

import com.wtbw.block.BaseTileBlock;
import com.wtbw.tile.trashcan.EnergyTrashCanTileEntity;
import net.minecraft.block.BlockState;
//import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

/*
  @author: Naxanria
*/
public class EnergyTrashCanBlock extends BaseTileBlock<EnergyTrashCanTileEntity>
{
  public EnergyTrashCanBlock(Properties properties)
  {
    super(properties, (world, state) -> new EnergyTrashCanTileEntity());
  }

//  @Override
//  public BlockRenderLayer getRenderLayer()
//  {
//    return BlockRenderLayer.CUTOUT;
//  }

  @Override
  public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
  {
    return TrashCanBlock.SHAPE;
  }
}
