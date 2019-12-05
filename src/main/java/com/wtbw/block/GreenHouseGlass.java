package com.wtbw.block;

import com.wtbw.util.RandomUtil;
import net.minecraft.block.*;
import net.minecraft.item.DyeColor;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

/*
  @author: Naxanria
*/
public class GreenHouseGlass extends AbstractGlassBlock implements IBeaconBeamColorProvider
{
  public GreenHouseGlass(Properties properties)
  {
    super(properties.tickRandomly());
  }
  
  @Override
  public BlockRenderLayer getRenderLayer()
  {
    return BlockRenderLayer.TRANSLUCENT;
  }
  
  @Override
  public DyeColor getColor()
  {
    return DyeColor.GREEN;
  }
  
  @Override
  public void randomTick(BlockState state, World world, BlockPos pos, Random rand)
  {
    int maxRange = 25;
    int chance = 40;
    
    if (!world.isRemote)
    {
      if (!world.isDaytime())
      {
        return;
      }
      
      if (RandomUtil.chance(rand, chance))
      {
        if (world.canBlockSeeSky(pos.up()))
        {
          boolean grown = false;
          for (int i = 1; i < maxRange; i++)
          {
            BlockPos check = pos.down(i);
            BlockState foundState = world.getBlockState(check);
            Block block = foundState.getBlock();
            if (block instanceof CropsBlock)
            {
              grown = true;
              ((CropsBlock) block).grow(world, check, foundState);
         
              break;
            }
            if (block instanceof SaplingBlock)
            {
              grown = true;
              ((SaplingBlock) block).grow(world, check, foundState, rand);

              break;
            }
          }
          
          if (grown)
          {
            // particles
          }
        }
      }
    }
  }
  
  
}
