package com.wtbw.item.tools;

import com.wtbw.Flags;
import com.wtbw.WTBW;
import com.wtbw.config.CommonConfig;
import com.wtbw.util.NBTHelper;
import com.wtbw.util.RandomUtil;
import com.wtbw.util.Utilities;
import net.minecraft.block.*;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.*;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/*
  @author: Naxanria
*/
public class WateringCan extends Item
{
  public enum Tier
  {
    BASIC,
    QUARTZ,
    DIAMOND,
    ENDER;
  }
  
  public static class WateringCanData
  {
    public int radius;
    public int maxWater;
    public int waterUse;
    public int chance;
  
    public WateringCanData(int radius, int maxWater, int waterUse, int chance)
    {
      this.radius = radius;
      this.maxWater = maxWater;
      this.waterUse = waterUse;
      this.chance = chance;
    }
  }
  
  private static Map<Tier, WateringCanData> tierMap = new HashMap<Tier, WateringCanData>()
  {{
    put(Tier.BASIC, new WateringCanData(3, 1000, 5, 10));
    put(Tier.QUARTZ, new WateringCanData(3, 5000, 5, 25));
    put(Tier.DIAMOND, new WateringCanData(5, 10000, 5, 35));
    put(Tier.ENDER, new WateringCanData(7, 10000, 5, 50));
  }};
  
  public static WateringCanData getData(Tier tier)
  {
    return tierMap.get(tier);
  }
  
  protected WateringCanData wateringCanData;
  protected Tier tier;
  
  public WateringCan(Properties properties, Tier tier)
  {
    super(properties);
    
    this.tier = tier;
    wateringCanData = tierMap.getOrDefault(tier, tierMap.get(Tier.BASIC));
  }
  
  @Override
  public int getUseDuration(ItemStack stack)
  {
    return 100000000;
  }
  
  private int getWater(ItemStack stack)
  {
    CompoundNBT data = stack.getOrCreateChildTag("data");
    return NBTHelper.getInt(data, "water");
  }
  
  private void setWater(ItemStack stack, int amount)
  {
    amount = MathHelper.clamp(amount, 0, wateringCanData.maxWater);
    stack.getOrCreateChildTag("data").putInt("water", amount);
  }
  
  private int changeWater(ItemStack stack, int delta)
  {
    CompoundNBT data = stack.getOrCreateChildTag("data");
    int water = NBTHelper.getInt(data, "water");
    data.putInt("water", water = MathHelper.clamp(water + delta, 0, wateringCanData.maxWater));
    return water;
  }
  
  @Override
  public boolean showDurabilityBar(ItemStack stack)
  {
    return getWater(stack) < wateringCanData.maxWater;
  }
  
  @Override
  public double getDurabilityForDisplay(ItemStack stack)
  {
    return 1.0 - (getWater(stack) / (double) wateringCanData.maxWater);
  }
  
  @Override
  public ActionResultType onItemUse(ItemUseContext context)
  {
    return super.onItemUse(context);
  }
  
  @Override
  public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
  {
    playerIn.setActiveHand(handIn);
    return super.onItemRightClick(worldIn, playerIn, handIn);
  }
  
  @Override
  public void onUsingTick(ItemStack stack, LivingEntity player, int count)
  {
    if (!(player instanceof PlayerEntity))
    {
      return;
    }
  
    CommonConfig config = CommonConfig.get();
    
    World world = player.world;
    Random rand = world.rand;
    BlockRayTraceResult lookingAt = Utilities.getLookingAt((PlayerEntity) player, 5, RayTraceContext.FluidMode.SOURCE_ONLY);
    if (Utilities.isMiss(lookingAt))
    {
      return;
    }
    
    BlockState lookingState = world.getBlockState(lookingAt.getPos());
    if (lookingState.getFluidState().getFluid() == Fluids.WATER)
    {
      // todo? Make refill be configurable
      changeWater(stack, config.wateringCanRefillRate.get());
      
      return;
    }
    
    // enough maxWater
    if (!((PlayerEntity) player).isCreative() && changeWater(stack, -wateringCanData.waterUse) == 0)
    {
      return;
    }
  
    boolean spreadGrass = config.wateringCanSpreadGrass.get();
    boolean moisturize = config.wateringCanMoisterize.get();
    
    // get in radius
    List<BlockPos> blocks = Utilities.getBlocks(lookingAt.getPos(), Direction.DOWN, wateringCanData.radius);
    for (BlockPos pos : blocks)
    {
      if (world.isRemote && RandomUtil.chance(rand, 0.3f))
      {
        // todo: proper rain/water particles
        double x = pos.getX() + rand.nextDouble();
        double y = pos.getY() + rand.nextDouble() + .7;
        double z = pos.getZ() + rand.nextDouble();
//        double dx = world.rand.nextDouble() * .2;
//        double dy = world.rand.nextDouble() * -.3 - .4;
//        double dz = world.rand.nextDouble() * .2;
//
//        world.addParticle(new RedstoneParticleData(0, 0, 0.9f, 0.8f), x, y, z, dx, dy, dz);
        world.addParticle(ParticleTypes.RAIN, x, y, z, 0, 0, 0);
      }
  
      // Chance: P/20 -> 1/20th of a chance per tick, naively make it be ~P/sec
      if (!RandomUtil.chance(rand, (wateringCanData.chance / 100f / 20f)))
      {
        continue;
      }

      
      BlockState state = world.getBlockState(pos);
      Block block = state.getBlock();
      CropsBlock crop = null;
      SaplingBlock sapling = null;

      if (spreadGrass && block instanceof SpreadableSnowyDirtBlock)
      {
        if (!world.isRemote)
        {
          ((SpreadableSnowyDirtBlock) block).func_225534_a_(state, (ServerWorld) world, pos, rand);
        }
        else
        {
          BoneMealItem.spawnBonemealParticles(world, pos.up(), RandomUtil.range(rand, 3, 6));
        }
        
        continue;
      }
      
      if (block instanceof CropsBlock)
      {
        crop = (CropsBlock) block;
      }
      else if (block instanceof SaplingBlock)
      {
        sapling = (SaplingBlock) block;
      }
      
      if (crop == null && sapling == null)
      {
  
        if (moisturize && block instanceof FarmlandBlock)
        {
          int moisture = state.get(FarmlandBlock.MOISTURE);
          if (moisture < 7)
          {
            BlockState newState = state.cycle(FarmlandBlock.MOISTURE);
            world.setBlockState(pos, newState, 3);
          }
        }
  
        pos = pos.up();
        state = world.getBlockState(pos);
        block = state.getBlock();

        if (block instanceof CropsBlock)
        {
          crop = (CropsBlock) block;
        }
        else if (block instanceof SaplingBlock)
        {
          sapling = (SaplingBlock) block;
        }
      }
  
      if (crop == null && sapling == null)
      {
        continue;
      }
      
      if (crop != null)
      {
        if (!crop.isMaxAge(state) && crop.canGrow(world, pos, state, world.isRemote))
        {
          crop.grow(world, pos, state);
          if (world.isRemote)
          {
            BoneMealItem.spawnBonemealParticles(world, pos, RandomUtil.range(rand, 3, 6));
          }
        }
      }
      else
      {
        if (sapling.canGrow(world, pos, state, world.isRemote))
        {
          if (!world.isRemote)
          {
            sapling.func_226942_a_((ServerWorld) world, pos, state, rand);
          }
          else
          {
            BoneMealItem.spawnBonemealParticles(world, pos, RandomUtil.range(rand, 3, 6));
          }
        }
      }
    }
  }
  
  @Override
  public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
  {
    // todo: make localized
    int water = getWater(stack);
    tooltip.add(new StringTextComponent("Water: " + water + "/" + wateringCanData.maxWater));
    tooltip.add(new StringTextComponent("Radius: " + wateringCanData.radius));
    
    super.addInformation(stack, worldIn, tooltip, flagIn);
  }
  
  @Override
  public boolean canContinueUsing(ItemStack oldStack, ItemStack newStack)
  {
    return true;
  }
  
  @Override
  public UseAction getUseAction(ItemStack stack)
  {
    return UseAction.BLOCK;
  }
}
