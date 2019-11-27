package com.wtbw.tile.redstone;

import com.wtbw.tile.ModTiles;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

import javax.annotation.Nullable;

/*
  @author: Naxanria
*/
@SuppressWarnings("ConstantConditions")
public class RedstoneTimerTileEntity extends TileEntity implements ITickableTileEntity, INamedContainerProvider
{
  public RedstoneTimerTileEntity()
  {
    super(ModTiles.REDSTONE_TIMER);
  }
  
  @Override
  public void tick()
  {
  
  }
  
  @Override
  public ITextComponent getDisplayName()
  {
    return new StringTextComponent(getType().getRegistryName().getPath());
  }
  
  @Nullable
  @Override
  public Container createMenu(int windowID, PlayerInventory inventory, PlayerEntity player)
  {
    return null;
  }
  
}
