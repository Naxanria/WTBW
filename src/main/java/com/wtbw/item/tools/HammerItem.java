package com.wtbw.item.tools;

import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/*
  @author: Naxanria
*/
public class HammerItem extends PickaxeItem
{
  public HammerItem(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builder)
  {
    super(tier, attackDamageIn, attackSpeedIn, builder);
  }
  
  @Override
  public boolean onBlockDestroyed(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity entity)
  {
    if (!world.isRemote)
    {
      if (state.getBlockHardness(world, pos) == 0)
      {
        return super.onBlockDestroyed(stack, world, state, pos, entity);
      }
      
      stack.attemptDamageItem(1, world.rand, entity instanceof ServerPlayerEntity ? (ServerPlayerEntity) entity : null);
      if (entity instanceof PlayerEntity)
      {
        breakNeighbours(world, pos, ((PlayerEntity) entity));
      }
    }
    
    return super.onBlockDestroyed(stack, world, state, pos, entity);
  }
  
  private void breakNeighbours(World world, BlockPos pos, PlayerEntity player)
  {
  
  }
  
  @Override
  public float getDestroySpeed(ItemStack stack, BlockState state)
  {
    return super.getDestroySpeed(stack, state) / 6f;
  }
}
