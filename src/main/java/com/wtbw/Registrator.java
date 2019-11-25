package com.wtbw;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.AbstractFurnaceTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;
import java.util.List;

/*
  @author: Naxanria
*/
public class Registrator
{
  private static List<BlockItem> blockItems = new ArrayList<>();
  private static IForgeRegistry<Block> blockRegistry;
  private static IForgeRegistry<Item> itemRegistry;
  
  private static void registerAllBlocks()
  {
    // register blocks here
    register(new Block(getBlockProperties(Material.ROCK).hardnessAndResistance(5, 6)), "charcoal_block");
  }
  
  private static void registerAllItems()
  {
    // register items here
    register(new Item(getItemProperties()){
      @Override
      public int getBurnTime(ItemStack itemStack)
      {
        return 800;
      }
    }, "mini_coal");
  
    register(new Item(getItemProperties()){
      @Override
      public int getBurnTime(ItemStack itemStack)
      {
        return 800;
      }
    }, "mini_charcoal");
  }
  
  private static Item.Properties getItemProperties()
  {
    return new Item.Properties().group(WTBW.GROUP);
  }
  
  private static Block.Properties getBlockProperties(Material material)
  {
    return Block.Properties.create(material);
  }
  
  
  public static void registerBlocks(RegistryEvent.Register<Block> event)
  {
    blockRegistry = event.getRegistry();
    
    registerAllBlocks();
  }
  
  public static void registerItems(RegistryEvent.Register<Item> event)
  {
    itemRegistry = event.getRegistry();
    for (BlockItem blockItem : blockItems)
    {
      itemRegistry.register(blockItem);
    }
  
    blockItems.clear();
    
    registerAllItems();
  }
  
  public static void registerTileEntity(RegistryEvent.Register<TileEntityType<?>> event)
  { }
  
  public static void registerContainer(RegistryEvent.Register<ContainerType<?>> event)
  { }
  
  private static <T extends Block> T register(T block, String registryName)
  {
    return register(block, registryName, true, null);
  }
  
  private static <T extends Block> T register(T block, String registryName, boolean createBlockItem, Item.Properties blockItemProperties)
  {
    block.setRegistryName(WTBW.MODID, registryName);
    if (createBlockItem)
    {
      if (blockItemProperties == null)
      {
        blockItemProperties = getItemProperties();
      }
      blockItems.add((BlockItem) new BlockItem(block, blockItemProperties).setRegistryName(WTBW.MODID, registryName));
    }
    
    blockRegistry.register(block);
    
    return block;
  }
  
  private static <T extends Item> T register(T item, String name)
  {
    itemRegistry.register(item.setRegistryName(WTBW.MODID, name));
    return item;
  }
  
}
