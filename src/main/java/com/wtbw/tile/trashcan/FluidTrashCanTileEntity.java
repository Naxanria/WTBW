package com.wtbw.tile.trashcan;

import com.wtbw.tile.ModTiles;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/*
  @author: Naxanria
*/
@SuppressWarnings("ConstantConditions")
public class FluidTrashCanTileEntity extends TileEntity
{
  private LazyOptional<IFluidHandler> tank = LazyOptional.of(this::createTank);
  
  public FluidTrashCanTileEntity()
  {
    super(ModTiles.FLUID_TRASHCAN);
  }
  
  @Nonnull
  @Override
  public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side)
  {
    if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
    {
      return tank.cast();
    }
    
    return super.getCapability(cap, side);
  }
  
  private IFluidHandler createTank()
  {
    return new IFluidHandler()
    {
      @Override
      public int getTanks()
      {
        return 1;
      }
  
      @Nonnull
      @Override
      public FluidStack getFluidInTank(int i)
      {
        return FluidStack.EMPTY;
      }
  
      @Override
      public int getTankCapacity(int i)
      {
        return Integer.MAX_VALUE;
      }
  
      @Override
      public boolean isFluidValid(int i, @Nonnull FluidStack fluidStack)
      {
        return true;
      }
  
      @Override
      public int fill(FluidStack fluidStack, FluidAction fluidAction)
      {
        return fluidStack.getAmount();
      }
  
      @Nonnull
      @Override
      public FluidStack drain(FluidStack fluidStack, FluidAction fluidAction)
      {
        return FluidStack.EMPTY;
      }
  
      @Nonnull
      @Override
      public FluidStack drain(int i, FluidAction fluidAction)
      {
        return FluidStack.EMPTY;
      }
    };
  }
}
