package com.wtbw.block.ctm;

import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/*
  @author: Naxanria
*/
public class CTMGlassBlock extends CTMBlock
{
  public CTMGlassBlock(Properties properties)
  {
    super(properties);
  }
  
  public CTMGlassBlock(Properties properties, String textureLocation)
  {
    super(properties, textureLocation);
  }
  
  @OnlyIn(Dist.CLIENT)
  @Override
  public boolean isSideInvisible(BlockState state, BlockState adjacentBlockState, Direction side)
  {
    return adjacentBlockState.getBlock() == this || super.isSideInvisible(state, adjacentBlockState, side);
  }
  
  @Override
  public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos)
  {
    return true;
  }
  
  @Override
  public BlockRenderLayer getRenderLayer()
  {
    return BlockRenderLayer.TRANSLUCENT;
  }
  
  @Override
  public BlockRenderType getRenderType(BlockState p_149645_1_)
  {
    return super.getRenderType(p_149645_1_);
  }
  
  @Override
  public boolean canEntitySpawn(BlockState p_220067_1_, IBlockReader p_220067_2_, BlockPos p_220067_3_, EntityType<?> p_220067_4_)
  {
    return false;
  }
}
