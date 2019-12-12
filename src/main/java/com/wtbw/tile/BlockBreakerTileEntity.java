package com.wtbw.tile;

import com.wtbw.block.BlockBreakerBlock;
import com.wtbw.gui.container.BlockBreakerContainer;
import com.wtbw.gui.util.ClickType;
import com.wtbw.tile.util.IContentHolder;
import com.wtbw.tile.util.IRedstoneControlled;
import com.wtbw.tile.util.RedstoneControl;
import com.wtbw.tile.util.RedstoneMode;
import com.wtbw.util.StackUtil;
import com.wtbw.util.Utilities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/*
  @author: Naxanria
*/
@SuppressWarnings("ConstantConditions")
public class BlockBreakerTileEntity extends TileEntity implements ITickableTileEntity, INamedContainerProvider, IRedstoneControlled, IContentHolder
{
  private LazyOptional<ItemStackHandler> inventory = LazyOptional.of(this::createInventory);
  
  private RedstoneControl control;
  
  public BlockBreakerTileEntity()
  {
    super(ModTiles.BLOCK_BREAKER);
    
    control = new RedstoneControl(this, RedstoneMode.ON);
  }
  
  @Override
  public void tick()
  {
    if (!world.isRemote)
    {
      if (control.update())
      {
        if (breakBlock())
        {
          control.resetCooldown();
        }
      }
    }
  }
  
  private ItemStackHandler createInventory()
  {
    return new ItemStackHandler(9);
  }
  
  private boolean breakBlock()
  {
    Direction facing = world.getBlockState(pos).get(BlockBreakerBlock.FACING);
    BlockPos breakPos = pos.offset(facing);
    BlockState breakState = world.getBlockState(breakPos);
  
    // todo: config for breaking tiles, blacklist
    boolean breakTiles = false;
    if (!breakState.isAir(world, breakPos) && breakState.getFluidState() == Fluids.EMPTY.getDefaultState())
    {
      Block block = breakState.getBlock();
      if (block == Blocks.BEDROCK)
      {
        return false;
      }
      
      if (breakTiles || world.getTileEntity(breakPos) == null)
      {
        List<ItemStack> drops = Block.getDrops(breakState, (ServerWorld) world, breakPos, breakState.hasTileEntity() ? world.getTileEntity(breakPos) : null);
        for (ItemStack drop : drops)
        {
          if (!StackUtil.canInsert(inventory, drop, true))
          {
            return false;
          }
        }
        world.destroyBlock(breakPos, false);
        inventory.ifPresent(handler ->
        {
          for (ItemStack drop : drops)
          {
            for (int i = 0; i < handler.getSlots(); i++)
            {
              drop = handler.insertItem(i, drop, false);
              if (drop.isEmpty())
              {
                break;
              }
            }
          }
        });
        
        return true;
      }
    }
    
    return false;
  }

  @Override
  public RedstoneControl getControl()
  {
    return control;
  }
  
  @Override
  public ITextComponent getDisplayName()
  {
    return new TranslationTextComponent(getType().getRegistryName().toString());
  }
  
  @Nullable
  @Override
  public Container createMenu(int id, PlayerInventory inventory, PlayerEntity player)
  {
    return new BlockBreakerContainer(id, world, pos, inventory);
  }
  
  @Override
  public void read(CompoundNBT compound)
  {
    control.deserialize(compound.getCompound("control"));
    inventory.ifPresent(handler -> handler.deserializeNBT(compound.getCompound("inventory")));
    
    super.read(compound);
  }
  
  @Override
  public CompoundNBT write(CompoundNBT compound)
  {
    compound.put("control", control.serialize());
    inventory.ifPresent(handler -> compound.put("inventory", handler.serializeNBT()));
    
    return super.write(compound);
  }
  
  @Override
  public boolean handleButton(int buttonID, ClickType clickType)
  {
    if (control.handleButton(buttonID, clickType))
    {
      markDirty();
      return true;
    }
    
    return false;
  }
  
  public LazyOptional<ItemStackHandler> getInventory()
  {
    return inventory;
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
  public void dropContents()
  {
    inventory.ifPresent(handler -> Utilities.dropItems(world, handler, pos));
  }
}
