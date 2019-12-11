package com.wtbw.item;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EnderChestInventory;
import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

/*
  @author: Naxanria
*/
public class EnderPouchItem extends Item
{
  public EnderPouchItem(Properties properties)
  {
    super(properties);
  }
  
  @Override
  public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand handIn)
  {
    ItemStack stack = player.getHeldItem(handIn);
    if (stack.getItem() != this)
    {
      return new ActionResult<>(ActionResultType.FAIL, stack);
    }
  
    if (!world.isRemote)
    {
      EnderChestInventory enderInventory = player.getInventoryEnderChest();
      player.openContainer(new SimpleNamedContainerProvider
        (
          (id, playerInventory, playerEntity) -> ChestContainer.createGeneric9X3(id, playerInventory, enderInventory),
          new TranslationTextComponent("container.enderchest"))
      );
    }
  
    return new ActionResult<>(ActionResultType.SUCCESS, stack);
  }
}
