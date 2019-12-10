package com.wtbw.tile;

import com.wtbw.block.BlockPlacerBlock;
import com.wtbw.gui.container.BlockPlacerContainer;
import com.wtbw.tile.util.IContentHolder;
import com.wtbw.tile.util.IRedstoneControlled;
import com.wtbw.tile.util.RedstoneControl;
import com.wtbw.tile.util.RedstoneMode;
import com.wtbw.util.PlayEvent;
import com.wtbw.util.RandomUtil;
import com.wtbw.util.Utilities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/*
  @author: Naxanria
*/
@SuppressWarnings("ConstantConditions")
public class BlockPlacerTileEntity extends TileEntity implements ITickableTileEntity, IContentHolder, IRedstoneControlled, INamedContainerProvider
{
  private LazyOptional<ItemStackHandler> inventory = LazyOptional.of(this::createInventory);
  private RedstoneControl control;
  
  public BlockPlacerTileEntity()
  {
    super(ModTiles.BLOCK_PLACER);
    
    control = new RedstoneControl(this, RedstoneMode.ON);
  }
  
  @Override
  public void tick()
  {
    if (!world.isRemote)
    {
      if (control.update())
      {
        if (place())
        {
          control.resetCooldown();
        }
      }
    }
  }
  
  private boolean place()
  {
    boolean overrideFluids = true;
    
    Direction facing = world.getBlockState(pos).get(BlockPlacerBlock.FACING);
    BlockPos placePos = pos.offset(facing);
    BlockState placeState = world.getBlockState(placePos);

    boolean canPlace = placeState.isAir(world, placePos);
    boolean isFluid = placeState.getFluidState() != Fluids.EMPTY.getDefaultState();
    if (!isFluid && !canPlace)
    {
      return false;
    }
    if (!canPlace && isFluid)
    {
      canPlace = overrideFluids;
    }
    if (!canPlace)
    {
      canPlace = !placeState.getFluidState().isSource();
    }
    
    if (canPlace)
    {
      AtomicBoolean success = new AtomicBoolean(false);
      
      inventory.ifPresent(handler ->
      {
        List<Integer> availableSlots = new ArrayList<>();
  
        for (int i = 0; i < handler.getSlots(); i++)
        {
          ItemStack stackInSlot = handler.getStackInSlot(i);
          if (stackInSlot.isEmpty() || !(stackInSlot.getItem() instanceof BlockItem))
          {
            continue;
          }
          availableSlots.add(i);
        }
        
        if (availableSlots.size() == 0)
        {
          success.set(false);
          return;
        }
        
        int slotIndex = RandomUtil.get(world.rand, availableSlots);
        BlockItem blockItem = ((BlockItem) handler.getStackInSlot(slotIndex).getItem());
        PlayEvent.redstoneParticle(world, Utilities.getVec3d(placePos).add(.5, .5, .5), new Vec3d(0, 0, 0), 0xff333333);
        Block placedBlock = blockItem.getBlock();
        BlockState defaultState = placedBlock.getDefaultState();
        BlockState stateForPlacement = placedBlock.getStateForPlacement(defaultState, facing, defaultState, world, placePos, placePos.offset(facing), Hand.MAIN_HAND);
        world.setBlockState(placePos, stateForPlacement, 3);
        success.set(true);
        handler.extractItem(slotIndex, 1, false);

        SoundType soundType = stateForPlacement.getSoundType();
        world.playSound(pos.getX(), pos.getY(), pos.getZ(), soundType.getPlaceSound(), SoundCategory.BLOCKS, (soundType.getVolume() + 1) / 2f, soundType.getPitch() * 0.8f, false);
      });
      
      return success.get();
    }
    
    return false;
  }
  
  @Override
  public void dropContents()
  {
    inventory.ifPresent(handler -> Utilities.dropItems(world, handler, pos));
  }
  
  private ItemStackHandler createInventory()
  {
    return new ItemStackHandler(9);
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
  public RedstoneControl getControl()
  {
    return control;
  }
  
  public LazyOptional<ItemStackHandler> getInventory()
  {
    return inventory;
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
    return new BlockPlacerContainer(id, world, pos, inventory);
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
}
