package com.wtbw;

import com.wtbw.block.BaseTileBlock;
import com.wtbw.block.ModBlocks;
import com.wtbw.block.TieredFurnaceBlock;
import com.wtbw.block.trashcan.EnergyTrashCanBlock;
import com.wtbw.block.trashcan.FluidTrashCanBlock;
import com.wtbw.block.trashcan.TrashCanBlock;
import com.wtbw.block.redstone.RedstoneEmitterBlock;
import com.wtbw.block.redstone.RedstoneTimerBlock;
import com.wtbw.config.CommonConfig;
import com.wtbw.gui.container.BaseTileContainer;
import com.wtbw.gui.container.TieredFurnaceContainer;
import com.wtbw.gui.container.TrashCanContainer;
import com.wtbw.item.MagnetItem;
import com.wtbw.item.tools.HammerItem;
import com.wtbw.item.tools.SwapTool;
import com.wtbw.item.tools.Trowel;
import com.wtbw.tile.MagnetInhibitorTileEntity;
import com.wtbw.tile.furnace.FurnaceTier;
import com.wtbw.tile.redstone.RedstoneTimerTileEntity;
import com.wtbw.util.TextComponentBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.*;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/*
  @author: Naxanria
*/
@SuppressWarnings("ConstantConditions")
public class Registrator
{
  private static List<BlockItem> blockItems = new ArrayList<>();
  private static IForgeRegistry<Block> blockRegistry;
  private static IForgeRegistry<Item> itemRegistry;
  private static IForgeRegistry<TileEntityType<?>> tileRegistry;
  private static IForgeRegistry<ContainerType<?>> containerRegistry;
  
  private static void registerAllBlocks()
  {
    // register blocks here
    register(new Block(getBlockProperties(Material.ROCK).hardnessAndResistance(5, 6)), "charcoal_block", false);

    register(new Block(getBlockProperties(Material.ROCK).hardnessAndResistance(1.5F, 6.0F).lightValue(15)), "lava_stone_brick");
    register(new Block(getBlockProperties(Material.ROCK).hardnessAndResistance(1.5F, 6.0F).lightValue(15)), "lava_chiseled_stone_brick");
    register(new Block(getBlockProperties(Material.ROCK).hardnessAndResistance(1.5F, 6.0F)), "water_stone_brick");
    register(new Block(getBlockProperties(Material.ROCK).hardnessAndResistance(1.5F, 6.0F)), "water_chiseled_stone_brick");

    register(new TieredFurnaceBlock(getBlockProperties(Material.IRON).hardnessAndResistance(7), FurnaceTier.IRON), "iron_furnace");
    register(new TieredFurnaceBlock(getBlockProperties(Material.IRON).hardnessAndResistance(7), FurnaceTier.GOLD), "gold_furnace");
    register(new TieredFurnaceBlock(getBlockProperties(Material.IRON).hardnessAndResistance(7), FurnaceTier.DIAMOND), "diamond_furnace");
    register(new TieredFurnaceBlock(getBlockProperties(Material.IRON).hardnessAndResistance(7), FurnaceTier.END), "end_furnace");
    
    register(new RedstoneTimerBlock(getBlockProperties(Material.IRON).hardnessAndResistance(4)), "redstone_timer");
    register(new RedstoneEmitterBlock(getBlockProperties(Material.IRON).hardnessAndResistance(4)), "redstone_emitter");
    
    register(new TrashCanBlock(getBlockProperties(Material.IRON).hardnessAndResistance(4)), "trashcan");
    register(new FluidTrashCanBlock(getBlockProperties(Material.IRON).hardnessAndResistance(4)), "fluid_trashcan");
    register(new EnergyTrashCanBlock(getBlockProperties(Material.IRON).hardnessAndResistance(4)), "energy_trashcan");
    
    register(new BaseTileBlock<MagnetInhibitorTileEntity>(getBlockProperties(Material.IRON).hardnessAndResistance(4),
      (world, state) -> new MagnetInhibitorTileEntity())
     {
       @Override
       public void addInformation(ItemStack stack, @Nullable IBlockReader world, List<ITextComponent> tooltip, ITooltipFlag flag)
       {
         tooltip.add(TextComponentBuilder.createTranslated(WTBW.MODID + ".tooltip.magnet_inhibitor", 3).aqua().build());
         
         super.addInformation(stack, world, tooltip, flag);
       }
     }
      , "magnet_inhibitor");
  }
  
  private static void registerAllItems()
  {
    // register items here
    register(new Item(getItemProperties()){
      @Override
      public int getBurnTime(ItemStack itemStack)
      {
        return 200;
      }
    }, "tiny_coal");
  
    register(new Item(getItemProperties()){
      @Override
      public int getBurnTime(ItemStack itemStack)
      {
        return 200;
      }
    }, "tiny_charcoal");
    
    register(new BlockItem(ModBlocks.CHARCOAL_BLOCK, getItemProperties()){
      @Override
      public int getBurnTime(ItemStack itemStack)
      {
        return 14400;
      }
    }, "charcoal_block");
    
    // durability multiplier
    int dMul = CommonConfig.get().toolsDurabilityMultiplier.get();
    register(new HammerItem(ItemTier.STONE, 6, -3.6f, getItemProperties().maxDamage(Items.STONE_PICKAXE.getMaxDamage(null) * dMul)), "stone_hammer");
    register(new HammerItem(ItemTier.IRON, 8, -3.6f, getItemProperties().maxDamage(Items.IRON_PICKAXE.getMaxDamage(null) * dMul)), "iron_hammer");
    register(new HammerItem(ItemTier.GOLD, 6, -3.2f, getItemProperties().maxDamage(Items.GOLDEN_PICKAXE.getMaxDamage(null) * dMul)), "gold_hammer");
    register(new HammerItem(ItemTier.DIAMOND, 11, -3.6f, getItemProperties().maxDamage(Items.DIAMOND_PICKAXE.getMaxDamage(null) * dMul)), "diamond_hammer");
    
    register(new Trowel(getItemProperties()), "trowel");
    
    register(new SwapTool(ItemTier.STONE, getItemProperties()), "stone_swap_tool");
    register(new SwapTool(ItemTier.IRON, getItemProperties()), "iron_swap_tool");
    register(new SwapTool(ItemTier.DIAMOND, getItemProperties()), "diamond_swap_tool");
    
    register(new MagnetItem(getItemProperties().maxStackSize(1)), "magnet");
  }
  
  private static void registerAllTiles()
  {
//    register(() -> new BaseFurnaceTileEntity(FurnaceTier.IRON, IRecipeType.SMELTING), ModBlocks.TIERED_FURNACE, "iron_furnace");
//    register(ModBlocks.TIERED_FURNACE.tileEntityProvider::get, ModBlocks.TIERED_FURNACE, "iron_furnace");
    register(ModBlocks.IRON_FURNACE);
    register(ModBlocks.GOLD_FURNACE);
    register(ModBlocks.DIAMOND_FURNACE);
    register(ModBlocks.END_FURNACE);
    
    register(RedstoneTimerTileEntity::new, ModBlocks.REDSTONE_TIMER, "redstone_timer");
    
    register(ModBlocks.TRASHCAN);
    register(ModBlocks.FLUID_TRASHCAN);
    register(ModBlocks.ENERGY_TRASHCAN);
    
    register(ModBlocks.MAGNET_INHIBITOR);
  }
  
  private static void registerAllContainers()
  {
    registerContainer(TieredFurnaceContainer::new, "tiered_furnace");
    registerContainer(TrashCanContainer::new, "trashcan");
  }
  
  ////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////
  
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
  {
    tileRegistry = event.getRegistry();
    registerAllTiles();
  }
  
  public static void registerContainer(RegistryEvent.Register<ContainerType<?>> event)
  {
    containerRegistry = event.getRegistry();
//
//    registry.register(IForgeContainerType.create(Registrator::register
//    ).setRegistryName(WTBW.MODID, "tiered_furnace"));
    
    registerAllContainers();
  
//    containerRegistry.register(IForgeContainerType.create((windowId, inv, data) ->
//      {
//        BlockPos pos = data.readBlockPos();
//        return new TrashCanContainer(windowId, ClientSetup.getWorld(), pos, inv);
//      }
//    ).setRegistryName(WTBW.MODID, "trashcan"));
  }
  
  interface IContainerFactory
  {
    BaseTileContainer<?> create(int windowId, World world, BlockPos pos, PlayerInventory inv);
  }
  
  
//  private static TieredFurnaceContainer register(int windowId, PlayerInventory inv, PacketBuffer data)
//  {
//    BlockPos pos = data.readBlockPos();
//    return new TieredFurnaceContainer(windowId, ClientSetup.getWorld(), pos, inv);
//  }
  
  private static void registerContainer(IContainerFactory factory, String name)
  {
    containerRegistry.register(IForgeContainerType.create((windowId, inv, data) ->
      factory.create(windowId, ClientSetup.getWorld(), data.readBlockPos(), inv)
    ).setRegistryName(WTBW.MODID, name));
  }
  
  private static <T extends Block> T register(T block, String registryName)
  {
    return register(block, registryName, true, null);
  }
  
  private static <T extends Block> T register(T block, String registryName, boolean createBlockItem)
  {
    return register(block, registryName, createBlockItem, null);
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
  
  private static TileEntityType<?> register(BaseTileBlock tileBlock)
  {
    return register(tileBlock.tileEntityProvider::get, tileBlock, tileBlock.getRegistryName().getPath());
  }
  
  private static TileEntityType<?> register(Supplier<TileEntity> factory, Block block, String registryName)
  {
    TileEntityType<TileEntity> type = TileEntityType.Builder.create(factory, block).build(null);
    type.setRegistryName(WTBW.MODID, registryName);
    tileRegistry.register(type);
    return type;
  }

}
