package com.wtbw.item;

import com.wtbw.config.CommonConfig;
import com.wtbw.tile.MagnetInhibitorTileEntity;
import com.wtbw.util.NBTHelper;
import com.wtbw.util.StackUtil;
import com.wtbw.util.Utilities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

/*
  @author: Naxanria
*/
public class MagnetItem extends Item {
    public MagnetItem(Properties properties) {
        super(properties);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected) {
        // todo: make config option
        if (!world.isRemote) {
            if (!isActive(stack)) {
                return;
            }

            if (!(entity instanceof PlayerEntity)) {
                // we don't let mobs magnetize stuff... for now
                return;
            }

            PlayerEntity player = (PlayerEntity) entity;
            MinecraftServer server = world.getServer();
            CommonConfig config = CommonConfig.get();
            int tickRate = config.magnetTickRate.get();

            if (server != null && server.getTickCounter() % tickRate == 0) {

                int radius = config.magnetRadius.get();
                boolean checkCanPickup = config.magnetCheckCanPickUp.get();

                List<Entity> entities = world.getEntitiesWithinAABB(Entity.class, Utilities.getBoundingBox(entity.getPosition(), radius));

                for (Entity e : entities) {
                    if (e instanceof ItemEntity) {
                        ItemEntity itemEntity = (ItemEntity) e;
                        if (!itemEntity.cannotPickup()) {
                            if (checkCanPickup) {
                                if (!StackUtil.canInsert(player, itemEntity.getItem(), false)) {
                                    continue;
                                }
                            }

                            if (MagnetInhibitorTileEntity.isInhibitorInRange(e)) {
                                continue;
                            }

                            float speed = 3;
                            Vec3d moveVec = entity.getPositionVec().subtract(itemEntity.getPositionVec()).add(0, 1, 0).normalize().mul(speed, speed, speed);
                            if (itemEntity.collidedHorizontally) {
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
    public boolean hasEffect(ItemStack stack) {
        return isActive(stack);
    }

    private boolean swapActive(ItemStack stack) {
        boolean active = !isActive(stack);
        setActive(stack, active);
        return active;
    }

    private boolean setActive(ItemStack stack, boolean active) {
        stack.getOrCreateChildTag("data").putBoolean("active", active);
        return active;
    }

    private boolean isActive(ItemStack stack) {
        return NBTHelper.getBoolean(stack.getOrCreateChildTag("data"), "active", false);
    }


    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        if (!world.isRemote) {
            ItemStack stack = player.getHeldItem(hand);
            swapActive(stack);
        }

        return super.onItemRightClick(world, player, hand);
    }
}
