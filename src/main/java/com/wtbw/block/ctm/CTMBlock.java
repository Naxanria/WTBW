package com.wtbw.block.ctm;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;

import javax.annotation.Nullable;

/*
  @author: Naxanria
*/
public class CTMBlock extends Block
{
  public static final BooleanProperty NORTH = BooleanProperty.create("north");
  public static final BooleanProperty SOUTH = BooleanProperty.create("south");
  public static final BooleanProperty WEST = BooleanProperty.create("west");
  public static final BooleanProperty EAST = BooleanProperty.create("east");
  public static final BooleanProperty UP = BooleanProperty.create("up");
  public static final BooleanProperty DOWN = BooleanProperty.create("down");
  private String textureLocation;
  
  public CTMBlock(Properties properties)
  {
    this(properties, null);
  }
  
  public CTMBlock(Properties properties, String textureLocation)
  {
    super(properties);
    this.textureLocation = textureLocation;
  
    setDefaultState
    (
      getDefaultState()
        .with(NORTH, false)
        .with(SOUTH, false)
        .with(WEST, false)
        .with(EAST, false)
        .with(UP, false)
        .with(DOWN, false)
    );
  }
  
  @Nullable
  @Override
  public BlockState getStateForPlacement(BlockItemUseContext context)
  {
    return makeConnections(context.getWorld(), context.getPos());
  }
  
  @Override
  public void onNeighborChange(BlockState state, IWorldReader world, BlockPos pos, BlockPos neighbor)
  {
    BlockState newState = makeConnections(world, pos);
    if (newState != world.getBlockState(pos))
    {
    
    }
  }
  
  @Override
  public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos)
  {
    return makeConnections(worldIn, currentPos);
  }
  
  public BlockState makeConnections(IWorldReader world, BlockPos pos)
  {
    return getDefaultState()
      .with(NORTH, connected(world, pos.north()))
      .with(SOUTH, connected(world, pos.south()))
      .with(WEST, connected(world, pos.west()))
      .with(EAST, connected(world, pos.east()))
      .with(UP, connected(world, pos.up()))
      .with(DOWN, connected(world, pos.down()));
  }
  
  public boolean connected(IWorldReader world, BlockPos pos)
  {
    return world.getBlockState(pos).getBlock() == this;
  }
  
  @Override
  protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
  {
    builder.add(NORTH, SOUTH, WEST, EAST, UP, DOWN);
  }
  
  public String getTextureLocation()
  {
    if (textureLocation == null)
    {
      return "block/" + getRegistryName().getPath();
    }
    
    return "block/" + textureLocation;
  }
  
}
