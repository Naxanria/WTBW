package com.wtbw.item.tools;

import com.wtbw.WTBW;
import com.wtbw.config.CommonConfig;
import com.wtbw.util.StackUtil;
import com.wtbw.util.TextComponentBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

/*
  @author: Naxanria
*/
public class SwapTool extends TieredItem
{
  public static final String TOOLTIP_PRE =  WTBW.MODID + ".tooltip.swap_target_pre";
  public static final String TOOLTIP_NONE =  WTBW.MODID + ".tooltip.swap_none";
  public static final String MESSAGE_TARGET_CHANGED =  WTBW.MODID + ".text.swap_target_changed";
  
  public static CommonConfig config = CommonConfig.get();
  
  public SwapTool(IItemTier tierIn, Properties builder)
  {
    super(tierIn, builder);
  }
  
  @Override
  public ActionResultType onItemUse(ItemUseContext context)
  {
    World world = context.getWorld();
    if (!world.isRemote)
    {
      BlockPos pos = context.getPos();
      PlayerEntity player = context.getPlayer();
      
      if (player == null)
      {
        return ActionResultType.PASS;
      }
  
      BlockState state = world.getBlockState(pos);
  
      Block block = state.getBlock();
  
      if (block.hasTileEntity(state))
      {
        return ActionResultType.FAIL;
      }
  
      ItemStack stack = context.getItem();
  
      if (player.isSneaking())
      {
        if (isBlacklisted(block))
        {
          return ActionResultType.FAIL;
        }
        
        setTarget(stack, block);
        player.sendStatusMessage(
          TextComponentBuilder.createTranslated(MESSAGE_TARGET_CHANGED).white().next(" ")
          .nextTranslate(block.getTranslationKey()).aqua().build(), true);
        return ActionResultType.SUCCESS;
      }
      
      Block target = getTarget(stack);
      
      if (target != Blocks.AIR)
      {
        if (block != target)
        {
          if (isBlacklisted(block))
          {
            return ActionResultType.FAIL;
          }
          
          if (state.getBlockHardness(world, pos) > config.swapperMaxHardness.get())
          {
            return ActionResultType.FAIL;
          }
      
          if (swapBlock(player, pos, target))
          {
            stack.damageItem(1, player, playerEntity -> playerEntity.sendBreakAnimation(EquipmentSlotType.MAINHAND));
          }
        }
      }
    }
    
    return ActionResultType.PASS;
  }
  
  private boolean swapBlock(PlayerEntity player, BlockPos pos, Block target)
  {
    if (player.isAllowEdit())
    {
      // find if and where in the inventory we have the block
      int index = -1;
      boolean creative = player.isCreative();
      if (!creative)
      {
        BlockItem item = (BlockItem) target.asItem();
        index = StackUtil.getIndexOf(player.inventory, item);
        if (index == -1)
        {
          return false;
        }
      }
      
      // set the block to target block
      World world = player.world;
      BlockState swapped = world.getBlockState(pos);
      world.setBlockState(pos, target.getDefaultState(), 3);
  
      if (!creative)
      {
        ItemStack stackInSlot = player.inventory.getStackInSlot(index);
        stackInSlot.shrink(1);
  
//        List<ItemStack> drops = Block.getDrops(swapped, (ServerWorld) world, pos, null, player, player.getHeldItemMainhand());
//        for (ItemStack drop : drops)
//        {
//          player.inventory.addItemStackToInventory(drop);
//        }
        
        player.inventory.addItemStackToInventory(new ItemStack(swapped.getBlock()));
      }
      
      return true;
    }
    else
    {
      return false;
    }
   

  }
  
  private boolean isBlacklisted(Block block)
  {
    return config.swapperBlackList.get().contains(block.getRegistryName().toString());
  }
  
  @Override
  public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
  {
    Block targetBlock = getTarget(stack);
  
    TextComponentBuilder builder = TextComponentBuilder.createTranslated(TOOLTIP_PRE).keepStyle().white().next(" ");
    if (targetBlock != Blocks.AIR)
    {
      builder.nextTranslate(targetBlock.getTranslationKey()).bold().aqua();
    }
    else
    {
      builder.nextTranslate(TOOLTIP_NONE).bold().aqua();
    }
  
    tooltip.add(builder.build());
    
    super.addInformation(stack, worldIn, tooltip, flagIn);
  }
  
  public static void setTarget(ItemStack stack, Block target)
  {
    stack.getOrCreateChildTag("data").putString("targetId", target.getRegistryName().toString());
  }
  
  public static Block getTarget(ItemStack stack)
  {
    CompoundNBT tag = stack.getOrCreateChildTag("data");
    
    if (tag.contains("targetId"))
    {
      return Registry.BLOCK.getOrDefault(new ResourceLocation(tag.getString("targetId")));
    }
    
    return Blocks.AIR;
  }
  
  
}
