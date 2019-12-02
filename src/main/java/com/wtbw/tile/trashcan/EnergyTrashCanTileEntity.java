package com.wtbw.tile.trashcan;

import com.wtbw.tile.ModTiles;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;

/*
  @author: Naxanria
*/
@SuppressWarnings("ConstantConditions")
public class EnergyTrashCanTileEntity extends TileEntity {
    private LazyOptional<IEnergyStorage> storage = LazyOptional.of(this::createStorage);

    public EnergyTrashCanTileEntity() {
        super(ModTiles.ENERGY_TRASHCAN);
    }

    private IEnergyStorage createStorage() {
        return new IEnergyStorage() {
            @Override
            public int receiveEnergy(int maxReceive, boolean simulate) {
                return maxReceive;
            }

            @Override
            public int extractEnergy(int maxExtract, boolean simulate) {
                return 0;
            }

            @Override
            public int getEnergyStored() {
                return 0;
            }

            @Override
            public int getMaxEnergyStored() {
                return Integer.MAX_VALUE;
            }

            @Override
            public boolean canExtract() {
                return false;
            }

            @Override
            public boolean canReceive() {
                return true;
            }
        };
    }
}
