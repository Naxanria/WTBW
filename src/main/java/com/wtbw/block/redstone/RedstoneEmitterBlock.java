package com.wtbw.block.redstone;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

/*
  @author: Naxanria
*/
public class RedstoneEmitterBlock extends Block {
    public static final IntegerProperty POWER = BlockStateProperties.POWER_0_15;

    public RedstoneEmitterBlock(Properties properties) {
        super(properties);

        setDefaultState(stateContainer.getBaseState().with(POWER, 0));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(POWER);
    }

    @Override
    public int getWeakPower(BlockState state, IBlockReader world, BlockPos pos, Direction side) {
        return state.get(POWER);
    }

    @Override
    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (player.isAllowEdit() && handIn != Hand.OFF_HAND) {
            if (!worldIn.isRemote) {
                if (player.getHeldItem(handIn).isEmpty()) {
                    BlockState newState;
                    if (player.isSneaking()) {
                        int next = state.get(POWER) - 1;
                        if (next < 0) {
                            next = 15;
                        }
                        newState = state.with(POWER, next);
                    } else {
                        newState = state.cycle(POWER);
                    }

                    worldIn.setBlockState(pos, newState, 4);
                    worldIn.notifyNeighbors(pos, this);
                    return true;
                }
            } else {
                return true;
            }
        }
        return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
    }

    @Override
    public boolean canProvidePower(BlockState state) {
        return true;
    }
}
