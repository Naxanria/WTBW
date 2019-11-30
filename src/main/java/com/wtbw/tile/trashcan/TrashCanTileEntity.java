package com.wtbw.tile.trashcan;

import com.wtbw.gui.container.TrashCanContainer;
import com.wtbw.tile.ModTiles;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/*
  @author: Naxanria
*/
@SuppressWarnings("ConstantConditions")
public class TrashCanTileEntity extends TileEntity implements INamedContainerProvider
{
  private LazyOptional<ItemStackHandler> inventory = LazyOptional.of(this::createInventory);
  
  public TrashCanTileEntity()
  {
    super(ModTiles.TRASHCAN);
  }
  
  private ItemStackHandler createInventory()
  {
    return new ItemStackHandler()
    {
      @Override
      protected void onContentsChanged(int slot)
      {
        stacks.set(0, ItemStack.EMPTY);
      }
    };
  }
  
  @Nonnull
  @Override
  public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side)
  {
    if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
    {
      return inventory.cast();
    }
    
    return super.getCapability(cap, side);
  }
  
  @Override
  public ITextComponent getDisplayName()
  {
    return new StringTextComponent(getType().getRegistryName().getPath());
  }
  
  @Nullable
  @Override
  public Container createMenu(int id, PlayerInventory inventory, PlayerEntity player)
  {
    return new TrashCanContainer(id, world, pos, inventory);
  }
}
