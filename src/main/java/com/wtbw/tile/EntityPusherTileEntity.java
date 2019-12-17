package com.wtbw.tile;

import com.wtbw.config.CommonConfig;
import com.wtbw.util.NBTHelper;
import com.wtbw.util.Utilities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;

import java.util.List;

/*
  @author: Naxanria
*/
@SuppressWarnings("ConstantConditions")
public class EntityPusherTileEntity extends TileEntity implements ITickableTileEntity
{
  public enum PushMode
  {
    PUSH(-1),
    PULL(1);
    
    public final float multiplier;
    
    PushMode(float mul)
    {
      this.multiplier = mul;
    }
  }
  
  static CommonConfig config = CommonConfig.get();
  
  public final PushMode pushMode;
  private AxisAlignedBB bbox;
  private Vec3d vecPos;
  
  private int tickCount = 0;
  
  public EntityPusherTileEntity(PushMode pushMode)
  {
    super(pushMode == PushMode.PUSH ? ModTiles.PUSHER : ModTiles.PULLER);
    
    this.pushMode = pushMode;
  }
  
  @Override
  public void tick()
  {
    if (!world.isRemote)
    {
      tickCount++;
      
      if (bbox == null)
      {
        bbox = Utilities.getHorizontalBoundingBox(pos, config.pusherRange.get(), 2);
      }
      
      if (vecPos == null)
      {
        vecPos = Utilities.getVec3d(pos).add(.5, .5, .5);
      }
      
      if (tickCount % config.pusherTickRate.get() == 0)
      {
        List<Entity> entitiesWithinAABB = world.getEntitiesWithinAABB(Entity.class, bbox);
        
        for (Entity entity : entitiesWithinAABB)
        {
          if (entity instanceof PlayerEntity)
          {
            if (entity.isCrouching() || ((PlayerEntity) entity).isCreative())
            {
              continue;
            }
          }
          
          entity.setMotion(getMoveVector(entity));
        }
      }
    }
  }
  
  
  private Vec3d getMoveVector(Entity entity)
  {
    double strength = config.pusherStrength.get() * pushMode.multiplier;
    return vecPos.subtract(entity.getPositionVec()).normalize().mul(strength, strength, strength);
  }
  
  @Override
  public void read(CompoundNBT compound)
  {
    tickCount = NBTHelper.getInt(compound, "ticks");
    super.read(compound);
  }
  
  @Override
  public CompoundNBT write(CompoundNBT compound)
  {
    compound.putInt("ticks", tickCount);
    return super.write(compound);
  }
}
