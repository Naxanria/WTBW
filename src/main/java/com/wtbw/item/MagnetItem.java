package com.wtbw.item;

import com.wtbw.util.StackUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

/*
  @author: Naxanria
*/
public class MagnetItem extends Item
{
  private AxisAlignedBB getBoundingBox(BlockPos center, double radius)
  {
    return new AxisAlignedBB(center.add(-radius, -radius, -radius), center.add(radius, radius, radius));
  }
  
  public MagnetItem(Properties properties)
  {
    super(properties);
  }
  
  @Override
  public void inventoryTick(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected)
  {
    // todo: make config option
    if (!world.isRemote)
    {
      if (!(entity instanceof PlayerEntity))
      {
        // we don't let mobs magnetize stuff... for now
        return;
      }
      
      PlayerEntity player = (PlayerEntity) entity;
      MinecraftServer server = world.getServer();
      if (server != null && server.getTickCounter() % 10 == 0)
      {
        List<Entity> entities = world.getEntitiesWithinAABB(Entity.class, getBoundingBox(entity.getPosition(), 10));
    
        // fixme: use config
        boolean checkCanPickup = true;
        
        for (Entity e : entities)
        {
          if (e instanceof ItemEntity)
          {
            ItemEntity itemEntity = (ItemEntity) e;
            if (!itemEntity.cannotPickup())
            {
              if (checkCanPickup)
              {
                if (!StackUtil.canInsert(player, itemEntity.getItem(), false))
                {
                  continue;
                }
              }
              
              float speed = 3;
              Vec3d moveVec = entity.getPositionVec().subtract(itemEntity.getPositionVec()).add(0, 1, 0).normalize().mul(speed, speed, speed);
              if (itemEntity.collidedHorizontally)
              {
                moveVec.add(0, 0.3, 0);
              }
              itemEntity.setMotion(moveVec);
            }
          }
        }
      }
    }
  }
  
  @Override
  public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand handIn)
  {

    return super.onItemRightClick(world, player, handIn);
  }
}
