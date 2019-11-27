package com.wtbw.tile.furnace;

import com.wtbw.WTBW;
import com.wtbw.block.BaseFurnaceBlock;
import com.wtbw.gui.container.IronFurnaceContainer;
import com.wtbw.tile.ModTiles;
import com.wtbw.tile.tools.IContentHolder;
import com.wtbw.util.NBTHelper;
import com.wtbw.util.Utilities;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.AbstractCookingRecipe;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
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
import java.util.concurrent.atomic.AtomicBoolean;

/*
  @author: Naxanria
*/
@SuppressWarnings("NullableProblems")
public class BaseFurnaceTileEntity extends TileEntity implements ITickableTileEntity, INamedContainerProvider, IContentHolder
{
  protected int burnTime;
  protected int burnTimeTotal;
  protected int cookTime;
  protected int cookTimeTotal;
  protected float speed = 1;
  public final IRecipeType<? extends AbstractCookingRecipe> recipeType;
  
  protected LazyOptional<ItemStackHandler> inputHandler;
  protected LazyOptional<ItemStackHandler> fuelHandler;
  protected LazyOptional<ItemStackHandler> outputHandler;
  
  protected IInventory inventoryInput;
  
  public BaseFurnaceTileEntity(float speed, IRecipeType<? extends AbstractCookingRecipe> recipeType)
  {
    super(ModTiles.IRON_FURNACE);
    this.speed = speed;
    this.recipeType = recipeType;

    inputHandler = LazyOptional.of(this::createInputHandler);
    fuelHandler = LazyOptional.of(this::createFuelHandler);
    outputHandler = LazyOptional.of(this::createOutputHandler);
    
    cookTimeTotal = (int) (200 / speed);
    
    inventoryInput = new IInventory()
    {
      ItemStack stack = ItemStack.EMPTY;
      
      @Override
      public int getSizeInventory()
      {
        return 1;
      }
  
      @Override
      public boolean isEmpty()
      {
        return false;
      }
  
      @Override
      public ItemStack getStackInSlot(int index)
      {
        inputHandler.ifPresent
        (
          handler ->
          {
            stack = handler.getStackInSlot(index);
          }
        );
        
        return stack;
      }
  
      @Override
      public ItemStack decrStackSize(int index, int count)
      {
        return null;
      }
  
      @Override
      public ItemStack removeStackFromSlot(int index)
      {
        return null;
      }
  
      @Override
      public void setInventorySlotContents(int index, ItemStack stack)
      { }
  
      @Override
      public void markDirty()
      { }
  
      @Override
      public boolean isUsableByPlayer(PlayerEntity player)
      {
        return false;
      }
  
      @Override
      public void clear()
      { }
    };
  }
  
  @Override
  public void tick()
  {
    boolean burning = isBurning();
    boolean dirty = false;
    
    if (burning)
    {
      burnTime--;
      dirty = true;
    }
    
    if (!world.isRemote)
    {
      IRecipe<?> recipe = getRecipe();
      if (recipe == null)
      {
        if (cookTime > 0)
        {
          dirty = true;
        }
        
        cookTime = 0;
      }
      else
      {
        ItemStack target = recipe.getRecipeOutput();
        if (canOutput(target))
        {
          if (!burning || !isBurning())
          {
            startBurn();
            if (!isBurning())
            {
              dirty = true;
              cookTime = 0;
            }
          }
          else
          {
            cook(target);
            dirty = true;
          }
        }
      }
  
      BlockState state = world.getBlockState(pos);
      boolean lit = state.get(BaseFurnaceBlock.LIT);
      boolean currentBurn = isBurning();
      if (lit != currentBurn)
      {
        world.setBlockState(pos, state.with(BaseFurnaceBlock.LIT, currentBurn), 3);
        WTBW.LOGGER.info("Changed lit {} to {} burning: ", lit, currentBurn, burning);
        dirty = true;
      }
  
      if (dirty)
      {
        markDirty();
      }
    }
  }
  
  private void cook(ItemStack target)
  {
    cookTime++;
    if (cookTime >= cookTimeTotal)
    {
      cookTime = 0;
      inputHandler.ifPresent
      (
        handler ->
        {
          ItemStack input = handler.getStackInSlot(0);
          input.shrink(1);
          handler.setStackInSlot(0, input);
        }
      );
      
      outputHandler.ifPresent
      (
        handler ->
        {
          ItemStack output = handler.getStackInSlot(0);
          if (output == ItemStack.EMPTY)
          {
            handler.setStackInSlot(0, target.copy());
          }
          else
          {
            output.grow(target.getCount());
            handler.setStackInSlot(0, output);
          }
        }
      );
    }
  }
  
  private void startBurn()
  {
    fuelHandler.ifPresent
    (
      handler ->
      {
        ItemStack fuel = handler.getStackInSlot(0);
        int fuelTime = Utilities.getBurnTime(fuel);
        if (fuelTime > 0)
        {
          burnTimeTotal = fuelTime;
          burnTime = fuelTime;
          
          if (fuel.getItem() == Items.LAVA_BUCKET)
          {
            handler.setStackInSlot(0, new ItemStack(Items.BUCKET));
          }
          else
          {
            fuel.shrink(1);
            handler.setStackInSlot(0, fuel);
          }
        }
      }
    );
    if (!fuelHandler.isPresent())
    {
      WTBW.LOGGER.error("No fuel handler!?");
    }
  }
  
  private boolean canOutput(ItemStack target)
  {
    AtomicBoolean canOutput = new AtomicBoolean(false);
    outputHandler.ifPresent
    (
      handler ->
      {
        ItemStack current = handler.getStackInSlot(0);
        if (current.isEmpty())
        {
          canOutput.set(true);
        }
        else
        {
          int maxSize = current.getMaxStackSize();
          int size = current.getCount() + target.getCount();
          canOutput.set(current.getItem() == target.getItem() && (size <= maxSize));
        }
      }
    );
    
    return canOutput.get();
  }
  
  public boolean isBurning()
  {
    return burnTime > 0;
  }
  
  private IRecipe<?> getRecipe()
  {
    return this.world.getRecipeManager().getRecipe(recipeType, inventoryInput, this.world).orElse(null);
  }
  
  @Override
  public ITextComponent getDisplayName()
  {
    return new StringTextComponent(getType().getRegistryName().getPath());
  }
  
  @Nullable
  @Override
  public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity player)
  {
    return new IronFurnaceContainer(id, world, pos, playerInventory);
  }
  
  protected ItemStackHandler createInputHandler()
  {
    return new ItemStackHandler()
    {
      @Nonnull
      @Override
      public ItemStack extractItem(int slot, int amount, boolean simulate)
      {
        return ItemStack.EMPTY;
      }
  
      @Override
      public boolean isItemValid(int slot, @Nonnull ItemStack stack)
      {
        return true;
      }
  
      @Override
      protected void onContentsChanged(int slot)
      {
        markDirty();
        super.onContentsChanged(slot);
      }
    };
  }
  
  protected ItemStackHandler createFuelHandler()
  {
    return new ItemStackHandler()
    {
      @Nonnull
      @Override
      public ItemStack extractItem(int slot, int amount, boolean simulate)
      {
        return ItemStack.EMPTY;
      }
  
      @Nonnull
      @Override
      public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate)
      {
        return isItemValid(slot, stack) ? super.insertItem(slot, stack, simulate) : stack;
      }
  
      @Override
      public boolean isItemValid(int slot, @Nonnull ItemStack stack)
      {
        return Utilities.getBurnTime(stack) > 0;
      }
  
      @Override
      protected void onContentsChanged(int slot)
      {
        markDirty();
        super.onContentsChanged(slot);
      }
    };
  }
  
  protected ItemStackHandler createOutputHandler()
  {
    return new ItemStackHandler()
    {
      @Nonnull
      @Override
      public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate)
      {
        return stack;
      }
  
      @Override
      public boolean isItemValid(int slot, @Nonnull ItemStack stack)
      {
        return false;
      }
    };
  }
  
  @Nonnull
  @Override
  public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side)
  {
    if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
    {
      if (side == Direction.UP)
      {
        return inputHandler.cast();
      }
      
      if (side == Direction.DOWN)
      {
        return outputHandler.cast();
      }
      
      return fuelHandler.cast();
    }
    
    return super.getCapability(cap, side);
  }
  
  
//  burnTime;
//  burnTimeTotal
//  cookTime;
//  cookTimeTotal
  @Override
  public void read(CompoundNBT compound)
  {
    burnTime = NBTHelper.getInt(compound, "burnTime");
    burnTimeTotal = NBTHelper.getInt(compound, "burnTimeTotal");
    cookTime = NBTHelper.getInt(compound, "cookTime");
    cookTimeTotal = NBTHelper.getInt(compound, "cookTimeTotal");
    speed = NBTHelper.getFloat(compound, "speed", 1f);
    
    inputHandler.ifPresent(handler -> handler.deserializeNBT(NBTHelper.getCompound(compound, "input")));
    fuelHandler.ifPresent(handler -> handler.deserializeNBT(NBTHelper.getCompound(compound, "fuel")));
    outputHandler.ifPresent(handler -> handler.deserializeNBT(NBTHelper.getCompound(compound, "output")));
    
    super.read(compound);
  }
  
  @Override
  public CompoundNBT write(CompoundNBT compound)
  {
    compound.putInt("burnTime", burnTime);
    compound.putInt("burnTimeTotal", burnTimeTotal);
    compound.putInt("cookTime", cookTime);
    compound.putInt("cookTimeTotal", cookTimeTotal);
    
    inputHandler.ifPresent(handler -> compound.put("input" ,handler.serializeNBT()));
    fuelHandler.ifPresent(handler -> compound.put("fuel" ,handler.serializeNBT()));
    outputHandler.ifPresent(handler -> compound.put("output" ,handler.serializeNBT()));
    
    return super.write(compound);
  }
  
  public LazyOptional<ItemStackHandler> getInputHandler()
  {
    return inputHandler;
  }
  
  public LazyOptional<ItemStackHandler> getFuelHandler()
  {
    return fuelHandler;
  }
  
  public LazyOptional<ItemStackHandler> getOutputHandler()
  {
    return outputHandler;
  }
  
  public int getBurnTime()
  {
    return burnTime;
  }
  
  public BaseFurnaceTileEntity setBurnTime(int burnTime)
  {
    this.burnTime = burnTime;
    return this;
  }
  
  public int getBurnTimeTotal()
  {
    return burnTimeTotal;
  }
  
  public BaseFurnaceTileEntity setBurnTimeTotal(int burnTimeTotal)
  {
    this.burnTimeTotal = burnTimeTotal;
    return this;
  }
  
  public int getCookTime()
  {
    return cookTime;
  }
  
  public BaseFurnaceTileEntity setCookTime(int cookTime)
  {
    this.cookTime = cookTime;
    return this;
  }
  
  public int getCookTimeTotal()
  {
    return cookTimeTotal;
  }
  
  public BaseFurnaceTileEntity setCookTimeTotal(int cookTimeTotal)
  {
    this.cookTimeTotal = cookTimeTotal;
    return this;
  }
  
  @Override
  public void dropContents()
  {
    inputHandler.ifPresent(handler -> InventoryHelper.dropInventoryItems(world, pos, new Inventory(handler.getStackInSlot(0))));
    outputHandler.ifPresent(handler -> InventoryHelper.dropInventoryItems(world, pos, new Inventory(handler.getStackInSlot(0))));
    fuelHandler.ifPresent(handler -> InventoryHelper.dropInventoryItems(world, pos, new Inventory(handler.getStackInSlot(0))));
  }
}
