package com.wtbw.tile.trashcan;

import com.wtbw.tile.ModTiles;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/*
  @author: Naxanria
*/
@SuppressWarnings("ConstantConditions")
public class EnergyTrashCanTileEntity extends TileEntity
{
  private LazyOptional<IEnergyStorage> storage = LazyOptional.of(this::createStorage);

  public EnergyTrashCanTileEntity()
  {
    super(ModTiles.ENERGY_TRASHCAN);
  }

  private IEnergyStorage createStorage()
  {
    return new IEnergyStorage()
    {
      @Override
      public int receiveEnergy(int maxReceive, boolean simulate)
      {
        return maxReceive;
      }

      @Override
      public int extractEnergy(int maxExtract, boolean simulate)
      {
        return 0;
      }

      @Override
      public int getEnergyStored()
      {
        return 0;
      }

      @Override
      public int getMaxEnergyStored()
      {
        return Integer.MAX_VALUE;
      }

      @Override
      public boolean canExtract()
      {
        return false;
      }

      @Override
      public boolean canReceive()
      {
        return true;
      }
    };
  }
  
  @Nonnull
  @Override
  public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side)
  {
    if (cap == CapabilityEnergy.ENERGY)
    {
      return storage.cast();
    }
    
    return super.getCapability(cap, side);
  }
}
