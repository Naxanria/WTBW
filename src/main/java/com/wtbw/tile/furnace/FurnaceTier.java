package com.wtbw.tile.furnace;

import com.wtbw.tile.ModTiles;
import net.minecraft.tileentity.TileEntityType;

import java.util.function.Supplier;

/*
  @author: Naxanria
*/
public class FurnaceTier
{
  public static final FurnaceTier IRON = new FurnaceTier("Iron", () -> ModTiles.IRON_FURNACE, 160);
  public static final FurnaceTier GOLD = new FurnaceTier("Gold", () -> ModTiles.GOLD_FURNACE, 80);
  public static final FurnaceTier DIAMOND = new FurnaceTier("DIAMOND", () -> ModTiles.DIAMOND_FURNACE, 40);
  public static final FurnaceTier END = new FurnaceTier("End", () -> ModTiles.END_FURNACE, 10);

  public final String name;
  private final Supplier<TileEntityType<?>> tileProvider;
  private int cookTime;

  public FurnaceTier(String name, Supplier<TileEntityType<?>> tileProvider, int cookTime)
  {
    this.tileProvider = tileProvider;
    this.cookTime = cookTime;
    this.name = name;
  }

  public int getCookTime()
  {
    return cookTime;
  }

  public FurnaceTier setCookTime(int cookTime)
  {
    this.cookTime = cookTime;
    return this;
  }

  public TileEntityType<?> getTileEntityType()
  {
    return tileProvider.get();
  }
}
