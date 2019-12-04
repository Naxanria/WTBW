package com.wtbw.block;

import com.wtbw.tile.EntityPusherTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapeCube;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

/*
  @author: Naxanria
*/
public class PushBlock extends BaseTileBlock<EntityPusherTileEntity>
{
  private static final VoxelShape SHAPE = VoxelShapes.or
  (
    Block.makeCuboidShape(0, 0, 0, 16, 2, 16),
    Block.makeCuboidShape(1, 2, 1, 15, 3, 15)
  );
  
  public PushBlock(Properties properties, EntityPusherTileEntity.PushMode mode)
  {
    super(properties, (world, state) -> new EntityPusherTileEntity(mode));
  }
  
  @Override
  public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
  {
    return SHAPE;
  }
}
