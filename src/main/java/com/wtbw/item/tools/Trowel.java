package com.wtbw.item.tools;

import com.wtbw.WTBW;
import com.wtbw.util.RandomUtil;
import com.wtbw.util.TextComponentBuilder;
import com.wtbw.util.Utilities;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Collectors;

/*
  @author: Naxanria
*/
public class Trowel extends Item
{
  public Trowel(Properties properties)
  {
    super(properties.maxStackSize(1).maxDamage(768));
  }

  @Override
  public ActionResultType onItemUse(ItemUseContext context)
  {
    World world = context.getWorld();
    PlayerEntity player = context.getPlayer();

    if (player == null)
    {
      return ActionResultType.PASS;
    }

    int currentItem = player.inventory.currentItem;

    List<ItemStack> items;
    if (context.getHand() == Hand.OFF_HAND)
    {
      items = Utilities.getHotbar(player);
    }
    else
    {
      items = Utilities.getHotbar(player, currentItem);
    }

    List<ItemStack> collected = items.stream().filter(stack -> !stack.isEmpty() && stack.getItem() instanceof BlockItem).collect(Collectors.toList());
    if (collected.size() == 0)
    {
      return ActionResultType.FAIL;
    }

    if (!world.isRemote)
    {
      ItemStack toPlace = RandomUtil.get(world.rand, collected);
      int prePlaceCount = toPlace.getCount();
      BlockItem blockItem = (BlockItem) toPlace.getItem().getItem();


      ActionResultType placeResult = blockItem.onItemUse(new TrowelContext(context, toPlace));

      if (placeResult == ActionResultType.SUCCESS && !player.isCreative() && player instanceof ServerPlayerEntity)
      {
        context.getItem().damageItem(1, player, playerEntity -> playerEntity.sendBreakAnimation(context.getHand() == Hand.MAIN_HAND ? EquipmentSlotType.MAINHAND : EquipmentSlotType.OFFHAND));
//        context.getItem().attemptDamageItem(1, world.rand, (ServerPlayerEntity) player);
      }
      if (player.isCreative())
      {
        toPlace.setCount(prePlaceCount);
      }

      return placeResult;
    }

    return ActionResultType.PASS;
  }

  @Override
  public boolean isEnchantable(ItemStack stack)
  {
    return super.isEnchantable(stack);
  }

  @Override
  public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment)
  {
    if (stack.isEnchanted())
    {
      return false;
    }

    if (enchantment == Enchantments.UNBREAKING || enchantment == Enchantments.MENDING)
    {
      return true;
    }

    return false;
  }

  @Override
  public boolean isBookEnchantable(ItemStack stack, ItemStack book)
  {
//    Map<Enchantment, Integer> current = EnchantmentHelper.getEnchantments(stack);
//    Map<Enchantment, Integer> toAdd = EnchantmentHelper.getEnchantments(book);
//
    // fixme: only allow proper books instead of rejecting all

    return false;
  }

  @Override
  public int getItemEnchantability()
  {
    return 14;
  }

  private static class TrowelContext extends ItemUseContext
  {
    protected final ItemStack actualItem;

    public TrowelContext(ItemUseContext context, ItemStack toPlace)
    {
      super(context.getWorld(), context.getPlayer(), context.getHand(), context.getItem(), new BlockRayTraceResult(context.getHitVec(), context.getFace(), context.getPos(), context.isPlacerSneaking()));
      actualItem = toPlace;
    }

    @Override
    public ItemStack getItem()
    {
      return actualItem;
    }

    @Override
    public Hand getHand()
    {
      return Hand.MAIN_HAND;
    }
  }
  
  @Override
  public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
  {
    String baseKey = WTBW.MODID + ".tooltip.trowel";
    tooltip.add(TextComponentBuilder.createTranslated(baseKey).aqua().build());
  
    super.addInformation(stack, worldIn, tooltip, flagIn);
  }
}
