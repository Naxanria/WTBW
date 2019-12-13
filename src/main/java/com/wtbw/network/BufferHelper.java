package com.wtbw.network;

import com.google.common.collect.ImmutableMap;
import com.wtbw.WTBW;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.state.IProperty;
import net.minecraft.state.IStateHolder;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;

import java.util.Map;
import java.util.Optional;

/*
  @author: Naxanria
*/
public class BufferHelper
{
  public static Vec3d readVec3d(PacketBuffer buffer)
  {
    return new Vec3d
    (
      buffer.readDouble(),
      buffer.readDouble(),
      buffer.readDouble()
    );
  }
  
  public static PacketBuffer writeVec3d(PacketBuffer buffer, Vec3d vec3d)
  {
    buffer.writeDouble(vec3d.x);
    buffer.writeDouble(vec3d.y);
    buffer.writeDouble(vec3d.z);
    
    return buffer;
  }
  
  public static BlockState readState(PacketBuffer buffer)
  {
    String id = buffer.readString();
    int size = buffer.readInt();
  
    Block block = Registry.BLOCK.getOrDefault(new ResourceLocation(id));
    BlockState state = block.getDefaultState();
    StateContainer<Block, BlockState> stateContainer = block.getStateContainer();
    for (int i = 0; i < size; i++)
    {
      String p = buffer.readString();
      IProperty<?> property = stateContainer.getProperty(p);
      String v = buffer.readString();
      if (property != null)
      {
        state = setValueHelper(state, property, v);
      }
      else
      {
        WTBW.LOGGER.warn("Unknown property {} for {}", p, block);
      }
    }
  
    return state;
  }
  
  private static <S extends IStateHolder<S>, T extends Comparable<T>> S setValueHelper(S state, IProperty<T> property, String valueName)
  {
    Optional<T> optional = property.parseValue(valueName);
    if (optional.isPresent())
    {
      return (S)(state.with(property, (T)(optional.get())));
    }
    else
    {
      WTBW.LOGGER.warn("Unable to read property: {} with value: {} for blockstate: {}", property.getName(), valueName, state.toString());
      return state;
    }
  }
  
  public static PacketBuffer writeState(PacketBuffer buffer, BlockState state)
  {
    String id = Registry.BLOCK.getKey(state.getBlock()).toString();
    ImmutableMap<IProperty<?>, Comparable<?>> values = state.getValues();
    int size = values.size();
    buffer.writeString(id);
    buffer.writeInt(size);
  
    for(Map.Entry<IProperty<?>, Comparable<?>> entry : values.entrySet())
    {
      IProperty<?> property = entry.getKey();
      String name = property.getName();
      String value = getName(property, entry.getValue());
      buffer.writeString(name);
      buffer.writeString(value);
    }
    
    return buffer;
  }
  
  public static <T extends Comparable<T>> String getName(IProperty<T> property, Comparable<?> value) {
    return property.getName((T)value);
  }
}
