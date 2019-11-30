package com.wtbw.block.trashcan;

import com.wtbw.block.BaseTileBlock;
import com.wtbw.tile.trashcan.TrashCanTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

/*
  @author: Naxanria
*/
public class TrashCanBlock extends BaseTileBlock<TrashCanTileEntity>
{
  public static final VoxelShape BOTTOM = Block.makeCuboidShape(2, 0, 2, 14, 10, 14);
  public static final VoxelShape TOP = Block.makeCuboidShape(1, 10, 1, 15, 12, 15);
  public static final VoxelShape SHAPE = VoxelShapes.or(BOTTOM, TOP);
  
  public TrashCanBlock(Properties properties)
  {
    super(properties, (world, state) -> new TrashCanTileEntity());
  }
  
  @Override
  public BlockRenderLayer getRenderLayer()
  {
    return BlockRenderLayer.CUTOUT;
  }
  
  @Override
  public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
  {
    return SHAPE;
  }
}
