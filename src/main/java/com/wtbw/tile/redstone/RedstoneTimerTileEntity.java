package com.wtbw.tile.redstone;

import com.wtbw.WTBW;
import com.wtbw.tile.ModTiles;
import com.wtbw.util.Cooldown;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;

/*
  @author: Naxanria
*/
@SuppressWarnings("ConstantConditions")
public class RedstoneTimerTileEntity extends TileEntity implements ITickableTileEntity
{
  private Cooldown cooldown;
  private Cooldown windDown;
  private int sendPower = 0;
  
  public RedstoneTimerTileEntity()
  {
    super(ModTiles.REDSTONE_TIMER);
    
    cooldown = new Cooldown(10).start();
    windDown = new Cooldown(4).start();
  }
  
  @Override
  public void tick()
  {
    if (!world.isRemote)
    {
//      int power = world.getRedstonePowerFromNeighbors(pos);
      boolean powered = world.isBlockPowered(pos);
      
      if (powered)
      {
        cooldown.setCount(0);
        updatePower(0);
        return;
      }
      
//      if (power > 0)
//      {
//        sendPower = 0;
//        cooldown.stop();
//      }
//      else
//      {
        cooldown.start();
        cooldown.update();
        if (cooldown.isFinished())
        {
          updatePower(15);
          windDown.update();
          if (windDown.isFinished())
          {
            windDown.restart();
            cooldown.restart();
          }
        }
        else if (cooldown.getCount() > 1)
        {
          updatePower(0);
        }
//      }
    }
  }
  
  private void updatePower(int newPower)
  {
    if (newPower != sendPower)
    {
      sendPower = newPower;
      world.notifyNeighbors(pos, world.getBlockState(pos).getBlock());
    }
    else
    {
      sendPower = newPower;
    }
    
    
  }
  
  public int getPower()
  {
    return sendPower;
  }
}
