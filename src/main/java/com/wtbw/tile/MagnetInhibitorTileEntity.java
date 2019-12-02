package com.wtbw.tile;

import com.google.common.collect.MapMaker;
import com.wtbw.util.Utilities;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;

import java.util.Collections;
import java.util.Set;

/*
  @author: Naxanria
*/
@SuppressWarnings("ConstantConditions")
public class MagnetInhibitorTileEntity extends TileEntity implements ITickableTileEntity {
    private static Set<MagnetInhibitorTileEntity> inhibitors = Collections.newSetFromMap(new MapMaker().concurrencyLevel(2).weakKeys().makeMap());

    private AxisAlignedBB inhibitBox;

    public MagnetInhibitorTileEntity() {
        super(ModTiles.MAGNET_INHIBITOR);
    }

    public static boolean isInhibitorInRange(Entity e) {
        return inhibitors.stream()
                .filter(inhibitor -> !inhibitor.isRemoved())
                .filter(inhibitor -> inhibitor.world == e.world)
                .filter(inhibitor -> inhibitor.world.getTileEntity(inhibitor.pos) == inhibitor)
                .anyMatch(inhibitor -> inhibitor.getInhibitBox().contains(e.getPositionVec()));
    }

    private AxisAlignedBB getInhibitBox() {
        if (inhibitBox == null) {
            inhibitBox = Utilities.getBoundingBox(pos, 3);
        }

        return inhibitBox;
    }

    @Override
    public void tick() {
        if (!world.isRemote) {
            if (!inhibitors.contains(this)) {
                inhibitors.add(this);
            }
        }
    }
}
