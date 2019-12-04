package com.wtbw.block.spikes;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

/*
  @author: Naxanria
*/
public class SpikesBlock extends Block
{
  private static final VoxelShape SHAPE = Block.makeCuboidShape(0, 0, 0, 16, 5, 16);
  
  private final SpikesType type;
  
  public SpikesBlock(Properties properties, SpikesType type)
  {
    super(properties);
    this.type = type;
  }
  
  @Override
  public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn)
  {
    if (entityIn instanceof LivingEntity)
    {
      LivingEntity livingEntity = (LivingEntity) entityIn;
      if (type.lethal || livingEntity.getHealth() > 1f)
      {
//        livingEntity.attackEntityFrom(DamageSource.HOT_FLOOR, 1f);
        livingEntity.attackEntityFrom(new SpikesDamageSource(type), type.damage);
      }
    }
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
