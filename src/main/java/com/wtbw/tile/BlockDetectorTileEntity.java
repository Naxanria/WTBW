package com.wtbw.tile;

import com.wtbw.Flags;
import com.wtbw.WTBW;
import com.wtbw.block.SixWayTileBlock;
import com.wtbw.gui.container.BlockDetectorContainer;
import com.wtbw.gui.util.ClickType;
import com.wtbw.network.Networking;
import com.wtbw.network.UpdateDetectorPacket;
import com.wtbw.tile.util.IButtonHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.network.NetworkDirection;

import javax.annotation.Nullable;

/*
  @author: Naxanria
*/
@SuppressWarnings("ConstantConditions")
public class BlockDetectorTileEntity extends TileEntity implements ITickableTileEntity, INamedContainerProvider, IButtonHandler
{
  public static final int BUTTON_CURRENT = 0;
  public static final int BUTTON_MATCH = 1;
  
  private Direction facing;
  private int power;
  private boolean exactMatch = false;
  private BlockState target = Blocks.AIR.getDefaultState();
  
  public BlockDetectorTileEntity()
  {
    super(ModTiles.BLOCK_DETECTOR);
  }
  
  @Override
  public void tick()
  {
    if (matches())
    {
      setPower(15);
    }
    else
    {
      setPower(0);
    }
  }
  
  @Override
  public void read(CompoundNBT compound)
  {
    target = NBTUtil.readBlockState(compound.getCompound("target"));
    power = compound.getInt("power");
    exactMatch = compound.getBoolean("exact");
    
    super.read(compound);
  }
  
  @Override
  public CompoundNBT write(CompoundNBT compound)
  {
    compound.put("target", NBTUtil.writeBlockState(target));
    compound.putInt("power", power);
    compound.putBoolean("exact", exactMatch);
    
    return super.write(compound);
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
    return new BlockDetectorContainer(id, world, pos, inventory);
  }
  
  @Override
  public boolean handleButton(int buttonID, ClickType clickType)
  {
    switch (buttonID)
    {
      case BUTTON_CURRENT:
        setToCurrent();
        markDirty();
        return true;
        
      case BUTTON_MATCH:
        exactMatch = !exactMatch;
        markDirty();
        return true;
    }
    
    return false;
  }
  
  private void setToCurrent()
  {
    Direction facing = getFacing();
    BlockState previous = target;
    target = world.getBlockState(pos.offset(facing));
    if (Flags.debug())
    {
      WTBW.LOGGER.info("Set target to: {} from {}", target, previous);
    }
    
    if (target != previous)
    {
      Networking.sendAround(world, pos, 48, new UpdateDetectorPacket(pos, target));
    }
  }
  
  public void requestUpdate(ServerPlayerEntity player)
  {
    Networking.INSTANCE.sendTo(new UpdateDetectorPacket(pos, target), player.connection.netManager, NetworkDirection.PLAY_TO_CLIENT);
  }
  
  private boolean matches()
  {
    Direction facing = getFacing();
    BlockState found = world.getBlockState(pos.offset(facing));
    
    if (!exactMatch)
    {
      return target.getBlock() == found.getBlock();
    }
    else
    {
      return target == found;
    }
  }
  
  public void setPower(int power)
  {
    if (this.power != power)
    {
      this.power = power;
      BlockState state = getBlockState();
      world.neighborChanged(pos, state.getBlock(), pos);
      world.notifyNeighborsOfStateChange(pos, state.getBlock());
      BlockPos offset = pos.offset(getFacing().getOpposite());
      state = world.getBlockState(offset);
      world.neighborChanged(offset, state.getBlock(), offset);
      world.notifyNeighborsOfStateChange(offset, state.getBlock());
    }
  }
  
  public int getPower()
  {
    return power;
  }
  
  public boolean isExactMatch()
  {
    return exactMatch;
  }
  
  public BlockDetectorTileEntity setExactMatch(boolean exactMatch)
  {
    this.exactMatch = exactMatch;
    return this;
  }
  
  public BlockState getTarget()
  {
    return target;
  }
  
  public BlockDetectorTileEntity setTarget(BlockState target)
  {
    this.target = target;
    return this;
  }
  
  @Override
  public void updateContainingBlockInfo()
  {
    super.updateContainingBlockInfo();
    facing = null;
  }
  
  private Direction getFacing()
  {
    if (facing == null)
    {
      facing = getBlockState().get(SixWayTileBlock.FACING);
    }
    
    return facing;
  }
}
