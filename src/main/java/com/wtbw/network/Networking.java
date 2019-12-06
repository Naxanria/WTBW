package com.wtbw.network;

import com.wtbw.WTBW;
import com.wtbw.util.Utilities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import java.security.Provider;
import java.util.List;
import java.util.function.Supplier;

/*
  @author: Naxanria
*/
public class Networking
{
  public static SimpleChannel INSTANCE;
  private static int id = 0;

  protected static int id()
  {
    return id++;
  }

  public static void registerMessages()
  {
    INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(WTBW.MODID, "network"), () -> "1.0", s -> true, s -> true);

    INSTANCE.registerMessage(id(), CycleToolPacket.class, CycleToolPacket::toBytes, CycleToolPacket::new, CycleToolPacket::handle);
    INSTANCE.registerMessage(id(), ColoredRedstoneParticlePacket.class, ColoredRedstoneParticlePacket::toBytes, ColoredRedstoneParticlePacket::new, ColoredRedstoneParticlePacket::handle);
  }
  
  public static int sendAround(World world, BlockPos pos, double radius, Object packet)
  {
    if (world.isRemote)
    {
      throw new IllegalStateException("Can only be used on server side!");
    }
    
    List<Entity> players = world.getEntitiesWithinAABB(EntityType.PLAYER, Utilities.getBoundingBox(pos, radius), (e) -> true);
    for (Entity e : players)
    {
      ServerPlayerEntity player = (ServerPlayerEntity) e;
      INSTANCE.sendTo(packet, player.connection.netManager, NetworkDirection.PLAY_TO_CLIENT);
    }
    
    return players.size();
  }

}
