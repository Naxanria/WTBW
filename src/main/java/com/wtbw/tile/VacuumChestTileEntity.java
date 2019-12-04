package com.wtbw.tile;

import com.wtbw.Flags;
import com.wtbw.compat.item_filters.ItemFiltersWrapper;
import com.wtbw.gui.container.VacuumChestContainer;
import com.wtbw.tile.util.IContentHolder;
import com.wtbw.util.NBTHelper;
import com.wtbw.util.StackUtil;
import com.wtbw.util.Utilities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

/*
  @author: Naxanria
*/
@SuppressWarnings("ConstantConditions")
public class VacuumChestTileEntity extends TileEntity implements ITickableTileEntity, IContentHolder, INamedContainerProvider
{
  private AxisAlignedBB bound;
  private int radius = 6;
  private int frequency = 10;
  private int tick;
  
//  private ItemStack filter = ItemStack.EMPTY;
  private ItemStackHandler filter = new ItemStackHandler();
  
  private LazyOptional<ItemStackHandler> inventory = LazyOptional.of(this::createInventory);
  
  public VacuumChestTileEntity()
  {
    super(ModTiles.VACUUM_CHEST);
  }
  
  private ItemStackHandler createInventory()
  {
    return new ItemStackHandler(6);
  }
  
  @Override
  public void tick()
  {
    if (!world.isRemote)
    {
      tick++;
      
      if (bound == null)
      {
        bound = Utilities.getBoundingBox(pos, radius);
      }
      
      if (tick % frequency == 0)
      {
        List<Entity> entities = world.getEntitiesWithinAABB(EntityType.ITEM, bound, (e) -> canInsert(((ItemEntity) e).getItem()));
        for (Entity e : entities)
        {
          ItemEntity entity = (ItemEntity) e;
          if (!filter(entity.getItem()))
          {
            continue;
          }
          
          inventory.ifPresent(
            handler ->
            {
              ItemStack stack = entity.getItem();
              for (int i = 0; i < handler.getSlots(); i++)
              {
                stack = handler.insertItem(i, stack, false);
                if (stack == ItemStack.EMPTY)
                {
                  break;
                }
              }
              if (stack == ItemStack.EMPTY)
              {
                entity.remove();
                world.addParticle
                (
                  ParticleTypes.SMOKE,
                  entity.posX, entity.posY, entity.posZ,
                  world.rand.nextDouble() * 0.4 - 0.2,
                  world.rand.nextDouble() * 0.3 + 0.1,
                  world.rand.nextDouble() * 0.4 - 0.2
                );
              }
              else
              {
                entity.setItem(stack);
              }
            }
          );
        }
      }
    }
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
  
  private boolean canInsert(ItemStack item)
  {
    return StackUtil.canInsert(inventory, item, false);
  }
  
  @Override
  public void read(CompoundNBT compound)
  {
    tick = NBTHelper.getInt(compound, "tick");
    
    if (compound.contains("inventory"))
    {
      inventory.ifPresent(handler -> handler.deserializeNBT(compound.getCompound("inventory")));
    }
    
    if (compound.contains("filter"))
    {
      filter.deserializeNBT(compound.getCompound("filter"));
    }
    
    super.read(compound);
  }
  
  @Override
  public CompoundNBT write(CompoundNBT compound)
  {
    compound.putInt("tick", tick);
    
    inventory.ifPresent(handler -> compound.put("inventory", handler.serializeNBT()));
    
    compound.put("filter", filter.serializeNBT());
    
    return super.write(compound);
  }
  
  @Override
  public void dropContents()
  {
    inventory.ifPresent(handler ->
    {
      for (int i = 0; i < handler.getSlots(); i++)
      {
        InventoryHelper.spawnItemStack(world, pos.getX() + .5, pos.getY() + .5, pos.getZ() + .5, handler.getStackInSlot(i));
      }
    });
    
    if (!filter.getStackInSlot(0).isEmpty())
    {
      InventoryHelper.spawnItemStack(world, pos.getX() + .5, pos.getY() + .5, pos.getZ() + .5, filter.getStackInSlot(0));
    }
  }
  
  public LazyOptional<ItemStackHandler> getInventory()
  {
    return inventory;
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
    return new VacuumChestContainer(id, world, pos, inventory);
  }
  
  private boolean filter(ItemStack stack)
  {
    ItemStack filter = this.filter.getStackInSlot(0);
    
    if (Flags.isItemFiltersLoaded())
    {
      return ItemFiltersWrapper.filter(filter, stack);
    }
    
    return filter.isEmpty() || filter.getItem() == stack.getItem() && Objects.equals(stack.getTag(), filter.getTag());
  }
  
  public ItemStackHandler getFilter()
  {
    return filter;
  }
}
